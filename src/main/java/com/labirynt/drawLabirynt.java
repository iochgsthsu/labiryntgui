/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.labirynt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.Scrollable;


/**
 *
 * @author adam
 */
public class drawLabirynt extends JComponent implements Scrollable {
    
   private Labirynt l;

   public drawLabirynt(Labirynt l) {
      this.l = l;
      this.setPreferredSize(new Dimension(100, 100));
   }

   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D)g;
      if (this.l != null) {
         this.getPreferredSize();
         g2d.setFont(new Font("Arial", 1, 15));

         for(int i = 0; i < this.l.getIloscWierszy(); ++i) {
            for(int j = 0; j < this.l.getIloscKolumn(); ++j) {
               Pole p = this.l.getZawatosc(i, j);
               Color c;
               if (p.getDane() == 'X') {
                  c = Color.BLACK;
               } else if (p.getSciezka()) {
                  c = Color.RED;
               } else {
                  if (p.getDane() == 'P') {
                     c = Color.WHITE;
                     g2d.drawString("P", 30 * j + 15, 30 * i + 15);
                     continue;
                  }

                  if (p.getDane() == 'K') {
                     c = Color.WHITE;
                     g2d.drawString("K", 30 * j + 15, 30 * i + 15);
                     continue;
                  }

                  c = Color.WHITE;
               }

               g2d.setColor(c);
               g2d.fillRect(30 * j, 30 * i, 30, 30);
               g2d.setColor(Color.BLACK);
               g2d.drawRect(30 * j, 30 * i, 30, 30);
            }
         }
      }

   }

   public Dimension getPreferredSize() {
      return this.l != null ? new Dimension(this.l.getIloscWierszy() * 30, this.l.getIloscKolumn() * 30) : new Dimension(100, 100);
   }

   public void setLabirynt(Labirynt l) {
      this.l = l;
   }

   public Dimension getPreferredScrollableViewportSize() {
      return new Dimension(1048, 512);
   }

   public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
      return 128;
   }

   public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
      return 128;
   }

   public boolean getScrollableTracksViewportWidth() {
      return this.getPreferredSize().width <= this.getParent().getSize().width;
   }

   public boolean getScrollableTracksViewportHeight() {
      return this.getPreferredSize().height <= this.getParent().getSize().height;
   }
    
    
    
}
