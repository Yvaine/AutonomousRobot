
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;

public class AutonomousRobot {
	
	private static EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
	private static EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("D"));

	public static int displayX = 0;
	public static int displayY = 0;
	
	
	
	@SuppressWarnings("resource")
	public static void main(String[] args){
		Odometer odom = new Odometer(leftMotor, rightMotor, 30, true);
		Navigation nav = new Navigation(odom);
		UltrasonicPoller usPoller = new UltrasonicPoller();
		
		odom.start();
		USLocalizer usLocal = new USLocalizer(odom, usPoller, USLocalizer.LocalizationType.FALLING_EDGE, nav);
		usLocal.doLocalization();
	}
	
	
}
