import java.util.Scanner;

class Login{
  public void displayLogin(){
    Scanner t= new Scanner(System.in);
    Home home= new Home();
    Manager manager= new Manager();

    System.out.println("Please enter the following");
    // write logic to imlement login
    System.out.print("A. User ID:\t");
    String user_id= t.next();
    System.out.println("");

    System.out.print("Password:\t");
    String user_password= t.next();
    System.out.println("");

    System.out.println("Please select one of the following");
    System.out.println("1. Sign-In\n 2. Go Back");
    int user_choice= t.nextInt();
    switch (user_choice) {
      // check if credentials are correct and render Manager or Receptionist landing page
      case 1: manager.displayManagerLanding();
      case 2: home.displayHomepage();
      default: System.out.println("Enter a valid choice");
    }
  }

}
