package com.labirynt;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 *
 * @author adam
 */
public class OdczytPliku {

    protected String sciezkaDoPliku;

    OdczytPliku(String sciezka) {
        this.sciezkaDoPliku = sciezka;
    }

    public boolean sprawdzBinarny() {
        boolean b = false;
        try {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(this.sciezkaDoPliku))));
            while (true) {
                try {
                    int fileid = Integer.reverseBytes(dis.readInt());
                    if (fileid == 0x52524243) {
                        b = true;
                    }
                    break;

                } catch (java.io.EOFException eof) {
                    break;
                }
            }
            dis.close();
        } catch (IOException ex) {

        }

        return b;
    }

    public boolean sprawdzTektowy() {
        boolean b = false;
        String linia;
        try {
            File plik = new File(this.sciezkaDoPliku);
            Scanner skaner = new Scanner(plik);
            if (skaner.hasNextLine()) {
                linia = skaner.nextLine();
                for (int i = 0; i < linia.length(); i++) {
                    char c = linia.charAt(i);
                    if (c == 'a' || c == 'X' || c == 'P' || c == 'K' || c == '*') {

                    } else {
                        break;
                    }
                    if (i == linia.length() - 1) {
                        b = true;
                    }
                }

            }

            skaner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Nie znaleziono pliku!");
        }

        return b;
    }

}
