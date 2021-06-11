package com.blas.fish.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blas.fish.dao.AdminDAO;
import com.blas.fish.model.Admin;

@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private AdminDAO adminDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Admin admin = adminDAO.findAdmin(username);
		if(admin==null) {
			System.out.println("null");
		}else {
			System.out.println("not null");
		}
		System.out.println("sdfs: " + admin.toString());
		String role = admin.getRole();
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
		grantList.add(authority);
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		UserDetails userDetails = (UserDetails) new User(
				admin.getUsername(), admin.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, grantList);
		return userDetails;
	}
}
