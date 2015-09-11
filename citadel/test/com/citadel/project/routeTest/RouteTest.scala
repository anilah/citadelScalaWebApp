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
import play.api.test.FakeApplication

@RunWith(classOf[JUnitRunner])
object RouteTest extends PlaySpecification with Results {

   "Playframework Route Test for /clearCache " should {
    "return Json contain Success" in {
         running(FakeApplication()) {
      val Some(result) = route(FakeRequest(POST,"/clearCache"))
         val bodyText: JsValue = contentAsJson(result)
         val status= Json.stringify(bodyText.\("status"))
         status must contain("Success")
      
       }
    
    }
  }
   
    "Playframework Route Test for /loadFX " should {
    "Must return json Response" in {
        running(FakeApplication()) {
        val Some(result) = route(FakeRequest(POST,"/loadFX"))
        contentType(result) mustEqual Some("application/json")
      
       }
    
    }
  }
    
    
   "Playframework Route Test for /loadstocks " should {
    "Must return json Response" in {
        running(FakeApplication()) {
        val Some(result) = route(FakeRequest(POST,"/loadstocks"))
        contentType(result) mustEqual Some("application/json")
      
       }
    
    }
  }  
    
   
    
   "Playframework Route Test for /buysellstocks " should {
    "Must return json Response" in {
        running(FakeApplication()) {
        val Some(result) = route(FakeRequest(POST,"/buysellstocks"))
        contentType(result) mustEqual Some("text/html")
      
       }
    
    }
  }  
     
  
  
  
  
}