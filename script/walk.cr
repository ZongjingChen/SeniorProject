MODEL = XBOT;
function moveLeftLeg(duration){
	sim{
		LUpLeg.raise(90, duration);
        LLeg.raise(90, duration);
	};
	sim{
	    LUpLeg.raise(-90, duration);
        LLeg.raise(-90, duration);
	}
}

function moveRightLeg(duration){
    sim{
		RUpLeg.raise(90, duration);
        RLeg.raise(90, duration);
	};
	sim{
	    RUpLeg.raise(-90, duration);
        RLeg.raise(-90, duration);
	}
}

function rightArm(duration) {
    sim{
        RArm.raise(45, duration);
        LArm.raise(-45,duration);
    };
    sim{
        RArm.raise(-45, duration);
        LArm.raise(45, duration);
    }
}

function leftArm(duration) {
    sim{
        RArm.raise(-45, duration);
        LArm.raise(45,duration);
    };
    sim{
        RArm.raise(45, duration);
        LArm.raise(-45, duration);
    }
}

function twoSteps(duration) {
    sim{
        moveLeftLeg(duration);
        rightArm(duration);
    };
    sim{
        moveRightLeg(duration);
        leftArm(duration);
    }
}

BEGIN
sim{
rightArm(1);
moveLeftLeg(1);
};
leftArm(1);
END