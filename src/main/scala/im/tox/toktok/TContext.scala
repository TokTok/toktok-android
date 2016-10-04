package im.tox.toktok

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.view.{ LayoutInflater, WindowManager }

final case class TypedService[A](id: String)

object TContext {
  val INPUT_METHOD_SERVICE = TypedService[InputMethodManager](Context.INPUT_METHOD_SERVICE)
  val LAYOUT_INFLATER_SERVICE = TypedService[LayoutInflater](Context.LAYOUT_INFLATER_SERVICE)
  val WINDOW_SERVICE = TypedService[WindowManager](Context.WINDOW_SERVICE)
}

object TypedService {
  implicit class TypedContext(val c: Context) extends AnyVal {
    def getSystemService[A](ts: TypedService[A]): A = c.getSystemService(ts.id).asInstanceOf[A]
  }
}
