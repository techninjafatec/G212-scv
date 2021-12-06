package com.G1.scv.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@Entity
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String codVenda;
	
	private String cod;
	
	@NotNull
	@Size(min = 1, max = 50, message = "Nome deve ser preenchido")
	private String nome;
	
	private double valor;
	
	private int quant;
	
	@CNPJ(message = "CNPJ inválido")
	@Column(unique = true) // nao funciona com @Valid tem que tratar na camada de persistencia
	private String cnpjForn;
	
	private String dataCadastro;
	private String dataUltimaTransacao;

	public Produto() {
	}

	public Produto(String codVenda, String cod, String nome, double valor, int quant, String cnpjForn) {
		super();
		this.codVenda = codVenda;
		this.cod = cod;
		this.nome = nome;
		this.valor = valor;
		this.quant = quant;
		this.cnpjForn = cnpjForn;
		DateTime dataAtual = new DateTime();
		setDataCadastro(dataAtual);
	}

	public String getCodVenda() {
		return codVenda;
	}

	public void setCodVenda(String codVenda) {
		this.codVenda = codVenda;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getQuant() {
		return quant;
	}

	public void setQuant(int quant) {
		this.quant = quant;
	}

	public String getCnpjForn() {
		return cnpjForn;
	}

	public void setCnpjForn(String cnpjForn) {
		this.cnpjForn = cnpjForn;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(DateTime dataAtual) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY/MM/dd");
		this.dataCadastro = dataAtual.toString(fmt);
		setDataUltimaTransacao(dataAtual);
	}

	public String getDataUltimaTransacao() {
		return dataUltimaTransacao;
	}

	public void setDataUltimaTransacao(DateTime data) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY/MM/dd");
		this.dataUltimaTransacao = data.toString(fmt);
	}
// omitidos hashcode e equals
// omitido toString
}