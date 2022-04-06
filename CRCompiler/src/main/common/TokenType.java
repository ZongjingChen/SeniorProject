package main.common;


import java.util.HashMap;
import java.util.Map;

public enum TokenType {
    ID, // identifier, such as a variable name
    NUM, // numeric literal
    LPAR, // left parenthesis "("
    RPAR, // right parenthesis ")"
    LCB, // left curly braces "{"
    RCB, // right curly braces "}"
    ASSIGN, // equals sign "="
    LT, // less than "<"
    LE, // less than or equal "<="
    GT, // greater than ">"
    GE, // greater than or equal ">="
    PLUS, // plus operator "+"
    MINUS, // minus operator "-"
    TIMES, // times operator "*"
    DIVIDE, // divide operator "/"
    SEMI, // semicolon ";"
    COMMA, // comma ","
    PERIOD, // period "."
    // the rest are reserved words whose lexeme matches their name
    BEGIN, MODEL, XBOT, YBOT, END, TIME, sim, mod,
    seq,
    LArm,
    RArm,
    LForeArm,
    RForeArm,
    LHand,
    RHand,
    LLeg,
    RLeg,
    LUpLeg,
    RUpLeg,
    LFoot,
    RFoot,
    Head,
    Body,
    EQ,
    function;

    public static final Map<String, TokenType> reserved;

    public static final TokenType[] bodyParts = new TokenType[]{
            LArm,
            RArm,
            LForeArm,
            RForeArm,
            LHand,
            RHand,
            LLeg,
            RLeg,
            LUpLeg,
            RUpLeg,
            LFoot,
            RFoot,
            Head,
            Body
    };

    private static void addReserved(TokenType type) {
        reserved.put(type.toString(), type);
    }

    static {
        reserved = new HashMap<>();
        addReserved(BEGIN);
        addReserved(MODEL);
        addReserved(XBOT);
        addReserved(YBOT);
        addReserved(END);
        addReserved(TIME);
        addReserved(sim);
        addReserved(seq);
        addReserved(LArm);
        addReserved(RArm);
        addReserved(LForeArm);
        addReserved(RForeArm);
        addReserved(LHand);
        addReserved(RHand);
        addReserved(LLeg);
        addReserved(RLeg);
        addReserved(LUpLeg);
        addReserved(RUpLeg);
        addReserved(LFoot);
        addReserved(RFoot);
        addReserved(Head);
        addReserved(Body);
        addReserved(EQ);
        addReserved(function);
        addReserved(mod);
    }
}
