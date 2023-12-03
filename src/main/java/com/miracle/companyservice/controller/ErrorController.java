package com.miracle.companyservice.controller;

import com.miracle.companyservice.controller.swagger.ApiDefault;
import com.miracle.companyservice.exception.UnauthorizedTokenException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    @ApiDefault
    @GetMapping("/errors/token")
    public void errorToken() {
        throw new UnauthorizedTokenException("토큰 값이 일치하지 않습니다.");
    }
}
