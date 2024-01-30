package com.umc.TheGoods.apiPayload.exception.handler;

import com.umc.TheGoods.apiPayload.code.BaseErrorCode;
import com.umc.TheGoods.apiPayload.exception.GeneralException;

public class OrderHandler extends GeneralException {
    public OrderHandler(BaseErrorCode code) {
        super(code);
    }
}
