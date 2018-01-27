package im.tox.toktok.app.profile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import im.tox.toktok.R;
import im.tox.toktok.app.simple_dialogs.SimpleShareDialogDesign;
import im.tox.toktok.app.simple_dialogs.SimpleStatusDialogDesign;
import im.tox.toktok.app.simple_dialogs.SimpleTextDialogDesign;

public final class ProfileFragment extends Fragment {

  @Override
  public CoordinatorLayout onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {
    super.onCreate(savedState);
    CoordinatorLayout view = (CoordinatorLayout) inflater.inflate(R.layout.activity_profile, container, false);

    getActivity().getWindow().setStatusBarColor(Color.parseColor("#2b000000"));

    view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    final AppCompatActivity activity = (AppCompatActivity) getActivity();
    Toolbar toolbar = view.findViewById(R.id.profile_toolbar);
    activity.setSupportActionBar(toolbar);
    activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigation_menu);
    activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    final DrawerLayout drawer = getActivity().findViewById(R.id.home_layout);
    toolbar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        drawer.openDrawer(GravityCompat.START);
      }
    });

    RelativeLayout mShareIDButton = view.findViewById(R.id.profile_share_id);
    mShareIDButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SimpleShareDialogDesign dial = new SimpleShareDialogDesign(getActivity());
        dial.show();
      }
    });

    TextView mChangeNickname = view.findViewById(R.id.profile_change_nickname);
    mChangeNickname.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SimpleTextDialogDesign dial = new SimpleTextDialogDesign(
          getActivity(),
          getResources().getString(R.string.profile_nickname),
          getResources().getColor(R.color.homeColorToolbar, null),
          R.drawable.ic_person_black_48dp,
          getResources().getString(R.string.sample_user_name),
          null
        );
        dial.show();
      }
    });

    TextView mChangeStatusMessage = view.findViewById(R.id.profile_change_status_text);
    mChangeStatusMessage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SimpleTextDialogDesign dial = new SimpleTextDialogDesign(
          getActivity(),
          getResources().getString(R.string.profile_status_message),
          getResources().getColor(R.color.homeColorToolbar, null),
          R.drawable.ic_person_black_48dp,
          getResources().getString(R.string.sample_status_message),
          null
        );
        dial.show();
      }
    });

    TextView mChangeStatus = view.findViewById(R.id.profile_change_status);
    mChangeStatus.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SimpleStatusDialogDesign dial = new SimpleStatusDialogDesign(getActivity(), 0);
        dial.show();
      }
    });

    TextView mChangeReject = view.findViewById(R.id.profile_change_reject_call);
    mChangeReject.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getActivity(), RejectedCallMessages.class));
      }
    });

    return view;
  }

}
