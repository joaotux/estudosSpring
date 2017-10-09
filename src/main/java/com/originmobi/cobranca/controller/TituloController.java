package com.originmobi.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.originmobi.cobranca.model.StatusTitulo;
import com.originmobi.cobranca.model.Titulo;
import com.originmobi.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulo")
public class TituloController {

	private static String CADASTRO_TITULO = "CadastroTitulo";

	@Autowired
	private Titulos titulos;

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_TITULO);
		mv.addObject(new Titulo());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {

		if (errors.hasErrors())
			return CADASTRO_TITULO;

		try {			
			titulos.save(titulo);
			attributes.addFlashAttribute("mensagem", "Título salvo com sucesso!");
			return "redirect:/titulo/novo";
		} catch (DataIntegrityViolationException e) {
			errors.rejectValue("dataVencimento", null, "Formato de data inválido!");
			return CADASTRO_TITULO;
		}
	}

	@RequestMapping
	public ModelAndView pesquisando() {
		List<Titulo> todosTitulos = titulos.findAll();

		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		mv.addObject("titulos", todosTitulos);
		return mv;
	}

	@RequestMapping("{id}")
	public ModelAndView editar(@PathVariable("id") Titulo titulo) {

		ModelAndView mv = new ModelAndView(CADASTRO_TITULO);
		mv.addObject(titulo);

		return mv;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
		titulos.delete(id);
		
		attributes.addFlashAttribute("mensagem", "Titulo excluido com sucesso!");
		return "redirect:/titulo";
	}

	// retorna uma lista com os StatusTitulos.
	@ModelAttribute("todosStatosTitulos")
	public List<StatusTitulo> todosStatusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}
}
