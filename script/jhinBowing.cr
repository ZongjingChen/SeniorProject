MODEL = YBOT;

function raiseArmsForStartingPosition() {
    sim{
        LArm.lateralRaise(150, 0.1);
        RArm.lateralRaise(150, 0.1);
        LForeArm.rotate(-45, 0.1);
        RForeArm.rotate(-45, 0.1);
        LHand.rotate(-30, 0.1);
        RHand.rotate(-30, 0.1);
    };
    LForeArm.raise(30, 0.01);
    RForeArm.raise(30, 0.01);
}

function moveArms(duration) {
    sim{
        LArm.raise(-70, duration);
        LArm.lateralRaise(-30, duration);
        LForeArm.rotate(180, duration);
    };
    sim{
        LForeArm.raise(60,duration / 2);
        LHand.rotate(30, duration);
    }
}

function moveLLeg(duration) {
    sim{
        LUpLeg.raise(-20, duration);
        LUpLeg.lateralRaise(-5, duration);
        LLeg.raise(20, duration);
    };
}

function takeABow(duration) {
    sim{
        Body.bow(65, duration);
    }
}

BEGIN
raiseArmsForStartingPosition();
sim{
    takeABow(1);
    moveLLeg(1);
    moveArms(1);
}
END
//some comment

