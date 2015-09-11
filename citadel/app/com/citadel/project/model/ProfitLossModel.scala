package com.citadel.project.model

import java.util.Date
import play.api.libs.json.Writes
import play.api.libs.json.Json
import com.citadel.project.util.CitadelDateUtil

case class ProfitLossModel (
		stock:String,
		BuySell:String,
		Quantity:Int,
		Date:Date,
		currency:String,
		priceLocalCurrency:Double,
	    fxRate:Double,
		totalCost:Double,
		profitLossUsd:String,
		CumprofitLossUsd:String
		
	
)



object ProfitLossModelObj{

  
   implicit val profitLossWrites = new Writes[ProfitLossModel] {
  def writes(model: ProfitLossModel) = Json.obj(
    "Stock" -> model.stock,
    "buysell" -> model.BuySell,
    "Quantity" ->model.Quantity,
    "Date" ->CitadelDateUtil.convertDateToString(model.Date),
    "Currency" ->model.currency,
    "Price Local Currency" ->model.priceLocalCurrency,
    "Fx Rate" ->model.fxRate,
    "Total Cost / Proceeds in USD" ->model.totalCost,
    "Profit/Loss in USD" ->model.profitLossUsd,
    "Cumulative Profit / Loss USD" ->model.CumprofitLossUsd
  )
}
  
  
//   profitlossTable.::(ProfitLossModel("ABC","Buy",100,new Date("01/02/2015"),"USD",10,12,1000,1000.50,1000.50))
//  profitlossTable.::(ProfitLossModel("ABC","Sell",100,new Date("01/02/2015"),"USD",10,12,1000,1000.50,1000.50))
//  profitlossTable.::(ProfitLossModel("BCD","Buy",100,new Date("01/02/2015"),"USD",10,12,1000,1000.50,1000.50))
//  profitlossTable.::(ProfitLossModel("BCD","Sell",100,new Date("01/02/2015"),"USD",10,12,1000,1000.50,1000.50))    
  
 
 
  
}

