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
    _ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "ITEM4001", "상품 정보를 찾을 수 없습니다."),
    _ITEMOPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "ITEM4002", "상품 옵션 정보를 찾을 수 없습니다."),
    _ITEMOPTION_NOT_MATCH(HttpStatus.BAD_REQUEST, "ITEM4003", "해당 상품의 옵션이 아닙니다."),

    // 주문 관련 에러
    _LACK_OF_STOCK(HttpStatus.BAD_REQUEST, "ORDER4001", "재고가 부족합니다."),
    _NULL_ITEMOPTION_ERROR(HttpStatus.BAD_REQUEST, "ORDER4002", "주문할 상품 옵션을 선택해주세요."),

    // test
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "테스트");

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
