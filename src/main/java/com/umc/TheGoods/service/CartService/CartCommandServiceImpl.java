package com.umc.TheGoods.service.CartService;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.ItemHandler;
import com.umc.TheGoods.apiPayload.exception.handler.OrderHandler;
import com.umc.TheGoods.converter.cart.CartConverter;
import com.umc.TheGoods.domain.item.Item;
import com.umc.TheGoods.domain.item.ItemOption;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.domain.order.Cart;
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

    @Override
    public void addCart(CartRequestDTO.cartAddDTO request, Member member) {
        // item, itemOption 존재 여부 검증은 annotation으로 진행
        Item item = itemQueryService.findItemById(request.getItemId()).get();

        if (!item.getItemOptionList().isEmpty()) { // 옵션이 있는 상품의 경우
            // request에 optionAddDTO 리스트가 값을 갖고 있는지 검증
            if (request.getCartOptionAddDTOList().isEmpty()) {
                throw new OrderHandler(ErrorStatus.NULL_ITEMOPTION_ERROR);
            }

            // cartList 생성
            List<Cart> cartList = request.getCartOptionAddDTOList().stream().map(cartOptionAddDTO -> {
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
                Optional<Cart> existCart = cartRepository.findByMemberAndItemAndItemOption(member, item, itemOption);
                if (!existCart.isEmpty()) { // 장바구니 내역 이미 존재하는 경우
                    // 재고 수량과 비교
                    if (cartOptionAddDTO.getAmount() + existCart.get().getAmount() > itemOption.getStock()) {
                        throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
                    }

                    // 장바구니 내역의 수량 업데이트
                    existCart.get().updateAmount(existCart.get().getAmount() + cartOptionAddDTO.getAmount());
                    return existCart.get();
                } else { // 신규 장바구니 내역 생성
                    // cart 엔티티 생성 및 연관관계 매핑
                    Cart cart = CartConverter.toCart(cartOptionAddDTO.getAmount());
                    cart.setMember(member);
                    cart.setItem(item);
                    cart.setItemOption(itemOption);
                    return cart;
                }
            }).collect(Collectors.toList());

            // 엔티티 저장
            cartRepository.saveAll(cartList);

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

            Optional<Cart> existCart = cartRepository.findByMemberAndItem(member, item);
            if (!existCart.isEmpty()) { // 장바구니 내역 이미 존재하는 경우
                // 재고 수량과 비교
                if (request.getAmount() + existCart.get().getAmount() > item.getStock()) {
                    throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
                }

                // 장바구니 내역의 수량 업데이트
                existCart.get().updateAmount(request.getAmount());
            } else { // 장바구니 내역 신규 생성

                // cart 엔티티 생성 및 연관관계 매핑
                Cart cart = CartConverter.toCart(request.getAmount());
                cart.setMember(member);
                cart.setItem(item);

                // 엔티티 저장
                cartRepository.save(cart);
            }


        }
    }

    @Override
    public Cart updateCart(CartRequestDTO.cartUpdateDTO request, Member member) {
        // cart 존재 여부 검증은 annotation에서 진행
        Cart cart = cartRepository.findById(request.getCartId()).get();

        // 해당 cart 내역을 수정할 권한 있는지 검증
        if (!cart.getMember().equals(member)) {
            throw new OrderHandler(ErrorStatus.NOT_CART_OWNER);
        }

        // 재고 수량과 비교
        if (!cart.getItem().getItemOptionList().isEmpty()) { // 상품 옵션이 있는 경우
            if (request.getAmount() > cart.getItemOption().getStock()) {
                throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
            }
        } else {
            if (request.getAmount() > cart.getItem().getStock()) {
                throw new OrderHandler(ErrorStatus.LACK_OF_STOCK);
            }
        }

        return cart.updateAmount(request.getAmount());
    }
}
