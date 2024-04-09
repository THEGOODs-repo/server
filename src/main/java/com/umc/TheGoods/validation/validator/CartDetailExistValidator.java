package com.umc.TheGoods.validation.validator;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.service.CartService.CartQueryService;
import com.umc.TheGoods.validation.annotation.ExistCartDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class CartDetailExistValidator implements ConstraintValidator<ExistCartDetail, Long> {

    private final CartQueryService cartQueryService;

    @Override
    public void initialize(ExistCartDetail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        boolean isValid = true;
        //boolean isValid = cartQueryService.isExistCartDetail(value);
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.CART_DETAIL_NOT_FOUND.getMessage()).addConstraintViolation();
        }
        return isValid;
    }
}
