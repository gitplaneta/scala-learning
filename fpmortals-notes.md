* If an algebraic interface is tied to a specific type (Future, IO etc.), it's called an interpreter
``` trait DynAgents[F[_]] {
def initial: F[WorldView]
def update(old: WorldView): F[WorldView]
def act(world: WorldView): F[WorldView]
}
```
### 4 Data and functionality

# Polymorphic functions
A typeclass is a trait that:
* holds no state
* has a type parameter
* has at least one abstract method (primitive combinators)
* may contain generalised methods (derived combinators)
* may extend other typeclasses

* Typeclasses similar to algebraic interfaces BUT they *don't need to be coherent*
* Typeclass coherence ??? imports should not impact the behaviour of the code

Typeclass:
- No dependency on OOP class hierarchy. Useful to work with types we can't redefine

Typeclass pattern:
```
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
```

There are some rules of thumb that we will use throughout this book, e.g.
prefer implicit val over implicit object despite the temptation of less typing. It is a quirk
of implicit resolution8 that implicit object on companion objects are not treated the same as
implicit val .

* *Avoid java.net.URL* - toString, equals, hashCode calls network DNS lookup (sic!). It's slow and can result in I/O exceptions! Either use *String Refined Url* or jUrl
* \/ - angry rabbit, disjunction == Either with .flatMap

* Typeclass derivation - composition of other typeclasses?

5. Scalaz Typeclasses
```
@typeclass trait Semigroup[A] {
  @op("|+|") def append(x: A, y: =>A): A
  def multiply1(value: F, n: Int): F = ...
}

@typeclass trait Monoid[A] extends Semigroup[A] {
  def zero: A
  def multiply(value: F, n: Int): F =
  if (n <= 0) zero else multiply1(value, n - 1)
}

@typeclass trait Band[A] extends Semigroup[A]
```

A =Semigroup= can be defined for a type if two values can be combined. The
operation must be /associative/, meaning that the order of nested operations
should not matter, i.e.

```s
(a |+| b) |+| c == a |+| (b |+| c)
(1 |+| 2) |+| 3 == 1 |+| (2 |+| 3)
```

A =Monoid= is a =Semigroup= with a /zero/ element (also called /empty/
or /identity/). Combining =zero= with any other =a= should give =a=.

```s
a |+| zero == a
a |+| 0 == a
```

# Align
Align is about merging and padding anything with a Functor . Before looking at Align , meet the
\&/ data type (spoken as These, or hurray!).

Encodes inclusive logical OR. A or B, or both A and B


# Variance
Covariant Functor == Functor
Contravariant Functor == Covariant

both take xmap:
```s
@typeclass trait InvariantFunctor[F[_]] {
  def xmap[A, B](fa: F[A], f: A => B, g: B => A): F[B]
}
```

Functor ignores `g`, Cotravariant ignores `f`.
```
@typeclass trait Functor[F[_]] extends InvariantFunctor[F] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
  def xmap[A, B](fa: F[A], f: A => B, g: B => A): F[B] = map(fa)(f)
  ...
}
@typeclass trait Contravariant[F[_]] extends InvariantFunctor[F] {
  def contramap[A, B](fa: F[A])(f: B => A): F[B]
  def xmap[A, B](fa: F[A], f: A => B, g: B => A): F[B] = contramap(fa)(g)
  ...
}
```
.map may be understand by its contract “if you give me an F of A and a way to turn an A into a B ,
then I can give you an F of B ”.
Likewise, .contramap reads as “if you give me an F of A and a way to turn a B into a A , then I can
give you an F of B ”.

# Apply and Bind
```
@typeclass trait Apply[F[_]] extends Functor[F] {
@op("<*>") def ap[A, B](fa: =>F[A])(f: =>F[A => B]): F[B]
...
```


> The |@| operator has many names. Some call it the Cartesian Product Syntax

`The syntax <* and *> (left bird and right bird) offer a convenient way to ignore the output from one of two parallel effects.`

> Unfortunately, although the |@| syntax is clear, there is a problem in that a new Applicative-
Builder object is allocated for each additional effect. However, when performing CPU-bound work, use the alternative
lifting with arity syntax, which does not produce any intermediate objects:

