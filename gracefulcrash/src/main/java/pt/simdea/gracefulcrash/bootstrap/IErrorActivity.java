package pt.simdea.gracefulcrash.bootstrap;

import android.support.annotation.NonNull;
import android.widget.TextView;

/**
 * GracefulCrash ErrorActivity interface.
 *
 * GracefulCrash ErrorActivity can be customized by any application using GracefulCrash.
 * This interface enforces a contract that all ErrorActivity instances must follow.
 */
public interface IErrorActivity {

    /**
     * Procedure meant to handle the Activity Log given by GracefulCrash.
     * @param view        {@link TextView} instance meant to house the Activity Log given
     *                    by GracefulCrash.
     * @param activityLog {@link String} value representing the Activity Log given by GracefulCrash.
     */
    void onActivityLog(@NonNull final TextView view, @NonNull final String activityLog);

    /**
     * Procedure meant to handle the StackTrace logging on restart given by GracefulCrash.
     * @param logErrorOnRestartStackTrace {@link String} value representing the StackTrace log
     *                                    given by GracefulCrash.
     */
    void onLogErrorOnRestart(@NonNull String logErrorOnRestartStackTrace);

    // Add more functionality here...

}
