package com.jykj.interceptor;

import com.jykj.entity.LoginInfo;
import com.jykj.entity.Result;
import com.jykj.util.GsonUtils;
import com.jykj.util.JWTUtils;;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author nielong123
 * @date 2018/9/15
 */
public class WebInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(WebInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String accessToken = request.getHeader("Authorization");
//        String accessToken = "1 eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1ZDA5NzY0NTU5MzUxMzc1ZDBkNTBkOTIiLCJzdWIiOiI1ZDFhMDc1NGFmNmQyMWZiMWUzNTE3MmEiLCJpYXQiOjE1NjIwODA3NTEsInJvbCI6WyI1ZDExZTQzZWFmNmQyMWZiMWVmMmI0NzYiXX0.hIR_c2DAdQhJxZpKUPtgBJlDY72DWF9WwxwQ67ICjro";
//        String accessToken = "1 eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1ZDA5NzY0NTU5MzUxMzc1ZDBkNTBkOTIiLCJzdWIiOiI1ZDA2MGM0MDE1NTA2NmQ5NzhjNTI4ODMiLCJpYXQiOjE1NjEwMTgzNzAsInJvbCI6WyI1ZDBhZDg4NzE1NTA2NmQ5NzhlZDA1ZDciLCI1ZDBhZTU3NDE1NTA2NmQ5NzhlZDdhNjMiXX0.VJossaRu_r2ImZmzKuLyPJE3QESHrCoWLJpa_btwsqc";
        logger.info("++++token : ++++" + accessToken);
//        String uri = request.getServletPath();
        if (accessToken == null) {
            makeErrorResponse(response, "身份认证失败，请重新登录");
            return false;
        }
        String[] array = accessToken.split(" ");
        LoginInfo loginInfo = JWTUtils.getClaimsFormToken(request.getSession(), array[1], new LoginInfo());
//        LoginInfo sessionValue = (LoginInfo) request.getSession().getAttribute("loginInfo");
//        if (sessionValue!=null&&!loginInfo.getSub().equals(sessionValue.getSub())){
//
//        }
        request.getSession().setAttribute("loginInfo", loginInfo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private void makeErrorResponse(HttpServletResponse httpServletResponse, String errorMsg) throws IOException {
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("contentType,text/html;charset=utf-8");
        PrintWriter printWriter = httpServletResponse.getWriter();
        printWriter.write(GsonUtils.getJsonFromObject(Result.createFail(errorMsg)));
    }

}
