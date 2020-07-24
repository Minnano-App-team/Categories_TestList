package com.example.categeory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class catAdapter extends FirestoreRecyclerAdapter<catObject,catAdapter.ItemHolder> {
    private onItemClickListener listener;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public catAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemHolder itemHolder, int i, @NonNull catObject catObject) {
        itemHolder.catName.setText(catObject.getName());

    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_layout,parent,false);
        return new ItemHolder(v);
    }



    public class ItemHolder extends RecyclerView.ViewHolder {
        TextView catName ;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.Cat_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(pos),pos);
                    }
                }
            });
        }
    }

    public interface onItemClickListener{
        public void onItemClick(DocumentSnapshot documentSnapshot,int position);
    }

    public void setOnClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}
