package com.example.hakatonfinaljava.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.hakatonfinaljava.responses.BaseResponse;
import com.redmadrobot.inputmask.helper.Mask;
import com.redmadrobot.inputmask.model.CaretString;

import retrofit2.Response;

public final class Utils {

    public static String formatPhone(String param) {
        Mask mask = new Mask("7 [000] [000] [00] [00]");
        Mask.Result result = mask.apply(
                new CaretString(
                        param,
                        param.length(),
                        new CaretString.CaretGravity.BACKWARD(false)
                )
        );
        return result.getFormattedText().getString();
    }

    public static <T extends BaseResponse> T handleResults(Response<T> response) throws Throwable {
        if (response.isSuccessful()) {
            T body = (T) response.body();
            if (body != null) {
                switch (body.getResult()) {
                    case 0:
                        return body;
                    default:
                        throw new Throwable(body.getDescription());
                }
            } else {
                throw new Throwable("Data is null");
            }
        }
        if (response.errorBody() != null) {
            // todo
            throw new Throwable(response.message());
        } else {
            throw new Throwable(response.message());
        }
    }

    public static void handleError(Throwable throwable, Context context) {
        Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
