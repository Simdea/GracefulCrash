package pt.simdea.gracefulcrash.bootstrap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import pt.simdea.gracefulcrash.R;
import pt.simdea.gracefulcrash.data.constants.GracefulCrashConstants;

/**
 * Class responsible for GracefulCrash's default error {@link Activity}.
 */
public final class DefaultErrorActivity extends AppCompatActivity
        implements IErrorActivity {

    private TextView mText;

    /** {@inheritDoc} */
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gracefulcrash_default_error);
        bindDefaultErrorViews();
    }

    /** {@inheritDoc} */
    @Override
    protected void onStart() {
        super.onStart();
        processIntentData(getIntent());
    }

    /** {@inheritDoc} */
    @Override
    public void onActivityLog(@NonNull final TextView view, @NonNull final String activityLog) {
        view.setText(activityLog);
    }

    /** {@inheritDoc} */
    @Override
    public void onLogErrorOnRestart(@NonNull String logErrorOnRestartStackTrace) {
        if (GracefulCrash.getInstance().getConfiguration().isLogErrorOnRestart()) {
            Log.e(GracefulCrashConstants.TAG, logErrorOnRestartStackTrace);
        }
    }

    private void processIntentData(@Nullable final Intent intent) {
        if (intent != null) {
            final Bundle extras = intent.getExtras();
            if (extras != null) {
                onActivityLog(mText, extras.getString(GracefulCrashConstants.ACTIVITY_LOG_DATA,
                        GracefulCrashConstants.EMPTY_STRING));
                onLogErrorOnRestart(extras.getString(GracefulCrashConstants.ACTIVITY_ERROR_LOG_ON_RESTART_DATA,
                        GracefulCrashConstants.EMPTY_STRING));
            }
        } else {
            Log.d(GracefulCrashConstants.TAG, GracefulCrashConstants.ACTIVITY_UNABLE_TO_PROCESS_INTENT_DATA);
        }
    }

    private void bindDefaultErrorViews() {
        mText = findViewById(R.id.tvDefaultErrorText);
    }

    /* Add further implementation here ... */

}
