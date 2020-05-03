package com.example.dotsandboxes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


public class MyDrawView extends View {
    public  static  int n = 3; // make this accessible for modification
    float canvasSize ;
    float dotRadius = 20 ;
    float gap ;
    Paint blackFill = new Paint();


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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasSize = canvas.getHeight() ;
        gap = canvasSize/(n+1);
        canvas.drawColor(Color.GRAY);


        drawDots(canvas);


        postInvalidate();
    }


    private void drawDots(Canvas canvas){
        for(int i=1 ; i<= n ; i++){
            for(int j=1 ; j<= n ; j++){
                canvas.drawCircle(i*gap, j*gap , dotRadius, blackFill );
            }
        }
    }
}
