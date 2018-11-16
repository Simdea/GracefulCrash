package pt.simdea.gracefulcrash.sample.support.injection.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * This class is used to specify an Activity scope in which a dependency object persists.
 * {@see @{@link Scope} for more information}
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity { /* Do nothing here ... */ }

