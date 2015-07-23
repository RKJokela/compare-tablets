package com.rjokela.comparetablets;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.text.DecimalFormat;

/**
 * Holds all the information for one tablet,
 * and can load it from an xml array.
 *
 * Use the following xml array format:
 *    <array name="identifier">
 *        <item>Name</item>                         (String)
 *        <item>OS Version</item>                   (String)
 *        <item>Price</item>                        (int)
 *        <item>Width in inches</item>              (float)
 *        <item>Height in inches</item>             (float)
 *        <item>Screen Size in inches</item>        (float)
 *        <item>weight in oz</item>                 (float)
 *        <item>screen horizontal resolution</item> (int)
 *        <item>screen vertical resolution</item>   (int)
 *        <item>drawable id</item>                  (Drawable)
 *    </array>
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
    // physical dimensions
    private float mWidth_in;
    private float mHeight_in;
    private float mScreenSize_in;
    private float mWeight_oz;
    // screen resolution
    private int mResolution_x;
    private int mResolution_y;
    // picture of the tablet
    private Drawable mDrawable;

    /**
     * Constructor - pass the id of the xml Typed Array resource to load
     */
    public Tablet(Resources res, int id) {
        int idx = 0;
        TypedArray data = res.obtainTypedArray(id);

        // assert data is good
        if (BuildConfig.DEBUG && data.length() != COUNT_MEMBERS) {
            throw new AssertionError("Resource id must be a TypedArray with exactly "
                    + COUNT_MEMBERS + " items.");
        }

        // load data from array resource
        mName = data.getString(idx++);
        mOsVersion = data.getString(idx++);
        mPrice_dollars = data.getInt(idx++, 0);
        mWidth_in = data.getFloat(idx++, 0);
        mHeight_in = data.getFloat(idx++, 0);
        mScreenSize_in = data.getFloat(idx++, 0);
        mWeight_oz = data.getFloat(idx++, 0);
        mResolution_x = data.getInt(idx++, 0);
        mResolution_y = data.getInt(idx++, 0);
        mDrawable = data.getDrawable(idx);

        data.recycle();

        Log.d(TAG, mName + " loaded successfully.");
    }

    public String getName() { return mName; }
    @Override
    public String toString() { return mName; }

    public String getOsVersion() { return mOsVersion; }
    
    public String getPrice() { return "$" + mPrice_dollars; }
    
    public String getDimensions() {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(mWidth_in) + "\" x " + df.format(mHeight_in) + "\"";
    }
    
    public String getScreenSize() {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(mScreenSize_in) + "\"";
    }
    
    public String getWeight() {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(mWeight_oz) + " oz";
    }
    
    public String getResolution() { return mResolution_x + "x" + mResolution_y; }

    public Drawable getDrawable() { return mDrawable; }
}
