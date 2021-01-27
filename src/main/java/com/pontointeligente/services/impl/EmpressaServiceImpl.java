package com.pontointeligente.services.impl;

import com.pontointeligente.model.Empressa;
import com.pontointeligente.repository.EmpressaRepository;
import com.pontointeligente.services.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpressaServiceImpl implements EmpresaService {

    private static final Logger log = LoggerFactory.getLogger(EmpressaServiceImpl.class);

    @Autowired
    private EmpressaRepository empressaRepository;

    @Override
    public Optional<Empressa> buscarPorCnpj(String cnpj) {
        log.info("Buscando uma empressa para o CNPJ {}", cnpj);
        return Optional.ofNullable(empressaRepository.findByCnpj(cnpj));
    }

    @Override
    public Empressa persistir(Empressa empressa) {
        log.info("Persistiendo empressa: {}", empressa);
        return this.empressaRepository.save(empressa);
    }

}
