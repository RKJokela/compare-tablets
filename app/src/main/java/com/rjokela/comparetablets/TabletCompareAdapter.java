package com.rjokela.comparetablets;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Randon K. Jokela on 7/23/2015.
 */
public class TabletCompareAdapter extends ArrayAdapter<Tablet> {

    public static final String TAG = "TabletCompareAdapter";

    private static final int IDX_IMAGE      = 0;
    private static final int IDX_TITLE      = 1;
    private static final int IDX_OS         = 2;
    private static final int IDX_PRICE      = 3;
    private static final int IDX_DIMEN      = 4;
    private static final int IDX_WEIGHT     = 5;
    private static final int IDX_SCREENSIZE = 6;
    private static final int IDX_RESOLUTION = 7;
    private static final int IDX_CHECKBOX   = 8;

    private final TabletComparer tabletComparer;

    private Resources res;

    public TabletCompareAdapter(Context context, int resource, int textViewResourceId, Tablet[] objects) {
        super(context, resource, textViewResourceId, objects);
        tabletComparer = TabletComparer.getInstance();
        res = context.getResources();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewGroup layout = (ViewGroup) super.getView(position, convertView, parent);

        //Log.d(TAG, "layout.getChildCount() returned " + layout.getChildCount());

        Tablet tablet = getItem(position);

        // set the image
        ImageView image = (ImageView) layout.getChildAt(IDX_IMAGE);
        image.setImageDrawable(tablet.getDrawable());

        // set all the text
        TextView tv;
        tv = (TextView) layout.getChildAt(IDX_OS); // skip the title as this is done by the superclass
        tv.setText(tablet.getOsVersion());
        tv = (TextView) layout.getChildAt(IDX_PRICE);
        tv.setText(tablet.getPrice());
        tv = (TextView) layout.getChildAt(IDX_DIMEN);
        tv.setText(tablet.getDimensions());
        tv = (TextView) layout.getChildAt(IDX_WEIGHT);
        tv.setText(tablet.getWeight());
        tv = (TextView) layout.getChildAt(IDX_SCREENSIZE);
        tv.setText(tablet.getScreenSize());
        tv = (TextView) layout.getChildAt(IDX_RESOLUTION);
        tv.setText(tablet.getResolution());

        CheckBox checkBox = (CheckBox) layout.getChildAt(IDX_CHECKBOX);
        // make sure all checkboxes have different IDs???
        checkBox.setId(R.id.checkBox_id + position);
        checkBox.setChecked(true);
        Log.d(TAG, "Getting view at position " + position + "; checkbox id is " + checkBox.getId());
        final int finalPosition = position;
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabletComparer.unSelect(getItem(finalPosition));
                layout.setVisibility(View.GONE);
            }
        });

        return layout;
    }
}
