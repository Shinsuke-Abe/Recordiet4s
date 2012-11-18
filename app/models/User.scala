package models

case class User(id: Option[Long], mailAddress: String, password: String)

object User {
  
  def authenticate(mailAddress: String, password: String): Option[User] = {
    None
  }
}