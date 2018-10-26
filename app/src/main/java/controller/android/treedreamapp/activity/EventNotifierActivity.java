package controller.android.treedreamapp.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import controller.android.treedreamapp.R;
import me.everything.providers.android.calendar.Calendar;
import me.everything.providers.android.calendar.CalendarProvider;
import me.everything.providers.android.calendar.Event;
import me.everything.providers.core.Data;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

public class EventNotifierActivity extends AppCompatActivity {

    private RecyclerView eventslist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gift_category);

       // eventslist = (RecyclerView) findViewById(R.id.giftCategory);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

       // eventslist.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
       // eventslist.setLayoutManager(mLayoutManager);

        if (ContextCompat.checkSelfPermission(EventNotifierActivity.this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(EventNotifierActivity.this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(EventNotifierActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EventNotifierActivity.this, new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
        }else{
            readCalendar();
        }




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void readCalendar(){
    CalendarProvider calendarProvider = new CalendarProvider(EventNotifierActivity.this);
    List<Calendar> calendars = calendarProvider.getCalendars().getList();
    List<Event> eventList = new ArrayList<>();
    for(Calendar calendar: calendars){
        List<Event> events =  calendarProvider.getEvents(calendar.id).getList();
        for(Event event: events){
            eventList.add(event);
            Log.d("Events: "+event.ownerAccount,""+event.description);
        }

    }
}




}
