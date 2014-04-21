package com.example.homepage;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter{

	private Context mContext;
	ArrayList<Integer> imgs;
	
	public ImageAdapter(Context c, ArrayList<Integer> list){
		
		imgs = list;
		mContext = c;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imgs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ImageView imageView;
		
		if (convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
           imageView.setPadding(8, 8, 8, 8);
           
		}
		else{
			imageView = (ImageView) convertView;
		}
		
		imageView.setImageResource(imgs.get(position));
        return imageView;
	}

}
