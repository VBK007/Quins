package com.example.quins.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devlomi.circularstatusview.CircularStatusView;
import com.example.quins.Common.Common;
import com.example.quins.R;
import com.example.quins.RecyclerModel.QuinsData;
import com.example.quins.statusvieweractivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    public Context context;
    public List<QuinsData> quinsData;
    public List<QuinsData> quinsDataList;
    public DatabaseReference reference;
    int size=0;

    public RecyclerAdapter() {
    }

    public RecyclerAdapter(Context context, List<QuinsData> quinsData) {
        this.context = context;
        this.quinsData = quinsData;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerstatus, parent, false);
        quinsDataList = new ArrayList<>();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        QuinsData data = quinsData.get(position);

        if (data.getPhotoimageurl() != null) {
            Glide.with(context).load(data.getPhotoimageurl()).into(holder.circleImageView);

        } else {
            Glide.with(context).load(R.drawable.pp).into(holder.circleImageView);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, statusvieweractivity.class);
                Common.put_key = data.getUid();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                //Toast.makeText(context, ""+data.getUid(), Toast.LENGTH_SHORT).show();


            }
        });


        if (data.getUsername() != null) {
            holder.textView.setText(data.getUsername());
        }


    }


    @Override
    public int getItemCount() {
        return quinsData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircularStatusView circularStatusView;
        public CircleImageView circleImageView;
        public TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circularStatusView = itemView.findViewById(R.id.circular_status_view);
            circleImageView = itemView.findViewById(R.id.circular);
            textView = itemView.findViewById(R.id.username);


        }
    }
}
