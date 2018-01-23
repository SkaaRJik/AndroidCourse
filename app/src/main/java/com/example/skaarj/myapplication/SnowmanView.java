package com.example.skaarj.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by SkaaRJ on 23.01.2018.
 */

public class SnowmanView extends View {

    private class SnowmanTail{
        float x;
        float y;
        float radius;
        Paint paint;
        public SnowmanTail() {}

        public SnowmanTail(float x, float y, float radius, Paint paint) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.paint = paint;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getRadius() {
            return radius;
        }

        public void setX(float x) {
            this.x = x;
        }

        public void setY(float y) {
            this.y = y;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }

        public Paint getPaint() {
            return paint;
        }

        public void setPaint(Paint paint) {
            this.paint = paint;
        }
    }
    int widthScr;
    int heightScr;
    boolean resized = false;
    ArrayList<SnowmanTail> snowmanElements;

    float[][] snowflaps = new float[100][3];

    public SnowmanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        final int MIN_SIZE_FLAKS = 2;
        final int MAX_SIZE_FLAKS = 4;

        for (int i = 0; i < snowflaps.length; i++) {
            snowflaps[i][0] = (float) Math.random();
            snowflaps[i][1] = (float) Math.random();
            snowflaps[i][2] = (float) (MIN_SIZE_FLAKS + (int)(Math.random() * ((MAX_SIZE_FLAKS - MIN_SIZE_FLAKS) + 1)));
        }
        snowmanElements = new ArrayList<SnowmanTail>();
    }

    void zoomFlaps(){
        for(int i = 0 ; i < 100; i++){
            this.snowflaps[i][0] *= this.widthScr;
            this.snowflaps[i][1] *= this.heightScr;
        }
        resized = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!resized) zoomFlaps();

        for(int i = 0 ; i < 100; i++){
            if(this.snowflaps[i][0] > this.widthScr) this.snowflaps[i][0] =  0;
            else  this.snowflaps[i][0]+= 4/this.snowflaps[i][2];
            if(this.snowflaps[i][1] > this.heightScr) this.snowflaps[i][1] = 0;
            else  this.snowflaps[i][1]+=this.snowflaps[i][2];
        }

        this.invalidate();

        //return super.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(this.snowmanElements.isEmpty()){

            this.widthScr = this.getWidth();
            this.heightScr = this.getHeight();

            int delimetr = 6;
            int radius = widthScr/delimetr;

            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            snowmanElements.add(new SnowmanTail(widthScr/2, heightScr/2, radius, paint));

            radius =  widthScr / (delimetr - 2);

            snowmanElements.add(new SnowmanTail(widthScr/2, heightScr/2+snowmanElements.get(0).radius+radius-delimetr*2, radius, paint));

            radius =  widthScr / (delimetr + 2);
            snowmanElements.add(new SnowmanTail(widthScr/2, heightScr/2-snowmanElements.get(0).radius-radius+delimetr*2, radius, paint));
        }


        Paint paint = new Paint();
        paint.setColor(Color.BLACK);


        canvas.drawPaint(paint);
        paint.setColor(Color.WHITE);
        for(int i = 0 ; i < 100; i++){
            if (i==50){
                for (SnowmanTail snowmanTail: this.snowmanElements) {
                    canvas.drawCircle(snowmanTail.getX(), snowmanTail.getY(), snowmanTail.getRadius(), snowmanTail.getPaint());
                }
            }
            canvas.drawCircle(this.snowflaps[i][0], this.snowflaps[i][1], this.snowflaps[i][2], paint);
        }






    }
}
