package com.miracle.companyservice.util.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GetMethodInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals(HttpMethod.GET.name())) {
            return true;
        }
        if (request.getHeader("Company-Id") == null || request.getHeader("Company-Email") == null || request.getHeader("Company-Bno") == null) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/errors/token");
            requestDispatcher.forward(request, response);
            return false;
        }

        return true;
    }
}
