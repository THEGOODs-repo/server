package com.umc.TheGoods.apiPayload.exception.handler;

import com.umc.TheGoods.apiPayload.code.BaseErrorCode;
import com.umc.TheGoods.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {
    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
