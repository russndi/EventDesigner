import java.math.BigDecimal;
import java.util.Scanner;

public class MainView {
	
	
	public String welcomeScreen() {
	String message;
	message = "Welcome to Event Builder";
	message+= "\n for Optional questions please enter NO if N/A";
			return message;
	}
	
	public void result()
	{
		String result;
		result = "The result for your event based on your parameters are as followed.";
				
		System.out.println(result);
	}
	
	public void selectArrangementType()
	{
		System.out.println("Please select your arrangement type(s): One number in each line. Enter 0 when finished entering all arrangements.");
	}
	
	public void printResults()
	{
		
	}
	
}
