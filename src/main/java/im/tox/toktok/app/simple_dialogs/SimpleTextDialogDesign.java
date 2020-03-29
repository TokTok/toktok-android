package im.tox.toktok.app.simple_dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import im.tox.toktok.R;
import im.tox.toktok.app.CompatUtil;

public final class SimpleTextDialogDesign extends Dialog {

    @NonNull
    private final Activity activity;
    private final String title;
    private final int color;
    private final int icon;
    private final String value;
    private final View.OnClickListener clickAction;

    public SimpleTextDialogDesign(
            @NonNull Activity activity,
            String title,
            int color,
            int icon,
            String value,
            View.OnClickListener clickAction
    ) {
        super(activity, R.style.DialogSlideAnimation);
        this.activity = activity;
        this.title = title;
        this.color = color;
        this.icon = icon;
        this.value = value;
        this.clickAction = clickAction;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.simple_input_dialog_design);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        this.findViewById(R.id.simple_dialog_color).setBackgroundTintList(ColorStateList.valueOf(color));
        this.<ImageView>findViewById(R.id.simple_dialog_img).setImageResource(icon);
        this.<TextView>findViewById(R.id.simple_dialog_text).setText(title);

        EditText input = this.findViewById(R.id.simple_dialog_input);
        input.setText(value);

        Button cancelButton = this.findViewById(R.id.simple_dialog_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        final Button confirmButton = this.findViewById(R.id.simple_dialog_confirm);
        input.addTextChangedListener(new TextWatcher() {
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

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });
    }
}
