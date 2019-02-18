package org.nju.mycourses.web.security.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.nju.mycourses.data.entity.State;
import org.nju.mycourses.data.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Set;

import static org.nju.mycourses.web.security.WebSecurityConstants.*;

@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomUserDetails implements UserDetails {
	private Integer id;
	private String username;
	private String password;
	private Set<GrantedAuthority> authorities;

	CustomUserDetails(User user) {
		BeanUtils.copyProperties(user, this);



		switch (user.getRole()) {
			case STUDENT:
				if(user.getState().equals(State.UNCERTIFIED)){
					this.authorities = Collections.singleton(UNCERTIFIED_STUDENT_AUTHORITY);
				}else{
					this.authorities = Collections.singleton(STUDENT_AUTHORITY);
				}

				break;
			case TEACHER:
				if(user.getState().equals(State.UNCERTIFIED)){
					this.authorities = Collections.singleton(UNCERTIFIED_TEACHER_AUTHORITY);
				}else{
					this.authorities = Collections.singleton(TEACHER_AUTHORITY);
				}
				break;
			case ADMIN:
				this.authorities = Collections.singleton(ADMIN_AUTHORITY);
				break;
			default:
				throw new UsernameNotFoundException(username);
		}
		if(user.getState().equals(State.CANCELLED)){
			this.authorities = Collections.singleton(CANCELLED_AUTHORITY);
		}
	}

	CustomUserDetails(String username, String password) {
		this.id = 0;
		this.username = username;
		this.password = DigestUtils.sha256Hex(password);
		this.authorities = Collections.singleton(ADMIN_AUTHORITY);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean isStudent() {
		return authorities.contains(STUDENT_AUTHORITY);
	}

	public boolean isTeacher() {
		return authorities.contains(TEACHER_AUTHORITY);
	}

	public boolean isAdmin() {
		return authorities.contains(ADMIN_AUTHORITY);
	}
}
