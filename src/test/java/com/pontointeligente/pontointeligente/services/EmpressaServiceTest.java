package com.pontointeligente.pontointeligente.services;

import com.pontointeligente.pontointeligente.model.Empressa;
import com.pontointeligente.pontointeligente.repository.EmpressaRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpressaServiceTest {

    @MockBean
    private EmpressaRepository empressaRepository;

    @Autowired
    private EmpresaService empresaService;

    private static final String CNPJ = "534534545545345";

    @Before
    public void setUp() throws Exception {
        BDDMockito.given(this.empressaRepository.findByCnpj(Mockito.anyString())).willReturn(new Empressa());
        BDDMockito.given(this.empressaRepository.save(Mockito.any(Empressa.class))).willReturn(new Empressa());
    }

    @Test
    public void testBuscarEmpressaPorCnpj() {
        Optional<Empressa> empressa = this.empresaService.buscarPorCnpj(CNPJ);
        Assert.assertTrue(empressa.isPresent());
    }

    @Test
    public void testPersistirEmpressa() {
        Empressa empressa = this.empresaService.persistir(new Empressa());
        Assert.assertNotNull(empressa);
    }

}
