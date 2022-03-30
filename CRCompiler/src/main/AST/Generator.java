package main.AST;

import main.AST.*;

public interface Generator {
    void generate(Program program);

    // Declarations
    void generate(ModelDeclaration modelDeclaration);
    void generate(FunctionDeclaration functionDeclaration);

    // Statements
    void generate(Assignment assignment);
    void generate(FunctionCall functionCall);
    void generate(PrimitiveFunctionCall primitiveFunctionCall);
    void generate(SimBlock simBlock);
    void generate(SeqBlock seqBlock);
    void generate(EmptyStatement emptyStatement);
}
