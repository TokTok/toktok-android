package im.tox.toktok

import android.os.Bundle
import im.tox.toktok.TypedBundleKey.{ IntKey, StringKey }

object BundleKey {
  val colorPrimary = IntKey("colorPrimary")
  val colorPrimaryStatus = IntKey("colorPrimaryStatus")
  val contactColorPrimary = IntKey("contactColorPrimary")
  val contactColorStatus = IntKey("contactColorStatus")
  val contactName = StringKey("contactName")
  val contactPhotoReference = IntKey("contactPhotoReference")
  val imgResource = IntKey("imgResource")
  val messageTitle = StringKey("messageTitle")
  val messageType = IntKey("messageType")
}

object TypedBundleKey {
  final case class StringKey(id: String)
  final case class IntKey(id: String)

  implicit class TypedBundle(val bundle: Bundle) extends AnyVal {
    def apply(key: StringKey): String = bundle.getString(key.id)
    def apply(key: IntKey): Int = bundle.getInt(key.id)

    def update(key: StringKey, value: String): Unit = bundle.putString(key.id, value)
    def update(key: IntKey, value: Int): Unit = bundle.putInt(key.id, value)
  }
}
