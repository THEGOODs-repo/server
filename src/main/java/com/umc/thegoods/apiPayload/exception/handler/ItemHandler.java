package com.umc.thegoods.apiPayload.exception.handler;

import com.umc.thegoods.apiPayload.code.BaseErrorCode;
import com.umc.thegoods.apiPayload.exception.GeneralException;

public class ItemHandler extends GeneralException {
    public ItemHandler(BaseErrorCode code) {
        super(code);
    }
}
