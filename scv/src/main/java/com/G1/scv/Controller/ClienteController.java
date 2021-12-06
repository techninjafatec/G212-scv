package com.G1.scv.Controller;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import com.G1.scv.servico.ClienteServico;
import com.G1.scv.model.Cliente;

@Controller
@RequestMapping(path = "/sig")
public class ClienteController {
	Logger logger = LogManager.getLogger(ClienteController.class);
	@Autowired
	ClienteServico servico;

	@GetMapping("/clientes")
	public ModelAndView retornaFormDeConsultaTodosClientes() {
		ModelAndView modelAndView = new ModelAndView("consultarCliente");
		modelAndView.addObject("clientes", servico.findAll());
		return modelAndView;
	}

	@GetMapping("/cliente")
	public ModelAndView retornaFormDeCadastroDe(Cliente cliente) {
		ModelAndView mv = new ModelAndView("cadastrarCliente");
		mv.addObject("cliente", cliente);
		return mv;
	}

	@GetMapping("/clientes/{cpf}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView retornaFormParaEditarCliente(@PathVariable("cpf") String cpf) {
		ModelAndView modelAndView = new ModelAndView("atualizarCliente");
		modelAndView.addObject("cliente", servico.findByCpf(cpf)); // o repositorio e injetado no controller
		return modelAndView; // addObject adiciona objetos para view
	}

	@GetMapping("/cliente/{id}")
	public ModelAndView excluirNoFormDeConsultaCliente(@PathVariable("id") Long id) {
		servico.deleteById(id);
		logger.info(">>>>>> 1. servico de exclusao chamado para o id => " + id);
		ModelAndView modelAndView = new ModelAndView("consultarCliente");
		modelAndView.addObject("clientes", servico.findAll());
		return modelAndView;
	}

	@PostMapping("/clientes")
	public ModelAndView save(@Valid Cliente cliente, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarCliente");
		if (result.hasErrors()) {
			modelAndView.setViewName("cadastrarCliente");
		} else {
			try {
				servico.save(cliente);
				modelAndView.addObject("clientes", servico.findAll());
				logger.error(">>>>>> 5. controller save chamada para consultar clientes ");
			} catch (HttpClientErrorException e) {
				modelAndView.addObject("message", "Dados invalidos - 400 Bad Request");
				modelAndView.setViewName("cadastrarCliente");
			} catch (ConstraintViolationException e) {
				modelAndView.addObject("message", "Dados invalidos - constraint violation");
				modelAndView.setViewName("cadastrarCliente");
			} catch (DataIntegrityViolationException e) {
				modelAndView.addObject("message", "Dados invalidos - cliente já cadastrado");
				modelAndView.setViewName("cadastrarCliente");
			} catch (Exception e) {
				modelAndView = new ModelAndView("cadastrarCliente");
				modelAndView.addObject("message", "Erro não esperado - contate o administrador ==>" + e.getMessage());
				logger.error(">>>>> 5. erro nao esperado ==> " + e.getMessage());
				logger.error(">>>>> 5. erro nao esperado ==> " + e.toString());
			}
		}
		return modelAndView;
	}

	@PostMapping("/clientes/{id}")
	public ModelAndView atualizaCliente(@PathVariable("id") Long id, @Valid Cliente cliente, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarCliente");
		if (result.hasErrors()) {
			cliente.setId(id);
			return new ModelAndView("atualizarCliente");
		}
		Cliente umCliente = servico.findById(id);
		umCliente.setId(id);
		umCliente.setCpf(cliente.getCpf());
		umCliente.setNome(cliente.getNome());
		umCliente.setEmail(cliente.getEmail());
		umCliente.setCep(cliente.getCep());
		DateTime dataAtual = new DateTime();
		umCliente.setDataUltimaTransacao(dataAtual);
		servico.save(umCliente);
		modelAndView.addObject("clientes", servico.findAll());
		return modelAndView;
	}
}
