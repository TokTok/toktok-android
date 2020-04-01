package im.tox.toktok.app.simple_dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import im.tox.toktok.R;
import im.tox.toktok.app.CompatUtil;

public final class SimpleAddFriendDialogDesign extends Dialog {

    @NonNull
    private final Activity activity;
    private final View.OnClickListener clickAction;

    public SimpleAddFriendDialogDesign(
            @NonNull Activity activity,
            View.OnClickListener clickAction
    ) {
        super(activity, R.style.DialogSlideAnimation);
        this.activity = activity;
        this.clickAction = clickAction;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.simple_addfriend_dialog_design);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText input = this.findViewById(R.id.simple_dialog_input);
        final Button confirmButton = this.findViewById(R.id.simple_dialog_confirm);

        Button cancelButton = this.findViewById(R.id.simple_dialog_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                confirmButton.setEnabled(true);
                final Resources resources = activity.getResources();
                confirmButton.setTextColor(CompatUtil.getColor(resources, R.color.simpleDialogTextButton));
                confirmButton.setBackgroundTintList(ColorStateList.valueOf(CompatUtil.getColor(resources, R.color.simpleDialogIconButton)));
                confirmButton.setOnClickListener(clickAction);
            }
        });
    }

}
