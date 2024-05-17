/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.labirynt;

/**
 *
 * @author adam
 */
public class Pole {
    private char Dane = 'X';
   private int wspolrzednaX;
   private int wspolrzednaY;
   private boolean Odwiedzony;
   private boolean jestSciezka;

   Pole(int wspX, int wspY) {
      this.wspolrzednaX = wspX;
      this.wspolrzednaY = wspY;
      this.Odwiedzony = false;
      this.jestSciezka = false;
   }

   public char getDane() {
      return this.Dane;
   }

   public int getX() {
      return this.wspolrzednaX;
   }

   public int getY() {
      return this.wspolrzednaY;
   }

   public void printDane() {
      System.out.println(this.Dane);
   }

   public void setDane(char Dane) {
      this.Dane = Dane;
   }

   public void Odwiedz() {
      this.Odwiedzony = true;
   }

   public boolean getOdwiedzony() {
      return this.Odwiedzony;
   }

   public void setSciezka() {
      this.jestSciezka = true;
   }

   public boolean getSciezka() {
      return this.jestSciezka;
   }

   public String toString() {
      return "(" + Integer.toString(this.wspolrzednaX) + ", " + Integer.toString(this.wspolrzednaY) + ")";
   }
    
}
