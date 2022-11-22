package com.example.musiccircle;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Objects;

public class ViewDialog {
    public void showDialog(Context context, String str) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.group_chat_error_dialog);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        FrameLayout mDialogOk = dialog.findViewById(R.id.frmOk);
        mDialogOk.setOnClickListener(v -> dialog.cancel());

        TextView mDialogErr = dialog.findViewById(R.id.dialog_err_message);
        mDialogErr.setText(str);

        dialog.show();
    }
}
