package com.example.canicall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder>{
    public List<userDetails> userDetails;
    public myAdapter(List<userDetails> userDetails){
        this.userDetails=userDetails;
    }
    @NonNull
    @Override
    public myAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myAdapter.myViewHolder holder, int position) {
        int res = userDetails.get(position).getImageView();
        String nme = userDetails.get(position).getNameOfUser();
        String userStats = userDetails.get(position).getUserStatus();
        holder.setData(res,nme,userStats);
    }

    @Override
    public int getItemCount() {
        return userDetails.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameOnItem;
        private TextView userStatusOnItem;
        private ImageView userDpImage;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameOnItem = itemView.findViewById(R.id.listUserName);
            userStatusOnItem = itemView.findViewById(R.id.listUserStatus);
            userDpImage = itemView.findViewById(R.id.dpImage);
        }

        public void setData(int res, String nme, String userStats) {
            userDpImage.setImageResource(res);
            userNameOnItem.setText(nme);
            userStatusOnItem.setText(userStats);
        }
    }
}
