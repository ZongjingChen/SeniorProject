MODEL = YBOT;

function testFunc1(a, c) {
    a = 30;
    LArm.raise(a, c);
}

function testFunc2(a, b) {
    a = 20;
    testFunc1(a, 10);
    HEAD.raise(a, b);
}

BEGIN

a = 10;
testFunc2(a, 20);
LHand.rotate(a, 2);

END
//some comment