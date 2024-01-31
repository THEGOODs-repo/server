package com.umc.TheGoods.apiPayload.exception.handler;

import com.umc.TheGoods.apiPayload.code.BaseErrorCode;
import com.umc.TheGoods.apiPayload.exception.GeneralException;

public class TagHandler extends GeneralException {
    public TagHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
