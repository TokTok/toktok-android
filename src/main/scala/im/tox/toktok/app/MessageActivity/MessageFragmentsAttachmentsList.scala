package im.tox.toktok.app.MessageActivity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.{View, ViewGroup, LayoutInflater}
import im.tox.toktok.R

class MessageFragmentsAttachmentsList extends Fragment{

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedState: Bundle): View = {

    val view: View = inflater.inflate(R.layout.fragment_attachments_files, container, false)
    return view

  }

}