package com.example.victor.zetattendance;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.victor.database.Period;
import com.victor.database.Student;
import com.victor.database.StudentsDAO;

import java.util.ArrayList;
import java.util.List;


/**
 * A fragment representing a single Period detail screen.
 * This fragment is either contained in a {@link PeriodListActivity}
 * in two-pane mode (on tablets) or a {@link PeriodDetailActivity}
 * on handsets.
 */
public class PeriodDetailFragment extends ListFragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    private List<Student> students;

    public static final String ARG_ITEM_ID = "item_id";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PeriodDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            long periodId = getArguments().getLong(ARG_ITEM_ID);
            StudentsDAO studentsDAO = new StudentsDAO(getActivity());
            try {
                studentsDAO.open();
                students = studentsDAO.getStudentsForPeriodId(periodId);
                setListAdapter(new ArrayAdapter<Student>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        students));
            } catch (Exception e) {
                Log.i("sumtingwong", "error", e);
            }
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Student aStudent = (Student) l.getItemAtPosition(position);
        int currentStatus = aStudent.getStatus().ordinal();
        currentStatus++;
        if (currentStatus > 2) {
            currentStatus %= 3;
        }
        aStudent.setStatus(Student.AttendanceStatus.fromOrdinal(currentStatus));
        StudentsDAO studentsDAO = new StudentsDAO(getActivity());
        try {
            studentsDAO.open();
            studentsDAO.updateStudent(aStudent);
        } catch (Exception e) {
            Log.e("PeriodDetailFragment", "error", e);
        }
        l.refreshDrawableState();
    }
}
