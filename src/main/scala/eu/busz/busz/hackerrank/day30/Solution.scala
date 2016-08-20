package main.scala.eu.busz.busz.hackerrank.day30

object Solution {

  def main(args: Array[String]) {
    val pattern = ("[a-z\\.]+@gmail\\.com")
    val sc = new java.util.Scanner (System.in)
    val N = sc.nextInt()
    var list = List[(String, String)]()
    var a0 = 0
    while(a0 < N){
      var firstName = sc.next()
      var emailID = sc.next()
      a0+=1
      list = (firstName, emailID) :: list
    }

    list
      .filter{case (name, email) => email.matches(pattern)}
      .map{case (name, email) => name}
      .sortWith(_ < _)
      .foreach(println(_))
  }

}
