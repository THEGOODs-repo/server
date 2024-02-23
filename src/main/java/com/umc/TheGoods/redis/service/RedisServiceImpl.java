package com.umc.TheGoods.redis.service;

import com.umc.TheGoods.apiPayload.code.status.ErrorStatus;
import com.umc.TheGoods.apiPayload.exception.handler.JwtHandler;
import com.umc.TheGoods.apiPayload.exception.handler.MemberHandler;
import com.umc.TheGoods.config.springSecurity.provider.TokenProvider;
import com.umc.TheGoods.domain.member.Member;
import com.umc.TheGoods.redis.domain.LoginStatus;
import com.umc.TheGoods.redis.domain.RefreshToken;
import com.umc.TheGoods.redis.repository.LoginStatusRepository;
import com.umc.TheGoods.redis.repository.RefreshTokenRepository;
import com.umc.TheGoods.repository.member.MemberRepository;
import com.umc.TheGoods.web.dto.member.MemberRequestDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RedisServiceImpl implements RedisService {

    private final MemberRepository memberRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final LoginStatusRepository loginStatusRepository;

    private final TokenProvider tokenProvider;

    Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Override
    @Transactional
    public RefreshToken generateRefreshToken(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        String token = tokenProvider.createRefreshToken(Arrays.asList(new SimpleGrantedAuthority("USER")));
        Long memberId = member.getId();

        LocalDateTime currentTime = LocalDateTime.now();

        LocalDateTime expireTime = currentTime.plusMonths(1);

        RefreshToken refreshToken = RefreshToken.builder()
                .memberId(memberId)
                .token(token)
                .expireTime(expireTime).build();
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    @Transactional
    public RefreshToken reGenerateRefreshToken(MemberRequestDTO.RefreshTokenDTO request) {
        if (request.getRefreshToken() == null)
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        RefreshToken findRefreshToken = refreshTokenRepository.findById(request.getRefreshToken()).orElseThrow(() -> new JwtHandler(ErrorStatus.JWT_REFRESH_TOKEN_EXPIRED));
        System.out.println(findRefreshToken.getToken());
        LocalDateTime expireTime = findRefreshToken.getExpireTime();
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime expireDeadLine = current.plusSeconds(20);

        Member member = memberRepository.findById(findRefreshToken.getMemberId()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if (current.isAfter(expireTime)) {
            logger.error("이미 만료된 리프레시 토큰 발견");
            throw new JwtHandler(ErrorStatus.JWT_REFRESH_TOKEN_EXPIRED);
        }

        if (expireTime.isAfter(expireDeadLine)) {
            logger.info("기존 리프레시 토큰 발급");
            return findRefreshToken;
        } else {
            logger.info("accessToken보다 먼저 만료될 예정인 리프레시 토큰 발견");
            deleteRefreshToken(request.getRefreshToken());
            return generateRefreshToken(member.getEmail());
        }
    }

    @Override
    @Transactional
    public String saveLoginStatus(Long memberId, String accessToken) {
        loginStatusRepository.save(
                LoginStatus.builder()
                        .accessToken(accessToken)
                        .memberId(memberId)
                        .build()
        );

        return accessToken;
    }

    @Override
    @Transactional
    public void resolveLogout(String accessToken) {
        LoginStatus loginStatus = loginStatusRepository.findById(accessToken).get();
        loginStatusRepository.delete(loginStatus);
    }

    @Override
    public void deleteRefreshToken(String refreshToken) {
        Optional<RefreshToken> target = refreshTokenRepository.findById(refreshToken);
        if (target.isPresent())
            refreshTokenRepository.delete(target.get());
    }

    @Override
    public Boolean validateLoginToken(String accessToken) {
        Long aLong = tokenProvider.validateAndReturnSubject(accessToken);

        return loginStatusRepository.findById(accessToken).isPresent();
    }
}
