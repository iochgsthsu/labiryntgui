package com.labirynt;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import javax.swing.JFileChooser;

/**
 *
 * @author adam
 */
public class Labirynt {

    private int iloscWierszy;
    private int iloscKolumn;
    private int[] Poczatek;
    private int[] Koniec;
    private Pole[][] Zawartosc;
    private boolean Rozwiazany;
    private boolean Format;

    Labirynt(int iloscWierszy, int iloscKolumn, Pole[][] Zawartosc, int[] Poczatek, int[] Koniec) {
        this.iloscWierszy = iloscWierszy;
        this.iloscKolumn = iloscKolumn;
        this.Zawartosc = Zawartosc;
        this.Poczatek = Poczatek;
        this.Koniec = Koniec;
        this.Rozwiazany = false;
    }
    
    Labirynt(OdczytPlikuTekstowego opt){
        this.iloscWierszy = opt.odczytajIloscWierszy();
        this.iloscKolumn = opt.odczytajIloscKolumn();
        this.Zawartosc = opt.odczytajZawartoscPliku(this.iloscWierszy, this.iloscKolumn);
        this.Poczatek = opt.odczytajWPoczatku();
        this.Koniec = opt.odczytajWKonca();
        this.Rozwiazany = opt.sprawdzRozwiazanie(iloscWierszy, iloscKolumn);
      
    }
    
    Labirynt(OdczytPlikuBinarnego opb){
        Binarny b = opb.odczytNaglowka();
        this.iloscWierszy = b.getLines();
        this.iloscKolumn = b.getColumns();
        this.Zawartosc = opb.odczytZawartosci(b);
        this.Poczatek = new int[2];
        this.Poczatek[0] = b.getEntry_y() -1;
        this.Poczatek[1] = b.getEntry_x() -1;
        this.Koniec = new int[2];
        this.Koniec[0] = b.getExit_y() -1;
        this.Koniec[1] = b.getExit_x() -1;
        this.Rozwiazany = false;
    }

    public void printLabirynt() {
        for (int i = 0; i < this.iloscWierszy; ++i) {
            for (int j = 0; j < this.iloscKolumn; ++j) {
                System.out.print(this.Zawartosc[i][j].getDane());
            }

            System.out.print('\n');
        }

    }

    public int getIloscKolumn() {
        return this.iloscKolumn;
    }

    public int getIloscWierszy() {
        return this.iloscWierszy;
    }

    public void setZawartosc(int x, int y, char c) {
        this.Zawartosc[x][y].setDane(c);
    }

    public Pole getZawatosc(int x, int y) {
        return this.Zawartosc[x][y];
    }
    
    public Pole[][] getZawartosc(){
        return this.Zawartosc;
    }

    public void setOdwiedzony(int x, int y) {
        this.Zawartosc[x][y].Odwiedz();
    }

    public void setPoczatek(int x, int y) {
        this.Poczatek[0] = x;
        this.Poczatek[1] = y;
    }

    public void setKoniec(int x, int y) {
        this.Koniec[0] = x;
        this.Koniec[1] = y;
    }

    public void setRozwiazany(boolean b) {
        this.Rozwiazany = b;
    }

    public void getInfo() {
        if (this != null) {
            System.out.println("Ilość kolumn: " + Integer.toString(this.iloscKolumn));
            System.out.println("Ilość wierszy: " + Integer.toString(this.iloscWierszy));
            System.out.println("Poczatek: (" + Integer.toString(this.Poczatek[0]) + ", " + Integer.toString(this.Poczatek[1]) + ")");
            System.out.println("Koniec: (" + Integer.toString(this.Koniec[0]) + ", " + Integer.toString(this.Koniec[1]) + ")");
        } else {
            System.out.println("Nie wczytano labiryntu");
        }

    }

    public int[] getPoczatek() {
        return this.Poczatek;
    }

    public Pole getPole(int x, int y) {
        return this.Zawartosc[x][y];
    }

    public void jestSciezka(int x, int y) {
        this.Zawartosc[x][y].setSciezka();
    }

    public boolean czyRozwiazany() {
        return this.Rozwiazany;
    }

