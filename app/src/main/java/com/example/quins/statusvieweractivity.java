package com.example.quins;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.quins.Adapter.SliderAdapter;
import com.example.quins.Common.Common;
import com.example.quins.RecyclerModel.QuinsData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class statusvieweractivity extends AppCompatActivity implements StoriesProgressView.StoriesListener {
    StoriesProgressView storiesProgressView;
    List<QuinsData> quinsDataList;
    ViewPager viewPager;
    SliderAdapter sliderAdapter;
    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statusviewer);
        quinsDataList = new ArrayList<QuinsData>();

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               inti();

           }
       },1500);



        viewPager = findViewById(R.id.viewpager);
        sliderAdapter = new SliderAdapter(statusvieweractivity.this, quinsDataList);

//        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);
//        storiesProgressView.setStoriesCount(quinsDataList.size());
//        storiesProgressView.setStoryDuration(1200L);
//        storiesProgressView.setStoriesListener(this);
//        storiesProgressView.startStories();

        Toast.makeText(statusvieweractivity.this, ""+quinsDataList.size(), Toast.LENGTH_SHORT).show();


    }

    private void inti() {
        reference = FirebaseDatabase.getInstance().getReference("Quin").child(Common.put_key);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                quinsDataList.clear();


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    QuinsData quinsData = dataSnapshot.getValue(QuinsData.class);
                    quinsDataList.add(quinsData);

                }
                sliderAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onNext() {

    }

    @Override
    public void onPrev() {

    }

    @Override
    public void onComplete() {

    }
}
