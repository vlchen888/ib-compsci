package com.example.victor.zetattendance;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.victor.database.Student;
import com.victor.database.StudentsDAO;

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
    private ArrayAdapter<Student> mStudentsAdapter;

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
                mStudentsAdapter = new StudentAttendanceAdapter(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        students);
                setListAdapter(mStudentsAdapter);
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
        mStudentsAdapter.notifyDataSetChanged();
    }

    public class StudentAttendanceAdapter extends ArrayAdapter<Student> {
        private List<Student> students;
        public StudentAttendanceAdapter(Context context, int textViewResourceId, List<Student> items) {
            super(context,textViewResourceId,items);
            students=items;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.student_row, null);
            }
            /*
            v.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    //do your sorting stuff here
                }
            });
            */
            Student s = students.get(position);
            if(s!=null) {
                TextView t = (TextView) v.findViewById(R.id.studentName);
                TextView u = (TextView) v.findViewById(R.id.studentStatus);
                if (t != null && u != null) {
                    t.setText(s.toString());
                    u.setText(s.getStatus().toString());
                    switch(s.getStatus()) {
                        case PRESENT:
                            v.setBackgroundColor(0xFFFFFFFF);
                            break;
                        case ABSENT:
                            v.setBackgroundColor(0xFFFF6666);
                            break;
                        case TARDY:
                            v.setBackgroundColor(0xFFFFFF00);
                            break;
                    }
                }
            }
            return v;
        }
    }
}
