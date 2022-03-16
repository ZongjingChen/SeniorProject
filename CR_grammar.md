# Context-free Grammar for CR

## Lexical Rules

```
letter -> [A-Za-z]
digit -> [0-9]
ident -> letter (letter | digit)*  // except for the reserved words, listed below

number -> digit* (.digit*)
```

Whitespace between tokens may be zero or more spaces, tabs, newlines, carriage returns, or comments.
A comment is any sequence of characters begins with '//'.

### Reserved Words

```
MODEL, XBOT, YBOT, BEGIN, END, TIME
if, else, elif, while, for, and, or, mod, not,
function, sim, seq

LeftArm, RightArm, LeftForeArm, RightForeArm, LeftHand, RightHand
LeftLeg, RightLeg, LeftUpperLeg, RightUpperLeg, LeftFoot, RightFoot,
Head, Body

```

## Syntax Rules
```
Program -> DeclSequence BEGIN StatementSequence END

DeclSequence -> ModelDecl ; FunctionDeclSequence

ModelDecl -> MODEL = RobotType
RobotType -> XBOT | YBOT

FunctionDeclSequence -> FunctionDecl FunctionDeclSequence
FunctionDeclSequence ->

FunctionDecl -> FunctionSignature FunctionBody

FunctionSignature -> function ident ( IdentList )

IdentList -> ident IdentListRest
IdentList -> 

IdentListRest -> , ident IdentListRest
IdentListRest -> 

FunctionBody -> { StatementSequence } 

StatementSequence -> Statement StatementSequenceRest
StatementSequenceRest -> ; Statement StatementSequenceRest
StatementSequenceRest -> 

Statement -> Assignment | FunctionCall | NumDecl
             SimBlock | SeqBlock
[comment]: <> (IfStatement | WhileStatement | ForStatement |)
Statement -> 

Assignment -> ident = Expression

FunctionCall -> BodyPart . BuiltInFunctionCall
FunctionCall -> ident ActualParameters

BuiltInFunctionCall -> BuiltInFunctionName ActualParameters
BuiltInFunctionName -> raise | lateralRaise | rotate 

BodyPart -> LeftArm | RightArm | LeftForeArm | RightForeArm | LeftHand | RightHand |
            LeftLeg | RightLeg | LeftUpperLeg | RightUpperLeg | LeftFoot | RightFoot |
            Head | Body | MODEL

ActualParameters -> ( ExpList )
ActualParameters -> ( )

ExpList -> Expression ExpListRest

ExpListRest -> , Expression
ExpListRest

NumDecl -> num ident
NumDecl -> num ident = Expression

SimBlock -> sim { StatementSequence }
SeqBlock -> seq { StatementSequence }

Expression -> + Term ExprRest
Expression -> - Term ExprRest
Expression -> Term ExprRest

ExprRest -> AddOperator Term ExprRest 
ExprRest -> 

AddOperator -> + | -

Term -> Factor TermRest

TermRest -> MulOperator Factor TermRest
TermRest ->

MulOperator -> * | / | mod

Factor -> number
Factor -> ident 
Factor -> ( Expression )

```





