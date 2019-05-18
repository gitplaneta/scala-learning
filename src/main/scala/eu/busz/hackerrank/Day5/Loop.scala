package main.scala.eu.busz.busz.hackerrank.Day5

object Loop extends App {

  val sc = new java.util.Scanner (System.in);
  var N = sc.nextInt();

  for (ln <- scala.io.Source.stdin.getLines) {
    var first = ""
    var sec = ""

    for (j <- 0 until ln.length)
      if (j % 2 == 0) first += ln(j) else sec += ln(j)

    print(first + " ")
    println(sec)
  }
  sc.close()
}
