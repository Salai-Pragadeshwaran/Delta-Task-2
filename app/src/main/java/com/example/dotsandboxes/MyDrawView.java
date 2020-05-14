package com.example.dotsandboxes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.round;


public class MyDrawView extends View {

    public static ArrayList<LinesList> linesLists = new ArrayList<LinesList>();
    public static ArrayList<LinesList> availableOptions = new ArrayList<>();
    public static  int n = 5; // make this accessible for modification
    public static int playerNumber = 2;
    public static Boolean singlePlayerMode = false;
    public static int scoreA ;
    public static int scoreB ;
    public static int scoreC ;
    public static int scoreD ;
    public static int turnAnim ;
    public static int difficultyLevel = 2 ;
    int computerStreak = 0;
    int index, turnMax;
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
    Paint lineC = new Paint();
    Paint lineD = new Paint();
    Paint highlightLine = new Paint();
    Paint boxA = new Paint();
    Paint boxB = new Paint();
    Paint boxC = new Paint();
    Paint boxD = new Paint();
    public static boolean continueStreak = false;
    int computerStreak2 = 0;
    boolean initialTouch = true ;
    boolean drawLine = false ;
    boolean skipOnce = false ; // to avoid the unwanted line
    public static boolean gameOn ;
    public static boolean chainsFormed = true;
    MediaPlayer clickSound = MediaPlayer.create(getContext(), R.raw.click2);
    MediaPlayer boxSound = MediaPlayer.create(getContext(), R.raw.coin);
    MediaPlayer gameOverSound = MediaPlayer.create(getContext(), R.raw.powerup);
    CountDownTimer turnNotMadeTimer = new CountDownTimer(500000, 5000) {
        @Override
        public void onTick(long millisUntilFinished) {

            bounceAnim(millisUntilFinished);
        }

        @Override
        public void onFinish() {
            turnNotMadeTimer.start();
        }
    };

    Intent intent = new Intent(getContext(), WinnerActivity.class);
    CountDownTimer intentTimer = new CountDownTimer(2500, 1250) {
        @Override
        public void onTick(long millisUntilFinished) {
            //do nothing
        }

        @Override
        public void onFinish() {
            intentTimerRunning = false;
            getContext().startActivity(intent);
            linesLists.clear();
        }
    };
    boolean intentTimerRunning = false;


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
        turnAnim = 0;
        turnNotMadeTimer.start();
        gameOn = true;


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

        lineB.setColor(getResources().getColor(R.color.lineB));
        lineB.isAntiAlias();
        lineB.setStyle(Paint.Style.STROKE);
        lineB.setStrokeWidth(10);

        boxB.setColor(getResources().getColor(R.color.boxB));
        boxB.isAntiAlias();
        boxB.setStyle(Paint.Style.FILL);


            scoreC =0;
            lineC.setColor(getResources().getColor(R.color.lineC));
            lineC.isAntiAlias();
            lineC.setStyle(Paint.Style.STROKE);
            lineC.setStrokeWidth(10);

            boxC.setColor(getResources().getColor(R.color.boxC));
            boxC.isAntiAlias();
            boxC.setStyle(Paint.Style.FILL);


            scoreD = 0;
            lineD.setColor(getResources().getColor(R.color.lineD));
            lineD.isAntiAlias();
            lineD.setStyle(Paint.Style.STROKE);
            lineD.setStrokeWidth(10);

        highlightLine.setColor(getResources().getColor(R.color.greenHighlight));
        highlightLine.isAntiAlias();
        highlightLine.setStyle(Paint.Style.STROKE);
        highlightLine.setStrokeWidth(10);

