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
import play.api.test.PlaySpecification
import play.api.test.FakeRequest
import scalaz.Equal
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import play.api.test.FakeApplication

@RunWith(classOf[JUnitRunner])
object BuySellControllerTest extends PlaySpecification with Results {

  class TestController() extends Controller with BuySellController

  "load stock list" should {
    "return json reponse" in {
      val result: Future[SimpleResult] = BuySellController.loadstocks.apply(FakeRequest())
      val bodyText: JsValue = contentAsJson(result)
      val status= bodyText.isInstanceOf[JsValue]
      status must equalTo(true)
    
    }
  }
  
   "load stock list count Test" should {
    "return json reponse" in {
      val result: Future[SimpleResult] = BuySellController.loadstocks.apply(FakeRequest())
      val bodyText: JsValue = contentAsJson(result)
     val count= bodyText.as[JsArray].value.size
     count must equalTo(8)
    
    }
  }
  
  "load Currency list count Test" should {
    "return json reponse" in {
      val result: Future[SimpleResult] = BuySellController.loadFX.apply(FakeRequest())
      val bodyText: JsValue = contentAsJson(result)
      val count= bodyText.as[JsArray].value.size
     count must equalTo(6)
    
    }
  }
  
  
   "SGD FXrate value Test" should {
    "return json reponse" in {
      val result: Future[SimpleResult] = BuySellController.loadFX.apply(FakeRequest())
      val bodyText: JsValue = contentAsJson(result).as[JsArray].value.filter(item=>{item.\("currName").asInstanceOf[JsString].toString.replaceAll("\"", "").equalsIgnoreCase("SGD")}).head
      val status= bodyText.\("fxrate")
      status must equalTo(JsNumber(0.9))
    
    }
  }
  
  
   "Clear Cache of playframework" should {
    "return Json Success" in {
         running(FakeApplication()) {
         val result = BuySellController.clearCache.apply(FakeRequest())
         val bodyText: JsValue = contentAsJson(result)
         val status= Json.stringify(bodyText.\("status"))
         status must contain("Success")
       }
    
    }
  }
}