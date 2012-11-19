package integration

import org.specs2.mutable.Specification
import play.api.test._
import play.api.test.Helpers._
import fixtures.InitialTestData

class UserFlowTest extends Specification {
  
  "ユーザログインフォーム" should {
    "履歴なし：ログインすると履歴ページに未登録メッセージが表示される" in {
      running(TestServer(3333), HTMLUNIT) {browser =>
        InitialTestData.insert
        browser.goTo("http://localhost:3333")
        browser.$("input").text("hoge@mail.com", "pass1234")
        browser.submit("#login")
        
        browser.url must equalTo("http://localhost:3333/weightLogs")
        //browser.$("#information").getTexts().get(0) must equalTo("履歴が未登録です。")
      }
    }
  }
}