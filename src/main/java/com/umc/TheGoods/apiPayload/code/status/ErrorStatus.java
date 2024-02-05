package com.umc.TheGoods.apiPayload.code.status;

import com.umc.TheGoods.apiPayload.code.BaseErrorCode;
import com.umc.TheGoods.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 에러
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 상품 관련 에러
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "ITEM4001", "해당 상품을 찾을 수 없습니다."),
    ITEMOPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "ITEM4002", "해당 상품 옵션을 찾을 수 없습니다."),
    ITEMOPTION_NOT_MATCH(HttpStatus.BAD_REQUEST, "ITEM4003", "해당 상품의 옵션이 아닙니다."),

    // 주문 관련 에러
    LACK_OF_STOCK(HttpStatus.BAD_REQUEST, "ORDER4001", "재고가 부족합니다."),
    NULL_ITEMOPTION_ERROR(HttpStatus.BAD_REQUEST, "ORDER4002", "주문할 상품 옵션을 선택해주세요."),
    NO_LOGIN_ORDER_NOT_AVAILABLE(HttpStatus.INTERNAL_SERVER_ERROR, "ORDER4003", "비회원 주문 불가, 관리자에게 문의 바랍니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER4004", "해당 주문 내역을 찾을 수 없습니다"),
    ORDER_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER4005", "해당 주문 상품 내역을 찾을 수 없습니다."),
    NOT_ORDER_OWNER(HttpStatus.BAD_REQUEST, "ORDER4006", "본인의 주문 내역이 아닙니다. 접근할 수 없습니다."),
    NO_LOGIN_ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER4007", "비회원 주문 내역을 찾을 수 없습니다"),
    ORDER_ITEM_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "ORDER4008", "주문 상품 정보 변경이 불가합니다."),
    NULL_AMOUNT_ERROR(HttpStatus.BAD_REQUEST, "ORDER4009", "주문 수량은 null일 수 없습니다."),

    // 장바구니 관련 에러
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "CART4001", "해당 장바구니 내역을 찾을 수 없습니다."),
    NOT_CART_OWNER(HttpStatus.BAD_REQUEST, "CART4002", "본인의 장바구니 내역이 아닙니다. 접근할 수 없습니다."),

    // test
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "테스트"),

    // Member
    MEMBER_NICKNAME_DUPLICATED(HttpStatus.BAD_REQUEST, "MEMBER4001", "중복된 닉네임 입니다."),
    MEMBER_PASSWORD_ERROR(HttpStatus.BAD_REQUEST, "MEMBER4002", "비밀번호가 잘못되었습니다."),
    MEMBER_EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4003", "이메일이 존재하지 않습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4004", "해당 회원을 찾을 수 없습니다."),
    MEMBER_PHONE_AUTH_ERROR(HttpStatus.BAD_REQUEST, "MEMBER4005", "유효하지 않는 번호입니다."),
    MEMBER_EMAIL_AUTH_ERROR(HttpStatus.BAD_REQUEST, "MEMBER4006", "유효하지 않는 이메일입니다."),


    //Category
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY4001", "해당 카테고리가 존재하지 않습니다"),

    //Term
    TERM_NOT_FOUND(HttpStatus.NOT_FOUND, "TERM4001", "해당 약관이 존재하지 않습니다."),

    //tag error
    TAG_NOT_FOUND(HttpStatus.BAD_REQUEST, "TAG4001", "존재하지 않는 태그입니다."),

    // 페이징 관련 에러
    PAGE_NEGATIVE_INPUT(HttpStatus.BAD_REQUEST, "PAGE4001", "페이지 번호는 1이상의 숫자여야 합니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
