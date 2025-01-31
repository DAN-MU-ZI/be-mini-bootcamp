package com.study.bootcamp.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}

	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "signup_form";
		}

		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect",
				"2개의 패스워드가 일치하지 않습니다.");
			return "signup_form";
		}

		try {
			userService.create(userCreateForm.getUsername(),
				userCreateForm.getEmail(), userCreateForm.getPassword1());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "signup_form";
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "signup_form";
		}

		return "redirect:/";
	}

	@GetMapping("/login")
	public String login() {
		return "login_form";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/change/password")
	public String changePassword(PasswordChangeForm passwordChangeForm) {
		return "password_change_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/change/password")
	public String changePassword(
		PasswordChangeForm passwordChangeForm,
		BindingResult bindingResult,
		Principal principal
	) {
		if (bindingResult.hasErrors()) {
			return "/user/change/password";
		}

		SiteUser user = userService.getUser(principal.getName());
		userService.update(user, passwordChangeForm.password1());
		return "redirect:/";
	}
}
