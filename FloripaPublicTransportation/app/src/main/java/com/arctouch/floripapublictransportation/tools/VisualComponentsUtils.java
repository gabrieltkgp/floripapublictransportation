package com.arctouch.floripapublictransportation.tools;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by GabrielPacheco on 21/01/2016.
 */

public class VisualComponentsUtils {
    public static void setDynamicHeightListView(ListView mListView) {
        ListAdapter mListAdapter = mListView.getAdapter();

        if (mListAdapter == null) {
            return;
        }

        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < mListAdapter.getCount(); i++) {
            View listItem = mListAdapter.getView(i, null, mListView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = mListView.getLayoutParams();
        params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
        mListView.setLayoutParams(params);
        mListView.requestLayout();
    }

    public static void setDynamicHeightGridView(GridView gridView) {
        ListAdapter gridViewAdapter = gridView.getAdapter();

        if (gridViewAdapter == null) {
            return;
        }

        int height = 0;

        double count = gridView.getCount();
        double columns = gridView.getNumColumns();
        double result = count / columns;
        int nNumLines = (int) Math.ceil(result);

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(gridView.getWidth(), View.MeasureSpec.AT_MOST);
        View listItem = gridViewAdapter.getView(1, null, gridView);
        listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
        height = listItem.getMeasuredHeight() * (nNumLines);

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = height;
        gridView.setLayoutParams(params);
        gridView.requestLayout();
    }
}
