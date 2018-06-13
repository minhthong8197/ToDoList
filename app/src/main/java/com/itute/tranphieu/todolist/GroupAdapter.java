package com.itute.tranphieu.todolist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    ArrayList<String> groupWorkArrayList;
    Context context;

    public GroupAdapter(ArrayList<String> groupWorkArrayList, Context context) {
        this.groupWorkArrayList = groupWorkArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.custom_row_group, parent, false);
        return new GroupViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final GroupViewHolder holder, int position) {
        holder.txtGroupName.setText(groupWorkArrayList.get(position));

        holder.setItemClickListener(new RecycleViewItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                String groupName = groupWorkArrayList.get(position);
                MainActivity.mDataGroupRoot = MainActivity.mDataUserRoot.child(groupName);
                MainActivity.drawerLayout.closeDrawer(GravityCompat.START);
                MainActivity.collapsingToolbarLayout.setTitle(groupName);

                //Get New Value for Work List
                MainActivity.workArrayList.clear();
                MainActivity.workAdapter.notifyDataSetChanged();
                try {
                    MainActivity.mDataGroupRoot.removeEventListener(MainActivity.groupChildListener);
                } catch (Exception e) {
                    Log.d("Remove Listener", e.getMessage().toString());
                }
                MainActivity.mDataGroupRoot.addChildEventListener(MainActivity.groupChildListener);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return groupWorkArrayList.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        protected TextView txtGroupName;

        private RecycleViewItemClickListener recycleViewItemClickListener;

        public GroupViewHolder(View itemView) {
            super(itemView);
            txtGroupName = (TextView) itemView.findViewById(R.id.TextViewGroupName);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(RecycleViewItemClickListener recycleViewItemClickListener) {
            this.recycleViewItemClickListener = recycleViewItemClickListener;
        }

        @Override
        public void onClick(View v) {
            recycleViewItemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            recycleViewItemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }
    }
}
