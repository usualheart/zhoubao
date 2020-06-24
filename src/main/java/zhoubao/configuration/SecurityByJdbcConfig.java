package zhoubao.configuration;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//这是第二种 基于数据库表进行认证
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import zhoubao.bean.RespBean;

@Configuration
@EnableWebSecurity
public class SecurityByJdbcConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	//配置为通过jdbc的方式 设置dataSource
	@Autowired
	DataSource dataSource;
	@SuppressWarnings("deprecation")
	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception{
		auth.userDetailsService(myUserDetailsService)
			.passwordEncoder(NoOpPasswordEncoder.getInstance());
//		.jdbcAuthentication()
//				.dataSource(dataSource)
//				.usersByUsernameQuery("select userName as username,userPwd as password,true"
//						+ " from user where userName=?")
//				.authoritiesByUsernameQuery("select userName as username,'ROLE_USER'"
//						+ " from user where userName=?")
//				.groupAuthoritiesByUsername("select '0','groupX','ROLE_USER' from user where userName=?");
					
	}
//	//兼容Spring Security4.*配置不需要加密的密码存储方式
//	@SuppressWarnings("deprecation")
//	@Bean
//	public static NoOpPasswordEncoder passwordEncoder() {
//	    return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//	}
	 @Override
	    public void configure(WebSecurity web) throws Exception {
	        web.ignoring().antMatchers("/index.html", "/static/**", "/login_p", "/favicon.ico","/");
	    }
	
	
	@Override
	protected void configure(HttpSecurity http)throws Exception{
		http
		.formLogin().loginProcessingUrl("/login")
		
		//
		//.loginPage("/_login").loginProcessingUrl("/login")//可以这样写来自定义登录页 即通过"_login"控制器
        .usernameParameter("username").passwordParameter("password")
		.successHandler(myAuthenticationSuccessHandler)
		.failureHandler(new AuthenticationFailureHandler() {

			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				// TODO 自动生成的方法存根
				response.setContentType("application/json;charset=utf-8");
				ObjectMapper om = new ObjectMapper();
				RespBean respBean=RespBean.error("登录失败！",exception.getMessage());//
				
				response.getWriter().write(om.writeValueAsString(respBean));
				//response.getWriter().write("登录失败！");
				//System.out.println("测试一下登录失败后的处理");
				//System.out.println(request.toString());
			}})
		
	.and()
	//所有接口都启用安全验证
		.authorizeRequests()
		.anyRequest()
		.authenticated()
		
	.and()
//		.authorizeRequests()
//			.antMatchers("/homepage").authenticated()
//			.antMatchers("/restZhoubao").authenticated()
//			.antMatchers("/user/yanzheng").authenticated()
//			.antMatchers(HttpMethod.POST,"/spitter/register").authenticated()
//			.anyRequest().permitAll()
//		.and()
		.httpBasic()
		
	.and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessHandler(new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                resp.setContentType("application/json;charset=utf-8");
                RespBean respBean = RespBean.ok("注销成功!");
                ObjectMapper om = new ObjectMapper();
                PrintWriter out = resp.getWriter();
                out.write(om.writeValueAsString(respBean));
                out.flush();
                out.close();
            }
        })
		.and()
			.csrf()
			.disable()
			.requiresChannel()
				.antMatchers("/restZhoubao")
				.requiresInsecure()
		.and()
			.headers()
				.frameOptions().sameOrigin();
		
		
	}
	
}
