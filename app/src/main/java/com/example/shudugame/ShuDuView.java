package com.example.shudugame;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class ShuDuView extends View {
    //用于记录单元格的宽度和高度
    private float width;
    private float height;
    private Game game=new Game();
    private int selectX;
    private int selectY;

    public ShuDuView(Context context) {
        super(context);
    }

    public  void setSelectedTile(int t) {
        if (game.setTileValid(selectX,selectY,t)){
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //计算当前单元格的款高度
        width = w / 9f;
        height = h / 9f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景
        Paint backGroundPaint = new Paint();
        backGroundPaint.setColor(getResources().getColor(R.color.shadow_background));
        canvas.drawRect(0, 0, getWidth(), getHeight(), backGroundPaint);

        Paint darkPaint = new Paint();
        darkPaint.setColor(getResources().getColor(R.color.shadow_dark));

        Paint lightPaint = new Paint();
        lightPaint.setColor(getResources().getColor(R.color.shadow_title));

        Paint title = new Paint();
        title.setColor(getResources().getColor(R.color.shadow_title));
        //画浅色线
        for (int i = 0; i < 9; i++) {
            //造成视觉效果，刻上去一样
            canvas.drawLine(0, i * height, getWidth(), i * height, lightPaint);
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, lightPaint);
            canvas.drawLine(i * width, 0, i * width, getHeight(), lightPaint);
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), lightPaint);
        }
        //画深色线
        for (int i = 0; i < 9; i++) {
            if (i%3==0){
                canvas.drawLine(0, i * height, getWidth(), i * height, darkPaint);
                canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, darkPaint);
                canvas.drawLine(i * width, 0, i * width, getHeight(), darkPaint);
                canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), darkPaint);
            }
        }
        //绘制文字
        Paint textPaint=new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextSize(height*0.75f);
        textPaint.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fm=textPaint.getFontMetrics();
        //文字偏移量
        float x=width/2;
        float y=height/2-(fm.ascent+fm.descent)/2;
        //填写数字
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                canvas.drawText(game.getTileString(i,j),i*width+x,j*height+y,textPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()!=MotionEvent.ACTION_DOWN){
            return super.onTouchEvent(event);
        }
        selectX = (int) (event.getX()/width);
        selectY = (int) (event.getY()/height);

        StringBuilder sb=new StringBuilder();
        int used[]=game.getUsedTile(selectX, selectY);
        CustomDialog dialog=new CustomDialog(this.getContext(),used,this);
        dialog.show();
        return true;
    }
}
