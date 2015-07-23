package com.rjokela.comparetablets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A placeholder fragment containing a simple view.
 */
public class TabletListFragment extends Fragment {

    public static final String TAG = "TabletListFragment";

    private TabletComparer tabletComparer;

    private ListView listContainer;
    private ListAdapter tabletAdapter;

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

        TypedArray tabletIdArray = getResources().obtainTypedArray(R.array.tablet_ids);
        int[] tabletIds = new int[tabletIdArray.length()];
        for (int i = 0; i < tabletIds.length; i++) {
            tabletIds[i] = tabletIdArray.getResourceId(i, -1);
        }
        TabletComparer.loadData(getResources(), tabletIds);
        tabletComparer = TabletComparer.getInstance();

        listContainer = (ListView) getActivity().findViewById(R.id.tabletList_listView);
        Button compareButton = (Button) getActivity().findViewById(R.id.tabletList_buttonCompare);
        tabletAdapter = new TabletListAdapter(getActivity(),
                R.layout.tablet_list_entry,
                R.id.tabletEntry_title,
                tabletComparer.getTablets(),
                compareButton);
        listContainer.setAdapter(tabletAdapter);
    }
}
