package com.example.yanring.a3yanring;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yanring on 2016/2/10.
 */
public class RecordListAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<RecordItem> mRecordItems = new ArrayList<>();
    private List<String> mStringList;
    private String mRecordText;

    public RecordListAdapter(Context context,  List<RecordItem> RecordList){
        mContext = context;
        mRecordItems = RecordList;

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mRecordItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mRecordItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();
        if(convertview==null){
            convertview = mLayoutInflater.inflate(R.layout.itme_record,null);
            viewHolder = new ViewHolder();
            viewHolder.recordTimeTextView = (TextView) convertview.findViewById(R.id.record_time);viewHolder.recordTimeTextView = (TextView) convertview.findViewById(R.id.record_time);
            viewHolder.IntervalTimeTextView = (TextView) convertview.findViewById(R.id.interval_time);viewHolder.recordTimeTextView = (TextView) convertview.findViewById(R.id.record_time);
            convertview.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertview.getTag();
        }
        mRecordText = "#"+(mRecordItems.size()-i)+"  "+mRecordItems.get(i).getTimeRecord();
        viewHolder.recordTimeTextView.setText(mRecordText);
        viewHolder.IntervalTimeTextView.setText(""+mRecordItems.get(i).getIntervalTime());

        return convertview;
    }

    private class ViewHolder {
        TextView recordTimeTextView;
        TextView IntervalTimeTextView;
    }
    public void refreData(List<RecordItem> RecordList) {
        mRecordItems = RecordList;
        notifyDataSetChanged();//更改ListView的数据

    }
}
