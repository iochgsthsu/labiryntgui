/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.labirynt;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author adam
 */
public class OdczytPlikuTekstowego extends OdczytPliku {

    OdczytPlikuTekstowego(String sciezkaDoPliku) {
        super(sciezkaDoPliku);
    }

    public int odczytajIloscWierszy() {
        int iloscWierszy = 0;

        try {
            File plik = new File(this.sciezkaDoPliku);

            Scanner skaner;
            for (skaner = new Scanner(plik); skaner.hasNextLine(); ++iloscWierszy) {
                String linia = skaner.nextLine();
            }

            skaner.close();
            return iloscWierszy;
        } catch (FileNotFoundException ex) {
            System.out.println("Nie znaleziono pliku!");
            return -1;
        }
    }

    public int odczytajIloscKolumn() {
        String linia;
        try {
            File plik = new File(this.sciezkaDoPliku);
            Scanner skaner = new Scanner(plik);
            linia = skaner.nextLine();
            skaner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Nie znaleziono pliku!");
            return -1;
        }

        return linia.length();
    }

    public Pole[][] odczytajZawartoscPliku(int iloscWierszy, int iloscKolumn) {
        Pole[][] zawartosc = new Pole[iloscWierszy][iloscKolumn];
        int danyWiersz = 0;

        try {
            File plik = new File(this.sciezkaDoPliku);

            Scanner skaner;
            for (skaner = new Scanner(plik); skaner.hasNextLine(); ++danyWiersz) {
                String linia = skaner.nextLine();

                for (int i = 0; i < linia.length(); ++i) {
                    zawartosc[danyWiersz][i] = new Pole(danyWiersz, i);
                    zawartosc[danyWiersz][i].setDane(linia.charAt(i));
                    if(linia.charAt(i) == '*')
                    {
                        zawartosc[danyWiersz][i].setSciezka();
                    }

                }
            }

            skaner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Nie znaleziono pliku!");
        }

        return zawartosc;
    }

    public boolean sprawdzRozwiazanie(int iloscWierszy, int iloscKolumn) {

        int danyWiersz = 0;
        boolean r = false;

        try {
            File plik = new File(this.sciezkaDoPliku);

            Scanner skaner;
            for (skaner = new Scanner(plik); skaner.hasNextLine(); ++danyWiersz) {
                String linia = skaner.nextLine();

                for (int i = 0; i < linia.length(); ++i) {
                    if (linia.charAt(i) == '*') {
                        r = true;
                        break;

                    }

                    if (r == true) {
                        break;
                    }

                }
            }

            skaner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Nie znaleziono pliku!");
        }

        return r;

    }

    public int[] odczytajWPoczatku() {
        int[] p = new int[2];
        int danyWiersz = 0;

        try {
            File plik = new File(this.sciezkaDoPliku);

            Scanner skaner;
            for (skaner = new Scanner(plik); skaner.hasNextLine(); ++danyWiersz) {
                String linia = skaner.nextLine();

                for (int i = 0; i < linia.length(); ++i) {
                    if (linia.charAt(i) == 'P') {
                        p[0] = danyWiersz;
                        p[1] = i;
                    }
                }
            }

            skaner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Nie znaleziono pliku!");
        }

        return p;
    }

    public int[] odczytajWKonca() {
        int[] k = new int[2];
        int danyWiersz = 0;

        try {
            File plik = new File(this.sciezkaDoPliku);

            Scanner skaner;
            for (skaner = new Scanner(plik); skaner.hasNextLine(); ++danyWiersz) {
                String linia = skaner.nextLine();

                for (int i = 0; i < linia.length(); ++i) {
                    if (linia.charAt(i) == 'K') {
                        k[0] = danyWiersz;
                        k[1] = i;
                    }
                }
            }

            skaner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Nie znaleziono pliku!");
        }

        return k;
    }

}
