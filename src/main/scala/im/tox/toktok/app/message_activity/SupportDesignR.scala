package im.tox.toktok.app.message_activity

object SupportDesignR {

  val snackbar_text: Int = Class.forName("android.support.design.R$id").getField("snackbar_text").get(null).asInstanceOf[Int]

}
