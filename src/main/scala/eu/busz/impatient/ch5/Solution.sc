import scala.beans.BeanProperty

// 3, 4

class Time(private val _hours: Int, private val _minutes: Int) {

  private val minutesAfterMidnight: Int = _minutes + _hours * 60

  def hours = {
    minutesAfterMidnight / 60
  }

  def minutes(): Int = {
    minutesAfterMidnight - this.hours
  }

  def before(other: Time): Boolean = {
    hours < other.hours || hours == other.hours && minutes < other.minutes
  }
}

new Time(9,0).hours


// 5
class Student( @BeanProperty var name: String,  @BeanProperty var id: Long) {

}

new Student("adsf", 12).getName


// 7
class Person(name: String) {
  private val splittedName = name.split(" ")
  val firstName = splittedName(0)
  val lastName = splittedName(1)
}

class Car(val manufacturer: String, val modelName: String,
          year: Int = -1, var plate: String = "") {
  def this(manufacturer: String, model: String) {
    this()
  }
}

class Employee {
  private var _name: String = ""
  var _salary: Double = 0

  def this(name: String, salary: Double) {
    this()
    this._name = name
    this._salary = salary
  }

  def name = _name
}