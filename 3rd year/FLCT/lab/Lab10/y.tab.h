/* A Bison parser, made by GNU Bison 2.3.  */

/* Skeleton interface for Bison's Yacc-like parsers in C

   Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2, or (at your option)
   any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor,
   Boston, MA 02110-1301, USA.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     AND = 258,
     START = 259,
     FINISH = 260,
     VAR = 261,
     ELSE = 262,
     EXECUTE = 263,
     WHILE = 264,
     IF = 265,
     THEN = 266,
     INT = 267,
     CHAR = 268,
     READ = 269,
     PRINT = 270,
     STRING = 271,
     EXIT = 272,
     ID = 273,
     CONST = 274,
     ATTRIB = 275,
     EQ = 276,
     NE = 277,
     LTE = 278,
     GTE = 279,
     LT = 280,
     GT = 281,
     NOT = 282,
     PLUS = 283,
     MINUS = 284,
     DIV = 285,
     MUL = 286,
     OPEN_CURLY_BRACKET = 287,
     CLOSED_CURLY_BRACKET = 288,
     OPEN_ROUND_BRACKET = 289,
     CLOSED_ROUND_BRACKET = 290,
     OPEN_RIGHT_BRACKET = 291,
     CLOSED_RIGHT_BRACKET = 292,
     COMMA = 293,
     SEMICOLON = 294,
     COLON = 295,
     SPACE = 296
   };
#endif
/* Tokens.  */
#define AND 258
#define START 259
#define FINISH 260
#define VAR 261
#define ELSE 262
#define EXECUTE 263
#define WHILE 264
#define IF 265
#define THEN 266
#define INT 267
#define CHAR 268
#define READ 269
#define PRINT 270
#define STRING 271
#define EXIT 272
#define ID 273
#define CONST 274
#define ATTRIB 275
#define EQ 276
#define NE 277
#define LTE 278
#define GTE 279
#define LT 280
#define GT 281
#define NOT 282
#define PLUS 283
#define MINUS 284
#define DIV 285
#define MUL 286
#define OPEN_CURLY_BRACKET 287
#define CLOSED_CURLY_BRACKET 288
#define OPEN_ROUND_BRACKET 289
#define CLOSED_ROUND_BRACKET 290
#define OPEN_RIGHT_BRACKET 291
#define CLOSED_RIGHT_BRACKET 292
#define COMMA 293
#define SEMICOLON 294
#define COLON 295
#define SPACE 296




#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
# define YYSTYPE_IS_TRIVIAL 1
#endif

extern YYSTYPE yylval;

