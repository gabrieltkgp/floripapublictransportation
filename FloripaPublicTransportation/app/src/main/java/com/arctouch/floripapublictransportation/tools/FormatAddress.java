package com.arctouch.floripapublictransportation.tools;

/**
 * Created by GabrielPacheco on 23/01/2016.
 */
public class FormatAddress {

    public String extractStreetName(String street){
        String streetExtract;

        streetExtract = street.toUpperCase();

        streetExtract = streetExtract.replace("AVENIDA", "");
        streetExtract = streetExtract.replace("PROFESSOR", "");
        streetExtract = streetExtract.replace("RUA", "");
        streetExtract = streetExtract.replace("SERVIDÃO", "");
        streetExtract = streetExtract.replace("RODOVIA", "");

        streetExtract = streetExtract.replace("AV.", "");
        streetExtract = streetExtract.replace("R.", "");
        streetExtract = streetExtract.replace("SERV.", "");
        streetExtract = streetExtract.replace("ROD.", "");
        streetExtract = streetExtract.replace("PROF.", "");

        streetExtract = streetExtract.trim();

        return streetExtract;
    }
}
