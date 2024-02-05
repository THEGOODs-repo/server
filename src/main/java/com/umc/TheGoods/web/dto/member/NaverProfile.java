package com.umc.TheGoods.web.dto.member;

import lombok.Data;

@Data
public class NaverProfile {

    public String resultcode;
    public String message;
    public Response response;

    @Data
    public class Response {

        public String id;
        public String email;
        public String mobile;
        public String mobile_e164;

    }

}
