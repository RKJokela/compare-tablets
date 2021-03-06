package com.rjokela.comparetablets;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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
public class TabletListAdapter extends ArrayAdapter<Tablet> {

    public static final String TAG = "TabletListAdapter";

    private final TabletComparer tabletComparer;

    private Button compareButton;

    public TabletListAdapter(Context context, int resource, int textViewResourceId, Tablet[] objects, Button compareButton) {
        super(context, resource, textViewResourceId, objects);
        tabletComparer = TabletComparer.getInstance();
        this.compareButton = compareButton;
        boolean anySelected = tabletComparer.getSelectedCount() > 0;
        compareButton.setEnabled(anySelected);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewGroup layout = (ViewGroup) super.getView(position, convertView, parent);

        // highlight selected items
        final ColorDrawable highlight = new ColorDrawable(getContext().getResources().getColor(R.color.yellow_highlight));
        final boolean selected = tabletComparer.isSelected(getItem(position));
        if (selected)
            layout.setBackground(highlight);

        ImageView image = (ImageView) layout.getChildAt(1);
        image.setImageDrawable(getItem(position).getDrawable());

        CheckBox checkBox = (CheckBox) layout.getChildAt(0);
        // make sure all checkboxes have different IDs???
        checkBox.setId(R.id.checkBox_id + position);
        Log.d(TAG, "Getting view at position " + position + "; checkbox id is " + checkBox.getId());
        checkBox.setChecked(selected);

        // handle clicks on the checkbox
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tabletComparer.toggleSelected(getItem(position));

                    // highlight selected tablets
                    layout.setBackground(tabletComparer.isSelected(position) ? highlight : null);
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
