package com.wilembergson.minhasFinancas.model.repository;

import com.wilembergson.minhasFinancas.model.entity.Lancamento;
import com.wilembergson.minhasFinancas.model.enums.StatusLancamento;
import com.wilembergson.minhasFinancas.model.enums.TipoLancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    @Query(value =
            "select sum(l.valor) from Lancamento l join l.usuario u " +
                    " where u.id = :idUsuario and l.tipo =:tipo and l.status = :status group by u ")
    BigDecimal obterSaldoPorTipoEUsuarioEStatus(@Param("idUsuario") Long idUsuario,
                                                @Param("tipo") TipoLancamento tipo,
                                                @Param("status") StatusLancamento status);
}
