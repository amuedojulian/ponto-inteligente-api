package com.pontointeligente.repository;

import com.pontointeligente.enums.PerfilEnum;
import com.pontointeligente.model.Empressa;
import com.pontointeligente.model.Funcionario;
import com.pontointeligente.utils.PasswordsUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpressaRepository empressaRepository;

    private static final String EMAIL = "email@email.com";
    private static final String CPF = "9845766765";

    @Before
    public void setUp() throws  Exception {
        Empressa empressa = this.empressaRepository.save(obterDadosEmpressa());
        this.funcionarioRepository.save(obterDadosFuncionario(empressa));
    }

    @After
    public final void tearDown() {
        this.empressaRepository.deleteAll();
    }

    @Test
    public void testBuscarFuncionarioPorEmail() {
        Funcionario funcionario = this.funcionarioRepository.findByEmail(EMAIL);
        Assert.assertEquals(EMAIL, funcionario.getEmail());
    }

    @Test
    public void testBuscarFuncionarioPorCpf() {
        Funcionario funcionario = this.funcionarioRepository.findByCpf(CPF);
        Assert.assertEquals(CPF, funcionario.getCpf());
    }

    @Test
    public void testBuscarFuncionarioPorEmailECpf() {
        Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);
        Assert.assertNotNull(funcionario);
    }

    @Test
    public void testBuscarFuncionarioPorEmailECpfParaEmailInvalido() {
        Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, "email@invalido.com");
        Assert.assertNotNull(funcionario);
    }

    private Funcionario obterDadosFuncionario(Empressa empressa) throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Fulano de Tal");
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(PasswordsUtils.gerarBCrypt("123456"));
        funcionario.setCpf(CPF);
        funcionario.setEmail(EMAIL);
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
