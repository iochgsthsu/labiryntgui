/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.labirynt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.Scrollable;

/**
 *
 * @author adam
 */
public class Rysowanie extends JComponent implements Scrollable {

    private int wielkoscPola;
    private int wcP;

    private Labirynt l;

    public Rysowanie(Labirynt l) {
        this.l = l;
        this.wielkoscPola = 20;
        this.wcP = this.wielkoscPola;
        this.setPreferredSize(new Dimension(100, 100));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (this.l != null) {
            this.getPreferredSize();

            for (int i = 0; i < this.l.getIloscWierszy(); ++i) {
                for (int j = 0; j < this.l.getIloscKolumn(); ++j) {
                    Pole p = this.l.getZawatosc(i, j);
                    Color c;
                    if (p.getDane() == 'X') {
                        c = Color.BLACK;
                    } else if (p.getDane() == 'P') {
                        c = Color.BLUE;
                    } else if (p.getDane() == 'K') {
                        c = Color.GREEN;
                    } else if (p.getSciezka()) {
                        c = Color.RED;
                    } else {
                        c = Color.WHITE;
                    }

                    g2d.setColor(c);
                    g2d.fillRect(this.wielkoscPola * j, this.wielkoscPola * i, this.wielkoscPola, this.wielkoscPola);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(this.wielkoscPola * j, this.wielkoscPola * i, this.wielkoscPola, this.wielkoscPola);
                }
            }
        }

    }

    public Dimension getPreferredSize() {
        return this.l != null ? new Dimension(this.l.getIloscWierszy() * this.wielkoscPola, this.l.getIloscKolumn() * this.wielkoscPola) : new Dimension(100, 100);
    }

    public void setLabirynt(Labirynt l) {
        this.l = l;
    }

    public int getWielkosc() {

        return this.wielkoscPola;
    }

    public int getwcP() {
        return this.wcP;
    }

    public Dimension getPreferredScrollableViewportSize() {
        return new Dimension(1024, 512);
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 128;
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 128;
    }

    public boolean getScrollableTracksViewportWidth() {
        return this.getPreferredSize().width <= this.getParent().getSize().width;
    }

    public boolean getScrollableTracksViewportHeight() {
        return this.getPreferredSize().height <= this.getParent().getSize().height;
    }

    public void setWielkosc(int w) {
        this.wielkoscPola = w;
    }

    public void zapiszZdjecie() {
        if (l != null) {
            JFileChooser fc = new JFileChooser();
            int w = fc.showSaveDialog(null);
            if (w == 0) {

                BufferedImage bi = new BufferedImage(this.wielkoscPola * l.getIloscWierszy(), this.wielkoscPola * l.getIloscKolumn(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = bi.createGraphics();
                this.paintAll(g2d);
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
