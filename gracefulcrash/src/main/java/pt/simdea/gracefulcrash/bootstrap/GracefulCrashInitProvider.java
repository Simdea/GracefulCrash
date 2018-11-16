package pt.simdea.gracefulcrash.bootstrap;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import lombok.NoArgsConstructor;
import pt.simdea.gracefulcrash.BuildConfig;
import pt.simdea.gracefulcrash.data.constants.GracefulCrashConstants;

/**
 * Empty {@link ContentProvider} meant to initialise the GracefulCrash library module.
 *
 * We use an empty provider so that the library module can have access to the application's {@link Context} before
 * any {@link Activity} is started.
 *
 * @see <a href="https://medium.com/@andretietz/auto-initialize-your-android-library-2349daf06920">
 *     Auto-initialize your android library</a>
 */
@NoArgsConstructor
public final class GracefulCrashInitProvider extends ContentProvider {

    /** {@inheritDoc} */
    @Override
    public boolean onCreate() {
        final Context context = getContext(); // get the context (Application context)
        GracefulCrash.getInstance().install(context); // initialize whatever you need
        Log.e(GracefulCrashConstants.TAG, "BuildConfig -> " + BuildConfig.BUILD_TYPE);
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void attachInfo(@NonNull final Context context, @Nullable final ProviderInfo providerInfo) {
        if (providerInfo == null) {
            throw new IllegalArgumentException(GracefulCrashConstants.INVALID_ARGUMENT);
        }
        // If the authorities equal the library internal ones, the developer forgot to set his applicationId!
        if (GracefulCrashConstants.PROVIDER_ID.equals(providerInfo.authority)) {
            throw new IllegalStateException(GracefulCrashConstants.INVALID_PROVIDER_AUTHORITY);
        }
        super.attachInfo(context, providerInfo);
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public Cursor query(@NonNull final Uri uri, @Nullable final String[] projection, @Nullable final String selection,
                        @Nullable final String[] selectionArgs, @Nullable final String sortOrder) {
        return null;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public String getType(@NonNull final Uri uri) {
        return null;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public Uri insert(@NonNull final Uri uri, @Nullable final ContentValues values) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public int delete(@NonNull final Uri uri, @Nullable final String selection,
                      @Nullable final String[] selectionArgs) {
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public int update(@NonNull final Uri uri, @Nullable final ContentValues values,
                      @Nullable final String selection, @Nullable final String[] selectionArgs) {
        return 0;
    }

}
