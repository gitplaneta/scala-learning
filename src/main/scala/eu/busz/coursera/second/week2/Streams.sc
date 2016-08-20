Stream(1,2,3)

val xs = Stream.cons(1, Stream.cons(2, Stream.empty))

val y = (1 to 1000).toStream

1 #:: xs

lazy val x = { println("dupa"); 3}

def from(n: Int): Stream[Int] = Stream.cons(n, from(n+1))

def sieve(s: Stream[Int]): Stream[Int] = {
  s.head #:: sieve(s.tail.filter(_ % s.head != 0))
}

val primes = sieve(from(2))

val xxx = primes.take(100).toList

def sqrtStream(x : Double): Stream[Double] = {
  def improve(guess: Double) = (guess + x / guess) / 2
  lazy val gueses: Stream[Double] = 1 #:: (gueses map improve)
  gueses
}