package com.miracle.companyservice.util.filter;


import org.springframework.core.env.Environment;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/v1/*")
public class TokenCheckFilter implements Filter {

    private final String privateKey;

    public TokenCheckFilter(Environment environment) {
        this.privateKey = environment.getProperty("miracle.privateKey");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String id = httpServletRequest.getHeader("Session-Id") + privateKey;
        int miracle = id.hashCode();
        int token = httpServletRequest.getIntHeader("Miracle");

        if (miracle != token) {
            RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/errors/token");
            requestDispatcher.forward(request,response);
            return;
        }
        chain.doFilter(request, response);
        }
}
