
package com.milegado.controllers;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	//devuelve la vista con el formulario para crear una nueva conmemoracion, y con el model del perfil logueado
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
	//guarda la conmemoracion creada
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
	//muestra la conmemoracion a traves del model con todos los atributos.
	@GetMapping("/mostrar")
	public String mostrar(HttpSession session, Model model, Authentication usuario) throws MyException, Exception {
		model.addAttribute("memoriales", conmemoracionService.listar(usuario));
		if (usuario != null) {
			model.addAttribute("usuario", usuarioService.obtenernombre(usuario));
			model.addAttribute("perfil",
					perfilService.buscarXId(usuarioService.buscarXUserName(usuario.getName()).getId()));
		}
		return "conmemoracion-mostrar.html";
	}
	// devuelve solo los memoriales del usuario logueado
	@GetMapping("/misMemoriales")
	public String misMemoriales(HttpSession session, Model model, Authentication usuario)
			throws MyException, Exception {
		model.addAttribute("memoriales", conmemoracionService.listarXId(usuario));
		if (usuario != null) {
			model.addAttribute("usuario", usuarioService.obtenernombre(usuario));
			model.addAttribute("perfil",
					perfilService.buscarXId(usuarioService.buscarXUserName(usuario.getName()).getId()));
		}
		return "conmemoracion-mostrar.html";
	}
	//para agregar fotos al album de determinado memorial del cual recibimos el id
	@PostMapping("/album/{idMemorial}")
	public String fotoAlbum(Model model, Authentication usuario, MultipartFile archivo,
			@PathVariable("idMemorial") String idMemorial) {
		
		try {
			model.addAttribute("usuario", usuarioService.obtenernombre(usuario));
			model.addAttribute("perfil",
					perfilService.buscarXId(usuarioService.buscarXUserName(usuario.getName()).getId()));
			model.addAttribute("memorial", conmemoracionService.buscarXId(idMemorial));
			conmemoracionService.agregarFoto(archivo, usuario, idMemorial);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			
			e.printStackTrace();
		}
		return "memorial";

	}
	//devuelve la vista del memorial que pasamos por id 
	@GetMapping("/album/{idMemorial}")
	public String album(Authentication usuario,
			@PathVariable("idMemorial") String idMemorial, Model model) {
		try {
			model.addAttribute("usuario", usuarioService.obtenernombre(usuario));
			model.addAttribute("perfil",
					perfilService.buscarXId(usuarioService.buscarXUserName(usuario.getName()).getId()));
			model.addAttribute("memorial", conmemoracionService.buscarXId(idMemorial));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "memorial";

	}
	//
	@PostMapping("/foto/{id}")
	public String foto(Model model, RedirectAttributes redirectAttributes, Authentication usuario,@PathVariable("id") String id,
			@RequestParam MultipartFile foto) throws Exception {
		try {
			conmemoracionService.guardarFoto(id, foto);
			redirectAttributes.addFlashAttribute("success", "Perfil modificado satisfactoriamente!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			System.out.println(e.getMessage());
			return "redirect:/";
		}

		return "redirect:/";
	}
	
}
