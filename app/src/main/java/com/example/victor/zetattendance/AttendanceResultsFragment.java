package com.example.victor.zetattendance;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;


import com.example.victor.zetattendance.dummy.DummyContent;
import com.victor.database.PeriodsDAO;
import com.victor.database.Student;
import com.victor.database.StudentsDAO;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p />
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p />
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class AttendanceResultsFragment extends Fragment implements AbsListView.OnItemClickListener {

    private OnFragmentInteractionListener mListener;
    private List<Student> students;
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AttendanceResultsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            StudentsDAO studentsDAO = new StudentsDAO(getActivity());
            try {
                studentsDAO.open();
                students = studentsDAO.getBadStudents();
                mAdapter = new BadStudentAdapter(
                        getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        students);
                studentsDAO.close();
            } catch (Exception e) {
                Log.i("sumtingwong", "error", e);
            }

        // TODO: Change Adapter to display your content
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendanceresultsfragment, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.attendance_results_list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
    * This interface must be implemented by activities that contain this
    * fragment to allow an interaction in this fragment to be communicated
    * to the activity and potentially other fragments contained in that
    * activity.
    * <p>
    * See the Android Training lesson <a href=
    * "http://developer.android.com/training/basics/fragments/communicating.html"
    * >Communicating with Other Fragments</a> for more information.
    */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    public class BadStudentAdapter extends ArrayAdapter<Student> {
            private List<Student> students;
            public BadStudentAdapter(Context context, int textViewResourceId, List<Student> items) {
                super(context,textViewResourceId,items);
                students=items;
            }
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v=vi.inflate(R.layout.student_attendance_row, null);
                }
                Student s = students.get(position);
                if(s!=null) {
                    TextView t1 = (TextView) v.findViewById(R.id.attendancePeriod);
                    TextView t2 = (TextView) v.findViewById(R.id.attendanceName);
                    TextView t3 = (TextView) v.findViewById(R.id.attendanceStatus);
                    if (t1 != null && t2 != null && t3 != null) {
                        PeriodsDAO periodsDAO = new PeriodsDAO(getActivity());
                        try {
                            periodsDAO.open();
                            t1.setText(periodsDAO.getPeriodForId(s.getPeriodId()).toString());
                            t2.setText(s.toString());
                            t3.setText(s.getStatus().toString());
                            periodsDAO.close();
                        }
                        catch(Exception e)
                        {

                        }
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
