package com.itute.tranphieu.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.itute.tranphieu.todolist.object.Work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.WorkViewHolder> {
    // Create a class ViewHolder to use for functiọn ViewHolder
//    public class WorkViewHolder extends RecyclerView.ViewHolder {
//        //Variable in XML custom_row_work
//        protected LinearLayout lWork;
//        public CheckBox cbWork;
//        public TextView txtWorkTitle;
//        public WorkViewHolder(View itemView) {
//            super(itemView);
//            lWork = (LinearLayout) itemView.findViewById(R.id.LayoutRowWork);
//            cbWork = (CheckBox) itemView.findViewById(R.id.checkboxWork);
//            txtWorkTitle = (TextView) itemView.findViewById(R.id.TextViewWorkTitle);
//        }
//    }

    ArrayList<Work> workList;
    Context context;
    private int clickPosition;
    Intent inforIntent;
    public WorkAdapter(ArrayList<Work> workList, Context context) {
        this.workList = workList;
        this.context = context;
    }
    DatabaseReference mDataWork;
    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.custom_row_work,parent,false);
        return new WorkViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final WorkViewHolder workViewHolder, final int position) {
        workViewHolder.txtWorkTitle.setText(workList.get(position).getTitle());
        workViewHolder.cbWork.setChecked(workList.get(position).isFinished());

        workViewHolder.setItemClickListener(new RecycleViewItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Work work = workList.get(position);
                inforIntent = new Intent(context.getApplicationContext(),WorkInfoActivity.class);
                inforIntent.putExtra("DATA",position);
                context.startActivity(inforIntent);
            }
        });
        workViewHolder.cbWork.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                workList.get(position).setFinished(isChecked);
                String key = workList.get(position).getKey().toString();
                Work work = workList.get(position);
                MainActivity.mDataGroupRoot.child(key).setValue(work);

            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return workList.size();
    }

    public class WorkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        protected LinearLayout lWork;
        protected CheckBox cbWork;
        protected TextView txtWorkTitle;

        private RecycleViewItemClickListener recycleViewItemClickListener;

        public WorkViewHolder(View itemView) {
            super(itemView);
            lWork = (LinearLayout) itemView.findViewById(R.id.LayoutRowWork);
            cbWork = (CheckBox) itemView.findViewById(R.id.checkboxWork);
            txtWorkTitle = (TextView) itemView.findViewById(R.id.TextViewWorkTitle);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            this.setIsRecyclable(false);
        }

        public void setItemClickListener(RecycleViewItemClickListener recycleViewItemClickListener) {
            this.recycleViewItemClickListener = recycleViewItemClickListener;
        }
        @Override
        public void onClick(View v) {
            recycleViewItemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            recycleViewItemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }

    }
}
