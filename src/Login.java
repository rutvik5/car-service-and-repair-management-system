import java.util.Scanner;

class Login{
  public static void displayLogin{
    Scanner t= new Scanner(System.in);

    System.out.println("Please Enter the following");

    System.out.print("A. User ID:\t");
    String user_id= t.next();

    System.out.println("Password:\t");
    String password= t.next();

    System.out.println("Please select one of the following");
    System.out.println("1. Sign-In\n 2. Go Back");
    int user_choice= t.nextInt();
    switch (user_choice) {
      case 1: Manager.landingPage(); // check if credentials are correct and render Manager/Receptionist landing page
      case 2: displayHomepage();
      default: System.out.println("Enter a valid choice");
    }
  }

}
