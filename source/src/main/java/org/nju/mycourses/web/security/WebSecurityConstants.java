package org.nju.mycourses.web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public abstract class WebSecurityConstants {
	private static final String ROLE_PREFIX = "ROLE_";

	public static final String STUDENT_ROLE = "STUDENT";
	public static final String TEACHER_ROLE = "TEACHER";
	public static final String ADMIN_ROLE = "ADMIN";

	public static final String UNCERTIFIED_STUDENT_ROLE = "UNCERTIFIED_STUDENT";
	public static final String UNCERTIFIED_TEACHER_ROLE = "UNCERTIFIED_TEACHER";
	public static final String CANCELLED_ROLE = "CANCELLED";

	public static final GrantedAuthority STUDENT_AUTHORITY = new SimpleGrantedAuthority(ROLE_PREFIX + STUDENT_ROLE);
	public static final GrantedAuthority TEACHER_AUTHORITY = new SimpleGrantedAuthority(ROLE_PREFIX + TEACHER_ROLE);
	public static final GrantedAuthority ADMIN_AUTHORITY = new SimpleGrantedAuthority(ROLE_PREFIX + ADMIN_ROLE);

	public static final GrantedAuthority UNCERTIFIED_STUDENT_AUTHORITY = new SimpleGrantedAuthority(ROLE_PREFIX + UNCERTIFIED_STUDENT_ROLE);
	public static final GrantedAuthority UNCERTIFIED_TEACHER_AUTHORITY = new SimpleGrantedAuthority(ROLE_PREFIX + UNCERTIFIED_TEACHER_ROLE);
	public static final GrantedAuthority CANCELLED_AUTHORITY = new SimpleGrantedAuthority(ROLE_PREFIX + CANCELLED_ROLE);
}
