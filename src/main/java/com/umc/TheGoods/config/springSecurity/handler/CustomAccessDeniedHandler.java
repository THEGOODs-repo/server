package com.umc.TheGoods.config.springSecurity.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.TheGoods.apiPayload.ApiResponse;
import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {


        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        String errorResponseJson = objectMapper.writeValueAsString(ApiResponse.onFailure(ErrorStatus.JWT_BAD_REQUEST.getCode(), ErrorStatus.JWT_BAD_REQUEST.getMessage(), ""));
        response.getOutputStream().write(errorResponseJson.getBytes("UTF-8"));
    }
}
