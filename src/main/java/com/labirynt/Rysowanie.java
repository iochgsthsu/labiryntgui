package com.labirynt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
public class Rysowanie extends JComponent implements Scrollable, MouseListener {

    private int wielkoscPola;
    private int wcP;
    private GUI g;
    private Point p;

    private Labirynt l;

    public Rysowanie(Labirynt l, GUI g) {
        this.l = l;
        this.wielkoscPola = 2;
        this.wcP = this.wielkoscPola;
        this.setPreferredSize(new Dimension(100, 100));
        this.g = g;
        addMouseListener(this);
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
        Dimension d = g.getFrameDim();
        return new Dimension((d.width*27)/32, d.height);

        
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

    @Override
    public void mouseClicked(MouseEvent me) {
        
        this.p = me.getPoint();
        g.powiadom(this.getPoint(), this.getWielkosc());
        
          
    } 
    
    
    
    public Point getPoint(){
        return p;
        
    }

    public int getWielkoscPola() {
        return wielkoscPola;
    }
    
    
    
    
    

    @Override
    public void mousePressed(MouseEvent me) {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }
    
    
    
    
    

    public void setWielkosc(int w) {
        this.wielkoscPola = w;
    }

    /*public void zapiszZdjecie() {
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
*/

}
