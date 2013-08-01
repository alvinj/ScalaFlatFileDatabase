# A Scala Flat File Database

From time to time I write a "one off" application, where I don't want to use a real database, 
I just want to store some text data in one or more flat files. For instance, in my current 
"Stock Browser" application, I wanted to write code like this:

    val stocksDb = new DataStore(stocksDataFile)
    val stocks = stocksDb.getAllItems()
    stocksDb.addItem(symbol)
    stocksDb.removeItem(symbol)
    
In short, the "DataStore" is all that this code provides. It lets you do exactly what those four 
lines of code show:

1. Specify a file to use for the DataStore.
1. Get all items from the datastore.
1. Add an item to the datastore.
1. Remove an item from the datastore.

## How it works

The items are stored as lines in a text file, i.e., a "flat file". The datastore doesn't care what 
those lines are. My assumption is that they are lines of text that may be separated by a pipe delimiter, 
comma, or nothing at all.

## Future stuff

I may add the ability to specify a field delimiter so the DataStore can return "fields" from each record.
I may add that capability, but I don't need it now, and the implementation in Scala would also be very simple, 
something like this:

    var fieldSeparator = "|"
    def fields(row: String) = row.split(fieldSeparator).map(_.trim)

so that method could be used like this:

    val Array(month, revenue, expenses, profit) = dataStore.fields(currentRow)

## More information

Written by Alvin Alexander.  
http://alvinalexander.com
