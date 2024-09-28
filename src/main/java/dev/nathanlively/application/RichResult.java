package dev.nathanlively.application;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.function.SerializableFunction;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public interface RichResult<R> extends Result<R> {
    static <R> RichResult<R> success(R value) {
        Objects.requireNonNull(value, "value cannot be null");
        return success(Collections.singletonList(value));
    }

    static <R> RichResult<R> success(List<R> values) {
        return new MultiSuccessResult<>(values);
    }

    static <R> RichResult<R> failure(List<String> messages) {
        Objects.requireNonNull(messages, "messages cannot be null");
        return new MultiErrorResult<>(messages);
    }

    static <R> RichResult<R> failure(String message) {
        Objects.requireNonNull(message, "message cannot be null");
        return failure(Collections.singletonList(message));
    }

    List<R> allValues();

    List<String> allErrorMessages();

    class MultiSuccessResult<R> implements RichResult<R> {
        private final List<R> values;

        MultiSuccessResult(List<R> values) {
            this.values = values;
        }

        @Override
        public void handle(SerializableConsumer<R> ifOk, SerializableConsumer<String> ifError) {
            values.forEach(ifOk);
        }

        @Override
        public <S> Result<S> flatMap(SerializableFunction<R, Result<S>> mapper) {
            List<Result<S>> mappedResults = values.stream()
                    .map(mapper)
                    .toList();

            List<String> errorMessages = mappedResults.stream()
                    .filter(Result::isError)
                    .map(Result::getMessage)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            if (!errorMessages.isEmpty()) {
                return Result.error(String.join("; ", errorMessages));
            }

            List<S> successValues = mappedResults.stream()
                    .filter(result -> !result.isError())
                    .map(result -> ((RichResult<S>) result).allValues())
                    .flatMap(List::stream)
                    .collect(Collectors.toList());

            return RichResult.success(successValues);
        }

        @Override
        public boolean isError() {
            return false;
        }

        @Override
        public Optional<String> getMessage() {
            return Optional.empty();
        }

        @Override
        public <X extends Throwable> R getOrThrow(SerializableFunction<String, ? extends X> exceptionProvider) {
            return values.getFirst(); // Return the first value, or throw an exception if needed
        }

        @Override
        public List<R> allValues() {
            return values;
        }

        @Override
        public List<String> allErrorMessages() {
            return List.of();
        }
    }

    class MultiErrorResult<R> implements RichResult<R> {
        private final List<String> errorMessages;

        MultiErrorResult(List<String> errorMessages) {
            this.errorMessages = errorMessages;
        }

        @Override
        public void handle(SerializableConsumer<R> ifOk, SerializableConsumer<String> ifError) {
            errorMessages.forEach(ifError);
        }

        @Override
        public <S> Result<S> flatMap(SerializableFunction<R, Result<S>> mapper) {
            return Result.error(String.join("; ", errorMessages));
        }

        @Override
        public boolean isError() {
            return true;
        }

        @Override
        public Optional<String> getMessage() {
            return Optional.of(String.join("; ", errorMessages));
        }

        @Override
        public <X extends Throwable> R getOrThrow(SerializableFunction<String, ? extends X> exceptionProvider) throws X {
            throw exceptionProvider.apply(String.join("; ", errorMessages));
        }

        @Override
        public List<R> allValues() {
            return List.of();
        }

        @Override
        public List<String> allErrorMessages() {
            return errorMessages;
        }
    }
}