```s
def ^[F[_]: Apply,A,B,C](fa: =>F[A],fb: =>F[B])(f: (A,B) =>C): F[C] = ...
def ^^[F[_]: Apply,A,B,C,D](fa: =>F[A],fb: =>F[B],fc: =>F[C])(f: (A,B,C) =>D): F[D] = ...
...
def ^^^^^^[F[_]: Apply, ...]

ex: ^^^^(d.getBacklog, d.getAgents, m.getManaged, m.getAlive, m.getTime)

or directly call applyX
Apply[F].apply5(d.getBacklog, d.getAgents, m.getManaged, m.getAlive, m.getTime)
```

Bind introduces flatMap (bind):

```s
@typeclass trait Bind[F[_]] extends Apply[F] {
  @op(">>=") def bind[A, B](fa: F[A])(f: A => F[B]): F[B]

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B] = bind(fa)(f)

  override def ap[A, B](fa: =>F[A])(f: =>F[A => B]): F[B] =
    bind(f)(x => map(fa)(x))

  override def apply2[A, B, C](fa: =>F[A], fb: =>F[B])(f: (A, B) => C): F[C] =
    bind(fa)(a => map(fb)(b => f(a, b)))

  def join[A](ffa: F[F[A]]): F[A] = bind(ffa)(identity)
  def mproduct[A, B](fa: F[A])(f: A => F[B]): F[(A, B)] = ...
  def ifM[B](value: F[Boolean], t: =>F[B], f: =>F[B]): F[B] = ...
}
```

# Applicative and Monad
> From a functionality point of view, Applicative is Apply with a pure method, and Monad extends
Applicative with Bind .

```@typeclass
trait Applicative[F[_]] extends Apply[F] {
  def point[A](a: =>A): F[A]
  def pure[A](a: =>A): F[A] = point(a)
}
@typeclass trait Monad[F[_]] extends Applicative[F] with Bind[F]
```

Instances of Applicative must meet some laws, effectively asserting that all the methods are
consistent:
• Identity: fa `<*>` pure(identity) === fa , (where fa is an F[A] ) i.e. applying pure(identity) does nothing.
• Homomorphism: pure(a) `<*>` pure(ab) === pure(ab(a)) (where ab is an A => B ), i.e.
applying a pure function to a pure value is the same as applying the function to the value and then using pure on the result.
• Interchange: pure(a) `<*> fab === fab <*> pure(f => f(a))` , (where fab is an F[A =>B] ), i.e. pure is a left and right identity
• Mappy: `map(fa)(f) === fa <*> pure(f)`

# Natuarl transformation (F ∼> G)
Equivalent of Function[A,B] but for `F[_] => G[_]`
Scalaz provides similar syntax sugar F ∼> G for functions over type
constructors `F[_] to G[_]`.

# Isomorphism (A <=> B)
Conversions between same tings that have different types. Defines a formal "is equivalent to" between two types.

```s
object Isomorphism {
trait Iso[Arr[_, _], A, B] {
  def to: Arr[A, B]
  def from: Arr[B, A]
}
type IsoSet[A, B] = Iso[Function1, A, B]
type <=>[A, B] = IsoSet[A, B]
```

```s
val listIListIso: List <~> IList = new IsoFunctorTemplate[List, IList] {
    def to[A](fa: List[A]) = fromList(fa)
    def from[A](fa: IList[A]) = fa.toList
}
```

If we had a way to define F <=> G, instead of defining seperate typeclasses for B, we could:
```trait IsomorphismSemigroup[F, G] extends Semigroup[F] {
implicit def G: Semigroup[G]
def iso: F <=> G
def append(f1: F, f2: =>F): F = iso.from(G.append(iso.to(f1), iso.to(f2)))
}
```

# Co-things (CoBind, CoMonad etc.)
A co-thing typically has some opposite type signature to whatever thing does, but is not necessarily
its inverse. To highlight the relationship between thing and co-thing, we will include the type
signature of thing wherever we can.

