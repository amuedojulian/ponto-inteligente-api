package com.pontointeligente.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordsUtilsTest {

    private static final String SENHA = "12344465";
    private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();

    @Test
    public void testSenhaNula() throws Exception {
        Assert.assertNull(PasswordsUtils.gerarBCrypt(null));
    }

    @Test
    public void testGerarHashSenha() throws Exception {
        String hash = PasswordsUtils.gerarBCrypt(SENHA);

        Assert.assertTrue(bCryptEncoder.matches(SENHA, hash));
    }
}
