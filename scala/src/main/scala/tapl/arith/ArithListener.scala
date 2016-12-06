package tapl.arith

import better.files.File
import org.antlr.v4.runtime.tree.{ ErrorNode, TerminalNode }
import org.antlr.v4.runtime.{ CommonTokenStream, ParserRuleContext }
import tapl.arith.Error.InvalidParser
import tapl.arith.Info.FI
import tapl.arith.Syntax._
import tapl.parsers.ARITHListener
import tapl.parsers.ARITHParser.{ BlockContext, GTermContext, TermContext }

class ArithListener(f: File, tokens: CommonTokenStream) extends ARITHListener {

  val astTerms = collection.mutable.ListBuffer.empty[Term]

  import collection.JavaConverters._

  def generate(ctx: TermContext): Term = {
    val tmTrue = ctx.TRUE()
    val tmFalse = ctx.FALSE()
    val tmIf = ctx.IF()
    val tmThen = ctx.THEN()
    val tmElse = ctx.ELSE()
    val tmZero = ctx.ZERO()
    val tmSucc = ctx.SUCC()
    val tmPred = ctx.PRED()
    val tmIsZero = ctx.ISZERO()
    //    val (ft, lt) = (ctx.getStart, ctx.getStop)
    val ft = ctx.getStart
    val fi = FI(f, ft.getLine, ft.getCharPositionInLine)
    val terms = ctx.gTerm().asScala.map(gt ⇒ generate(gt.term()))
    if (tmTrue != null) {
      TmTrue(fi)
    } else if (tmFalse != null) {
      TmFalse(fi)
    } else if (tmIf != null && tmThen != null && tmElse != null) {
      require(terms.size == 3)
      val (tC, tT, tE) = (terms.head, terms(1), terms(2))
      TmIf(fi, tC, tT, tE)
    } else if (tmZero != null) {
      TmZero(fi)
    } else if (tmSucc != null) {
      require(terms.size == 1)
      TmSucc(fi, terms.head)
    } else if (tmPred != null) {
      require(terms.size == 1)
      TmPred(fi, terms.head)
    } else if (tmIsZero != null) {
      require(terms.size == 1)
      TmIsZero(fi, terms.head)
    } else {
      throw InvalidParser(ctx.toString)
    }
  }

  override def enterBlock(ctx: BlockContext): Unit = {
    ctx.term().asScala.foreach(c ⇒ astTerms += generate(c))
  }

  override def exitBlock(ctx: BlockContext): Unit = {}

  override def enterTerm(ctx: TermContext): Unit = {}

  override def exitTerm(ctx: TermContext): Unit = {}

  override def visitTerminal(node: TerminalNode): Unit = {}

  override def visitErrorNode(node: ErrorNode): Unit = {}

  override def exitEveryRule(ctx: ParserRuleContext): Unit = {}

  override def enterEveryRule(ctx: ParserRuleContext): Unit = {}

  override def enterGTerm(ctx: GTermContext): Unit = {}

  override def exitGTerm(ctx: GTermContext): Unit = {}
}