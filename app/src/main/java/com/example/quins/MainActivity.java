package com.example.quins;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.quins.Adapter.RecyclerAdapter;
import com.example.quins.Common.Common;
import com.example.quins.RecyclerModel.QuinsData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 7172;
    FirebaseUser mauth;
    FloatingActionButton floatingActionButton;
    RelativeLayout relativeLayout, main;
    ProgressBar progressBar;
    RecyclerAdapter recyclerAdapter;
    Button btn;
    EditText editdes;
    ImageView imageViewi;
    RecyclerView recyclerView;
    List<QuinsData> quinsDataList;
    String[] permission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION};
    DatabaseReference databaseReference, userfront;
    Uri uri;
    FirebaseUser firebaseUser;
    List<QuinsData> quinsDataListd;
    StorageReference storageReference;
    MaterialSearchBar searchView;
    ImageView searchuicom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mauth = FirebaseAuth.getInstance().getCurrentUser();
        floatingActionButton = findViewById(R.id.dloatingbutton);
        relativeLayout = findViewById(R.id.visible);
        progressBar = findViewById(R.id.progress_bar);
        btn = findViewById(R.id.btn);
        main = findViewById(R.id.main);
        quinsDataListd = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler);
        searchuicom=findViewById(R.id.searchi);
        searchView=findViewById(R.id.searchview);
        quinsDataList = new ArrayList<>();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


       searchuicom.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               searchView.setVisibility(View.VISIBLE);
               searchuicom.setVisibility(View.GONE);
           }
       });



        databaseReference = FirebaseDatabase.getInstance().getReference("Quin");

        userfront=FirebaseDatabase.getInstance().getReference("QuinUser");



        storageReference = FirebaseStorage.getInstance().getReference("QuinPicks").child("Image/");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerAdapter = new RecyclerAdapter(this, quinsDataList);

        progressBar.setVisibility(View.VISIBLE);
        handler();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ischecked()) {
                    AlertDialog.Builder getdatabox = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_DayNight_NoActionBar);
                    android.app.AlertDialog spotdialog = new SpotsDialog.Builder().setContext(MainActivity.this).setMessage("Please wait").setCancelable(false).build();
                    LayoutInflater layoutInflater = getLayoutInflater();
                    View view = layoutInflater.inflate(R.layout.getdata, null);
                    imageViewi = view.findViewById(R.id.postimage);
                    editdes = view.findViewById(R.id.edittext);
                    String desc = editdes.getText().toString();
                    imageViewi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), Common.PICK_IMAGE_REQUEST);
                        }
                    });
                    getdatabox.setView(view);
                    getdatabox.setTitle("Status Activity");
                    getdatabox.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            spotdialog.show();

                            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            loaduser();
                                            String postid = databaseReference.push().getKey();
                                            HashMap<String, Object> hashMap = new HashMap<>();
                                            hashMap.put("url", uri.toString());
                                            hashMap.put("uid", firebaseUser.getUid());
                                            if (!desc.isEmpty()) {
                                                hashMap.put("desc", desc);
                                            }

                                            databaseReference.child(firebaseUser.getUid()).child(postid).setValue(hashMap);

                                            Toast.makeText(MainActivity.this, "Status Upload Sucessfully", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            spotdialog.dismiss();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    spotdialog.dismiss();
                                }
                            });

                        }
                    });
                    getdatabox.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    getdatabox.show();

                }
            }
        });

searchView.addTextChangeListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
searchuser(s.toString().toLowerCase());
    }

    @Override
    public void afterTextChanged(Editable s) {


    }
});



    }

    private void searchuser(String s) {

        Query query= userfront.orderByChild("username")
                .startAt(s)
                .endAt(s+"\uf8ff");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                quinsDataList.clear();
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                   QuinsData user=snapshot.getValue(QuinsData.class);
                    quinsDataList.add(user);
                }
                recyclerAdapter.notifyDataSetChanged();
                Collections.reverse(quinsDataList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void loaduser() {


        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("username", firebaseUser.getDisplayName());
        if (firebaseUser.getPhotoUrl() != null) {
            hashMap.put("photoimageurl", firebaseUser.getPhotoUrl());
        } else {
            hashMap.put("photoimageurl", Common.photourl);
        }
        hashMap.put("uid", firebaseUser.getUid());

        userfront.child(firebaseUser.getUid()).setValue(hashMap);

    }




    private void handler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                looip();
            }
        }, 1500);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Common.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();
            if (uri != null) {
                imageViewi.setImageURI(uri);

            }
        }
    }

    private boolean ischecked() {

        List<String> list = new ArrayList<>();
        for (String permis : permission) {

            if (ContextCompat.checkSelfPermission(this, permis) != PackageManager.PERMISSION_GRANTED) {
                list.add(permis);
            }

            if (!list.isEmpty()) {
                ActivityCompat.requestPermissions(this, permission, REQUEST_CODE_PERMISSION);

            } else {
                return true;
            }

        }

        return false;
    }

    private void looip() {
        if (Common.isConnectionAvailabele(getApplicationContext())) {
            relativeLayout.setVisibility(View.GONE);
            loop2();

        } else {
            relativeLayout.setVisibility(View.VISIBLE);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler();
                }
            });


        }
    }

    private void loop2() {
        intit();
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();


    }

    private void intit() {

        userfront.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    QuinsData data = dataSnapshot.getValue(QuinsData.class);
                    quinsDataList.add(data);


                }

                recyclerAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onPause() {
        super.onPause();
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mauth == null) {
            startActivity(new Intent(this, LoginActivity.class));

        }

    }
}