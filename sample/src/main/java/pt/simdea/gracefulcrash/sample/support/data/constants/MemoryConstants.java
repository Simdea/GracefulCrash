package pt.simdea.gracefulcrash.sample.support.data.constants;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Utility class meant to hold all System Memory state {@link String} Constants.
 */
@Retention(RetentionPolicy.CLASS)
@StringDef({
        MemoryConstants.SYSTEM_LOW_MEMORY,
        MemoryConstants.SYSTEM_TRIM_MEMORY_RUNNING_MODERATE,
        MemoryConstants.SYSTEM_TRIM_MEMORY_RUNNING_LOW,
        MemoryConstants.SYSTEM_TRIM_MEMORY_RUNNING_CRITICAL,
        MemoryConstants.SYSTEM_TRIM_MEMORY_BACKGROUND,
        MemoryConstants.SYSTEM_TRIM_MEMORY_MODERATE,
        MemoryConstants.SYSTEM_TRIM_MEMORY_COMPLETE,
        MemoryConstants.SYSTEM_TRIM_MEMORY_UI_HIDDEN
})
public @interface MemoryConstants {

    String SYSTEM_LOW_MEMORY = "::Running low on memory...";
    String SYSTEM_TRIM_MEMORY_RUNNING_MODERATE
            = "::Device is beginning to run low on memory. The Application is running and cannot be killed.";
    String SYSTEM_TRIM_MEMORY_RUNNING_LOW
            = "::Device is running much lower on memory. The Application is running and cannot be killed.";
    String SYSTEM_TRIM_MEMORY_RUNNING_CRITICAL
            = "::Device is running extremely low on memory. The Application is not yet considered to be killed.\n"
            + "The system will start killing background processes, so we need to release non-critical resources.";
    String SYSTEM_TRIM_MEMORY_BACKGROUND
            = "::The system is running low on memory and your process is near the beginning of the LRU list.";
    String SYSTEM_TRIM_MEMORY_MODERATE
            = "::The system is running low on memory and your process is near the middle of the LRU list.";
    String SYSTEM_TRIM_MEMORY_COMPLETE
            = "::The system is running low on memory and your process is one of the first to be killed"
            + " if the system does not recover memory now.";
    String SYSTEM_TRIM_MEMORY_UI_HIDDEN
            = "::UI is no longer visible, so this is a good time to release large resources used by UI.";

}
