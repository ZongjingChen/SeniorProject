MODEL = YBOT;

function raiseArms(duration) {
    sim{
        LArm.raise(20, duration);
        RArm.raise(20, duration);
        LForeArm.raise(90, duration);
        RForeArm.raise(90, duration);
    }
}

BEGIN
raiseArms(1);
sim{
    LForeArm.rotate(95,0.1);
    RForeArm.rotate(85,0.1);
    LHand.rotate(-95, 0.1);
    RHand.rotate(-85, 0.1);
};
sim{
LForeArm.raise(29,0.5);
RForeArm.raise(29,0.5);
Head.raise(-25, 0.5);
};
sim{
LForeArm.raise(-29,0.5);
RForeArm.raise(-29,0.5);
Head.raise(25, 0.5);
};
sim{
LForeArm.raise(29,0.5);
RForeArm.raise(29,0.5);
Head.raise(-25, 0.5);
};
sim{
LForeArm.raise(-29,0.5);
RForeArm.raise(-29,0.5);
Head.raise(25, 0.5);
};sim{
LForeArm.raise(29,0.5);
RForeArm.raise(29,0.5);
Head.raise(-25, 0.5);
};
sim{
LForeArm.raise(-29,0.5);
RForeArm.raise(-29,0.5);
Head.raise(25, 0.5);
};
END