package im.tox.toktok.app.simple_dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import im.tox.toktok.R;

public final class SimpleShareDialogDesign extends Dialog {

    public SimpleShareDialogDesign(@NonNull Context context) {
        super(context, R.style.DialogSlideAnimation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.simple_share_dialog_design);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button cancelButton = this.findViewById(R.id.simple_dialog_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
