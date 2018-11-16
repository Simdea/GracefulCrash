package pt.simdea.gracefulcrash.sample.support.bootstrap;

import android.os.StrictMode;

import pt.simdea.gracefulcrash.sample.support.data.constants.ExceptionConstants;

/**
 * Utility class meant to handle Strict Mode related operations.
 */
final class StrictModeAdmin {

    /**
     * Instantiates a new StrictModeAdmin. Private to prevent instantiation.
     */
    private StrictModeAdmin() {
        throw new AssertionError(ExceptionConstants.ASSERTION_ERROR); // Throw an exception if this *is* ever called
    }

    /**
     * Procedure meant to enable and configure Strict Mode Virtual Machine Policy.
     */
    static void enableStrictModeVMPolicy() {
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build());
    }

    /**
     * Procedure meant to enable and configure Strict Mode Thread Policy.
     */
    static void enableStrictModeThreadPolicy() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
    }

}
