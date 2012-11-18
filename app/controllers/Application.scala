package controllers

import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import play.api._
import models.User

object Application extends Controller {
  
  val loginForm = Form(
    mapping(
      "mailAddress" -> nonEmptyText,
      "password" -> nonEmptyText
    )((mailAddress, password) => User(None, mailAddress, password))
     ((user: User) => Some(user.mailAddress, user.password))
  )
  
  def index = Action {
    Ok(views.html.index(loginForm))
  }
  
  def login = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(errors)),
      user => {
        Redirect(routes.WeightLogs.index)
      }
    )
  }
  
}