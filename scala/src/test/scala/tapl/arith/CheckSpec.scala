package tapl.arith

import org.scalatest.{FlatSpec, Matchers}
import tapl.arith.Info.DummyInfo

class CheckSpec extends FlatSpec with Matchers {

  import Syntax._

  val di = DummyInfo

  "eval" should "pass" in {
    val t1 = TmIf(di, TmFalse(di), TmSucc(di, TmZero(di)), TmIsZero(di, TmZero(di)))
    t1.eval should be(TmTrue(di))
    t1.eval.toString should be("true")
    val t2 = TmIf(di, TmTrue(di), TmSucc(di, TmZero(di)), TmIsZero(di, TmZero(di)))
    t2.eval should be(TmSucc(di, TmZero(di)))
    t2.eval.toString should be("1")
  }

}
