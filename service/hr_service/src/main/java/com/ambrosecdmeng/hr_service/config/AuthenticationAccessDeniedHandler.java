package com.ambrosecdmeng.hr_service.config;

import com.ambrosecdmeng.hr_service.model.RespBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpServletResponse.setContentType("application/json; charset=UTF-8");
        PrintWriter out = httpServletResponse.getWriter();
        RespBean error = RespBean.error("not enough permission, please contact the system administrator");
        out.write(new ObjectMapper().writeValueAsString(error));
        out.flush();
        out.close();
    }
}
