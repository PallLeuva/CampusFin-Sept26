package com.campusfin.controller;

import com.campusfin.model.SignupRequest;
import com.campusfin.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String showSignupPage(Model model) {

        model.addAttribute(
                "signupRequest",
                new SignupRequest()
        );

        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(
            @Valid
            @ModelAttribute("signupRequest")
            SignupRequest signupRequest,
            BindingResult bindingResult,
            Model model) {

        if (!signupRequest.getPassword()
                .equals(signupRequest.getConfirmPassword())) {

            bindingResult.rejectValue(
                    "confirmPassword",
                    "passwordMismatch",
                    "Passwords do not match."
            );
        }

        if (!bindingResult.hasFieldErrors("email")
                && userService.emailExists(signupRequest.getEmail())) {

            bindingResult.rejectValue(
                    "email",
                    "emailExists",
                    "An account already exists with this email."
            );
        }

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        try {

            userService.registerUser(signupRequest);

        } catch (IllegalArgumentException exception) {

            model.addAttribute(
                    "signupError",
                    exception.getMessage()
            );

            return "signup";
        }

        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String showLoginPage() {

        return "login";
    }
}