package com.labirynt;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.swing.JFileChooser;

/**
 *
 * @author adam
 */
public class ZapisJakoBinarny extends ZapisPliku {

    public ZapisJakoBinarny(Labirynt l) {
        super(l);

    }

    public int obliczCounter() {
        int count = 0;
        int akc = 0;
        char c, t = 'N';

        for (int i = 0; i < l.getIloscWierszy(); i++) {
            for (int j = 0; j < l.getIloscKolumn(); j++) {
                Pole p = l.getZawatosc(i, j);
                c = p.getDane();
                if (c == 'P' || c == 'K') {
                    c = ' ';
                }
                
                if (c != t && j!= 0) {
                    count++;
                    akc = 0;

                } else if (akc  == 256) {
                    count++;
                    akc = 0;
                }
                akc++;
                t = c;

            }
            akc= 0;
            count++;
        }

        return count;

    }

    public int obliczSolOffset() {
        return 40 + (3 * this.obliczCounter());
    }

    public void zapisBin() {
        if (l != null) {
            JFileChooser fc = new JFileChooser();
            int w = fc.showSaveDialog(null);
            if (w == 0) {

                try {
                    DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(fc.getSelectedFile().getAbsolutePath() + ".bin")));
                    dos.write(ByteBuffer.allocate(4).putInt(Integer.reverseBytes(0x52524243)).array());
                    dos.write(0x1B);
                    dos.write(ByteBuffer.allocate(2).putShort(Short.reverseBytes((short) l.getIloscKolumn())).array());
                    dos.write(ByteBuffer.allocate(2).putShort(Short.reverseBytes((short) l.getIloscWierszy())).array());

                    int[] p = l.getPoczatek();
                    int[] k = l.getKoniec();
                    p[0] += 1;
                    p[1] += 1;
                    k[0] += 1;
                    k[1] += 1;

                    dos.write(ByteBuffer.allocate(2).putShort(Short.reverseBytes((short) p[1])).array());
                    dos.write(ByteBuffer.allocate(2).putShort(Short.reverseBytes((short) p[0])).array());
                    dos.write(ByteBuffer.allocate(2).putShort(Short.reverseBytes((short) k[1])).array());
                    dos.write(ByteBuffer.allocate(2).putShort(Short.reverseBytes((short) k[0])).array());
                    dos.write(ByteBuffer.allocate(12).putInt(Integer.reverseBytes(0x000000000000000000000000)).array());
                    dos.write(ByteBuffer.allocate(4).putInt(Integer.reverseBytes(obliczCounter())).array());
                    if (l.czyRozwiazany() == true) {
                        dos.write(ByteBuffer.allocate(4).putInt(Integer.reverseBytes(obliczSolOffset())).array());
                    } else {
                        dos.write(ByteBuffer.allocate(4).putInt(Integer.reverseBytes(0x00000000)).array());
                    }
                    dos.write(0x23);
                    dos.write(0x58);
                    dos.write(0x20);

                    int count = 0;
                    int akc = 0;
                    char c, t;
                    c = 'A';
                    t = 'N';

                    for (int i = 0; i < l.getIloscWierszy(); i++) {
                        for (int j = 0; j < l.getIloscKolumn(); j++) {
                            Pole pol = l.getZawatosc(i, j);
                            c = pol.getDane();
                            if (c == 'P' || c == 'K') {
                                c = ' ';
                            }
                            if (akc == 256) {
                                if (c == ' ') {
                                    dos.write(0x23);
                                    dos.write(0x20);
                                    dos.write(akc - 1);
                                } else if (c == 'X') {
                                    dos.write(0x23);
                                    dos.write(0x58);
                                    dos.write(akc - 1);
                                }
                                akc = 0;
                            } else if (j != 0 && t != c) {
                                if (t == ' ') {
                                    dos.write(0x23);
                                    dos.write(0x20);
                                    dos.write(akc - 1);
                                } else if (t == 'X') {
                                    dos.write(0x23);
                                    dos.write(0x58);
                                    dos.write(akc - 1);
                                }
                                akc = 0;

                            }
                            akc++;

                            t = c;

                        }
                        if (akc != 0) {
                            if (c == ' ') {
                                dos.write(0x23);
                                dos.write(0x20);
                                dos.write(akc - 1);
                            } else if (c == 'X') {
                                dos.write(0x23);
                                dos.write(0x58);
                                dos.write(akc - 1);
                            }
                        }

                        t = 'N';
                        akc = 0;

                    }
                    dos.write(ByteBuffer.allocate(4).putInt(Integer.reverseBytes(0x52524243)).array());
                    if(l.czyRozwiazany() == true){
                        
                        
                        
                    }
                   
                    dos.close();

                } catch (IOException ex) {
                    ex.printStackTrace();

                }
            }
        }
    }
    

}
