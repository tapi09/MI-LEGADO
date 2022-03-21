package com.milegado.controllers;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.milegado.exceptions.MyException;
import com.milegado.services.ConmemoracionService;
import com.milegado.services.PerfilService;
import com.milegado.services.UsuarioService;

@Controller
@RequestMapping("/conmemoracion")
public class ConmemoracionController {

	@Autowired
	private ConmemoracionService conmemoracionService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PerfilService perfilService;

	@GetMapping("/crear")
	public String crear(Model model, Authentication usuario) throws MyException {
		try {
			model.addAttribute("perfil",
					perfilService.buscarXId(usuarioService.buscarXUserName(usuario.getName()).getId()));

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());

		}
		return "conmemoracion-form";
	}

	@PostMapping("/save")
	public String save(Authentication usuario, @RequestParam String nombre, @RequestParam String apellido,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDefuncion,
			@RequestParam(required = false) MultipartFile foto,
			@RequestParam(required = false) MultipartFile fotoPortada) throws Exception {
		conmemoracionService.crearConmemoracion(nombre, apellido, fechaNacimiento, fechaDefuncion, foto, fotoPortada,
				usuario);

		return "redirect:/conmemoracion/crear";

	}

	@GetMapping("/mostrar")
	public String mostrar(HttpSession session, Model model, Authentication usuario)
			throws MyException, Exception {
		model.addAttribute("memoriales", conmemoracionService.listar(usuario));
		if (usuario != null) {
			model.addAttribute("usuario", usuarioService.obtenernombre(usuario));
			model.addAttribute("perfil",
					perfilService.buscarXId(usuarioService.buscarXUserName(usuario.getName()).getId()));
		}
		return "conmemoracion-mostrar.html";
	}

}
