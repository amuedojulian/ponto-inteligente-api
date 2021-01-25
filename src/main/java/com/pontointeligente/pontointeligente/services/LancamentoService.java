package com.pontointeligente.pontointeligente.services;

import com.pontointeligente.pontointeligente.model.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface LancamentoService {

    /**
     * Retorna uma lista paginada de lancamentos de um determinado funcionário
     *
     * @param funcionarioId
     * @return pageRequest
     */
    Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest);

    /**
     * Retorna um lancamento por Id
     *
     * @param id
     * @return Optional<Lancamento>
     */
    Optional<Optional<Lancamento>> buscarPorId(Long id);

    /**
     * Persiste um lancamento na base de dados
     *
     * @param lancamento
     * @return Lancamento
     */
    Lancamento persistir(Lancamento lancamento);

    /**
     * Remove um lancamento na base de dados
     *
     * @param id
     */
    void remover(Long id);


}