            boxD.setColor(getResources().getColor(R.color.boxD));
            boxD.isAntiAlias();
            boxD.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(singlePlayerMode){
        if (gameOn && (!intentTimerRunning)) {
            canvasSize = canvas.getHeight();
            gap = round(canvasSize / (n + 1));
            if(availableOptions.size()==0){
                initializeAvailableOptions();
            }
            canvas.drawColor(getResources().getColor(R.color.canvasBg));
            drawAllLines(canvas, linesLists);
            drawDots(canvas);
            checkIfGameOn();
        } else if (!gameOn) {
            gameOn = true;
            displayWinner();
        } else {
            canvasSize = canvas.getHeight();
            gap = round(canvasSize / (n + 1));
            canvas.drawColor(getResources().getColor(R.color.canvasBg));
            drawAllLines(canvas, linesLists);

            drawDots(canvas);
        }
        }
        else {
            if (gameOn && (!intentTimerRunning)) {
                canvasSize = canvas.getHeight();
                gap = round(canvasSize / (n + 1));
                canvas.drawColor(getResources().getColor(R.color.canvasBg));
                drawAllLines(canvas, linesLists);

                drawDots(canvas);
                checkIfGameOn();
            } else if (!gameOn) {
                gameOn = true;
                displayWinner();
            } else {
                canvasSize = canvas.getHeight();
                gap = round(canvasSize / (n + 1));
                canvas.drawColor(getResources().getColor(R.color.canvasBg));
                drawAllLines(canvas, linesLists);

                drawDots(canvas);
            }
        }
        postInvalidate();
    }


    private void displayWinner() {

        if((scoreA>scoreB)&&(scoreA>scoreC)&&(scoreA>scoreD)){
            //A wins
            intent.putExtra("WIN_MSG", "Congratulations\nPlayer A\nYou Win !");
        }
        else if((scoreB>scoreA)&&(scoreB>scoreC)&&(scoreB>scoreD)){
            //B wins
            if(singlePlayerMode){
                intent.putExtra("WIN_MSG", "Computer Wins !");
            }
            else {
                intent.putExtra("WIN_MSG", "Congratulations\nPlayer B\nYou Win !");
            }
        }
        else if((scoreC>scoreB)&&(scoreC>scoreA)&&(scoreC>scoreD)){
            //C wins
            intent.putExtra("WIN_MSG", "Congratulations\nPlayer C\nYou Win !");
        }
        else if((scoreD>scoreB)&&(scoreD>scoreC)&&(scoreD>scoreA)){
            //D wins
            intent.putExtra("WIN_MSG", "Congratulations\nPlayer D\nYou Win !");
        }
        else{
            intent.putExtra("WIN_MSG", "Match Tie");
        }

        intentTimer.start();
        gameOverSound.start();
        intentTimerRunning = true;

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
        boolean a, b, c;

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
                dir = 3;
            }
            a = searchIfPresent(linesLists, xx1, yy1, xx1 - gap, yy1);
            b = searchIfPresent(linesLists, xx1 - gap, yy1, xx1 - gap, yy1+gap);
            c = searchIfPresent(linesLists, xx1 - gap, yy1 + gap, xx1, yy1 +gap );
            if (a && b && c) {
                // box on left
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
            c = searchIfPresent(linesLists, xx1 + gap, yy1 - gap, xx1 + gap, yy1);
            if (a && b && c) {
                // box on top
                dir = 12;
            }
            a = searchIfPresent(linesLists, xx1, yy1, xx1, yy1 +gap);
            b = searchIfPresent(linesLists, xx1, yy1+gap, xx1+gap, yy1+gap);
            c = searchIfPresent(linesLists, xx1+gap, yy1 + gap, xx1 + gap, yy1);
            if (a && b && c) {
                // box on bottom
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
        computerStreak2 = 0;
        if(singlePlayerMode){
            makeComputerMove();
        }
    }


    private void drawAllLines(Canvas canvas , ArrayList<LinesList> linesList){
        int scorea = 0;
        int scoreb = 0;
        int scorec = 0;
        int scored = 0;
        int turn = 0 ;
        if(linesList.size()>=1) {

            if (linesList.get(linesList.size() - 1).getDirection() == -1) {
                linesList.remove(linesList.size() - 1); // removes overdrawn line
            }
        }
        for(int i = 0 ; i < linesList.size(); i++){
        canvas.drawLine(linesList.get(i).getX1(), linesList.get(i).getY1(), linesList.get(i).getX2(), linesList.get(i).getY2(), linePaint(turn));
        turn++;

        switch (linesList.get(i).getDirection()){
            case 3 : {
                turn--;
                if(linesList.get(i).getY1() > linesList.get(i).getY2()) {
                    canvas.drawRect(linesList.get(i).getX2() + padding, linesList.get(i).getY2() + padding,
                            linesList.get(i).getX2() + gap - padding, linesList.get(i).getY2() + gap - padding,
                            boxPaint(turn));
                }
                else {
                    canvas.drawRect(linesList.get(i).getX1() + padding, linesList.get(i).getY1() + padding,
                            linesList.get(i).getX1() + gap - padding, linesList.get(i).getY1() + gap - padding,
                            boxPaint(turn));
                }
                switch (turn%playerNumber){
                    case 0:{
                        scorea++;
                        break;
                    }
                    case 1:{
                        scoreb++;
                        break;
                    }
                    case 2:{
                        scorec++;
                        break;
                    }
                    case 3:{
                        scored++;
                        break;
                    }
                }

                break;
            }
            case 9 : {
                turn--;
                if(linesList.get(i).getY1() > linesList.get(i).getY2()) {
                    canvas.drawRect(linesList.get(i).getX2() - padding, linesList.get(i).getY2() + padding,
                            linesList.get(i).getX2() - gap + padding, linesList.get(i).getY2() + gap - padding,
                            boxPaint(turn));
                }
                else {
                    canvas.drawRect(linesList.get(i).getX1() - padding, linesList.get(i).getY1() + padding,
                            linesList.get(i).getX1() - gap + padding, linesList.get(i).getY1() + gap - padding,
                            boxPaint(turn));
                }
                switch (turn%playerNumber){
                    case 0:{
                        scorea++;
                        break;
                    }
                    case 1:{
                        scoreb++;
                        break;
                    }
                    case 2:{
                        scorec++;
                        break;
                    }
                    case 3:{
                        scored++;
                        break;
                    }
                }
                break;
            }
            case 39 : {
                turn--;
                if(linesList.get(i).getY1() > linesList.get(i).getY2()) {
                    canvas.drawRect(linesList.get(i).getX2() + padding, linesList.get(i).getY2() + padding,
                            linesList.get(i).getX2() + gap - padding, linesList.get(i).getY2() + gap - padding,
                            boxPaint(turn));
                    canvas.drawRect(linesList.get(i).getX2() - padding, linesList.get(i).getY2() + padding,
                            linesList.get(i).getX2() - gap + padding, linesList.get(i).getY2() + gap - padding,
                            boxPaint(turn));
                }
                else {
                    canvas.drawRect(linesList.get(i).getX1() + padding, linesList.get(i).getY1() + padding,
                            linesList.get(i).getX1() + gap - padding, linesList.get(i).getY1() + gap - padding,
                            boxPaint(turn));
                    canvas.drawRect(linesList.get(i).getX1() - padding, linesList.get(i).getY1() + padding,
                            linesList.get(i).getX1() - gap + padding, linesList.get(i).getY1() + gap - padding,
                            boxPaint(turn));
                }
                switch (turn%playerNumber){
                    case 0:{
                        scorea+=2;
                        break;
                    }
                    case 1:{
                        scoreb+=2;
                        break;
                    }
                    case 2:{
                        scorec+=2;
                        break;
                    }
                    case 3:{
                        scored+=2;
                        break;
                    }
                }
                break;
            }
            case 12:{
                turn--;
                if(linesList.get(i).getX1() > linesList.get(i).getX2()) {
                    canvas.drawRect(linesList.get(i).getX2() + padding, linesList.get(i).getY2() - padding,
                            linesList.get(i).getX2() + gap - padding, linesList.get(i).getY2() - gap + padding,
                            boxPaint(turn));
                }
                else {
                    canvas.drawRect(linesList.get(i).getX1() + padding, linesList.get(i).getY1() - padding,
                            linesList.get(i).getX1() + gap - padding, linesList.get(i).getY1() - gap + padding,
                            boxPaint(turn));
                }
                switch (turn%playerNumber){
                    case 0:{
                        scorea++;
                        break;
                    }
                    case 1:{
                        scoreb++;
                        break;
                    }
                    case 2:{
                        scorec++;
                        break;
                    }
                    case 3:{
                        scored++;
                        break;
                    }
                }
                break;
            }
            case 6:{
                turn--;
                if(linesList.get(i).getX1() > linesList.get(i).getX2()) {
                    canvas.drawRect(linesList.get(i).getX2() + padding, linesList.get(i).getY2() + padding,
                            linesList.get(i).getX2() + gap - padding, linesList.get(i).getY2() + gap - padding,
                            boxPaint(turn));
                }
                else {
                    canvas.drawRect(linesList.get(i).getX1() + padding, linesList.get(i).getY1() + padding,
                            linesList.get(i).getX1() + gap - padding, linesList.get(i).getY1() + gap - padding,
                            boxPaint(turn));
                }
                switch (turn%playerNumber){
                    case 0:{
                        scorea++;
                        break;
                    }
                    case 1:{
                        scoreb++;
                        break;
                    }
                    case 2:{
                        scorec++;
                        break;
                    }
                    case 3:{
                        scored++;
                        break;
                    }
                }
                break;
            }
            case 612:{
                turn--;
                if(linesList.get(i).getX1() > linesList.get(i).getX2()) {
                    canvas.drawRect(linesList.get(i).getX2() + padding, linesList.get(i).getY2() - padding,
                            linesList.get(i).getX2() + gap - padding, linesList.get(i).getY2() - gap + padding,
                            boxPaint(turn));
                    canvas.drawRect(linesList.get(i).getX2() + padding, linesList.get(i).getY2() + padding,
                            linesList.get(i).getX2() + gap - padding, linesList.get(i).getY2() + gap - padding,
                            boxPaint(turn));
                }
                else {
                    canvas.drawRect(linesList.get(i).getX1() + padding, linesList.get(i).getY1() - padding,
                            linesList.get(i).getX1() + gap - padding, linesList.get(i).getY1() - gap + padding,
                            boxPaint(turn));
                    canvas.drawRect(linesList.get(i).getX1() + padding, linesList.get(i).getY1() + padding,
                            linesList.get(i).getX1() + gap - padding, linesList.get(i).getY1() + gap - padding,
                            boxPaint(turn));
                }
                switch (turn%playerNumber){
                    case 0:{
                        scorea+=2;
                        break;
                    }
                    case 1:{
                        scoreb+=2;
                        break;
                    }
                    case 2:{
                        scorec+=2;
                        break;
                    }
                    case 3:{
                        scored+=2;
                        break;
                    }
                }
                break;
            }
            default:{
                break;
            }
        }

    }
        if(scorea!=scoreA) {
            if(scorea>scoreA){
                boxSound.start();
                vibrate();
            }
            scoreA = scorea;
            TextView txtView = (TextView) ((MainActivity)getContext()).findViewById(R.id.scoreViewA);
            txtView.setText(""+scoreA);
        }
        if(scoreb!=scoreB) {
            if(scoreb>scoreB){
                boxSound.start();
                vibrate();
            }
            scoreB = scoreb;
            TextView txtView = (TextView) ((MainActivity)getContext()).findViewById(R.id.scoreViewB);
            txtView.setText(""+scoreB);
        }
        if(playerNumber>2) {
            if (scorec != scoreC) {
                if (scorec > scoreC) {
                    boxSound.start();
                    vibrate();
                }
                scoreC = scorec;
                TextView txtView = (TextView) ((MainActivity) getContext()).findViewById(R.id.scoreViewC);
                txtView.setText("" + scoreC);
            }
        }
        if(playerNumber>3) {
            if (scored != scoreD) {
                if (scored > scoreD) {
                    boxSound.start();
                    vibrate();
                }
                scoreD = scored;
                TextView txtView = (TextView) ((MainActivity) getContext()).findViewById(R.id.scoreViewD);
                txtView.setText("" + scoreD);
            }
        }
        if(turn!=turnAnim){
            turnAnim = turn;
            turnNotMadeTimer.cancel();
            turnNotMadeTimer.start();
        }

        if((singlePlayerMode&&((turn%2)==1))&&(availableOptions.size()!=0)){
            if(linesList.size()<(2*(n)*(n-1))) {
                drawComputerLine();
            }
        }

        if(turn>turnMax) {
            turnMax = turn;
        }
        if(linesList.size()==0){
            turnMax = 0;
        }
    }

    private void checkIfGameOn(){
        if(linesLists.size() == (2*(n)*(n-1))){
            gameOn = false;
            turnNotMadeTimer.cancel();
        }
    }

    private void bounceAnim(long muf) {
        if(muf<499900){
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);

        switch(turnAnim%playerNumber){
            case 0:{
                ImageView player = ((MainActivity) getContext()).findViewById(R.id.playerA);
                player.startAnimation(animation);
                break;
            }
            case 1:{
                ImageView player = ((MainActivity) getContext()).findViewById(R.id.playerB);
                player.startAnimation(animation);
                break;
            }
            case 2:{
                ImageView player = ((MainActivity) getContext()).findViewById(R.id.playerC);
                player.startAnimation(animation);
                break;
            }
            case 3:{
                ImageView player = ((MainActivity) getContext()).findViewById(R.id.playerD);
                player.startAnimation(animation);
                break;
            }
        }}
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

    public Paint linePaint(int turn){
        if(((turn+1)==turnMax)&&(singlePlayerMode)){
            return highlightLine;
        }
        switch(turn%playerNumber){
            case 0: {
                return lineA;
            }
            case 1: {
                return lineB;
            }
            case 2: {
                return  lineC;
            }
            case 3: {
                return lineD;
            }
        }
        return dotsPaint;
    }

    public Paint boxPaint(int turn){
        switch(turn%playerNumber){
            case 0: {
                return boxA;
            }
            case 1: {
                return boxB;
            }
            case 2: {
                return  boxC;
            }
            case 3: {
                return boxD;
            }
        }
        return dotsPaint;
    }

// SINGLE MODE SINGLE MODE SINGLE MODE // SINGLE MODE SINGLE MODE SINGLE MODE  // SINGLE MODE SINGLE MODE SINGLE MODE  // SINGLE MODE SINGLE MODE SINGLE MODE

    private void initializeAvailableOptions() {
            for(int i=1; i<=n; i++){
                for(int j=1; j<n; j++){
                    availableOptions.add(new LinesList(j*gap, i*gap, (j+1)*gap, i*gap, 0));
                }
            }
            for(int i=1; i<=n; i++){
                for(int j=1; j<n; j++){
                    availableOptions.add(new LinesList(i*gap, j*gap, i*gap, (j+1)*gap, 0));
                }
            }

    }


    public int searchIfPresentInAvailableOptions(ArrayList<LinesList> lists  , LinesList line) {
        float x1 = line.getX1();
        float x2 = line.getX2();
        float y1 = line.getY1();
        float y2 = line.getY2();
        for (int i = 0; i < lists.size(); i++) {
            if ((lists.get(i).getX1() == x1) && (lists.get(i).getY1() == y1) &&
                    (lists.get(i).getX2() == x2) && (lists.get(i).getY2() == y2)) {
                return i;
            }
            if ((lists.get(i).getX1() == x2) && (lists.get(i).getY1() == y2) &&
                    (lists.get(i).getX2() == x1) && (lists.get(i).getY2() == y1)) {
                return i;
            }
        }
        return -1 ;
    }


    private void makeComputerMove() {
    index = searchIfPresentInAvailableOptions(availableOptions, linesLists.get(linesLists.size()-1));
    if(index!=-1) {
        availableOptions.remove(index);
    }
    }


    private int checkIf3rdLine( ArrayList temporary){
        float xx1 ;
        float yy1 ;
        float xx2 ;
        float yy2 ;
        boolean a, b;
        outer:
        for(int i ;availableOptions.size()>0 ; avoidThirdLine(temporary)) {
            xx1 = availableOptions.get(index).getX1();
            yy1 = availableOptions.get(index).getY1();
            xx2 = availableOptions.get(index).getX2();
            yy2 = availableOptions.get(index).getY2();
            if (yy1 == yy2) {
                a = searchIfPresent(linesLists, xx1, yy1, xx1, yy1 - gap);
                b = searchIfPresent(linesLists, xx2, yy2, xx2, yy2 - gap);
                if (a && b) {
                    continue outer;
                }

                a = searchIfPresent(linesLists, xx1, yy1, xx1, yy1 + gap);
                b = searchIfPresent(linesLists, xx2, yy2, xx2, yy2 + gap);
                if (a && b) {
                    continue outer;
                }

                if (xx1 < xx2) {
                    a = searchIfPresent(linesLists, xx1, yy1, xx1, yy1 + gap);
                    b = searchIfPresent(linesLists, xx1, yy1 + gap, xx1 + gap, yy1 + gap);
                    if (a && b) {
                        continue outer;
                    }

                    a = searchIfPresent(linesLists, xx1, yy1, xx1, yy1 - gap);
                    b = searchIfPresent(linesLists, xx1, yy1 - gap, xx1 + gap, yy1 - gap);
                    if (a && b) {
                        continue outer;
                    }


                    a = searchIfPresent(linesLists, xx2, yy2, xx2, yy2 - gap);
                    b = searchIfPresent(linesLists, xx2, yy2 - gap, xx2 - gap, yy2 - gap);
                    if (a && b) {
                        continue outer;
                    }


                    a = searchIfPresent(linesLists, xx2, yy2, xx2, yy2 + gap);
                    b = searchIfPresent(linesLists, xx2, yy2 + gap, xx2 - gap, yy2 + gap);
                    if (a && b) {
                        continue outer;
                    }

                } else {
                    float change;
                    change = xx1;
                    xx1 = xx2;
                    xx2 = change;
                    change = yy1;
                    yy1 = yy2;
                    yy2 = change;

                    a = searchIfPresent(linesLists, xx1, yy1, xx1, yy1 + gap);
                    b = searchIfPresent(linesLists, xx1, yy1 + gap, xx1 + gap, yy1 + gap);
                    if (a && b) {
                        continue outer;
                    }


                    a = searchIfPresent(linesLists, xx1, yy1, xx1, yy1 - gap);
                    b = searchIfPresent(linesLists, xx1, yy1 - gap, xx1 + gap, yy1 - gap);
                    if (a && b) {
                        continue outer;
                    }


                    a = searchIfPresent(linesLists, xx2, yy2, xx2, yy2 - gap);
                    b = searchIfPresent(linesLists, xx2, yy2 - gap, xx2 - gap, yy2 - gap);
                    if (a && b) {
                        continue outer;
                    }


                    a = searchIfPresent(linesLists, xx2, yy2, xx2, yy2 + gap);
                    b = searchIfPresent(linesLists, xx2, yy2 + gap, xx2 - gap, yy2 + gap);
                    if (a && b) {
                        continue outer;
                    }
                }
            } else if (xx1 == xx2) {
                a = searchIfPresent(linesLists, xx1, yy1, xx1 + gap, yy1);
                b = searchIfPresent(linesLists, xx2, yy2, xx2 + gap, yy2);
                if (a && b) {
                    continue outer;
                }


                a = searchIfPresent(linesLists, xx1, yy1, xx1 - gap, yy1);
                b = searchIfPresent(linesLists, xx2, yy2, xx2 - gap, yy2);
                if (a && b) {
                    continue outer;
                }


                if (yy1 < yy2) {
                    a = searchIfPresent(linesLists, xx1, yy1, xx1 + gap, yy1);
                    b = searchIfPresent(linesLists, xx1 + gap, yy1, xx1 + gap, yy1 + gap);
                    if (a && b) {
                        continue outer;
                    }


                    a = searchIfPresent(linesLists, xx1, yy1, xx1 - gap, yy1);
                    b = searchIfPresent(linesLists, xx1 - gap, yy1, xx1 - gap, yy1 + gap);
                    if (a && b) {
                        continue outer;
                    }


                    a = searchIfPresent(linesLists, xx2, yy2, xx2 + gap, yy2);
                    b = searchIfPresent(linesLists, xx2 + gap, yy2, xx2 + gap, yy2 - gap);
                    if (a && b) {
                        continue outer;
                    }


                    a = searchIfPresent(linesLists, xx2, yy2, xx2 - gap, yy2);
                    b = searchIfPresent(linesLists, xx2 - gap, yy2, xx2 - gap, yy2 - gap);
                    if (a && b) {
                        continue outer;
                    }
                }
            } else {
                float change;
                change = xx1;
                xx1 = xx2;
                xx2 = change;
                change = yy1;
                yy1 = yy2;
                yy2 = change;

                a = searchIfPresent(linesLists, xx1, yy1, xx1 + gap, yy1);
                b = searchIfPresent(linesLists, xx1 + gap, yy1, xx1 + gap, yy1 + gap);
                if (a && b) {
                    continue outer;
                }


                a = searchIfPresent(linesLists, xx1, yy1, xx1 - gap, yy1);
                b = searchIfPresent(linesLists, xx1 - gap, yy1, xx1 - gap, yy1 + gap);
                if (a && b) {
                    continue outer;
                }


                a = searchIfPresent(linesLists, xx2, yy2, xx2 + gap, yy2);
                b = searchIfPresent(linesLists, xx2 + gap, yy2, xx2 + gap, yy2 - gap);
                if (a && b) {
                    continue outer;
                }


                a = searchIfPresent(linesLists, xx2, yy2, xx2 - gap, yy2);
                b = searchIfPresent(linesLists, xx2 - gap, yy2, xx2 - gap, yy2 - gap);
                if (a && b) {
                    continue outer;
                }

            }

            return index;
        }
        if(availableOptions.size()==0){
            chainsFormed = true;
            availableOptions.addAll(temporary);
            temporary.clear();
            Random random = new Random();
            if(availableOptions.size()==0){
                index = 0;
            }
            else {
                index = random.nextInt(availableOptions.size());
            }
        }
        else {
            availableOptions.addAll(temporary);
            temporary.clear();
        }
        return index;
    }

    private void selectRandomLine(){

        ArrayList<LinesList> temporary = new ArrayList();


        if(difficultyLevel>1){
            Random random = new Random();
            if(availableOptions.size()>0) {
                index = random.nextInt(availableOptions.size());
            }
            else{
                index = 0;
            }
            if(!chainsFormed) {
                index = checkIf3rdLine(temporary);
            }
            else{
                if((computerStreak2>5)&&(availableOptions.size()>3)&&(computerStreak==0)&&(difficultyLevel>3)){
                    temporary.add(linesLists.get(linesLists.size()-1));
                    linesLists.remove(linesLists.size()-1);
                    linesLists.remove(linesLists.size()-1);
                    linesLists.add(new LinesList(temporary.get(0).getX1(), temporary.get(0).getY1(), temporary.get(0).getX2(), temporary.get(0).getY2(), 0));
                    temporary.clear();
                    computerStreak2 = 0;
                    return;
                }
            }
            }


        else {
            Random random = new Random();
            index = random.nextInt(availableOptions.size());
        }

        linesLists.add(new LinesList(availableOptions.get(index).getX1(), availableOptions.get(index).getY1(),
                availableOptions.get(index).getX2(), availableOptions.get(index).getY2()
                , checkBox(availableOptions.get(index).getX1(), availableOptions.get(index).getY1(),
                availableOptions.get(index).getX2(), availableOptions.get(index).getY2())));
        availableOptions.remove(index);
        computerStreak2++;
        availableOptions.addAll(temporary);
        temporary.clear();
    }

    private void avoidThirdLine( ArrayList temporary) {
            temporary.add(availableOptions.get(index));
            availableOptions.remove(index);

        Random random = new Random();
        if(availableOptions.size()>0) {
            index = random.nextInt(availableOptions.size());
        }
    }

    private boolean draw4thLine(boolean a, boolean b, float xx1, float yy1, float xx2, float yy2) {
        if (a && b && (searchIfPresent(availableOptions, xx1, yy1, xx2, yy2)) && (!searchIfPresent(linesLists, xx1, yy1, xx2, yy2))) {
            linesLists.add(new LinesList(xx1, yy1, xx2, yy2, checkBox(xx1,yy1,xx2,yy2)));
            computerStreak2++;
            index = searchIfPresentInAvailableOptions(availableOptions, linesLists.get(linesLists.size() - 1));
            availableOptions.remove(index);

            if(difficultyLevel>2) {
                computerStreak++;

                return true;
            }
        }
        return false;
    }
    private void drawComputerLine(){
        switch (difficultyLevel) {
            case 1:{
                selectRandomLine();
                break;
            }
            case 3:
            case 4:
            case 2: {
                float xx1, yy1, xx2, yy2;
                boolean a, b;
                xx1 = linesLists.get(linesLists.size() - 1).getX1();
                yy1 = linesLists.get(linesLists.size() - 1).getY1();
                xx2 = linesLists.get(linesLists.size() - 1).getX2();
                yy2 = linesLists.get(linesLists.size() - 1).getY2();
                if((continueStreak)&&(index>=0)){
                    xx1 = linesLists.get(index).getX1();
                    yy1 = linesLists.get(index).getY1();
                    xx2 = linesLists.get(index).getX2();
                    yy2 = linesLists.get(index).getY2();
                    continueStreak = false;
                }
                if (yy1 == yy2) {
                    a = searchIfPresent(linesLists, xx1, yy1, xx1, yy1 - gap);
                    b = searchIfPresent(linesLists, xx2, yy2, xx2, yy2 - gap);
                    if (draw4thLine(a, b, xx1, yy1 - gap, xx2, yy2 - gap)) {
                        break;
                    }

                    a = searchIfPresent(linesLists, xx1, yy1, xx1, yy1 + gap);
                    b = searchIfPresent(linesLists, xx2, yy2, xx2, yy2 + gap);
                    if (draw4thLine(a, b, xx1, yy1 + gap, xx2, yy2 + gap)) {
                        break;
                    }

                    if (xx1 < xx2) {
                        a = searchIfPresent(linesLists, xx1, yy1, xx1, yy1 + gap);
                        b = searchIfPresent(linesLists, xx1, yy1 + gap, xx1 + gap, yy1 + gap);
                        if (draw4thLine(a, b, xx2, yy2, xx2, yy2 + gap)) {
                            break;
                        }

                        a = searchIfPresent(linesLists, xx1, yy1, xx1, yy1 - gap);
                        b = searchIfPresent(linesLists, xx1, yy1 - gap, xx1 + gap, yy1 - gap);
                        if (draw4thLine(a, b, xx2, yy2, xx2, yy2 - gap)) {
                            break;
                        }

                        a = searchIfPresent(linesLists, xx2, yy2, xx2, yy2 - gap);
                        b = searchIfPresent(linesLists, xx2, yy2 - gap, xx2 - gap, yy2 - gap);
                        if (draw4thLine(a, b, xx1, yy1, xx1, yy1 - gap)) {
                            break;
                        }

                        a = searchIfPresent(linesLists, xx2, yy2, xx2, yy2 + gap);
                        b = searchIfPresent(linesLists, xx2, yy2 + gap, xx2 - gap, yy2 + gap);
                        if (draw4thLine(a, b, xx1, yy1, xx1, yy1 + gap)) {
                            break;
                        }
                    } else {
                        float change;
                        change = xx1;
                        xx1 = xx2;
                        xx2 = change;
                        change = yy1;
                        yy1 = yy2;
                        yy2 = change;

                        a = searchIfPresent(linesLists, xx1, yy1, xx1, yy1 + gap);
                        b = searchIfPresent(linesLists, xx1, yy1 + gap, xx1 + gap, yy1 + gap);
                        if (draw4thLine(a, b, xx2, yy2, xx2, yy2 + gap)) {
                            break;
                        }

                        a = searchIfPresent(linesLists, xx1, yy1, xx1, yy1 - gap);
                        b = searchIfPresent(linesLists, xx1, yy1 - gap, xx1 + gap, yy1 - gap);
                        if (draw4thLine(a, b, xx2, yy2, xx2, yy2 - gap)) {
                            break;
                        }

                        a = searchIfPresent(linesLists, xx2, yy2, xx2, yy2 - gap);
                        b = searchIfPresent(linesLists, xx2, yy2 - gap, xx2 - gap, yy2 - gap);
                        if (draw4thLine(a, b, xx1, yy1, xx1, yy1 - gap)) {
                            break;
                        }

                        a = searchIfPresent(linesLists, xx2, yy2, xx2, yy2 + gap);
                        b = searchIfPresent(linesLists, xx2, yy2 + gap, xx2 - gap, yy2 + gap);
                        if (draw4thLine(a, b, xx1, yy1, xx1, yy1 + gap)) {
                            break;
                        }
                    }
                } else if (xx1 == xx2) {
                    a = searchIfPresent(linesLists, xx1, yy1, xx1 + gap, yy1);
                    b = searchIfPresent(linesLists, xx2, yy2, xx2 + gap, yy2);
                    if (draw4thLine(a, b, xx1 + gap, yy1, xx2 + gap, yy2)) {
                        break;
                    }

                    a = searchIfPresent(linesLists, xx1, yy1, xx1 - gap, yy1);
                    b = searchIfPresent(linesLists, xx2, yy2, xx2 - gap, yy2);
                    if (draw4thLine(a, b, xx1 - gap, yy1, xx2 - gap, yy2)) {
                        break;
                    }

                    if (yy1 < yy2) {
                        a = searchIfPresent(linesLists, xx1, yy1, xx1 + gap, yy1);
                        b = searchIfPresent(linesLists, xx1 + gap, yy1, xx1 + gap, yy1 + gap);
                        if (draw4thLine(a, b, xx2, yy2, xx2 + gap, yy2)) {
                            break;
                        }

                        a = searchIfPresent(linesLists, xx1, yy1, xx1 - gap, yy1);
                        b = searchIfPresent(linesLists, xx1 - gap, yy1, xx1 - gap, yy1 + gap);
                        if (draw4thLine(a, b, xx2, yy2, xx2 - gap, yy2)) {
                            break;
                        }

                        a = searchIfPresent(linesLists, xx2, yy2, xx2 + gap, yy2);
                        b = searchIfPresent(linesLists, xx2 + gap, yy2, xx2 + gap, yy2 - gap); //fishy
                        if (draw4thLine(a, b, xx1, yy1, xx1 + gap, yy1)) {
                            break;
                        }

                        a = searchIfPresent(linesLists, xx2, yy2, xx2 - gap, yy2);
                        b = searchIfPresent(linesLists, xx2 - gap, yy2, xx2 - gap, yy2 - gap);
                        if (draw4thLine(a, b, xx1, yy1, xx1 - gap, yy1)) {
                            break;
                        }
                    } else {
                        float change;
                        change = xx1;
                        xx1 = xx2;
                        xx2 = change;
                        change = yy1;
                        yy1 = yy2;
                        yy2 = change;

                        a = searchIfPresent(linesLists, xx1, yy1, xx1 + gap, yy1);
                        b = searchIfPresent(linesLists, xx1 + gap, yy1, xx1 + gap, yy1 + gap);
                        if (draw4thLine(a, b, xx2, yy2, xx2 + gap, yy2)) {
                            break;
                        }

                        a = searchIfPresent(linesLists, xx1, yy1, xx1 - gap, yy1);
                        b = searchIfPresent(linesLists, xx1 - gap, yy1, xx1 - gap, yy1 + gap);
                        if (draw4thLine(a, b, xx2, yy2, xx2 - gap, yy2)) {
                            break;
                        }

                        a = searchIfPresent(linesLists, xx2, yy2, xx2 + gap, yy2);
                        b = searchIfPresent(linesLists, xx2 + gap, yy2, xx2 + gap, yy2 - gap);
                        if (draw4thLine(a, b, xx1, yy1, xx1 + gap, yy1)) {
                            break;
                        }

                        a = searchIfPresent(linesLists, xx2, yy2, xx2 - gap, yy2);
                        b = searchIfPresent(linesLists, xx2 - gap, yy2, xx2 - gap, yy2 - gap);
                        if (draw4thLine(a, b, xx1, yy1, xx1 - gap, yy1)) {
                            break;
                        }
                    }

                }

                if (computerStreak < 1) {
                    continueStreak = false;
                } else {
                    continueStreak = true;
                }

                if (continueStreak) {
                    index = linesLists.size() - 1 - computerStreak;
                    computerStreak = 0;
                } else{
                    selectRandomLine();
                    //if chains are formed ask computer to check for locations where squares can be formed
                }
                        break;

            }
            default:{
                selectRandomLine();
                break;
            }
        }
    }
}
