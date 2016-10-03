# BottomNavgationBar
### 自定义View实现底部导航栏

![image](https://github.com/jiyewushen/BottomNavigationBar/blob/master/screen.gif)

### 使用图像混合显示模式处理图像
```java
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
   ```
### 实现点击后的波纹效果
```java
private void drawRipple(Canvas canvas){
    canvas.save();
      if (currentR<getMeasuredWidth()/2+10){
          canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,currentR,bgPaint);
          currentR+=10;
          postInvalidateDelayed(30);
      }else{
          canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,0,bgPaint);
          currentR=0;
      }
    canvas.restore();
  }
```
