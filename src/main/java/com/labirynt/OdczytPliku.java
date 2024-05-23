/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class OdczytPliku {
    
    protected String sciezkaDoPliku;
    
    OdczytPliku(String sciezka){
        this.sciezkaDoPliku = sciezka;
    }
    
     public void sprawdzFormat() throws IOException {

        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(this.sciezkaDoPliku))));
        String linia;
        while (true) {
            try {
                int zn = (int)dis.readInt();
                System.out.println(zn);
                break;

            } catch (java.io.EOFException eof) {
                System.out.println("koiniec");
                break;
            }
        }

    }
    
}
