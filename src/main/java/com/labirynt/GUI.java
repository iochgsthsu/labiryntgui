/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.labirynt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Stack;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author adam
 */
public class GUI extends JFrame implements ActionListener, ChangeListener {

    private JButton buttonOtwPlik;
    private JButton buttonPrintLab;
    private JButton buttonLabInfo;
    private JButton buttonSolve;
    private JButton buttonZapiszZdj;
    private JButton buttonZapiszTekst;
    private JButton buttonZapiszBin;
    private JButton buttonUstawP;
    private JButton buttonUstawK;
    private JLabel labelWiel;
    private JLabel labelZapis;
    private JLabel labelZnaczniki;
    private OdczytPliku op;
    private Rysowanie dl;
    private JScrollPane sp;
    private JSlider slid;
    private String sciezkaPliku;
    private int iw;
    private int ik;
    private Pole[][] zaw;
    private Labirynt l;
    private int[] pcz;
    private int[] kn;
    private boolean czekaNaP;
    private boolean czekaNaK;

    GUI() {

        JPanel cont = new JPanel();
        cont.setLayout(new BorderLayout());
        JPanel scr = new JPanel();
        scr.setLayout(new BorderLayout());
        JPanel przy = new JPanel();
        BoxLayout bl = new BoxLayout(przy, BoxLayout.Y_AXIS);
        BorderLayout br = new BorderLayout();
        przy.setLayout(new GridLayout(15, 5, 3, 10));
        this.buttonOtwPlik = new JButton("Wybierz plik");
        this.buttonOtwPlik.addActionListener(this);

        this.buttonPrintLab = new JButton("Wypisz labirynt");
        this.buttonPrintLab.addActionListener(this);

        this.buttonLabInfo = new JButton("Wypisz informacje");
        this.buttonLabInfo.addActionListener(this);

        this.buttonSolve = new JButton("Rozwiąż labirynt");
        this.buttonSolve.addActionListener(this);

        this.buttonZapiszTekst = new JButton("Zapisz jako plik tekstowy");
        this.buttonZapiszTekst.addActionListener(this);

        this.buttonZapiszZdj = new JButton("Zapisz jako zdjęcie");
        this.buttonZapiszZdj.addActionListener(this);

        this.buttonZapiszBin = new JButton("Zapisz jako plik binarny");

        this.buttonUstawK = new JButton("Ustaw koniec");
        this.buttonUstawK.addActionListener(this);

        this.buttonUstawP = new JButton("Ustaw początek");
        this.buttonUstawP.addActionListener(this);

        this.labelWiel = new JLabel("Wybierz wielkość labiryntu");
        this.labelZapis = new JLabel("Wybierz metodę zapisu labiryntu");
        this.labelZnaczniki = new JLabel("Ustaw znacznik labiryntu");

        slid = new JSlider(3, 27, 15);
        slid.addChangeListener(this);

        this.dl = new Rysowanie(null, this);
        this.sp = new JScrollPane(this.dl);

        this.setTitle("Labirynt");
        this.setSize(1600, 900);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);

        przy.add(this.buttonOtwPlik);
        przy.add(this.buttonSolve);
        przy.add(this.labelZnaczniki);
        przy.add(this.buttonUstawP);
        przy.add(this.buttonUstawK);
        przy.add(this.labelWiel);
        przy.add(this.slid);
        przy.add(this.labelZapis);
        przy.add(this.buttonZapiszTekst);
        przy.add(this.buttonZapiszBin);
        przy.add(this.buttonZapiszZdj);

        scr.add(this.sp, "East");

        cont.add(przy, "West");
        cont.add(scr, "East");

        this.add(cont);

