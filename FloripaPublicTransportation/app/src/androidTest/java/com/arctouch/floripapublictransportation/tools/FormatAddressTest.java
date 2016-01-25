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
    public void testExractStreetNameRemoveAvenida(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "AVENIDA TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testExractStreetNameRemoveAv(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "AV. TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testExractStreetNameRemoveRua(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "RUA TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testExractStreetNameRemoveR(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "R. TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testExractStreetNameRemoveServidao(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "SERVIDÃO TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testExractStreetNameRemoveServ(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "SERV. TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testExractStreetNameRemoveRodovia(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "RODOVIA TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testExractStreetNameRemoveRod(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "ROD. TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testExractStreetNameRemoveProfessor(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "PROFESSOR TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testExractStreetNameRemoveProf(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "PROF. TEST";
        street = formatAddress.extractStreetName(street);

        Assert.assertEquals(street, "TEST");
    }

    @Test
    public void testRemoveSpecialCharacter(){
        FormatAddress formatAddress = new FormatAddress();
        String street = "á ç í ã â á à ü";
        street = formatAddress.removeSpecialCharacter(street);

        Assert.assertEquals(street, "a c i a a a a u");
    }

}