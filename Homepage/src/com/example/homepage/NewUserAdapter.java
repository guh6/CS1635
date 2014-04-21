package com.example.homepage;

 
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
 
public class NewUserAdapter extends BaseAdapter {
    private Context mContext;
 
    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.blacksheep, 
            R.drawable.penguin,
            R.drawable.bird,
            R.drawable.bunny,
            R.drawable.cat,
            R.drawable.chick2,
            R.drawable.cow,
            R.drawable.rabbit,
            R.drawable.sheep,
            R.drawable.hippo,
            R.drawable.hatchlings,
            R.drawable.giraffe,
            R.drawable.rooster
            
    };
 
    // Constructor
    public NewUserAdapter(Context c){
        mContext = c;
    }
 
    @Override
    public int getCount() {
        return mThumbIds.length;
    }
 
    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new GridView.LayoutParams(110, 110));
        return imageView;
    }
 
}