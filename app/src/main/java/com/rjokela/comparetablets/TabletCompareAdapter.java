package com.rjokela.comparetablets;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Randon K. Jokela on 7/23/2015.
 */
public class TabletCompareAdapter extends ArrayAdapter<Tablet> {

    public static final String TAG = "TabletCompareAdapter";

    private final TabletComparer tabletComparer;

    public TabletCompareAdapter(Context context, int resource, int textViewResourceId, Tablet[] objects) {
        super(context, resource, textViewResourceId, objects);
        tabletComparer = TabletComparer.getInstance();
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
                    Log.e(TAG, "ERROR: SHOULD NEVER REACH HERE");
                    ((CheckBox) v).setChecked(false);
                    Toast.makeText(getContext(), R.string.tabletList_tooManySelected, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        return layout;
    }
}
