package com.example.victor.zetattendance;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.victor.database.Period;
import com.victor.database.PeriodsDAO;


/**
 * An activity representing a single Period detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link PeriodListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link PeriodDetailFragment}.
 */
public class PeriodDetailActivity extends Activity {

    private Period selectedPeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period_detail);


        // Show the Up button in the action bar.
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
                long periodId = getIntent().getLongExtra(PeriodDetailFragment.ARG_ITEM_ID, 0);
                dao.open();
                selectedPeriod = dao.getPeriodForId(periodId);
                setTitle(selectedPeriod.getName());
                dao.close();

                arguments.putLong(PeriodDetailFragment.ARG_ITEM_ID, periodId);
                PeriodDetailFragment fragment = new PeriodDetailFragment();
                fragment.setArguments(arguments);
                getFragmentManager().beginTransaction()
                        .add(R.id.period_detail_container, fragment)
                        .commit();
            } catch (Exception e) {

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, PeriodListActivity.class));
            return true;
        } else if (id == R.id.add_student) {
            Intent studentDetailIntent = new Intent(this, StudentDetailActivity.class);
            studentDetailIntent.putExtra(StudentDetailActivity.PERIOD_ID_ARG, selectedPeriod.getId());
            startActivity(studentDetailIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.period_menu, menu);
        return true;
    }
}
