package com.citadel.project.model

import java.util.Date
import com.citadel.project.util.CitadelDateUtil
import play.api.libs.json.Format
import play.api.libs.json.JsValue
import play.api.libs.json.JsString
import play.api.libs.json.JsNumber
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsSuccess

case class CurrencyModel (
  
  currName:String, 
  fxr: Double,
  date:Date

)

object CurrencyModel{
 val currencyTable:List[CurrencyModel]=List(
//   CurrencyModel("USD",1,CitadelDateUtil.convertStringToDate("2015/01/23")),
//   CurrencyModel("USD",1,CitadelDateUtil.convertStringToDate("2015/01/24")),
   CurrencyModel("SGD",.90,CitadelDateUtil.convertStringToDate("2015/01/23")),
   CurrencyModel("SGD",.80,CitadelDateUtil.convertStringToDate("2015/01/24")), 
   CurrencyModel("GBP",1.66,CitadelDateUtil.convertStringToDate("2015/01/25")),
   CurrencyModel("GBP",1.77,CitadelDateUtil.convertStringToDate("2015/01/26")), 
   CurrencyModel("EUR",1.90,CitadelDateUtil.convertStringToDate("2015/01/27")),
   CurrencyModel("EUR",2.10,CitadelDateUtil.convertStringToDate("2015/01/28")) 
 
  )
  
   implicit object CurrencyModelFormat extends Format[CurrencyModel] {
 
        // convert from StocksModel object to JSON (serializing to JSON)
        def writes(curr: CurrencyModel): JsValue = {
               val stockSeq = Seq(
                "currName" -> JsString(curr.currName),
                "fxrate" -> JsNumber(curr.fxr),
                "date" -> JsString(CitadelDateUtil.convertDateToString(curr.date))
            )
            JsObject(stockSeq)
        }
 
        // convert from JSON string to a Currency object (de-serializing from JSON)
        // (i don't need this method; just here to satisfy the api)
        def reads(json: JsValue): JsResult[CurrencyModel] = {
            JsSuccess(CurrencyModel("", 0,new Date()))
        }
 
    }
  
}