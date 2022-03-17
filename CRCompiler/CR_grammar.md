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
Program -> ModelDecl ; FunctionDeclSequence BEGIN StatementSequence END

ModelDecl -> MODEL = RobotType
RobotType -> XBOT | YBOT

FunctionDeclSequence -> FunctionDecl FunctionDeclSequence
FunctionDeclSequence ->

FunctionDecl -> function ident ( IdentList ) { StatementSequence } 

IdentList -> ident IdentListRest
IdentList -> 

IdentListRest -> , ident IdentListRest
IdentListRest ->  

StatementSequence -> Statement StatementSequenceRest
StatementSequenceRest -> ; Statement StatementSequenceRest
StatementSequenceRest -> 

Statement -> Assignment | FunctionCall | PrimitiveFunctionCall |
             SimBlock | SeqBlock
[comment]: <> (IfStatement | WhileStatement | ForStatement |)
Statement -> 

Assignment -> ident = Expression

FunctionCall -> ident ActualParameters

PrimitiveFunctionCall -> BodyPart . BuiltInFunctionName ActualParameters
PrimitiveFunctionName -> raise | lateralRaise | rotate 

BodyPart -> LArm | RArm | LForeArm | RForeArm | LHand | RHand |
            LLeg | RLeg | LUpLeg | RUpLeg | LFoot | RFoot |
            HEAD | BODY | MODEL

ActualParameters -> ( ExpList )
ActualParameters -> ( )

ExpList -> Expression ExpListRest

ExpListRest -> , Expression
ExpListRest

NumDecl -> ident = Expression

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






