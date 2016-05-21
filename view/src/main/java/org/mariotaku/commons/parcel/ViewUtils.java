package org.mariotaku.commons.parcel;

import android.support.annotation.UiThread;
import android.view.View;

import org.mariotaku.commons.math.CommonsMathUtils;

/**
 * Created by mariotaku on 16/1/23.
 */
public class ViewUtils {
    private ViewUtils() {
    }

    @UiThread
    public static boolean hitView(float x, float y, View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return CommonsMathUtils.inRange(x, location[0], location[0] + view.getWidth(), CommonsMathUtils.RANGE_INCLUSIVE_INCLUSIVE) &&
                CommonsMathUtils.inRange(y, location[1], location[1] + view.getHeight(), CommonsMathUtils.RANGE_INCLUSIVE_INCLUSIVE);
    }
}
