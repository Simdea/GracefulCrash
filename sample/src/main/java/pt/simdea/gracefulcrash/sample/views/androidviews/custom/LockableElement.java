package pt.simdea.gracefulcrash.sample.views.androidviews.custom;

import android.support.annotation.NonNull;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * TODO PR: Add JavaDocs...
 * https://mpope9.github.io/2018/09/06/LockingAndroidUI.html
 */
public abstract class LockableElement<T extends View> {

    private final WeakReference<T> mView; // TODO PR: Can this be a WeakReference?
    private final AtomicBoolean mLocked = new AtomicBoolean();

    /**
     * Instantiates a new {@link LockableElement}.
     * @param view
     * @param isLocked
     */
    LockableElement(@NonNull final WeakReference<T> view, final boolean isLocked) {
        mView = view;
        mLocked.set(isLocked);
    }

    public void toggleEnabled(final boolean isEnabled) {
        final T view = mView.get();
        if (view != null) {
            view.setEnabled(isEnabled);
            view.setClickable(isEnabled);
            view.invalidate();
        }
    }

    public void setLocked() {
        mLocked.set(true);
        toggleEnabled(false);
    }

    public void setUnlocked() {
        mLocked.set(false);
        toggleEnabled(true);
    }

    public boolean isLocked() {
        return mLocked.get();
    }

}
