import java.math.BigDecimal;
import java.text.NumberFormat;

public interface EventReport {

	public String printClientReport(Client client);
	void printEventResults(Client client, Florist florsit);
	void printEventReport(Client Client, ClientEvent event);
	void printFloristReport(ClientEvent event);
	void printNumberOfArrangements(ClientEvent event);
}
class Reports implements EventReport{

	public Reports()
	{

	}
	@Override

	public String printClientReport(Client client)
	{
		String clientreport;
		clientreport = "Client name: " + client.getClientName();
		String number = client.getPhoneNumber().replaceFirst("(\\d{3})(\\d{3})(\\d+)","($1) $2-$3");

		clientreport += "\nClient PhoneNumber: " + number;
		if(client.getPartnerName() == null)
		{clientreport += "\nClient partner: N/A";
		}
		else {
			clientreport += "\nClient partner: " + client.getPartnerName();}
		return clientreport;
	}
	@Override
	public void printEventReport(Client Client, ClientEvent event) {
		System.out.println(printClientReport(Client));
		String eventReport="\n*********************************";
		eventReport += "\nYou've entered the following parameters for your event";
		eventReport += "\nDate (YEAR-MONTH-DAY): " + event.getEventDate();

		eventReport += "\nExpected Budget: " + NumberFormat.getCurrencyInstance().format(event.getBudgetAmount());
		eventReport += "\nTable Count: " + event.getTableCount();
		if (event.getGuestCount() != null) {
			eventReport += "\nGuest Count: " + event.getGuestCount();
		}
		if (event.getEventTheme() !=null)
			eventReport +="\nEvent Theme: " + event.getEventTheme();
		if(event.getColorPalette() != null) {
			eventReport += "\nColor Palette: " + event.getColorPalette() + "\n";
		}
		eventReport += "*****************************************\n\n\n";
		System.out.println(eventReport);

	}

	@Override
	public void printNumberOfArrangements(ClientEvent event) {
		
		event.getArrangementsForEvent().forEach((key, value) -> {
			String arrangementReport="\n*********************************";
			arrangementReport += "\nThe suggested number of each of your selected arrangements is as follows: ";
			arrangementReport += "\n" + key.toString() + " " + value.intValue();
			System.out.println(arrangementReport);
		});
	}


	@Override
	public void printEventResults(Client client, Florist florist)
	{

		String eventResult = "Based on your selected parameters these results were generated: \n";
		eventResult += "Your budget was: " + NumberFormat.getCurrencyInstance().format(client.getClientEvent().getBudgetAmount())
				;
		eventResult += "\nEstimated Event Cost: " + NumberFormat.getCurrencyInstance().format(client.getClientEvent().getEstimatedEventCost()) + "\n";
		eventResult += "These are the florists available in your budget";

		System.out.println(eventResult);



	}

	@Override
	public void printFloristReport(ClientEvent event) {
		event.getFloristsWithInBudget().forEach((key, value) -> {
			String floristReport = "";
			floristReport += "\n*********************************";
			floristReport += "\nCompany name: "+ key.getFloristName();
			floristReport += "\nContact number: "+ key.getFloristContactNumber();
			floristReport += "\nMinimum budget required: "+ key.getMinimumBudget();
			floristReport += "\nEstimated fee: " + NumberFormat.getCurrencyInstance().format(key.getTotalFee()) + "\n";
			floristReport +="\nTotal Estimated Event Cost: " + NumberFormat.getCurrencyInstance().format(event.getEstimatedEventCost()
					.add(key.getTotalFee()));
			System.out.println(floristReport);
		}
				);

	}


}