package com.G1.scv.servico;

import org.springframework.web.servlet.ModelAndView;
import com.G1.scv.model.Produto;

public interface ProdutoServico {
	public Iterable<Produto> findAll();

	public Produto findByCod(String cod);

	public void deleteById(Long id);

	public Produto findById(Long id);

	public ModelAndView saveOrUpdate(Produto produto);
	
	
}
