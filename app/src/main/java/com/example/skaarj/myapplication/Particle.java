package com.example.skaarj.myapplication;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by SkaaRJ on 24.01.2018.
 */

public class Particle implements Comparable<Particle> {
    float radius;
    float angle;
    private int size;
    private final float VELOCITY = (float) (Math.random()/10  - 0.05);


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
        } else if( this.angle < 0){
            this.angle += (float) Math.PI * 2;
        }
    }

    public float getSize() {
        return size;
    }

    public void generateRandomSize(int minSize, int maxSize){
        this.size = (int)((minSize+ (Math.random() * (((maxSize) - minSize) + 1))));
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Particle particle = (Particle) o;

        return Float.compare(particle.angle, angle) == 0;
    }

    @Override
    public int hashCode() {
        return (angle != +0.0f ? Float.floatToIntBits(angle) : 0);
    }

    public Particle() {
        super();
    }


    @Override
    public int compareTo(@NonNull Particle particle) {
        return (int) (particle.angle - this.angle);
    }
}
