package com.rjokela.comparetablets;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;


/**
 * A placeholder fragment containing a simple view.
 */
public class TabletCompareActivityFragment extends Fragment {
    public static final String TAG = "TabletCompareActivityFragment";

    TabletComparer tabletComparer;

    LinearLayout dynamicLayout;

    public TabletCompareActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tablet_compare, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tabletComparer = TabletComparer.getInstance();
        dynamicLayout = (LinearLayout) getActivity().findViewById(R.id.tabletCompare_tabletEntriesParent);

        TabletCompareAdapter adapter = new TabletCompareAdapter(getActivity(), R.layout.tablet_compare_entry, R.id.tabletCompare_entryTitle, tabletComparer.getSelectedTablets());

        int count = tabletComparer.getSelectedCount();
        for (int i = 0; i < count; i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            View tabletColumn = adapter.getView(i, null, null);
            dynamicLayout.addView(tabletColumn, lp);
        }
    }

    public void onBackPressed() {
        // TODO
    }
}
