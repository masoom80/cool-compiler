  /* JFlex example: partial Java language lexer specification */


    /*
     * This class is a simple example lexer.
     */


%%

%class Scanner
%public
%unicode
%function next
%type Token
%line
%column
%implements Lexical

%{
  public Token token;
  public StringBuilder string;
  public String nextToken(){
        token = null;
      try{
          token = next();
          if(token.equals(null)){
              return "$";
          }
          switch(token.type){
              case (0):
                  if(token.token.equals("bool")||
                  token.token.equals("string")||
                  token.token.equals("int")||
                  token.token.equals("real")
                  ){
                      return token.token+"Literal";
                  }
                  return token.token;

              case (1):
                  return "id";
              case (2):
                  if(token.token.contains("x") || token.token.contains("X")){
                      return "Hexadecimal";
                  }else{
                     return "Decimal";
                  }

              case (3):
                   if(token.token.contains("e") || token.token.contains("E")){
                       return "Scientific";
                   }else{
                       return "Realnumber";
                   }

              case (7):
                  if(token.token.equals("\"")){
                      string = new StringBuilder();
                      do{
                          token = next();
                          string.append(token.token);
                      }while (! token.token.equals("\""));

                  return "String";
                  }if(token.token.equals(",")){
                      return "comma";
                  }
                  return token.token;

              default:
                  return null;

          }
      }catch(Exception e){
           return "$";
      }
}
  public class  Token {
          String token;
          int type;
          int line;
          int column;

          //reserved 0
          //Identifiers 1
          //Integer Numbers 2
          //Real Numbers 3
          //Strings 4
          //Special Characters  5
          //Comments  6
          //Operators and Punctuations  7
          //Undefined Token  8

            Token(String token , int type ){
                this.type = type;
                this.token = token;
                this.line = yyline;
                this.column = yycolumn;
            }
  }


%}

    LineTerminator = \r|\n|\r\n
    InputCharacter = [^\r\n]
    WhiteSpace     = {LineTerminator} | [ \t\f]

    /* comments */
    Comment = {TraditionalComment} | {EndOfLineComment}

    TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
    // Comment can be the last line of the file, without line terminator.
    EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?


   
    reservedKeywords = ("let" |    
"static"     |
"while"   |
"new"     |
"void"    |
"class"   |
"break"   |
"func"    |
"int"     |
"for"     |
"continue"|
"return"  |
"real"    |
"len"     |
"if"      |
"inputStr"|
"bool"    |
"loop"    |
"range"   |
"inputInt"|
"string"  |
"print"   |
"else"    |
"in"      |
"true"    |
"false")
 
    
    Identifier = [a-zA-Z]([[a-zA-Z]|[0-9]|_]){0,30}
    DecimalInteger = 0 | [1-9][0-9]*
    Hexadecimal = (0x|0X)(([0-9]|[a-fA-F])+)
    Realnumber = {DecimalInteger}\.{DecimalInteger}?
    ScientificNotation = ({Realnumber}|{DecimalInteger})(E|e)(-{DecimalInteger}|\+{DecimalInteger}|{DecimalInteger})
    
%state STRING
%state STRINGBACK

%%

 
    <YYINITIAL> {
      {reservedKeywords}       {return new Token(yytext(),0);}
      /* identifiers */

      {Identifier}                   { return new Token(yytext(),1); }

      /* literals */
      {ScientificNotation}    { return new  Token(yytext(),3); }
      {Realnumber}            { return  new Token(yytext(),3); }
      {DecimalInteger}            { return new Token(yytext(),2); }
      {Hexadecimal}            { return  new Token(yytext(),2); }

      /* comments */
      {Comment}                      { return  new Token(yytext(),6);}

      /* operators */
      "+"                            { return new  Token("+" ,7); }
      "*"                            { return new  Token("*" ,7); }
      "+="                           { return new  Token("+=",7); }
      "*="                           { return new  Token("*=",7); }
      "++"                           { return new  Token("++",7); }
      "<"                            { return new  Token("<" ,7); }
      ">"                            { return new  Token(">" ,7); }
      "!="                           { return new  Token("!=",7); }
      "="                            { return new  Token("=" ,7); }
      "&&"                           { return new  Token("&&",7); }
      "&"                            { return new  Token("&" ,7); }
      \"                             {  yybegin(STRING);
                                      return new  Token("\""  ,7); }
      "!"                            { return new  Token("!" ,7); }
      ","                            { return new  Token("," ,7); }
      "["                            { return new  Token("[" ,7); }
      "("                            { return new  Token("(" ,7); }
      "{"                            { return new  Token("{" ,7); }
      "-"                            { return new  Token("-" ,7); }
      "/"                            { return  new Token("/" ,7); }
      "-="                           { return new  Token("-=",7); }
      "/="                           { return  new Token("/=",7); }
      "--"                           { return  new Token("--",7); }
      "<="                           { return  new Token("<=",7); }
      ">="                           { return  new Token(">=",7); }
      "=="                           { return  new Token("==",7); }
      "%"                            { return  new Token("%" ,7); }
      "||"                           { return  new Token("||",7); }
      "|"                            { return  new Token("|" ,7); }
      "^"                            { return  new Token("^" ,7); }
      "."                            { return  new Token("." ,7); }
      ";"                            { return  new Token(";" ,7); }
      "]"                            { return  new Token("]" ,7); }
      ")"                            { return  new Token(")" ,7); }
      "}"                            { return  new Token("}" ,7); }



      /* whitespace */
      {WhiteSpace}                   { /* ignore */ }
    }

    <STRING> {
      \"                             { yybegin(YYINITIAL);
                                       return new  Token(yytext(),7); }
      \\                            { yybegin(STRINGBACK); }
      [^\"\\] +                { return new  Token( yytext() ,4); }

    }
    <STRINGBACK>{
    \"                           { yybegin(STRING);return  new Token("\\\"",5); }
    \'                            { yybegin(STRING);return  new Token("\\\'",5); }
    \\                            { yybegin(STRING);return   new Token("\\\\",5);}
    t                            { yybegin(STRING);return  new Token("\\t",5); }
    n                            { yybegin(STRING);return  new Token("\\n",5);  }
    r                           { yybegin(STRING);return  new Token("\\r",5);  }

    [^]{1}                            {yybegin(STRING);return new Token("\\"+yytext(),4);}

    }

    /* error fallback */
    [^]                              { return  new Token(yytext(),8); }