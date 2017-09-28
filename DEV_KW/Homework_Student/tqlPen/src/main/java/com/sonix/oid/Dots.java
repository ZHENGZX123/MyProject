package com.sonix.oid;

/**
 * Created by Administrator on 2017/9/21.
 */

public class Dots {
    public Dots(int SectionID, int OwnerID, int BookID, int PageID, float x, float y, int f, int t, int width, int color, int counter, int angle) {
        this.SectionID = SectionID;
        this.OwnerID = OwnerID;
        this.BookID = BookID;
        this.PageID = PageID;
        this.pointX = x;
        this.pointY = y;
        this.force = f;
        this.ntype = t;
        this.penWidth = width;
        this.ncolor = color;
        this.ncounter = counter;
        this.nangle = angle;
    }

    public int id;
    public int SectionID;
    public int OwnerID;
    public int BookID;
    public int PageID;
    public int ncounter;
    public float pointX;
    public float pointY;
    public int force;
    public int ntype;  //0-down;1-move;2-up;
    public int penWidth;
    public int ncolor;
    public int nangle;

    public Dots() {

    }

    @Override
    public String toString() {
        return "Dots{" +
                "id=" + id +
                ", SectionID=" + SectionID +
                ", OwnerID=" + OwnerID +
                ", BookID=" + BookID +
                ", PageID=" + PageID +
                ", ncounter=" + ncounter +
                ", pointX=" + pointX +
                ", pointY=" + pointY +
                ", force=" + force +
                ", ntype=" + ntype +
                ", penWidth=" + penWidth +
                ", ncolor=" + ncolor +
                ", nangle=" + nangle +
                '}';
    }
}