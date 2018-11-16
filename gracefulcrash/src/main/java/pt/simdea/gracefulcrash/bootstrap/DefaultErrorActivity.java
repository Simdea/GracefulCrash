package pt.simdea.gracefulcrash.bootstrap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import pt.simdea.gracefulcrash.R;
import pt.simdea.gracefulcrash.data.constants.GracefulCrashConstants;

/**
 * TODO PR: Add JavaDocs...
 */
public final class DefaultErrorActivity extends AppCompatActivity {

    private TextView mText;

    /** {@inheritDoc} */
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gracefulcrash_default_error);


        mText = findViewById(R.id.tvDefaultErrorText);

        final Intent intent = getIntent();
        if (intent != null) {
            final Bundle extras = intent.getExtras();
            if (extras != null) {
                mText.setText(extras.getString(GracefulCrashConstants.ACTIVITY_LOG_DATA));
            }
        }

        // TODO
    }

    /* Add further implementation here ... */

}
