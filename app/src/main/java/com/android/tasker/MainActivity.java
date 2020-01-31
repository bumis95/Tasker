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

    private Calendar calendar = Calendar.getInstance();

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
                            stopTasker();
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

        currentDay = QueryPreferences.getDayQuery(getApplicationContext());
        lastDay = QueryPreferences.getNumberOfDaysQuery(getApplicationContext());
        startDate = QueryPreferences.getDateQuery(getApplicationContext());

        if(currentDay == 0) {
            updateTitle();
            updateDate();
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
                        stopTasker();
                        return;
                    }
                    currentDay++;
                    QueryPreferences.setDayQuery(getApplicationContext(), currentDay);
                    mViewInfo.setText(R.string.tasksComplete);
                    updateTitle();
                    cancelCheckBoxes();
                    mFloatingActionButton.setEnabled(false);
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
                                firstRun();
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

    private void firstRun() {
        currentDay = 1;
        startDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(calendar.getTime());
        QueryPreferences.setDayQuery(getApplicationContext(), currentDay);
        QueryPreferences.setNumberOfDaysQuery(getApplicationContext(), lastDay);
        QueryPreferences.setDateQuery(getApplicationContext(), startDate);
        mViewInfo.setText(R.string.infoNotComplete);
        updateTitle();
        updateDate();
        cancelCheckBoxes();
        mFloatingActionButton.setEnabled(true);
        invalidateOptionsMenu();
    }

    private void stopTasker() {
        currentDay = 0;
        QueryPreferences.setDayQuery(getApplicationContext(), currentDay);
        QueryPreferences.setNumberOfDaysQuery(getApplicationContext(), currentDay);
        QueryPreferences.setDateQuery(getApplicationContext(), "");
        mViewInfo.setText("");
        updateTitle();
        updateDate();
        cancelCheckBoxes();
        mFloatingActionButton.setEnabled(false);
        invalidateOptionsMenu();
    }

    private void cancelCheckBoxes() {
        mTask1.setChecked(false);
        mTask2.setChecked(false);
        mTask3.setChecked(false);
        mTask4.setChecked(false);
    }

    public void checkCondition() {
        String dateNow = //"02.01.20";
                new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(calendar.getTime());
        String dateStart = //"31.12.19";
                QueryPreferences.getDateQuery(getApplicationContext());

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        Date dateOne = null;
        Date dateTwo = null;
        try {
            dateOne = format.parse(dateNow);
            dateTwo = format.parse(dateStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendarNow = Calendar.getInstance();
        Calendar calendarStart = Calendar.getInstance();
        calendarNow.setTime(dateOne);
        calendarStart.setTime(dateTwo);
        calendarStart.add(Calendar.DAY_OF_MONTH, currentDay - 1);

        int daysBetween = calendarNow.get(Calendar.DAY_OF_YEAR) - calendarStart.get(Calendar.DAY_OF_YEAR);

        if(daysBetween > 0) {
            stopTasker();
            Toast toast = Toast.makeText(this, R.string.unsuccess, Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            updateTitle();
            updateDate();
            mViewInfo.setText(R.string.infoComplete);
            if(calendarNow.equals(calendarStart)) {
                mFloatingActionButton.setEnabled(true);
                mViewInfo.setText(R.string.infoNotComplete);
            }
        }
    }

    public void updateTitle() {
        if (currentDay == 0)
            mWhatDayToday.setText(R.string.notStart);
        else if(currentDay <= lastDay)
            mWhatDayToday.setText(getString(R.string.day, currentDay, lastDay));
    }

    public void updateDate() {
        if(currentDay == 0)
            mDate.setText("");
        else if(currentDay > 0)
            mDate.setText(getString(R.string.dateStart, startDate));
    }

}