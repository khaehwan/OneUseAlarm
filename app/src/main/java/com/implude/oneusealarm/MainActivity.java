package com.implude.oneusealarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int MODE_SOUND=1, MODE_VIB=2;

    TimePicker timePicker;
    Button buttonVib, buttonSound, buttonAddAlarm;

    Animation anim_timepicker, anim_buttonsound,
                anim_buttonvib, anim_buttonaddalarm;

    int which=MODE_SOUND;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = (TimePicker)findViewById(R.id.timePicker);
        buttonVib = (Button)findViewById(R.id.VibButton);
        buttonSound = (Button)findViewById(R.id.SoundButton);
        buttonAddAlarm = (Button)findViewById(R.id.AddAlarmButton);

        buttonSound.setBackgroundResource(R.drawable.sound_clicked);

        anim_buttonaddalarm = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_buttonaddalarm);
        buttonAddAlarm.startAnimation(anim_buttonaddalarm);

        anim_buttonsound = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_buttonsound);
        buttonSound.startAnimation(anim_buttonsound);

        anim_buttonvib = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_buttonvib);
        buttonVib.startAnimation(anim_buttonvib);

        anim_timepicker = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_timepicker);
        timePicker.startAnimation(anim_timepicker);


        buttonVib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(which==MODE_SOUND) {
                    buttonVib.setBackgroundResource(R.drawable.vib_clicked);
                    buttonSound.setBackgroundResource(R.drawable.sound);

                    which=MODE_VIB;
                }
            }
        });

        buttonSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(which==MODE_VIB) {
                    buttonVib.setBackgroundResource(R.drawable.vib);
                    buttonSound.setBackgroundResource(R.drawable.sound_clicked);

                    which=MODE_SOUND;
                }
            }
        });

        buttonAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, hour);
                cal.set(Calendar.MINUTE, minute);
                cal.set(Calendar.SECOND, 1);

                Intent alarmIntent = new Intent("com.implude.oneusealarm.ALARM_START");
                alarmIntent.putExtra("Mode", which);

                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(
                                getApplicationContext(),
                                123,
                                alarmIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );

                AlarmManager alarmManager = (AlarmManager)
                        getSystemService(Context.ALARM_SERVICE);

                alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        cal.getTimeInMillis(),
                        pendingIntent
                );

                finish();
            }
        });
    }
}
