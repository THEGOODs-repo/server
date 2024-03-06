package com.umc.TheGoods.apiPayload.exception.handler;

import com.umc.TheGoods.apiPayload.code.BaseErrorCode;
import com.umc.TheGoods.apiPayload.exception.GeneralException;

public class JwtHandler extends GeneralException {
    public JwtHandler(BaseErrorCode code) {
        super(code);
    }
}
