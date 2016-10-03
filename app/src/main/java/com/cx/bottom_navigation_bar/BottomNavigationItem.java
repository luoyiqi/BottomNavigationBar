package com.cx.bottom_navigation_bar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import util.DisplayUtil;

/**
 * Created by CX on 2016/9/27.
 */

public class BottomNavigationItem extends View {
    private Bitmap src;
    private String text;
    private int color;
    private Paint paint;
    private Paint textPaint;
    private Bitmap bitmap;
    private Rect imgRect;
    private Rect textRect;

    private boolean click = false;//选中状态，默认为未选中
    private int clickColor;
    private Paint clickPaint;
    private Paint clickTextPaint;
    private Bitmap clickBitmap;
    private int currentR=0;
    private Rect imgClickRect;
    private Paint bgPaint;

    private int imgWidth = DisplayUtil.dp2px(getContext(), 24);
    private int textSize = DisplayUtil.sp2px(getContext(), 12);
    private int clickTextSize = DisplayUtil.sp2px(getContext(), 14);
    private int bottom = DisplayUtil.dp2px(getContext(), 46);


    public void setClick(boolean click) {
        this.click = click;
        invalidateView();
    }

    public boolean getClick() {
        return click;
    }

    public BottomNavigationItem(Context context) {
        this(context, null);
    }

    public BottomNavigationItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        initTools();
    }

    /**
     * 从XML中获取对应属性
     */
    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationItem);
        BitmapDrawable bd = (BitmapDrawable) array.getDrawable(R.styleable.BottomNavigationItem_src);
        assert bd != null;
        src = bd.getBitmap();
        text = array.getString(R.styleable.BottomNavigationItem_text);
        clickColor = array.getColor(R.styleable.BottomNavigationItem_color, 0xff6666);
        array.recycle();
    }

    private void initTools() {
        textRect = new Rect();
        textPaint = new TextPaint();
        textPaint.setTextSize(textSize);
        textPaint.setColor(0xff6666);
        textPaint.getTextBounds(text, 0, text.length(), textRect);
        imgRect = new Rect();
        imgClickRect = new Rect();
        paint = new Paint();
        clickTextPaint = new Paint();
        clickPaint = new Paint();
        bgPaint=new Paint();
        bgPaint.setColor(clickColor);
        bgPaint.setAlpha(50);
        color=0xFF807E7E;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int left = getMeasuredWidth() / 2 - imgWidth / 2;
        int top = DisplayUtil.dp2px(getContext(), 8);
        int clickTop = DisplayUtil.dp2px(getContext(), 6);
        imgRect.set(left, top, left + imgWidth, top + imgWidth);
        imgClickRect.set(left, clickTop, left + imgWidth, clickTop + imgWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (click) {
           if (clickBitmap==null)clickBitmap=creatClickBitmap(clickColor,imgClickRect,clickPaint);
            drawRipple(canvas);
            canvas.drawBitmap(clickBitmap, 0, 0, null);
            drawTargetText(canvas, 255);
        } else {
            if (bitmap==null) bitmap=creatClickBitmap(color,imgRect,paint);
            canvas.drawBitmap(bitmap, 0, 0, null);
            drawSourceText(canvas, 0);
        }

    }

    /**
     * 画波纹
     */
  private void drawRipple(Canvas canvas){
    canvas.save();
      if (currentR<getMeasuredWidth()/2+10){
          canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,currentR,bgPaint);
          currentR+=10;
          postInvalidateDelayed(15);
      }else{
          canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,0,bgPaint);
          currentR=0;
      }
    canvas.restore();
  }

    private void drawTargetText(Canvas canvas, int alpha) {
        clickTextPaint.setColor(clickColor);
        clickTextPaint.setAlpha(alpha);
        clickTextPaint.setTextSize(clickTextSize);
        canvas.drawText(text, getMeasuredWidth() / 2 - textRect.width() / 2, bottom, clickTextPaint);
    }

    private void drawSourceText(Canvas canvas, int alpha) {
        textPaint.setColor(color);
        textPaint.setAlpha(255 - alpha);
        canvas.drawText(text, getMeasuredWidth() / 2 - textRect.width() / 2, bottom, textPaint);
    }


    private Bitmap creatClickBitmap(int color,Rect rect,Paint paint){
      Bitmap bitmap=Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setDither(true);
        canvas.drawRect(rect, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint.setAlpha(255);
        canvas.drawBitmap(src, null, rect, paint);
        return bitmap;
    }


    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }
}
