package com.example.categeory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class TestAdapter extends FirestoreRecyclerAdapter<TestObject, TestAdapter.ItemHolder> {
    private onItemClickListener listener;
    public TestAdapter(@NonNull FirestoreRecyclerOptions<TestObject> options) {
        super(options);
    }

    //method to bind each TestObject
    @Override
    protected void onBindViewHolder(@NonNull ItemHolder holder, int position, @NonNull TestObject model) {
        holder.textViewTitle.setText(model.getTitle());
        holder.textViewDescription.setText(model.getDescription());
        holder.textViewRating.setText(String.valueOf(model.getRating()));
    }

    //Inflator as in adapters
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item_layout, parent, false);
        return new ItemHolder(v);
    }

    //compulsory class implementation that extends ViewHolder
    class ItemHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewRating;
        public ItemHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewRating = itemView.findViewById(R.id.text_view_rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos= getAdapterPosition();
                    if(listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(pos),pos);
                    }
                }
            });
        }
    }

    public interface onItemClickListener{
        public void onItemClick(DocumentSnapshot documentSnapshot,int pos);
    }
    public void setOnClickListener(onItemClickListener listener){
        this.listener=listener;
    }
}