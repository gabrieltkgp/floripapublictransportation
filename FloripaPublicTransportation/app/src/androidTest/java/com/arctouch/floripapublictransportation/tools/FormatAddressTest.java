package com.arctouch.floripapublictransportation.tools;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by GabrielPacheco on 23/01/2016.
 */
@RunWith(AndroidJUnit4.class)
public class FormatAddressTest {

    @Test
    public void testeExractStreetNameRemoveAvenida(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "AVENIDA TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testeExractStreetNameRemoveAv(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "AV. TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testeExractStreetNameRemoveRua(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "RUA TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testeExractStreetNameRemoveR(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "R. TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testeExractStreetNameRemoveServidao(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "SERVIDÃO TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testeExractStreetNameRemoveServ(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "SERV. TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testeExractStreetNameRemoveRodovia(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "RODOVIA TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testeExractStreetNameRemoveRod(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "ROD. TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testeExractStreetNameRemoveProfessor(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "PROFESSOR TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testeExractStreetNameRemoveProf(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "PROF. TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

}