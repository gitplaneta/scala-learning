import scala.collection.mutable

class BankAccount(initialBalance: Double) {
  protected var balance = initialBalance
  def currentBalance = balance
  def deposit(amount: Double) = {
    balance += amount
    balance
  }
  def withdraw(amount: Double) = {
    balance -= amount
    balance
  }
}

class CheckingAccount(initialBalance: Double) extends BankAccount(initialBalance) {

  override def deposit(amount: Double): Double = {
    chargeComission()
    super.deposit(amount)
  }
  override def withdraw(amount: Double): Double = {
    chargeComission()
    super.withdraw(amount)
  }

  def chargeComission() = balance -= 1
}

class SavingsAccount(initialBalance: Double, interest: Double) extends BankAccount(initialBalance) {

  private var operations = 0

  override def deposit(amount: Double) = {
    operations += 1
    super.deposit(amount)
  }

  override def withdraw(amount: Double) = {
    operations += 1
    super.withdraw(amount)
  }

  def earnMonthlyInterest() = {
    if (operations >= 3) {
      balance *= (1 + interest)
    }
    operations = 0
  }
}

abstract class Item {
  def description: String
  def price: Double
}

class SimpleItem(val description: String, val price: Double) extends Item

class Bundle extends Item {

  val items = mutable.ArrayBuffer[Item]()
  override def description: String = {
    items.map(_.description).mkString(" ,")
  }

  override def price: Double = {
    items.foldLeft(0.0)(_ + _.price)
  }

  def addItem(item: Item) = {

  }

}

//ex 5
class Point(val x: Double, val y: Double)
class LabeledPoint(label: String, x: Double, y: Double) extends Point(x, y)

//ex 6
abstract class Shape {
  def centerPoint(): Point
}

class Rectangle(height: Int, length: Int) extends Shape {

  def centerPoint: Point = {
    new Point(height / 2, length / 2)
  }
}

class Circle(radius: Int, override val centerPoint: Point) extends Shape{
}

//7
class Shape2(x: Int, y: Int, width: Int) extends java.awt.Rectangle {

  def this() = {
    this(0, 0, 0)
  }

  def this(width: Int) {
    this(0,0, width)
  }
}