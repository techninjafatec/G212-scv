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
import com.G1.scv.servico.ProdutoServico;
import com.G1.scv.model.Produto;

@Controller
@RequestMapping(path = "/sig")
public class ProdutoController {
	Logger logger = LogManager.getLogger(ProdutoController.class);
	@Autowired
	ProdutoServico servico;

	@GetMapping("/produtos")
	public ModelAndView retornaFormDeConsultaTodosProdutos() {
		ModelAndView modelAndView = new ModelAndView("consultarProduto");
		modelAndView.addObject("produtos", servico.findAll());
		return modelAndView;
	}

	@GetMapping("/produto")
	public ModelAndView retornaFormDeCadastroDe(Produto produto) {
		ModelAndView mv = new ModelAndView("cadastrarProduto");
		mv.addObject("produto", produto);
		return mv;
	}

	@GetMapping("/produtos/{cod}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView retornaFormParaEditarProduto(@PathVariable("cod") String cod) {
		ModelAndView modelAndView = new ModelAndView("atualizarProduto");
		modelAndView.addObject("produto", servico.findByCod(cod)); // o repositorio e injetado no controller
		return modelAndView; // addObject adiciona objetos para view
	}

	@GetMapping("/produto/{id}")
	public ModelAndView excluirNoFormDeConsultaProduto(@PathVariable("id") Long id) {
		servico.deleteById(id);
		logger.info(">>>>>> 1. servico de exclusao chamado para o id => " + id);
		ModelAndView modelAndView = new ModelAndView("consultarProduto");
		modelAndView.addObject("produtos", servico.findAll());
		return modelAndView;
	}

	@PostMapping("/produtos")
	public ModelAndView save(@Valid Produto produto, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarProduto");
		if (result.hasErrors()) {
			modelAndView.setViewName("cadastrarProduto");
		} else {
			modelAndView = servico.saveOrUpdate(produto);
		}
		return modelAndView;
	}

	@PostMapping("/produtos/{id}")
	public ModelAndView atualizaProduto(@PathVariable("id") Long id, @Valid Produto produto, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarProduto");
		if (result.hasErrors()) {
			produto.setId(id);
			return new ModelAndView("atualizarProduto");
		}
// programacao defensiva - deve-se verificar se o Cliente existe antes de atualizar
		Produto umProduto = servico.findById(id);
		umProduto.setCod(produto.getCod());
		umProduto.setNome(produto.getNome());
		umProduto.setValor(produto.getValor());
		umProduto.setQuant(produto.getQuant());
		umProduto.setCnpjForn(produto.getCnpjForn());
		modelAndView = servico.saveOrUpdate(umProduto);
		return modelAndView;
	}
}
