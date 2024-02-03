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

                // cart 엔티티 생성 및 연관관계 매핑
                Cart cart = CartConverter.toCart(cartOptionAddDTO.getAmount());
                cart.setMember(member);
                cart.setItem(item);
                cart.setItemOption(itemOption);
                return cart;
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

            // cart 엔티티 생성 및 연관관계 매핑
            Cart cart = CartConverter.toCart(request.getAmount());
            cart.setMember(member);
            cart.setItem(item);

            // 엔티티 저장
            cartRepository.save(cart);
        }
    }
}
