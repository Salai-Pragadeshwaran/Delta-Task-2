package com.example.dotsandboxes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

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
    Paint dotsPaint = new Paint();
    Paint lineA = new Paint();
    Paint lineB = new Paint();
    Paint boxA = new Paint();
    Paint boxB = new Paint();
    boolean initialTouch = true ;
    boolean drawLine = false ;
    boolean skipOnce = false ; // to avoid the unwanted line
    MediaPlayer clickSound = MediaPlayer.create(getContext(), R.raw.click2);


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

    @SuppressLint("ResourceAsColor")
    private void init(@Nullable AttributeSet set){
        scoreA = 0;
        scoreB = 0;

        dotsPaint.setColor(getResources().getColor(R.color.dots));
        dotsPaint.setStyle(Paint.Style.FILL);
        dotsPaint.isAntiAlias();

        lineA.setColor(getResources().getColor(R.color.lineA));
        lineA.isAntiAlias();
        lineA.setStyle(Paint.Style.STROKE);
        lineA.setStrokeWidth(10);

        boxA.setColor(getResources().getColor(R.color.boxA));
        boxA.isAntiAlias();
        lineA.setStyle(Paint.Style.FILL);

        boxB.setColor(getResources().getColor(R.color.boxB));
        boxB.isAntiAlias();
        boxB.setStyle(Paint.Style.FILL);

        lineB.setColor(getResources().getColor(R.color.lineB));
        lineB.isAntiAlias();
        lineB.setStyle(Paint.Style.STROKE);
        lineB.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasSize = canvas.getHeight() ;
        gap = round(canvasSize/(n+1));
        canvas.drawColor(getResources().getColor(R.color.canvasBg));
        //drawTheLine(canvas);
        drawAllLines(canvas, linesLists);
        //canvas.drawRect(centreX, centreY, centreX+30, centreY+30, lineA);

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
        if(checkLineBorder(x1, y1, x2, y2)){
            return -1;
        }

        clickSound.start();
        if(x1 == x2) {
            float yy1 = (y1<y2)? y1 : y2;
            float xx1 = (yy1==y1)? x1 : x2;
            a = searchIfPresent(linesLists, xx1, yy1, xx1 + gap, yy1);
            b = searchIfPresent(linesLists, xx1 + gap, yy1, xx1 + gap, yy1 +gap);
            c = searchIfPresent(linesLists, xx1 + gap, yy1 +gap, x1, yy1 +gap);
            if (a && b && c) {
                // box on right
                //Toast.makeText(getContext(), "right", Toast.LENGTH_SHORT).show();
                dir = 3;
            }
            a = searchIfPresent(linesLists, xx1, yy1, xx1 - gap, yy1);
            b = searchIfPresent(linesLists, xx1 - gap, yy1, xx1 - gap, yy1+gap);
            c = searchIfPresent(linesLists, xx1 - gap, yy1 + gap, xx1, yy1 +gap );
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
            float xx1 = (x1<x2)? x1 : x2;
            float yy1 = (xx1==x1)? y1 : y2;
            a = searchIfPresent(linesLists, xx1, yy1, xx1, yy1-gap);
            b = searchIfPresent(linesLists, xx1, yy1 - gap, xx1+gap, yy1-gap);
            //d = searchIfPresent(linesLists, x1, y1 - gap, x1-gap, y1-gap);
            c = searchIfPresent(linesLists, xx1 + gap, yy1 - gap, xx1 + gap, yy1);
            if (a && b && c) {
                // box on top
                //Toast.makeText(getContext(), "top", Toast.LENGTH_SHORT).show();
                dir = 12;
            }
            a = searchIfPresent(linesLists, xx1, yy1, xx1, yy1 +gap);
            b = searchIfPresent(linesLists, xx1, yy1+gap, xx1+gap, yy1+gap);
           // d = searchIfPresent(linesLists, x1, y1 + gap, x1-gap, y1+gap);
            c = searchIfPresent(linesLists, xx1+gap, yy1 + gap, xx1 + gap, yy1);
            if (a && b && c) {
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
        if(linesList.size()>=1) {

            if (linesList.get(linesList.size() - 1).getDirection() == -1) {
                linesList.remove(linesList.size() - 1); // removes overdrawn line
            }
        }
        for(int i = 0 ; i < linesList.size(); i++){
        canvas.drawLine(linesList.get(i).getX1(), linesList.get(i).getY1(), linesList.get(i).getX2(), linesList.get(i).getY2(), (firstPlayerTurn)? lineA : lineB);
        //lines.lineTo(linesList.get(i).getX2() , linesList.get(i).getY2());

        switch (linesList.get(i).getDirection()){
            case 3 : {
                if(linesList.get(i).getY1() > linesList.get(i).getY2()) {
                    canvas.drawRect(linesList.get(i).getX2() + padding, linesList.get(i).getY2() + padding,
                            linesList.get(i).getX2() + gap - padding, linesList.get(i).getY2() + gap - padding,
                            (firstPlayerTurn)? boxA : boxB);
                }
                else {
                    canvas.drawRect(linesList.get(i).getX1() + padding, linesList.get(i).getY1() + padding,
                            linesList.get(i).getX1() + gap - padding, linesList.get(i).getY1() + gap - padding,
                            (firstPlayerTurn)? boxA : boxB);
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
                            (firstPlayerTurn)? boxA : boxB);
                }
                else {
                    canvas.drawRect(linesList.get(i).getX1() - padding, linesList.get(i).getY1() + padding,
                            linesList.get(i).getX1() - gap + padding, linesList.get(i).getY1() + gap - padding,
                            (firstPlayerTurn)? boxA : boxB);
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
                            (firstPlayerTurn)? boxA : boxB);
                    canvas.drawRect(linesList.get(i).getX2() - padding, linesList.get(i).getY2() + padding,
                            linesList.get(i).getX2() - gap + padding, linesList.get(i).getY2() + gap - padding,
                            (firstPlayerTurn)? boxA : boxB);
                }
                else {
                    canvas.drawRect(linesList.get(i).getX1() + padding, linesList.get(i).getY1() + padding,
                            linesList.get(i).getX1() + gap - padding, linesList.get(i).getY1() + gap - padding,
                            (firstPlayerTurn)? boxA : boxB);
                    canvas.drawRect(linesList.get(i).getX1() - padding, linesList.get(i).getY1() + padding,
                            linesList.get(i).getX1() - gap + padding, linesList.get(i).getY1() + gap - padding,
                            (firstPlayerTurn)? boxA : boxB);
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
                            (firstPlayerTurn)? boxA : boxB);
                }
                else {
                    canvas.drawRect(linesList.get(i).getX1() + padding, linesList.get(i).getY1() - padding,
                            linesList.get(i).getX1() + gap - padding, linesList.get(i).getY1() - gap + padding,
                            (firstPlayerTurn)? boxA : boxB);
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
                            (firstPlayerTurn)? boxA : boxB);
                }
                else {
                    canvas.drawRect(linesList.get(i).getX1() + padding, linesList.get(i).getY1() + padding,
                            linesList.get(i).getX1() + gap - padding, linesList.get(i).getY1() + gap - padding,
                            (firstPlayerTurn)? boxA : boxB);
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
                            (firstPlayerTurn)? boxA : boxB);
                    canvas.drawRect(linesList.get(i).getX2() + padding, linesList.get(i).getY2() + padding,
                            linesList.get(i).getX2() + gap - padding, linesList.get(i).getY2() + gap - padding,
                            (firstPlayerTurn)? boxA : boxB);
                }
                else {
                    canvas.drawRect(linesList.get(i).getX1() + padding, linesList.get(i).getY1() - padding,
                            linesList.get(i).getX1() + gap - padding, linesList.get(i).getY1() - gap + padding,
                            (firstPlayerTurn)? boxA : boxB);
                    canvas.drawRect(linesList.get(i).getX1() + padding, linesList.get(i).getY1() + padding,
                            linesList.get(i).getX1() + gap - padding, linesList.get(i).getY1() + gap - padding,
                            (firstPlayerTurn)? boxA : boxB);
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
            if (linesList.get(i).getDirection() == 0) {
                if (firstPlayerTurn) {
                    firstPlayerTurn = false;
                } else {
                    firstPlayerTurn = true;
                }
            }
    }
        if(scorea!=scoreA) {
            if(scorea>scoreA){
                vibrate();
            }
            scoreA = scorea;
            TextView txtView = (TextView) ((MainActivity)getContext()).findViewById(R.id.scoreViewA);
            txtView.setText(""+scoreA);
        }
        if(scoreb!=scoreB) {
            if(scoreb>scoreB){
                vibrate();
            }
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
                canvas.drawCircle(i*gap, j*gap , dotRadius, dotsPaint);
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

    public boolean checkLineBorder(float x1 , float y1 , float x2 , float y2){
        if((x1==0)||(x2==0)||(y1==0)||(y2==0)||(x1==((n+1)*gap))||(x2==((n+1)*gap))||(y1==((n+1)*gap))||(y2==((n+1)*gap))){
            return true;
        }
        else{
            return false;
        }
    }

    public void vibrate(){
        Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(200);
        }
    }

}
