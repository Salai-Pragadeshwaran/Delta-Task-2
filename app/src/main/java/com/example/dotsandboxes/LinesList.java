package com.example.dotsandboxes;

import java.util.ArrayList;

public class LinesList {

    private float x1 ;
    private float y1 ;
    private float x2 ;
    private float y2 ;
    private int direction;


    public LinesList(float initX , float initY, float finalX, float finalY, int dir){
        x1 = initX;
        y1 = initY;
        x2 = finalX;
        y2 = finalY;
        direction = dir;
    }


    public float getX1(){return x1;}
    public float getX2(){return x2;}
    public float getY1(){return y1;}
    public float getY2(){return y2;}
    public int getDirection(){return direction;}




}
