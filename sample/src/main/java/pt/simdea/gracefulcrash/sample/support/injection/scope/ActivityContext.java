package pt.simdea.gracefulcrash.sample.support.injection.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * This class is used to qualify the dependency for an Activity Context instance.
 * {@see @{@link Qualifier} for more information}
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityContext { /* Do nothing here ... */ }
