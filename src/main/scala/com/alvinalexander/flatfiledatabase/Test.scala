package com.alvinalexander.flatfiledatabase

object Test extends App {

    val dataStore = new DataStore("/Users/Al/RssReader.data")

    // ADD RECORDS LIKE THIS
//    dataStore.add("alvinalexander.com| http://alvinalexander.com/rss.xml")
//    dataStore.add("One Man's Alaska  | http://onemansalaska.com/rss.xml")
//    dataStore.add("Cubs Insider | http://www.cubsinsider.com/feed/")
    val scala = "Scala | http://www.scala-lang.org/feed/index.xml"
    dataStore.add(scala)
    
    println("\nHANDLE THE PIPES YOURSELF")
    val items = dataStore.getAllItems()
    for (i <- items) {
        val fields = i.split("\\|").map(_.trim)
        println(s"name: ${fields(0)}, url: ${fields(1)}")
    }

    println("\nLET THE DATASTORE SPLIT THE PIPES INTO COLUMNS")
    val rows = dataStore.getAllItemsSeparatedIntoColumns()  //Seq[Array[String]]
    for (row <- rows) {
        println(s"name: ${row(0)}, url: ${row(1)}")
    }

    // REMOVE RECORDS LIKE THIS
    dataStore.remove(scala)

    println("\nLET THE DATASTORE SPLIT THE PIPES INTO COLUMNS (Version 2)")
    // notice the use of Array here to access the columns
    val records = dataStore.getAllItemsSeparatedIntoColumns()
    for (Seq(name, url) <- records) {
        println(s"name: $name, url: $url")
    }

}





