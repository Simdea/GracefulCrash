package pt.simdea.gracefulcrash.sample.support.data.constants;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Utility class meant to hold all {@link String} constants for the App Sample sub module.
 */
@Retention(RetentionPolicy.CLASS)
@StringDef({
        SampleConstants.LOG_TAG,
        SampleConstants.APPLICATION_TERMINATED
})
public @interface SampleConstants {

    /* Misc. */
    String LOG_TAG = "APP-Sample";
    String APPLICATION_TERMINATED = "::Application Terminated.";

}
