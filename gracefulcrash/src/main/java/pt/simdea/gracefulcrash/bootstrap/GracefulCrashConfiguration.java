package pt.simdea.gracefulcrash.bootstrap;

import android.app.Activity;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import pt.simdea.gracefulcrash.data.constants.GracefulCrashBackgroundModes;
import pt.simdea.gracefulcrash.data.constants.GracefulCrashConstants;

/**
 * Class responsible for defining a configuration to the Graceful Crash module.
 *
 * This class employs Builder Pattern, meaning this class contains all the required fields.
 * It's constructor receives all the required parameters. All optional parameters can be set via this class.
 */
@SuppressWarnings("unused")
@ToString
@EqualsAndHashCode
@Getter
public final class GracefulCrashConfiguration
        implements Comparable<GracefulCrashConfiguration> {

    private final Class<? extends Activity> mErrorActivity;
    private final boolean mEnabled;
    private final boolean mTrackActivities;
    private final boolean mLogErrorOnRestart;
    private final int mBackgroundMode;
    private final int mTrackActivitiesMinLogEntryTrace;
    private final String mTrackActivitiesDateFormat;
    // Add more fields here ...

    /**
     * Instantiates a new {@link GracefulCrashConfiguration}.
     * @param builder {@link ConfigurationBuilder} instance representing the
     *                GracefulCrashConfiguration's builder.
     */
    private GracefulCrashConfiguration(@NonNull final ConfigurationBuilder builder) {
        mEnabled = builder.isEnabled;
        mBackgroundMode = builder.mBackgroundMode;
        mTrackActivities = builder.mTrackActivities;
        mTrackActivitiesDateFormat = builder.mTrackActivitiesDateFormat;
        mTrackActivitiesMinLogEntryTrace = builder.mTrackActivitiesMinLogEntryTrace;
        mErrorActivity = builder.mErrorActivity;
        mLogErrorOnRestart = builder.mLogErrorOnRestart;
        // Add more fields here ...
    }

    /** {@inheritDoc} */
    @Override
    public int compareTo(@NonNull final GracefulCrashConfiguration other) {
        return 0; // TODO PR: How to compare a Configuration?
    }

    /**
     * Procedure meant to update an existing {@link GracefulCrashConfiguration}.
     */
    public void apply() {
        GracefulCrash.getInstance().setConfiguration(this);
    }

    /**
     * Configuration builder class responsible for harboring all the required fields of a
     * {@link GracefulCrashConfiguration}.
     */
    public static class ConfigurationBuilder {

        private Class<? extends Activity> mErrorActivity = DefaultErrorActivity.class;
        private boolean isEnabled = true; // true by default
        private boolean mTrackActivities = false; // false by default
        private boolean mLogErrorOnRestart = true; // true by default
        private String mTrackActivitiesDateFormat
                = GracefulCrashConstants.DEFAULT_DATE_FORMAT; // yyyy-MM-dd HH:mm:ss by default
        private int mTrackActivitiesMinLogEntryTrace = 50; // 50 by default
        private int mBackgroundMode = GracefulCrashBackgroundModes.SHOW_CUSTOM_CRASH; // Show Custom Error by default
        // Add more fields here ...

        /**
         * Instantiates a new {@link ConfigurationBuilder}.
         * This constructor is responsible for for initializing a {@link GracefulCrashConfiguration}'s mandatory
         * parameters.
         */
        public ConfigurationBuilder() {
            /* TODO PR: Add proper implementation here */
        }

        /**
         * Procedure meant to set the value for {@link #isEnabled} optional parameter.
         * If set to true, the module will intercept crashes and handle them. If set to false, the module will not
         * interfere and the default system behavior will occur. The default value set for this property is true.
         * @param enable {@link Boolean} value indicating whether the {@link GracefulCrash} module should be enabled
         *               or not.
         * @return       {@link ConfigurationBuilder} instance representing the same object builder object
         *               after setting the optional attribute.
         */
        @NonNull
        public ConfigurationBuilder enable(final boolean enable) {
            isEnabled = enable;
            return this;
        }

        /**
         * Procedure meant to set the value for {@link #mBackgroundMode} optional parameter.
         * TODO PR: Add details!
         * @param backgroundMode {@link GracefulCrashBackgroundModes} value indicating the desired background mode
         *                       for the module to operate under.
         * @return               {@link ConfigurationBuilder} instance representing the same object builder
         *                       object after setting the optional attribute.
         */
        @NonNull
        public ConfigurationBuilder withBackgroundMode(@IntRange(from = 0) final int backgroundMode) {
            mBackgroundMode = backgroundMode;
            return this;
        }

        /**
         * Procedure meant to set the value for {@link #mTrackActivities} optional parameter.
         * If set to true, the module will track the activities the user visits and their lifecycle calls. This is
         * done so that this information is collected on the error details. If set to false, the module will not track
         * the activities the user visits nor track their lifecycle calls. The default value set for this property is
         * false.
         * @param enableTrack      {@link Boolean} value indicating whether the {@link GracefulCrash} module should
         *                         track activities or not.
         * @param dateFormat       {@link String} value indicating the desired date format to be stored for the logs.
         * @param minLogEntryTrace {@link Integer} value indicating the minimum amount of logs to be stored.
         * @return                 {@link ConfigurationBuilder} instance representing the same object builder
         *                         object after setting the optional attribute.
         */
        @NonNull
        public ConfigurationBuilder trackActivities(final boolean enableTrack, @Nullable final String dateFormat,
                                                    @IntRange(from = 0, to = 50) final int minLogEntryTrace) {
            mTrackActivities = enableTrack;
            if (enableTrack) {
                mTrackActivitiesDateFormat
                        = dateFormat != null ? dateFormat : GracefulCrashConstants.DEFAULT_DATE_FORMAT;
                mTrackActivitiesMinLogEntryTrace
                        = minLogEntryTrace >= 0 && minLogEntryTrace <= 50 ? minLogEntryTrace : 50;
            }
            return this;
        }

        /**
         * Procedure meant to set the value for {@link #mLogErrorOnRestart} optional parameter.
         * If set to true the module will re-log the error stacktrace when the custom error activity is launched. This
         * is done because Android Studio's LogCat only shows the stacktrace from the current active process. If set
         * to false the error stacktrace will not be re-logged.
         * @param logErrorOnRestart {@link Boolean} value indicating whether the {@link GracefulCrash} module should
         *                          re-log the error stacktrace when the custom error activity is launched or not.
         * @return                  {@link ConfigurationBuilder} instance representing the same object builder
         *                          object after setting the optional attribute.
         */
        @NonNull
        public ConfigurationBuilder logErrorOnRestart(final boolean logErrorOnRestart) {
            mLogErrorOnRestart = logErrorOnRestart;
            return this;
        }

        /**
         * Procedure meant to set the value for {@link #mErrorActivity} optional parameter.
         * If a class is provided, it allows for the injection of a custom error {@link Activity} that will be
         * launched when a crash occurs.
         * @param errorActivity {@link Class} instance representing the target custom error {@link Activity} that
         *                      will be launched when a crash occurs.
         * @return              {@link ConfigurationBuilder} instance representing the same object builder
         *                      object after setting the optional attribute.
         */
        @NonNull
        public ConfigurationBuilder withErrorActivity(@NonNull final Class<? extends Activity> errorActivity) {
            mErrorActivity = errorActivity;
            return this;
        }

        /**
         * Procedure meant to return the desired {@link GracefulCrashConfiguration}.
         * @return the built {@link GracefulCrashConfiguration} instance.
         */
        @NonNull
        public GracefulCrashConfiguration buildConfiguration() {
            return new GracefulCrashConfiguration(this);
        }

    }

}
