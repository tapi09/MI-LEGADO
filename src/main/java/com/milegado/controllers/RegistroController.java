package com.milegado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.milegado.services.PerfilService;



@Controller
@RequestMapping("/registro")
public class RegistroController {


	
	@Autowired
	private PerfilService perfilService;

	@GetMapping("")
	public String registro() {
		return "inicio-registro";
	}
	@PostMapping("")
	public String Save(Model model,RedirectAttributes redirectAttributes,@RequestParam String nombre, @RequestParam String apellido, @RequestParam String username, @RequestParam String password, @RequestParam String password1) {
		System.out.println(nombre+" "+apellido +" "+username+" "+password+" "+password1 );
		try {
			System.out.println();
			perfilService.save(nombre, apellido, username,password,password1);
			redirectAttributes.addFlashAttribute("success", "Usuario registrado satisfactoriamente!");
			
			return "redirect:/";
			}catch(Exception e) {
				model.addAttribute("error", e.getMessage());
				model.addAttribute("username", username);
				return "inicio-registro";
			}
	}
}