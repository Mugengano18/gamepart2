package com.example.gamepart2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.Sets;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetsActivity extends AppCompatActivity {
    @BindView(R.id.sets_recycler)
    RecyclerView setsView;
    private  SetAdapter setAdapter;
    private FirebaseFirestore firestore;
    private Dialog loadingDialog;
    @BindView(R.id.sa_toolbar)
    Toolbar toolbar;
    public static List<SetModel> setList = new ArrayList<>();
    public static int selected_set_index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Levels");

        loadingDialog = new Dialog(SetsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);


        firestore = FirebaseFirestore.getInstance();


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setsView.setLayoutManager(layoutManager);


        loadSets();
    }

    private void loadSets() {

       loadingDialog.show();
       setList.clear();

       firestore.collection("DETOUR").document("SETS")
               .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if (task.isSuccessful()){
                   DocumentSnapshot documentSnapshot = task.getResult();
                   if (documentSnapshot.exists()){
                       long count = (long) documentSnapshot.get("COUNT");
                       for (int i=1;i<= count;i++){
                           String setName = documentSnapshot.getString("SET" + String.valueOf(i) + "_NAME");
                           String setid = documentSnapshot.getString("SET" + String.valueOf(i) + "_ID");
                           setList.add(new SetModel(setid,setName));
                       }

                       setAdapter =new SetAdapter(setList);
                       setsView.setAdapter(setAdapter);
                   }
                   else {
                       Toast.makeText(SetsActivity.this, "No Level Here!!!", Toast.LENGTH_SHORT).show();
                   }
               }
               else {
                   Toast.makeText(SetsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
               }
               loadingDialog.dismiss();
           }
       });

    }
}