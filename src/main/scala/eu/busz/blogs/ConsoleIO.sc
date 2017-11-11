//http://degoes.net/articles/easy-monads

sealed trait Sequential[F[_], A] {
  def map[B](f: A => B): Sequential[F, B] = Map[F, A, B](this, f)

  def flatMap[B](f: A => Sequential[F, B]): Sequential[F, B] = Chain[F, A, B](this, f)
}
final case class Effect[F[_], A](fa: F[A]) extends Sequential[F, A]
final case class Pure[F[_], A](value: A) extends Sequential[F, A]
final case class Chain[F[_], A0, A](v: Sequential[F, A0], f: A0 => Sequential[F, A]) extends Sequential[F, A]
final case class Map[F[_], A0, A](v: Sequential[F, A0], f: A0 => A) extends Sequential[F, A]

sealed trait ConsoleF[A]
final case class WriteLine(line: String) extends ConsoleF[Unit]
final case class ReadLine() extends ConsoleF[String]

type ConsoleIO[A] = Sequential[ConsoleF, A]


val x = Pure(10)
x

WriteLine("What is your name?").effect