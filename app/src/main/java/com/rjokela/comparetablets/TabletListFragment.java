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

    public class TabletAdapter extends ArrayAdapter<Tablet> {
        private final TabletComparer tabletComparer;

        private Button compareButton;

        public TabletAdapter(Context context, int resource, int textViewResourceId, Tablet[] objects, Button compareButton) {
            super(context, resource, textViewResourceId, objects);
            tabletComparer = TabletComparer.getInstance();
            this.compareButton = compareButton;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewGroup layout = (ViewGroup) super.getView(position, convertView, parent);

            //Log.d(TAG, "layout.getChildCount() returned " + layout.getChildCount());

            ImageView image = (ImageView) layout.getChildAt(1);
            image.setImageDrawable(getItem(position).getDrawable());

            CheckBox checkBox = (CheckBox) layout.getChildAt(0);
            // make sure all checkboxes have different IDs???
            checkBox.setId(R.id.checkBox_id + position);
            Log.d(TAG, "Getting view at position " + position + "; checkbox id is " + checkBox.getId());
            final int finalPosition = position;
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        tabletComparer.toggleSelected(getItem(finalPosition));
                    } catch (TabletComparer.TooManySelectedException e) {
                        ((CheckBox) v).setChecked(false);
                        Toast.makeText(getContext(), R.string.tabletList_tooManySelected, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // update button
                    if (!compareButton.isEnabled() && tabletComparer.getSelectedCount() > 0) {
                        compareButton.setEnabled(true);
                        compareButton.setText(getContext().getResources().getString(R.string.tabletList_buttonCompareEnabled));
                    } else if (compareButton.isEnabled() && tabletComparer.getSelectedCount() == 0) {
                        compareButton.setEnabled(false);
                        compareButton.setText(getContext().getResources().getString(R.string.tabletList_buttonCompareDisabled));
                    }
                }
            });

            return layout;
        }
    }

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
        tabletAdapter = new TabletAdapter(getActivity(),
                R.layout.tablet_list_entry,
                R.id.tabletEntry_title,
                tabletComparer.getTablets(),
                compareButton);
        listContainer.setAdapter(tabletAdapter);
    }
}
