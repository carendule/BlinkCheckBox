package com.caren.blinkcheckbox;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.ArrayList;
import java.util.List;

public class BlinkCheckBox extends View implements Checkable {

    public BlinkCheckBox(Context context) {
        super(context);
        init(context,null);
    }

    public BlinkCheckBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public BlinkCheckBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BlinkCheckBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private static final int DEFAULT_WIDTH = dp2pxInt(36);
    private static final int DEFAULT_HEIGHT = dp2pxInt(36);

    private boolean isChecked = false;

    private Drawable checkedImg,uncheckedImg,normalBackgroundImg = null;

    private Drawable checkedBackground,uncheckedBackground = null;

    private int normalBackgroundColor = Color.WHITE;

    private int checkedColor = Color.parseColor("#87CEFA");

    private int uncheckedColor = Color.DKGRAY;

    private int imgMarginLeft,imgMarginRight,imgMarginTop,imgMarginBottom = 0;

    private int width ,height = 0;

    private float defaultRadis = 0;

    private float unCheckedRadis = 0;

    private float checkedRadis = 0;

    private int effectDuration,blinkDuration = 500;

    private int effect = -1;

    private Paint paint,paintUncheck,paintCheck;

    private OnCheckedChangeListener onCheckedChangeListener;

    private void init(Context context,AttributeSet attrs){
        TypedArray typedArray = null;
        if(attrs != null){
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.box);
        }

        checkedImg = optDrawable(typedArray, R.styleable.box_checkedImgInside,null);

        uncheckedImg = optDrawable(typedArray, R.styleable.box_uncheckedImgInside,null);

        normalBackgroundImg = optDrawable(typedArray, R.styleable.box_normalBackgroundImg,null);

        checkedBackground = optDrawable(typedArray, R.styleable.box_normalBackgroundImg,null);

        uncheckedBackground = optDrawable(typedArray, R.styleable.box_normalBackgroundImg,null);

        normalBackgroundColor = optColor(typedArray, R.styleable.box_normalBackgroundColor,normalBackgroundColor);

        checkedColor =optColor(typedArray, R.styleable.box_checkedColor,checkedColor);

        uncheckedColor = optColor(typedArray, R.styleable.box_uncheckedColor,uncheckedColor);

        imgMarginTop = optPixelSize(typedArray, R.styleable.box_imgMarginTop,0);

        imgMarginLeft = optPixelSize(typedArray, R.styleable.box_imgMarginLeft,0);

        imgMarginRight = optPixelSize(typedArray, R.styleable.box_imgMarginRight,0);

        imgMarginBottom = optPixelSize(typedArray, R.styleable.box_imgMarginBottom,0);

        effect = optInt(typedArray, R.styleable.box_effect,-1);

        effectDuration = optInt(typedArray, R.styleable.box_effectDuration,effectDuration);

        blinkDuration = optInt(typedArray, R.styleable.box_blinkDuration,blinkDuration);

        if(typedArray != null){
            typedArray.recycle();
        }

        if(normalBackgroundImg != null){
            normalBackgroundImg = getDrawable(normalBackgroundImg,normalBackgroundColor);
            checkedBackground = getDrawable(checkedBackground,checkedColor);
            uncheckedBackground = getDrawable(uncheckedBackground,uncheckedColor);
        }

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintUncheck = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCheck = new Paint(Paint.ANTI_ALIAS_FLAG);

