package com.rjokela.comparetablets;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A placeholder fragment containing a simple view.
 */
public class TabletListFragment extends Fragment {

    public static final String TAG = "TabletListFragment";

    public ViewGroup listContainer;

    public TabletListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tablet_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LayoutInflater inflater = getLayoutInflater(null);

        listContainer = (ViewGroup) getActivity().findViewById(R.id.tabletList_scrollView);

        Tablet kindle = new Tablet(getResources(), R.array.kindle);

        inflater.inflate(R.layout.tablet_list_entry, listContainer);
    }
}
