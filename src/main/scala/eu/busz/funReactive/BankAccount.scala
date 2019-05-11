package eu.busz.funReactive

object BankAccount {

  trait Validation[A, B]

  trait Date

  type V[A] = Validation[String, A]

  trait Acc {
    def validateAccountNo(no: String): V[String]

    def validateOpenCloseDate(openDate: Option[Date], closeDate: Option[Date]):
    V[(Date, Option[Date])]

    def validateRateOfInterest(rate: BigDecimal): V[BigDecimal]

    def apply3[V[_], A, B, C, D](va: V[A], vb: V[B], vc: V[C])(f: (A, B, C) => D): V[D]

    def lift3[V[_], A, B, C, D](f: (A, B, C) => D): (V[A], V[B], V[C]) => V[D] = apply3(_, _, _)(f)
  }

}