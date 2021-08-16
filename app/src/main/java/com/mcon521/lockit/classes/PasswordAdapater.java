package com.mcon521.lockit.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mcon521.lockit.R;

import java.util.ArrayList;


public class PasswordAdapater extends RecyclerView.Adapter<PasswordAdapater.ViewHolder>{

    /*RecyclerView RecyclerView =  findViewById(R.id.myPasswordsListContainer);*/

    public Entries mPassWordList;
    private ArrayList<Entry> mData;
    private final String mKeyPrefsName = "MYPASSWORDLIST";
    private final String mMyList = "MYLIST";

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    private ArrayList<Entry> mPasswordEntry;

    public PasswordAdapater(ArrayList<Entry> arrayList) {
        mPasswordEntry = arrayList;
    }

    public PasswordAdapater(Context context, ArrayList<Entry> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.password_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.individual_password_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String passwordTitle = mData.get(position).getSite();
        holder.myTextView.setText(passwordTitle);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public String getItem(int id) {
        return mData.get(id).getSite();
    }

    public String getPass(int id) {
        return mData.get(id).getPassword();
    }

    public String getUSer(int id) {
        return mData.get(id).getUsername();
    }

    public String userAndPass(int id) {
        return "\n" + mData.get(id).getUsername() + "\n" + mData.get(id).getPassword() ;
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }



}
