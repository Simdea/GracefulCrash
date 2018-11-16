package pt.simdea.gracefulcrash.sample.support.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pt.simdea.gracefulcrash.sample.support.injection.scope.ApplicationContext;

/**
 * Dependency provider responsible for providing objects which can be injected at the Application level.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
@Module
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationModule {

    private Application mApplication;

    /**
     * Procedure meant to provide the Application level {@link Context} instance.
     * @return the Application level Context instance.
     */
    @Provides
    @Singleton
    @ApplicationContext
    public Context provideContext() {
        return mApplication;
    }

    /**
     * Procedure meant to provide the {@link Application} instance.
     * @return the Application instance.
     */
    @Provides
    @Singleton
    public Application provideApplication() {
        return mApplication;
    }

    // Add remaining module provisions here...

}