        this.czekaNaK = false;
        this.czekaNaP = false;
    }

    @Override
    public void stateChanged(ChangeEvent ce) {

        dl.setWielkosc(dl.getwcP() * (slid.getValue()));
        this.dl.revalidate();
        this.dl.repaint();

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.buttonOtwPlik) {
            JFileChooser filech = new JFileChooser();
            int w = filech.showOpenDialog(null);
            if (w == 0) {
                File file = new File(filech.getSelectedFile().getAbsolutePath());
                this.sciezkaPliku = file.getAbsolutePath();
                op = new OdczytPliku(this.sciezkaPliku);
                if (op.sprawdzTektowy() == true) {
                    JOptionPane.showMessageDialog(this, "Wczytano plik w formacie tekstowym", "Labirynt: info", JOptionPane.INFORMATION_MESSAGE);
                    this.l = new Labirynt(new OdczytPlikuTekstowego(this.sciezkaPliku));
                } else if (op.sprawdzBinarny() == true) {
                    JOptionPane.showMessageDialog(this, "Wczytano plik w formacie binarnym", "Labirynt: info", JOptionPane.INFORMATION_MESSAGE);
                    this.l = new Labirynt(new OdczytPlikuBinarnego(this.sciezkaPliku));
                } else {
                    JOptionPane.showMessageDialog(this, "Nieprawidłowy format pliku", "Labirynt: info", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (l.getZawartosc() != null) {
                    l.setRozwiazany(false);
                    this.czekaNaK = false;
                    this.czekaNaP = false;
                    dl.setWielkosc(slid.getValue() * dl.getwcP());
                    this.narysujLabirynt();
                } else {
                    JOptionPane.showMessageDialog(this, "Błąd wczytywania pliku, prawdopodobnie uszkodzony", "Labirynt: info", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (ae.getSource() == this.buttonPrintLab) {
            if (this.l != null) {
                this.l.printLabirynt();
            } else {
                JOptionPane.showMessageDialog(this, "Nie wybrano labiryntu!", "Labirynt: błąd", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (ae.getSource() == this.buttonSolve) {
            if (this.l == null) {
                JOptionPane.showMessageDialog(this, "Nie wybrano labiryntu!", "Labirynt: błąd", JOptionPane.ERROR_MESSAGE);
            } else if (l.czyRozwiazany() == true) {
                JOptionPane.showMessageDialog(this, "Labirynt już został rozwiązany!", "Labirynt: info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Stack st = this.l.BFS();
                if(st!=null){
                    this.l.getSciezka(st);
                this.dl.setLabirynt(this.l);
                this.narysujLabirynt();
                    
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Brak rozwiązania danego labiryntu", "Labirynt: info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        if (ae.getSource() == this.buttonLabInfo) {
            if (this.l != null) {
                this.l.getInfo();
            } else {
                JOptionPane.showMessageDialog(this, "Nie wybrano labiryntu!", "Labirynt: błąd", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (ae.getSource() == this.buttonZapiszZdj) {
            if (l != null) {
                dl.zapiszZdjecie();
            } else {
                JOptionPane.showMessageDialog(this, "Nie wybrano labiryntu!", "Labirynt: błąd", JOptionPane.ERROR_MESSAGE);
            }

        }

        if (ae.getSource() == this.buttonZapiszTekst) {
            if (l != null) {
                l.zapisTekst();
            } else {
                JOptionPane.showMessageDialog(this, "Nie wybrano labiryntu!", "Labirynt: błąd", JOptionPane.ERROR_MESSAGE);
            }

        }

        if (ae.getSource() == this.buttonUstawP) {
            if (l != null) {
                this.czekaNaP = true;
                this.czekaNaK = false;
            } else {
                JOptionPane.showMessageDialog(this, "Nie wybrano labiryntu!", "Labirynt: błąd", JOptionPane.ERROR_MESSAGE);
            }

        }

        if (ae.getSource() == this.buttonUstawK) {
            if (l != null) {
                this.czekaNaP = false;
                this.czekaNaK = true;
            } else {
                JOptionPane.showMessageDialog(this, "Nie wybrano labiryntu!", "Labirynt: błąd", JOptionPane.ERROR_MESSAGE);
            }

        }

    }

    private void narysujLabirynt() {
        this.dl.setLabirynt(this.l);
        this.dl.revalidate();
        this.dl.repaint();
    }

    public void powiadom(Point p, int wielkosc) {
        if (l != null) {
            if (l.getIloscWierszy() * wielkosc <= p.y || l.getIloscKolumn() * wielkosc <= p.x) {
                return;
            }
            if (this.czekaNaP == true) {
                this.czekaNaP = false;
                int nr_wiersza = p.y / wielkosc;
                int nr_kolumny = p.x / wielkosc;
                l.ustawNowyPoczatek(nr_wiersza, nr_kolumny);
                this.narysujLabirynt();

            } else if (this.czekaNaK == true) {
                this.czekaNaK = false;
                int nr_wiersza = p.y / wielkosc;
                int nr_kolumny = p.x / wielkosc;
                l.ustawNowyKoniec(nr_wiersza, nr_kolumny);
                this.narysujLabirynt();

            }

        }
    }

    public Dimension getFrameDim() {
        return this.getContentPane().getSize();
    }
}
