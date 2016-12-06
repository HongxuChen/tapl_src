package tapl.arith

object Info {

  import better.files._

  sealed trait Info

  case class FI(fn: File, r: Int, c: Int) extends Info {
    override def toString: String = s"FI(${fn.name}, ${r}, ${c})"
  }

  case object DummyInfo extends Info

  val dummyInfo = DummyInfo

}

object Error {

  case class InvalidParser(msg: String) extends Exception(msg)

  case class NoRuleApplies(msg: String) extends Exception(msg)

}
