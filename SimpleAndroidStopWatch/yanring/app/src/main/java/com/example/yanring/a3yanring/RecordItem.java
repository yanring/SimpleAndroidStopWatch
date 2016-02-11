package com.example.yanring.a3yanring;

import java.io.Serializable;

/**
 * Created by Yanring on 2016/2/10.
 */
public class RecordItem implements Serializable {
    private String mTimeRecord;
    private String mIntervalTime;

    public  RecordItem(String TimeRecord,String IntervalTime){
        mTimeRecord = TimeRecord;
        mIntervalTime = IntervalTime;

    }

    public String getIntervalTime() {
        return mIntervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        mIntervalTime = intervalTime;
    }

    public String getTimeRecord() {
        return mTimeRecord;
    }

    public void setTimeRecord(String timeRecord) {
        mTimeRecord = timeRecord;
    }
}
