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

                if (c != t && j != 0) {
                    count++;
                    akc = 0;

                } else if (akc == 256) {
                    count++;
                    akc = 0;
                }
                akc++;
                t = c;

            }
            akc = 0;
            count++;
        }

        return count;

    }

    public int obliczSolOffset() {
        return 40 + (3 * this.obliczCounter());
    }

    public int obliczKroki(String s) {
        char c = 'a';
        char t = 'b';
        int il = 0;
        int akc = 0;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (c != t && i != 0) {
                il++;
                akc = 0;
            } else if (akc == 256) {
                il++;
                akc = 0;
            }

            akc++;
            t = c;
        }
        il++;
        return il;

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

                            //0x23 - '#'
                            //0x20 - ' '
                            //0x58 - 'X'
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
                    if (l.czyRozwiazany() == true) {
                        dos.write(ByteBuffer.allocate(2).putShort(Short.reverseBytes((short) this.obliczKroki(l.getKroki()))).array());
                        count = 0;
                        akc = 0;
                        c = 'a';
                        t = 'a';
                        String kr = l.getKroki();
                        for (int i = 0; i < kr.length(); i++) {
                            c = kr.charAt(i);
                            //0x4E - 'N'
                            //0x45 - 'E'
                            //0x53 - 'S'
                            //0x57 - 'W'

                            if (akc == 256) {
                                if (c == 'G') {
                                    dos.write(0x4E);
                                    dos.write(akc - 1);

                                } else if (c == 'D') {
                                    dos.write(0x53);
                                    dos.write(akc - 1);

                                } else if (c == 'P') {
                                    dos.write(0x45);
                                    dos.write(akc - 1);

                                } else if (c == 'L') {
                                    dos.write(0x57);
                                    dos.write(akc - 1);

                                }
                                akc = 0;
                            } else if (i != 0 && t != c) {
                                if (t == 'G') {
                                    dos.write(0x4E);
                                    dos.write(akc - 1);

                                } else if (t == 'D') {
                                    dos.write(0x53);
                                    dos.write(akc - 1);

                                } else if (t == 'P') {
                                    dos.write(0x45);
                                    dos.write(akc - 1);

                                } else if (t == 'L') {
                                    dos.write(0x57);
                                    dos.write(akc - 1);

                                }
                                akc = 0;

                            }
                            akc++;

                            t = c;

                        }

                        if (akc != 0) {
                            if (c == 'G') {
                                dos.write(0x4E);
                                dos.write(akc - 1);

                            } else if (c == 'D') {
                                dos.write(0x53);
                                dos.write(akc - 1);

                            } else if (c == 'P') {
                                dos.write(0x45);
                                dos.write(akc - 1);

                            } else if (c == 'L') {
                                dos.write(0x57);
                                dos.write(akc - 1);

                            }
                        }

                    }
                    dos.write(ByteBuffer.allocate(4).putInt(Integer.reverseBytes(0x52524243)).array());
                    dos.close();

                } catch (IOException ex) {
                    ex.printStackTrace();

                }
            }
        }
    }

}
