import java.util.Scanner;

class Login{
  public void displayLogin(){
    Scanner t= new Scanner(System.in);
    Home home= new Home();
   

    System.out.println("Please enter the following");
    // write logic to implement login
    System.out.print("A. User ID:\t");
    String user_id= t.nextLine();
    System.out.println("");

    System.out.print("Password:\t");
    String user_password= t.nextLine();
    System.out.println("");
    if (user_id.equals("abc") && user_password.equals("abc")) {
    	enterPortal(t, home);
    } else {
    	System.out.println("Invalid Username/password");
    	displayLogin();
    }

    
  }

private void enterPortal(Scanner t, Home home) {
	System.out.println("Please enter role - manager or customer or receptionist or back");
    String user_choice= t.nextLine().toLowerCase();
    switch (user_choice) {
      // check if credentials are correct and render Manager or Receptionist landing page
      case "manager": 
    	  Manager manager= new Manager();
    	  manager.displayManagerLanding();
    	  break;
      case "receptionist": 
    	  Receptionist recp = new Receptionist();
    	  recp.displayReceptionistLanding();
    	  break;
      case "back":
    	  home.displayHomepage();
    	  break;
      default: System.out.println("Enter a valid choice");
    }
}

}
