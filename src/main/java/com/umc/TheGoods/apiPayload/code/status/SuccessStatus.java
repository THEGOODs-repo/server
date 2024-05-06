package com.umc.TheGoods.apiPayload.code.status;

import com.umc.TheGoods.apiPayload.code.BaseCode;
import com.umc.TheGoods.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {
    _OK(HttpStatus.OK, "COMMON200", "성공입니다."),

    //Member
    MEMBER_DELETE_SUCCESS(HttpStatus.OK, "MEMBER2001", "회원 탈퇴 성공입니다."),
    MEMBER_NOTIFICATION_UPDATE(HttpStatus.OK, "MEMBER2002", "회원 알림 변경 성공입니다."),
    MEMBER_ACCOUNT_UPDATE(HttpStatus.OK,"MEMBER2003", "회원 계좌 변경 성공입니다."),
    MEMBER_ACCOUNT_DELETE(HttpStatus.OK, "MEMBER2004", "회원 계좌 삭제 성공입니다."),
    MEMBER_ADDRESS_DELETE(HttpStatus.OK, "MEMBER2005", "회원 배송지 삭제 성공입니다."),
    MEMBER_CUSTOM_INFO_UPDATE(HttpStatus.OK, "MEMBER2006", "회원 맞춤 정보 변경 성공입니다."),
    MEMBER_DECLARE_SUCCESS(HttpStatus.OK, "MEMBER2007", "신고 등록 성공했습니다."),
    MEMBER_DECLARE_DELETE(HttpStatus.OK, "MEMBER2008", "신고 삭제 성공했습니다."),
    MEMBER_CONTACT_SUCCESS(HttpStatus.OK, "MEMBER2009", "연락가능 시간 변경 성공입니다."),

    //POST
    POST_FOLLOW_SUCCESS(HttpStatus.OK, "POST2001", "팔로우 성공입니다."),
    POST_DELETE_FOLLOW_SUCCESS(HttpStatus.OK, "POST2002", "팔로우 취소 성공입니다."),
    POST_UPLOAD_SUCCESS(HttpStatus.OK, "POST2003", "포스트 업로드 성공입니다."),
    POST_UPDATE_SUCCESS(HttpStatus.OK, "POST2004", "포스트 업데이트 성공입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}

