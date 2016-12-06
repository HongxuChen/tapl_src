package tapl.arith

import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.antlr.v4.runtime.{ ANTLRFileStream, CommonTokenStream }
import tapl.parsers.{ ARITHLexer, ARITHParser }

object Parser {

  import better.files._
  import tapl.utils.Configs._

  def main(args: Array[String]): Unit = {
    val inputFile = folder / "arith.f"
    require(inputFile.exists)
    val lexer = new ARITHLexer(new ANTLRFileStream(inputFile.toString))
    val tokens = new CommonTokenStream(lexer)
    val parser = new ARITHParser(tokens)
    val tree = parser.block()
    val walker = new ParseTreeWalker
    val listener = new ArithListener(inputFile, tokens)
    walker.walk(listener, tree)
    val terms = listener.astTerms.toList
    for (t ← terms) {
      println(t.eval.repr)
    }
  }

}
