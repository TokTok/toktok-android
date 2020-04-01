package im.tox.toktok.app.simple_dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import im.tox.toktok.R;

public final class SimpleDialogDesign extends Dialog {

    private final String question;
    private final int color;
    private final int icon;
    private final View.OnClickListener clickAction;

    public SimpleDialogDesign(
            @NonNull Activity activity,
            String question,
            int color,
            int icon,
            View.OnClickListener clickAction
    ) {
        super(activity, R.style.DialogSlideAnimation);
        this.question = question;
        this.color = color;
        this.icon = icon;
        this.clickAction = clickAction;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.simple_dialog_design);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        this.findViewById(R.id.simple_dialog_color).setBackgroundTintList(ColorStateList.valueOf(color));
        this.<ImageView>findViewById(R.id.simple_dialog_img).setImageResource(icon);
        this.<TextView>findViewById(R.id.simple_dialog_text).setText(question);

        Button confirmButton = this.findViewById(R.id.simple_dialog_confirm);
        confirmButton.setOnClickListener(clickAction);

        Button cancelButton = this.findViewById(R.id.simple_dialog_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
