package com.rjokela.comparetablets;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class TabletListFragment extends Fragment {

    public static final String TAG = "TabletListFragment";

    private TabletComparer tabletComparer;

    private ViewGroup listContainer;
    private ViewGroup[] rowContainers;

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

        TabletComparer.loadData(getResources(), R.array.kindle);
        tabletComparer = TabletComparer.getInstance();

        listContainer = (ViewGroup) getActivity().findViewById(R.id.tabletList_verticalList);

        inflateTabletEntries();
    }

    private void inflateTabletEntries() {
        int count = tabletComparer.getTabletCount();
        Log.d(TAG, "Creating " + count + " tablet views...");

        rowContainers = new ViewGroup[count];
        LayoutInflater inflater = getLayoutInflater(null);

        for (int i = 0; i < count; i++) {
            Tablet t = tabletComparer.getTablet(i);
            Log.d(TAG, "Adding tablet " + t.getName() + " to table");
            rowContainers[i] = (ViewGroup) inflater.inflate(R.layout.tablet_list_entry, listContainer);

            populateTabletData(rowContainers[i], t);
        }

        Log.d(TAG, "All tablets now show onscreen");
    }

    private void populateTabletData(ViewGroup layout, Tablet t) {
        layout = (ViewGroup) layout.getChildAt(0);

        CheckBox checkBox = (CheckBox) layout.getChildAt(0);
        if (t.equals(tabletComparer.getTablet(2)))
            checkBox.setChecked(true);

        ImageView image = (ImageView) layout.getChildAt(1);
        image.setImageDrawable(t.getDrawable());

        TextView title = (TextView) layout.getChildAt(2);
        title.setText(t.getName());
    }
}
