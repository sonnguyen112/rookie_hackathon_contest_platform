package com.group10.contestPlatform.controllers;


import com.group10.contestPlatform.dtos.ResponMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;



@RequestMapping("api")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TestController {

	
	@GetMapping(value = "/test")
	public ResponseEntity<?> logoutPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) {



		return new ResponseEntity<>(new ResponMessage("Hello"), HttpStatus.OK);
	}
	


}
