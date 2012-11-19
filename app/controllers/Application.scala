package controllers

import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import play.api._
import models.User

object Application extends Controller {
  
  val loginForm = Form(
    tuple(
      "mailAddress" -> nonEmptyText,
      "password" -> nonEmptyText
    ) verifying("ユーザ名かパスワードが違います", fields => fields match{
      case (mailAddress, password) => User.authenticate(mailAddress, password).isDefined
    })
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