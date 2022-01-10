%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define YYDEBUG 1

int yylex();
yyerror(char *s);

%}

%token RESERVED
%token CONST
%token ID
%token OPERATOR
%token SEPARATOR

%start compound_statement

%%
compound_statement : statement SEPARATOR stmtTemp
    ;
stmtTemp : /*Empty*/  | compound_statement
    ;
declaration_statement : RESERVED ID | RESERVED ID OPERATOR expression
    ;
statement : declaration_statement | assignment_statement | io_statement | if_statement | while_statement
    ;
io_statement : read_statement | write_statement
    ;
assignment_statement : ID OPERATOR expression
    ;
read_statement : ID OPERATOR RESERVED SEPARATOR SEPARATOR
    ;
write_statement : RESERVED SEPARATOR expression SEPARATOR
    ;
expression : term | term OPERATOR expression
    ;
term : CONST | ID | SEPARATOR expression SEPARATOR
    ;
if_statement : RESERVED SEPARATOR condition SEPARATOR SEPARATOR compound_statement SEPARATOR
    ;
while_statement : WHILE SEPARATOR condition SEPARATOR SEPARATOR compound_statement SEPARATOR
    ;
condition : expression OPERATOR expression
    ;

%%

yyerror(char *s)
{
	printf("%s\n",s);
}

extern FILE *yyin;

int main(int argc, char **argv)
{
	if(argc>1) yyin = fopen(argv[1],"r");
    //yydebug = 1;
	if(!yyparse()) fprintf(stderr, "\tO.K.\n");
}