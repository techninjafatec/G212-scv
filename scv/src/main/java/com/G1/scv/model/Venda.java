package com.G1.scv.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@Entity
public class Venda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private String cod;
	
	@NotNull
	private String cpf;
	
	@NotNull
	private String codProduto;
	
	@NotNull
	private int quant;
	
	private double total;
	
	@ManyToOne
	private Cliente cliente;
	
	@ManyToOne
	private Produto produto;
	private String dataVenda;
	
	public Venda() {
	}

	public Venda(String cod,String cpf, String codProduto, int quant, double total, Produto produto, Cliente cliente) {
		this.cod = cod;
		this.cpf = cpf;
		this.codProduto = codProduto;
		this.quant = quant;
		this.total = total;
		this.produto = produto;
		this.cliente = cliente;
		DateTime dataAtual = new DateTime();
		setDataVenda(dataAtual);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}	

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public int getQuant() {
		return quant;
	}

	public void setQuant(int quant) {
		this.quant = quant;
	}
	
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getCodProduto() {
		return codProduto;
	}

	public void setCodProduto(String codProduto) {
		this.codProduto = codProduto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public String getDataVenda() {
		return dataVenda;
	}
	
	public void setDataVenda(DateTime dataAtual) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY/MM/dd");
		this.dataVenda = dataAtual.toString(fmt);
	}
	

}