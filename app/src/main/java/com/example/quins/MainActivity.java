package com.example.quins;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseUser mauth;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mauth = FirebaseAuth.getInstance().getCurrentUser();
        floatingActionButton = findViewById(R.id.dloatingbutton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               AlertDialog.Builder additem=new AlertDialog.Builder(getApplicationContext(),R.style.Theme_AppCompat);
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.getdata, null);

                additem.setView(view);


                additem.setTitle("Status ");
                additem.setMessage("Add Photo");
                additem.show();
                additem.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
dialog.dismiss();
                    }
                });
                additem.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });


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