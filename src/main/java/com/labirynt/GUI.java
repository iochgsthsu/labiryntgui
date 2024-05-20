/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.labirynt;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Stack;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author adam
 */


public class GUI extends JFrame implements ActionListener {
   private JButton buttonOtwPlik;
   private JButton buttonPrintLab;
   private JButton buttonLabInfo;
   private JButton buttonSolve;
   private JLabel labelWiel;
   private JComboBox cbWielkosc;
   private Rysowanie dl;
   private JScrollPane sp;
   private String sciezkaPliku;
   private int iw;
   private int ik;
   private Pole[][] zaw;
   private Labirynt l;
   private int[] pcz;
   private int[] kn;

   GUI() {
      
      JPanel cont = new JPanel();
      cont.setLayout(new BorderLayout());
      JPanel scr = new JPanel();
      scr.setLayout(new BorderLayout());
      JPanel przy = new JPanel();
      BoxLayout bl = new BoxLayout(przy, BoxLayout.Y_AXIS);
      BorderLayout br = new BorderLayout();
      przy.setLayout(new GridLayout(20, 20, 10,5));
      this.buttonOtwPlik = new JButton("Wybierz plik");
      this.buttonOtwPlik.addActionListener(this);
      
      this.buttonPrintLab = new JButton("Wypisz labirynt");
      this.buttonPrintLab.addActionListener(this);
      
      this.buttonLabInfo = new JButton("Wypisz informacje");
      this.buttonLabInfo.addActionListener(this);
      
      this.buttonSolve = new JButton("Rozwiaz labirynt");
      this.buttonSolve.addActionListener(this);
      
      this.labelWiel = new JLabel("Wybierz wielkość pola");
      
      String [] s ={ "25%", "50%", "75%", "100%", "125%", "150%", "175%", "200%", "300%"};
      this.cbWielkosc = new JComboBox(s);
      this.cbWielkosc.setSelectedIndex(3);
      this.cbWielkosc.addActionListener(this);
      
      this.dl = new Rysowanie(null);
      this.sp = new JScrollPane(this.dl);
      
      this.setTitle("Labirynt");
      this.setSize(1300, 700);
      this.setVisible(true);
      this.setDefaultCloseOperation(3);
      this.setResizable(false);
      
      przy.add(this.buttonOtwPlik);
      //przy.add(this.buttonPrintLab);
      //przy.add(this.buttonLabInfo);
      przy.add(this.buttonSolve);
      przy.add(this.labelWiel);
      przy.add(this.cbWielkosc);
      
      scr.add(this.sp, "East");
      
      cont.add(przy, "West");
      cont.add(scr, "East");
      
      this.add(cont);
   }
   @Override
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
            l.setRozwiazany(false);
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
         if (this.l == null) {
            System.out.println("Nie wybrano labiyrntu!");
         }
         else if(l.czyRozwiazany() == true){
             System.out.println("Labirynt juz rozwiazany");
         }
         else {
            Stack st = this.l.BFS();
            this.l.getSciezka(st);
            this.dl.setLabirynt(this.l);
            this.narysujLabirynt();
         }
      }

      if (ae.getSource() == this.buttonLabInfo) {
         if (this.l != null) {
            this.l.getInfo();
         } else {
            System.out.println("Nie wybrano labiyrntu!");
         }
      }
      
      if(ae.getSource() == this.cbWielkosc){
          int w = dl.getwcP();
          switch((String)cbWielkosc.getSelectedItem()){
              case "25%":{
                  dl.setWielkosc(w/4);
                  break;
              }
              case "50%":{
                  dl.setWielkosc(w/2);
                  break;
              }
              case "75%":{
                  dl.setWielkosc((w*3)/4);
                  break;
              }
              case "100%":{
                  dl.setWielkosc(w);
                  break;
              }
              case "125%":{
                  dl.setWielkosc((w*5)/4);
                  break;
              }
              case "150%":{
                  dl.setWielkosc((w*6)/4);
                  break;
              }
              case "175%":{
                  dl.setWielkosc((w*8)/4);
                  break;
              }
              case "200%":{
                  dl.setWielkosc(2*w);
                  break;
              }
              case "300%":{
                  dl.setWielkosc(3*w);
                  break;
              }
   
          }
          this.dl.revalidate();
          this.dl.repaint();
      }

   }

   private void narysujLabirynt() {
      this.dl.setLabirynt(this.l);
      this.dl.revalidate();
      this.dl.repaint();
   }
}
