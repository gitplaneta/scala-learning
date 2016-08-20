def product(f: Int => Int)(a: Int, b: Int): Int = {
  if (a > b) 1
  else f(a) * product(f)(a+1, b)
}

def fact(n: Int) = {
  product(x => x)(1, n)
}

def gen(map: Int => Int)(a: Int, b: Int)(combine: (Int, Int) => Int, unitValue: Int): Int = {
  if (a > b) unitValue
  else combine(map(a), gen(map)(a+1, b)(combine, unitValue))
}

product(a => a*a)(1,3)
fact(3)