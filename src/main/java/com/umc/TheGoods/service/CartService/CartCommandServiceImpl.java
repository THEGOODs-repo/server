package com.umc.TheGoods.service.CartService;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.ItemHandler;
import com.umc.TheGoods.apiPayload.exception.handler.OrderHandler;
import com.umc.TheGoods.converter.cart.CartConverter;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.Cart;
import com.umc.TheGoods.domain.order.CartDetail;
import com.umc.TheGoods.repository.cart.CartDetailRepository;
import com.umc.TheGoods.repository.cart.CartRepository;
import com.umc.TheGoods.service.ItemService.ItemQueryService;
import com.umc.TheGoods.web.dto.cart.CartRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartCommandServiceImpl implements CartCommandService {

    private final ItemQueryService itemQueryService;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;

    @Override
    public void addCart(CartRequestDTO.cartAddDTO request, Member member) {
        // item, itemOption 존재 여부 검증은 annotation으로 진행
        Item item = itemQueryService.findItemById(request.getItemId()).get();

        if (!item.getItemOptionList().isEmpty()) { // 옵션이 있는 상품의 경우
            // request에 optionAddDTO 리스트가 값을 갖고 있는지 검증
            if (request.getCartOptionAddDTOList().isEmpty()) {
                throw new OrderHandler(ErrorStatus.NULL_ITEMOPTION_ERROR);
            }

            // cartOptionAddDTOList를 돌며 cart, cartDetail 엔티티 생성 및 업데이트
            request.getCartOptionAddDTOList().forEach(cartOptionAddDTO -> {
                ItemOption itemOption = itemQueryService.findItemOptionById(cartOptionAddDTO.getItemOptionId()).get();
                // itemOption이 해당 item의 것이 맞는지 검증
                if (!itemOption.getItem().equals(item)) {
                    throw new ItemHandler(ErrorStatus.ITEMOPTION_NOT_MATCH);
                }

                // 재고 수량과 비교
                if (cartOptionAddDTO.getAmount() > itemOption.getStock()) {
                    throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
                }

                // cart 테이블에 해당 장바구니 내역 이미 존재하면, 해당 내역의 담은 수량만 업데이트
                Optional<Cart> cart = cartRepository.findByMemberAndItem(member, item);
                if (cart.isPresent()) {  // cart 테이블에 해당 장바구니 내역 존재하는 경우
                    Optional<CartDetail> existCartDetail = cartDetailRepository.findByCartAndItemOption(cart.get(), itemOption);
                    if (!existCartDetail.isEmpty()) { // cartDetail 테이블에 해당 옵션 내역 이미 존재하는 경우
                        // 재고 수량과 비교
                        if (cartOptionAddDTO.getAmount() + existCartDetail.get().getAmount() > itemOption.getStock()) {
                            throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
                        }
                        // 장바구니 상세 내역의 수량 업데이트
                        existCartDetail.get().updateAmount(existCartDetail.get().getAmount() + cartOptionAddDTO.getAmount());
                        cartDetailRepository.save(existCartDetail.get());

                    } else { // cart 내역은 존재하지만 cartDetail 내역은 존재하지 않는 경
                        // 신규 장바구니 상세 내역 생성
                        CartDetail cartDetail = CartConverter.toCartDetail(cartOptionAddDTO.getAmount());
                        cartDetail.setCart(cart.get());
                        cartDetail.setItemOption(itemOption);
                        cartDetailRepository.save(cartDetail);
                    }

                } else { // cart 테이블에 해당 장바구니 내역이 없는 경우
                    // 신규 장바구니 내역 생성
                    Cart newCart = CartConverter.toCart();
                    newCart.setMember(member);
                    newCart.setItem(item);

                    // 신규 장바구니 상세 내역 생성
                    CartDetail newCartDetail = CartConverter.toCartDetail(cartOptionAddDTO.getAmount());
                    newCartDetail.setCart(newCart);
                    newCartDetail.setItemOption(itemOption);

                    cartRepository.save(newCart);
                    cartDetailRepository.save(newCartDetail);
                }
            });

        } else { // 옵션이 없는 상품의 경우
            // request에 optionAddDTO 리스트가 비어있는지 검증
            if (!request.getCartOptionAddDTOList().isEmpty()) {
                throw new ItemHandler(ErrorStatus.ITEMOPTION_NOT_MATCH);
            }

            // request에 amount 값이 있는지 검증
            if (request.getAmount() == null) {
                throw new OrderHandler(ErrorStatus.NULL_AMOUNT_ERROR);
            }

            // 재고 수량과 비교
            if (request.getAmount() > item.getStock()) {
                throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
            }

            Optional<Cart> cart = cartRepository.findByMemberAndItem(member, item);
            if (cart.isPresent()) { // 장바구니 내역 이미 존재하는 경우
                CartDetail cartDetail = cartDetailRepository.findByCart(cart.get()).orElseThrow(() -> new OrderHandler(ErrorStatus.CART_DETAIL_FOUND_ERROR));

                // 재고 수량과 비교
                if (request.getAmount() + cartDetail.getAmount() > item.getStock()) {
                    throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
                }

                // 장바구니 내역의 수량 업데이트
                cartDetail.updateAmount(request.getAmount());
                cartDetailRepository.save(cartDetail);

            } else { // 장바구니 및 상세 내역 신규 생성

                // 신규 장바구니 내역 생성
                Cart newCart = CartConverter.toCart();
                newCart.setMember(member);
                newCart.setItem(item);

                // 신규 장바구니 상세 내역 생성
                CartDetail newCartDetail = CartConverter.toCartDetail(request.getAmount());
                newCartDetail.setCart(newCart);

                cartRepository.save(newCart);
                cartDetailRepository.save(newCartDetail);
            }
        }
    }

    @Override
    public List<CartDetail> updateCart(CartRequestDTO.cartUpdateDTOList request, Member member) {
        // cartDetail 존재 여부 검증은 annotation에서 진행

        List<CartDetail> cartDetailList = request.getCartUpdateDTOList().stream().map(cartUpdateDTO -> {
            CartDetail cartDetail = cartDetailRepository.findById(cartUpdateDTO.getCartDetailId()).get();

            // 해당 cart 내역을 수정할 권한 있는지 검증
            if (!cartDetail.getCart().getMember().equals(member)) {
                throw new OrderHandler(ErrorStatus.NOT_CART_OWNER);
            }

            // 재고 수량과 비교
            if (!cartDetail.getCart().getItem().getItemOptionList().isEmpty()) { // 상품 옵션이 있는 경우
                if (cartUpdateDTO.getAmount() > cartDetail.getItemOption().getStock()) {
                    throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
                }
            } else {
                if (cartUpdateDTO.getAmount() > cartDetail.getCart().getItem().getStock()) {
                    throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
                }
            }

            return cartDetail.updateAmount(cartUpdateDTO.getAmount());

        }).collect(Collectors.toList());

        return cartDetailList;
    }

    @Override
    public void deleteCartDetail(CartRequestDTO.cartDetailDeleteDTO request, Member member) {


        request.getCartDetailIdList().forEach(cartDetailId -> {

            CartDetail cartDetail = cartDetailRepository.findById(cartDetailId).orElseThrow(() -> new OrderHandler(ErrorStatus.CART_DETAIL_NOT_FOUND));

            Cart cart = cartDetail.getCart();

            // 해당 cart 내역을 수정할 권한 있는지 검증
            if (!cartDetail.getCart().getMember().equals(member)) {
                throw new OrderHandler(ErrorStatus.NOT_CART_OWNER);
            }

            cartDetailRepository.deleteById(cartDetail.getId());

            // 장바구니 상품의 마지막 옵션 내역을 삭제한 경: 장바구니 상품 내역도 삭제
            if (cart.getCartDetailList().isEmpty()) {
                cartRepository.delete(cart);
            }
        });

    }
}
