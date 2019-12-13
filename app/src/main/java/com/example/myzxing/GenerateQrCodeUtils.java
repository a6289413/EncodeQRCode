package com.example.myzxing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.EnumMap;
import java.util.Map;

public class GenerateQrCodeUtils {
    private static final String TAG = "GenerateQrCodeUtils";
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    /**
     * @param content          要转换成二维码的内容
     * @param width            二维码的宽度
     * @param height           二维码的高度
     * @param isBlackBackgroud 二维码所在的父布局是否是黑色背景，黑色背景的情况下，二维码必须至少有1px的白边，否则二维码不能正常识别
     * @param borderWidh       二维码白边大小
     * @param isRound          是否圆角
     * @param radian           圆角弧度
     * @return
     */
    public static Bitmap generateQrCode(String content, int width, int height, boolean isBlackBackgroud, int borderWidh, boolean isRound, int radian) {
        Bitmap bitmap = null;
        if (width != height) {
            Log.w(TAG, "the width and the height must be same dimension");
            return null;
        }

        if (borderWidh == 0 && isRound) {
            Log.w(TAG, "if the whiteBorderWidth is 0 and the QR code is round corner, then the QR code maybe can't be scan, so this method will return null");
            return null;
        }


        Map<EncodeHintType, Object> hints = hints = new EnumMap(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, "0");
        BitMatrix result;
        try {
            result = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }

        // 0 ，1下标表示的是二维码的白边
        // 2, 3 下标表示的是二维码的实际宽度
        Log.v(TAG, "real white: " + result.getEnclosingRectangle()[0]);
        Log.v(TAG, "real width: " + result.getEnclosingRectangle()[3]);

        if (borderWidh == 0) {
            if (result.getEnclosingRectangle()[0] == 0) {
                if (result.getEnclosingRectangle()[2] == width) {
                    if (!isBlackBackgroud) {
                        bitmap = getBitmap(result);
                    } else { // 黑色背景, 此时必须要有至少1px 白边，二维码才能够被识别
                        bitmap = getBitmap(deleteWhite(result, 1));
                    }
                }
            }
        } else {
            // 生成的二维码与期待（传入的宽高）的宽高一样，直接利用去白边方法添加白边
            if (result.getEnclosingRectangle()[2] == width) {
                bitmap = getBitmap(deleteWhite(result, borderWidh));
                if (isRound) {
                    bitmap = bimapRound(bitmap, radian);
                }
                // 生成的二维码比期待（传入的宽高）的宽高小，先去掉二维码的白边，生成二维码bitmap， 放大二维码，然后增加白边。
            } else if (result.getEnclosingRectangle()[2] < width) {
                bitmap = getBitmap(deleteWhite(result, 0));
                bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                bitmap = bitmapWhiteBorder(bitmap, borderWidh);

//                if (isRound) {
//                    bitmap = bimapRound(bitmap, radian);
//                }
            }

        }
        return bitmap;
    }

    private static Bitmap getBitmap(BitMatrix result) {
        Bitmap bitmap;
        int resultWidth = result.getWidth();
        int resultHeight = result.getHeight();

        int[] pixels = new int[resultWidth * resultHeight];
        for (int y = 0; y < resultHeight; y++) {
            int offset = y * resultWidth;
            for (int x = 0; x < resultWidth; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        bitmap = Bitmap.createBitmap(resultWidth, resultHeight, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, resultWidth, 0, 0, resultWidth, resultHeight);
        return bitmap;
    }

    private static BitMatrix deleteWhite(BitMatrix matrix, int margin) {
        int tempM = margin * 2;
        int[] rec = matrix.getEnclosingRectangle(); // 获取二维码图案的属性
        int resWidth = rec[2] + tempM;
        int resHeight = rec[3] + tempM;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
        resMatrix.clear();
        for (int i = margin; i < resWidth - margin; i++) { // 循环，将二维码图案绘制到新的bitMatrix中
            for (int j = margin; j < resHeight - margin; j++) {
                if (matrix.get(i - margin + rec[0], j - margin + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }

    private static Bitmap bimapRound(Bitmap mBitmap, float radius) {
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        //设置矩形大小
        Rect rect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        RectF rectf = new RectF(rect);

        // 相当于清屏
        canvas.drawARGB(0, 0, 0, 0);
        //画圆角
        canvas.drawRoundRect(rectf, radius, radius, paint);
        // 取两层绘制，显示上层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // 把原生的图片放到这个画布上，使之带有画布的效果
        canvas.drawBitmap(mBitmap, rect, rect, paint);
        return bitmap;
    }

    private static Bitmap bitmapWhiteBorder(Bitmap bitmap, int borderWidth) {
        Bitmap bitmapWithBorder = Bitmap.createBitmap(bitmap.getWidth() + borderWidth * 2, bitmap.getHeight() + borderWidth * 2, bitmap.getConfig());
        Canvas canvas = new Canvas(bitmapWithBorder);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, borderWidth, borderWidth, null);
        return bitmapWithBorder;
    }
}
