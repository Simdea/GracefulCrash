package pt.simdea.gracefulcrash.data.constants;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Utility class meant to hold all Graceful Crash Background Mode {@link Integer} Constants.
 */
@Retention(RetentionPolicy.CLASS)
@IntDef({
        GracefulCrashBackgroundModes.SHOW_CUSTOM_CRASH,
        GracefulCrashBackgroundModes.SHOW_SYSTEM_CRASH,
        GracefulCrashBackgroundModes.SILENT_CRASH
})
public @interface GracefulCrashBackgroundModes {

    /* Misc. Constants. */
    int SHOW_CUSTOM_CRASH = 0; // launch the custom error activity even if the app is in background.
    int SHOW_SYSTEM_CRASH = 1; // launch the default system error when the app is in background.
    int SILENT_CRASH = 2;      // crash silently when the app is in background.

}
