package com.example.securepass;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CredentialAddActivityTest {

    DatabaseHelper db;

    @Before
    public void init(){
        Context appContext = InstrumentationRegistry.getTargetContext();

        db = new DatabaseHelper(appContext);
    }

    @Test
    public void addData() {

        Credential credential = new Credential("facv","osa", "dee","https://osa.com");
        db.addCredential(credential);

    }
}