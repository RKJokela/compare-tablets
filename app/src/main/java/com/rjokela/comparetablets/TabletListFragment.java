package com.rjokela.comparetablets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class TabletListFragment extends Fragment {

    public static final String TAG = "TabletListFragment";

    private TabletComparer tabletComparer;

    private ListView listContainer;
    private ListAdapter tabletAdapter;

    private List<CheckBox> checkBoxes;

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

        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TabletCompareActivity.class);
                startActivity(i);
            }
        });
    }

    public void updateCheckBoxesAndButton() {
        Log.d(TAG, "Updating checkboxes...");
        for (int i = 0; i < tabletComparer.getTabletCount(); i++) {
            CheckBox cb = (CheckBox) getActivity().findViewById(R.id.checkBox_id + i);
            if (cb == null)
                Log.d(TAG, "Could not find checkbox at id " + (R.id.checkBox_id + i));
            else {
                boolean selected = tabletComparer.isSelected(i);
                if (selected != cb.isChecked()) {
                    Log.d(TAG, "Checkbox " + i + " needed update, setting to " + (selected ? "checked" : "unchecked"));
                    cb.setChecked(selected);
                }
            }
        }
        Button button = (Button) getActivity().findViewById(R.id.tabletList_buttonCompare);
        boolean anySelected = tabletComparer.getSelectedCount() > 0;
        if (anySelected) {
            button.setEnabled(true);
            button.setText(getString(R.string.tabletList_buttonCompareEnabled));
        } else {
            button.setEnabled(false);
            button.setText(getString(R.string.tabletList_buttonCompareDisabled));
        }
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume() called!");
        updateCheckBoxesAndButton();
        super.onResume();
    }

//    @Override
//    public void onStart() {
//        Log.d(TAG, "onStart() called!");
//        super.onStart();
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        Log.d(TAG, "onAttach() called! Activity = " + activity.toString());
//        super.onAttach(activity);
//    }
//
//    @Override
//    public void onPause() {
//        Log.d(TAG, "onPause() called!");
//        super.onPause();
//    }
//
//    @Override
//    public void onStop() {
//        Log.d(TAG, "onStop() called!");
//        super.onStop();
//    }
}
