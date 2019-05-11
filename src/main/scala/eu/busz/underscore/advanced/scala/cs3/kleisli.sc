import cats.data.Kleisli
import cats.instances.list._

val k: Kleisli[List, Int, Int] = Kleisli(x => List(x+1, x-1))
val k2: Kleisli[List, Int, Int] = Kleisli(x => List(x * x))

(k andThen k2).run(10)