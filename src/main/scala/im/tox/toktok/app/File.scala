package im.tox.toktok.app

class File(name: String, date: String) {

  val fileName: String = name
  val fileDate: String = date

  def getName(): String = {
    return fileName
  }

  def getDate(): String = {
    return fileDate
  }

}

object File {
  val file = new File("Movie_2015-02-01.mp4", "Downloaded 2015-05-21")
}