package com.G1.scv.servico;

import org.springframework.web.servlet.ModelAndView;

import com.G1.scv.model.Produto;
import com.G1.scv.model.Venda;
import com.G1.scv.model.Cliente;


public interface VendaServico {

	public Iterable<Venda> findAll();
	
	public Venda findByCod(String cod);

	public void deleteById(Long id);

	public Venda findById(Long id);

	public ModelAndView saveOrUpdate(Venda venda);
	
	public Produto obtemProduto(String cod);
	
	public Cliente obtemCpf(String cpf);

}