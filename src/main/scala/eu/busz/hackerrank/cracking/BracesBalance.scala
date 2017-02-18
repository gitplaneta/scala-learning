package scala.eu.busz.hackerrank.cracking

object BracesBalance extends App {

  val sc = new java.util.Scanner (System.in);
  var t = sc.nextInt();
  var a0 = 0;
  while(a0 < t){
    var expression = sc.next();
    println(if (isBalanced(expression)) "YES" else "NO")
    a0+=1;
  }

  def isBalanced(expression: String): Boolean = {
    var stack = List[Char]()

    for (c <- expression) {
      if (c == '(' || c == '[' || c == '{') stack = c :: stack
      else if (!stack.isEmpty && c == invertParenthesis(stack.head)) {
        stack = stack.tail
      } else return false
    }
    stack.isEmpty
  }

  def invertParenthesis(c: Char): Char = c match {
    case ')' => '('
    case ']' => '['
    case '}' => '{'
    case '(' => ')'
    case '[' => ']'
    case '{' => '}'
    case _ => throw new IllegalArgumentException
  }

}