        super.setClickable(true);
        this.setPadding(0, 0, 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    public final void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(0, 0, 0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if(widthMode == MeasureSpec.UNSPECIFIED
                || widthMode == MeasureSpec.AT_MOST){
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(DEFAULT_WIDTH, MeasureSpec.EXACTLY);
        }
        if(heightMode == MeasureSpec.UNSPECIFIED
                || heightMode == MeasureSpec.AT_MOST){
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(DEFAULT_HEIGHT, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
        unCheckedRadis =width<height ? width*.5f : height*.5f;
        checkedRadis = 0;
        //圆形的半径
        defaultRadis = width<height ? width*.5f : height*.5f;
        postInvalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制底色
        paint.setStrokeWidth(dp2px(10));
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(normalBackgroundColor);
        if(normalBackgroundImg == null){
            canvas.drawCircle(defaultRadis,defaultRadis,defaultRadis,paint);
        }else{
            normalBackgroundImg.setBounds(0,0,width,height);
            normalBackgroundImg.draw(canvas);
        }


        //绘制未选中
        paintUncheck.setStyle(Paint.Style.FILL);
        paintUncheck.setColor(uncheckedColor);
        if(normalBackgroundImg == null){
            canvas.drawCircle(defaultRadis,defaultRadis,unCheckedRadis,paintUncheck);
        }else{
            float ratio = 1-(unCheckedRadis/defaultRadis);
            int left = (int) (ratio*(width/2));
            int top = (int) (ratio*(height/2));
            int right = width-left;
            int bottom = height-top;
            uncheckedBackground.setBounds(left,top,right,bottom);
            uncheckedBackground.draw(canvas);
        }


        //绘制选中
        paintCheck.setStyle(Paint.Style.FILL);
        paintCheck.setColor(checkedColor);
        if(normalBackgroundImg == null){
            canvas.drawCircle(defaultRadis,defaultRadis,checkedRadis,paintCheck);
        }else{
            float ratio = 1-(checkedRadis/defaultRadis);
            int left = (int) (ratio*(width/2));
            int top = (int) (ratio*(height/2));
            int right = width-left;
            int bottom = height-top;
            checkedBackground.setBounds(left,top,right,bottom);
            checkedBackground.draw(canvas);
        }


        if(checkedImg != null){
            checkedImg.setBounds(imgMarginLeft,imgMarginTop,width-imgMarginRight,height-imgMarginBottom);
            checkedImg.setAlpha(checkedImgAlpha);
            checkedImg.draw(canvas);
        }

        if(uncheckedImg != null){
            uncheckedImg.setBounds(imgMarginLeft,imgMarginTop,width-imgMarginRight,height-imgMarginBottom);
            uncheckedImg.setAlpha(unCheckedImgAlpha);
            uncheckedImg.draw(canvas);
        }

    }

    @Override
    public void setChecked(boolean checked) {
        if(checked == isChecked()){
            postInvalidate();
            return;
        }
        toggle();
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        if(isChecked){
            transferToUncheck(true);
            if(onCheckedChangeListener != null){
                onCheckedChangeListener.onCheckedChanged(this,false);
            }
        }else{
            transferToCheck(true);
            if(onCheckedChangeListener != null){
                onCheckedChangeListener.onCheckedChanged(this,true);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                final AnimatorSet scaleDown = new AnimatorSet();
                this.setPivotX(width*.5f);
                this.setPivotY(height*.5f);
                scaleDown.playTogether(
                        ObjectAnimator.ofFloat(this, "scaleX", 1, 0.9f)
                                .setDuration(100),
                        ObjectAnimator.ofFloat(this, "scaleY", 1, 0.9f)
                                .setDuration(100)
                );
                scaleDown.start();
                break;
            case MotionEvent.ACTION_UP:
                final AnimatorSet scaleUp = new AnimatorSet();
                this.setPivotX(width*.5f);
                this.setPivotY(height*.5f);
                scaleUp.playTogether(
                        ObjectAnimator.ofFloat(this, "scaleX", 0.9f, 1)
                                .setDuration(100),
                        ObjectAnimator.ofFloat(this, "scaleY", 0.9f, 1)
                                .setDuration(100)
                );
                scaleUp.start();
                toggle();
                break;
        }
        return super.onTouchEvent(event);
    }


    private void transferToCheck(boolean animate){
        isChecked = true;
        if(valueAnimator != null && valueAnimator.isRunning()){
            return;
        }
        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(blinkDuration);
        valueAnimator.setRepeatCount(0);
        valueAnimator.addUpdateListener(animatorUpdateListener);
        lastFraction = 0;
        valueAnimator.start();
        playCheckAnime();
    }

    private void transferToUncheck(boolean animate){
        isChecked = false;
        if(valueAnimator != null && valueAnimator.isRunning()){
            return;
        }
        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(blinkDuration);
        valueAnimator.setRepeatCount(0);
        valueAnimator.addUpdateListener(animatorUpdateListener);
        lastFraction = 0;
        valueAnimator.start();
        playUncheckAnime();
    }

    private void playCheckAnime(){
        switch (effect){
            case 0:playFlipVer();break;
            case 1:playFlipHor(true);break;
            case 2:playFlipSur(true);break;
            case 3:playBounce();break;
        }
    }

    private void playUncheckAnime(){
        switch (effect){
            case 0:playFlipVer();break;
            case 1:playFlipHor(false);break;
            case 2:playFlipSur(false);break;
            case 3:playBounce();break;
        }
    }

    private void playFlipVer(){
            ObjectAnimator.ofFloat(this, "rotationX", 0, 360)
                    .setDuration(effectDuration).start();
    }

    private void playFlipHor(boolean isChecked){
        if(isChecked){
            ObjectAnimator.ofFloat(this, "rotationY", 0, 180)
                    .setDuration(effectDuration).start();
        }else{
            ObjectAnimator.ofFloat(this, "rotationY", 180,0)
                    .setDuration(effectDuration).start();
        }

    }

    private void playFlipSur(boolean isChecked){
        if(isChecked){
            ObjectAnimator.ofFloat(this, "rotation", 0, 360)
                    .setDuration(effectDuration).start();
        }else{
            ObjectAnimator.ofFloat(this, "rotation", 360,0)
                    .setDuration(effectDuration).start();
        }
    }

    private void playBounce(){
        AnimatorSet allQueue = new AnimatorSet();
        List<Animator> animatorList = new ArrayList<>();
        for(float i = 1.3f ; i>1 ;i=i-0.1f){
            AnimatorSet scaleDown = new AnimatorSet();
            AnimatorSet scaleUp = new AnimatorSet();
            scaleDown.playTogether(
                    ObjectAnimator.ofFloat(this, "scaleX", 1f, i)
                            .setDuration(effectDuration/6),
                    ObjectAnimator.ofFloat(this, "scaleY", 1f, i)
                            .setDuration(effectDuration/6)
            );
            scaleUp.playTogether(
                    ObjectAnimator.ofFloat(this, "scaleX", i, 1f)
                            .setDuration(effectDuration/6),
                    ObjectAnimator.ofFloat(this, "scaleY", i, 1f)
                            .setDuration(effectDuration/6)
            );
            animatorList.add(scaleDown);
            animatorList.add(scaleUp);
        }
        allQueue.playSequentially(animatorList);
        allQueue.start();
    }

    private ValueAnimator valueAnimator;

    private float lastFraction = 0;

    private float thisFraction;

    private int checkedImgAlpha = 0;

    private int unCheckedImgAlpha = 255;

    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            thisFraction = valueAnimator.getAnimatedFraction();
            float step = defaultRadis*(thisFraction - lastFraction)*2;
            int alphaStep = (int) (255*(thisFraction - lastFraction)*2);
            lastFraction = thisFraction;
            if(isChecked){
                if(unCheckedRadis>0){
                    unCheckedRadis = thisFraction >= 0.5 ? 0 : unCheckedRadis-step;
                }else if(checkedRadis < defaultRadis){
                    unCheckedRadis = 0;
                    checkedRadis = thisFraction == 1 ? defaultRadis : checkedRadis+step;
                    if(checkedRadis > defaultRadis){
                        checkedRadis = defaultRadis;
                    }
                }

                if(unCheckedImgAlpha >0){
                    unCheckedImgAlpha = thisFraction >= 0.5 ? 0 : unCheckedImgAlpha-alphaStep;
                }else if(checkedImgAlpha < 255){
                    unCheckedImgAlpha = 0;
                    checkedImgAlpha = thisFraction == 1 ? 255 : checkedImgAlpha+alphaStep;
                }
            }else{
                if(checkedRadis>0){
                    checkedRadis = thisFraction >= 0.5 ? 0 : checkedRadis-step;
                }else if(unCheckedRadis < defaultRadis){
                    checkedRadis = 0;
                    unCheckedRadis = thisFraction == 1 ? defaultRadis : unCheckedRadis+step;
                    if(unCheckedRadis > defaultRadis){
                        unCheckedRadis = defaultRadis;
                    }
                }

                if(checkedImgAlpha >0){
                    checkedImgAlpha = thisFraction >=0.5 ? 0 : checkedImgAlpha-alphaStep;
                }else if(unCheckedImgAlpha < 255){
                    checkedImgAlpha = 0;
                    unCheckedImgAlpha = thisFraction == 1 ? 255 : unCheckedImgAlpha+alphaStep;
                }
            }
            postInvalidate();
        }
    };

    public void setOnCheckedChangeListener(OnCheckedChangeListener l){
        onCheckedChangeListener = l;
    }

    public interface OnCheckedChangeListener{
        void onCheckedChanged(BlinkCheckBox view, boolean isChecked);
    }


    /*******************util**************************************/
    private static float dp2px(float dp){
        Resources r = Resources.getSystem();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    private static int dp2pxInt(float dp){
        return (int) dp2px(dp);
    }

    private static Bitmap getBitmap(Drawable drawable,int color){
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bm = bd.getBitmap();
        Bitmap outBitmap = Bitmap.createBitmap (bm.getWidth(),bm.getHeight(),bm.getConfig());
        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        paint.setColorFilter( new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)) ;
        canvas.drawBitmap(bm , 0, 0, paint) ;
        return outBitmap;
    }

    private Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (!origin.isRecycled()) {
            origin.recycle();
        }
        return newBM;
    }

    //更改指定色值
    private static Drawable getDrawable(Drawable drawable,int beColor) {
        DrawableCompat.setTintList(drawable, ColorStateList.valueOf(beColor));
        return drawable;
    }

    private static int optInt(TypedArray typedArray,
                              int index,
                              int def) {
        if(typedArray == null){return def;}
        return typedArray.getInt(index, def);
    }


    private static float optPixelSize(TypedArray typedArray,
                                      int index,
                                      float def) {
        if(typedArray == null){return def;}
        return typedArray.getDimension(index, def);
    }

    private static int optPixelSize(TypedArray typedArray,
                                    int index,
                                    int def) {
        if(typedArray == null){return def;}
        return typedArray.getDimensionPixelOffset(index, def);
    }

    private static int optColor(TypedArray typedArray,
                                int index,
                                int def) {
        if(typedArray == null){return def;}
        return typedArray.getColor(index, def);
    }

    private static boolean optBoolean(TypedArray typedArray,
                                      int index,
                                      boolean def) {
        if(typedArray == null){return def;}
        return typedArray.getBoolean(index, def);
    }

    private static Drawable optDrawable(TypedArray typedArray,
                                        int index,
                                        Drawable def){
        if(typedArray == null){return def;}
        return typedArray.getDrawable(index);
    }
}
