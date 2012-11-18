package functional

import org.specs2.mutable.Specification
import play.api.test._
import play.api.test.Helpers._
import models.User

class ApplicationControllerTest extends Specification {
  "Applicationコントローラ" should {
    "indexアクションでログインフォームを表示する" in {
      val Some(result) = routeAndCall(FakeRequest(GET, "/"))
      
      status(result) must equalTo(OK)
      contentType(result) must beSome("text/html")
      charset(result) must beSome("utf-8")
      contentAsString(result) must contain("Recordiet")
      contentAsString(result) must contain("ログイン")
    }
    
    "認証が通るユーザでloginアクションを実行するとweightLogが表示される" in {
      val testUser = User(None, "hoge@mail.com", "pass1234")
      val Some(result) = routeAndCall(
          FakeRequest(POST, "/login").withFormUrlEncodedBody(
              ("mailAddress", testUser.mailAddress),
              ("password", testUser.password)))

      status(result) must equalTo(SEE_OTHER)
      redirectLocation(result).get must equalTo("/weightLogs")
    }
    
    "認証が通らないユーザでloginアクションを実行するとindexが再表示される" in {
      val invalidUser = User(None, "fuga@mail.com", "pass9876")
      val Some(result) = routeAndCall(
          FakeRequest(POST, "/login").withFormUrlEncodedBody(
              ("mailAddress", invalidUser.mailAddress),
              ("password", invalidUser.password)))
              
      status(result) must equalTo(BAD_REQUEST)
    }
  }
}