package com.example.pemesanangasonline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.pemesanangasonline.R;

import java.util.HashMap;
import java.util.List;

public class ExpandListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> parentList;
    private HashMap<String, List<String>> childList;

    public ExpandListViewAdapter(Context context, List<String> parentList, HashMap<String, List<String>> childList) {
        this.context = context;
        this.parentList = parentList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return this.parentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childList.get(this.parentList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.parentList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.childList.get(this.parentList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String parentlist = (String) getGroup(groupPosition);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expand_list_riwayat, null);
        }

        TextView parentTv = convertView.findViewById(R.id.parentlist);
        parentTv.setText(parentlist);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childlist = (String) getChild(groupPosition,childPosition);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expand_list_child_riwayat, null);
        }

        TextView childTv = convertView.findViewById(R.id.childlist);
        TextView childTv2 = convertView.findViewById(R.id.childlist2);

        if (childPosition == 0){

            childTv.setText(childlist);
            childTv2.setText("Nomor Pemesanan");
        }

        else if (childPosition == 1){
            childTv.setText(childlist);
            childTv2.setText("Dikirim Kepada");
        }

        else if (childPosition == 2){
            childTv.setText(childlist);
            childTv2.setText("Telp");
        }

        else if (childPosition == 3){
            childTv.setText(childlist);
            childTv2.setText("Alamat");
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
