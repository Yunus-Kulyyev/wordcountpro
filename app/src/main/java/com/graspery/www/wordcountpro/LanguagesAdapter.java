package com.graspery.www.wordcountpro;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

public class LanguagesAdapter extends ArrayAdapter<String> {

    LayoutInflater flater;
    Activity c;

    public LanguagesAdapter(Activity context, int resouceId, List<String> list){

        super(context,resouceId, list);
        this.c = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView,position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    private View rowview(View convertView , int position){

        String p = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.language_row, null, false);

            rowview.setTag(holder);

            holder.country = rowview.findViewById(R.id.country_name);
            holder.country.setText(p);

            /*holder.flag = rowview.findViewById(R.id.flag_image);
            if(p.equals("English")) {
                holder.flag.setImageDrawable(c.getDrawable(R.drawable.united_states));
            } else if(p.equals("German")) {
                holder.flag.setImageDrawable(c.getDrawable(R.drawable.germany));
            } else if(p.equals("Polish")) {
                holder.flag.setImageDrawable(c.getDrawable(R.drawable.poland));
            } else if(p.equals("Turkish")) {
                holder.flag.setImageDrawable(c.getDrawable(R.drawable.turkey));
            } else if(p.equals("Russian")) {
                holder.flag.setImageDrawable(c.getDrawable(R.drawable.russia));
            } else if(p.equals("Spanish")) {
                holder.flag.setImageDrawable(c.getDrawable(R.drawable.spain));
            } else if(p.equals("Italian")) {
                holder.flag.setImageDrawable(c.getDrawable(R.drawable.italy));
            } else if(p.equals("Brazilian")) {
                holder.flag.setImageDrawable(c.getDrawable(R.drawable.brazil));
            } else if(p.equals("French")) {
                holder.flag.setImageDrawable(c.getDrawable(R.drawable.france));
            } else if(p.equals("Argentinian")) {
                holder.flag.setImageDrawable(c.getDrawable(R.drawable.argentina));
            } else if(p.equals("Slovakian")) {
                holder.flag.setImageDrawable(c.getDrawable(R.drawable.slovakia));
            } else if(p.equals("Norwegian")) {
                holder.flag.setImageDrawable(c.getDrawable(R.drawable.norway));
            } else if(p.equals("Japanese")) {
                holder.flag.setImageDrawable(c.getDrawable(R.drawable.japan));
            }*/
        }else{
            holder = (viewHolder) rowview.getTag();
        }

        return rowview;
    }

    private class viewHolder{
        NormalTextView country;
        ImageView flag;
    }
}

