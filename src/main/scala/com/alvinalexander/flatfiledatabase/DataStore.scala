package com.alvinalexander.flatfiledatabase

import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import java.io.{ File, BufferedWriter, FileWriter }

/**
 * I assume the user wants to store strings in a flat-file database
 * (where I use the word "database" very loosely.)
 * 
 * If you store things as pipe-delimited records, you can use
 * record.split("|") to get the fields back.
 * 
 */
class DataStore(val dataFile: String, val delimiter: String = "|") {

    private val items = new ArrayBuffer[String]()

    // initialize `items` at startup
    getAllItems()

    //TODO return an Option?
    def getAllItems(): List[String] = {
        if ((new File(dataFile).exists())) {
            val linesFromFile = getLinesFromFile(dataFile)
            //println("getAllItems: linesFromFile size: " + linesFromFile.size)
            items.clear
            items.appendAll(linesFromFile)
            //println("getAllItems: items size: " + items.size)
            items.toList
        } else {
            Nil
        }
    }
    
    /**
     * returns all of the items, with each row separated into columns
     * by the column/field delimiter. the default delimiter is a 
     * `|` character.
     */
    def getAllRowsSeparatedIntoColumns(): Seq[Array[String]] = {
        // can't use a plain "|" with `split`
        val fieldSeparator = if (delimiter == "|") "\\|" else delimiter 
        val allItems = getAllItems
        val itemsPipeSeparated = for {
            i <- allItems
            fields = i.split(fieldSeparator).map(_.trim)
        } yield fields
        itemsPipeSeparated
    }

    def add(item: String) {
        items += item
        saveItems()
    }

    /**
     * this must exactly match the item that was stored
     */
    def remove(item: String) {
        items -= item
        saveItems()
    }

    private def saveItems() {
        val append = false
        val bw = new BufferedWriter(new FileWriter(new File(dataFile), append))
        //for (item <- items.sorted) bw.write(s"$item\n")
        for (item <- items) bw.write(s"$item\n")
        bw.close()
    }

    private def getLinesFromFile(file: String): List[String] = {
        val bufferedSource = Source.fromFile(file)
        val records = (for (line <- bufferedSource.getLines) yield line).toList
        bufferedSource.close
        records
    }

}


