package pt.simdea.gracefulcrash.bootstrap;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.Locale;

import pt.simdea.gracefulcrash.data.constants.GracefulCrashConstants;
import pt.simdea.gracefulcrash.data.exceptions.ActivityTrackerInstantiationException;

/**
 * Class responsible for maintaining and tracking {@link Activity} history.
 * This class employs the Singleton Pattern.
 */
public final class GracefulCrashActivityTracker {

    /** {@link GracefulCrashActivityTracker} sole instance. */
    private static volatile GracefulCrashActivityTracker sTrackerInstance;

    public WeakReference<Activity> mLastActivityCreated;
    public boolean mInBackground;
    public int mCurrentlyStartedActivities;
    public final Deque<String> mActivityRecord;

    private final DateFormat mDateFormat;

    /* ############################################### Constructors ############################################### */

    /**
     * Instantiates a new SslDefaultExecutorSupplier.
     * Private to prevent instantiation as per Singleton Pattern rules.
     */
    private GracefulCrashActivityTracker() {
        /* Prevent form the reflection api. */
        if (sTrackerInstance != null){
            throw new ActivityTrackerInstantiationException(GracefulCrashConstants.SINGLETON_INSTANTIATION_ERROR);
        }

        mCurrentlyStartedActivities = 0;
        mDateFormat = new SimpleDateFormat(GracefulCrash.getInstance()
                .getConfiguration().getTrackActivitiesDateFormat(), Locale.getDefault());

        mActivityRecord = new ArrayDeque<>(GracefulCrash.getInstance()
                .getConfiguration().getTrackActivitiesMinLogEntryTrace());
        mLastActivityCreated = new WeakReference<>(null);
        mInBackground = true;
    }

    /**
     * Procedure meant to retrieve this class' sole instance as per Singleton Pattern rules.
     * @return {@link GracefulCrashActivityTracker} instance representing this class' sole instance as per Singleton
     *         Pattern rules.
     */
    public static GracefulCrashActivityTracker getInstance() {
        /* Double check locking pattern */
        if (sTrackerInstance == null) { // check for the first time
            synchronized (GracefulCrashActivityTracker.class) { // Check for the second time.
                // if there is no instance available... create new one
                if (sTrackerInstance == null) {
                    sTrackerInstance = new GracefulCrashActivityTracker();
                }
            }
        }
        return sTrackerInstance;
    }

    /* ############################################### Constructors ############################################### */

    /**
     * Procedure meant to add lifecycle events meant to trace the user's footsteps regarding the application's usage.
     * @param activityName {@link String} value representing the target {@link Activity}'s name meant to be collected.
     */
    public void addLifecycleEvent(@NonNull final String activityName, @NonNull final String lifecycle) {
        final String date = mDateFormat.format(new Date());
        final String builder = date + GracefulCrashConstants.SINGLE_TWO_POINTS_STRING + activityName + lifecycle;
        mActivityRecord.add(builder);
    }

}
