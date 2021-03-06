
import java.io.IOException;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class EventBuilderMain {
	static Client currentclient;
	static Florist florist = new Florist();
	public static void main(String[] args) throws IOException {
		final EventReport report = new Reports();
		MainView view = new MainView();
		final ClientEventBuilder builder = new BuilderImpl();
		final FloristBuilder floristBuilder = new Builder();




		Scanner scan = new Scanner(System.in);
		System.out.println(view.welcomeScreen());
		System.out.println("What is the client's name?");
		System.out.println("First Name");

		String first_name= scan.nextLine();
		System.out.println("Last name?");
		String last_name = scan.nextLine();
		System.out.println("Phone Number?");
		String phone_num = scan.nextLine();
		System.out.println("(Optional)Partner's Name?");
		String partner_name = scan.nextLine();
		if (partner_name.equalsIgnoreCase("no"))
			partner_name = null;
		Optional<String> partnerName = Optional.ofNullable(partner_name);


		currentclient = new Client(first_name, last_name,partnerName, phone_num, builder);

		System.out.println("Date of the Event? (YEAR-MONTH-DAY)?");

		String date = scan.nextLine();
		DateTimeFormatter format = DateTimeFormatter.ISO_DATE;
		LocalDate eventDate= LocalDate.parse(date, format);

		System.out.println("Budget?");
		BigDecimal budgetAmount = scan.nextBigDecimal();

		System.out.println("(Optional) Guest count?");
		String guestcount = scan.next();
		if(guestcount.equalsIgnoreCase("no"))
			guestcount=null;

		int guest_count = Integer.parseInt(guestcount);
		Optional<Integer> guestCount = Optional.ofNullable(guest_count);

		System.out.println("Table count?");
		int tableCount = scan.nextInt();


		System.out.println("(Optional) Event theme?");
		String theme = scan.next();
		if (theme.equalsIgnoreCase("no"))
			theme = null;
		Optional <String> eventTheme = Optional.ofNullable(theme);

		System.out.println("(Optional) Color Palette?");
		String color_palette = scan.next();
		if (color_palette.equalsIgnoreCase("no"))
			color_palette = null;
		Optional <String> colorPalette = Optional.ofNullable(color_palette);

		//System.out.println(report.printClientReport(currentclient));

		ClientEvent event = currentclient.createEvent(eventDate, budgetAmount, guestCount, tableCount, eventTheme, colorPalette);



		System.out.println("Please choose Arrangements from the list below.");
		System.out.println(LargeFloralRing.Instance.ArrangementReport());
		JFrame frame = new JFrame("Large FLoral Ring Arrangement(1)");
		ImageIcon lfra = new ImageIcon("images/LargeFloralHeavyRing.jpg");

		JLabel label = new JLabel(lfra);
		frame.add(label);
		frame.setSize(lfra.getIconWidth(), lfra.getIconHeight());
		frame.setVisible(true);
		System.out.println(VotiveArrangement.Instance.ArrangementReport());
		JFrame frame1 = new JFrame("Votive Arrangement(5)");

		ImageIcon votive = new ImageIcon("images/VotivesAndGreenery.jpg");

		JLabel label1 = new JLabel(votive);
		frame1.setSize(votive.getIconWidth(), votive.getIconHeight());
		frame1.add(label1);
		frame1.setVisible(true);
		System.out.println(TallAnchorArrangement.Instance.ArrangementReport());
		JFrame frame2 = new JFrame("Tall Anchor Arrangement (4)");
		ImageIcon taa = new ImageIcon("images/TallAnchorPiece.jpg");

		JLabel label2 = new JLabel(taa);
		frame2.setSize(taa.getIconWidth(), taa.getIconHeight());
		frame2.add(label2);
		frame2.setVisible(true);
		System.out.println(LowFloral.Instance.ArrangementReport());
		JFrame frame3 = new JFrame("Low Floral Arrangement (2)");
		ImageIcon lfa= new ImageIcon("images/LowFloralHeavyArrangement.jpg");
		JLabel label3 = new JLabel(lfa);
		frame3.setSize(lfa.getIconWidth(), lfa.getIconHeight());
		frame3.add(label3);
		frame3.setVisible(true);
		System.out.println(SmallFloralRing.Instance.ArrangementReport());
		JFrame frame4 = new JFrame("Samll Floral Ring Arrangement (3)");
		ImageIcon sfra= new ImageIcon("images/SmallFloralRing.jpg");
		JLabel label4 = new JLabel(sfra);
		frame4.setSize(sfra.getIconWidth(), sfra.getIconHeight());
		frame4.add(label4);
		frame4.setVisible(true);


		view.selectArrangementType();
		int arrangementChoice;
		Object arrangements[] = new Object[4];
		int i = 0;


//		while (0 != (arrangementChoice = scan.nextInt())) {
//			arrangements[i] = arrangementChoice;
//			i++;
//			}; 
		
		while (0 != (arrangementChoice = scan.nextInt())) {
			if (arrangementChoice == 1) {
				arrangements[i] = LargeFloralRing.getLargeFloralRingArrangement().getLargeFloralRingArrangement();
				i++;
			}
			
			if (arrangementChoice == 2) {
				arrangements[i] = VotiveArrangement.getVotiveArrangement();
				i++;
			}
			
			if (arrangementChoice == 3) {
				arrangements[i] = TallAnchorArrangement.getTallAnchorArrangement();
				i++;
			}
			
			if (arrangementChoice == 4 ) {
				arrangements[i] = LowFloral.getLowFloralArrangement();
				i++;
			}
			
			if (arrangementChoice == 5) {
				arrangements[i] = SmallFloralRing.getSmallFloralRingArrangement();
				i++;
			}

		}
			
		getResults(arrangements);
			report.printEventReport(currentclient, event);	
			report.printNumberOfArrangements(event);
			report.printEventResults(currentclient, floristBuilder.getFlorist());
			report.printFloristReport(event);

	}

	public static void getResults(Object[] arrangements) {	
		currentclient.getClientEvent().designEvent(florist, arrangements);
	}		





}


