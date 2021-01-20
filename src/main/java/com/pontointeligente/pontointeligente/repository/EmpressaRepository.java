package com.pontointeligente.pontointeligente.repository;

import com.pontointeligente.pontointeligente.model.Empressa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface EmpressaRepository extends JpaRepository<Empressa, Long>{
    @Transactional(readOnly = true)
    Empressa findByCnpj(String cnpj);
}
