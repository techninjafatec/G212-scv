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
import com.G1.scv.servico.VendaServico;
import com.G1.scv.model.Venda;

@Controller
@RequestMapping(path = "/sig")
public class VendaController {
	Logger logger = LogManager.getLogger(VendaController.class);
	@Autowired
	VendaServico servico;

	@GetMapping("/vendas")
	public ModelAndView retornaFormDeConsultaTodasVendas() {
		ModelAndView modelAndView = new ModelAndView("consultarVenda");
		modelAndView.addObject("vendas", servico.findAll());
		return modelAndView;
	}

	@GetMapping("/venda")
	public ModelAndView retornaFormDeCadastroDe(Venda venda) {
		ModelAndView mv = new ModelAndView("cadastrarVenda");
		mv.addObject("venda", venda);
		return mv;
	}

	@GetMapping("/vendas/{cod}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView retornaFormParaEditarVenda(@PathVariable("cod") String cod) {
		ModelAndView modelAndView = new ModelAndView("atualizarVenda");
		modelAndView.addObject("venda", servico.findByCod(cod)); // o repositorio e injetado no controller
		return modelAndView; // addObject adiciona objetos para view
	}

	@GetMapping("/venda/{id}")
	public ModelAndView excluirNoFormDeConsultaVenda(@PathVariable("id") Long id) {
		servico.deleteById(id);
		logger.info(">>>>>> 1. servico de exclusao chamado para o id => " + id);
		ModelAndView modelAndView = new ModelAndView("consultarVenda");
		modelAndView.addObject("vendas", servico.findAll());
		return modelAndView;
	}

	@PostMapping("/vendas")
	public ModelAndView save(@Valid Venda venda, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarVenda");
		if (result.hasErrors()) {
			modelAndView.setViewName("cadastrarVenda");
		} else {
			modelAndView = servico.saveOrUpdate(venda);
		}
		return modelAndView;
	}

	@PostMapping("/vendas/{id}")
	public ModelAndView atualizaVenda(@PathVariable("id") Long id, @Valid Venda venda, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarVenda");
		if (result.hasErrors()) {
			venda.setId(id);
			return new ModelAndView("atualizarVenda");
		}
// programacao defensiva - deve-se verificar se o Cliente existe antes de atualizar
		Venda umaVenda = servico.findById(id);
		umaVenda.setCod(venda.getCod());
		umaVenda.setCpf(venda.getCpf());
		umaVenda.setCodProduto(venda.getCodProduto());
		umaVenda.setQuant(venda.getQuant());
		umaVenda.setTotal(venda.getTotal());
		modelAndView = servico.saveOrUpdate(umaVenda);
		return modelAndView;
	}
}