package com.labirynt;

import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;

/**
 *
 * @author adam
 */
public class ZapisJakoTekst extends ZapisPliku{
    
    ZapisJakoTekst(Labirynt la){
        super(la);
        
    }
    
    public void zapisTekst() {
        if (l != null) {
            JFileChooser fc = new JFileChooser();
            int w = fc.showSaveDialog(null);
            if (w == 0) {

                try {
                    FileWriter wri = new FileWriter(fc.getSelectedFile().getAbsolutePath() + ".txt");
                    for (int i = 0; i < l.getIloscWierszy(); i++) {

                        for (int j = 0; j < l.getIloscKolumn(); j++) {
                            Pole p = l.getZawatosc(i, j);
                            if (p.getDane() == 'X') {
                                wri.write("X");
                            } else if (p.getDane() == 'P') {
                                wri.write("P");

                            } else if (p.getDane() == 'K') {
                                wri.write("K");

                            } else if (p.getSciezka()) {
                                wri.write("*");

                            } else {
                                wri.write(" ");

                            }

                        }
                        wri.write("\n");
                    }
                    wri.close();
                } catch (IOException ex) {
                    ex.printStackTrace();

                }
            }
        }
    }
    
}
