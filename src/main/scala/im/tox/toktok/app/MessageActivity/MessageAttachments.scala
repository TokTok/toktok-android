package im.tox.toktok.app.MessageActivity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.{LayoutInflater, View, ViewGroup}
import im.tox.toktok.R


class MessageAttachments extends Fragment {

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    val view: View = inflater.inflate(R.layout.overlay_attachments, container, false);

    return view
  }

}