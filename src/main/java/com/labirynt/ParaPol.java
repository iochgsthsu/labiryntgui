/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.labirynt;

/**
 *
 * @author adam
 */
public class ParaPol {
   private Pole p1;
   private Pole p2;

   ParaPol(Pole p1, Pole p2) {
      this.p1 = p1;
      this.p2 = p2;
   }

   public void setPola(Pole p1, Pole p2) {
      this.p1 = p1;
      this.p2 = p2;
   }

   public Pole getP1() {
      return this.p1;
   }

   public Pole getP2() {
      return this.p2;
   }

   public String toString() {

      return this.p1.toString() + ", " + this.p2.toString();
   }
    
}
