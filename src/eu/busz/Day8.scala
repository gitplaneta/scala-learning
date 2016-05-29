package eu.busz

import java.util.Scanner

object Day8 {

  def main(args: Array[String]): Unit = {
    val sc = new Scanner(System.in)
    val n = sc.nextInt()
    val phoneBook = scala.collection.mutable.HashMap.empty[String, Int]

    for (_ <- 1 to n) {
      phoneBook(sc.next()) = sc.nextInt()
    }

    while(sc.hasNext) {
      val query = sc.next()
      if (phoneBook.isDefinedAt(query)) println(s"${query}=${phoneBook(query)}") else println("Not found")
    }
  }

  def fact(n: Int): Int = if (n == 1) 1 else n * fact(n-1)

}
