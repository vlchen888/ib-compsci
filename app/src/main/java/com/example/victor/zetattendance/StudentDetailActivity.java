package com.example.victor.zetattendance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.victor.database.Period;
import com.victor.database.PeriodsDAO;
import com.victor.database.Student;
import com.victor.database.StudentsDAO;


public class StudentDetailActivity extends Activity {
    public static final String PERIOD_ID_ARG = "period_id";
    public static final String STUDENT_ID_ARG = "student_id";
    private Period selectedPeriod;
    private Student selectedStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            PeriodsDAO dao = new PeriodsDAO(this);
            Bundle arguments = new Bundle();
            try {
                long periodId = getIntent().getLongExtra(PERIOD_ID_ARG, 0);
                dao.open();
                selectedPeriod = dao.getPeriodForId(periodId);
                setTitle(selectedPeriod.getName());
                dao.close();
                setTitle(selectedPeriod.getName());
                long studentId = getIntent().getLongExtra(STUDENT_ID_ARG, 0);
                if (studentId == 0) {
                    Button saveButton = (Button)findViewById(R.id.save_button);
                    saveButton.setText("New Student");
                }
            } catch (Exception e) {

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, PeriodDetailActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveStudent(View view) {
        EditText firstNameText = (EditText)findViewById(R.id.first_name);
        EditText lastNameText = (EditText) findViewById(R.id.last_name);
        String fName = firstNameText.getText().toString();
        String lName = lastNameText.getText().toString();

        if (lName.length() == 0 || fName.length() == 0) {
            return;
        }
        if (selectedStudent == null) {
            Student newStudent = new Student(fName, lName);
            newStudent.setPeriodId(selectedPeriod.getId());
            StudentsDAO studentDao = new StudentsDAO(this);
            try {
                studentDao.open();
                studentDao.addStudentToPeriod(newStudent, selectedPeriod.getId());
                studentDao.close();
            } catch (Exception e) {
            }
        }
        NavUtils.navigateUpTo(this, new Intent(this, PeriodDetailActivity.class));
    }
}
