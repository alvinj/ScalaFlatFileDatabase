# Database Example

I don’t have time to explain this much right now, but this code shows an example of
how to create a more-functional database access object (DAO) from the built-in DataStore:

```scala
import com.alvinalexander.flatfiledatabase.DataStore
import com.alvinalexander.notes.{Globals, Note, Utils}

class Database {

    val dataStore = new DataStore(Globals.DB_FILE)

    def save(n: Note) = {
        val s = convertNoteToPipedString(n)
        dataStore.add(s)
    }

    def delete(n: Note) = {
        val s = convertNoteToPipedString(n)
        dataStore.remove(s)
    }

    /**
      * note: i do a `trim` on each field
      */
    private def convertNoteToPipedString(n: Note): String = {
        s"${n.getNote.trim}|${n.getUrl.trim}|${n.getTags.trim}|${n.getDatetime}"
    }

    private def createNoteFromDatabaseRec(note: String, url: String, tags: String, date: String): Note = {
        new Note(note, url, tags, date)
    }

    def getAll(): Seq[Note] = {
        val records: Seq[Seq[String]] = dataStore.getAllItemsSeparatedIntoColumns()
        val notes = for {
            Seq(note,url,tags,date) <- records
        } yield createNoteFromDatabaseRec(note,url,tags,date)
        notes
    }

    def getAllFullTextSearch(searchFor: String): Seq[Note] = {
        val records: Seq[Seq[String]] = dataStore.getAllItemsSeparatedIntoColumns()
        val notes = for {
            rec <- records
            if anyFieldContainsString(rec, searchFor)
        } yield createNoteFromDatabaseRec(rec(0),rec(1),rec(2),rec(3))
        notes
    }

    private def anyFieldContainsString(notes: Seq[String], searchFor: String): Boolean = {
        val s = notes.mkString(" ")
        if (s.contains(searchFor)) true else false
    }

    def getAllByTag(searchFor: String): Seq[Note] = {
        val records: Seq[Seq[String]] = dataStore.getAllItemsSeparatedIntoColumns()
        val notes = for {
            rec <- records //Seq[String]
            if rec(2) contains(searchFor)
        } yield createNoteFromDatabaseRec(rec(0),rec(1),rec(2),rec(3))
        notes
    }

}
```

Given an object `db` of type `Database`, you can now write code like this:

```scala
val notes = db.getAll()
db.save(note)
db.delete(note)
```

The search functions work like this:

```scala
val notes = db.getAllByTag(tagToSearchFor)
val notes = db.getAllFullTextSearch(textToSearchFor)
```




