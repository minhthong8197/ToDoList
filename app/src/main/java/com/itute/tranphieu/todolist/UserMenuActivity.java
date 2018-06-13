package com.itute.tranphieu.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class UserMenuActivity extends AppCompatActivity {

    ImageView imgAvatar;
    TextView txtUsername, txtUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        AnhXa();
    }

    private void AnhXa() {
        imgAvatar = (ImageView) findViewById(R.id.ImageViewAvatar);
        txtUsername = (TextView) findViewById(R.id.TextViewUsername);
        txtUserEmail = (TextView) findViewById(R.id.TextViewUserEmail);
    }
}
