package com.example.pc.basemvp.data.source.remote.api.error;

import android.support.annotation.Nullable;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import retrofit2.Response;

public final class BaseException extends RuntimeException {

    private static final String TIME_OUT = "タイムアウトに達しました";
    private static final String SERVER_ERROR = "システムエラーが発生しました";

    @Type
    private final String type;
    @Nullable
    private Response response;
    @Nullable
    private String errorMessage;

    private BaseException(@Type String type, Throwable cause) {
        super(cause.getMessage(), cause);
        this.type = type;
    }

    private BaseException(@Type String type, @Nullable Response response) {
        this.type = type;
        this.response = response;
    }

    public BaseException(@Type String type, @Nullable String errorMessage) {
        this.type = type;
        this.errorMessage = errorMessage;
    }

    public static BaseException toNetworkError(Throwable cause) {
        return new BaseException(Type.NETWORK, cause);
    }

    public static BaseException toHttpError(Response response) {
        return new BaseException(Type.HTTP, response);
    }

    public static BaseException toUnexpectedError(Throwable cause) {
        return new BaseException(Type.UNEXPECTED, cause);
    }

    public static BaseException toServerError(String errorMessage) {
        return new BaseException(Type.SERVER, errorMessage);
    }

    @Type
    public String getErrorType() {
        return type;
    }

    public String getMessage() {
        switch (type) {
            case Type.SERVER:
                return errorMessage;
            case Type.NETWORK:
                return getNetworkErrorMessage(getCause());
            case Type.HTTP:
                if (response != null) {
                    return getHttpErrorMessage(response.code());
                }
                return SERVER_ERROR;
            default:
                return SERVER_ERROR;
        }
    }

    private String getNetworkErrorMessage(Throwable throwable) {
        if (throwable instanceof SocketTimeoutException) {
            return throwable.getMessage();
        }

        if (throwable instanceof UnknownHostException) {
            return throwable.getMessage();
        }

        if (throwable instanceof IOException) {
            return throwable.getMessage();
        }

        return throwable.getMessage();
    }

    private String getHttpErrorMessage(int httpCode) {
        if (httpCode >= 300 && httpCode <= 308) {
            // Redirection
            return SERVER_ERROR;
        }
        if (httpCode >= 400 && httpCode <= 451) {
            // Client error
            return SERVER_ERROR;
        }
        if (httpCode >= 500 && httpCode <= 511) {
            // Server error
            return SERVER_ERROR;
        }

        // Unofficial error
        return SERVER_ERROR;
    }
}
