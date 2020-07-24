package com.example.categeory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class Category extends AppCompatActivity  {

    final static private String TAG=Category.class.getSimpleName();
    private GridView catGrid;
    private FirebaseFirestore db;
    private CollectionReference Cref;
    private catAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        callRecycle();

    }

    void callRecycle(){
        db = FirebaseFirestore.getInstance();
        Cref = db.collection("Categories");

        Query query = Cref.orderBy("name");

        FirestoreRecyclerOptions<catObject> options = new FirestoreRecyclerOptions.Builder<catObject>()
                .setQuery(query, catObject.class)
                .build();

        adapter = new catAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_cat);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new catAdapter.onItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                catObject c = documentSnapshot.toObject(catObject.class);
                String n=c.getName();
                Toast.makeText(Category.this,n,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),TestList.class);
                i.putExtra("name",n);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}