package com.example.pc.basemvp.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pc.basemvp.R;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;
import java.util.concurrent.TimeUnit;

public class RxView {

    public RxView() {
        // No-Op
    }

    public static Observable<View> clicks(View view, boolean isCheckNetwork) {
        return Observable.create((ObservableOnSubscribe<View>) emitter -> {
            emitter.setCancellable(() -> {
                view.setOnClickListener(null);
                emitter.onComplete();
            });
            view.setOnClickListener(v -> {
                boolean isConnected = InternetManager.isConnected(v.getContext());
                if (isCheckNetwork && !isConnected) {
                    String errorMessage = v.getContext()
                            .getApplicationContext()
                            .getString(R.string.msg_error_no_internet);
                    Toast.makeText(v.getContext(), errorMessage, Toast.LENGTH_LONG).show();
                    return;
                }
                emitter.onNext(v);
            });
        }).throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread());
    }

    public static Observable<String> search(TextView textView) {

        final PublishSubject<String> subject = PublishSubject.create();

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //No-Op
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    subject.onNext(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //No-Op
            }
        });
        return subject;
    }
}
