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
    private static Labirynt ins = null;
    private int iloscWierszy;
    private int iloscKolumn;
    private int[] Poczatek;
    private int[] Koniec;
    private Pole[][] Zawartosc;
    private boolean Rozwiazany;
    private boolean Format;
    private String kroki = "";

    private Labirynt(int iloscWierszy, int iloscKolumn, Pole[][] Zawartosc, int[] Poczatek, int[] Koniec) {
        this.iloscWierszy = iloscWierszy;
        this.iloscKolumn = iloscKolumn;
        this.Zawartosc = Zawartosc;
        this.Poczatek = Poczatek;
        this.Koniec = Koniec;
        this.Rozwiazany = false;
    }

    private Labirynt(OdczytPlikuTekstowego opt) {
        this.iloscWierszy = opt.odczytajIloscWierszy();
        this.iloscKolumn = opt.odczytajIloscKolumn();
        this.Zawartosc = opt.odczytajZawartoscPliku(this.iloscWierszy, this.iloscKolumn);
        this.Poczatek = opt.odczytajWPoczatku();
        this.Koniec = opt.odczytajWKonca();
        this.Rozwiazany = opt.sprawdzRozwiazanie(iloscWierszy, iloscKolumn);

    }

    private Labirynt(OdczytPlikuBinarnego opb) {
        Binarny b = opb.odczytNaglowka();
        this.iloscWierszy = b.getLines();
        this.iloscKolumn = b.getColumns();
        this.Zawartosc = opb.odczytZawartosci(b);
        this.Poczatek = new int[2];
        this.Poczatek[0] = b.getEntry_y() - 1;
        this.Poczatek[1] = b.getEntry_x() - 1;
        this.Koniec = new int[2];
        this.Koniec[0] = b.getExit_y() - 1;
        this.Koniec[1] = b.getExit_x() - 1;
        if (b.getSollution_off() != 0) {
            this.Rozwiazany = true;
        } else {
            this.Rozwiazany = false;
        }
    }
    
    
    public static Labirynt getInstance(OdczytPlikuBinarnego opb){
        if(ins == null){
            ins = new Labirynt(opb);
        }else{
            zamienDane(opb);
        }
        
        return ins;
        
    }
    
    public static Labirynt getInstance(OdczytPlikuTekstowego opt){
        if(ins == null){
            ins =  new Labirynt(opt);
        }else{
            zamienDane(opt);
        }
        
        return ins;
        
    }
    
    private static void zamienDane(OdczytPlikuBinarnego opb){
        Binarny b = opb.odczytNaglowka();
        ins.iloscWierszy = b.getLines();
        ins.iloscKolumn = b.getColumns();
        ins.Zawartosc = opb.odczytZawartosci(b);
        ins.Poczatek = new int[2];
        ins.Poczatek[0] = b.getEntry_y() - 1;
        ins.Poczatek[1] = b.getEntry_x() - 1;
        ins.Koniec = new int[2];
        ins.Koniec[0] = b.getExit_y() - 1;
        ins.Koniec[1] = b.getExit_x() - 1;
        if (b.getSollution_off() != 0) {
            ins.Rozwiazany = true;
        } else {
            ins.Rozwiazany = false;
        }
        
    }
    
    private static void zamienDane(OdczytPlikuTekstowego opt){
        ins.iloscWierszy = opt.odczytajIloscWierszy();
        ins.iloscKolumn = opt.odczytajIloscKolumn();
        ins.Zawartosc = opt.odczytajZawartoscPliku(ins.iloscWierszy, ins.iloscKolumn);
        ins.Poczatek = opt.odczytajWPoczatku();
        ins.Koniec = opt.odczytajWKonca();
        ins.Rozwiazany = opt.sprawdzRozwiazanie(ins.iloscWierszy, ins.iloscKolumn);
        
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

    public Pole[][] getZawartosc() {
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

    public int[] getKoniec() {
        return this.Koniec;
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

    public String krokiRozwiazania() {
        String kro = "";
        if (this.Rozwiazany == true) {

            int row = 0, col = 0;
            int x = this.Poczatek[0];
            int y = this.Poczatek[1];
            Pole p = this.getPole(x, y);
            char c = p.getDane();
            while (c != 'K') {
                p = this.getPole(x, y);
                c = p.getDane();
                for (int i = 0; i < 4; i++) {
                    if (i == 0) {
                        row = x - 1;
                        col = y;

                    } else if (i == 1) {
                        row = x;
                        col = y + 1;

                    } else if (i == 2) {
                        row = x + 1;
                        col = y;

                    } else if (i == 3) {
                        row = x;
                        col = y - 1;
                    }
                    if (row >= 0 && col >= 0 && row <= this.getIloscWierszy() - 1 && col <= this.getIloscKolumn() - 1) {
                        Pole ps = this.getPole(row, col);
                        if (ps.getSciezka() == true && ps.getOdwiedzony() == false) {
                            if (i == 0) {
                                kro += "G";

                            } else if (i == 1) {
                                kro += 'P';

                            } else if (i == 2) {
                                kro += 'D';

                            } else if (i == 3) {
                               kro += 'L';
                            }
                            x = row;
                            y = col;
                            this.Zawartosc[row][col].setOdwiedzony(true);
                            break;
                            
                        }
                    }

                }

            }
        }
        return kro;
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

                if (r >= 0 && c >= 0 && r <= this.getIloscWierszy() - 1 && c <= this.getIloscKolumn() - 1) {
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

    public String getSciezka(Stack s) {
        int[] pl = this.getPoczatek();
        int dx, dy;
        String kroki = "";
        char krok = 'X';
        ParaPol pp = (ParaPol) s.pop();
        Pole obec = pp.getP1(), poprz = pp.getP2();

        while (!s.isEmpty()) {
            obec = pp.getP1();
            poprz = pp.getP2();
            dx = obec.getX() - poprz.getX();
            dy = obec.getY() - poprz.getY();
            if (dx == 1) {
                krok = 'G';
            } else if (dx == -1) {
                krok = 'D';
            } else if (dy == 1) {
                krok = 'L';
            } else if (dy == -1) {
                krok = 'P';
            }
            kroki += krok;
            this.jestSciezka(poprz.getX(), poprz.getY());

            while (poprz.getX() != obec.getX() || poprz.getY() != obec.getY()) {
                if (!s.isEmpty()) {
                    pp = (ParaPol) s.pop();
                    poprz = pp.getP2();

                } else {
                    break;
                }

            }

            obec = pp.getP1();
            if (!s.isEmpty()) {
                this.jestSciezka(poprz.getX(), poprz.getY());
                this.jestSciezka(obec.getX(), obec.getY());
            }

        }

        dx = obec.getX() - poprz.getX();
        dy = obec.getY() - poprz.getY();
        if (obec.getX() == this.Poczatek[0] && obec.getY() == this.Poczatek[1] && poprz.getSciezka()) {
            if (dx == 1) {
                krok = 'G';
            } else if (dx == -1) {
                krok = 'D';
            } else if (dy == 1) {
                krok = 'L';
            } else if (dy == -1) {
                krok = 'P';
            }
            kroki += krok;
        }

        StringBuffer sb = new StringBuffer(kroki);
        sb.reverse();
        return sb.toString();

    }

    public boolean ustawNowyPoczatek(int w, int k) {
        Pole p = this.getZawatosc(w, k);
        if (p.getDane() == 'X' || p.getDane() == 'K') {
            return false;
        } else {
            wyczyscRozwiazanie();
            this.setZawartosc(Poczatek[0], Poczatek[1], ' ');
            this.Poczatek[0] = w;
            this.Poczatek[1] = k;
            this.setZawartosc(w, k, 'P');
            return true;
        }

    }

    public boolean ustawNowyKoniec(int w, int k) {
        Pole p = this.getZawatosc(w, k);
        if (p.getDane() == 'X' || p.getDane() == 'P') {
            return false;

        } else {
            wyczyscRozwiazanie();
            this.setZawartosc(Koniec[0], Koniec[1], ' ');
            this.Koniec[0] = w;
            this.Koniec[1] = k;
            this.setZawartosc(w, k, 'K');
            return true;
        }

    }

    private void wyczyscRozwiazanie() {
        for (int i = 0; i < this.getIloscWierszy(); i++) {
            for (int j = 0; j < this.getIloscWierszy(); j++) {
                if (Zawartosc[i][j].getDane() == '*') {
                    Zawartosc[i][j].setDane(' ');
                }
                this.Zawartosc[i][j].setSciezka(false);
                this.Zawartosc[i][j].setOdwiedzony(false);
            }
        }
        this.kroki = "";
        this.Rozwiazany = false;
    }

    public void setKroki(String s) {
        this.kroki = s;
    }

    public String getKroki() {
        return this.kroki;
    }

}
