MODEL = XBOT;

function testFunc1(a, c) {
    // Some comment
    a = 10 + c;
    sim{
        b = 10;
        a = 10 + b * 20 + (20 * 3);
        seq {
            LArm.raise(a, (10 + 20 * 1) * 5 / 3 - 4);
        }
    }
}

function testFunc2(a, b) {
    //testFunc1(a);
}

BEGIN
testFunc1();
seq {
    LArm.rotate(10, 20);
    RLeg.rise(1,a);
};
LArm.rotate();
LFoot.rise();
END
//some comment