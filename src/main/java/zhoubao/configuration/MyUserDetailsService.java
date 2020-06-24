package zhoubao.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import zhoubao.data.ZhouUserRepository;
import zhoubao.domain.ZhouUser;

@Component
public class MyUserDetailsService implements UserDetailsService{
	@Autowired
	private ZhouUserRepository zhouUserRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO 自动生成的方法存根
		
		return zhouUserRepository.findByUsername(username);
	}

}
