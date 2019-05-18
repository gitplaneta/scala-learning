object Numeric {
  def apply[T](implicit numeric: Numeric[T]): Numeric[T] = numeric
  object ops {
    implicit class NumericOps[T](t: T)(implicit N: Numeric[T]) {
      def +(o: T): T = N.plus(t, o)
      def *(o: T): T = N.times(t, o)
      def unary_-: = N.negate(t)
      def abs: T = N.abs(t)
      def <(o: T) = N.lt(t, o)
      def >(o: T) = N.gt(t, o)
    }
  }
}

object Playground {
  import Numeric.ops._

  def signOfTheTimes[T: Numeric](t: T): T = -(t.abs) * t
}