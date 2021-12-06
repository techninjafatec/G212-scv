package com.G1.scv.servico;

import org.springframework.web.servlet.ModelAndView;
import com.G1.scv.model.Fornecedor;
import com.G1.scv.model.Endereco;

public interface FornecedorServico {
	public Iterable<Fornecedor> findAll();

	public Fornecedor findByCnpj(String cnpj);

	public void deleteById(Long id);

	public Fornecedor findById(Long id);

	public ModelAndView saveOrUpdate(Fornecedor fornecedor);
	
	public Fornecedor save (Fornecedor fornecedor);

	public Endereco obtemEndereco(String cep);
}
