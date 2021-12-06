package com.G1.scv.Controller;

import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.G1.scv.servico.FornecedorServico;
import com.G1.scv.model.Fornecedor;

@Controller
@RequestMapping(path = "/sig")
public class FornecedorController {
	Logger logger = LogManager.getLogger(FornecedorController.class);
	@Autowired
	FornecedorServico servico;

	@GetMapping("/fornecedores")
	public ModelAndView retornaFormDeConsultaTodosFornecedores() {
		ModelAndView modelAndView = new ModelAndView("consultarFornecedor");
		modelAndView.addObject("fornecedores", servico.findAll());
		return modelAndView;
	}

	@GetMapping("/fornecedor")
	public ModelAndView retornaFormDeCadastroDe(Fornecedor fornecedor) {
		ModelAndView mv = new ModelAndView("cadastrarFornecedor");
		mv.addObject("fornecedor", fornecedor);
		return mv;
	}

	@GetMapping("/fornecedores/{cnpj}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView retornaFormParaEditarFornecedor(@PathVariable("cnpj") String cnpj) {
		ModelAndView modelAndView = new ModelAndView("atualizarFornecedor");
		modelAndView.addObject("fornecedor", servico.findByCnpj(cnpj)); // o repositorio e injetado no controller
		return modelAndView; // addObject adiciona objetos para view
	}

	@GetMapping("/fornecedor/{id}")
	public ModelAndView excluirNoFormDeConsultaCliente(@PathVariable("id") Long id) {
		servico.deleteById(id);
		logger.info(">>>>>> 1. servico de exclusao chamado para o id => " + id);
		ModelAndView modelAndView = new ModelAndView("consultarFornecedor");
		modelAndView.addObject("fornecedor", servico.findAll());
		return modelAndView;
	}

	@PostMapping("/fornecedores")
	public ModelAndView save(@Valid Fornecedor fornecedor, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarFornecedor");
		if (result.hasErrors()) {
			modelAndView.setViewName("cadastrarFornecedor");
		} else {
			modelAndView = servico.saveOrUpdate(fornecedor);
		}
		return modelAndView;
	}

	@PostMapping("/fornecedores/{id}")
	public ModelAndView atualizaFornecedor(@PathVariable("id") Long id, @Valid Fornecedor fornecedor, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarFornecedor");
		if (result.hasErrors()) {
			fornecedor.setId(id);
			return new ModelAndView("atualizarFornecedor");
		}
// programacao defensiva - deve-se verificar se o Cliente existe antes de atualizar
		Fornecedor umFornecedor = servico.findById(id);
		umFornecedor.setCnpj(fornecedor.getCnpj());
		umFornecedor.setNome(fornecedor.getNome());
		umFornecedor.setEmail(fornecedor.getEmail());
		umFornecedor.setCep(fornecedor.getCep());
		modelAndView = servico.saveOrUpdate(umFornecedor);
		return modelAndView;
	}
}