package com.study.bootcamp.user;

import com.study.bootcamp.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public SiteUser create(String username, String email, String password) {
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
		return user;
	}

	public SiteUser getUser(String username) {
		return userRepository.findByUsername(username)
			.orElseThrow(() -> new DataNotFoundException("siteuser not found"));
	}

	public void update(SiteUser user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}
}
