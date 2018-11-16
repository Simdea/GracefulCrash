package pt.simdea.gracefulcrash.sample.support.data.constants;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Utility class meant to hold all System {@link android.app.Activity} state {@link String} Constants.
 */
@Retention(RetentionPolicy.CLASS)
@StringDef({
        ActivityStateConstants.ACTIVITY_CALLBACK_CREATED,
        ActivityStateConstants.ACTIVITY_CALLBACK_STARTED,
        ActivityStateConstants.ACTIVITY_CALLBACK_RESUMED,
        ActivityStateConstants.ACTIVITY_CALLBACK_PAUSED,
        ActivityStateConstants.ACTIVITY_CALLBACK_STOPPED,
        ActivityStateConstants.ACTIVITY_CALLBACK_DESTROYED,
        ActivityStateConstants.ACTIVITY_CALLBACK_SAVE_INSTANCE_STATE
})
public @interface ActivityStateConstants {

    String ACTIVITY_CALLBACK_CREATED = "::Activity %s created.";
    String ACTIVITY_CALLBACK_STARTED = "::Activity %s started.";
    String ACTIVITY_CALLBACK_RESUMED = "::Activity %s resumed.";
    String ACTIVITY_CALLBACK_PAUSED = "::Activity %s paused.";
    String ACTIVITY_CALLBACK_STOPPED = "::Activity %s stopped.";
    String ACTIVITY_CALLBACK_DESTROYED = "::Activity %s destroyed.";
    String ACTIVITY_CALLBACK_SAVE_INSTANCE_STATE = "::Activity %s saved Instance State.";

}
