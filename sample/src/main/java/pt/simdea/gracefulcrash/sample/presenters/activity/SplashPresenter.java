package pt.simdea.gracefulcrash.sample.presenters.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Class responsible for the Splash Screen.
 */
public class SplashPresenter extends BaseActivity {

    /** {@inheritDoc} */
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomePresenter.start(this); // Start home activity
        finish(); // close splash activity
    }

}
