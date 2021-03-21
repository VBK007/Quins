package com.example.quins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.quins.Adapter.RecyclerAdapter;
import com.example.quins.Common.Common;
import com.example.quins.RecyclerModel.QuinsData;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseUser mauth;
    FloatingActionButton floatingActionButton;
    RelativeLayout relativeLayout;
    ProgressBar progressBar;
    Button btn;
    RecyclerView recyclerView;
    List<QuinsData> quinsDataList;
    String[] permission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_CONTACTS};
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mauth = FirebaseAuth.getInstance().getCurrentUser();
        floatingActionButton = findViewById(R.id.dloatingbutton);
        relativeLayout = findViewById(R.id.visible);
        progressBar = findViewById(R.id.progress_bar);
        btn = findViewById(R.id.btn);
        recyclerView = findViewById(R.id.recycler);
        quinsDataList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Quin");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);

            }
        }, 2000);


        looip();


    }

    private void looip() {
        if (Common.isConnectionAvailabele(getApplicationContext())) {
            relativeLayout.setVisibility(View.VISIBLE);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    looip();
                }
            });

        } else {
            relativeLayout.setVisibility(View.GONE);
            loop2();

        }
    }

    private void loop2() {
        intit();
        recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, quinsDataList);
        recyclerView.setAdapter(recyclerAdapter);


    }

    private void intit() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChildren()) {

                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    QuinsData data = snapshot.getValue(QuinsData.class);
                    quinsDataList.add(data);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mauth == null) {
            startActivity(new Intent(this, LoginActivity.class));

        }

    }
}