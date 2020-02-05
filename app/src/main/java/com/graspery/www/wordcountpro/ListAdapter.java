package com.graspery.www.wordcountpro;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Archive> {

    private int lastPosition = -1 ;
    private int resourceLayout;
    private Context mContext;
    private String theme;
    private int total;

    public ListAdapter(Context context, int resource, List<Archive> items, String theme, int size) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.theme = theme;
        total = size;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Archive p = getItem(position);
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/helvetica.otf");

        if (p != null) {
            TextView tt2 = v.findViewById(R.id.archive_date);
            tt2.setTypeface(typeface);
            TextView tt3 = v.findViewById(R.id.archive_textview);
            tt3.setTypeface(typeface);

            //tt3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.content_start, 0, R.drawable.content_end, 0);

            if(theme.equalsIgnoreCase("dark") || theme.equalsIgnoreCase("super_dark")) {
                int textColor = mContext.getResources().getColor(R.color.dark_theme_text_color);
                tt2.setTextColor(textColor);
                tt3.setTextColor(textColor);
            }


            if (tt2 != null) {
                tt2.setText(p.getDate());
            }

            if (tt3 != null) {
                tt3.setText(p.getText());
            }
        }

        Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //v.startAnimation(animation);
        lastPosition = position;

        return v;
    }

}