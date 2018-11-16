package pt.simdea.gracefulcrash.sample.views.mvpviews;

import android.support.annotation.NonNull;

/**
 * This interface corresponds to Main / Home Screen high level logic (behavior).
 */
public interface IHomeViewMvp extends IViewMvp {

    /* HomePresenter's contract. What is Home Activity meant to do? */

    /**
     * Custom Listener interface for providing Listener logic to this Mvp View.
     */
    interface StartTestViewMvcListener {

        /**
         * Procedure meant to handle clicks to the Test Button.
         */
        void onTestStartRequest();

    }

    /**
     * Procedure meant to set the {@link StartTestViewMvcListener} to this Mvp View.
     * @param listener {@link StartTestViewMvcListener} instance representing the listener that should be notified; null
     *                 to clear.
     */
    void setTestStartListener(@NonNull final StartTestViewMvcListener listener);

    /**
     * Procedure meant to receive the target data to be handled by this Mvp View.
     * @param content {@link String} value representing the target data to be handled by this Mvp View.
     */
    void onDataProcessedByModel(@NonNull final String content);

}
