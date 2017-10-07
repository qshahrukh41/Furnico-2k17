package com.log2n.rentafurniture;

/**
 * Created by shahrukh on 8/20/17.
 */

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.log2n.rentafurniture.Item;

import java.util.List;

public class ItemListAdapter extends ArrayAdapter<ImageUpload> {

    private  Activity context;
    private int resource;
    private List<ImageUpload> listImage;

    public ItemListAdapter (@NonNull Activity context, @LayoutRes int resource, @NonNull List<ImageUpload> objects) {
        super(context, resource , objects );

        this.context=context;
        this.resource=resource;
        listImage = objects;

    }



    @Override
    public View getView(int position,@Nullable  View convertView,@NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resource, null);
        TextView tvName = (TextView) v.findViewById(R.id.tvItemName);
        TextView tvRent = (TextView) v.findViewById(R.id.tvItemRent);
        TextView tvLocation = (TextView) v.findViewById(R.id.tvItemLocation);
        ImageView img = (ImageView) v.findViewById(R.id.imgView);

        tvName.setText(listImage.get(position).getName());
        tvRent.setText(listImage.get(position).getRent());
        tvLocation.setText(listImage.get(position).getLocation());
        Glide.with(context).load(listImage.get(position).getUrl()).into(img);

        return v;
    }

}
