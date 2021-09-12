package com.example.pc.basemvp.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.example.pc.basemvp.R;
import java.util.Objects;

public class MessageDialog extends Dialog {

    public MessageDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_message);
    }

    public MessageDialog setTitle(String pTitle){
        ((TextView)findViewById(R.id.tv_dialog_message_title)).setText(pTitle);
        return this;
    }

    public MessageDialog setContent(String pContent){
        ((TextView)findViewById(R.id.tv_dialog_message_content)).setText(pContent);
        return this;
    }

    public MessageDialog setActionListener(View.OnClickListener pListener){
        findViewById(R.id.btn_dialog_ok).setOnClickListener(pListener);
        return this;
    }
}

