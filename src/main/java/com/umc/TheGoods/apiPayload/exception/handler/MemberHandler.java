package com.umc.TheGoods.apiPayload.exception.handler;

import com.umc.TheGoods.apiPayload.code.BaseErrorCode;
import com.umc.TheGoods.apiPayload.exception.GeneralException;

public class MemberHandler extends GeneralException {
    public MemberHandler(BaseErrorCode errorCode){
        super(errorCode);
    }
}
