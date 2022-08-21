package Helper;

import javax.swing.JOptionPane;

public class Helper {
	
	public static void showMessage(String str) {	// Printing common alert messages according to str or directly given message.
		String msg = "";
		
		switch(str) {
		case "fill" :
			msg = "Please fill in all fields !";
			break;
			
		case "success" :
			msg = "Process is done successfully !";
			break;
			
		case "fail" :
			msg = "Ops... Process cannot be completed !";
			break;
			
		default :
			msg = str;	
		}
	
		JOptionPane.showConfirmDialog(null, msg, "AlertMessage", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	public static boolean confirm(String str) {
		String msg = "";
		switch(str) {
		case "sure" :
			msg = "Do you confirm ?";
			break;
			
		default :
			msg = str;
			break;
		}
	
		int res = JOptionPane.showConfirmDialog(null, msg, "Alert!", JOptionPane.YES_NO_OPTION); 
		if (res == 0) return true;
		else return false;
	}
	
}
