package com.pontointeligente.pontointeligente.services;

import com.pontointeligente.pontointeligente.model.Funcionario;

import java.util.Optional;

public interface FuncionarioService {

    /**
     * Persiste um funcionário na base de dados
     *
     * @param funcionario
     * @return funcionario
     */
    Funcionario persistir(Funcionario funcionario);

    /**
     * Busca e retorna um funcionario dado um cpf
     *
     * @param cpf
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorCpf(String cpf);

    /**
     * Busca e retorna um funcionario dado um email
     *
     * @param email
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorEmail(String email);

    /**
     * Busca e retorna um funcionario por ID
     *
     * @param id
     * @return Optional<Funcionario>
     */
    Optional<Optional<Funcionario>> buscarPorId(Long id);
}
