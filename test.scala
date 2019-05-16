// To run: `scalac test.scala && scala DS`
import java.io._ 
// using hashmap
import scala.collection.mutable._
// case classes for matching
abstract class JPattern
case class JArray(a: List[JPattern]) extends JPattern
case class JBoolean(b: Boolean) extends JPattern 
case class JFloat(f: Float) extends JPattern 
case class JString(s: String) extends JPattern 
case class JNull(n: Null) extends JPattern 
case class JField(j: HashMap[String, JPattern]) extends JPattern


class Deserialize {
  val z = ":,"
  var comma: String = ","
  val symbols: Array[Char] = z.toArray
  val brackets = Map("}" -> "{", "]" -> "[")
  val openBrace: Char = "{".charAt(0)
  val openBracket: Char = "[".charAt(0)
  val colon: Char = ":".charAt(0)
  val quote: Char = '\"'

  // Determines what type of Pattern and returns String/Null/Float
  // and returns a HashMap or Array if 
  def returnsTheCorrectJPattern(str: String, s: Integer, end: Integer): JPattern = {
    var i: Integer = 0
    val len: Integer = str.length()
    var compare1: Boolean = str(i) != openBrace
    var compare2: Boolean = str(i) != openBracket
    // Value is not a {} or []
    if (compare1 && compare2) {

      val bool = isNumeric(str)
      if (bool) {
        return JFloat(str.toFloat)
      }
      if (str.slice(0,4) == "null") {
        return JNull(null)
      }
      return JString(str)

    } else { // if HashMap/Array
      var re: JPattern = str(i) match {
        case `openBrace` => JField(returnHashMap(str, i, len))
        case `openBracket` => JArray(returnArray(str, i, len))
      }
      return re
    }
    JNull(null) // default return
  }

// Finds the last index of the string based on where the comma is.
// Wil skip commas if count > 0 which only increases when iterating
// over open brackets/braces and decreases when iterating over closing
// brackets/braces
  def lastIndexOfValue(str: String, start: Integer): Integer = {
    var count = 0
    var i = start
    while (i < str.length()) {
      var each: String = str(i).toString()
      // if not last field but simple
      if (each == comma && count == 0) {
        return i
      }
      // if has brackets or braces
      if (brackets.valuesIterator.exists(_.contains(each))) {
        count = count + 1
      } else if (brackets.keySet.exists(_ == each)){
        if (count > 0) {
          count = count - 1
        } else {          
          return i+1
        }
      }
      i = i+1
    }
    i//returns int to prevent compile error
  }
  // Uses mutable HashMap to construct a Map by recursively
  // putting the appropriate JPattern
  def returnHashMap(str: String, s: Integer, e: Integer): HashMap[String, JPattern] = {
      var hash = HashMap[String, JPattern]()
      var i = s
      while (i < e) {
        var indexOfColon: Integer = str.indexOf(colon, i)
        var key = str.slice(i+2, indexOfColon-1)
        var valueLastIndex: Integer = lastIndexOfValue(str, indexOfColon+1)
        var valueSubStr: String = str.slice(indexOfColon+1, valueLastIndex)
        hash.put(key, returnsTheCorrectJPattern(valueSubStr, 0, valueSubStr.length()))
        i = valueLastIndex
      }
      return hash
  }
  // Uses mutable ListBuffer to construct a list by recursively
  // appending the appropriate JPattern
  def returnArray(str: String, s: Integer, e: Integer): List[JPattern] = {
      var array = ListBuffer[JPattern]()
      var i = s
      while (i < e) {
        var valueLastIndex: Integer = lastIndexOfValue(str, i+1)
        var valueSubStr: String = str.slice(i+1, valueLastIndex)
        array += returnsTheCorrectJPattern(valueSubStr, 0, valueSubStr.length())
        i = valueLastIndex
      }
      return array.toList
  }
  // checks if string is a float or not
  def isNumeric(input: String): Boolean = input.forall(_.isDigit)

} // end Split


object DS {
  // retrieves json from file and removes whitespaces
  val jsonTxt = scala.io.Source.fromFile("easy.json").mkString
  val cleanJson = jsonTxt.replaceAll("\\s+","")
  var ds = new Deserialize()

  def main(args: Array[String]): Unit = {
    var result = ds.returnsTheCorrectJPattern(cleanJson, 0, cleanJson.length)
    println(result)
  }

}