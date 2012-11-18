package units

import org.specs2.mutable.Specification
import play.api.test._
import play.api.test.Helpers._
import models.User

class UserTest extends Specification {
  Fixtures.load("data.yml")
  
  "ユーザモデル" should {
    "登録済ユーザのメールアドレスとパスワードで認証できる" in {
      User.authenticate("hoge@mail.com", "pass1234").isDefined must beTrue
    }
  }
}