    public Stack BFS() {
        int r = 0;
        int c = 0;
        new ParaPol(new Pole(0, 0), new Pole(0, 0));
        Queue q = new LinkedList();
        Stack s = new Stack();
        int[] Pocz = this.getPoczatek();
        Pole p = this.getPole(Pocz[0], Pocz[1]);
        this.setOdwiedzony(Pocz[0], Pocz[1]);
        q.add(p);

        while (!q.isEmpty()) {
            p = (Pole) q.remove();

            for (int i = 0; i < 4; ++i) {
                if (i == 0) {
                    r = p.getX() - 1;
                    c = p.getY();
                }

                if (i == 1) {
                    r = p.getX();
                    c = p.getY() + 1;
                }

                if (i == 2) {
                    r = p.getX() + 1;
                    c = p.getY();
                }

                if (i == 3) {
                    r = p.getX();
                    c = p.getY() - 1;
                }

                if (r >= 0 && c >= 0 && r<=this.getIloscWierszy()-1 && c<=this.getIloscKolumn()-1) {
                    Pole p_tmp = this.getPole(r, c);
                    if (!p_tmp.getOdwiedzony()) {
                        char t = p_tmp.getDane();
                        if (t == ' ' || t == 'K') {
                            ParaPol pp = new ParaPol(p, p_tmp);
                            s.add(pp);
                            this.setOdwiedzony(r, c);
                            q.add(p_tmp);
                            if (t == 'K') {
                                this.setRozwiazany(true);
                                return s;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    public void getSciezka(Stack s) {
        int[] pl = this.getPoczatek();
        ParaPol pp = (ParaPol) s.pop();
        Pole obec,poprz;

        while (!s.isEmpty()) {
            obec = pp.getP1();
            poprz = pp.getP2();
            this.jestSciezka(poprz.getX(), poprz.getY());

            while (poprz.getX() != obec.getX() || poprz.getY() != obec.getY()) {
                if(!s.isEmpty()){
                    pp = (ParaPol) s.pop();
                    poprz = pp.getP2();
                    
                }
                else
                {
                    break;
                }
              
            }

            obec = pp.getP1();
            this.jestSciezka(poprz.getX(), poprz.getY());
            //this.jestSciezka(obec.getX(), obec.getY());
        }
        
    }
    
    public void ustawNowyPoczatek(int w, int k){
        wyczyscRozwiazanie();
        Pole pp = this.getZawatosc(Poczatek[0], Poczatek[1]);
        char c = pp.getZamiana();
        if(c == 'N'){
            this.setZawartosc(pp.getX(), pp.getY(), 'X');
        }
        else
        {
            this.setZawartosc(pp.getX(), pp.getY(), c);
        }

        this.Poczatek[0] = w;
        this.Poczatek[1] = k;
       
        this.Zawartosc[w][k].setZamiana(this.Zawartosc[w][k].getDane());
 
        
        this.setZawartosc(w, k,'P');

        
    }
    
    public void ustawNowyKoniec(int w, int k){
        wyczyscRozwiazanie();
        Pole pp = this.getZawatosc(Koniec[0], Koniec[1]);
        char c = pp.getZamiana();
        if(c == 'N'){
            this.setZawartosc(pp.getX(), pp.getY(), 'X');
        }
        else
        {
            this.setZawartosc(pp.getX(), pp.getY(), c);
        }
        
        
        this.Koniec[0] = w;
        this.Koniec[1] = k;
        this.Zawartosc[w][k].setZamiana(this.Zawartosc[w][k].getDane());
        this.setZawartosc(w, k, 'K');
        
    }
    
    private void wyczyscRozwiazanie(){
        for(int i = 0; i<this.getIloscWierszy(); i++){
            for(int j = 0 ;j<this.getIloscWierszy(); j++){
                if(Zawartosc[i][j].getDane() == '*')
                {
                    Zawartosc[i][j].setDane(' ');
                }
                this.Zawartosc[i][j].setSciezka(false);
                this.Zawartosc[i][j].setOdwiedzony(false);
            }
        }
        this.Rozwiazany = false;
    }
    

}
