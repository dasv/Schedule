package com.secretary.schedule;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.secretary.schedule.Adapter.LessonListAdapter;
import com.secretary.schedule.R;

import java.util.List;

public class TestActivity extends ListActivity {

    ListView lessonList;
    private LessonsDataSource datasource;
    private int mWeekDay = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        datasource = new LessonsDataSource(this);
        datasource.open();

        Lesson[] myList = datasource.readDayLessons(mWeekDay);
        lessonList = (ListView) findViewById(android.R.id.list);
        //lessonList.setAdapter(new LessonListAdapter(this, R.layout.row_layout, myList));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
