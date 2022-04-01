package com.milegado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.milegado.entities.Perfil;
import com.milegado.entities.Usuario;
import com.milegado.services.PerfilService;
import com.milegado.services.UsuarioService;

@Controller
@RequestMapping("/perfil")
public class PerfilController {
	@Autowired
	private PerfilService perfilService;

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/modificar")
	public String crear(Model model, Authentication usuario) throws Exception {
		try {

			Usuario usuario1 = usuarioService.buscarXUserName(usuario.getName());

			model.addAttribute("usuario", usuario1);

			Perfil perfil = perfilService.buscarXId(usuario1.getId());
			model.addAttribute("perfil", perfil);

			return "perfil-usuario";
		} catch (Exception e) {

			model.addAttribute("error ", e.getMessage());
			return "error";

		}

	}

	@PostMapping("/modificar")
	public String modificar(Model model, RedirectAttributes redirectAttributes, Authentication usuario,
			@RequestParam String nombre, @RequestParam String apellido,
			@RequestParam(required = false) MultipartFile foto,
			@RequestParam(required = false) MultipartFile fotoPortada) throws Exception {
		try {
			perfilService.modificar(usuarioService.buscarXUserName(usuario.getName()).getId(), nombre, apellido, foto,
					fotoPortada);

			redirectAttributes.addFlashAttribute("success", "Perfil modificado satisfactoriamente!");

			model.addAttribute("usuario", usuarioService.buscarXUserName(usuario.getName()));

			model.addAttribute("perfil",
					perfilService.buscarXId(usuarioService.buscarXUserName(usuario.getName()).getId()));

			return "redirect:/perfil/crear";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "perfil-form";
		}
	}
	//guarda foto de perfil del usuario
	@PostMapping("/foto")
	public String foto(Model model, RedirectAttributes redirectAttributes, Authentication usuario,
			@RequestParam MultipartFile foto) throws Exception {
		try {
			perfilService.guardarFoto(usuarioService.buscarXUserName(usuario.getName()).getId(), foto);
			redirectAttributes.addFlashAttribute("success", "Perfil modificado satisfactoriamente!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			System.out.println(e.getMessage());
			return "redirect:/";
		}

		return "redirect:/";
	}
	//guarda foto de portada del usuario
	@PostMapping("/fotoPortada")
	public String fotoPortada(Authentication usuario, @RequestParam MultipartFile fotoPortada) throws Exception {
		perfilService.guardarFotoPortada(usuarioService.buscarXUserName(usuario.getName()).getId(), fotoPortada);

		return "redirect:/";
	}

}
