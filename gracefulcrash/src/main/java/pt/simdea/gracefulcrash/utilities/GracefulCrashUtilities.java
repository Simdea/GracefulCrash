package pt.simdea.gracefulcrash.utilities;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pt.simdea.gracefulcrash.data.constants.GracefulCrashConstants;
import pt.simdea.gracefulcrash.data.exceptions.UtilityInstantiationException;

/**
 * Class responsible for exposing a set of utility procedures that may or may not be related with each other.
 */
@SuppressWarnings("unused")
public final class GracefulCrashUtilities {

    /**
     * Instantiates a new {@link GracefulCrashUtilities}.
     * Private to prevent instantiation.
     */
    private GracefulCrashUtilities() {
        throw new UtilityInstantiationException(GracefulCrashConstants.INSTANTIATING_UTILITY_CLASS_NOT_ALLOWED);
    }

//    /**
//     * See http://jhshi.me/2014/11/09/monitor-screen-touch-event-in-android/index.html
//     * @param currentActivity
//     * @param touchListener
//     */
//    @SuppressLint("ClickableViewAccessibility")
//    public static void monitorScreenTouchEvents(@NonNull final Activity currentActivity,
//                                                @NonNull final View.OnTouchListener touchListener) {
//        final WindowManager mWindowManager = (WindowManager) currentActivity.getSystemService(Context.WINDOW_SERVICE);
//        final View mDummyView = new LinearLayout(currentActivity);
//
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
//        mDummyView.setLayoutParams(params);
//        mDummyView.setOnTouchListener(touchListener);
//    }

    public static View getLastFocusedViewForActivity(@NonNull final Activity currentActivity) {
        final View root = getRootViewForActivity(currentActivity);
        View target = root;
        if (root != null) {
            final List<View> allViewsForActivity = getAllViews(root);
            target = allViewsForActivity.get(new Random().nextInt(allViewsForActivity.size()));
        }
        return target;
    }

    /**
     * Procedure meant to retrieve the root {@link View} of a target {@link Activity}.
     * @param currentActivity {@link Activity} instance representing the current {@link Activity}.
     * @return                {@link View} instance representing the root {@link View} of the target {@link Activity}.
     */
    @Nullable
    public static View getRootViewForActivity(@NonNull final Activity currentActivity) {
        final Window window = currentActivity.getWindow();
        if (window != null) {
            return window.getDecorView();
        }
        return null;
    }

    private static List<View> getAllViews(@NonNull final View v) {
        if (!(v instanceof ViewGroup) || ((ViewGroup) v).getChildCount() == 0) {
            // It's a leaf
            final List<View> r = new ArrayList<>();
            r.add(v);
            return r;
        } else {
            final List<View> list = new ArrayList<>(); list.add(v); // If it's an internal node add itself
            int children = ((ViewGroup) v).getChildCount();
            for (int i = 0; i < children; ++i) {
                list.addAll(getAllViews(((ViewGroup) v).getChildAt(i)));
            }
            return list;
        }
    }

}
