package com.pontointeligente.repository;

import com.pontointeligente.enums.PerfilEnum;
import com.pontointeligente.enums.TipoEnum;
import com.pontointeligente.model.Empressa;
import com.pontointeligente.model.Funcionario;
import com.pontointeligente.model.Lancamento;
import com.pontointeligente.utils.PasswordsUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpressaRepository empressaRepository;

    private Long funcionarioId;

    @Before
    public void setUp() throws  Exception {
        Empressa empressa = this.empressaRepository.save(obterDadosEmpressa());

        Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empressa));
        this.funcionarioId = funcionario.getId();

        this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
        this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
    }

    @After
    public final void tearDown() {
        this.empressaRepository.deleteAll();
    }

    @Test
    public void testBuscarLancamentosPorFuncionarioId() {
        List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId);
        Assert.assertEquals(2, lancamentos.size());
    }

    @Test
    public void testBuscarLancamentosPorFuncionarioIdPaginado() {
        Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, PageRequest.of(0, 10));
        Assert.assertEquals(2, lancamentos.getTotalElements());
    }

    private Lancamento obterDadosLancamentos(Funcionario funcionario) {
        Lancamento lancamento = new Lancamento();
        lancamento.setDate(new Date());
        lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
        lancamento.setFuncionario(funcionario);
        return lancamento;
    }

    private Funcionario obterDadosFuncionario(Empressa empressa) throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Fulano de Tal");
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(PasswordsUtils.gerarBCrypt("123456"));
        funcionario.setCpf("56564564564");
        funcionario.setEmail("email@email.com");
        funcionario.setEmpresa(empressa);
        return funcionario;
    }

    private Empressa obterDadosEmpressa() {
        Empressa empressa = new Empressa();
        empressa.setRazonSocial("Empresa de Exemplo");
        empressa.setCnpj("4534565445767667");
        return empressa;
    }
}
