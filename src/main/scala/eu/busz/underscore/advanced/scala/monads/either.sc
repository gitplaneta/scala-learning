object either1 {
  import cats.syntax.either._

  val a = 1.asRight
  val b = 2.asLeft

  def countPositive(nums: List[Int]) =
    nums.foldLeft(Right(0): Either[String, Int]) { (accumulator, num) =>
      if(num > 0) {
        accumulator.map(_ + 1)
      } else {
        Left("Negative. Stopping!")
      }
    }

  -1.asRight[String].ensure("Must be non-negative!")(_ > 0)

  for {
    a <- 1.asRight[String]
    b <- 0.asRight[String]
    c <- if(b == 0) "DIV0".asLeft[Int] else (a / b).asRight[String
      ]
  } yield c * 100
  
}