package tapl.arith

import com.typesafe.scalalogging.LazyLogging

object Syntax {

  import Error._
  import Info._

  sealed trait Term extends LazyLogging {

    val info: Info

    def isNumericVal: Boolean = this match {
      case TmZero(_) ⇒ true
      case TmSucc(_, tm) ⇒ tm.isNumericVal
      case _ ⇒ false
    }

    def isVal: Boolean = this match {
      case TmTrue(_) | TmFalse(_) ⇒ true
      case tm if tm.isNumericVal ⇒ true
      case _ ⇒ false
    }

    private def cnt(acc: Int, tm: Term): String = tm match {
      case TmZero(_) ⇒ s"${acc}"
      case TmSucc(_, tt) ⇒ cnt(acc + 1, tt)
      case _ ⇒ s"(${acc}, ${tm})"
    }

    def repr: String = this match {
      case TmTrue(_) ⇒ "true"
      case TmFalse(_) ⇒ "false"
      case TmZero(_) ⇒ "0"
      case TmSucc(_, tm) ⇒ cnt(1, tm)
      case _ ⇒ throw NoRuleApplies(s"${this.getClass}")
    }

    def eval: Term = try eval1.eval catch {
      case NoRuleApplies(msg) ⇒ {
        logger.debug(s"no rules: ${msg}")
        this
      }
    }

    private def eval1: Term = this match {
      // E-IFTRUE
      case TmIf(_, TmTrue(_), ifTerm, _) ⇒ ifTerm
      // E-IFFALSE
      case TmIf(_, TmFalse(_), _, elseTerm) ⇒ elseTerm
      // E-IF
      case TmIf(fi, cond, ifTerm, elseTerm) ⇒ TmIf(fi, cond.eval1, ifTerm, elseTerm)
      // E-SUCC
      case TmSucc(fi, tm) ⇒ TmSucc(fi, tm.eval1) // still on the way
      // E-PREDZERO
      case TmPred(_, TmZero(_)) ⇒ TmZero(dummyInfo)
      // E-PREDSUCC
      case TmPred(_, TmSucc(_, nv)) if nv.isNumericVal ⇒ nv
      // E-PRED
      case TmPred(fi, tm) ⇒ TmPred(fi, tm.eval1) // still on the way
      // E-ISZEROZERO
      case TmIsZero(_, TmZero(_)) ⇒ TmTrue(dummyInfo)
      // E-ISZEROSUCC
      case TmIsZero(_, TmSucc(_, _)) ⇒ TmFalse(dummyInfo)
      // E-ISZERO
      case TmIsZero(fi, tm) ⇒ TmIsZero(fi, tm.eval1)
      // exceptions
      case _ ⇒ throw NoRuleApplies(s"${this}")
    }
  }

  case class TmTrue(info: Info) extends Term

  case class TmFalse(info: Info) extends Term

  case class TmIf(info: Info, cond: Term, ifTm: Term, elseTm: Term) extends Term

  case class TmZero(info: Info) extends Term

  case class TmSucc(info: Info, tm: Term) extends Term

  case class TmPred(info: Info, tm: Term) extends Term

  case class TmIsZero(info: Info, tm: Term) extends Term

}
