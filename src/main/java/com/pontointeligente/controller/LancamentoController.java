package com.pontointeligente.controller;

import com.pontointeligente.dto.LancamentoDto;
import com.pontointeligente.enums.TipoEnum;
import com.pontointeligente.model.Funcionario;
import com.pontointeligente.model.Lancamento;
import com.pontointeligente.response.Response;
import com.pontointeligente.services.FuncionarioService;
import com.pontointeligente.services.LancamentoService;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
@CrossOrigin(origins = "*")
public class LancamentoController {

    private static final Logger log = LoggerFactory.getLogger(LancamentoController.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Value("${paginacao.qtd_por_pagina}")
    private int qtdPorPagina;

    public LancamentoController() {
    }

    /**
     * Retorna a listagem de lancamentos de um funcionário
     *
     * @param funcionarioId
     * @return ResponseEntity<Response<LancamentoDto>>
     */
    @GetMapping(value = "/funcionario/{funcionarioId}")
    public ResponseEntity<Response<Page<LancamentoDto>>> listarPorFuncionarioId(
            @PathVariable("funcionarioId") Long funcionarioId,
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "DESC") String dir) {
        log.info("Buscando lancamentos por ID do funcionario: {}, página: {}", funcionarioId, pag);
        Response<Page<LancamentoDto>> response = new Response<Page<LancamentoDto>>();

        PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Sort.Direction.valueOf(dir), ord);
        Page<Lancamento> lancamentos = this.lancamentoService.buscarPorFuncionarioId(funcionarioId, pageRequest);
        Page<LancamentoDto> lancamentosDto = lancamentos.map(lancamento -> this.converterLancamentoDto(lancamento));

        response.setData(lancamentosDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Retorna um lancamento por ID
     *
     * @param id
     * @return ResponseEntity<Response<LancamentoDto>>
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<LancamentoDto>> listarPorId(@PathVariable("id") Long id) {
        log.info("Buscando lancamento por ID: {}", id);
        Response<LancamentoDto> response = new Response<LancamentoDto>();
        Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(id);

        if (!lancamento.isPresent()) {
            log.info("Lancamento nao encontrado para o ID: {}", id);
            response.getErrors().add("Lancamento nao encontrado para o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.converterLancamentoDto(lancamento.get()));
        return ResponseEntity.ok(response);
    }

    /**
     * Adiciona um novo lancamento
     *
     * @param lancamentoDto
     * @param result
     * @return ResponseEntity<Response<LancamentoDto>>
     * @throws ParseException
     */
    @PostMapping
    public ResponseEntity<Response<LancamentoDto>> adicionar(@Valid @RequestBody LancamentoDto lancamentoDto, BindingResult result) throws ParseException {
        log.info("Adicionando lancamento: {}", lancamentoDto.toString());
        Response<LancamentoDto> response = new Response<LancamentoDto>();
        validarFuncionario(lancamentoDto, result);
        Lancamento lancamento = this.converterDtoParaLancamento(lancamentoDto, result);

        if (result.hasErrors()) {
            log.error("Erro validando lancamento: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        lancamento = this.lancamentoService.persistir(lancamento);
        response.setData(this.converterLancamentoDto(lancamento));
        return ResponseEntity.ok(response);
    }

    /**
     * Atualiza os dados de um lancamento
     *
     * @param id
     * @param lancamentoDto
     * @return ResponseEntity<Response<LancamentoDto>>
     * @throws ParseException
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<LancamentoDto>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody LancamentoDto lancamentoDto, BindingResult result) throws ParseException {
        log.info("Atualizando lancamento: {}", lancamentoDto.toString());
        Response<LancamentoDto> response = new Response<LancamentoDto>();
        validarFuncionario(lancamentoDto, result);
        lancamentoDto.setId(Optional.of(id));
        Lancamento lancamento = this.converterDtoParaLancamento(lancamentoDto, result);

        if (result.hasErrors()) {
            log.error("Erro validando lancamento: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        lancamento = this.lancamentoService.persistir(lancamento);
        response.setData(this.converterLancamentoDto(lancamento));
        return ResponseEntity.ok(response);
    }

    /**
     * Remove um lancamento por Id
     *
     * @param id
     * @return ResponseEntity<Response<Lancamento>>
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
        log.info("Removendo lancamento: {}", id);
        Response<String> response = new Response<String>();
        Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(id);

        if (!lancamento.isPresent()) {
            log.error("Erro ao remover devido ao lancamento Id: {} ser inválido", id);
            response.getErrors().add("Erro ao remover lancamento. Registro nao encontrado para o id" +  id);
            return ResponseEntity.badRequest().body(response);
        }

        this.lancamentoService.remover(id);
        return ResponseEntity.ok(new Response<String>());
    }

    /**
     * Valida um funcionario. verificando se ele é exitente e válido no sistema
     *
     * @param lancamentoDto
     * @return result
     */
    private void validarFuncionario(LancamentoDto lancamentoDto, BindingResult result) {
        if (lancamentoDto.getFuncionarioId() == null) {
            result.addError(new ObjectError("funcionario", "Funcionario nao informado"));
            return;
        }

        log.info("Validando funcionario id {}:", lancamentoDto.getFuncionarioId());
        Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(lancamentoDto.getFuncionarioId());
        if (!funcionario.isPresent()) {
            result.addError(new ObjectError("funcionario", "Funcionario nao encontrado. ID inexistente"));
        }
    }

    /**
     * Converte uma entidade lancamento para seu respectivo DTO
     *
     * @param lancamento
     * @return LancamentoDto
     */
    private LancamentoDto converterLancamentoDto(Lancamento lancamento) {
        LancamentoDto lancamentoDto = new LancamentoDto();
        lancamentoDto.setId(Optional.of(lancamento.getId()));
        lancamentoDto.setData(this.dateFormat.format(lancamento.getDate()));
        lancamentoDto.setTipo(lancamento.getTipo().toString());
        lancamentoDto.setDescricao(lancamento.getDescricao());
        lancamentoDto.setLocalicacao(lancamento.getLocalicacao());
        lancamentoDto.setFuncionarioId(lancamento.getFuncionario().getId());

        return lancamentoDto;
    }

    /**
     * Converte um LancamentoDto para uma entidade Lancamento
     *
     * @param lancamentoDto
     * @param result
     * @return Lancamento
     * @throws ParseException
     */
    private Lancamento converterDtoParaLancamento(LancamentoDto lancamentoDto, BindingResult result) throws ParseException {
        Lancamento lancamento = new Lancamento();

        if (lancamentoDto.getId().isPresent()) {
            Optional<Lancamento> lanc = this.lancamentoService.buscarPorId(lancamentoDto.getId().get());
            if (lanc.isPresent()) {
                lancamento = lanc.get();
            } else {
                result.addError(new ObjectError("lancamento", "Lancamento nao encontrado"));
            }
        } else {
            lancamento.setFuncionario(new Funcionario());
            lancamento.getFuncionario().setId(lancamentoDto.getFuncionarioId());
        }

        lancamento.setDescricao(lancamentoDto.getDescricao());
        lancamento.setLocalicacao(lancamentoDto.getLocalicacao());
        lancamento.setDate(this.dateFormat.parse(lancamentoDto.getData()));

        if (EnumUtils.isValidEnum(TipoEnum.class, lancamentoDto.getTipo())) {
            lancamento.setTipo(TipoEnum.valueOf(lancamentoDto.getTipo()));
        } else {
            result.addError(new ObjectError("tipo", "Tipo inválido"));
        }

        return lancamento;
    }

}
