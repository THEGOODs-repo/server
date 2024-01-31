package com.umc.TheGoods.validation.validator;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.service.OrderService.OrderQueryService;
import com.umc.TheGoods.validation.annotation.ExistOrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class OrderItemExistValidator implements ConstraintValidator<ExistOrderItem, Long> {

    private final OrderQueryService orderQueryService;

    @Override
    public void initialize(ExistOrderItem constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        boolean isValid = orderQueryService.isExistOrderItem(value);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.ORDER_ITEM_NOT_FOUND.getMessage()).addConstraintViolation();
        }

        return isValid;
    }
}
