package com.example.skaarj.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class SpaceActivity extends AppCompatActivity {

    SpaceView spaceView;
    boolean firstLaunch  = true;


    class MyThread extends Thread {
        // флаг для остановки потока
        private boolean running = true;

        public void switchThread(){
            this.running = !this.running;
        }


        @Override
        public void run() {
            super.run();
            while (running) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {}
                // каждые 50 мс вызываем функцию
                // move() в основном потоке
                // здесь используется передача в функцию
                // т.н. анонимного класса
                spaceView.post(new Runnable() {
                    @Override
                    public void run() {
                        spaceView.update();
                    }
                });
            }
        }
    }


    MyThread drawThread = new MyThread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space);
        spaceView = this.findViewById(R.id.spaceView);
        drawThread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        drawThread.switchThread();
        return super.onTouchEvent(event);
    }
}
