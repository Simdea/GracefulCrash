package pt.simdea.gracefulcrash.sample.support.injection.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * This class is used to qualify the dependency for an Application Context instance.
 * {@see @{@link Qualifier } for more information}
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationContext { /* Do nothing here ... */ }