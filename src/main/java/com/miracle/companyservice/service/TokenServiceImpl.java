package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.request.AccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean validateToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        AccessToken accessToken = new AccessToken(token);
        HttpEntity<AccessToken> requestEntity = new HttpEntity<>(accessToken, headers);

        ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity(
                "http://13.125.220.223:60200/v1/jwt/validate-gateway",
                requestEntity,
                Boolean.class
        );

        boolean success = responseEntity.getStatusCode().is2xxSuccessful();
        boolean result = Boolean.TRUE.equals(responseEntity.getBody());
        return success && result;
    }
}
