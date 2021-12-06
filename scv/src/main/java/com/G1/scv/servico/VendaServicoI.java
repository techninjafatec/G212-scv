package com.G1.scv.servico;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.G1.scv.servico.ProdutoServico;
import com.G1.scv.servico.ClienteServico;
import com.G1.scv.model.Cliente;
import com.G1.scv.model.ClienteRepository;
import com.G1.scv.model.Produto;
import com.G1.scv.model.ProdutoRepository;
import com.G1.scv.model.Venda;
import com.G1.scv.model.VendaRepository;


@Service
public class VendaServicoI implements VendaServico{

	Logger logger = LogManager.getLogger(VendaServicoI.class);
	@Autowired
	private VendaRepository vendaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private ProdutoServico produtoServico;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ClienteServico clienteServico;


	public Iterable<Venda> findAll() {
		return vendaRepository.findAll();
	}

	public Venda findByCod(String cod) {
		return vendaRepository.findByCod(cod);
	}

	public void deleteById(Long id) {
		vendaRepository.deleteById(id);
		logger.info(">>>>>> 2. comando exclusao executado para o id => " + id);
	}

	public Venda findById(Long id) {
		return vendaRepository.findById(id).get();
	}

	@Override
	public ModelAndView saveOrUpdate(Venda venda) {
		ModelAndView modelAndView = new ModelAndView("consultarVenda");
		try {
			Produto produto = obtemProduto(venda.getCodProduto());
			Cliente cliente = obtemCpf(venda.getCpf());
				
				venda.setDataVenda(new DateTime());
				produtoRepository.save(produto);
				clienteRepository.save(cliente);
				venda.setProduto(produto);
				venda.setCliente(cliente);
				double total = (venda.getQuant()* produto.getValor());
				venda.setTotal(total);
				int estoque= (produto.getQuant() - venda.getQuant());
				produto.setQuant(estoque);
				vendaRepository.save(venda);
				logger.info(">>>>>> 4. comando save executado ");
				modelAndView.addObject("vendas", vendaRepository.findAll());
				
		} catch (Exception e) {
			modelAndView.setViewName("cadastrarVenda");
			if (e.getMessage().contains("could not execute statement")) {
				modelAndView.addObject("message", "Dados invalidos - venda já cadastrada.");
				logger.info(">>>>>> 5. venda ja cadastrada ==> " + e.getMessage());
			} else {
				modelAndView.addObject("message", "Erro não esperado - contate o administrador");
				logger.error(">>>>>> 5. erro nao esperado ==> " + e.getMessage());
			}
		}
		return modelAndView;
		
	}
	
		
	public Produto obtemProduto(String cod) {
		return produtoServico.findByCod(cod);
	}
	
	public Cliente obtemCpf(String cpf) {
		return clienteServico.findByCpf(cpf);
	}

}