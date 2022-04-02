package com.example.noface.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.noface.Constants;
import com.example.noface.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapUtil {
    private static final String TAG = "BitmapUtils";


    public static byte[] bmpToByteArray(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static byte[] bmpToByteArrayForWeChat(final Bitmap bmp, final boolean needRecycle) {
        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        }  else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        Canvas localCanvas =  new Canvas(localBitmap);

        while ( true) {
            localCanvas.drawBitmap(bmp,  new Rect(0, 0, i, j),  new Rect(0, 0,i, j),  null);
            if (needRecycle)
                bmp.recycle();
            ByteArrayOutputStream localByteArrayOutputStream =  new ByteArrayOutputStream();
            localBitmap.compress(CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            }  catch (Exception e) {
                // F.out(e);
            }
            i = bmp.getHeight();
            j = bmp.getHeight();
        }
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }


    public static Bitmap getBitmapByView(NestedScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        scrollView.draw(canvas);
        return bitmap;
    }


    public static Bitmap loadBitmapFromView(View v) {
        v.setDrawingCacheEnabled(true);
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int w = v.getMeasuredWidth();
        int h = v.getMeasuredHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);
        v.setDrawingCacheEnabled(false);
        return bmp;
    }

    public static Bitmap loadBitmap(String path) {
        Bitmap bm = null;
        bm = BitmapFactory.decodeFile(path);
        return bm;
    }

    public static Bitmap loadBitmap(byte[] data, int width, int height) {
        Bitmap bm = null;
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        bm = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
        int x = opts.outWidth / width;
        int y = opts.outHeight / height;
        opts.inSampleSize = x > y ? x : y;
        opts.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
        return bm;
    }

    public static void save(Bitmap bm, File file) throws Exception {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream stream = new FileOutputStream(file);
        bm.compress(CompressFormat.JPEG, 100, stream);
    }

    //dstWidth��dstHeight�ֱ�ΪĿ��ImageView�Ŀ��
    public static int calSampleSize(Options options, int dstWidth, int dstHeight) {
        int rawWidth = options.outWidth;
        int rawHeight = options.outHeight;
        int inSampleSize = 1;
        if (rawWidth > dstWidth || rawHeight > dstHeight) {
            float ratioHeight = (float) rawHeight / dstHeight;
            float ratioWidth = (float) rawWidth / dstHeight;
            inSampleSize = (int) Math.min(ratioWidth, ratioHeight);
        }
        return inSampleSize;
    }

    public static Bitmap get(int reqHeight, int reqWidth, String key) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = null;
        try {
            BitmapFactory.decodeStream(new URL(key).openStream(), null, options);
            int width = options.outWidth, height = options.outHeight;
            int inSampleSize = 1;
            if (height > reqHeight || width > reqWidth) {
                final int halfHeight = height / 2;
                final int halfWidth = width / 2;
                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                    inSampleSize *= 2;
                }
            }
            Options opt = new Options();
            opt.inSampleSize = inSampleSize;
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            bitmap = BitmapFactory.decodeStream(new URL(key).openStream(), null, opt);
        } catch (IOException e) {
            LogUtil.e(TAG, "get e = " + e.getMessage().toString());
        } catch (OutOfMemoryError e) {
            LogUtil.e(TAG, "get OutOfMemoryError e = " + e.getMessage().toString());
            System.gc();
        }

        return bitmap;
    }

    public static Bitmap getBitmapFromFile(String filePath, int reqHeight, int reqWidth) {
        Options options = new Options();
        options.inJustDecodeBounds = true;

        Bitmap bitmap = null;
        try {
            FileInputStream fs = null;
            fs = new FileInputStream(filePath);
            BitmapFactory.decodeFileDescriptor(fs.getFD(), null, options);
            Options opt = new Options();
            opt.inSampleSize = calSampleSize(options, reqWidth, reqHeight);
            LogUtil.i(TAG, "getBitmapFromFile inSampleSize = " + opt.inSampleSize);
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            bitmap = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, opt);
        } catch (IOException e) {
            LogUtil.e(TAG, "get IOException e = " + e.getMessage());
        } catch (OutOfMemoryError e) {
            LogUtil.e(TAG, "get OutOfMemoryError e = " + e.getMessage());
            System.gc();
        }
        return bitmap;
    }

    public static Bitmap getBitmapFromRes(Context context, int resId, ImageView imageView) {
        if (null == imageView) {
            LogUtil.i(TAG, "getBitmapFromRes null==imageView ");
            return null;
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;

        Options opt = new Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inSampleSize = calSampleSize(options, imageView.getWidth(), imageView.getHeight());
        LogUtil.i(TAG, "getBitmapFromRes inSampleSize = " + opt.inSampleSize);
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }


    public static void loadBitmapToImage(Context mContext, String url, ImageView imageview) {
        // LogUtil.i(TAG, "url:" + url);
        if (TextUtils.isEmpty(url)) {
            BitmapUtil.loadResourceBitmapToImage(mContext, R.drawable.myself, imageview);
        } else {
            if (!TextUtils.isEmpty(url) && !url.contains("sharingschool.com")) {
                url = Constants.FILE_URL + url;
            }
            try {
                Activity activity = (Activity) mContext;
                if (activity.isDestroyed() || activity.isFinishing()) {
                    return;
                }
                Glide.with(mContext).load(url)
                        .asBitmap()
                        .placeholder(R.drawable.myself)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.myself)
                        .into(imageview);
            } catch (Exception e) {
                LogUtil.i(TAG, "loadBitmapToImage error:" + e.toString());
            }
        }

    }

    public static void loadResourceBitmapToImage(Context mContext, int Resource, ImageView imageview) {
        Activity activity = (Activity) mContext;
        if (activity.isDestroyed() || activity.isFinishing()) {
            return;
        }
        Glide.with(mContext).load(Resource)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageview);
    }

    public static void loadPathBitmapToImage(Context mContext, String url, ImageView imageview) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        try {
            Glide.with(mContext).load(url).asBitmap()
                    .placeholder(R.drawable.myself)
                    .error(R.drawable.myself)
                    .into(imageview);
        } catch (Exception e) {
            LogUtil.i(TAG, "loadPathBitmapToImage error:" + e.toString());
        }
    }

    public static void loadRoundImage(final Context context, final int cornerRadius, String url, final ImageView imageView) {
        if (!TextUtils.isEmpty(url) && !url.startsWith(Constants.FILE_URL)) {
            url = Constants.FILE_URL + url;
        }
        try {
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .placeholder(R.drawable.myself)
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //设置缓存
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            super.setResource(resource);
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCornerRadius(cornerRadius); //设置圆角弧度
                            imageView.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        } catch (Exception e) {
            LogUtil.i(TAG, "loadRoundImage error:" + e.toString());
        }
    }

    public static Boolean loadRoundImage2(final Context context, final int cornerRadius, String url, final ImageView imageView) {
        if(TextUtils.isEmpty(url)){
            return false;
        }
        if (!TextUtils.isEmpty(url) && !url.startsWith(Constants.FILE_URL)) {
            url = Constants.FILE_URL + url;
        }
        try {
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .placeholder(R.drawable.myself)
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //设置缓存
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            super.setResource(resource);
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCornerRadius(cornerRadius); //设置圆角弧度
                            imageView.setImageDrawable(circularBitmapDrawable);
                        }
                    });
            return true;
        } catch (Exception e) {
            LogUtil.i(TAG, "loadRoundImage error:" + e.toString());
        }
        return false;
    }

    public static Bitmap getRoundCornerImage(Bitmap bitmap_bg, Bitmap bitmap_in) {
        Bitmap roundConcerImage = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundConcerImage);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, 500, 500);
        Rect rectF = new Rect(0, 0, bitmap_in.getWidth(), bitmap_in.getHeight());
        paint.setAntiAlias(true);
        NinePatch patch = new NinePatch(bitmap_bg, bitmap_bg.getNinePatchChunk(), null);
        patch.draw(canvas, rect);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap_in, rectF, rect, paint);
        return roundConcerImage;
    }

    public static Bitmap getShardImage(Bitmap bitmap_bg, Bitmap bitmap_in) {
        Bitmap roundConcerImage = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundConcerImage);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, 500, 500);
        paint.setAntiAlias(true);
        NinePatch patch = new NinePatch(bitmap_bg, bitmap_bg.getNinePatchChunk(), null);
        patch.draw(canvas, rect);
        Rect rect2 = new Rect(2, 2, 498, 498);
        canvas.drawBitmap(bitmap_in, rect, rect2, paint);
        return roundConcerImage;
    }

    /**
     * 连接网络获得相对应的图片
     *
     * @param imageUrl
     * @return
     */
    public static Drawable getImageNetwork(String imageUrl) {
        URL myFileUrl = null;
        Drawable drawable = null;
        try {
            myFileUrl = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            // 在这一步最好先将图片进行压缩，避免消耗内存过多
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            drawable = new BitmapDrawable(bitmap);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;
    }

}
