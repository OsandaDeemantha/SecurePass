package com.example.securepass;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CryptoServiceTest {

    CryptoService cr;

    @Before
    public void init(){

        cr = new CryptoService();
    }

    @Test
    public void encrypt() {
        String encrypted = cr.encrypt("osa");
        String ex = "4KzyElo4MZ7qH2RYiecivg==";

        Assert.assertEquals(ex,encrypted);
    }

    @Test
    public void decrypt() {
        String decrypted = cr.decrypt("4KzyElo4MZ7qH2RYiecivg==");
        String ex = "osa";

        Assert.assertEquals(ex,decrypted);
    }
}