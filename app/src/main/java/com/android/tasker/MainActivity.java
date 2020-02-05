package com.android.tasker;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView mWhatDayToday;
    private TextView mViewInfo;
    private CheckBox mTask1;
    private CheckBox mTask2;
    private CheckBox mTask3;
    private CheckBox mTask4;
    private MenuItem mMenuItem;
    private TextView mDate;
    private FloatingActionButton mFloatingActionButton;

    Challenge challenge;

    private int currentDay;
    private int lastDay;
    private String startDate = "";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMenuItem = menu.findItem(R.id.btn_menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(currentDay != 0) {
            mMenuItem.setTitle(R.string.del);
            mMenuItem.setIcon(R.drawable.sharp_delete_forever_white_48);
        }
        else {
            mMenuItem.setTitle(R.string.start);
            mMenuItem.setIcon(R.drawable.sharp_add_white_48);
        }
        return true;
    }

    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }

    public void menuClick(MenuItem item) {
        if(currentDay == 0) {
            showAddDaysDialog(MainActivity.this);
        }
        else {
            new AlertDialog.Builder( this)
                    .setTitle(R.string.del)
                    .setMessage(R.string.delQuestion)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            control(0, 0, "", false);
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        challenge = new Challenge(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mWhatDayToday = findViewById(R.id.currentNumberOfDay);
        mViewInfo = findViewById(R.id.viewInfo);
        mTask1 = findViewById(R.id.chkboxTask1);
        mTask2 = findViewById(R.id.chkboxTask2);
        mTask3 = findViewById(R.id.chkboxTask3);
        mTask4 = findViewById(R.id.chkboxTask4);
        mDate = findViewById(R.id.viewStartDate);

        mFloatingActionButton = findViewById(R.id.floating_action_button);
        mFloatingActionButton.setEnabled(false);

        currentDay = challenge.getCurrentDay();
        lastDay = challenge.getLastDay();
        startDate = challenge.getStartDate();

        if(currentDay == 0) {
            update();
        }
        else
            checkCondition();

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTask1.isChecked() && mTask2.isChecked()
                        && mTask3.isChecked() && mTask4.isChecked()) {
                    if(currentDay == lastDay) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(R.string.success)
                                .setMessage(R.string.successComplete)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                        control(0, 0, "", false);
                        return;
                    }
                    control(2, lastDay, getString(R.string.tasksComplete), false);
                }
                else {
                    Snackbar snackbar = Snackbar.make(
                            view, R.string.tasksNoComplete, Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });

    }

    private void showAddDaysDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        taskEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        taskEditText.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        taskEditText.setLongClickable(false);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle(R.string.start)
                .setMessage(R.string.hint)
                .setView(taskEditText)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int num;
                        try
                        {
                            num = Integer.parseInt(String.valueOf(taskEditText.getText()).trim());
                            if(num > 0) {
                                lastDay = num;
                                control(1, lastDay, getString(R.string.infoNotComplete), true);
                            }
                            else {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        R.string.error, Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                        catch (NumberFormatException e)
                        {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    R.string.error, Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                })
                .setNegativeButton(R.string.no, null)
                .create();
        dialog.show();
    }

    private class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }

    private void control(int i, int dayLast, String info, boolean b) {
        if(i == 0) {
            currentDay = 0;
            startDate = "";
        }
        else if(i == 1) {
            currentDay = 1;
            startDate = new SimpleDateFormat("dd.MM.yyyy",
                                        Locale.getDefault()).format(DateChecker.calendarNow.getTime());
        }
        else {
            currentDay++;
        }
        challenge.setCurrentDay(getApplicationContext(), currentDay);
        challenge.setLastDay(getApplicationContext(), dayLast);
        challenge.setStartDate(getApplicationContext(), startDate);
        mViewInfo.setText(info);
        update();
        cancelCheckBoxes();
        mFloatingActionButton.setEnabled(b);
        invalidateOptionsMenu();
    }

    private void cancelCheckBoxes() {
        mTask1.setChecked(false);
        mTask2.setChecked(false);
        mTask3.setChecked(false);
        mTask4.setChecked(false);
    }

    public void checkCondition() {
        if(DateChecker.check(currentDay, startDate) > 0) {
            control(0, 0, "", false);
            Toast toast = Toast.makeText(this, R.string.unsuccess, Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            update();
            mViewInfo.setText(R.string.infoComplete);
            if(DateChecker.calendarNow.equals(DateChecker.calendarStart)) {
                mFloatingActionButton.setEnabled(true);
                mViewInfo.setText(R.string.infoNotComplete);
            }
        }
    }

    public void update() {
        if (currentDay == 0) {
            mWhatDayToday.setText(R.string.notStart);
            mDate.setText("");
        }
        else if(currentDay <= lastDay) {
            mWhatDayToday.setText(getString(R.string.day, currentDay, lastDay));
            mDate.setText(getString(R.string.dateStart, startDate));
        }
    }

}