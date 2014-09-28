package com.example.victor.zetattendance;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
public class PeriodDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
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
                List<Student> students = studentsDAO.getStudentsForPeriodId(periodId);

                /*
                setListAdapter(new ArrayAdapter<Student>(
                        getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        android.R.id.text1,
                        students));
                        */
            } catch (Exception e) {

            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_period_detail, container, false);

        // Show the dummy content as text in a TextView.
        return rootView;
    }
}
