package com.umc.TheGoods.validation.validator;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.service.MemberService.MemberCommandService;
import com.umc.TheGoods.service.MemberService.MemberQueryService;
import com.umc.TheGoods.validation.annotation.ExistMemberName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberNameExistValidator implements ConstraintValidator<ExistMemberName, List<String>> {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @Override
    public void initialize(ExistMemberName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        boolean isValid = values.stream()
                .allMatch(value -> memberCommandService.existMemberById(memberQueryService.findMemberByNickname(value).get().getId()));

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.MEMBER_NOT_FOUND.getMessage()).addConstraintViolation();

        }

        return isValid;
    }
}
