package com.usiellau.numberselecterdemo;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by UsielLau on 2017/9/16 0016 19:58.
 */

public class MyContentView extends View {

    private Context context;

    private NumberSelecter numberSelecter;
    private WindowManager windowManager;

    public MyContentView(Context context) {
        super(context);
        this.context=context;
    }

    public MyContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public MyContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("MyContentView","手指按下了");
                showNumberSelecter(event.getRawX(),event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                numberSelecter.endChanged(event.getRawX(),event.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                getWindowManager().removeView(numberSelecter);
                Log.d("MyContentView","手指抬起了，选择数字："+numberSelecter.getCurrentSelectedNumber());
                Toast.makeText(context, "select:"+numberSelecter.getCurrentSelectedNumber(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    private void showNumberSelecter(float startX,float startY){
        if(numberSelecter==null){
            numberSelecter=new NumberSelecter(context);
        }
        numberSelecter.setStartX(startX);
        numberSelecter.setStartY(startY);
        WindowManager windowManager=getWindowManager();
        int screenWidth=windowManager.getDefaultDisplay().getWidth();
        int screenHeight=windowManager.getDefaultDisplay().getHeight();
        WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
        layoutParams.x=0;
        layoutParams.y=0;
        layoutParams.type= WindowManager.LayoutParams.TYPE_PHONE;
        layoutParams.format= PixelFormat.RGBA_8888;
        layoutParams.gravity= Gravity.LEFT|Gravity.TOP;
        layoutParams.width=screenWidth;
        layoutParams.height=screenHeight;
        windowManager.addView(numberSelecter,layoutParams);

    }



    private WindowManager getWindowManager(){
        if(windowManager==null){
            windowManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        }
        return windowManager;
    }

}
