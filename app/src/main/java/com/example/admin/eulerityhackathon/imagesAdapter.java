package com.example.admin.eulerityhackathon;

import android.app.Activity;
import android.content.Context;

import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 3/31/18.
 */

public class imagesAdapter extends ArrayAdapter<Event> {


    public imagesAdapter(@NonNull Context context, List<Event> events) {
        super(context, 0, events);

    }


    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.images_list, null, true);
        }

        Event event = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.imageViewProduct);
        Picasso.get().load(event.getUrl()).into(imageView);

        return convertView;

    }

}
