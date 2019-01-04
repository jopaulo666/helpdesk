 package com.joaopaulo.helpdesk.api.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.joaopaulo.helpdesk.api.entity.User;
import com.joaopaulo.helpdesk.api.security.jwt.JwtUserFactory;
import com.joaopaulo.helpdesk.api.service.UserService;

@Service
 public class JwtUserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userService.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException(String.format("Usuário não encontrado '%s'.", email));
		} else {
			return JwtUserFactory.create(user);
		}
	}

}
