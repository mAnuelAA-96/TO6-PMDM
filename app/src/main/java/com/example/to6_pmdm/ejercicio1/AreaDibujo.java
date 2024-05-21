package com.example.to6_pmdm.ejercicio1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AreaDibujo extends View {

    public Integer colorTrazo = Color.BLACK;
    public Integer grosorTrazo = 5;
    float posx = 0;
    float posy = 0;
    Path path;
    Paint paint;
    List<Path> paths;
    List<Paint> paints;

    public AreaDibujo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paths = new ArrayList<>();
        paints = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int i = 0;
        for (Path trazo : paths) {
            canvas.drawPath(trazo, paints.get(i++));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        posx = event.getX();
        posy = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                paint = new Paint();
                paint.setStrokeWidth(grosorTrazo);
                paint.setColor(colorTrazo);
                paint.setStyle(Paint.Style.STROKE);
                paints.add(paint);

                path = new Path();
                path.moveTo(posx, posy);
                paths.add(path);
                break;

            case MotionEvent.ACTION_MOVE:

            case MotionEvent.ACTION_UP:
                int puntos = event.getHistorySize();
                for (int i = 0; i < puntos; i++) {
                    path.lineTo(event.getHistoricalX(i), event.getHistoricalY(i));
                }
        }

        invalidate();
        return true;
    }

    public void borrarDibujo(){
        paths.clear();
        paints.clear();
        invalidate();
    }

    public void deshacerUltimoTrazo(){
        if(!paths.isEmpty() && !paints.isEmpty()){
            paths.remove(paths.size() - 1);
            paints.remove(paints.size() - 1);
            invalidate();
        }
    }

}
