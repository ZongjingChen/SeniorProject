MODEL = XBOT;

function testFunc1(a, b) {
    // Some comment
    a = 10 + 20;
    sim{
        a = 10 + c * 20;
        seq {
            LArm.rotate(a, (12 + b) * 5);
        }
    }
}

function testFunc2(a, b) {
    Head.testFunc();
}

BEGIN
testFunc(10, 20);
seq {
    LArm.rotate(10, 20);
    RLeg.rise(1,a);
};
LArm.rotate();
LFoot.rise()
END
//some comment

