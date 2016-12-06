grammar ARITH;

block : (term SEMICOLON)+;

term : TRUE
     | FALSE
     | IF gTerm THEN gTerm ELSE gTerm
     | ZERO
     | SUCC gTerm
     | PRED gTerm
     | ISZERO gTerm
     ;

gTerm : LP term RP
      | term
      ;

COMMENT
  : '//' ( ~'\n' )*
  -> skip
  ;

TAB
  : ( '\t' )+ -> skip
  ;

SPACE
  : ( ' ' )+ -> skip
  ;

NEWLINE
  : '\n' -> skip
  ;

CR
  : '\r' -> skip
  ;

LP     : '(';
RP     : ')';
TRUE   : T R U E;
FALSE  : F A L S E;
IF     : I F;
THEN   : T H E N;
ELSE   : E L S E;
ZERO   : '0';
SUCC   : S U C C;
PRED   : P R E D;
ISZERO : I S Z E R O;

SEMICOLON  : ';';

fragment A:'a'|'A';
fragment B:'b'|'B';
fragment C:'c'|'C';
fragment D:'d'|'D';
fragment E:'e'|'E';
fragment F:'f'|'F';
fragment G:'g'|'G';
fragment H:'h'|'H';
fragment I:'i'|'I';
fragment J:'j'|'J';
fragment K:'k'|'K';
fragment L:'l'|'L';
fragment M:'m'|'M';
fragment N:'n'|'N';
fragment O:'o'|'O';
fragment P:'p'|'P';
fragment Q:'q'|'Q';
fragment R:'r'|'R';
fragment S:'s'|'S';
fragment T:'t'|'T';
fragment U:'u'|'U';
fragment V:'v'|'V';
fragment W:'w'|'W';
fragment X:'x'|'X';
fragment Y:'y'|'Y';
fragment Z:'z'|'Z';