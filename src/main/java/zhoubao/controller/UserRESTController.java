package zhoubao.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import zhoubao.data.ZhouUserRepository;
import zhoubao.domain.ZhouUser;

@RestController // 为该控制器的所有处理方法应用消息转换功能
@RequestMapping(value = "/user")
public class UserRESTController {
	@Autowired
	private ZhouUserRepository zhouUserRepository;

	// 处理"/restZhoubao"的GET请求 没有参数 获取所有周报user表种信息（除密码）
	@RequestMapping(value = "/yanzheng", method = RequestMethod.GET)
	public ZhouUser zhouUsers(Authentication authentication,HttpServletRequest req) {
		ZhouUser zhouUser=new ZhouUser();
		zhouUser=zhouUserRepository.findByUsername(authentication.getName());
		zhouUser.setLoginTime(new Date());
		zhouUser.setIp(req.getRemoteAddr());
		zhouUserRepository.save(zhouUser);
		return zhouUser;
	}

	@RequestMapping(value = "/{Xingshi}", method = RequestMethod.GET)
	public List<ZhouUser> findByXingshi(@PathVariable("Xingshi") String Xingshi) {
		return zhouUserRepository.findNameLike(Xingshi);
	}

	// 获取所有周报user表种信息（除密码）
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	public List<ZhouUser> zhouUsers() {
		return zhouUserRepository.findAll();
	}

	/*
	 * // 针对Rest的错误处理
	 * 
	 * @ExceptionHandler(EmptyResultDataAccessException.class) public
	 * ResponseEntity<MyError> spitterNotFound(EmptyResultDataAccessException e) {
	 * 
	 * // String spitterUserName=e.getSpitterUserName(); MyError error = new
	 * MyError(4, "Spitter not found"); return new ResponseEntity<MyError>(error,
	 * HttpStatus.NOT_FOUND); }
	 */

}
