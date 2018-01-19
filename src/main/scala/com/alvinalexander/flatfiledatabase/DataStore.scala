package com.alvinalexander.flatfiledatabase

import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import java.io.{BufferedWriter, File, FileWriter}

import scala.collection.immutable

/**
 * This class lets you store strings in a flat-file database,
 * where I use the word "database" very loosely.
 * 
 * If you store your strings as pipe-delimited records, you can get
 * your records back using either `getAllItems` or `getAllItemsSeparatedIntoColumns`.
 * 
 * `add` and `remove` methods are provided. There is no `edit` or
 * `update` method because an “update” is just a `remove` followed
 * by an `add`.
 *
 * If there are newline characters in your string, they will stay as newline
 * characters in the ArrayBuffer in this class. Those "\n" characters will
 * be written to the file as a "«", but that’s only for the file representation;
 * they will be kept as newline characters in the ArrayBuffer. This is important
 * for the ‘delete’ process.
 * 
 */
class DataStore(val dataFile: String, val delimiter: String = "|") {

    private val items = new ArrayBuffer[String]()
    private val NEWLINE_SYMBOL = "«"

    // initialize `items` at startup
    getAllItems()

    /**
      * if a record had newline characters when it was saved, those
      * newlines characters will be in these lines as well.
      */
    def getAllItems(): Seq[String] = {
        if ((new File(dataFile).exists())) {
            val lines: Seq[String] = getLinesFromFileWithNewlineCharactersRestored(dataFile)
            items.clear
            items.appendAll(lines)
            items
        } else {
            Nil
        }
    }

    private def convertRareCharacterToNewline(s: String): String = s.replaceAll(NEWLINE_SYMBOL, "\n")
    private def convertNewlineToRareCharacter(s: String): String = s.replaceAll("\n", NEWLINE_SYMBOL)

    /**
     * returns all of the items, with each row separated into columns
     * by the column/field delimiter. the default delimiter is a 
     * `|` character.
     */
    def getAllItemsSeparatedIntoColumns(): Seq[Seq[String]] = {
        // note: can't use a plain "|" with `split`
        val fieldSeparator = if (delimiter == "|") "\\|" else delimiter 
        val allItems: Seq[String] = getAllItems
        val itemsPipeSeparated: Seq[Seq[String]] = for {
            i <- allItems
            fields = i.split(fieldSeparator).map(_.trim).toSeq
        } yield fields
        itemsPipeSeparated
    }

    def add(item: String) {
        items += item
        saveItems()
    }

    /**
     * the given `item` must exactly match the item in our list.
     * `-=` is used in this method, and it deletes ONLY the first matching
     * element in the ArrayBuffer.
     */
    def remove(item: String) {
        items -= item
        saveItems()
    }

//    //TODO need to update/fix this method
//    def removeAll(item: String): Unit = {
//        val newItems = items.filter(_ != item)
//        items.clear
//        items.appendAll(newItems)
//    }

    /**
      * "\n" characters in each line are converted to "«". if i don't do
      * something like that, this approach fails when any field has a newline
      * character.
      */
    private def saveItems() {
        val append = false
        val bw = new BufferedWriter(new FileWriter(new File(dataFile), append))
        //for (item <- items.sorted) bw.write(s"$item\n")
        for (item <- items) {
            val modifiedItem = convertNewlineToRareCharacter(item)
            bw.write(s"$modifiedItem\n")
        }
        bw.close()
    }

    /*
     * "«" is converted to "\n" in this method
     */
    private def getLinesFromFileWithNewlineCharactersRestored(file: String): Seq[String] = {
        val bufferedSource = Source.fromFile(file)
        val records: Iterator[String] = for {
            line <- bufferedSource.getLines
        } yield convertRareCharacterToNewline(line)
        val recordsAsList = records.toList
        bufferedSource.close
        recordsAsList
    }

}


