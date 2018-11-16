package pt.simdea.gracefulcrash.sample.views.mvpviews;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.Unbinder;

@SuppressWarnings("unused")
public interface IViewMvp {

    @Nullable
    View getRootView();

    @Nullable
    Bundle getViewState();

    @Nullable
    Unbinder getUnBinder();

    @LayoutRes
    int getLayoutResId();

}
