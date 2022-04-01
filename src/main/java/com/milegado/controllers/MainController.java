package com.milegado.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.milegado.services.ConmemoracionService;
import com.milegado.services.PerfilService;
import com.milegado.services.UsuarioService;

@Controller
@RequestMapping("/")
public class MainController {
	@Autowired
	private UsuarioService usuarioService;
	@Autowired 
	private PerfilService perfilService;
	@Autowired 
	private ConmemoracionService conmemoracionService;

	
	@GetMapping("")
	public String index(HttpSession session, Authentication usuario, Model model)throws Exception {
		//si el usuario es distinto de null se devuelven 3 model, nombre de usuario logueado, perfil y memoriales con todos sus atributos
		if(usuario != null) {
		model.addAttribute("usuario", usuarioService.obtenernombre(usuario)) ;
		model.addAttribute("perfil", perfilService.buscarXId(usuarioService.buscarXUserName(usuario.getName()).getId()));
		model.addAttribute("memoriales", conmemoracionService.listar(usuario));
		}
		
		return "index";
	}

}
