package com.umc.TheGoods.apiPayload.exception.handler;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.GeneralException;

public class MemberHandler extends GeneralException {

    public MemberHandler(ErrorStatus errorCode) {
        super(errorCode);
    }
}
