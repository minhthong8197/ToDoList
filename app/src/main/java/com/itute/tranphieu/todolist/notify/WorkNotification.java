package com.itute.tranphieu.todolist.notify;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.itute.tranphieu.todolist.object.Work;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class WorkNotification {
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Intent notifyIntent;
    private Context context;
    Calendar calendar;

    public WorkNotification(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        this.notifyIntent = new Intent(context, WorkNotificationReceiver.class);
        calendar = Calendar.getInstance();
    }

    public void setNotify(ArrayList<Work> works) {
        for (int i = 0; i < works.size(); i++) {
            try {
                //xoa đi những thông báo dã lên lịch để lên lịch lại (dùng sau khi có thông báo bị sửa đổi)
                PendingIntent.getBroadcast(context, works.get(i).getId(), notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT).cancel();
                Log.d("           ", "Xoa thanh cong");
            } catch (Exception e) {
                //e.printStackTrace();
                Log.d("           ", "Loi khi xoa pendingIntent");
            }
        }

        for (int i = 0; i < works.size(); i++) {
            try {
                Work work = works.get(i);
                calendar.set(work.getTime().getYear(), work.getTime().getMonth() - 1, work.getTime().getDay(), work.getTime().getHour(), work.getTime().getMinute());
                Long timeToNotify = calendar.getTimeInMillis();
                //kiểm tra nếu thời gian cần thông báo chưa trôi qua thì sẽ lên lịch
                if (timeToNotify >= Calendar.getInstance().getTimeInMillis()) {
                    notifyIntent.removeExtra("description");
                    notifyIntent.removeExtra("title");
                    notifyIntent.putExtra("description", work.getDescription());
                    notifyIntent.putExtra("title", work.getTime().toString() + "     " + work.getTitle());
                    pendingIntent = PendingIntent.getBroadcast(context, work.getId(), notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    //alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000L, pendingIntent);//test only
                    alarmManager.set(AlarmManager.RTC_WAKEUP, timeToNotify, pendingIntent);
                }
            }
            catch (Exception e)
            {

            }
        }
    }
}
