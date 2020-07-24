package com.example.categeory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class TestList extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef;
    private TestAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
        RecyclerViewcall();
    }
    private void RecyclerViewcall() {
        //Crate a get Intent here that gives a string from the categeories layout
        Intent intent = getIntent();
        String str = intent.getStringExtra("name");
        notebookRef = db.collection("Categories").document(str).collection("TestList");

        //Edit this query to get data as per category
        Query query = notebookRef.orderBy("rating", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<TestObject> options = new FirestoreRecyclerOptions.Builder<TestObject>()
                .setQuery(query, TestObject.class)
                .build();

        adapter = new TestAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //Implement go to test editor in this function
        adapter.setOnClickListener(new TestAdapter.onItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int pos) {
                TestObject obj = documentSnapshot.toObject(TestObject.class);
                String title = obj.getTitle();
                Toast.makeText(TestList.this,title,Toast.LENGTH_SHORT).show();
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
