package com.rjokela.comparetablets;

import android.app.ActionBar;
import android.content.res.Resources;
import android.util.Log;

/**
 * Created by Randon K. Jokela on 7/21/2015.
 */
public class TabletComparer {
    public static final String TAG = "TabletComparer";

    // can't select more than this; will throw an exception if you try
    public static final int MAX_SELECTED = 3;

    private static TabletComparer instance = null;

    Tablet[] tablets;
    boolean[] isSelected;

    private TabletComparer(Resources res, int[] resourceIds) {
        // initialize tablets
        tablets = new Tablet[resourceIds.length];
        for (int i = 0; i < tablets.length; i++) {
            tablets[i] = new Tablet(res, resourceIds[i]);
        }

        isSelected = new boolean[tablets.length];
        unSelectAll();
    }

    public static void loadData(Resources res, int[] resourceIds) {
        instance = new TabletComparer(res, resourceIds);
    }

    public static TabletComparer getInstance() {
        if (instance == null)
            Log.e(TAG, "Tablets must be loaded from xml first! Call loadData first.");
        return instance;
    }

    /**
     * Get a tablet from the list
     * @param index which tablet to get
     * @return the Tablet at specified index, or null if out of bounds
     */
    public Tablet getTablet(int index) {
        if (index >= 0 && index < tablets.length) return tablets[index];
        return null;
    }

    public Tablet[] getTablets() { return tablets; }

    public int getTabletCount() { return tablets.length; }

    public Tablet getSelectedTablet(int index) {
        for (int i = 0; i < tablets.length; i++) {
            if (isSelected[i]) {
                if (--index < 0) {
                    return tablets[i];
                }
            }
        }
        return null;
    }

    /**
     * get the number of tablets currently selected
     * @return how many are selected
     */
    public int getSelectedCount() {
        int count = 0;
        for (boolean b : isSelected) {
            if (b) count++;
        }
        return count;
    }

    public boolean isSelected(Tablet t) {
        for (int i = 0; i < tablets.length; i++) {
            if (t.equals(tablets[i])) return isSelected[i];
        }
        return false;
    }

    public class TooManySelectedException extends RuntimeException {
        public TooManySelectedException() {
            super("You may not select more than " + MAX_SELECTED + " tablets!");
        }
    }

    public void setSelected(Tablet t, boolean selected) throws TooManySelectedException {
        for (int i = 0; i < tablets.length; i++) {
            if (t.equals(tablets[i])) {
                if (selected && getSelectedCount() >= MAX_SELECTED) {
                    throw new TooManySelectedException();
                }
                else if (isSelected[i] == selected) {
                    Log.w(TAG, t.getName() + " is already " + (selected ? "" : "un") + "selected!");
                }
                else {
                    isSelected[i] = selected;
                    Log.d(TAG, "Tablet " + (selected ? "" : "un") + "selected: " + t.getName());
                }
                break;
            }
        }

        // Logging for sanity check
        Log.d(TAG, "# of tablets selected: " + getSelectedCount());
        int i = 0;
        Tablet test = getSelectedTablet(i);
        while (test != null) {
            Log.d(TAG, " - " + test.getName());
            test = getSelectedTablet(++i);
        }
    }

    public void select(Tablet t) {
        setSelected(t, true);
    }

    public void unSelect(Tablet t) {
        setSelected(t, false);
    }

    public void toggleSelected(Tablet t) {
        setSelected(t, !isSelected(t));
    }

    public void unSelectAll() {
        for (boolean b : isSelected) b = false;
        Log.d(TAG, "All tablets unselected!");
    }
}
