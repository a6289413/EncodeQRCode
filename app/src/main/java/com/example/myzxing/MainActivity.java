package com.example.myzxing;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView iv = findViewById(R.id.iv);

        Bitmap bitmap = GenerateQrCodeUtils.generateQrCode("罗克维尔斯罗克维尔斯", 300, 300, true, 15, true, 40);

        iv.setImageBitmap(bitmap);


//        Rect rect = new Rect(50, 50, src.getWidth(), src.getHeight());
//        RectF rectF = new RectF(rect);

//        Bitmap bitmap = Bitmap.createBitmap(src.getWidth() + 40, src.getHeight() + 40, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        canvas.drawARGB(0, 0, 0, 0);
//        canvas.drawRect(new Rect(0, 0, src.getWidth() + 40, src.getHeight() + 40), paint);
//
////        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
////
////        canvas.drawBitmap(src, rect, rect, paint);
//
//        canvas.drawBitmap(src, 20, 20, paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));

//        canvas.save(Canvas.ALL_SAVE_FLAG);

//        ImageView iv = findViewById(R.id.iv);
//
//        iv.setImageBitmap(src);
    }
}