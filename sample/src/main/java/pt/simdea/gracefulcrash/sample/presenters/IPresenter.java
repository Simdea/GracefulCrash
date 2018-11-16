package pt.simdea.gracefulcrash.sample.presenters;

import android.support.annotation.NonNull;

import pt.simdea.gracefulcrash.sample.views.mvpviews.IViewMvp;

@SuppressWarnings("unused")
public interface IPresenter<V extends IViewMvp> {

    void attachView(@NonNull final V viewMvp);

    void detachView();

}
