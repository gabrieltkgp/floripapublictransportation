package com.arctouch.floripapublictransportation.tools;

import java.text.Normalizer;

/**
 * Created by GabrielPacheco on 23/01/2016.
 */
public class FormatAddress {

    /**
     * Method to remove the public place name to send to the rest server.
     * @param street
     * @return
     */
    public String extractStreetName(String street) {
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

        streetExtract = removeSpecialCharacter(streetExtract);

        streetExtract = streetExtract.trim();

        return streetExtract;
    }

    /**
     * to avoid errors, it´s necessary remove special characteres to send to the rest server.
     * @param street
     * @return
     */

    public String removeSpecialCharacter(String street) {
        CharSequence cs = new StringBuilder(street);

        return Normalizer.normalize(cs, Normalizer.Form.NFKD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
