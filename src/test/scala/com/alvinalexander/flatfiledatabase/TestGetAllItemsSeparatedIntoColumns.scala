package com.alvinalexander.flatfiledatabase

import java.io.File
import java.util.{Calendar, Date}
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter

class TestGetAllItemsSeparatedIntoColumns extends FunSuite with BeforeAndAfter {

    val dir = System.getProperty("user.dir")
    val slash = System.getProperty("file.separator")
    val testDir = s"${dir}${slash}testdata"
    val testDbFile = s"${testDir}${slash}Stocks.dat"

    private val DELIMITER = "‡"
    val dataStore = new DataStore(
        testDbFile,
        delimiter = DELIMITER,
        newlineSymbol = "«"
    )

    before {
        // make sure the database is clean before each test
        dataStore.removeAll
    }

    val aapl = Stock("AAPL", "Apple", 178.46)
    val fb = Stock("FB", "Facebook", 181.29)
    val goog = Stock("GOOG", "Alphabet", 1137.51)

    type DbRow = Seq[String]
    type DbField = String

    test("getAllItemsSeparatedIntoColumns - test adding/getting 1 stock") {
        // add AAPL
        dataStore.add(convertStockToPipedString(aapl))
        // make sure you get AAPL back out
        val stocks: Seq[DbRow] = dataStore.getAllItemsSeparatedIntoColumns
        val stock1: Seq[DbField] = stocks(0)
        val appleStock = createStockFromDatabaseRecord(
            stock1(0), stock1(1), stock1(2)
        )
        assert(aapl == appleStock)
    }

    test("getAllItemsSeparatedIntoColumns - test adding/getting 3 stocks") {
        // add three stocks
        dataStore.add(convertStockToPipedString(aapl))
        dataStore.add(convertStockToPipedString(fb))
        dataStore.add(convertStockToPipedString(goog))
        // make sure you get three back out
        // the inner Seq[String] is the fields that were separated by pipes
        val stocksFromDb: Seq[DbRow] = dataStore.getAllItemsSeparatedIntoColumns
        val res = for {
            stock <- stocksFromDb
        } yield createStockFromDatabaseRecord(stock(0), stock(1), stock(2))

        assert(res.size == 3)
        assert(res.contains(aapl))
        assert(res.contains(fb))
        assert(res.contains(goog))
    }

    private def convertStockToPipedString(
        s: Stock
    ): String = {
        s"${s.symbol.trim}${DELIMITER}" +
        s"${s.name.trim}${DELIMITER}" +
        s"${s.price}${DELIMITER}"
    }

    private def createStockFromDatabaseRecord(
        symbol: String,
        name: String,
        priceString: String
    ): Stock = {
        Stock(
            symbol,
            name,
            priceString.toDouble
        )
    }

}

// note: using dates causes problems, so i removed the date field
case class Stock (
    symbol: String,
    name: String,
    price: Double
)





