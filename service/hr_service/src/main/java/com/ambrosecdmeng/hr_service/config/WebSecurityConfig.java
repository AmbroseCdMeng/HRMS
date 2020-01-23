package com.ambrosecdmeng.hr_service.config;

import com.ambrosecdmeng.hr_service.model.RespBean;
import com.ambrosecdmeng.hr_service.service.HrService;
import com.ambrosecdmeng.hr_service.utils.HrUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 配置 Spring Security
 * <p>
 * 1、 首先，通过 @EnableGlobalMethodSecurity 注解开启基于注解的安全配置，启用 @PreAuthorize 和 @PostAuthorize 注解
 * 2、 在配置类中注入之前创建的 4 个 Bean， 在 AuthenticationManagerBuilder 中配置 userDetailService 和 passwordEncoder
 * 3、 在 WebSecurity 中配置需要忽略的路径
 * 4、 在 HttpSecurity 中配置拦截规则、表单登录、登录成功或失败的响应等
 * 5、 最后通过 accessDeniedHandler 配置异常处理
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    HrService hrService;
    @Autowired(required = false)
    CustomMetadataSource metadataSource;
    @Autowired(required = false)
    UrlAccessDecisionManager urlAccessDecisionManager;
    @Autowired(required = false)
    AuthenticationAccessDeniedHandler deniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html", "/static/**", "/login_p");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {

            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                o.setSecurityMetadataSource(metadataSource);
                o.setAccessDecisionManager(urlAccessDecisionManager);
                return o;
            }
        }).and().formLogin().loginPage("/login_p").loginProcessingUrl("/login").usernameParameter(
                "username").passwordParameter("password").failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse, AuthenticationException e)
                    throws IOException, ServletException {
                httpServletResponse.setContentType("application/json;charset=utf-8");
                RespBean respBean = null;
                if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException)
                    respBean = RespBean.error("用户名或密码错误");
                else if (e instanceof LockedException)
                    respBean = RespBean.error("账户被锁定，请联系系统管理员");
                else if (e instanceof CredentialsExpiredException)
                    respBean = RespBean.error("密码过期，请联系管理员");
                else if (e instanceof AccountExpiredException)
                    respBean = RespBean.error("账户过期，请联系管理员");
                else if (e instanceof DisabledException)
                    respBean = RespBean.error("账户被禁用, 请联系管理员");
                else
                    respBean = RespBean.error("登录失败");
                httpServletResponse.setStatus(401);
                ObjectMapper objectMapper = new ObjectMapper();
                PrintWriter printWriter = httpServletResponse.getWriter();
                printWriter.write(objectMapper.writeValueAsString(respBean));
                printWriter.flush();
                printWriter.close();
            }
        }).successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse, Authentication authentication)
                    throws IOException, ServletException {
                httpServletResponse.setContentType("application/json;charset=utf-8");
                RespBean respBean = RespBean.ok("登录成功", HrUtils.getCurrentHr());
                ObjectMapper objectMapper = new ObjectMapper();
                PrintWriter printWriter = httpServletResponse.getWriter();
                printWriter.write(objectMapper.writeValueAsString(respBean));
                printWriter.flush();
                printWriter.close();
            }
        }).permitAll().and().logout().permitAll().and().csrf().disable().exceptionHandling().accessDeniedHandler(
                deniedHandler);
    }
}