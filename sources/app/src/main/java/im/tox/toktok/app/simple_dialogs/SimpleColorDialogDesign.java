package im.tox.toktok.app.simple_dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import im.tox.toktok.R;
import im.tox.toktok.app.MaterialColors;

public final class SimpleColorDialogDesign extends Dialog {

    @NonNull
    private final Activity activity;
    private final String title;
    private final int contactColor;
    private final int icon;
    private final int color;
    private final OnClickListener clickAction;

    public SimpleColorDialogDesign(
            @NonNull Activity activity,
            String title,
            int contactColor,
            int icon,
            int color,
            OnClickListener clickAction
    ) {
        super(activity, R.style.DialogSlideAnimation);
        this.activity = activity;
        this.title = title;
        this.contactColor = contactColor;
        this.icon = icon;
        this.color = color;
        this.clickAction = clickAction;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.simple_color_dialog_design);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        this.findViewById(R.id.simple_dialog_color).setBackgroundTintList(ColorStateList.valueOf(contactColor));
        this.<ImageView>findViewById(R.id.simple_dialog_img).setImageResource(icon);
        this.<TextView>findViewById(R.id.simple_dialog_text).setText(title);

        RecyclerView color_recycler = this.findViewById(R.id.simple_color_dialog_recycler_view);

        color_recycler.setAdapter(new SimpleColorDialogDesignAdapter(MaterialColors.colors));
        color_recycler.setLayoutManager(new LinearLayoutManager(activity));

        Button cancelButton = this.findViewById(R.id.simple_dialog_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
