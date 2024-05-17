/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.labirynt;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Stack;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author adam
 */


public class GUI extends JFrame implements ActionListener {
   JButton buttonOtwPlik;
   JButton buttonPrintLab;
   JButton buttonLabInfo;
   JButton buttonSolve;
   drawLabirynt dl;
   JScrollPane sp;
   String sciezkaPliku;
   int iw;
   int ik;
   Pole[][] zaw;
   Labirynt l;
   int[] pcz;
   int[] kn;

   GUI() {
      JPanel cont = new JPanel();
      cont.setLayout(new BorderLayout());
      JPanel scr = new JPanel();
      scr.setLayout(new BorderLayout());
      JPanel przy = new JPanel();
      BoxLayout bl = new BoxLayout(przy, 1);
      przy.setLayout(bl);
      this.buttonOtwPlik = new JButton("Wybierz plik");
      this.buttonOtwPlik.addActionListener(this);
      
      this.buttonPrintLab = new JButton("Wypisz labirynt");
      this.buttonPrintLab.addActionListener(this);
      
      this.buttonLabInfo = new JButton("Wypisz informacje");
      this.buttonLabInfo.addActionListener(this);
      
      this.buttonSolve = new JButton("Rozwiaz labirynt");
      this.buttonSolve.addActionListener(this);
      
      this.dl = new drawLabirynt(null);
      this.sp = new JScrollPane(this.dl);
      
      this.setTitle("Labirynt");
      this.setSize(1300, 700);
      this.setVisible(true);
      this.setDefaultCloseOperation(3);
      this.setResizable(false);
      
      przy.add(this.buttonOtwPlik);
      przy.add(this.buttonPrintLab);
      przy.add(this.buttonLabInfo);
      przy.add(this.buttonSolve);
      
      scr.add(this.sp, "East");
      
      cont.add(przy, "West");
      cont.add(scr, "East");
      
      this.add(cont);
   }

   public void actionPerformed(ActionEvent ae) {
      if (ae.getSource() == this.buttonOtwPlik) {
         JFileChooser filech = new JFileChooser();
         int w = filech.showOpenDialog(null);
         if (w == 0) {
            File file = new File(filech.getSelectedFile().getAbsolutePath());
            this.sciezkaPliku = file.getAbsolutePath();
            this.iw = OdczytPliku.odczytajIloscWierszy(this.sciezkaPliku);
            this.ik = OdczytPliku.odczytajIloscKolumn(this.sciezkaPliku);
            this.zaw = OdczytPliku.odczytajZawartoscPliku(this.iw, this.ik, this.sciezkaPliku);
            this.pcz = OdczytPliku.odczytajWPoczatku(this.sciezkaPliku);
            this.kn = OdczytPliku.odczytajWKonca(this.sciezkaPliku);
            this.l = new Labirynt(this.iw, this.ik, this.zaw, this.pcz, this.kn);
            this.narysujLabirynt();
         }
      }

      if (ae.getSource() == this.buttonPrintLab) {
         if (this.l != null) {
            this.l.printLabirynt();
         } else {
            System.out.println("Nie wybrano labiyrntu!");
         }
      }

      if (ae.getSource() == this.buttonSolve) {
         if (this.l != null) {
            Stack st = this.l.BFS();
            this.l.getSciezka(st);
            this.dl.setLabirynt(this.l);
            this.narysujLabirynt();
         } else {
            System.out.println("Nie wybrano labiyrntu!");
         }
      }

      if (ae.getSource() == this.buttonLabInfo) {
         if (this.l != null) {
            this.l.getInfo();
         } else {
            System.out.println("Nie wybrano labiyrntu!");
         }
      }

   }

   private void narysujLabirynt() {
      this.dl.setLabirynt(this.l);
      this.dl.revalidate();
      this.dl.repaint();
   }
}
