package com.pontointeligente.services.impl;

import com.pontointeligente.model.Lancamento;
import com.pontointeligente.repository.LancamentoRepository;
import com.pontointeligente.services.LancamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
        log.info("Buscando lancamentos para o funcionario Id {}", funcionarioId, pageRequest);
        return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
    }

    public Optional<Lancamento> buscarPorId(Long id) {
        log.info("Buscando um lcancamento pelo Id: {}", id);
        return this.lancamentoRepository.findById(id);
    }

    public Lancamento persistir(Lancamento lancamento) {
        log.info("Persisitndo o lancamento {}", lancamento);
        return this.lancamentoRepository.save(lancamento);
    }

    public void remover(Long id) {
        log.info("Removendo o lancamento ID {}", id);
        this.lancamentoRepository.deleteById(id);
    }
}
