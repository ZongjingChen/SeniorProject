MODEL = YBOT;

function testFunc1(a, c) {
    a = 30;
    sim{
        LArm.raise(a, c);
        RArm.raise(20, a);
        BODY.rotate(10, 5);
    }
}

function testFunc2(a, b) {
    a = 20;
    testFunc1(a, 10);
    HEAD.raise(a, b);
}

BEGIN

testFunc1(10, 10);
HEAD.raise(2, 5);

END
//some comment