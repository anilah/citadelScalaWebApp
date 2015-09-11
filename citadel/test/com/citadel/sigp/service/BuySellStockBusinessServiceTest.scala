package com.citadel.sigp.service
import play.api.libs.json._
import com.citadel.project.model.ProfitLossModel
import java.util.Date
import com.citadel.project.model.StocksModel
import com.citadel.project.model.CurrencyModel
import play.cache.Cache
import com.citadel.project.model.ProfitLossModelObj
import com.citadel.project.model.CurrencyModel
import org.specs2.mutable.Specification
import com.citadel.project.util.CitadelDateUtil
import play.api.test.PlaySpecification
import play.api.test.FakeApplication
import play.api.test.WithApplication
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner


@RunWith(classOf[JUnitRunner])
class BuySellStockBusinessServiceTest extends  PlaySpecification  {
  


  "buy stock" should {
    "return json reponse having total cost 10000.0" in {
     running(FakeApplication()) {
     val result: JsValue = BuySellStockBusinessService.buyStock("Buy", "ABC", 100, CitadelDateUtil.convertStringToDate("2015/01/23"));
     val totalcost= Json.stringify(result.\("Total Cost / Proceeds in USD"))
     totalcost must equalTo("10000.0")
       }
    }
  }
  
  
  "Testing BuySellStockBusinessService" should {

  "Buy Sell stock" in new WithApplication {
    
     val result0: JsValue = BuySellStockBusinessService.sellStock("Sell", "ABC", 100, CitadelDateUtil.convertStringToDate("2015/01/23"))
     val status0= Json.stringify(result0.\("Status"))
     
   //  println(status0+"--------------status0")
    
    
     val result: JsValue = BuySellStockBusinessService.buyStock("Buy", "ABC", 100, CitadelDateUtil.convertStringToDate("2015/01/23"))
     val totalcost= Json.stringify(result.\("Total Cost / Proceeds in USD"))
     
     val result1: JsValue = BuySellStockBusinessService.buyStock("Buy", "ABC", 100, CitadelDateUtil.convertStringToDate("2015/01/23"))
     val status= Json.stringify(result1.\("Status"))
     
     // stock should be purchased before sell so this should be fail
     status0 must contain("fail")
     
     totalcost must equalTo("10000.0")
     
     //duplicate stock purchase is not allowed so it should fail
     status must contain("fail")
  }
  
 
}
  

}

