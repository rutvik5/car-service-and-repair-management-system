import java.util.Scanner;
import java.sql.SQLException;

public class SetupConnection{

  public static String username="";
  public static String password="";

  public static void main(String[] args){
    Scanner t = new Scanner(System.in);

    Home home = new Home();

    System.out.println("Enter username: ");
    username= t.nextLine();

    System.out.println("Enter password: ");
    password= t.nextLine();
   
    home.displayHomepage();
  }
}
