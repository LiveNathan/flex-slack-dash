package dev.nathanlively.application;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.CollectionAssert;

import java.util.List;

public class ResultAssert<T> extends AbstractAssert<ResultAssert<T>, RichResult<T>> {

    public ResultAssert(RichResult<T> result) {
        super(result, ResultAssert.class);
    }

    public ResultAssert<T> isSuccess() {
        isNotNull();
        List<String> errorMessages = actual.allErrorMessages();
        if (!errorMessages.isEmpty()) {
            failWithMessage("Expected success but found failure with messages: %s", errorMessages);
        }
        return this;
    }

    public ResultAssert<T> isFailure() {
        isNotNull();
        List<String> errorMessages = actual.allErrorMessages();
        if (errorMessages.isEmpty()) {
            failWithMessage("Expected failure but found success.");
        }
        return this;
    }

    public CollectionAssert<T> successValues() {
        return new CollectionAssert<>(actual.allValues());
    }

    public CollectionAssert<String> failureMessages() {
        return new CollectionAssert<>(actual.allErrorMessages());
    }
}
