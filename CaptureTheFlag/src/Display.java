import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;

public class Display extends Thread {
	private TextLCD t;

	String strDisplay = "";
	
	public Display(){
		t = LocalEV3.get().getTextLCD();
	}
	
	public void print(String str, String content){
		t.drawString(str + ": " + content, AutonomousRobot.displayX, AutonomousRobot.displayY);
		AutonomousRobot.displayX++;
		AutonomousRobot.displayY++;
	}
	
	public void print(String str, String content, int row)
	{
		t.clear(row);
		t.drawString(str + ":" + content, 0, row);
	}
}
