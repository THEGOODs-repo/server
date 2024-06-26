package com.umc.TheGoods.apiPayload.exception.handler;

import com.umc.TheGoods.apiPayload.code.BaseErrorCode;
import com.umc.TheGoods.apiPayload.exception.GeneralException;

public class ReviewHandler extends GeneralException {
    public ReviewHandler(BaseErrorCode code) {
        super(code);
    }
}
