package com.usiellau.numberselecterdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by UsielLau on 2017/9/16 0016 20:10.
 */

public class NumberSelecter extends View {

    private float startX;
    private float startY;

    private float endX;
    private float endY;


    private float bigCircleRadius=200;
    private float smallCircleRadius=100;

    private int currentSelectedNumber;

    public NumberSelecter(Context context) {
        super(context);
    }

    public NumberSelecter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberSelecter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint=new Paint();
        drawRing(canvas);
        drawNumber(canvas);
        //绘制中心圆点
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(startX,startY,10,paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(startX,startY,15,paint);
        //绘制线
        canvas.drawLine(startX,startY,endX,endY,paint);

        super.draw(canvas);
    }

    private void drawRing(Canvas canvas){
        //圆矩形轮廓
        RectF rectF=new RectF(startX-bigCircleRadius+50,startY-bigCircleRadius+50,startX+bigCircleRadius-50,
                startY+bigCircleRadius-50);


        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(80);
        paint.setAlpha(100);
        canvas.drawArc(rectF,0,360,false,paint);



        //绘制选中部分扇形
        if(!fingerInSmallCircle()){
            paint.setAlpha(200);
            canvas.drawArc(rectF,-getSelectedArcAngle(),40,false,paint);
        }else{
            currentSelectedNumber=0;
        }



    }

    private boolean fingerInSmallCircle(){
        float tempX=endX-startX;
        float tempY=endY-startY;
        double dis=Math.sqrt(Math.pow(tempX,2)+Math.pow(tempY,2));
        if(dis<110)return true;
        return false;
    }

    private void drawNumber(Canvas canvas){
        //绘制数字
        Paint paint=new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(45);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        float tempX,tempY;
        int currentNumber=4;
        for(int i=0;i<9;++i,currentNumber++){
            if(currentNumber==10)currentNumber=1;
            tempX=(float)(startX+150*Math.cos(Math.toRadians(40*i+18)));
            tempY=(float)(startY+150*Math.sin(Math.toRadians(40*i+18)));
            tempY+=10;
            canvas.drawText(String.valueOf(currentNumber),tempX,tempY,paint);
        }
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public float getEndX() {
        return endX;
    }

    public void setEndX(float endX) {
        this.endX = endX;
    }

    public float getEndY() {
        return endY;
    }

    public void setEndY(float endY) {
        this.endY = endY;
    }

    public void endChanged(float newEndX,float newEndY){
        endX=newEndX;
        endY=newEndY;
        invalidate();
    }

    public int getCurrentSelectedNumber() {
        return currentSelectedNumber;
    }

    private int getSelectedArcAngle(){
        //以起始点为原点，以平面直角坐标系为标准，计算拉线角度
        float tempY=endY-startY;
        float tempX=endX-startX;
        double tempRadian=Math.atan(Math.abs(tempY)/Math.abs(tempX));
        double tempAngle=Math.toDegrees(tempRadian);
        double angle=0.0;
        Log.d("NumberSelecter","tempY:"+tempY+"tempX:"+tempX+"tempAngle:"+tempAngle);
        if(tempX>=0&&tempY>=0){
            //第四象限
            angle=360-tempAngle;
            Log.d("NumberSelecter","第四象限");
        }else if(tempX>=0&&tempY<=0){
            //第一象限
            angle=tempAngle;
            Log.d("NumberSelecter","第一象限");
        }else if(tempX<=0&&tempY>=0){
            //第三象限
            angle=tempAngle+180;
            Log.d("NumberSelecter","第三象限");
        }else if(tempX<=0&&tempY<=0){
            //第二象限
            angle=180-tempAngle;
            Log.d("NumberSelecter","第二象限");
        }
        int res=(int)angle/40;
        currentSelectedNumber=3-res;
        if(currentSelectedNumber<=0){
            currentSelectedNumber+=9;
        }
        Log.d("NumberSelecter","res:"+res);
        return (res+1)*40;
    }


    public interface OnNumberSelectedListener{
        void onSelected(int number);
    }

}
