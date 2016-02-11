package com.example.yanring.a3yanring;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.Console;

public class StopWatch extends View  {

    private static final String TAG ="" ;
    private Paint mPaint;
    private Rect mRect= null;
    private  int mBackgroundColor;
    private  int mTextSize;
    private int mWidth;
    private int mRadius =300;
    private RectF mOval =null;
    private RectF mInsideOval = null;
    private int mCentre;
    private int mSmallRadius=290;
    private float mTotalTime = 0;
    private float mArcTime = 0;
    int mMinute = 0;
    int mSecond = 0;
    int mMillisecond= 0;
    private float mPreviousTime ;
    private String mSecondText;
    private String mMillisecondText;
    private float mTotalIntervalTime;
    private int mStartTime;


    public StopWatch(Context context) {
        this(context,null);
    }

    public StopWatch(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public StopWatch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    private void init(Context context,AttributeSet attrs){
        mPaint = new Paint();
        mRect = new Rect();
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.StopWatch);//第一步获取自定义属性的所有属性

        //获取backgroundColor,TextSize
        mBackgroundColor=typedArray.getColor(R.styleable.StopWatch_backgroundColor, Color.RED);//默认为红色
        mTextSize = (int) typedArray.getDimension(R.styleable.StopWatch_TextSize,30);
        mRadius = (int) typedArray.getDimension(R.styleable.StopWatch_radius,350);
        mTotalTime = typedArray.getDimension(R.styleable.StopWatch_time,0);
        mOval = new RectF();
        mInsideOval = new RectF();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG,"OnMeasure");
        mWidth = getMeasuredWidth()/2;
        mRadius = mWidth -120;
        mSmallRadius = (mRadius-20);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mCentre = mWidth;

        mOval.set(mCentre - mRadius, mCentre - mRadius, mCentre + mRadius, mCentre + mRadius);
        mInsideOval.set(mCentre - mSmallRadius, mCentre - mSmallRadius, mCentre + mSmallRadius, mCentre + mSmallRadius);
        // mOval.contains(mCentre-mRadius, mCentre -mRadius, mCentre +mRadius, mCentre +mRadius);
        mInsideOval.contains(mCentre - mSmallRadius, mCentre - mSmallRadius, mCentre + mSmallRadius, mCentre + mSmallRadius);

        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(19);
        canvas.drawArc(mOval, 0, 360, false, mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        canvas.drawArc(mOval, -90, mArcTime, false, mPaint);


        mMinute = (int) (mTotalTime/60000);//画分钟
        mPaint.setColor(Color.WHITE);
        //mPaint.setTypeface(Typeface.MONOSPACE);
        mPaint.setStyle(Paint.Style.FILL);
        String MinueteText = String.valueOf(mMinute);
        mPaint.setTextSize(250);
        //String text ="213124";
        mPaint.getTextBounds(MinueteText, 0, MinueteText.length(), mRect);
        //mRect获得text的边距
        int textWidth = mRect.width();
        int textHeight = mRect.height();
        canvas.drawText(MinueteText, mCentre  - textWidth / 2-300, mCentre + textHeight / 2, mPaint);

        mSecond= (int) (mTotalTime%60000)/1000;//画秒
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(100f);
        mSecondText = "00";
        if (mSecond<10){
            mSecondText = "0"+mSecond;
        }
        else {
            mSecondText = ""+mSecond;
        }
        mPaint.setTextSize(250);
        //String text ="213124";
        mPaint.getTextBounds(mSecondText, 0, mSecondText.length(), mRect);
        //mRect获得text的边距
        textWidth = mRect.width();
        textHeight = mRect.height();
        canvas.drawText(mSecondText,0,2, mCentre  - textWidth / 2, mCentre + textHeight / 2, mPaint);

        mMillisecond= (int) (mTotalTime%1000)/10;//画毫秒
        mPaint.setColor(Color.WHITE);
        mMillisecondText = "00";
        if (mMillisecond<10){
            mMillisecondText = "0"+mMillisecond;
        }
        else {
            mMillisecondText = ""+mMillisecond;
        }
        mPaint.setTextSize(150);
        //String text ="213124";
        mPaint.getTextBounds(mSecondText, 0, mSecondText.length(), mRect);
        canvas.drawText(mMillisecondText,0,2, mCentre+230, mCentre + textHeight / 2, mPaint);
    }
    public void ChangeTimer(float time){
        mTotalTime = time;
        mArcTime = ((time-mStartTime)%60000)/(60000/360);
        invalidate();
    }
    public String getRecordTime(){
        String RecordTime = ""+mMinute+":"+mSecondText+":"+mMillisecondText;
        return RecordTime;
    }
    public String getIntervalTime(){
        mTotalIntervalTime = mTotalTime - mPreviousTime;

        mPreviousTime = mTotalTime;

        int Second= (int) (mTotalIntervalTime%60000)/1000;//画秒
        String SecondText;
        if (Second<10){
            SecondText = "0"+Second;
        }
        else {
            SecondText = ""+Second;
        }
        int Millisecond= (int) (mTotalIntervalTime%1000)/10;//画毫秒
        String MillisecondText = "00";
        if (Millisecond<10){
            MillisecondText = "0"+Millisecond;
        }
        else {
            MillisecondText = ""+Millisecond;
        }
        String RecordTime = (int) (mTotalIntervalTime /60000)+":"+SecondText+":"+MillisecondText;
        return RecordTime;
    }
    public  void resetStartTime(int StartTime){
        mStartTime = StartTime;
    }
    public  void resetStopWatch(){
         mTotalTime = 0;
         mArcTime = 0;
         mMinute = 0;
         mSecond = 0;
         mMillisecond= 0;
         mPreviousTime = 0 ;

        mTotalIntervalTime= 0;
         mStartTime =  0;
    }
}
