package com.example.myzxing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    private Paint mPaint;
    private Bitmap mSrc;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
//        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        Resources resources = context.getResources();
//        mSrc = BitmapFactory.decodeResource(resources, R.drawable.source);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.WHITE);

//        int save = canvas.saveLayer(new RectF(0, 0, 400, 400), null);

        Bitmap circle = getCircleBitmap();//        int sc = canvas.saveLayer(0, 0, 400, 400, null,
//                Canvas.MATRIX_SAVE_FLAG |
//                        Canvas.CLIP_SAVE_FLAG |
//                        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
//                        Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
        Bitmap rectangle = getRetangleBitmap();


//
        Paint paint = new Paint();
        /**
         * 开启硬件离屏缓存
         */
        setLayerType(LAYER_TYPE_HARDWARE, paint);
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        /**
         * 画bitmap的也透明
         */
//        canvas.drawARGB(0, 0, 0, 0);
//        canvas.drawCircle(100, 100, 100, paint);
        canvas.drawBitmap(circle, 0, 0, paint);
//        Bitmap b= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
//        Rect rect = new Rect(0, 0, 100, 100);
//        canvas.drawBitmap(b,rect, rect, paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));

//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST));
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));

//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
        canvas.drawBitmap(rectangle, 100, 100, paint);
//        canvas.restoreToCount(save);
//        canvas.restoreToCount(sc);
    }


    private Bitmap getRetangleBitmap() {
        /**
         * bm1 在bitmap上面画正方形
         */
        Bitmap rectangle = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas c1 = new Canvas(rectangle);
        Paint p1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        p1.setColor(getResources().getColor(R.color.colorAccent));
        /**
         * 设置透明
         */
        c1.drawARGB(0, 0, 0, 0);
        c1.drawRect(0, 0, 200, 200, p1);
        return rectangle;
    }

    private Bitmap getCircleBitmap() {
        /**
         * bm 在bitmap上面画圆
         */
        Bitmap circle = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(circle);
        /**
         * 设置透明
         */
        c.drawARGB(0, 0, 0, 0);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(getResources().getColor(R.color.colorPrimary));
        c.drawCircle(100, 100, 100, p);
//        c.drawOval(new RectF(0, 0, 100*3/4, 100*3/4), p);
        return circle;
    }
}
