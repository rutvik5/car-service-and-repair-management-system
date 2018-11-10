import java.util.Scanner;

class Home{
  public static void displayHomepage(){

    Scanner t= new Scanner(System.in);

    System.out.println("Enter choice (1-3)\n 1. Login\n 2. Sign Up\n 3. Exit");
    int user_choice = t.nextInt();
    switch(user_choice){
      case 1: Login.displayLogin();
      case 2: SignUp.displaySignUp();
      case 3: return;
      default: System.out.println("Please enter a valid choice");

    }
  }
}
