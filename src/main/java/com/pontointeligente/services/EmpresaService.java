package com.pontointeligente.services;

import com.pontointeligente.model.Empressa;

import java.util.Optional;

public interface EmpresaService {

    /**
     * Retorna uma empresa dado um CNPJ
     *
     * @param cnpj
     * @return Optional<Empressa>
     */
    Optional<Empressa> buscarPorCnpj(String cnpj);

    /**
     * Cadastra uma nova empressa na base de dados
     *
     * @param empressa
     * @return Empressa
     */
    Empressa persistir(Empressa empressa);
}
