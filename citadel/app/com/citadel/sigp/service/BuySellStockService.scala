package com.citadel.sigp.service
import akka.actor.UntypedActor
import play.api.libs.json._
import akka.util.Timeout
import akka.util.Timeout
import com.citadel.project.model.ProfitLossModel
import java.util.Date
import com.citadel.project.model.StocksModel
import com.citadel.project.model.CurrencyModel
import play.cache.Cache
import com.citadel.project.model.ProfitLossModelObj
import com.citadel.project.model.CurrencyModel
import com.citadel.sigp.service.BuySellStockBusinessService


/*
 * Akka based Service
 */
class BuySellStockService extends UntypedActor {

  def onReceive(obj: Any) = {
    if (obj.isInstanceOf[JsObject]) {

      var jsonobj = obj.asInstanceOf[JsObject]

      val buySell = jsonobj.\("Buy/Sell").as[String]
      val stockname = jsonobj.\("Stock").as[String]
      val quantity = jsonobj.\("Quantity").as[Int]
      val transcationDate = jsonobj.\("Date").as[Date]
   
      var jsonresponse=Json.parse("""{"Status" : "fail",  "msg" : "Transaction type should be Buy or Sell "}""")
    

      if ("Buy".equalsIgnoreCase(buySell.toString)) jsonresponse= BuySellStockBusinessService.buyStock(buySell, stockname, quantity, transcationDate)


      if ("Sell".equalsIgnoreCase(buySell.toString)) jsonresponse= BuySellStockBusinessService.sellStock(buySell, stockname, quantity, transcationDate)
     
      sender.tell(jsonresponse, getSelf())

    }

  }

}

