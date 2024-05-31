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
                    /*
                    System.out.print((char)sep);
                    System.out.print((char)val);
                    System.out.print(Byte.toUnsignedInt(count));
                    System.out.print(" ");
*/
                    
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

                dis.close();
            } catch (java.io.EOFException eof) {

            }
                
        } catch (IOException ex) {

        }
        return z;

    }

}
