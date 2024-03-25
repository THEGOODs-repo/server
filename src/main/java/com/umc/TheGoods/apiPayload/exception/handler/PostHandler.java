package com.umc.TheGoods.apiPayload.exception.handler;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.GeneralException;

public class PostHandler extends GeneralException {

    public PostHandler(ErrorStatus errorCode){ super(errorCode);}
}
