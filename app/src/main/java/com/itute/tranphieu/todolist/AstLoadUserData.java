package com.itute.tranphieu.todolist;

import android.os.AsyncTask;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AstLoadUserData extends AsyncTask<FirebaseAuth,Void,ArrayList<String>> {
    @Override
    protected ArrayList<String> doInBackground(FirebaseAuth... firebaseAuths) {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        ArrayList<String> stringArrayList = new ArrayList<String>();
        if(mUser!=null)
        {
            stringArrayList.add(mUser.getUid().toString());
            stringArrayList.add(mUser.getDisplayName().toString());
            stringArrayList.add(mUser.getEmail().toString());
            stringArrayList.add(mUser.getPhotoUrl().toString());
        }
        return stringArrayList;
    }

}
