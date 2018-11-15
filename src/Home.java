import java.util.Scanner;

class Home{
  public void displayHomepage(){

    Scanner t= new Scanner(System.in);
    SignUp signup= new SignUp();
    Login login= new Login();

    System.out.println("Enter choice (1-3)\n 1. Login\n 2. Sign Up\n 3. Exit");
    int user_choice = t.nextInt();
    switch(user_choice){
      case 1: login.displayLogin();
      case 2: signup.displaySignUp();
      case 3: return;
      default: System.out.println("Please enter a valid choice");

    }
  }
}
