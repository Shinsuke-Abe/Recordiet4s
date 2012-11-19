package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class User(id: Option[Long], mailAddress: String, password: String, displayName: String)

object User {
  
  val user = {
    get[Long]("id")~
    get[String]("mail_address")~
    get[String]("password")~
    get[String]("display_name") map {
      case id~mail_address~password~display_name => User(Some(id), mail_address, password, display_name)
    }
  }
  
  def authenticate(mailAddress: String, password: String): Option[User] = {
    DB.withConnection { implicit connection =>
      SQL("""
          select * from recordiet_user
          where mail_address = {mailAddress}
          and   password = {password}"""
      ).on(
          'mailAddress -> mailAddress,
          'password -> password
      ).as(user.singleOpt)
    }
  }
  
  def create(newUser: User) {
    DB.withConnection { implicit connection =>
      SQL("""
          insert into recordiet_user
          (mail_address, password, display_name) values
          ({mailAddress}, {password}, {displayName})"""
      ).on(
          'mailAddress -> newUser.mailAddress,
          'password -> newUser.password,
          'displayName -> newUser.displayName
      ).executeUpdate()
    }
  }
  
  def findByMailAddress(mailAddress: String): Option[User] = {
    DB.withConnection { implicit connection =>
      SQL("""
          select * from recordiet_user
          where mail_address = {mailAddress}"""
      ).on(
          'mailAddress -> mailAddress
      ).as(user.singleOpt)}
  }
}