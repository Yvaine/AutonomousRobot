
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.Brick;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.LED;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.remote.ev3.RMIEV3;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMIRemoteRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.remote.ev3.RemoteMotorPort;
import lejos.remote.ev3.RemoteRequestEV3;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;

/**
 * @author Ralph Bou Samra
 * @version 1.0 Nov 1, 2015
 */

public class AutonomousRobot {

	public static EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
	public static EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("D"));

	public static int displayX = 0;
	public static int displayY = 0;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws MalformedURLException, NotBoundException, InterruptedException {

		 Odometer odom = new Odometer(leftMotor, rightMotor, 30, true);
		 Navigation nav = new Navigation(odom);

		//UltrasonicPoller usPoller = new UltrasonicPoller();
		//LightSensorPoller lsPoller = new LightSensorPoller();

		odom.start();
		//OdometryCorrection odomCorr = new OdometryCorrection(odom);
		//odomCorr.start();
		
		
		 //USLocalizer usLocal = new USLocalizer(odom, usPoller,
		 //USLocalizer.LocalizationType.FALLING_EDGE, nav);
		 //usLocal.doLocalization();
		 //LightLocalizer lsLocalizer = new LightLocalizer(odom, lsPoller, nav);
		 //lsLocalizer.doLocalization();

		 
		// Thread.sleep(2000);
		
		
			//leftMotor.rotate(convertAngle(2.1, 15, 90), true);
			//rightMotor.rotate(-convertAngle(2.1, 15, 90), false);
		 nav.travelTo(30, 30);
		 //nav.turnTo(0, true);
		 
		
		 // odom.setPosition(new double[]{0, 0, 0}, new boolean[]{true, true, true});
		 
		 
		// ObstacleAvoider obstacleAvoider = new ObstacleAvoider(usPoller, nav, odom);
		// obstacleAvoider.avoidObstacles();
		 
		 
		 
		/*
		System.out.println("Master brick will command the slave brick to catch the block now!");

		RemoteEV3 secondBrick = null;
		try {
			secondBrick = new RemoteEV3("172.20.10.7");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (secondBrick != null) {
			System.out.println("This part works!!!!");
			RMIRegulatedMotor motor = secondBrick.createRegulatedMotor("B", 'L');
			RMIRegulatedMotor secondMotor = secondBrick.createRegulatedMotor("C", 'M');

			BlockCatcher blockCatcher = new BlockCatcher(motor, secondMotor);
			blockCatcher.start();
		} else {
			System.out.println("Second brick is null!");
		}*/
		while (Button.waitForAnyPress() != Button.ID_ESCAPE)
			;
		System.exit(0);
	}
	
	
	private static int convertAngle(double radius, double width, double angle) {
		return convertDistance(radius, Math.PI * width * angle / 360.0);
	}

	
	private static int convertDistance(double radius, double distance) {
		return (int)((distance*180.0)/(Math.PI*radius));
	}
	
}
