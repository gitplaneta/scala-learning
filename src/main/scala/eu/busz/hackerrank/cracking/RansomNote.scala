package scala.eu.busz.hackerrank.cracking

object RansomNote extends App {

  val sc = new java.util.Scanner (System.in);
  var m = sc.nextInt();
  var n = sc.nextInt();
  var magazine = new Array[String](m);
  var magWordMap = Map[String, Int]().withDefault(_ => 0)
  for(magazine_i <- 0 to m-1) {
    val word = sc.next()
    magazine(magazine_i) = word;
    magWordMap = magWordMap.updated(word, magWordMap(word) + 1)
  }
  var ransom = new Array[String](n);
  var result = 1
  for(ransom_i <- 0 to n-1) {
    val word = sc.next()
    ransom(ransom_i) = word
    if (magWordMap(word) - 1 < 0) {
      result = -1
    }
    magWordMap = magWordMap.updated(word, magWordMap(word) - 1)
  }
  println(result)

}

object BalancedBrackets extends App {


}
