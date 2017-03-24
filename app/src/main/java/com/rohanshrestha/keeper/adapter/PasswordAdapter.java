package com.rohanshrestha.keeper.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rohanshrestha.keeper.R;
import com.rohanshrestha.keeper.data.Credential;
import com.rohanshrestha.keeper.utils.Utils;

import java.util.ArrayList;

/**
 * Created by rohan on 2/6/17.
 */

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.MyViewHolder> {

    private ArrayList<Credential> credentials;
    private Context context;

    public PasswordAdapter(Context context, ArrayList<Credential> credentials) {
        this.context = context;
        this.credentials = credentials;
    }

    public Credential getItem(int position) {
        return credentials.get(position);
    }

    public void removeItem(int position) {
        credentials.remove(position);
        notifyItemRemoved(position);
    }

    public void replace(int position, Credential newCredential) {
        credentials.set(position, newCredential);
        notifyItemChanged(position);
    }

    public void addItem(Credential credential) {
        credentials.add(credential);
        notifyItemInserted(credentials.size() - 1);
    }

    public void addAll(ArrayList<Credential> credentials) {
        this.credentials = credentials;
        notifyDataSetChanged();
    }

    @Override
    public PasswordAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.password_row, parent, false);
        return new PasswordAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PasswordAdapter.MyViewHolder holder, int position) {
        Credential credential = credentials.get(position);
        holder.tv_title.setText(credential.title);
        holder.tv_username.setText(String.format("Username: %s", credential.username));
        holder.tv_password.setText(String.format("Password: %s", credential.password));
        holder.tv_date.setText(Utils.epochToDate(credential.createdTime));
    }

    @Override
    public int getItemCount() {
        return credentials.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout frameLayout;
        private ImageView iv_edit, iv_delete;
        private TextView tv_username, tv_password, tv_title, tv_date;

        public MyViewHolder(View itemView) {
            super(itemView);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.item_background);
            iv_edit = (ImageView) itemView.findViewById(R.id.iv_edit);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);

            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_password = (TextView) itemView.findViewById(R.id.tv_password);
            tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
        }

        public void setRightSwipeView() {
            frameLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
            iv_edit.setVisibility(View.VISIBLE);
            iv_delete.setVisibility(View.INVISIBLE);
        }

        public void setLeftSwipeView() {
            frameLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
            iv_edit.setVisibility(View.INVISIBLE);
            iv_delete.setVisibility(View.VISIBLE);
        }
            /*@Override
            public boolean onTouch(View view, MotionEvent event) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        //Toast.makeText(context, "from adapter", Toast.LENGTH_LONG).show();
                        showPopupMenu(view, getAdapterPosition());
                        return true;
                }
                return false;
            }*/
    }
}
