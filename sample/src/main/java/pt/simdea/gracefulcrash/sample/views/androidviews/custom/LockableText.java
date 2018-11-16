package pt.simdea.gracefulcrash.sample.views.androidviews.custom;

import android.support.annotation.NonNull;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * TODO
 */
public final class LockableText extends LockableElement<TextView> {

    /**
     * Instantiates a new {@link LockableText}.
     * @param view
     * @param isLocked
     */
    public LockableText(@NonNull final TextView view, final boolean isLocked) {
        super(new WeakReference<>(view), isLocked);

    }

}
