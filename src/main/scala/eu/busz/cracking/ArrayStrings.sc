//1
def uniqueString(str: String): Boolean = {
  var chars = Set[Char]()
  var notUnique: Boolean = false
  for (c <- str) {
    notUnique |= chars(c)
    chars += c
  }
  !notUnique
}

def uniqueStringVector(str: String): Boolean = {
  var chars = 0
  var notUnique: Boolean = false
  for (c <- str) {
    notUnique |= (chars & (1 << c)) > 0
    chars |= (1 << c)
  }
  !notUnique
}

uniqueString("uniqe")
uniqueString("aba")

uniqueStringVector("abe")
//2
"asbss".reverse

//3
//sort?
//count chars in map/array[256]?
def isPerm(a: String, b: String): Boolean = {
  if (a.length != b.length) return false

  a.sortWith(_.compareTo(_) < 0).equals(b.sortWith(_.compareTo(_) < 0))
}

isPerm("kayak", "kayak")
isPerm("radek", "kedar")
isPerm("blabla", "blablb")