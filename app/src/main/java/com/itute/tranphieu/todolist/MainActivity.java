package com.itute.tranphieu.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itute.tranphieu.todolist.notify.WorkNotification;
import com.itute.tranphieu.todolist.object.GroupWork;
import com.itute.tranphieu.todolist.object.Work;
import com.itute.tranphieu.todolist.sort.DateTimeSorter;
import com.itute.tranphieu.todolist.sort.FinishedSorter;
import com.itute.tranphieu.todolist.sort.Sorter;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

import static com.itute.tranphieu.todolist.LoginActivity.mGoogleSignInClient;

public class MainActivity extends AppCompatActivity {

    //Menu
    static DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    ImageView imgAvatar;
    TextView txtUsername, txtUserEmail, txtNewGroup;
    RecyclerView rcvGroup;
    GroupAdapter groupAdapter;
    ArrayList<String> groupWorkArrayList;

    //Toolbar
    Toolbar toolbar;
    public static CollapsingToolbarLayout collapsingToolbarLayout;
    //RecycleViewWork
    RecyclerView rcvTodo;
    static WorkAdapter workAdapter;
    static ArrayList<Work> workArrayList;
    //UI
    FloatingActionButton fabAdd;
    Intent intent_Login;
    Sorter sorter;
    //Dialog Add Work
    Dialog addWorkDialog;
    EditText edtWorkTitle;
    ImageButton  btnAddWork_Dialog;
    //Dialog Add Work Group
    Dialog addGroupDialog;
    EditText edtGroupName;
    ImageButton btnAddGroup_Dialog;

    //Authentication
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    AstLoadUserData astLoadUserData;

    //Firebase
    String userID;
    public static DatabaseReference mDataUserRoot, mDataGroupRoot;
    final String DEFAULT_TAB = "My ToDo";
    //Linster
    static ChildEventListener userChildListener, groupChildListener;

