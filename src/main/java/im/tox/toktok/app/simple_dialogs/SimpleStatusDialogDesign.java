package im.tox.toktok.app.simple_dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import im.tox.toktok.R;

public final class SimpleStatusDialogDesign extends Dialog {

    private final int statusActive;

    public SimpleStatusDialogDesign(
            @NonNull Activity activity,
            int statusActive
    ) {
        super(activity, R.style.DialogSlideAnimation);
        this.statusActive = statusActive;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.simple_status_chooser_dialog_design);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        int res;
        switch (statusActive) {
            case 0:
                res = R.id.simple_dialog_status_online;
                break;
            case 1:
                res = R.id.simple_dialog_status_away;
                break;
            case 2:
                res = R.id.simple_dialog_status_busy;
                break;
            default:
                throw new RuntimeException("Unknown status: " + statusActive);
        }
        final TransitionDrawable[] activeBackgroundTransition = {(TransitionDrawable) this.findViewById(res).getBackground()};

        activeBackgroundTransition[0].startTransition(250);

        final LinearLayout onlineStatus = this.findViewById(R.id.simple_dialog_status_online);
        onlineStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeBackgroundTransition[0].reverseTransition(250);
                activeBackgroundTransition[0] = (TransitionDrawable) onlineStatus.getBackground();
                activeBackgroundTransition[0].startTransition(250);
            }
        });

        final LinearLayout awayStatus = this.findViewById(R.id.simple_dialog_status_away);
        awayStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeBackgroundTransition[0].reverseTransition(250);
                activeBackgroundTransition[0] = (TransitionDrawable) awayStatus.getBackground();
                activeBackgroundTransition[0].startTransition(250);
            }
        });

        final LinearLayout busyStatus = this.findViewById(R.id.simple_dialog_status_busy);
        busyStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeBackgroundTransition[0].reverseTransition(250);
                activeBackgroundTransition[0] = (TransitionDrawable) busyStatus.getBackground();
                activeBackgroundTransition[0].startTransition(250);
            }
        });

        final Button cancelButton = this.findViewById(R.id.simple_dialog_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
