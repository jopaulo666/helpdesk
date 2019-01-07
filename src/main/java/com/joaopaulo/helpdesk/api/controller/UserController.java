package com.joaopaulo.helpdesk.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaopaulo.helpdesk.api.entity.User;
import com.joaopaulo.helpdesk.api.response.Response;
import com.joaopaulo.helpdesk.api.service.UserService;
import com.mongodb.DuplicateKeyException;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')") // somente o adm pode cadastrar usuário
	public ResponseEntity<Response<User>> create(HttpServletRequest request, @RequestBody User user,
			BindingResult result) {
		Response<User> response = new Response<User>();
		try {
			validateCreateUser(user, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));// criptografa senha
			User userPersisted = (User) userService.createOrUpdate(user);
			response.setData(userPersisted);
		} catch (DuplicateKeyException keyException) {
			response.getErrors().add("E-mail já cadastrado no sistema");
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	// valida criação d esuário
	private void validateCreateUser(User user, BindingResult result) {
		if (user.getEmail() == null) {
			result.addError(new ObjectError("User", "E-mail obrigatório"));
		}
	}

	@PutMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<User>> update(HttpServletRequest request, @RequestBody User user,
			BindingResult result) {
		Response<User> response = new Response<User>();
		try {
			validateUpdateUser(user, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));// criptografa senha
			User userPersisted = (User) userService.createOrUpdate(user);
			response.setData(userPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	private void validateUpdateUser(User user, BindingResult result) {
		if (user.getEmail() == null) {
			result.addError(new ObjectError("User", "Id obrigatório"));
		}
		if (user.getEmail() == null) {
			result.addError(new ObjectError("User", "E-mail obrigatório"));
		}
	}

	@GetMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<User>> findById(@PathVariable("id") String id) {
		Response<User> response = new Response<User>();
		User user = userService.findById(id);
		if (user == null) {
			response.getErrors().add("Cadastro não encontrado: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(user);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") String id) {
		Response<String> response = new Response<String>();
		User user = userService.findById(id);
		if (user == null) {
			response.getErrors().add("Cadastro não encontrado: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		userService.delete(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "{page}/{count}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Page<User>>> findAll(@PathVariable int page, @PathVariable int count) {
		Response<Page<User>> response = new Response<Page<User>>();
		Page<User> users = userService.findAll(page, count);
		response.setData(users);
		return ResponseEntity.ok(response);
	}    
}
