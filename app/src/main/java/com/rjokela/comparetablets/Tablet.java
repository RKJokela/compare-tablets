package com.rjokela.comparetablets;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;

/**
 * Holds all the information for one tablet,
 * and can load it from an xml array.
 *
 * Created by Randon K. Jokela on 7/20/2015.
 */
public class Tablet {
    public static final String TAG = "Tablet";
    // number of members loaded from xml
    public static final int COUNT_MEMBERS = 10;

    private String mName;
    private String mOsVersion;
    private int mPrice_dollars;
    // physical dimensions (metric)
    private int mWidth_mm;
    private int mHeight_mm;
    private int mScreenSize_mm;
    private int mWeight_g;
    // screen resolution
    private int mResolution_x;
    private int mResolution_y;
    private int mScreenDpi;


    /**
     * Constructor - pass the id of the xml Typed Array resource to load
     */
    public Tablet(Resources res, int id) {
        int idx = 0;
        TypedArray data = res.obtainTypedArray(id);

        // assert data is good
        if (BuildConfig.DEBUG && data.getIndexCount() != COUNT_MEMBERS) {
            throw new AssertionError("Resource id must be a TypedArray with exactly "
                    + COUNT_MEMBERS + " items.");
        }

        // load data from array resource
        mName = data.getString(idx++);
        mOsVersion = data.getString(idx++);
        mPrice_dollars = data.getInt(idx++, 0);
        mWidth_mm = data.getInt(idx++, 0);
        mHeight_mm = data.getInt(idx++, 0);
        mScreenSize_mm = data.getInt(idx++, 0);
        mWeight_g = data.getInt(idx++, 0);
        mResolution_x = data.getInt(idx++, 0);
        mResolution_y = data.getInt(idx++, 0);
        mScreenDpi = data.getInt(idx, 0);

        data.recycle();

        Log.d(TAG, mName + " loaded successfully.");
    }

    public String getName() { return mName; }

    public String getOsVersion() { return mOsVersion; }
}
