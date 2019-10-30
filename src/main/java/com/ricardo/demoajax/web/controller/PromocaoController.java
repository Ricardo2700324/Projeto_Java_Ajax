package com.ricardo.demoajax.web.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ricardo.demoajax.domain.Categoria;
import com.ricardo.demoajax.domain.Promocao;
import com.ricardo.demoajax.dto.PromocaoDTO;
import com.ricardo.demoajax.repository.CategoriaRepository;
import com.ricardo.demoajax.repository.PromocaoRepository;
import com.ricardo.demoajax.service.PromocaoDataTablesService;

@Controller
@RequestMapping("/promocao")
public class PromocaoController {

	private static Logger Log = LoggerFactory.getLogger(PromocaoController.class);

	@Autowired
	private PromocaoRepository promocaoRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;

	// ================================DATATABLES==================================================
	@GetMapping("/tabela")
	public String showTabela() {
		return "promo-datatables";
	}

	@GetMapping("/datatables/server")
	public ResponseEntity<?> datatables(HttpServletRequest request) {
		Map<String, Object> data = new PromocaoDataTablesService().execute(promocaoRepository, request);
		return ResponseEntity.ok(data);
	}

	// excluir promocao
	@GetMapping("/delete/{id}")
	public ResponseEntity<?> excluirPromocao(@PathVariable("id") Long id) {
		promocaoRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

	// editar promocao dataTable
	@GetMapping("/edit/{id}")
	public ResponseEntity<?> preEditarPromocao(@PathVariable("id") Long id) {
		Promocao promo = promocaoRepository.findById(id).get();
		return ResponseEntity.ok(promo);
	}

	// Metodo UPDATE dataTable
	@PostMapping("/edit")
	public ResponseEntity<?> editarPromocao(@Valid PromocaoDTO dto, BindingResult result) {

		if (result.hasErrors()) {

			Map<String, String> errors = new HashMap<>();
			for (FieldError error : result.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.unprocessableEntity().body(errors);
		}
		
		Promocao promo = promocaoRepository.findById(dto.getId()).get();
		promo.setCategoria(dto.getCategoria());
		promo.setDescricao(dto.getDescricao());
		promo.setLinkImagem(dto.getLinkImagem());
		promo.setPreco(dto.getPreco());
		promo.setTitulo(dto.getTitulo());
		
		promocaoRepository.save(promo);
		
		return ResponseEntity.ok().build();
	}

	// ================================AUTOCOMPLETE==================================================

	@GetMapping("/site")
	public ResponseEntity<?> autocolpleteByTermo(@RequestParam("termo") String termo) {
		List<String> sites = promocaoRepository.findSiteByTermo(termo);
		return ResponseEntity.ok(sites);
	}

	@GetMapping("/site/list")
	public String listarPorSite(@RequestParam("site") String site, ModelMap model) {
		Sort sort = new Sort(Sort.Direction.DESC, "dtCadastro");
		PageRequest pageRequest = PageRequest.of(0, 8, sort);
		model.addAttribute("promocoes", promocaoRepository.findBySite(site, pageRequest));
		return "promo-card";
	}

	// ================================ADD
	// LIKES==================================================
	@PostMapping("/like/{id}")
	public ResponseEntity<?> adicionarLikes(@PathVariable("id") Long id) {
		promocaoRepository.updateSomarLikes(id);
		int likes = promocaoRepository.findLikesById(id);
		return ResponseEntity.ok(likes);
	}

	// ================================LISTA
	// OFERTAS==================================================
	@GetMapping("/list")
	public String listarOfertas(ModelMap model) {
		Sort sort = new Sort(Sort.Direction.DESC, "dtCadastro");
		PageRequest pageRequest = PageRequest.of(0, 8, sort);
		model.addAttribute("promocoes", promocaoRepository.findAll(pageRequest));

		return "promo-list";
	}

	@GetMapping("/list/ajax")
	public String listarCards(@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "site", defaultValue = "") String site, ModelMap model) {

		Sort sort = new Sort(Sort.Direction.DESC, "dtCadastro");
		PageRequest pageRequest = PageRequest.of(page, 8, sort);

		if (site.isEmpty()) {
			model.addAttribute("promocoes", promocaoRepository.findAll(pageRequest));
		} else {
			model.addAttribute("promocoes", promocaoRepository.findBySite(site, pageRequest));
		}
		return "promo-card";
	}

	// ================================ADD
	// OFERTAS==================================================

	@PostMapping("/save")
	public ResponseEntity<?> salvarPromocao(@Valid Promocao promocao, BindingResult result) {

		if (result.hasErrors()) {

			Map<String, String> errors = new HashMap<>();
			for (FieldError error : result.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}

			return ResponseEntity.unprocessableEntity().body(errors);
		}

		Log.info("Promocao {}", promocao.toString());
		promocao.setDtCadastro(LocalDateTime.now());
		promocaoRepository.save(promocao);
		return ResponseEntity.ok().build();
	}

	@ModelAttribute("categorias")
	public List<Categoria> getCategorias() {

		return categoriaRepository.findAll();
	}

	@GetMapping("/add")
	public String abrirCadastro() {

		return "promo-add";
	}
}
