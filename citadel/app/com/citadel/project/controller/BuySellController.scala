package com.citadel.project.controller

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.mvc.Http.Request
import play.libs.Akka._
import akka.actor.Props
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import com.citadel.sigp.service.BuySellStockService
import play.libs.Akka
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.libs.F
import com.citadel.project.model.StocksModel
import com.citadel.project.model.CurrencyModel
import play.cache.Cache
import play.api.mvc.Results
import play.api.mvc.Action

trait BuySellController {
  
  this: Controller =>

   def index  = Action(parse.json){ implicit request =>
     
   val buySellactor = Akka.system.actorOf(Props[BuySellStockService])
   implicit val timeout = Timeout(30 seconds)
   val futurePIValue: Future[Any] =ask(buySellactor, request.body.as[JsObject])
   
   Async{
    	futurePIValue.map(pi=>Ok(pi.asInstanceOf[JsObject]))
   }
   

  }
   
   def loadstocks = Action {
        val stocks = StocksModel.stocksTable // 
        Ok(convertStocksToJson(stocks))
    }
 
   def convertStocksToJson(stocks: List[StocksModel]): JsValue = Json.toJson(stocks)
   
    def loadFX = Action {
        val currencies = CurrencyModel.currencyTable // 
        Ok(convertFXToJson(currencies))
    }
 
   def convertFXToJson(currencies: List[CurrencyModel]): JsValue = Json.toJson(currencies)
   
   def  clearCache()=Action {     
      Cache.set("profitlossmodeltable", List())
      val json: JsValue = Json.parse("""{"status" : "Success"}""")
      Ok(json)
   } 
   
}

object BuySellController extends Controller with BuySellController