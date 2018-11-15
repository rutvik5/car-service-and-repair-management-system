import java.util.Scanner;

class SignUp{
  public void displaySignUp(){
    Scanner t= new Scanner(System.in);
    Home home= new Home();
    Login login= new Login();

    System.out.println("Please enter the following");

    System.out.print("A. Email Address:\t");
    String user_email= t.next();
    System.out.println("");

    System.out.println("Password:\t");
    String user_password= t.next();
    System.out.println("");

    System.out.println("Name:\t");
    String user_name= t.next();
    System.out.println("");

    System.out.println("Address:\t");
    String user_address= t.next();
    System.out.println("");

    System.out.println("Phone Number:\t");
    String user_phone= t.next();
    System.out.println("");

    System.out.println("Please select one of the following");
    System.out.println("1. Sign-Up\n 2. Go Back");
    int user_choice= t.nextInt();
    switch (user_choice) {
      case 1:
            login.displayLogin(); //save the user info to database
            System.out.println("User Successfully Added");
      case 2: home.displayHomepage();
      default: System.out.println("Enter a valid choice");
    }
  }

}
