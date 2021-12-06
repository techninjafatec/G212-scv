package com.G1.scv.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends CrudRepository<Fornecedor, Long> {
	public Fornecedor findByCnpj(@Param("cnpj") String cnpj);
}
