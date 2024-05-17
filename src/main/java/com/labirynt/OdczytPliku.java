/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.labirynt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author adam
 */


public class OdczytPliku {
     public static int odczytajIloscWierszy(String sciezkaDoPliku) {
      int iloscWierszy = 0;

      try {
         File plik = new File(sciezkaDoPliku);

         Scanner skaner;
         for(skaner = new Scanner(plik); skaner.hasNextLine(); ++iloscWierszy) {
            String linia = skaner.nextLine();
         }

         skaner.close();
         return iloscWierszy;
      } catch (FileNotFoundException ex) {
         System.out.println("Nie znaleziono pliku!");
         return -1;
      }
   }

   public static int odczytajIloscKolumn(String sciezkaDoPliku) {
      String linia;
      try {
         File plik = new File(sciezkaDoPliku);
         Scanner skaner = new Scanner(plik);
         linia = skaner.nextLine();
         skaner.close();
      } catch (FileNotFoundException ex) {
         System.out.println("Nie znaleziono pliku!");
         return -1;
      }

      return linia.length();
   }

   public static Pole[][] odczytajZawartoscPliku(int iloscWierszy, int iloscKolumn, String sciezkaDoPliku) {
      Pole[][] zawartosc = new Pole[iloscWierszy][iloscKolumn];
      int danyWiersz = 0;

      try {
         File plik = new File(sciezkaDoPliku);

         Scanner skaner;
         for(skaner = new Scanner(plik); skaner.hasNextLine(); ++danyWiersz) {
            String linia = skaner.nextLine();

            for(int i = 0; i < linia.length(); ++i) {
               zawartosc[danyWiersz][i] = new Pole(danyWiersz, i);
               zawartosc[danyWiersz][i].setDane(linia.charAt(i));
            }
         }

         skaner.close();
      } catch (FileNotFoundException ex) {
         System.out.println("Nie znaleziono pliku!");
      }

      return zawartosc;
   }

   public static int[] odczytajWPoczatku(String sciezkaDoPliku) {
      int[] p = new int[2];
      int danyWiersz = 0;

      try {
         File plik = new File(sciezkaDoPliku);

         Scanner skaner;
         for(skaner = new Scanner(plik); skaner.hasNextLine(); ++danyWiersz) {
            String linia = skaner.nextLine();

            for(int i = 0; i < linia.length(); ++i) {
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

   public static int[] odczytajWKonca(String sciezkaDoPliku) {
      int[] k = new int[2];
      int danyWiersz = 0;

      try {
         File plik = new File(sciezkaDoPliku);

         Scanner skaner;
         for(skaner = new Scanner(plik); skaner.hasNextLine(); ++danyWiersz) {
            String linia = skaner.nextLine();

            for(int i = 0; i < linia.length(); ++i) {
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
