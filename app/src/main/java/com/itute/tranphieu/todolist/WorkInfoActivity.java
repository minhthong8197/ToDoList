package com.itute.tranphieu.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.itute.tranphieu.todolist.object.Time;
import com.itute.tranphieu.todolist.object.Work;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import static com.itute.tranphieu.todolist.LoginActivity.mGoogleSignInClient;


public class WorkInfoActivity extends AppCompatActivity {
    EditText edtWorkTitle, edtNote, edtRepeat;
    TextView txtTime, txtDay;
    CheckBox cbFinised;
    Toolbar toolbar;
    ImageButton btnDelete;
    Work work;
    Time time;
    int position;

    //PickerDialog
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_info);
        AnhXa();
        CustomToolbar();
        getData();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mDataGroupRoot.child(MainActivity.workArrayList.get(position).getKey()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        Log.d("Sự kiện xóa thành công:","Đã xóa");
                        finish();
                    }
                });
            }
        });
        txtDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int date = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(WorkInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if(time==null)
                        {
                            time =new Time(dayOfMonth, month, year);
                        }
                        else {
                           time.setDay(dayOfMonth);
                           time.setMonth(month);
                           time.setYear(year);
                        }
                        txtDay.setText(time.daytoString());
                        txtTime.setText(time.toString());
                    }
                },year,month,date);
                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if(time!=null) {
                            time = null;
                            txtDay.setText("No Day");
                            txtTime.setText("No Time");
                        }
                    }
                });
                datePickerDialog.show();
            }
        });
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TimePickerDialog timePickerDialog = new TimePickerDialog(WorkInfoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(time == null)
                        {
                            time = new Time(hourOfDay,minute);

                        }
                        else
                        {
                            time.setHour(hourOfDay);
                            time.setMinute(minute);
                        }
                        txtTime.setText(time.toString());
                        txtDay.setText(time.daytoString());
                    }
                },0,0,true);
                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if(time!=null)
                        {
                            time.setMinute(0);
                            time.setHour(0);
                            txtTime.setText(time.toString());
                        }
                    }
                });
                timePickerDialog.show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
    }

    private void getData()
    {
        Intent intent= getIntent();
        position =  intent.getIntExtra("DATA",0);
        work = MainActivity.workArrayList.get(position);
        //Sure have data
        edtWorkTitle.setText(work.getTitle());
        cbFinised.setChecked(work.isFinished());
        //
        try {
            if (work.getTime() == null || work.getTime().getDay() == 0 || work.getTime().getYear() == 0) {
                txtTime.setText("No Time");
                txtDay.setText("No Day");
                time = null;
            } else {
                txtTime.setText(work.getTime().toString());
                txtDay.setText(work.getTime().daytoString());
                time=work.getTime();
            }
            edtRepeat.setText(String.valueOf(work.getRepeat()));
            if (!work.getDescription().trim().equals("")) {
                edtNote.setText(work.getDescription());
            }
        }
        catch (Exception e)
        {
            Log.d("Có lỗi get dữ liệu: ",e.getMessage());
        }
    }
    private void setDataToArray()
    {
        //Sure have data
        work.setTitle(edtWorkTitle.getText().toString());
        work.setFinished(cbFinised.isChecked());
        //
        try {
            if (!edtRepeat.getText().equals("")) {
                work.setRepeat(Integer.parseInt(edtRepeat.getText().toString()));
            } else
                work.setRepeat(0);
            if (!edtNote.getText().equals("Note") && !edtNote.getText().toString().trim().equals("")) {
                work.setDescription(edtNote.getText().toString());
            }
            if (time == null || time.getDay()==0 || time.getYear()==0) {
                work.setTime(null);
            }
            else
                work.setTime(time);
        }
        catch (Exception e)
        {
            Log.d("Có lỗi set dữ liệu", e.getMessage());
        }
        MainActivity.workArrayList.set(position,work);
        MainActivity.mDataGroupRoot.child(work.getKey()).setValue(work);
    }
    //Override behavior for Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save:
                setDataToArray();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.workinfo_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //End Override behavior for Menu
    protected void CustomToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void AnhXa() {
        edtWorkTitle = (EditText) findViewById(R.id.info_EditTextWorkTitle);
        edtNote = (EditText) findViewById(R.id.info_EditTextNote);
        txtTime = (TextView) findViewById(R.id.info_Time);
        txtDay = (TextView) findViewById(R.id.info_Day);
        edtRepeat = (EditText) findViewById(R.id.info_EditTextRepeat);
        cbFinised = (CheckBox) findViewById(R.id.info_checkbox);
        btnDelete = (ImageButton) findViewById(R.id.info_imagebutton_delete);
        toolbar = (Toolbar) findViewById(R.id.info_toolbar);
    }
}
