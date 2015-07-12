package im.tox.toktok.app.ContactsActivity

class File( name : String, date : String){

  val fileName :String = name
  val filedate :String = date

  def getName(): String ={
    return fileName
  }

  def getDate(): String ={
    return filedate
  }

}