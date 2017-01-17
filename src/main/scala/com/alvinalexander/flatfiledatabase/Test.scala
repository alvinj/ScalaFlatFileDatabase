package com.alvinalexander.flatfiledatabase

object Test extends App {

    val dataStore = new DataStore("/Users/Al/.ddstockbrowser")

//    dataStore.add("alvinalexander.com| http://alvinalexander.com/rss.xml")
//    dataStore.add("One Man's Alaska  | http://onemansalaska.com/rss.xml")
//    dataStore.add("Cubs Insider | http://www.cubsinsider.com/feed/")
//    dataStore.add("foo")
//    dataStore.add("bar")
//    dataStore.add("Scala | http://www.scala-lang.org/feed/index.xml")

    println("\nHANDLE THE PIPES YOURSELF")
    val items = dataStore.getAllItems()
    for (i <- items) {
        val fields = i.split("\\|").map(_.trim)
        println(s"name: ${fields(0)}, url: ${fields(1)}")
    }

    println("\nLET THE DATASTORE SPLIT THE PIPES INTO COLUMNS")
    val rows = dataStore.getAllRowsSeparatedIntoColumns()  //Seq[Array[String]]
    for (row <- rows) {
        println(s"name: ${row(0)}, url: ${row(1)}")
    }

    println("\nLET THE DATASTORE SPLIT THE PIPES INTO COLUMNS (Version 2)")
    val records = dataStore.getAllRowsSeparatedIntoColumns()
    for (Array(name, url) <- records) {
        println(s"name: $name, url: $url")
    }

}
