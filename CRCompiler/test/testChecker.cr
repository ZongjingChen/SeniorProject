MODEL = XBOT;

function testFunc1(a, c) {
    // Some comment
    a = 10 + b;
    sim{
        b = 10;
        a = 10 + b * 20 + (20 * 3);
    }
}

function testFunc2(a, b) {
    //testFunc1(a);
}

BEGIN
a = 10;
testFunc1(1, 2);
Head.rotate(10, 20);
END
//some comment