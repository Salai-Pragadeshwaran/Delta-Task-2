package com.example.dotsandboxes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static java.lang.Math.round;


public class MyDrawView extends View {

    ArrayList<LinesList> linesLists = new ArrayList<LinesList>();

    public  static  int n = 5; // make this accessible for modification
    float canvasSize ;
    float dotRadius = 20 ;
    float gap ;
    float centreX;
    float centreY;
    float x, y;
    Paint blackFill = new Paint();
    Paint redS = new Paint();
    boolean initialTouch = true ;
    boolean drawLine = false ;


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
        blackFill.setColor(Color.BLACK);
        blackFill.setStyle(Paint.Style.FILL);
        blackFill.isAntiAlias();

        redS.setColor(Color.RED);
        redS.isAntiAlias();
        redS.setStyle(Paint.Style.STROKE);
        redS.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasSize = canvas.getHeight() ;
        gap = canvasSize/(n+1);
        canvas.drawColor(Color.GRAY);


        drawDots(canvas);

        drawTheLine(canvas);
        drawAllLines(canvas, linesLists);
        //canvas.drawRect(centreX, centreY, centreX+30, centreY+30, redS);

        postInvalidate();
    }

    private void drawTheLine(Canvas canvas) {
        if(drawLine){
            if(centreX - x >= gap){
                // draw line to left
               // canvas.drawLine(centreX, centreY, centreX - gap, centreY, redS);
                linesLists.add(new LinesList(centreX, centreY, centreX - gap, centreY));
               // lines.lineTo(centreX-gap, centreY);
                initialTouch = true;
                drawLine = false;
            }
            else if(-centreX + x >= gap){
                //draw line to right
               // canvas.drawLine(centreX, centreY, centreX + gap, centreY, redS);
                linesLists.add(new LinesList(centreX, centreY, centreX + gap, centreY));
                //lines.lineTo(centreX+gap, centreY);
                initialTouch = true;
                drawLine = false;
            }
            else if(centreY - y >= gap){
                //draw line to top
               // canvas.drawLine(centreX, centreY, centreX, centreY - gap, redS);
                linesLists.add(new LinesList(centreX, centreY, centreX, centreY - gap));
               // lines.lineTo(centreX, centreY - gap);
                initialTouch = true;
                drawLine = false;
            }
            else if(-centreY + y >= gap){
                //draw line to bottom
               // canvas.drawLine(centreX, centreY, centreX, centreY + gap , redS);
                linesLists.add(new LinesList(centreX, centreY, centreX, centreY + gap));
               // lines.lineTo(centreX, centreY + gap);
                initialTouch = true;
                drawLine = false;

            }

        }

    }

    private void drawAllLines(Canvas canvas , ArrayList<LinesList> linesList){
        for(int i = 0 ; i < linesList.size(); i++){
        canvas.drawLine(linesList.get(i).getX1(), linesList.get(i).getY1(), linesList.get(i).getX2(), linesList.get(i).getY2(), redS);
        //lines.lineTo(linesList.get(i).getX2() , linesList.get(i).getY2());

    }


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        switch (event.getAction()){

            case MotionEvent.ACTION_MOVE:{
                x = event.getX();
                y = event.getY();

                if(initialTouch){
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
}
