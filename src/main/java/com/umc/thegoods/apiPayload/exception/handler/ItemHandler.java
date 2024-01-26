package com.umc.TheGoods.apiPayload.exception.handler;

import com.umc.TheGoods.apiPayload.code.BaseErrorCode;
import com.umc.TheGoods.apiPayload.exception.GeneralException;

public class ItemHandler extends GeneralException {
    public ItemHandler(BaseErrorCode code) {
        super(code);
    }
}
