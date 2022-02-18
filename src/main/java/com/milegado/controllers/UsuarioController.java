package com.milegado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.milegado.services.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	
	@Autowired
	private UsuarioService usuarioService;
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/listar")
	public String listar(Model model, @RequestParam(required =false) String dato) {
		
			model.addAttribute("usuarios", usuarioService.listarTodos());
		
		return "#";
	}
}
