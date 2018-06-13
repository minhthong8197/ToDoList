package com.itute.tranphieu.todolist;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.itute.tranphieu.todolist.object.Work;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class WorkList extends AppCompatActivity {
//
//    //Menu
//    DrawerLayout drawerLayout;
//    ActionBarDrawerToggle drawerToggle;
//    //
//    Toolbar toolbar;
//    RecyclerView rcvTodo;
//    FloatingActionButton fabAdd;
//    LinearLayoutManager llmTodo;
//    ArrayList<Work> workArrayList;
//    WorkAdapter workAdapter;
//
//    //Dialog
//    Dialog addDialog;
//    EditText edtWorkTitle;
//    ImageButton  btnAdd_Dialog;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        AnhXa();
//        CustomToolbar();
//        CreateVariables();
//        CustomNavigationDrawer();
//        ConfigureRecycleView();
//        ConfigureAddDialog();
//        setOnButtonClick();
//    }
//
//    private void CustomNavigationDrawer() {
//        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,
//                                                            R.string.navigation_drawer_open,
//                                                            R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(drawerToggle);
//    }
//
//    //Override behavior fof NavigationDrawer
//    @Override
//    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        drawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        drawerToggle.onConfigurationChanged(newConfig);
//    }
//    //End Override behavior fof NavigationDrawer
//
//    private void CustomToolbar() {
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        Drawable drawable = getResources().getDrawable(R.drawable.icon_menu);
//        getSupportActionBar().setHomeAsUpIndicator(drawable);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(drawerToggle.onOptionsItemSelected(item))
//        {
//            return true;
//        }
//        switch (item.getItemId())
//        {
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    private void setOnButtonClick() {
//        fabAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addDialog.show();
//            }
//        });
//    }
//
//    private void CreateVariables() {
//        workArrayList = new ArrayList<Work>();
//        workAdapter = new WorkAdapter(workArrayList,getApplicationContext());
//    }
//
//    private void ConfigureAddDialog() {
//        //Mapping
//        addDialog = new Dialog(this);
//        addDialog.setContentView(R.layout.custom_dialog_newwork);
//        addDialog.setCancelable(true);
//        //
//        edtWorkTitle = (EditText) addDialog.findViewById(R.id.cdl_add_edittextWorkTitle);
//        btnAdd_Dialog = (ImageButton) addDialog.findViewById(R.id.cdl_add_button_add);
//        btnAdd_Dialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                workArrayList.add(0,new Work(edtWorkTitle.getText().toString()+"",false));
//                addDialog.dismiss();
//                workAdapter.notifyItemInserted(0);
//            }
//        });
//        addDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                edtWorkTitle.setText("");
//            }
//        });
//        addDialog.getWindow().setGravity(Gravity.BOTTOM);
//
//    }
//
//    private void ConfigureRecycleView() {
//        llmTodo = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, llmTodo.getOrientation());
//        rcvTodo.addItemDecoration(dividerItemDecoration);
//        rcvTodo.setItemAnimator(new SlideInUpAnimator());
//        rcvTodo.setLayoutManager(llmTodo);
//        for(int i=0;i<10;i++)
//        {
//            workArrayList.add(new Work("Ví dụ "+i,false));
//        }
//        rcvTodo.setAdapter(workAdapter);
//    }
//
//    private void AnhXa() {
//        //Main activity
//        rcvTodo = (RecyclerView) findViewById(R.id.RecycleViewToDo);
//        fabAdd = (FloatingActionButton) findViewById(R.id.FloatingActionButtonAdd);
//        //
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        //Menu
//        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer);
//    }
}
