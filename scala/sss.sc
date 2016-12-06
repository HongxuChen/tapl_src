import fastparse.all._

val ab = P("a" ~ "b")

ab.parse("ab")

val az = P(CharIn('a' to 'z'))

val cp = P(CharPred(_.isUpper).rep.! ~ "." ~ End)

cp.parse("ABC.")

