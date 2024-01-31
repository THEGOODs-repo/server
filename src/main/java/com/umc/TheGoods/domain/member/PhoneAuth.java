package com.umc.TheGoods.domain.member;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneAuth {
    //만료시간 5분
    private static final Long MAX_EXPIRE_TIME = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    private String code;
    private Boolean expired;
    private LocalDateTime expireDate = LocalDateTime.now().plusMinutes(MAX_EXPIRE_TIME);

    public void useToken() {
        this.expired = true;
    }
}
