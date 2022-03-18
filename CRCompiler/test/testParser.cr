MODEL = XBOT;

function testFunc1(a, b) {
    // Some comment
    a = 10 + 20;
    sim{
        a = 10 + b * 20 + (20 * 3);
        seq {
            LArm.rotate(a, (10 + 20 * q) * 5 / 3 - 4);
        }
    }
}

function testFunc2(a, b) {
    HEAD.testFunc();
}

BEGIN
testFunc(10, 20);
seq {
    LArm.rotate(10, 20);
    RLeg.rise(1,a);
};
LArm.rotate();
LFoot.rise();
END
//some comment

