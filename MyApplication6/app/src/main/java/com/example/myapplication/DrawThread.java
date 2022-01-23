package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private volatile boolean running = true;
    float x = 10000, y = 10000, r = 0;

    void set(float x, float y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    DrawThread(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    public void requestStop() {
        running = false;
    }

    @Override
    public void run() {
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    canvas.drawColor(Color.BLUE);
                    canvas.drawCircle(x, y, r, paint);
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            r += 5;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}