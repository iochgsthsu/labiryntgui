package com.labirynt;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author adam
 */
public class OdczytPlikuBinarnego extends OdczytPliku {

    OdczytPlikuBinarnego(String sciezkaDoPliku) {
        super(sciezkaDoPliku);
    }

    public Binarny odczytNaglowka() {
        Binarny b = new Binarny();
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(new File(this.sciezkaDoPliku)));

            try {
                b.setFileid(Integer.reverseBytes(dis.readInt()));
                b.setEscape(dis.readByte());
                b.setColumns(Short.reverseBytes(dis.readShort()));
                b.setLines(Short.reverseBytes(dis.readShort()));
                b.setEntry_x(Short.reverseBytes(dis.readShort()));
                b.setEntry_y(Short.reverseBytes(dis.readShort()));
                b.setExit_x(Short.reverseBytes(dis.readShort()));
                b.setExit_y(Short.reverseBytes(dis.readShort()));
                b.setRes(dis.readNBytes(12));
                b.setCounter(Integer.reverseBytes(dis.readInt()));
                b.setSollution_off(Integer.reverseBytes(dis.readInt()));
                b.setSeparator(dis.readByte());
                b.setWall(dis.readByte());
                b.setPath(dis.readByte());
                //b.printInfo();
                dis.close();

            } catch (java.io.EOFException eof) {

            }
        } catch (IOException ex) {
            return null;
        }
        return b;

    }

    public Pole[][] odczytZawartosci(Binarny b) {
        Pole[][] z = new Pole[b.getLines()][b.getColumns()];
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(new File(this.sciezkaDoPliku)));

            try {

                dis.readNBytes(40);
                byte sep;
                byte val;
                byte count;
                int nr_kolumny = 0;
                int nr_wiersza = 0;

                for (int i = 0; i < b.getCounter(); i++) {
                    sep = dis.readByte();
                    val = dis.readByte();
                    count = dis.readByte();

                    for (int j = 0; j < Byte.toUnsignedInt(count) + 1; j++) {

                        if (sep != b.getSeparator()) {
                            return null;
                        }

                        if (nr_kolumny == b.getColumns()) {
                            nr_kolumny = 0;
                            nr_wiersza++;
                        }

                        if (nr_wiersza == b.getEntry_y() - 1 && nr_kolumny == b.getEntry_x() - 1) {
                            z[nr_wiersza][nr_kolumny] = new Pole(nr_wiersza, nr_kolumny);
                            z[nr_wiersza][nr_kolumny].setDane('P');

                        } else if (nr_wiersza == b.getExit_y() - 1 && nr_kolumny == b.getExit_x() - 1) {
                            z[nr_wiersza][nr_kolumny] = new Pole(nr_wiersza, nr_kolumny);
                            z[nr_wiersza][nr_kolumny].setDane('K');

                        } else {
                            if (val == (byte) b.getWall()) {
                                z[nr_wiersza][nr_kolumny] = new Pole(nr_wiersza, nr_kolumny);
                                z[nr_wiersza][nr_kolumny].setDane('X');

                            } else if (val == (byte) b.getPath()) {
                                z[nr_wiersza][nr_kolumny] = new Pole(nr_wiersza, nr_kolumny);
                                z[nr_wiersza][nr_kolumny].setDane(' ');

                            }

                        }
                        nr_kolumny++;

                    }

                }
                if (b.getSollution_off() != 0) {
                    dis.readNBytes(4);
                    b.setSteps(Short.reverseBytes(dis.readShort()));
                    byte dir;
                    byte cou;
                    int pw = b.getEntry_y() - 1;
                    int pk = b.getEntry_x() - 1;
                    z[pw][pk].setSciezka();
                    for (int i = 0; i < b.getSteps(); i++) {
                        dir = dis.readByte();
                        cou = dis.readByte();
                        //0x4E - 'N'
                        //0x45 - 'E'
                        //0x53 - 'S'
                        //0x57 - 'W'

                        if (dir == 0x4E) {
                            for(int j = 0; j<cou+1; j++){
                                pw--;
                                z[pw][pk].setSciezka();
                                
                            }
                            
                            
                        }else if(dir == 0x45){
                            for(int j = 0; j<cou+1; j++){
                                pk++;
                                z[pw][pk].setSciezka();
                                
                            }
                            
                        }else if(dir == 0x53){
                            for(int j = 0; j<cou+1; j++){
                                pw++;
                                z[pw][pk].setSciezka();
                                
                            }
                            
                        }else if(dir == 0x57){
                            for(int j = 0; j<cou+1; j++){
                                pk--;
                                z[pw][pk].setSciezka();
                                
                            }
                            
                        }
                    }

                }

                dis.close();
            } catch (java.io.EOFException eof) {

            }

        } catch (IOException ex) {

        }
        return z;

    }

}
