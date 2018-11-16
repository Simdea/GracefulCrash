package pt.simdea.gracefulcrash.sample.presenters.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import pt.simdea.gracefulcrash.sample.models.androidmodel.mvpimpl.DataModelMvp;
import pt.simdea.gracefulcrash.sample.models.mvpmodels.IDataModelObserver;
import pt.simdea.gracefulcrash.sample.support.data.structs.Optional;
import pt.simdea.gracefulcrash.sample.views.mvpviews.IHomeViewMvp;

/**
 * Class responsible for the Main / Home Screen.
 */
public class HomePresenter extends BaseActivity<IHomeViewMvp>
        implements IHomeViewMvp.StartTestViewMvcListener, IDataModelObserver {

    @Inject
    protected IHomeViewMvp mViewMvp;

    @Inject
    protected DataModelMvp mDataModelMvp;

    /**
     * Starter procedure for HomeActivity.
     * @param context {@link Context} instance representing the application's current context.
     */
    public static void start(@NonNull final Context context) {
        final Intent starter = new Intent(context, HomePresenter.class);
        context.startActivity(starter);
    }

    /** {@inheritDoc} */
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        attachView(mViewMvp);
        setContentView(mViewMvp.getRootView());
    }

    /** {@inheritDoc} */
    @Override
    protected void onStart() {
        super.onStart();
        mViewMvp.setTestStartListener(this);
        mDataModelMvp.registerObserver(this);
    }

    /** {@inheritDoc} */
    @Override
    protected void onPause() {
        mDataModelMvp.cancelPendingWork();
        super.onPause();
    }

    /** {@inheritDoc} */
    @Override
    protected void onStop() {
        mDataModelMvp.unregisterObserver(this);
        super.onStop();
    }

    /** {@inheritDoc} */
    @Override
    public void onDestroy() {
        unbind(mViewMvp);
        detachView();
        mViewMvp = null;
        super.onDestroy();
    }

    /** {@inheritDoc} */
    @Override
    public void onTestStartRequest() {
        mDataModelMvp.doSomething();
    }

    /** {@inheritDoc} */
    @Override
    public void onObservableChanged(@NonNull final Optional<String> notification) {
        if (notification.isDefined()) {
            mViewMvp.onDataProcessedByModel(notification.get());
        }
        throw new NullPointerException("Boom!");
    }

    // Add more logic in the future...

}
