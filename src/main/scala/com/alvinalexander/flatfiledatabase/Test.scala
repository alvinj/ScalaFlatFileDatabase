package com.alvinalexander.flatfiledatabase

object Test extends App {
  
  val dataStore = new DataStore("/Users/Al/.ddstockbrowser")
  val stocks = dataStore.getAllItems()
  stocks.foreach(println)

}