    //Notification
    WorkNotification workNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //For System Software
        AnhXa();
        CreateVariables();
        CustomToolbar(DEFAULT_TAB);
        CustomNavigationDrawer();
        ConfigureRecycleViewWork();
        ConfigureRecycleViewGroup();
        ConfigureAddWorkDialog();
        ConfigureAddGroupDialog();
        setOnButtonClick();
        //Custom function
        getUserData();
        setUserDataBase();
        SetListenerforRefFireBase();
    }
    private void CreateVariables() {
        workArrayList = new ArrayList<Work>();
        workAdapter = new WorkAdapter(workArrayList,getApplicationContext());
        groupWorkArrayList = new ArrayList<String>();
        groupAdapter = new GroupAdapter(groupWorkArrayList,getApplicationContext());
        astLoadUserData = new AstLoadUserData();
        intent_Login = new Intent(this,LoginActivity.class);
        userID="";
        workNotification = new WorkNotification(MainActivity.this);
    }
    private void getUserData() {
        //Get UserData from Google Server using Asyntask
        try{
            ArrayList<String> arrayList = astLoadUserData.execute(mAuth).get();
            if(arrayList.size()>0) {
                txtUsername.setText(arrayList.get(1));
                txtUserEmail.setText(arrayList.get(2));
                imgAvatar.setImageBitmap(new GetImage().execute(arrayList.get(3)).get());
                userID = arrayList.get(0).toString();
                Log.d("Email người dùng",userID);
            }
            else
            {
                Log.d("GetUserData","Không load được thông tin người dùng");
            }
        }
        catch (Exception e)
        {
            Log.d("GetUserData","Exception load thông tin người dùng" + e.getMessage());
        }
    }
    private void setUserDataBase()
    {
        if(userID!="") {
            mDataUserRoot = FirebaseDatabase.getInstance().getReference().child(userID);
            mDataGroupRoot = mDataUserRoot.child(DEFAULT_TAB);
        }
        else
        {
            Log.d("SetUserDatabase","Không có userEmail" + userID);
        }
    }
    //
    //Firebase Listener
    void SetListenerforRefFireBase()
    {
        //Event for Group Work
        userChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                groupWorkArrayList.add(dataSnapshot.getKey().toString());
                CleanArrayList.CleanSameValue(groupWorkArrayList);
                groupAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                groupWorkArrayList.remove(dataSnapshot.getKey().toString());
                groupAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } ;
        mDataUserRoot.addChildEventListener(userChildListener);

        //Event for Work
        groupChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                workArrayList.add(dataSnapshot.getValue(Work.class));
                CleanArrayList.CleanSameWork(workArrayList);
                workNotification.setNotify(workArrayList);
                workAdapter.notifyItemInserted(workArrayList.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                workAdapter.notifyDataSetChanged();
                workNotification.setNotify(workArrayList);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                workArrayList.remove(dataSnapshot.getValue(Work.class));
//                workAdapter.notifyDataSetChanged();
                workArrayList.clear();
                workAdapter.notifyDataSetChanged();
                mDataGroupRoot.removeEventListener(groupChildListener);
                mDataGroupRoot.addChildEventListener(groupChildListener);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                workAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDataGroupRoot.addChildEventListener(groupChildListener);
    }


    //


    // Override
    @Override
    protected void onStart() {
        super.onStart();
        getUserData();
        setUserDataBase();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getUserData();
        setUserDataBase();
    }
    //Override behavior fof NavigationDrawer
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
    //End Override behavior fof NavigationDrawer

    //Override behavior for Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        switch (item.getItemId())
        {
            case R.id.menu_logout:
                startActivity(intent_Login);
                finish();
                mAuth = FirebaseAuth.getInstance();
                Log.d("Auth",mAuth.getUid().toString());
                mAuth.signOut();
                mGoogleSignInClient.signOut();
                break;
            case R.id.menu_deleteGroup:
                if(mDataGroupRoot.equals(mDataUserRoot.child(DEFAULT_TAB)))
                {
                    Toast.makeText(this, "Can't delete Defaut Group", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    AlertDialog.Builder delalert =  new AlertDialog.Builder(MainActivity.this);
                    delalert.setTitle("Delete group");
                    delalert.setMessage("Are you sure?");
                    delalert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDataGroupRoot.removeValue();
                            try
                            {
                                groupWorkArrayList.remove(collapsingToolbarLayout.getTitle().toString());
                                groupAdapter.notifyDataSetChanged();
                            }
                            catch (Exception e)
                            {

                            }
                            mDataGroupRoot = mDataUserRoot.child(DEFAULT_TAB);
                            collapsingToolbarLayout.setTitle(DEFAULT_TAB);
                            workArrayList.clear();
                            workAdapter.notifyDataSetChanged();
                            mDataGroupRoot.removeEventListener(groupChildListener);
                            mDataGroupRoot.addChildEventListener(groupChildListener);
                            Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
                    delalert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    delalert.create();
                    delalert.show();
                }
                break;
            case R.id.menu_sort_finished:
                sorter = new FinishedSorter();
                sorter.sort(workArrayList);
                workAdapter.notifyDataSetChanged();
                break;
            case R.id.menu_sort_time:
                sorter = new DateTimeSorter();
                sorter.sort(workArrayList);
                workAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //End Override behavior for Menu

    //Finished
    private void setOnButtonClick() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWorkDialog.show();
            }
        });
        txtNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGroupDialog.show();
            }
        });
    }

    private void CustomNavigationDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
    }
    protected void CustomToolbar(String title) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(!title.isEmpty()) {
            getSupportActionBar().setTitle(title);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.icon_menu);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
    }
    private void ConfigureAddWorkDialog() {
        //Mapping
        addWorkDialog = new Dialog(this);
        addWorkDialog.setContentView(R.layout.custom_dialog_newwork);
        edtWorkTitle = (EditText) addWorkDialog.findViewById(R.id.cdl_add_edittextWorkTitle);
        btnAddWork_Dialog = (ImageButton) addWorkDialog.findViewById(R.id.cdl_add_button_add);
        //
        addWorkDialog.setCancelable(true);
        btnAddWork_Dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidateInput.validateWorkTitle(edtWorkTitle.getText().toString())) {
                    //
                    //Get unique key with null object
                    String key = mDataGroupRoot.push().getKey();
                    Work work = new Work(key, edtWorkTitle.getText().toString());
                    //
                    mDataGroupRoot.child(key).setValue(work);
                    addWorkDialog.dismiss();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Work title is characters and numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });
        addWorkDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                edtWorkTitle.setText("");
            }
        });
        addWorkDialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    private void ConfigureAddGroupDialog()
    {
        //Mapping
        addGroupDialog = new Dialog(this);
        addGroupDialog.setContentView(R.layout.custom_dialog_newgroup);
        edtGroupName = (EditText) addGroupDialog.findViewById(R.id.cdl_newgroup_edittextName);
        btnAddGroup_Dialog = (ImageButton) addGroupDialog.findViewById(R.id.cdl_newgroup_button_add);
        //
        addGroupDialog.setCancelable(true);
        //
        btnAddGroup_Dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = edtGroupName.getText().toString();
                if(ValidateInput.validateGroupName(groupName))
                {
                    if(!groupWorkArrayList.contains(groupName))
                    {
                        groupWorkArrayList.add(groupName);
                        groupAdapter.notifyItemInserted(groupWorkArrayList.size()-1);
                        addGroupDialog.dismiss();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "This name is already exists", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Group name is 20 character and numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //
        addGroupDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                edtGroupName.setText("");
            }
        });
        addGroupDialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    private void ConfigureRecycleViewWork() {
        LinearLayoutManager llmTodo = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, llmTodo.getOrientation());
        rcvTodo.addItemDecoration(dividerItemDecoration);
        rcvTodo.setItemAnimator(new SlideInUpAnimator());
        rcvTodo.setLayoutManager(llmTodo);
        rcvTodo.setAdapter(workAdapter);
    }
    private void ConfigureRecycleViewGroup()
    {
        LinearLayoutManager llmGroup = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, llmGroup.getOrientation());
        rcvGroup.setItemAnimator(new SlideInUpAnimator());
        rcvGroup.addItemDecoration(dividerItemDecoration);
        rcvGroup.setLayoutManager(llmGroup);
        rcvGroup.setAdapter(groupAdapter);
    }
    private void AnhXa() {
        //Main activity
        rcvTodo = (RecyclerView) findViewById(R.id.RecycleViewToDo);
        fabAdd = (FloatingActionButton) findViewById(R.id.FloatingActionButtonAdd);
        //
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //Menu
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer);
        imgAvatar = (ImageView) findViewById(R.id.ImageViewAvatar);
        txtUsername = (TextView) findViewById(R.id.TextViewUsername);
        txtUserEmail = (TextView) findViewById(R.id.TextViewUserEmail);
        txtNewGroup = (TextView) findViewById(R.id.TextViewAddGroup);
        rcvGroup = (RecyclerView) findViewById(R.id.nvb_RecycleView);
    }
    //End Finished
}
