package com.graspery.www.wordcountpro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.graspery.www.wordcountpro.GoogleApi.Models.EntityInfo;

import java.util.List;

public class EntitiyCustomListview extends ArrayAdapter<EntityInfo> {

    int theme;
    private int resourceLayout;
    private Context mContext;

    public EntitiyCustomListview(Context context, int resource, List<EntityInfo> items, int theme) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.theme = theme;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        EntityInfo p = getItem(position);

        if (p != null) {

            Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/helvetica.otf");

            TextView entityName = v.findViewById(R.id.entity_name);
            entityName.setTypeface(typeface);
            TextView entityType = v.findViewById(R.id.entity_type);
            entityType.setTypeface(typeface);
            TextView entityImportance = v.findViewById(R.id.entity_importance);
            entityImportance.setTypeface(typeface);
            TextView entityLink = v.findViewById(R.id.entity_link);
            entityLink.setTypeface(typeface);

            TextView nameT = v.findViewById(R.id.name_title);
            nameT.setTypeface(typeface);
            TextView typeT = v.findViewById(R.id.type_title);
            typeT.setTypeface(typeface);
            TextView linkT = v.findViewById(R.id.link_title);
            linkT.setTypeface(typeface);
            TextView importanceT = v.findViewById(R.id.importance_title);
            importanceT.setTypeface(typeface);

            if(theme == 1) {
                entityImportance.setTextColor(Color.WHITE);
                entityName.setTextColor(Color.WHITE);
                entityType.setTextColor(Color.WHITE);

                nameT.setTextColor(Color.WHITE);
                typeT.setTextColor(Color.WHITE);
                importanceT.setTextColor(Color.WHITE);
                linkT.setTextColor(Color.WHITE);
            }

            if (entityName != null) {
                entityName.setText(p.name);
            }

            if (entityType != null) {
                entityType.setText(p.type);
            }

            if (entityImportance != null) {
                entityImportance.setText(p.salience*100 + "%");
            }

            if (entityLink != null) {
                if(p.wikipediaUrl == null) {
                    linkT.setVisibility(View.GONE);
                    entityLink.setVisibility(View.GONE);
                } else {
                    linkT.setVisibility(View.VISIBLE);
                    entityLink.setVisibility(View.VISIBLE);
                    entityLink.setText(p.wikipediaUrl);
                    final String url = p.wikipediaUrl;
                    entityLink.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            mContext.startActivity(i);
                        }
                    });
                }
            }
        }

        return v;
    }

}