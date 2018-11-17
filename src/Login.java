import java.util.Scanner;

class Login{
  public void displayLogin(){
    Scanner t= new Scanner(System.in);
    Home home= new Home();
   

    System.out.println("Please enter the following");
    // write logic to imlement login
    System.out.print("A. User ID:\t");
    String user_id= t.nextLine();
    System.out.println("");

    System.out.print("Password:\t");
    String user_password= t.nextLine();
    System.out.println("");

    System.out.println("Please enter role - manager or customer or receptionist or back");
    String user_choice= t.nextLine().toLowerCase();
    switch (user_choice) {
      // check if credentials are correct and render Manager or Receptionist landing page
      case "manager": 
    	  Manager manager= new Manager();
    	  manager.displayManagerLanding();
      case "receptionist": 
    	  Receptionist recp = new Receptionist();
    	  recp.displayReceptionistLanding();
      case "back":
    	  home.displayHomepage();
      default: System.out.println("Enter a valid choice");
    }
  }

}
