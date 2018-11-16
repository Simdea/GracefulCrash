package pt.simdea.gracefulcrash.sample.support.injection.module;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import lombok.NoArgsConstructor;
import pt.simdea.gracefulcrash.sample.models.androidmodel.mvpimpl.DataModelMvp;
import pt.simdea.gracefulcrash.sample.support.injection.scope.ActivityContext;
import pt.simdea.gracefulcrash.sample.support.injection.scope.PerActivity;
import pt.simdea.gracefulcrash.sample.views.androidviews.mvpimpl.HomeViewMvpImpl;
import pt.simdea.gracefulcrash.sample.views.mvpviews.IHomeViewMvp;

/**
 * Dependency provider responsible for providing objects which can be injected at the Activity level.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
@Module
@NoArgsConstructor
public class ActivityModule {

    private Activity mActivity;

    /**
     * Instantiates a new ActivityModule.
     * @param activity {@link Activity} instance representing the Activity level object.
     */
    public ActivityModule(@NonNull final Activity activity) {
        mActivity = activity;
    }

    /**
     * Procedure meant to provide the Activity level {@link Context} instance.
     * @return the Activity level Context instance.
     */
    @Provides
    @ActivityContext
    public Context provideContext() {
        return mActivity;
    }

    /**
     * Procedure meant to provide the {@link Activity} instance.
     * @return the Activity instance.
     */
    @Provides
    public Activity provideActivity() {
        return mActivity;
    }

    /* ###################################### Module MVP View Provisions ######################################### */

    /**
     * Procedure meant to provide the HomeViewMvp instance.
     * {@see @{@link IHomeViewMvp } for more information}
     * @return the HomeViewMvp instance.
     */
    @Provides
    @PerActivity
    public IHomeViewMvp provideHomeViewMvp() {
        /* Instantiate MVP view associated with Home Activity */
        return new HomeViewMvpImpl(mActivity.getLayoutInflater(), null);
    }

    // Add remaining MVP Views provisions here...

    /* ###################################### Module MVP Model Provisions ######################################### */

    /**
     * Procedure meant to provide the HomeModelMvp instance.
     * @return the HomeModelMvp instance.
     */
    @Provides
    @PerActivity
    public DataModelMvp provideHomeModel() {
        return new DataModelMvp();
    }

    // Add remaining MVP Model provisions here...

}
