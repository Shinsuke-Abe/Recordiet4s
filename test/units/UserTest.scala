package units

import org.specs2.mutable.Specification
import play.api.test._
import play.api.test.Helpers._
import models.User
import fixtures.InitialTestData

class UserTest extends Specification {
  
  "ユーザモデル" should {
    "メールアドレスとパスワードと表示名を指定してユーザを登録できる" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        User.create(User(None, "hoge@mail.com", "pass1234", "ほげ"))
        User.findByMailAddress("hoge@mail.com") match {
          case Some(User(_, mailAddress, password, displayName)) => {
            mailAddress must equalTo("hoge@mail.com")
            password must equalTo("pass1234")
            displayName must equalTo("ほげ")
          }
          case None => failure("user not found")
        }
      }
    }
    
    "登録済ユーザのメールアドレスとパスワードで認証できる" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        InitialTestData.insert
        User.authenticate("hoge@mail.com", "pass1234").isDefined must beTrue
      }
    }
    
    "未登録ユーザのメールアドレスとパスワードでは認証できない" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        InitialTestData.insert
        User.authenticate("fuga@mail.com", "pass9876").isDefined must beFalse
      }
    }
  }
}