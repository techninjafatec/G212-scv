package com.G1.scv.servico;

import org.springframework.web.servlet.ModelAndView;
import com.G1.scv.model.Cliente;
import com.G1.scv.model.Endereco;

public interface ClienteServico {
	public Iterable<Cliente> findAll();

	public Cliente findByCpf(String cpf);

	public void deleteById(Long id);

	public Cliente findById(Long id);

	public ModelAndView saveOrUpdate(Cliente cliente);
	
	public Cliente save (Cliente cliente);

	public Endereco obtemEndereco(String cep);
	
	public String sendMail(Cliente cliente);
}
