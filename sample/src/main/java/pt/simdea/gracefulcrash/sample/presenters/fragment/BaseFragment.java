package pt.simdea.gracefulcrash.sample.presenters.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lombok.Getter;
import pt.simdea.gracefulcrash.sample.presenters.IPresenter;
import pt.simdea.gracefulcrash.sample.support.data.constants.ExceptionConstants;
import pt.simdea.gracefulcrash.sample.support.data.exceptions.MvpViewNotAttachedException;
import pt.simdea.gracefulcrash.sample.views.mvpviews.IViewMvp;

/**
 * Android SimpleRadioFramework's Base {@link Fragment} class, meant to serve as {@link IPresenter}.
 * @param <T> child object (Mvp View) to a {@link IViewMvp}.
 */
@SuppressWarnings("unused")
public class BaseFragment<T extends IViewMvp> extends Fragment
        implements IPresenter<T> {

    @Nullable
    @Getter
    private T mViewMvp;

    /** {@inheritDoc} */
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View rootView = super.onCreateView(inflater, container, savedInstanceState);
        // Add logic here...
        return rootView;
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

    protected boolean isViewAttached() {
        if (mViewMvp != null) {
            return true;
        } else {
            throw new MvpViewNotAttachedException(ExceptionConstants.MVP_VIEW_NOT_ATTACHED_ERROR);
        }
    }

    // Add remaining base logic here...

}
