MODEL = XBOT;
function raiseBothArm(degree, duration){
	sim{
		LArm.raise(degree, duration);
		RArm.raise(degree, duration);
	}
}

BEGIN
//raiseBothArm(90, 3);
END