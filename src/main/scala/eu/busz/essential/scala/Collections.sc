List(1,2,4) :+ 5
List(1,2,4) ++ List(5)

5 :: List(1,2,4)
List(1,2,4) ::: List(5)


val x = Some("adsf")
x.fold("empty")(s => s)
x.foldLeft("empty")((a,b) => b)