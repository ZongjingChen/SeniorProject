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
    FUNC, // "function"
    SIM, // "sim"
    SEQ, // "seq"
    LA, // "LeftArm"
    RA, // "RightArm"
    LFA, // "LeftForeArm"
    RFA, // "RightForeArm"
    LH, // "LeftHand"
    RH, // "RightHand"
    LL, // "LeftLeg"
    RL, // "RightLeg"
    LUL, // "LeftUpLeg"
    RUL, // "RightUpLeg"
    LF, // "LeftFoot"
    RF, // "RightFoot"
    HEAD, // "Head"
    BODY, // " Body"
    EQ, // "equals"
    // the rest are reserved words whose lexeme matches their name
    BEGIN, MODEL, XBOT, YBOT, END, TIME;

    public static final Map<String, TokenType> reserved;


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
    }
}
