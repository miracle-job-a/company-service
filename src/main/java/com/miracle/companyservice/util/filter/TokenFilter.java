package com.miracle.companyservice.util.filter;

import com.miracle.companyservice.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@WebFilter("/v1/*")
public class TokenFilter extends HttpFilter {

    private static final String HEADER_AUTHORIZATION = "Authorization";

    private final TokenService tokenService;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String bearerToken = getBearerToken(request);

        if (StringUtils.hasText(bearerToken) && tokenService.validateToken(bearerToken)) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect("/error/invalid-token");
        }
    }

    private String getBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_AUTHORIZATION);

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
