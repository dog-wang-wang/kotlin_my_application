package com.dorameet.myapplication.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF

class ImageUtils {
    private fun cropBitmap(bitmap: Bitmap): Bitmap {//从中间截取一个正方形
        val w = bitmap.getWidth() // 得到图片的宽，高
        val h = bitmap.getHeight()
        val cropWidth = if (w>=h){
            h;
        }else{
            w;
        }
        return Bitmap.createBitmap(bitmap, (bitmap.getWidth() - cropWidth) / 2,
            (bitmap.getHeight() - cropWidth) / 2, cropWidth, cropWidth);
    }

    fun getCircleBitmap(bitmap: Bitmap): Bitmap {//把图片裁剪成圆形
        val rBitmap = cropBitmap(bitmap)//裁剪成正方形
        try {
            val circleBitmap: Bitmap =
                Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            val canvas = Canvas(circleBitmap);
            val paint = Paint();
            val rect = Rect(
                0, 0, bitmap.getWidth(),
                bitmap.getHeight()
            );
            val rectF = RectF(
                Rect(
                    0, 0, bitmap.getWidth(),
                    bitmap.getHeight()
                )
            );
            var roundPx = 0.0f;
            roundPx = bitmap.getWidth().toFloat();
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.WHITE);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            val src = Rect(
                0, 0, bitmap.getWidth(),
                bitmap.getHeight()
            );
            canvas.drawBitmap(bitmap, src, rect, paint);
            return circleBitmap;
        } catch (e: Exception) {
            return bitmap;
        }
    }
}