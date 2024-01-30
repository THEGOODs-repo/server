package com.umc.TheGoods.validation.validator;

import com.umc.TheGoods.service.ItemService.ItemQueryService;
import com.umc.TheGoods.validation.annotation.OrderAvailable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class OrderAvailableValidator implements ConstraintValidator<OrderAvailable, Object> {

    private final ItemQueryService itemQueryService;

    @Override
    public void initialize(OrderAvailable constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
//        if (value instanceof OrderRequestDTO.OrderItemDto) {
//            OrderRequestDTO.OrderItemDto request = (OrderRequestDTO.OrderItemDto) value;
//
//            // item 존재 여부 검증
//            Optional<Item> item = itemQueryService.findItemById(request.getItemId());
//            if (item.isEmpty()) { // 상품이 존재하지 않는 경우
//                context.disableDefaultConstraintViolation();
//                context.buildConstraintViolationWithTemplate(ErrorStatus.ITEM_NOT_FOUND.getMessage()).addConstraintViolation();
//                return false;
//            }
//
//            if (request.getItemOptionId() != null) { // request에 itemOptionId 값이 있는 경우
//                // itemOption 존재 여부 검증
//                Optional<ItemOption> itemOption = itemQueryService.findItemOptionById(request.getItemOptionId());
//                if (itemOption.isEmpty()) { // 상품 옵션이 존재하지 않는 경우
//                    context.disableDefaultConstraintViolation();
//                    context.buildConstraintViolationWithTemplate(ErrorStatus.ITEMOPTION_NOT_FOUND.getMessage()).addConstraintViolation();
//                    return false;
//                }
//
//                // itemOption이 해당 item의 옵션이 맞는지 검증
//
//                boolean isValid = itemOption.get().getItem().equals(item.get());
//                if (!isValid) { // itemOption이 해당 상품의 옵션이 아닌 경우
//                    context.disableDefaultConstraintViolation();
//                    context.buildConstraintViolationWithTemplate(ErrorStatus.ITEMOPTION_NOT_MATCH.getMessage()).addConstraintViolation();
//                    return false;
//                }
//            } else {  // request에 itemOptionId 값이 없는 경우
//                // item이 단일 상품인지 검증
//                if (item.get().getPrice() == null) { // item이 단일 상품이 아닌 경우
//                    context.disableDefaultConstraintViolation();
//                    context.buildConstraintViolationWithTemplate(ErrorStatus.NULL_ITEMOPTION_ERROR.getMessage()).addConstraintViolation();
//                    return false;
//                }
//            }
//        }

        return true;
    }
}
