import java.awt.Point
import java.awt.geom.Ellipse2D
import java.beans.{PropertyChangeListener, PropertyChangeSupport}

//1
trait RectangleLike {
  this: Ellipse2D =>
  def translate(dx: Int, dy: Int)  {
    setFrame(getX + dx, getY + dy, getWidth, getHeight)
  }

  def grow(h: Int, w: Int) = {
    setFrame(getX, getY, getWidth + w, getHeight + h)
  }
}

val egg = new java.awt.geom.Ellipse2D.Double(5,10,20,30) with RectangleLike
egg.translate(10, 10)
egg.grow(20, 10)

//2

class OrderPoint extends java.awt.Point with scala.math.Ordered[Point] {
  override def compare(that: Point): Int =
    if ((x < that.x) || (y < that.y && y < that.y)) -1 else 0
}

//4
trait CryptoLogger extends Logger {
  override def log(msg: String) = {
    val ceasar: String = (for (ch <- msg) yield (ch + 13).toChar).mkString
    super.log(ceasar)
  }
}
trait Logger {
  def log(msg: String) = {}
}
trait ConsoleLogger extends Logger {
  override def log(msg: String) = {
    Console.println(msg)
  }
}

class Something extends Logger with ConsoleLogger with CryptoLogger {
  def x() = {
    log("hello")
  }
}

new Something() .x()

//5
trait PropertyChangeSupport {

  private val support = new java.beans.PropertyChangeSupport(this)

  def addPropertyChangeListener(listener: PropertyChangeListener) = {
    support.addPropertyChangeListener(listener)
  }

  def addPropertyChangeListener(propertyName :String, listener: PropertyChangeListener): Unit = {
    support.addPropertyChangeListener(propertyName, listener)
  }

  def fireIndexedPropertyChange(propertyName: String, index: Int, oldValue: Boolean, newValue: Boolean): Unit = {
    support.fireIndexedPropertyChange(propertyName, index, oldValue, newValue)
  }
}

val p = new Point() with PropertyChangeSupport

//6
trait Engine {

}

trait Chasis {

}

class Car extends Engine with Chasis{

}


// layered traits, concrete and abstract methods, concrete and abstract fields























