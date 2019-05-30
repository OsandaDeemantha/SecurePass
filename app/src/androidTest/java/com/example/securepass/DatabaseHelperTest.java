package com.example.securepass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseHelperTest {

    DatabaseHelper db;

    @Before
    public void init(){
        Context appContext = InstrumentationRegistry.getTargetContext();

        db = new DatabaseHelper(appContext);
    }

    @Test
    public void addCredential() {
        db.addCredential(new Credential("df","sod","ger","gfger"));
    }

    @Test
    public void getAllURLs(){
        List<String> urls = db.getAllURLs();
        List<String> expected = new ArrayList<>();
        expected.add("dfd");
        expected.add("df");

        Assert.assertArrayEquals(expected.toArray(),urls.toArray());
    }

    @Test
    public void getCredentialCount(){
        int i = db.getCredentialCount();
        int expected = 9;

        Assert.assertEquals(expected,i);
    }

}