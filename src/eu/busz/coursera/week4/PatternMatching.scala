package week4

trait Expr
case class Number(n: Int) extends Expr
case class Sum(e1: Exp, e2: Exp) extends Expr

object exprs {
    def show(e: Expr): String {
        e match {
            case Number(n) => n.toString
            case Sum(e1, e2) => show(e1) + " + " show(e2)
        }
    }
}