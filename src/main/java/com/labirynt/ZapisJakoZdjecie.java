/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.labirynt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

/**
 *
 * @author adam
 */
public class ZapisJakoZdjecie extends ZapisPliku{
    private Rysowanie ry;
    
    ZapisJakoZdjecie(Labirynt la, Rysowanie ry){
        super(la);
        this.ry = ry;
        
    }
    
    
    
    
    public void zapiszZdjecie() {
        if (l != null) {
            JFileChooser fc = new JFileChooser();
            int w = fc.showSaveDialog(null);
            if (w == 0) {

                BufferedImage bi = new BufferedImage(ry.getWielkoscPola() * l.getIloscWierszy(), ry.getWielkoscPola()  * l.getIloscKolumn(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = bi.createGraphics();
                ry.paintAll(g2d);
                try {
                    if (ImageIO.write(bi, "png", new File(fc.getSelectedFile().getAbsolutePath() + ".png"))) {
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();

                }
            }
        }
    }
    
}
