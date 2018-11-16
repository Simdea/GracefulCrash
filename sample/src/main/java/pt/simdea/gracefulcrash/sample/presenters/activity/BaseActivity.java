package pt.simdea.gracefulcrash.sample.presenters.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.Unbinder;
import lombok.Getter;
import pt.simdea.gracefulcrash.sample.presenters.IPresenter;
import pt.simdea.gracefulcrash.sample.support.bootstrap.SampleApplication;
import pt.simdea.gracefulcrash.sample.support.data.constants.ExceptionConstants;
import pt.simdea.gracefulcrash.sample.support.data.exceptions.MvpViewNotAttachedException;
import pt.simdea.gracefulcrash.sample.support.injection.component.ActivityComponent;
import pt.simdea.gracefulcrash.sample.support.injection.component.DaggerActivityComponent;
import pt.simdea.gracefulcrash.sample.support.injection.module.ActivityModule;
import pt.simdea.gracefulcrash.sample.views.mvpviews.IViewMvp;

/**
 * Base Activity class, meant to serve as {@link IPresenter}.
 * @param <T> child object (Mvp View) to a {@link IViewMvp}.
 */
@SuppressWarnings("unused")
public class BaseActivity<T extends IViewMvp> extends AppCompatActivity
        implements IPresenter<T> {

    private ActivityComponent mActivityComponent;

    @Nullable
    @Getter
    private T mViewMvp;

    /** {@inheritDoc} */
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add logic here...
    }

    /** {@inheritDoc} */
    @Override
    protected void onDestroy() {
        // Add logic here...
        super.onDestroy();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(@NonNull final T viewMvp) {
        mViewMvp = viewMvp;
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mViewMvp = null;
    }

    /**
     * Procedure meant to retrieve the Dagger ActivityComponent instance.
     *
     * activityModule is deprecated but it is safe to ignore
     * {@see <a href="Dagger ActivityModule Deprecation">
     *     https://stackoverflow.com/questions/36521302/dagger-2-2-component-builder-module-method-deprecated/
     *     </a>} for more information.
     *
     * @return the Dagger ActivityComponent instance.
     */
    @SuppressWarnings("deprecation")
    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    // list of modules that are part of this component need to be created here too
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(SampleApplication.get(this).getApplicationComponent())
                    .build();
        }
        return mActivityComponent;
    }

    protected void unbind(@NonNull final IViewMvp viewMvp) {
        final Unbinder unbinder = viewMvp.getUnBinder();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    protected boolean isViewAttached() {
        if (mViewMvp != null) {
            return true;
        } else {
            throw new MvpViewNotAttachedException(ExceptionConstants.MVP_VIEW_NOT_ATTACHED_ERROR);
        }
    }

    // Add remaining base logic here...

}
