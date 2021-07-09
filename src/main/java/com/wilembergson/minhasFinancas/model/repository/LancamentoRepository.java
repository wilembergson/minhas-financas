package com.wilembergson.minhasFinancas.model.repository;

import com.wilembergson.minhasFinancas.model.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
