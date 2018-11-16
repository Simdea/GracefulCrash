package pt.simdea.gracefulcrash.sample.views.androidviews.mvpimpl;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.Setter;
import pt.simdea.gracefulcrash.sample.views.mvpviews.IViewMvp;

/**
 * Base class responsible for MVP View implementations (ViewMvcImpl) of App Sample.
 * An implementation of {@link IViewMvp} interface.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@Setter
public abstract class BaseViewMvpImpl
        implements IViewMvp {

    protected View mRootView;
    protected Unbinder mUnbinder;

    public BaseViewMvpImpl(@NonNull final LayoutInflater layoutInflater, @Nullable final ViewGroup container) {
        setRootView(layoutInflater.inflate(getLayoutResId(), container, false));
        setUnbinder(ButterKnife.bind(this, mRootView));
    }

    /** {@inheritDoc} */
    @Override
    public abstract Bundle getViewState();

    /** {@inheritDoc} */
    @Override
    @LayoutRes
    public abstract int getLayoutResId();

    /** {@inheritDoc} */
    @Override
    public View getRootView() {
        return mRootView;
    }

    /** {@inheritDoc} */
    @Override
    public Unbinder getUnBinder() {
        return mUnbinder;
    }

    // Add remaining base logic here...

}
