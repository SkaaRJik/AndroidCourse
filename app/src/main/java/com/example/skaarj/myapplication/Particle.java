package com.example.skaarj.myapplication;

import java.util.Random;

/**
 * Created by SkaaRJ on 24.01.2018.
 */

public class Particle {
    float radius;
    float angle;
    private int size;
    private final float VELOCITY = (float) (Math.random() * 3 - 2);


    public Particle(float radius) {
        Random rand = new Random();
        this.radius = radius;
        this.angle = (float) (rand.nextFloat() * (Math.PI * 1.7));
    }

    public float getX(){
        return (float) (this.radius * Math.sin(this.angle * Math.PI / 2));
    }

    public float getY(){
        return (float) (this.radius * Math.cos(this.angle * Math.PI / 2));
    }


    public void move(){
        this.angle+=VELOCITY;
        if(this.angle > Math.PI * 2){
            this.angle -= (float) Math.PI * 2;
        }
    }

    public float getSize() {
        return size;
    }

    public void generateRandomSize(int minSize, int maxSize){
        this.size = (int)((minSize+ (Math.random() * (((maxSize) - minSize) + 1))));
    };
}
