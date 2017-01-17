A Scala Flat File Database
==========================

From time to time I write a "one off" application, where I don't want to use a real database, 
I just want to store some text data in one or more flat files. For instance, in my current 
"Stock Browser" application, I wanted to write code like this:

    val stocksDataFile = "/Users/Al/stocks.dat"
    val stocksDataStore = new DataStore(stocksDataFile)
    val stocks = stocksDataStore.getAllItems()
    stocksDataStore.add(symbol)
    stocksDataStore.remove(symbol)
    
In short, the "DataStore" is all that this code provides. It lets you do exactly what those four 
lines of code show:

1. Specify a file to use for the DataStore.
1. Get all items from the datastore.
1. Add an item to the datastore.
1. Remove an item from the datastore.


Usage
-----

To see examples of how the code works, see the *Test.scala* file.


What happens behind the scenes
------------------------------

The items you add are assumed to be plain text, and they’re stored as lines in a text file, i.e., a “flat file.” 
The datastore doesn’t care what those lines are. I generally store pipe-delimited strings in the file, but you
can store any sort of strings you want.


Future stuff
------------

I added the ability to specify a field delimiter in the `DataStore` constructor, but that
functionality hasn’t been tested. I have tested a default `|` delimiter a little, and it
seems to be working. See the *Test.scala* file for an example
of how I retrieve rows as columns.



More information
----------------

Written by Alvin Alexander.  
http://alvinalexander.com




