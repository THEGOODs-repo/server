package com.umc.TheGoods.service.CartService;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.ItemHandler;
import com.umc.TheGoods.apiPayload.exception.handler.OrderHandler;
import com.umc.TheGoods.converter.cart.CartConverter;
import com.umc.TheGoods.domain.enums.CartStatus;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.Cart;
import com.umc.TheGoods.repository.cart.CartRepository;
import com.umc.TheGoods.service.ItemService.ItemQueryService;
import com.umc.TheGoods.web.dto.cart.CartRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CartCommandServiceImpl implements CartCommandService {

    private final ItemQueryService itemQueryService;
    private final CartRepository cartRepository;

    @Override
    public void addCart(CartRequestDTO.cartAddDTOList request, Member member) {
        // item 존재 여부 검증은 annotation으로 진행
        request.getCartAddDTOList().forEach(cartAddDTO -> {
            Item item = itemQueryService.findItemById(cartAddDTO.getItemId()).get();

            if (!item.getItemOptionList().isEmpty()) { // 옵션이 있는 상품의 경우
                if (cartAddDTO.getItemOptionId() == null) {
                    throw new OrderHandler(ErrorStatus.NULL_ITEMOPTION_ERROR);
                }

                ItemOption itemOption = itemQueryService.findItemOptionById(cartAddDTO.getItemOptionId()).orElseThrow(() -> new ItemHandler(ErrorStatus.ITEMOPTION_NOT_FOUND));
                // itemOption이 해당 item의 것이 맞는지 검증
                if (!itemOption.getItem().equals(item)) {
                    throw new ItemHandler(ErrorStatus.ITEMOPTION_NOT_MATCH);
                }

                // 재고 수량과 비교
                if (cartAddDTO.getAmount() > itemOption.getStock()) {
                    throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
                }

                // cart 테이블에 해당 장바구니 내역 이미 존재하면, 해당 내역의 담은 수량만 업데이트
                Optional<Cart> cart = cartRepository.findByMemberAndItemAndItemOptionAndCartStatus(member, item, itemOption, CartStatus.ACTIVE);
                if (cart.isPresent()) { // cart 테이블에 해당 장바구니 내역 존재하는 경우
                    // 재고 수량과 비교
                    if (cartAddDTO.getAmount() + cart.get().getAmount() > itemOption.getStock()) {
                        throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
                    }
                    cart.get().updateAmount(cartAddDTO.getAmount());

                } else { // cart 테이블에 해당 장바구니 내역 존재하지 않는 경우
                    // 장바구니 내역 새로 생성
                    Cart newCart = CartConverter.toCartWithItemOption(item, itemOption, cartAddDTO.getAmount());
                    newCart.setMember(member);
                    cartRepository.save(newCart);
                }

            } else { // 옵션이 없는 상품의 경우
                // 재고 수량과 비교
                if (cartAddDTO.getAmount() > item.getStock()) {
                    throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
                }

                Optional<Cart> cart = cartRepository.findByMemberAndItemAndCartStatus(member, item, CartStatus.ACTIVE);
                if (cart.isPresent()) { // cart 테이블에 해당 장바구니 내역 존재하는 경우
                    if (cartAddDTO.getAmount() + cart.get().getAmount() > item.getStock()) {
                        throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
                    }

                    cart.get().updateAmount(cartAddDTO.getAmount());
                } else { // cart 테이블에 해당 장바구니 내역 존재하지 않는 경우
                    // 장바구니 내역 새로 생성
                    Cart newCart = CartConverter.toCartWithoutItemOption(item, cartAddDTO.getAmount());
                    newCart.setMember(member);
                    cartRepository.save(newCart);
                }
            }

        });


//        if (!item.getItemOptionList().isEmpty()) { // 옵션이 있는 상품의 경우
//            // request에 optionAddDTO 리스트가 값을 갖고 있는지 검증
//            if (request.getCartOptionAddDTOList().isEmpty()) {
//                throw new OrderHandler(ErrorStatus.NULL_ITEMOPTION_ERROR);
//            }
//
//            // cartOptionAddDTOList를 돌며 cart, cartDetail 엔티티 생성 및 업데이트
//            request.getCartOptionAddDTOList().forEach(cartOptionAddDTO -> {
//                ItemOption itemOption = itemQueryService.findItemOptionById(cartOptionAddDTO.getItemOptionId()).get();
//                // itemOption이 해당 item의 것이 맞는지 검증
//                if (!itemOption.getItem().equals(item)) {
//                    throw new ItemHandler(ErrorStatus.ITEMOPTION_NOT_MATCH);
//                }
//
//                // 재고 수량과 비교
//                if (cartOptionAddDTO.getAmount() > itemOption.getStock()) {
//                    throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
//                }
//
//                // cart 테이블에 해당 장바구니 내역 이미 존재하면, 해당 내역의 담은 수량만 업데이트
//                Optional<Cart> cart = cartRepository.findByMemberAndItem(member, item);
//                if (cart.isPresent()) {  // cart 테이블에 해당 장바구니 내역 존재하는 경우
//                    Optional<CartDetail> existCartDetail = cartDetailRepository.findByCartAndItemOption(cart.get(), itemOption);
//                    if (!existCartDetail.isEmpty()) { // cartDetail 테이블에 해당 옵션 내역 이미 존재하는 경우
//                        // 재고 수량과 비교
//                        if (cartOptionAddDTO.getAmount() + existCartDetail.get().getAmount() > itemOption.getStock()) {
//                            throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
//                        }
//                        // 장바구니 상세 내역의 수량 업데이트
//                        existCartDetail.get().updateAmount(existCartDetail.get().getAmount() + cartOptionAddDTO.getAmount());
//                        cartDetailRepository.save(existCartDetail.get());
//
//                    } else { // cart 내역은 존재하지만 cartDetail 내역은 존재하지 않는 경
//                        // 신규 장바구니 상세 내역 생성
//                        CartDetail cartDetail = CartConverter.toCartDetail(cartOptionAddDTO.getAmount());
//                        cartDetail.setCart(cart.get());
//                        cartDetail.setItemOption(itemOption);
//                        cartDetailRepository.save(cartDetail);
//                    }
//
//                } else { // cart 테이블에 해당 장바구니 내역이 없는 경우
//                    // 신규 장바구니 내역 생성
//                    Cart newCart = CartConverter.toCart();
//                    newCart.setMember(member);
//                    newCart.setItem(item);
//
//                    // 신규 장바구니 상세 내역 생성
//                    CartDetail newCartDetail = CartConverter.toCartDetail(cartOptionAddDTO.getAmount());
//                    newCartDetail.setCart(newCart);
//                    newCartDetail.setItemOption(itemOption);
//
//                    cartRepository.save(newCart);
//                    cartDetailRepository.save(newCartDetail);
//                }
//            });
//
//        } else { // 옵션이 없는 상품의 경우
//            // request에 optionAddDTO 리스트가 비어있는지 검증
//            if (!request.getCartOptionAddDTOList().isEmpty()) {
//                throw new ItemHandler(ErrorStatus.ITEMOPTION_NOT_MATCH);
//            }
//
//            // request에 amount 값이 있는지 검증
//            if (request.getAmount() == null) {
//                throw new OrderHandler(ErrorStatus.NULL_AMOUNT_ERROR);
//            }
//
//            // 재고 수량과 비교
//            if (request.getAmount() > item.getStock()) {
//                throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
//            }
//
//            Optional<Cart> cart = cartRepository.findByMemberAndItem(member, item);
//            if (cart.isPresent()) { // 장바구니 내역 이미 존재하는 경우
//                CartDetail cartDetail = cartDetailRepository.findByCart(cart.get()).orElseThrow(() -> new OrderHandler(ErrorStatus.CART_DETAIL_FOUND_ERROR));
//
//                // 재고 수량과 비교
//                if (request.getAmount() + cartDetail.getAmount() > item.getStock()) {
//                    throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
//                }
//
//                // 장바구니 내역의 수량 업데이트
//                cartDetail.updateAmount(request.getAmount());
//                cartDetailRepository.save(cartDetail);
//
//            } else { // 장바구니 및 상세 내역 신규 생성
//
//                // 신규 장바구니 내역 생성
//                Cart newCart = CartConverter.toCart();
//                newCart.setMember(member);
//                newCart.setItem(item);
//
//                // 신규 장바구니 상세 내역 생성
//                CartDetail newCartDetail = CartConverter.toCartDetail(request.getAmount());
//                newCartDetail.setCart(newCart);
//
//                cartRepository.save(newCart);
//                cartDetailRepository.save(newCartDetail);
//            }
//        }
    }

//    @Override
//    public List<CartDetail> updateCart(CartRequestDTO.cartUpdateDTOList request, Member member) {
//        // cartDetail 존재 여부 검증은 annotation에서 진행
//
//        List<CartDetail> cartDetailList = request.getCartUpdateDTOList().stream().map(cartUpdateDTO -> {
//            CartDetail cartDetail = cartDetailRepository.findById(cartUpdateDTO.getCartDetailId()).get();
//
//            // 해당 cart 내역을 수정할 권한 있는지 검증
//            if (!cartDetail.getCart().getMember().equals(member)) {
//                throw new OrderHandler(ErrorStatus.NOT_CART_OWNER);
//            }
//
//            // 재고 수량과 비교
//            if (!cartDetail.getCart().getItem().getItemOptionList().isEmpty()) { // 상품 옵션이 있는 경우
//                if (cartUpdateDTO.getAmount() > cartDetail.getItemOption().getStock()) {
//                    throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
//                }
//            } else {
//                if (cartUpdateDTO.getAmount() > cartDetail.getCart().getItem().getStock()) {
//                    throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
//                }
//            }
//
//            return cartDetail.updateAmount(cartUpdateDTO.getAmount());
//
//        }).collect(Collectors.toList());
//
//        return cartDetailList;
//    }
//
//    @Override
//    public void deleteCartDetail(CartRequestDTO.cartDetailDeleteDTO request, Member member) {
//
//        Cart firstCart = cartDetailRepository.findById(request.getCartDetailIdList().get(0)).orElseThrow(() -> new OrderHandler(ErrorStatus.CART_DETAIL_NOT_FOUND)).getCart();
//
//        // request의 cartDetailIdList로 cartDetailList 생성
//        List<CartDetail> cartDetailList = request.getCartDetailIdList().stream().map(cartDetailId -> {
//            CartDetail cartDetail = cartDetailRepository.findById(cartDetailId).orElseThrow(() -> new OrderHandler(ErrorStatus.CART_DETAIL_NOT_FOUND));
//
//            // 해당 cart 내역을 수정할 권한 있는지 검증
//            if (!cartDetail.getCart().getMember().equals(member)) {
//                throw new OrderHandler(ErrorStatus.NOT_CART_OWNER);
//            }
//
//            // cartDetailId가 모두 동일한 장바구니 내역에 대한 담은 옵션이 맞는지 검증
//            if (!firstCart.equals(cartDetail.getCart())) {
//                throw new OrderHandler(ErrorStatus.DELETE_CART_DETAIL_FAILED);
//            }
//            return cartDetail;
//        }).collect(Collectors.toList());
//
//
//        // 해당 장바구니 상품의 모든 담은 옵션 list 조회
//        List<CartDetail> originCartDetailList = firstCart.getCartDetailList();
//
//        List<Long> cartDetailIdList = cartDetailList.stream().map(cartDetail -> {
//            return cartDetail.getId();
//        }).collect(Collectors.toList());
//        List<Long> originCartDetailIdList = originCartDetailList.stream().map(originCartDetail -> {
//            return originCartDetail.getId();
//        }).collect(Collectors.toList());
//
//        // 장바구니 삭제
//        if (isEqualCartDetailList(cartDetailIdList, originCartDetailIdList)) { // 해당 장바구니 상품 내역의 담은 옵션 모두를 삭제하는 경우
//            cartRepository.deleteById(firstCart.getId());
//        } else { // 해당 장바구니 생품 내역의 담은 옵션 일부만 삭제하는 경우
//            cartDetailList.forEach(cartDetail -> {
//                cartDetail.detachCart();
//                cartDetail.detachItemOption();
//                cartDetailRepository.deleteById(cartDetail.getId());
//            });
//        }
//    }
//
//    @Override
//    public void deleteCart(CartRequestDTO.cartDeleteDTO request, Member member) {
//        request.getCartIdList().forEach(cartId -> {
//            Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new OrderHandler(ErrorStatus.CART_NOT_FOUND));
//
//            // 해당 cart 내역을 수정할 권한 있는지 검증
//            if (!cart.getMember().equals(member)) {
//                throw new OrderHandler(ErrorStatus.NOT_CART_OWNER);
//            }
//
//            cartRepository.deleteById(cart.getId());
//        });
//
//    }
//
//    private boolean isEqualCartDetailList(List<Long> x, List<Long> y) {
//        if (x == null) {
//            return y == null;
//        }
//
//        if (x.size() != y.size()) {
//            return false;
//        }
//
//        x = x.stream().sorted().collect(Collectors.toList());
//        y = y.stream().sorted().collect(Collectors.toList());
//
//        return x.equals(y);
//    }
}
