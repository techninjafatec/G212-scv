package com.G1.scv.servico;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import com.G1.scv.model.Produto;
import com.G1.scv.model.ProdutoRepository;

@Service
public class ProdutoServicoI implements ProdutoServico {
	Logger logger = LogManager.getLogger(ProdutoServicoI.class);
	@Autowired
	private ProdutoRepository produtoRepository;

	public Iterable<Produto> findAll() {
		return produtoRepository.findAll();
	}

	public Produto findByCod(String cod) {
		return produtoRepository.findByCod(cod);
	}

	public void deleteById(Long id) {
		produtoRepository.deleteById(id);
		logger.info(">>>>>> 2. comando exclusao executado para o id => " + id);
	}

	public Produto findById(Long id) {
		return produtoRepository.findById(id).get();
	}

	@Override
	public ModelAndView saveOrUpdate(Produto produto) {
		ModelAndView modelAndView = new ModelAndView("consultarProduto");
		try {
			produto.setDataCadastro(new DateTime());
			this.produtoRepository.save(produto);
		} catch (Exception e) {
			modelAndView.setViewName("cadastrarProduto");
			if (e.getMessage().contains("could not execute statement")) {
				modelAndView.addObject("message", "Dados invalidos - produto já cadastrado.");
				logger.info(">>>>>> 5. produto ja cadastrado ==> " + e.getMessage());
			} else {
				modelAndView.addObject("message", "Erro não esperado - contate o administrador");
				logger.error(">>>>>> 5. erro nao esperado ==> " + e.getMessage());
			}
		}
		return modelAndView.addObject("produtos", produtoRepository.findAll());
	}
}