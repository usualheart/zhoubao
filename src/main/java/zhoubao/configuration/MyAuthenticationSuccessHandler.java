package zhoubao.configuration;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import zhoubao.bean.RespBean;
import zhoubao.data.ZhouUserRepository;
import zhoubao.domain.ZhouUser;
/*
 * 用于编写自定义的认证成功处理
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private ZhouUserRepository zhouUserRepository;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		ZhouUser zhouUser=zhouUser=zhouUserRepository.findByUsername(authentication.getName());
		zhouUser.setLoginTime(new Date());
		zhouUser.setIp(request.getRemoteAddr());
		zhouUserRepository.save(zhouUser);
		
		response.setContentType("application/json;charset=utf-8");
		ObjectMapper om = new ObjectMapper();
		RespBean respBean=RespBean.ok("登录成功!", zhouUserRepository.findByUsername(authentication.getName()));
		
		response.getWriter().write(om.writeValueAsString(respBean));
		System.out.println("测试一下登录成功后的处理");
		
	}
}