- Is there a CoFunctor? Yes/no, they are basically the same (map: a => b & contramap: b => a)
- CoBind is


### Advanced monads

#Future
- executes eagerly "confaltes program definition with execution"
- each flatMap causes thread scheduling (it's slow!)
- impossible to know when job stated and finished
- requires execution context everywhere


# `BindRec[_]` and Trampoline
`BindRec[Trampoline]`
- used to make Monad stack-safe. Bind transforms stackcalls into ADTs placed in Heap (trampoline stack into heap)


# ReaderT `A => F[B]`
The reader monad wraps A => F[B] allowing a program F[B] to depend on a runtime value A . For
those familiar with dependency injection, the reader monad is the FP equivalent of Spring or Guice’s @Inject , without the XML and reflection.
ReaderT is just an alias to another more generally useful data type named after the mathematician
Heinrich Kleisli.


# WriterT (MonadTell) `F[(W, A)]`
Opposite of reading, typically for writing into a journal.
```s
final case class WriterT[F[_], W, A](run: F[(W, A)])
object WriterT {
  def put[F[_]: Functor, W, A](value: F[A])(w: W): WriterT[F, W, A] = ...
  def putWith[F[_]: Functor, W, A](value: F[A])(w: A => W): WriterT[F, W, A] = ...
  ...
}
```

W - journal is accumulated here


# StateT `S => F[(S, A)]`, MonadState
Special case of WriterT but you can read the state (log).
Lets us put,get or modify. A functional var.


# Free and FreeAp
Free Monad - good for injecting features between actions, monitoring, batching, reordering...
- a lot of boilerplate
- each action is an ADT
- cool in 2015
- The ADT defines an Abstract Syntax Tree (AST) because each member is representing a computation
in a program.
- the AST can be executed by interpreter which will transform it into IO[Unit]

```s
sealed abstract class Free[S[_], A] {
  def mapSuspension[T[_]](f: S ~> T): Free[T, A] = ...
  def foldMap[M[_]: Monad](f: S ~> M): M[A] = ...
  ...
}
object Free {
  implicit def monad[S[_], A]: Monad[Free[S, A]] = ...

  private final case class Suspend[S[_], A](a: S[A]) extends Free[S, A]
  private final case class Return[S[_], A](a: A)     extends Free[S, A]
  private final case class Gosub[S[_], A0, B](
    a: Free[S, A0],
    f: A0 => Free[S, B]
  ) extends Free[S, B] { type A = A0 }

  def liftF[S[_], A](value: S[A]): Free[S, A] = Suspend(value)
  ...
}

def program[F[_]: Monad](M: Machines[F]): F[Unit] = ...
val interpreter: Machines.Ast ~> IO = ...
val app: IO[Unit] = program[Free[Machines.Ast, ?]](Machines.liftF)
.foldMap(interpreter)
```

• Suspend represents a program that has not yet been interpreted
• Return is .pure
• Gosub is .bind

# Coyoneda (coyo, Free Functor)
- allows generating Functor data structure for any algebra
- simpler Free[] but for functors?
- can use coyoneda on Effect that is not a functor to change the whole ting into a functor (https://typelevel.org/blog/2014/06/22/mapping-sets.html)

```s
sealed abstract class Coyoneda[F[_], A] {
  def run(implicit F: Functor[F]): F[A] = ...
  def trans[G[_]](f: F ~> G): Coyoneda[G, A] = ...
  ...
}
object Coyoneda {
  implicit def functor[F[_], A]: Functor[Coyoneda[F, A]] = ...

  private final case class Map[F[_], A, B](fa: F[A], f: A => B) extends Coyoneda[F, A]
  def apply[F[_], A, B](sa: F[A])(f: A => B) = Map[F, A, B](sa, f)
  def lift[F[_], A](sa: F[A]) = Map[F, A, A](sa, identity)
  ...
}
```


Programs are just data. Free allows to make it explicit. Re-arranging and optimising becomes simpler.


# parallel
* Monad is explicitly forbidden from running effects in parallel.

If the implicit Applicative.Par[IO] is in scope, we can choose between sequential and parallel
traversal:
```val input: IList[String] = ...
def network(in: String): IO[Int] = ...
input.traverse(network): IO[IList[Int]] // one at a time
input.parTraverse(network): IO[IList[Int]] // all in parallel
```

Similarly, we can call .parApply or .parTupled after using scream operators
```val fa: IO[String] = ...
val fb: IO[String] = ...
val fc: IO[String] = ...
(fa |@| fb).parTupled: IO[(String, String)]
(fa |@| fb |@| fc).parApply { case (a, b, c) => a + b + c }: IO[String]
```

### Scalaz IO
* Implemented as a Free Monad
`type Task[A] = IO[Throwable, A]`

# Fibers
```s
def fork[E2]: IO[E2, Fiber[E, A]] = ...
def supervised(error: Throwable): IO[E, A] = ...

trait Fiber[E, A] {
  def join: IO[E, A]
  def interrupt[E2](t: Throwable): IO[E2, Unit]
}

for {
  fiber1   <- analysis(data).fork
  fiber2   <- validate(data).fork
  valid    <- fiber2.join
  _        <- if (!valid) fiber1.interrupt(BadData(data))
              else IO.unit
  result   <- fiber1.join
} yield result
```

# Promise
Represents asynchronous variable that can be set once. Unbounded subscribers can get this var.

# IORef
IO equivalent of an atomic-reference (can read, update, write with eventual consistency, immediate write, coompare and Spark-The-Definitive-Guide)

### Summary of Advanced Monads
- Different Monads don't compose, that's why we use monad transformers
- Future is slow and side-effecting
- Stack-safety can be guaranteed by Trampoline
- Free allows easy analysis, optimisation and testing of our programs
- IO can execute effects in parallel. It implements algebras as side-effects on the world

# Typeclass derivation

### Scala-z derivation
* Contravariant parameter - function parameter
* Covariant parameter - return parameter

* Using Contravariant[Equal] (contramap) to create new type classes based on more primitive ones (ex. converting our ADT to string and piggyback on typeclasses that use String as type)
* Using Covariant[Default] (map) to create typeclass based on

```s
@typeclass trait Equal[A] {
  // type parameter is in contravariant (parameter) position
  @op("===") def equal(a1: A, a2: A): Boolean
}

// for requesting default values of a type when testing
@typeclass trait Default[A] {
  // type parameter is in covariant (return) position
  def default: String \/ A
}

final case class Foo(s: String)

object Foo {
  implicit val equal: Equal[Foo] = Equal[String].contramap(b => b.s) //use implicit Equal[String]
  implicit val default: Default[Foo] = Default[String].map(a => Foo(a)) //use imiplicit Default[String]
}

```

# Shapeless
```s
sealed trait HList
final case class ::[+H, +T <: HList](head: H, tail: T) extends HList
sealed trait NNil extends HList
case object HNil extends HNil {
def ::[H](h: H): H :: HNil = ::(h, this)
}
sealed trait Coproduct
sealed trait :+:[+H, +T <: Coproduct] extends Coproduct
final case class Inl[+H, +T <: Coproduct](head: H) extends :+:[H, T]
final case class Inr[+H, +T <: Coproduct](tail: T) extends :+:[H, T]
sealed trait CNil extends Coproduct // no implementations
```

HList & Coproduct - generic representation of Product and CoProduct

* Clone of the IsoSet datatype for conversion from/to ADT and generic representation:
```s
trait Generic[T] {
type Repr
def to(t: T): Repr
def from(r: Repr): T
}
object Generic {
type Aux[T, R] = Generic[T] { type Repr = R }
def apply[T](implicit G: Generic[T]): Aux[T, G.Repr] = G
implicit def materialize[T, R]: Aux[T, R] = macro ...
}
```

* LabelledGeneric[T] - complementary for the above, includes fields name in types that get ereased at runtime

* Shapeless has problems with recursive types. Use `Cached[Strict[DerivedEqual[R]]]` (or `Lazy[DerivedEqual[R]]`) instead of `DerivedEqual[R]`. Lazy might be slower for some cases then `Cached[Strict]`
