package com.example.quins.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devlomi.circularstatusview.CircularStatusView;
import com.example.quins.R;
import com.example.quins.RecyclerModel.QuinsData;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    public Context context;
    public List<QuinsData> quinsData;

    public RecyclerAdapter(Context context, List<QuinsData> quinsData) {
        this.context = context;
        this.quinsData = quinsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recyclerstatus,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        QuinsData data=quinsData.get(position);
        Glide.with(context).load(data.getPhotoimageurl()).into(holder.circleImageView);
        holder.textView.setText(data.getUsername());

       holder.circleImageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
           }
       });


    }


    @Override
    public int getItemCount() {
        return quinsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircularStatusView circularStatusView;
        public CircleImageView circleImageView;
        public TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circularStatusView=itemView.findViewById(R.id.circular_status_view);
            circleImageView=itemView.findViewById(R.id.circular);
            textView=itemView.findViewById(R.id.username);


        }
    }
}
