package com.citadel.sigp.service
import play.api.libs.json._
import com.citadel.project.model.ProfitLossModel
import java.util.Date
import com.citadel.project.model.StocksModel
import com.citadel.project.model.CurrencyModel
import play.cache.Cache
import com.citadel.project.model.ProfitLossModelObj
import com.citadel.project.model.CurrencyModel

object BuySellStockBusinessService  {

  def buyStock(buySell: String, stockname: String, quantity: Int, transcationDate: Date): JsValue = {

    var StockCurrency = ""
    var priceinlocalcurr = 0
    var totalcost = 0.0
    var fxrate = 0.0
    var profitLos = ""

    val stockbuydetails = validateStockpurchase(stockname)
    if (stockbuydetails.size > 0) {

      val json: JsValue = Json.parse("""{"Status" : "fail",  "msg" : "This stock purchased Already,You may proceed for Sell"}""")

      return json

    }
    val StocksModelList = stockPriceAvail(stockname, transcationDate)
    
    if (StocksModelList.isEmpty) {

      val json: JsValue = Json.parse("""{"Status" : "fail",  "msg" : "This stock price not available for this date"}""")

      return json

    }
   
       val stocksModel=StocksModelList.head
       StockCurrency = stocksModel.currency
        if("USD".equalsIgnoreCase(StockCurrency))
          fxrate=1
        else
          fxrate= CurrencyModel.currencyTable.filter(currencymodel=>((currencymodel.currName.equalsIgnoreCase(StockCurrency) && currencymodel.date.equals(transcationDate)))).head.fxr
        
        priceinlocalcurr = stocksModel.price
        totalcost = quantity * priceinlocalcurr * fxrate
  


    val profitLossModel = ProfitLossModel(stockname, buySell, quantity, transcationDate, StockCurrency, priceinlocalcurr, fxrate, totalcost, profitLos, "0.00")

    // Adding transcation to cache
    profitlosstableCacheHandler(profitLossModel)

    Json.toJson(ProfitLossModelObj.profitLossWrites.writes(profitLossModel))
  }

  def sellStock(buySell: String, stockname: String, quantity: Int, transcationDate: Date): JsValue = {

    var StockCurrency = "";
    var priceinlocalcurr = 0;
    var totalcost = 0.0
    var fxrate = 0.0
    var profitLos = ""
      
    val stockbuydetails = validateStockpurchase(stockname)
    
    val StocksModelList = stockPriceAvail(stockname, transcationDate)
    
      if (stockbuydetails.size == 0) {

        val jsonresponse: JsValue = Json.parse("""{"Status" : "fail",  "msg" : "please buy this stock first"}""")

        return jsonresponse

      }
    
    if (StocksModelList.isEmpty) {

      val json: JsValue = Json.parse("""{"Status" : "fail",  "msg" : "This stock price not available for this date"}""")

      return json

    }

     val stocksModel=StocksModelList.head
   
      if (stockname.equalsIgnoreCase(stocksModel.stockName) && transcationDate.equals(stocksModel.date)) {
        StockCurrency = stocksModel.currency
           if("USD".equalsIgnoreCase(StockCurrency))
          fxrate=1
          else
        	  fxrate= CurrencyModel.currencyTable.filter(currencymodel=>((currencymodel.currName.equalsIgnoreCase(StockCurrency) && currencymodel.date.equals(transcationDate)))).head.fxr
        
        priceinlocalcurr = stocksModel.price
        totalcost = quantity * priceinlocalcurr * fxrate
      }
    
   
   
     
      val buytotalCost = stockbuydetails.head.totalCost

      val profitLosval = totalcost - buytotalCost;
      if (profitLosval.>(0.0)) {
        profitLos = profitLosval.+("")
      } else {
        profitLos = profitLosval.+("")
      }

   // }

    val profitLossModel = ProfitLossModel(stockname, buySell, quantity, transcationDate, StockCurrency, priceinlocalcurr, fxrate, totalcost, profitLos, profitLos)

    // Adding transcation to cache
    profitlosstableCacheHandler(profitLossModel)

    Json.toJson(ProfitLossModelObj.profitLossWrites.writes(profitLossModel))

  }

  /*
   * 
   * This methos id used to store profit loss table in cache in cache
   */

  private def profitlosstableCacheHandler(profitLossModel: com.citadel.project.model.ProfitLossModel): Unit = {

    val profitlossmodeltablecache = Cache.get("profitlossmodeltable")

    if (null != profitlossmodeltablecache) {

      val newlist = profitLossModel :: profitlossmodeltablecache.asInstanceOf[List[ProfitLossModel]]

      Cache.set("profitlossmodeltable", newlist)

    } else {

      Cache.set("profitlossmodeltable", List(profitLossModel))
     
    }
  }

  /**
   * This function is used to validate whether stock is bought Already
   */

  private def validateStockpurchase(stockname: String): List[ProfitLossModel] = {
    val profitlossmodeltablecache = Cache.get("profitlossmodeltable")
    if (null != profitlossmodeltablecache) {
      val profitlossmodeltable = profitlossmodeltablecache.asInstanceOf[List[ProfitLossModel]]
      return profitlossmodeltable.filter(stockitem => ((stockitem.stock.equalsIgnoreCase(stockname) && stockitem.BuySell.equalsIgnoreCase("Buy"))))
    }
    List[ProfitLossModel]()
  }
  
  private def validateStockPriceAvail(stockname: String): List[ProfitLossModel] = {
    val profitlossmodeltablecache = Cache.get("profitlossmodeltable")
    if (null != profitlossmodeltablecache) {
      val profitlossmodeltable = profitlossmodeltablecache.asInstanceOf[List[ProfitLossModel]]
      return profitlossmodeltable.filter(stockitem => ((stockitem.stock.equalsIgnoreCase(stockname) && stockitem.BuySell.equalsIgnoreCase("Buy"))))
    }
    List[ProfitLossModel]()
  }
  
  private def stockPriceAvail(stockname: String, transcationDate: java.util.Date): List[com.citadel.project.model.StocksModel] = {
    StocksModel.stocksTable.filter(stockmodel=>(stockmodel.stockName.equals(stockname) && stockmodel.date.equals(transcationDate)))
   
  }
  
  

}

