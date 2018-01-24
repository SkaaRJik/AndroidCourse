package com.example.skaarj.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by SkaaRJ on 24.01.2018.
 */

public class SpaceView extends View {
    Particle[] particles;
    final int RADIUS = 30;
    final int DELTA = 10;
    int scrWidth;
    int scrHeight;

    public SpaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.particles = new Particle[100];
        for (int i = 0; i < particles.length; i++) {
            this.particles[i] = new Particle(new Random().nextInt(DELTA) + RADIUS);
            this.particles[i].generateRandomSize(1, 5);
        }
    }

    public void update(){
        for (int i = 0; i < this.particles.length; i++) {
            this.particles[i].move();
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawPaint(paint);
        paint.setColor(Color.WHITE);
        scrWidth = this.getWidth();
        scrHeight = this.getHeight();

        for (int i = 0; i < particles.length; i++) {
            canvas.drawCircle(particles[i].getX() * (scrHeight%30)+(scrWidth/2),
                    particles[i].getY() * (scrHeight%30) + (scrHeight/2), particles[i].getSize(), paint);
        }

    }


}
