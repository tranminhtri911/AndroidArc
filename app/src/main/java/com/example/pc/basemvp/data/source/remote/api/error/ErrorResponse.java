package com.example.pc.basemvp.data.source.remote.api.error;

import android.util.Log;
import com.example.pc.basemvp.utils.LogUtils;
import com.example.pc.basemvp.utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Response;

/**
 * Created by MyPC on 30/10/2017.
 */

public class ErrorResponse {

    private static final String TAG = "ErrorResponse";
    private static final String ENTER_SPACE_FORMAT = "\n";
    private static final String ERROR_PARAM = "error";

    public static String getErrorMessage(Response response) {
        if (response == null) {
            return "";
        }
        try {
            JSONObject errorRes = new JSONObject(response.errorBody().string());

            String errorStr = errorRes.getString(ERROR_PARAM);

            if (!Utils.isJson(errorStr)) {
                LogUtils.d(TAG, errorStr);
                return errorStr;
            }

            List<String> errors = new ArrayList<>();
            String jsonString = errorRes.toString();
            JSONObject jObject = new JSONObject(jsonString).getJSONObject(ERROR_PARAM);
            Iterator<String> keys = jObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                JSONArray array = jObject.getJSONArray(key);
                for (int i = 0; i < array.length(); i++) {
                    errors.add(array.getString(i));
                }
            }

            String error = convertStringToListStringWithFormatPattern(errors, ENTER_SPACE_FORMAT);
            LogUtils.d(TAG, error);
            return error;
        } catch (JSONException | IOException e) {
            Log.e(TAG, "getMessageError: ", e);
            return "";
        }
    }

    private static String convertStringToListStringWithFormatPattern(List<String> strings,
            String format) {
        if (strings.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String s : strings) {
            builder.append(s);
            builder.append(format);
        }
        String result = builder.toString();
        result = result.substring(0, result.length() - format.length());
        return result;
    }
}
