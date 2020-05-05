package com.example.dotsandboxes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import static java.lang.Math.round;


public class MyDrawView extends View {

    public static ArrayList<LinesList> linesLists = new ArrayList<LinesList>();
    public static  int n = 5; // make this accessible for modification
    public static int scoreA ;
    public static int scoreB ;
    float canvasSize ;
    float dotRadius = 20 ;
    float gap ;
    float centreX;
    float centreY;
    float x, y;
    float padding = 10 ;
    Paint blackFill = new Paint();
    Paint redS = new Paint();
    Paint blueS = new Paint();
    Paint redRectS = new Paint();
    Paint blueRectS = new Paint();
    boolean initialTouch = true ;
    boolean drawLine = false ;
    boolean skipOnce = false ; // to avoid the unwanted line


    public MyDrawView(Context context) {
        super(context);

        init(null);
    }

    public MyDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public MyDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set){
        scoreA = 0;
        scoreB = 0;

        blackFill.setColor(Color.BLACK);
        blackFill.setStyle(Paint.Style.FILL);
        blackFill.isAntiAlias();

        redS.setColor(Color.RED);
        redS.isAntiAlias();
        redS.setStyle(Paint.Style.STROKE);
        redS.setStrokeWidth(10);

        redRectS.setColor(Color.RED);
        redRectS.isAntiAlias();
        redS.setStyle(Paint.Style.FILL);

        blueRectS.setColor(Color.BLUE);
        blueRectS.isAntiAlias();
        blueRectS.setStyle(Paint.Style.FILL);

        blueS.setColor(Color.BLUE);
        blueS.isAntiAlias();
        blueS.setStyle(Paint.Style.STROKE);
        blueS.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasSize = canvas.getHeight() ;
        gap = canvasSize/(n+1);
        canvas.drawColor(Color.GRAY);
        //drawTheLine(canvas);
        drawAllLines(canvas, linesLists);
        //canvas.drawRect(centreX, centreY, centreX+30, centreY+30, redS);

        drawDots(canvas);
        postInvalidate();
    }

    private void drawTheLine() {
        if(drawLine){
            if(centreX - x >= gap){
                // draw line to left
             linesLists.add(new LinesList(centreX, centreY, centreX - gap, centreY,
                     checkBox(centreX, centreY, centreX - gap, centreY)));
                commonDrawTheLine();
            }
            else if(-centreX + x >= gap){
                //draw line to right
                linesLists.add(new LinesList(centreX, centreY, centreX + gap, centreY,
                        checkBox(centreX, centreY, centreX + gap, centreY)));
                commonDrawTheLine();
            }
            else if(centreY - y >= gap){
                //draw line to top
                linesLists.add(new LinesList(centreX, centreY, centreX, centreY - gap,
                        checkBox(centreX, centreY, centreX, centreY - gap)));
                commonDrawTheLine();
            }
            else if(-centreY + y >= gap){
                //draw line to bottom

                linesLists.add(new LinesList(centreX, centreY, centreX, centreY + gap ,
                        checkBox(centreX, centreY, centreX, centreY + gap)));
               commonDrawTheLine();
            }

        }

    }

    private int checkBox(float x1 , float y1, float x2, float y2) {
        int dir = 0 ;
        boolean a, b, c, d;

        if(searchIfPresent(linesLists, x1, y1, x2, y2)){ // to avoid redrawing of lines
            return -1;
        }

        if(x1 == x2) {
            a = searchIfPresent(linesLists, x1, y1, x1 + gap, y1);
            b = searchIfPresent(linesLists, x1 + gap, y1, x2 + gap, y2);
            c = searchIfPresent(linesLists, x2, y2, x2 + gap, y2);
            if (a && b && c) {
                // box on right
                //Toast.makeText(getContext(), "right", Toast.LENGTH_SHORT).show();
                dir = 3;
            }
            a = searchIfPresent(linesLists, x1, y1, x1 - gap, y1);
            b = searchIfPresent(linesLists, x1 - gap, y1, x2 - gap, y2);
            c = searchIfPresent(linesLists, x2, y2, x2 - gap, y2);
            if (a && b && c) {
                // box on left
                //Toast.makeText(getContext(), "left", Toast.LENGTH_SHORT).show();
                if (dir == 3) {
                    dir = 39;
                } else {
                    dir = 9;
                }
            }
        }
        else{
            a = searchIfPresent(linesLists, x1, y1, x1, y1-gap);
            b = searchIfPresent(linesLists, x1, y1 - gap, x1+gap, y1-gap);
            d = searchIfPresent(linesLists, x1, y1 - gap, x1-gap, y1-gap);
            c = searchIfPresent(linesLists, x2, y2, x2, y2-gap);
            if (a && (b||d) && c) {
                // box on top
                //Toast.makeText(getContext(), "top", Toast.LENGTH_SHORT).show();
                dir = 12;
            }
            a = searchIfPresent(linesLists, x1, y1, x1, y1 +gap);
            b = searchIfPresent(linesLists, x1, y1+gap, x1+gap, y1+gap);
            d = searchIfPresent(linesLists, x1, y1 + gap, x1-gap, y1+gap);
            c = searchIfPresent(linesLists, x2, y2, x2, y2 +gap);
            if (a && (b||d) && c) {
                // box on bottom
                //Toast.makeText(getContext(), "bottom", Toast.LENGTH_SHORT).show();
                if (dir == 12) {
                    dir = 612;
                }
                else {
                    dir = 6;
                }
            }
        }
        return dir;
    }

    private void commonDrawTheLine (){
        initialTouch = true;
        drawLine = false;
        centreX = 0;
        skipOnce = true;
    }


    private void drawAllLines(Canvas canvas , ArrayList<LinesList> linesList){
        boolean firstPlayerTurn = true;
        int scorea = 0;
        int scoreb = 0;
        if(linesList.size()>1) {

            if (linesList.get(linesList.size() - 1).getDirection() == -1) {
                linesList.remove(linesList.size() - 1); // removes overdrawn line
            }
        }
        for(int i = 0 ; i < linesList.size(); i++){
        canvas.drawLine(linesList.get(i).getX1(), linesList.get(i).getY1(), linesList.get(i).getX2(), linesList.get(i).getY2(), (firstPlayerTurn)? redS : blueS);
        //lines.lineTo(linesList.get(i).getX2() , linesList.get(i).getY2());

        switch (linesList.get(i).getDirection()){
            case 3 : {
                if(linesList.get(i).getY1() > linesList.get(i).getY2()) {
                    canvas.drawRect(linesList.get(i).getX2() + padding, linesList.get(i).getY2() + padding,
                            linesList.get(i).getX2() + gap - padding, linesList.get(i).getY2() + gap - padding,
                            (firstPlayerTurn)? redRectS : blueRectS );
                }
                else {
                    canvas.drawRect(linesList.get(i).getX1() + padding, linesList.get(i).getY1() + padding,
                            linesList.get(i).getX1() + gap - padding, linesList.get(i).getY1() + gap - padding,
                            (firstPlayerTurn)? redRectS : blueRectS );
                }
                if(firstPlayerTurn)
                    scorea++;
                else
                    scoreb++;
                break;
            }
            case 9 : {
                if(linesList.get(i).getY1() > linesList.get(i).getY2()) {
                    canvas.drawRect(linesList.get(i).getX2() - padding, linesList.get(i).getY2() + padding,
                            linesList.get(i).getX2() - gap + padding, linesList.get(i).getY2() + gap - padding,
                            (firstPlayerTurn)? redRectS : blueRectS );
                }
                else {
                    canvas.drawRect(linesList.get(i).getX1() - padding, linesList.get(i).getY1() + padding,
                            linesList.get(i).getX1() - gap + padding, linesList.get(i).getY1() + gap - padding,
                            (firstPlayerTurn)? redRectS : blueRectS );
                }
                if(firstPlayerTurn)
                    scorea++;
                else
                    scoreb++;
                break;
            }
            case 39 : {
                if(linesList.get(i).getY1() > linesList.get(i).getY2()) {
                    canvas.drawRect(linesList.get(i).getX2() + padding, linesList.get(i).getY2() + padding,
                            linesList.get(i).getX2() + gap - padding, linesList.get(i).getY2() + gap - padding,
                            (firstPlayerTurn)? redRectS : blueRectS );
                    canvas.drawRect(linesList.get(i).getX2() - padding, linesList.get(i).getY2() + padding,
                            linesList.get(i).getX2() - gap + padding, linesList.get(i).getY2() + gap - padding,
                            (firstPlayerTurn)? redRectS : blueRectS );
                }
                else {
                    canvas.drawRect(linesList.get(i).getX1() + padding, linesList.get(i).getY1() + padding,
                            linesList.get(i).getX1() + gap - padding, linesList.get(i).getY1() + gap - padding,
                            (firstPlayerTurn)? redRectS : blueRectS );
                    canvas.drawRect(linesList.get(i).getX1() - padding, linesList.get(i).getY1() + padding,
                            linesList.get(i).getX1() - gap + padding, linesList.get(i).getY1() + gap - padding,
                            (firstPlayerTurn)? redRectS : blueRectS );
                }
                if(firstPlayerTurn)
                    scorea+=2;
                else
                    scoreb+=2;
                break;
            }
            case 12:{
                if(linesList.get(i).getX1() > linesList.get(i).getX2()) {
                    canvas.drawRect(linesList.get(i).getX2() + padding, linesList.get(i).getY2() - padding,
                            linesList.get(i).getX2() + gap - padding, linesList.get(i).getY2() - gap + padding,
                            (firstPlayerTurn)? redRectS : blueRectS );
                }
                else {
                    canvas.drawRect(linesList.get(i).getX1() + padding, linesList.get(i).getY1() - padding,
                            linesList.get(i).getX1() + gap - padding, linesList.get(i).getY1() - gap + padding,
                            (firstPlayerTurn)? redRectS : blueRectS );
                }
                if(firstPlayerTurn)
                    scorea++;
                else
                    scoreb++;
                break;
            }
            case 6:{
                if(linesList.get(i).getX1() > linesList.get(i).getX2()) {
                    canvas.drawRect(linesList.get(i).getX2() + padding, linesList.get(i).getY2() + padding,
                            linesList.get(i).getX2() + gap - padding, linesList.get(i).getY2() + gap - padding,
                            (firstPlayerTurn)? redRectS : blueRectS );
                }
                else {
                    canvas.drawRect(linesList.get(i).getX1() + padding, linesList.get(i).getY1() + padding,
                            linesList.get(i).getX1() + gap - padding, linesList.get(i).getY1() + gap - padding,
                            (firstPlayerTurn)? redRectS : blueRectS );
                }
                if(firstPlayerTurn)
                    scorea++;
                else
                    scoreb++;
                break;
            }
            case 612:{
                if(linesList.get(i).getX1() > linesList.get(i).getX2()) {
                    canvas.drawRect(linesList.get(i).getX2() + padding, linesList.get(i).getY2() - padding,
                            linesList.get(i).getX2() + gap - padding, linesList.get(i).getY2() - gap + padding,
                            (firstPlayerTurn)? redRectS : blueRectS );
                    canvas.drawRect(linesList.get(i).getX2() + padding, linesList.get(i).getY2() + padding,
                            linesList.get(i).getX2() + gap - padding, linesList.get(i).getY2() + gap - padding,
                            (firstPlayerTurn)? redRectS : blueRectS );
                }
                else {
                    canvas.drawRect(linesList.get(i).getX1() + padding, linesList.get(i).getY1() - padding,
                            linesList.get(i).getX1() + gap - padding, linesList.get(i).getY1() - gap + padding,
                            (firstPlayerTurn)? redRectS : blueRectS );
                    canvas.drawRect(linesList.get(i).getX1() + padding, linesList.get(i).getY1() + padding,
                            linesList.get(i).getX1() + gap - padding, linesList.get(i).getY1() + gap - padding,
                            (firstPlayerTurn)? redRectS : blueRectS );
                }
                if(firstPlayerTurn)
                    scorea+=2;
                else
                    scoreb+=2;
                break;
            }
            default:{
                break;
            }
        }

            if (firstPlayerTurn){
                firstPlayerTurn = false;
            }
            else{
                firstPlayerTurn = true;
            }

    }
        if(scorea!=scoreA) {
            scoreA = scorea;
            TextView txtView = (TextView) ((MainActivity)getContext()).findViewById(R.id.scoreViewA);
            txtView.setText(""+scoreA);
        }
        if(scoreb!=scoreB) {
            scoreB = scoreb;
            TextView txtView = (TextView) ((MainActivity)getContext()).findViewById(R.id.scoreViewB);
            txtView.setText(""+scoreB);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        switch (event.getAction()){

            case MotionEvent.ACTION_MOVE:{
                x = event.getX();
                y = event.getY();
                if(centreX!=0) {
                    drawTheLine();
                }
                if(initialTouch){
                    if(skipOnce){
                        return true;
                    }
                    centreX = (round((x/gap))*gap);
                    centreY = (round((y/gap))*gap);
                    initialTouch = false;
                    drawLine = true ;
                }

            return true ;
            }

            case MotionEvent.ACTION_DOWN: {
                x = event.getX();
                y = event.getY();
                skipOnce = false;

                //find the closest 2 points and draw a line between them



                return true ;
            }

        }



        return value;

    }

    private void drawDots(Canvas canvas){
        for(int i=1 ; i<= n ; i++){
            for(int j=1 ; j<= n ; j++){
                canvas.drawCircle(i*gap, j*gap , dotRadius, blackFill );
            }
        }
    }


    public boolean searchIfPresent(ArrayList<LinesList> linesLists  ,float x1 , float y1 , float x2 , float y2) {
        for (int i = 0; i < linesLists.size(); i++) {
            if ((linesLists.get(i).getX1() == x1) && (linesLists.get(i).getY1() == y1) &&
                    (linesLists.get(i).getX2() == x2) && (linesLists.get(i).getY2() == y2)) {
                return true;
            }
            if ((linesLists.get(i).getX1() == x2) && (linesLists.get(i).getY1() == y2) &&
                    (linesLists.get(i).getX2() == x1) && (linesLists.get(i).getY2() == y1)) {
                return true;
            }
        }
        return false;
    }
}
