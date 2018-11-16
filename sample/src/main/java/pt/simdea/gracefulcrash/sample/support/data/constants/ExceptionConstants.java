package pt.simdea.gracefulcrash.sample.support.data.constants;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Utility class meant to hold all Exception {@link String} Constants.
 */
@Retention(RetentionPolicy.CLASS)
@StringDef({
        ExceptionConstants.GENERIC_ERROR,
        ExceptionConstants.ASSERTION_ERROR,
        ExceptionConstants.ILLEGAL_ARGUMENT_ERROR,
        ExceptionConstants.UNSUPPORTED_OPERATION_ERROR,
        ExceptionConstants.OPTIONAL_VALUE_UNDEFINED,
        ExceptionConstants.FAILED_TO_BIND_SERVICE,
        ExceptionConstants.MVP_VIEW_NOT_ATTACHED_ERROR,
        ExceptionConstants.MODEL_API_REQUEST_IO_ERROR,
        ExceptionConstants.MODEL_OBSERVABLE_ILLEGAL_ARGUMENT_ERROR,
        ExceptionConstants.MODEL_OBSERVABLE_ILLEGAL_STATE_ERROR
})
public @interface ExceptionConstants {

    /* Misc. Exception Messages. */
    String GENERIC_ERROR = "Encountered an error. Sorry for the inconvenience.";
    String ASSERTION_ERROR = "Instantiating utility class.";
    String ILLEGAL_ARGUMENT_ERROR = "Found invalid argument when trying to instantiate %s class.";
    String UNSUPPORTED_OPERATION_ERROR = "Unsupported Operation.";
    String OPTIONAL_VALUE_UNDEFINED = "Unexpected error when using Optional: Value is not defined.";

    /* Android Component Exception Messages. */
    String FAILED_TO_BIND_SERVICE = "Failed to bind to service.";

    /* MVP View Exception Messages. */
    String MVP_VIEW_NOT_ATTACHED_ERROR
            = "Please call Presenter.attachView(MvpView) before requesting data to the Presenter.";

    /* MVP Model Exception Messages. */
    String MODEL_API_REQUEST_IO_ERROR = "Failed to retrieve data due to failed IO operation.";
    String MODEL_OBSERVABLE_ILLEGAL_ARGUMENT_ERROR = "Failed observable operation due to null observer argument.";
    String MODEL_OBSERVABLE_ILLEGAL_STATE_ERROR = "Failed observable operation due to invalid observer list.";

}
