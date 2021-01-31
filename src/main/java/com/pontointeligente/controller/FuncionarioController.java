package com.pontointeligente.controller;

import com.pontointeligente.dto.FuncionarioDto;
import com.pontointeligente.model.Funcionario;
import com.pontointeligente.response.Response;
import com.pontointeligente.services.FuncionarioService;
import com.pontointeligente.utils.PasswordsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping("api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

    private static  final Logger log = LoggerFactory.getLogger(FuncionarioController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    public FuncionarioController() {
    }

    /**
     * Atualiza os dados de um funcionario
     *
     * @param id
     * @param funcionarioDto
     * @param result
     * @return ResponseEntity<Response<FuncionarioDto>>
     * @throws java.security.NoSuchAlgorithmException
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody FuncionarioDto funcionarioDto, BindingResult result) throws NoSuchAlgorithmException {
        log.info("Atualizando funcionário: {}", funcionarioDto.toString());
        Response<FuncionarioDto> response = new Response<FuncionarioDto>();

        Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(id);
        if (!funcionario.isPresent()) {
            result.addError(new ObjectError("funcionario", "Funcionario nao encontrado"));
        }

        this.atualizarDadosFuncionario(funcionario.get(), funcionarioDto, result);

        if (result.hasErrors()) {
            log.error("Erro validando funcionário: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.funcionarioService.persistir(funcionario.get());
        response.setData(this.converterFuncionarioDto(funcionario.get()));

        return ResponseEntity.ok(response);
    }

    /**
     * Atualizar os dados do funcionario com base nos dados encontrados no DTO
     *
     * @param funcionario
     * @param funcionarioDto
     * @param bindingResult
     * @throws NoSuchAlgorithmException
     */
    private void atualizarDadosFuncionario(Funcionario funcionario, FuncionarioDto funcionarioDto, BindingResult bindingResult) throws NoSuchAlgorithmException {
        funcionario.setNome(funcionarioDto.getNome());

        if (!funcionario.getEmail().equals(funcionarioDto.getEmail())) {
            this.funcionarioService.buscarPorEmail(funcionarioDto.getEmail()).ifPresent(func -> bindingResult.addError(new ObjectError("email", "Email já Existente")));
            funcionario.setEmail(funcionarioDto.getEmail());
        }

        funcionario.setQtdHorasAlmoco(null);
        funcionarioDto.getQtdHorasAlmoco().ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));

        funcionario.setQtdHorasTrabalhaDia(null);
        funcionarioDto.getQtdHorasTrablhoDia().ifPresent(qtdHorasTrabalhoDia -> funcionario.setQtdHorasTrabalhaDia(Float.valueOf(qtdHorasTrabalhoDia)));

        funcionario.setValorHora(null);
        funcionarioDto.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));

        if (funcionarioDto.getSenha().isPresent()) {
            funcionario.setSenha(PasswordsUtils.gerarBCrypt(funcionarioDto.getSenha().get()));
        }
    }

    /**
     * Retorna um DTO com os dados de um funcionário
     *
     * @param funcionario
     * @return FuncionarioDto
     */
    public FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {
        FuncionarioDto funcionarioDto = new FuncionarioDto();
        funcionarioDto.setId(funcionario.getId());
        funcionarioDto.setEmail(funcionario.getEmail());
        funcionarioDto.setNome(funcionario.getNome());
        funcionario.getQtdHorasAlmocoOpt().ifPresent(qtdHorasAlmoco -> funcionarioDto.setQtdHorasTrablhoDia(Optional.of(qtdHorasAlmoco.toString())));
        funcionario.getQtdHorasTrabalhaDiaOpt().ifPresent(qtdHorasTrabalhoDia -> funcionarioDto.setQtdHorasTrablhoDia(Optional.of(qtdHorasTrabalhoDia.toString())));
        funcionario.getValorHoraOpt().ifPresent(valorHora -> funcionarioDto.setValorHora(Optional.of(valorHora.toString())));

        return funcionarioDto;
    }
}
