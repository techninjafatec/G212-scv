package com.G1.scv.servico;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.G1.scv.model.Fornecedor;
import com.G1.scv.model.FornecedorRepository;
import com.G1.scv.model.Endereco;
import com.G1.scv.model.EnderecoRepository;

@Service
public class FornecedorServicoI implements FornecedorServico {
	Logger logger = LogManager.getLogger(FornecedorServicoI.class);
	@Autowired
	private FornecedorRepository fornecedorRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Iterable<Fornecedor> findAll() {
		return fornecedorRepository.findAll();
	}

	public Fornecedor findByCnpj(String cnpj) {
		return fornecedorRepository.findByCnpj(cnpj);
	}

	public void deleteById(Long id) {
		fornecedorRepository.deleteById(id);
		logger.info(">>>>>> 2. comando exclusao executado para o id => " + id);
	}

	public Fornecedor findById(Long id) {
		return fornecedorRepository.findById(id).get();
	}

	public ModelAndView saveOrUpdate(Fornecedor fornecedor) {
		ModelAndView modelAndView = new ModelAndView("consultarFornecedor");
		try {
			Endereco endereco = obtemEndereco(fornecedor.getCep());
			if (endereco != null) {
				fornecedor.setDataCadastro(new DateTime());
				endereco.setCnpj(fornecedor.getCnpj());
				enderecoRepository.save(endereco);
				fornecedor.setEndereco(endereco);
				fornecedorRepository.save(fornecedor);
				logger.info(">>>>>> 4. comando save executado ");
				modelAndView.addObject("fornecedores", fornecedorRepository.findAll());
			}
		} catch (Exception e) {
			modelAndView.setViewName("cadastrarFornecedor");
			if (e.getMessage().contains("could not execute statement")) {
				modelAndView.addObject("message", "Dados invalidos - fornecedor já cadastrado.");
				logger.info(">>>>>> 5. fornecedor ja cadastrado ==> " + e.getMessage());
			} else {
				modelAndView.addObject("message", "Erro não esperado - contate o administrador");
				logger.error(">>>>>> 5. erro nao esperado ==> " + e.getMessage());
			}
		}
		return modelAndView;
	}

	public Fornecedor save(Fornecedor fornecedor) {
		Endereco endereco = obtemEndereco(fornecedor.getCep());
		if (endereco != null) {
			fornecedor.setDataCadastro(new DateTime());
			endereco.setCnpj(fornecedor.getCnpj());
			enderecoRepository.save(endereco);
			fornecedor.setEndereco(endereco);
			fornecedorRepository.save(fornecedor);
			logger.info(">>>>>> 4. servico comando save executado ");
		}
		return null;
	}

	public Endereco obtemEndereco(String cep) {
		RestTemplate template = new RestTemplate();
		String url = "https://viacep.com.br/ws/{cep}/json/";
		Endereco endereco = template.getForObject(url, Endereco.class, cep);
		logger.info(">>>>>> 3. obtem endereco ==> " + endereco.toString());
		return endereco;
	}
}