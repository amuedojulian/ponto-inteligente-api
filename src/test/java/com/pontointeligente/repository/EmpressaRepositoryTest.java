package com.pontointeligente.repository;
import com.pontointeligente.model.Empressa;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpressaRepositoryTest {

    @Autowired
    private EmpressaRepository empressaRepository;

    private static final String CNPJ = "434454576676567676";

    @Before
    public void setUp() throws  Exception {
        Empressa empressa = new Empressa();
        empressa.setRazonSocial("Empressa de exemplo");
        empressa.setCnpj(CNPJ);
        this.empressaRepository.save(empressa);
    }

    @After
    public final void tearDown() {
        this.empressaRepository.deleteAll();
    }

    @Test
    public void testBuscarPorCnpj() {
        Empressa empressa = this.empressaRepository.findByCnpj(CNPJ);
        Assert.assertEquals(CNPJ, empressa.getCnpj());

    }
}
