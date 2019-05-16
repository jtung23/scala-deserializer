
 // def splitString(str: String) { 
  //   // mutable implementation of List
  //   var li = ListBuffer[String]()
    
  //   var newString = new StringBuilder()
  //   var i: Integer = 1
  //   val len: Integer = str.length()

  //   while ( i < len ){
  //     var each: String = str(i).toString()
  //     // if has a : , then add to list and go to next
  //     if (symbols contains each) {
  //       // appends to list
  //       li += newString.toString()
  //       newString = new StringBuilder()

  //       i = i+1
  //     } 
  //     // checks if [ or {, returns index of closing symbol, get substring
  //     else if (brackets.valuesIterator.exists(_.contains(each))) {
  //       // from current index to end
  //       val sliced: String = str.slice(i, len)
  //       // returns closing index
  //       val closer: Integer = findClosingIndexofBrac(sliced)
  //       //w takes substr from opening bracket to closing bracket (including)
  //       val substr: String = sliced.slice(0, closer)
        
  //     }
  //     // if bracket then run through find closing index function
  //     newString.append(str(i))
  //     i = i+1
  //   }
  // } 