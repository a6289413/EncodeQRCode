package com.example.myzxing;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.GenerateQrCodeUtils;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv = findViewById(R.id.iv);
        Bitmap bitmap = GenerateQrCodeUtils.generateQrCode("https://www.baidu.com/", 300, 300, true, 15, true, 10);
        iv.setImageBitmap(bitmap);
    }
}