package com.example.yanring.a3yanring;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.yanring.a3yanring.StopWatch.*;

/**
 * Created by Yanring on 2016/2/9.
 */
public class StopWatchActivity extends Activity implements OnClickListener{
    private static final String TAG =StopWatchActivity.class.getSimpleName() ;
    TestHandler mTestHandler = new TestHandler();
    private ImageButton mButton;
    private StopWatch mStopWatch;
    private int mTime = 0;
    private int mFlagStarted = 0;
    private int mFlagRecord = 0;
    private List<RecordItem> mRecordItemList;
    private String mRecordTime,mIntervalTime;
    private RecordListAdapter mRecordListAdapter;
    private int mStartPauseTime=0,mEndPauseTime=0;
    private int mPausedTime = 0;
    private int mStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_watch_view);
        mStopWatch = (StopWatch) findViewById(R.id.stop_watch);
        mButton = (ImageButton) findViewById(R.id.Record_Button);
        mButton.setOnClickListener(this);
        mButton = (ImageButton)findViewById(R.id.Start_Button);
        mButton.setOnClickListener(this);

        mRecordItemList = new ArrayList<RecordItem>();

        ListView listView = (ListView) findViewById(R.id.list_view);
        mRecordListAdapter = new RecordListAdapter(StopWatchActivity.this,mRecordItemList);
        listView.setAdapter(mRecordListAdapter);

    }

    @Override
    public void onClick(View view) {
         switch (view.getId()) {
             case R.id.Start_Button: {
                 if (mFlagStarted == 0) {
                     mFlagStarted = 1;//1为正在计时
                     Log.i(TAG, "开始运行     " + mFlagStarted);
                     Message message = mTestHandler.obtainMessage();
                     message.arg1 = 0;
                     message.arg2 = 1;
                     message.what = 8888;//通过what来识别是哪个message
                     message.obj = 1;
                     if(mStartPauseTime!=0){
                         mEndPauseTime = (int) System.currentTimeMillis();
                         mPausedTime += mEndPauseTime - mStartPauseTime;
                         mButton.setImageResource(R.mipmap.pause_icon);

                     }else{
                         mStartTime = (int) System.currentTimeMillis();
                         mButton.setImageResource(R.mipmap.pause_icon);

                     }
                     mTestHandler.sendMessageDelayed(message, 100);//因此则要用外部类
                     Toast.makeText(StopWatchActivity.this, mFlagStarted + "", Toast.LENGTH_SHORT).show();
                     break;
                 }
                 if (mFlagStarted == 1) {
                     mFlagStarted = 0;
                     Log.i(TAG, "暂停运行   " + mFlagStarted);
                     mStartPauseTime = (int) System.currentTimeMillis();
                     mButton.setImageResource(R.mipmap.start_click_icon);
                     Toast.makeText(StopWatchActivity.this, mFlagStarted + "", Toast.LENGTH_SHORT).show();
                     break;
                 }
             }
             case R.id.Record_Button:{
                 if(mFlagStarted == 1) {
                     Log.i(TAG, "记录数据     " + mFlagStarted);
                     mRecordTime = mStopWatch.getRecordTime();

                     mIntervalTime = mStopWatch.getIntervalTime();
                     mRecordItemList.add(0, new RecordItem(mRecordTime, mIntervalTime));
                     mRecordListAdapter.refreData(mRecordItemList);
                     Toast.makeText(StopWatchActivity.this, mFlagStarted + "", Toast.LENGTH_SHORT).show();
                     mStopWatch.resetStartTime((int) System.currentTimeMillis()-mPausedTime-mStartTime);
                     break;
                 }else{
                     mRecordItemList.clear();
                     mStartTime = (int) System.currentTimeMillis();
                     mPausedTime = 0;
                     mFlagStarted = 0;
                     mStartPauseTime = 0;
                     mStopWatch.resetStopWatch();
                     mStopWatch.ChangeTimer(0);
                     mRecordListAdapter.refreData(mRecordItemList);
                 }
             }
         }
    }

    public class TestHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //接收消息
            switch (msg.what){
                case 8888:
                    int value = (int) msg.obj;
                    mTime+=100;
                    mStopWatch.ChangeTimer((int) System.currentTimeMillis()-mPausedTime-mStartTime);
                    msg = Message.obtain();
                    msg.arg1 = 0;
                    msg.arg2 = 1;
                    msg.what = 8888;//通过what来识别是哪个message
                    msg.obj = 2;
                    if(mFlagStarted==1){
                        mTestHandler.sendMessageDelayed(msg,100);//由于将延迟时间设为10ms会遇到时间不对的情况,所以设为了100
                    }
                    break;

            }
        }
    }
}
