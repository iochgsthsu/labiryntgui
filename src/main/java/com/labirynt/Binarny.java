package com.labirynt;

import java.math.BigInteger;

/**
 *
 * @author adam
 */
public class Binarny {

    private int fileid;
    private byte escape;
    private short columns;
    private short lines;
    private short entry_x;
    private short entry_y;
    private short exit_x;
    private short exit_y;
    private byte [] res;
    private int counter;
    private int sollution_off;
    private byte separator;
    private byte wall;
    private byte path;
    private byte [] solveid;
    private byte [] steps;
    

    
    
    
    Binarny(){
        solveid = new byte[4];
        res = new byte[12];
        steps = new byte[2];
        
    }

    public void setFileid(int fileid) {
        this.fileid = fileid;
    }

    public void setEscape(byte escape) {
        this.escape = escape;
    }

    public void setColumns(short columns) {
        this.columns = columns;
    }

    public void setLines(short lines) {
        this.lines = lines;
    }

    public void setEntry_x(short entry_x) {
        this.entry_x = entry_x;
    }

    public void setEntry_y(short entry_y) {
        this.entry_y = entry_y;
    }

    public void setExit_x(short exit_x) {
        this.exit_x = exit_x;
    }

    public void setExit_y(short exit_y) {
        this.exit_y = exit_y;
    }

    public void setRes(byte[] res) {
        this.res = res;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setSollution_off(int sollution_off) {
        this.sollution_off = sollution_off;
    }

    public void setSeparator(byte separator) {
        this.separator = separator;
    }

    public void setWall(byte wall) {
        this.wall = wall;
    }

    public void setPath(byte path) {
        this.path = path;
    }

    public void setSolveid(byte[] solveid) {
        this.solveid = solveid;
    }

    public void setSteps(byte[] steps) {
        this.steps = steps;
    }
    
    

    public int getFileid() {
        return fileid;
    }

    public byte getEscape() {
        return escape;
    }

    public short getColumns() {
        return columns;
    }

    public short getLines() {
        return lines;
    }

    public short getEntry_x() {
        return entry_x;
    }

    public short getEntry_y() {
        return entry_y;
    }

    public short getExit_x() {
        return exit_x;
    }

    public short getExit_y() {
        return exit_y;
    }

    public byte[] getRes() {
        return res;
    }

    public int getCounter() {
        return counter;
    }

    public int getSollution_off() {
        return sollution_off;
    }

    public byte getSeparator() {
        return separator;
    }

    public byte getWall() {
        return wall;
    }

    public byte getPath() {
        return path;
    }

    public byte[] getSolveid() {
        return solveid;
    }

    public byte[] getSteps() {
        return steps;
    }

   
    

    
    
    
    public void printInfo(){
        System.out.println("fileid: "+this.fileid);
        System.out.println("escape: "+ this.escape);
        System.out.println("columns: " +this.columns);
        System.out.println("lines: " +this.lines);
        System.out.println("entry_x: "+this.entry_x);
        System.out.println("entry_y: " + this.entry_y);
        System.out.println("exit_x: " + this.exit_x);
        System.out.println("exit_y: " + this.exit_y);
        System.out.println("counter: " +this.counter);
        System.out.println("sollution_off: " +this.sollution_off);
        System.out.println("separator: " +this.separator);
        System.out.println("wall: " + this.wall);
        System.out.println("path: " + this.path);
        
        
        
    }
    
    
   
    
    
}
