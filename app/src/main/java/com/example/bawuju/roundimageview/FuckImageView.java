package com.example.bawuju.roundimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by bawuju on 2015/10/13.
 */
public class FuckImageView extends ImageView {



    public FuckImageView(Context context) {
        super(context);
    }

    public FuckImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FuckImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();//获取bitmap
        bitmap = scalingBitmap(bitmap, getWidth(), getHeight());
        int width = bitmap.getWidth();//bitmap的宽度
        int height = bitmap.getHeight();//bitmap的高度
        int r = height > width ? width : height;//实际圆形的半径【getWidth()为实际控件的宽度 height同理】
        Bitmap backgroundBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);//新建一个bitmap,用来作为裁剪的底板
        Canvas cvs = new Canvas(backgroundBmp);//用画布在这个底板bitmap上画
        Paint paint = new Paint();//画笔
        paint.setAntiAlias(true);//抗锯齿
        cvs.drawCircle(width / 2, height / 2, r / 2, paint);//画一个圆,参数分别是 圆心X坐标, 圆心Y坐标, 半径, 画笔
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//设置模式,就是你说的那个
        cvs.drawBitmap(bitmap, 0, 0, paint);//画bitmap(画完之后,这个bitmap上就剩下了一个圆形的图)
        int offsetX = (width - getWidth()) / 2;//计算X偏移
        int offsetY = (height - getHeight()) / 2;//计算Y偏移
        canvas.drawBitmap(backgroundBmp, - offsetX, - offsetY, new Paint());//把裁剪出来的bitmap画到屏幕的canvas上
    }

    //缩放图片 长宽比例不变
    private Bitmap scalingBitmap(Bitmap bm, int newWidth, int newHeight){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 设置想要的大小
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        float scaling = scaleWidth < scaleHeight ? scaleWidth : scaleHeight;//这一行的作用就是保持长宽比例不变
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaling, scaling);
        // 得到新的图片
        Bitmap bitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return bitmap;
    }
}
