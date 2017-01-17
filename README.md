A Scala Flat File Database
==========================

From time to time I write little “one off” applications where I don't want to use a real database, 
I just want to store some plain text data in one or more flat files. For instance, in my current 
“Stock Browser” application I wanted to write code like this:

    val stocksDataFile = "/Users/Al/stocks.dat"
    val stocksDataStore = new DataStore(stocksDataFile)
    val stocks = stocksDataStore.getAllItems()
    
    // later ...
    stocksDataStore.add(symbol)
    stocksDataStore.remove(symbol)
    
In short, the “DataStore” is all that this code provides. It lets you do exactly what those
lines of code show:

1. Specify the name of a file to use for the DataStore.
1. Get all items from the DataStore.
1. Add an item to the DataStore.
1. Remove an item from the DataStore.


Usage
-----

To see up-to-date examples of how the code works, see the *Test.scala* file.

Note: One thing that isn’t demonstrated in *Test.scala* is that you can have multiple
data stores in a single application. Just provide different file and variable names 
for each store, like this:

````
val stocksDataStore = new DataStore("stocks.dat")
val usersDataStore = new DataStore("users.dat")
````


What happens behind the scenes
------------------------------

The items you add are assumed to be plain text, and they’re stored as lines in a text file, i.e., a “flat file.” 
The `DataStore` doesn’t care what those lines are. I generally store pipe-delimited strings in the file, but you
can store any sort of strings you want.

Note: Items are stored in the same order in which they are added.


Field delimiters
----------------

I added the ability to specify a field delimiter in the `DataStore` constructor, but that
functionality hasn’t been tested. I have tested a default `|` delimiter a little, and it
seems to be working. See the *Test.scala* file for an example
of how I retrieve rows as columns.


Future/To-Do
------------

It would be nice if I converted simple POJOs to pipe-delimited strings,
so a user could just save a POJO and then retrieve it, with the `DataStore`
doing all of the work for you. Something like this:

````
case class User (firstName: String, lastName: String)
val al = User("Alvin", "Alexander")
dataStore.addPojo(al)
````

and then:

````
val user = dataStore.getPojo("Alvin")  // some magic happens here
````

I haven’t thought this out much, but I can see where something like
that would be a nice feature.



More information
----------------

Written by Alvin Alexander.  
http://alvinalexander.com




