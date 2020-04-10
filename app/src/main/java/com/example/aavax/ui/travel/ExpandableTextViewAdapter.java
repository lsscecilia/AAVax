package com.example.aavax.ui.travel;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.aavax.R;

/**
 * Adapter to show the CDC levels and their details
 */
public class ExpandableTextViewAdapter extends BaseExpandableListAdapter {

    private String[] cdcHeaders;
    private String[][] cdcDetails;
    private Context context;

    public ExpandableTextViewAdapter(String[] cdcHeaders, String[][] cdcDetails, Context context) {
        this.cdcHeaders = cdcHeaders;
        this.cdcDetails = cdcDetails;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return cdcHeaders.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return cdcDetails[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return cdcHeaders[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return cdcDetails[groupPosition][childPosition];
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

        String cdcHeader = (String)getGroup(groupPosition);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cdc_level_header, null);

        }
        TextView cdcHeaderTextView = convertView.findViewById(R.id.cdc_level_header_view);
        cdcHeaderTextView.setText(cdcHeader);
        switch (cdcHeader){
            case ("Level 3: Warning"): {
                cdcHeaderTextView.setBackgroundColor(ContextCompat.getColor(convertView.getContext(), R.color.red));
                break;
            }
            case ("Level 2: Alert"): {
                cdcHeaderTextView.setBackgroundColor(ContextCompat.getColor(convertView.getContext(), R.color.yellow));
                break;
            }

            case ("Level 1: Watch"): {
                cdcHeaderTextView.setBackgroundColor(ContextCompat.getColor(convertView.getContext(), R.color.green));
                break;
            }

        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String cdcDetail = (String)getChild(groupPosition, childPosition);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cdc_level_details, null);
        }
        TextView cdcDetailsTextView = convertView.findViewById(R.id.cdc_level_details);
        cdcDetailsTextView.setText(cdcDetail);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
