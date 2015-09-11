package com.citadel.project.model

import java.util.Date
import org.joda.time.DateTimeUtils
import com.citadel.project.util.CitadelDateUtil
import play.api.libs.json.Json
import play.api.libs.json._


case class StocksModel (
  
  stockName: String, 
  currency: String,
  price: Int,
  date:Date

)

object StocksModel{
  
  val stocksTable:List[StocksModel]=List(
   StocksModel("ABC","USD",100,CitadelDateUtil.convertStringToDate("2015/01/23")),
   StocksModel("ABC","USD",500,CitadelDateUtil.convertStringToDate("2015/01/24")),
   StocksModel("SGX","SGD",100,CitadelDateUtil.convertStringToDate("2015/01/23")),
   StocksModel("SGX","SGD",500,CitadelDateUtil.convertStringToDate("2015/01/24")),
   
   StocksModel("BNP","GBP",200,CitadelDateUtil.convertStringToDate("2015/01/25")),
   StocksModel("BNP","GBP",500,CitadelDateUtil.convertStringToDate("2015/01/26")),
   
    StocksModel("Citi","EUR",200,CitadelDateUtil.convertStringToDate("2015/01/27")),
   StocksModel("Citi","EUR",500,CitadelDateUtil.convertStringToDate("2015/01/28")) 
   
  )
  
  
  implicit object StocksModelFormat extends Format[StocksModel] {
 
        // convert from StocksModel object to JSON (serializing to JSON)
        def writes(stock: StocksModel): JsValue = {
             val stockSeq = Seq(
                "stock" -> JsString(stock.stockName),
                "currency" -> JsString(stock.currency),
                 "price" -> JsNumber(stock.price),
                "date" -> JsString(CitadelDateUtil.convertDateToString(stock.date))
            )
            JsObject(stockSeq)
        }
 
        // convert from JSON string to a stock object (de-serializing from JSON)
        // (i don't need this method; just here to satisfy the api)
        def reads(json: JsValue): JsResult[StocksModel] = {
            JsSuccess(StocksModel("", "", 0,new Date()))
        }
 
    }
   
}