package com.miracle.companyservice.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/v1/*")
public class tokenCheckFilter implements Filter {

    private final String privateKey = "TkwkdsladkdlrhdnjfrmqdhodlfjgrpaksgdlwnjTdjdy";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String id = httpServletRequest.getHeader("sessionId") + privateKey;
        int miracle = id.hashCode();
        int token = httpServletRequest.getIntHeader("miracle");

        if (miracle != token) {
            RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/errors/token");
            requestDispatcher.forward(request,response);
            return;
        }
        chain.doFilter(request, response);
        }
}
