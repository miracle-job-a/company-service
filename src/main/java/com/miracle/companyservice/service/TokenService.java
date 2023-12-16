package com.miracle.companyservice.service;

public interface TokenService {

    /**
     * Validate token.
     * 유효한 토큰이란, 토큰이 인증된 서버에서 발급 받았으며 기간이 만료되지 않았음을 의미한다.
     *
     * @param token Token to validate
     * @return Returns true if {@code token} is valid, false otherwise.
     * @author chocola
     */
    boolean validateToken(String token);
}
