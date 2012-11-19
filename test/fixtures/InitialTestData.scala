package fixtures

import models.User

object InitialTestData {
  def insert {
    User.create(User(None, "hoge@mail.com", "pass1234", "ほげ"))
  }
}