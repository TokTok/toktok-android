package im.tox.toktok

import android.os.Bundle
import im.tox.toktok.TypedBundleKey.{ IntKey, StringKey }

import scala.annotation.varargs

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
  sealed abstract class KeyValue[A] {
    def put(bundle: Bundle): Unit
  }

  final case class StringKeyValue(id: String, value: String) extends KeyValue[String] {
    def put(bundle: Bundle): Unit = bundle.putString(id, value)
  }
  final case class IntKeyValue(id: String, value: Int) extends KeyValue[Int] {
    def put(bundle: Bundle): Unit = bundle.putInt(id, value)
  }

  final case class StringKey(id: String) {
    def ->(value: String): StringKeyValue = StringKeyValue(id, value)
  }
  final case class IntKey(id: String) {
    def ->(value: Int): IntKeyValue = IntKeyValue(id, value)
  }

  implicit class TypedBundle(val bundle: Bundle) extends AnyVal {
    def apply(key: StringKey): String = bundle.getString(key.id)
    def apply(key: IntKey): Int = bundle.getInt(key.id)
  }

  @varargs
  def SBundle(keyValues: KeyValue[_]*): Bundle = {
    val bundle = new Bundle
    keyValues.foreach(_.put(bundle))
    bundle
  }
}
