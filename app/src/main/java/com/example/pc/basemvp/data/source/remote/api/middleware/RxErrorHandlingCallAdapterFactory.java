package com.example.pc.basemvp.data.source.remote.api.middleware;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import com.example.pc.basemvp.data.source.remote.api.error.BaseException;
import com.example.pc.basemvp.data.source.remote.api.error.ErrorResponse;
import com.google.gson.JsonSyntaxException;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * ErrorHandling:
 * http://bytes.babbel.com/en/articles/2016-03-16-retrofit2-rxjava-error-handling.html
 * This class only for Call in retrofit 2
 */
public final class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {

    private static final String TAG = RxErrorHandlingCallAdapterFactory.class.getName();

    private final RxJava2CallAdapterFactory original;

    private RxErrorHandlingCallAdapterFactory() {
        original = RxJava2CallAdapterFactory.create();
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType, @NonNull Annotation[] annotations,
            @NonNull Retrofit retrofit) {
        return new RxCallAdapterWrapper(returnType,
                original.get(returnType, annotations, retrofit));
    }

    /**
     * RxCallAdapterWrapper
     */
    class RxCallAdapterWrapper<R> implements CallAdapter<R, Object> {
        private final Type returnType;
        private final CallAdapter<R, Object> wrapped;

        RxCallAdapterWrapper(Type type, CallAdapter<R, Object> wrapped) {
            returnType = type;
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @Override
        public Object adapt(@NonNull Call<R> call) {
            Class<?> rawType = getRawType(returnType);

            boolean isFlowable = rawType == Flowable.class;
            boolean isSingle = rawType == Single.class;
            boolean isMaybe = rawType == Maybe.class;
            boolean isCompletable = rawType == Completable.class;
            if (rawType != Observable.class && !isFlowable && !isSingle && !isMaybe) {
                return null;
            }
            if (!(returnType instanceof ParameterizedType)) {
                String name = isFlowable ? "Flowable"
                        : isSingle ? "Single" : isMaybe ? "Maybe" : "Observable";
                throw new IllegalStateException(name
                        + " return type must be parameterized"
                        + " as "
                        + name
                        + "<Foo> or "
                        + name
                        + "<? extends Foo>");
            }

            if (isFlowable) {
                return ((Flowable) wrapped.adapt(call)).onErrorResumeNext(throwable -> {
                    return Flowable.error(convertToBaseException((Throwable) throwable));
                });
            }
            if (isSingle) {
                return ((Single) wrapped.adapt(call)).onErrorResumeNext(
                        throwable -> Single.error(convertToBaseException((Throwable) throwable)));
            }
            if (isMaybe) {
                return ((Maybe) wrapped.adapt(call)).onErrorResumeNext(throwable -> {
                    return Maybe.error(convertToBaseException((Throwable) throwable));
                });
            }
            if (isCompletable) {
                return ((Completable) wrapped.adapt(call)).onErrorResumeNext(
                        throwable -> Completable.error(convertToBaseException(throwable)));
            }
            return ((Observable) wrapped.adapt(call)).onErrorResumeNext(throwable -> {
                return Observable.error(convertToBaseException((Throwable) throwable));
            });
        }

        private BaseException convertToBaseException(Throwable throwable) {
            if (throwable instanceof BaseException) {
                return (BaseException) throwable;
            }

            if (throwable instanceof IOException) {
                return BaseException.toNetworkError(throwable);
            }

            if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                Response response = httpException.response();
                if (response.errorBody() != null) {
                    try {
                        String errorMessage = ErrorResponse.getErrorMessage(response);
                        if (errorMessage != null && !TextUtils.isEmpty(errorMessage)) {
                            return BaseException.toServerError(errorMessage);
                        } else {
                            return BaseException.toHttpError(response);
                        }
                    } catch (JsonSyntaxException e) {
                        Log.e(TAG, e.getMessage());
                        return BaseException.toUnexpectedError(e);
                    }
                } else {
                    return BaseException.toHttpError(response);
                }
            }

            return BaseException.toUnexpectedError(throwable);
        }
    }
}
