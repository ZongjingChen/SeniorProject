MODEL = YBOT;

function testFunc1(a, c) {
    a = 30;
    sim{
        // BodyPart.action(degree, duration);
        LArm.raise(a, c);
        RArm.raise(20, a);
        Body.rotate(10, 5);
    }
}

function testFunc2(a, b) {
    a = 20;
    testFunc1(a, 10);
    Head.raise(a, b);
}

BEGIN

Head.rotate(45, 2);
LArm.lateralRaise(90, 4);

END
//some comment