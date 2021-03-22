package com.example.quins.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.quins.R;
import com.example.quins.RecyclerModel.QuinsData;

import java.util.List;

public class SliderAdapter  extends PagerAdapter {
public Context context;
public List<QuinsData> quinsDataList;
public LayoutInflater inflater;

    public SliderAdapter(Context context, List<QuinsData> quinsDataList) {
        this.context = context;
        this.quinsDataList = quinsDataList;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return quinsDataList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == ((LinearLayout) object);

    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = inflater.inflate(R.layout.statusslider, container, false);
        ImageView imageView=itemView.findViewById(R.id.image);
        TextView textView=itemView.findViewById(R.id.textdes);
        QuinsData data=quinsDataList.get(position);
        Glide.with(context).load(data.getUrl()).into(imageView);
        textView.setText(data.getUsername());
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
