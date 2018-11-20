import java.util.Scanner;
import java.sql.SQLException;

public class SetupConnection{

  public static String username="";
  public static String password="";

  public static void main(String[] args){
    Scanner t = new Scanner(System.in);

    Home home = new Home();

    System.out.print("Enter username: ");
    SetupConnection.username= t.nextLine();
    System.out.println("");

    System.out.print("Enter password: ");
    SetupConnection.password= t.nextLine();
    System.out.println("");
    try{
        DBUtility.connectDB(SetupConnection.username, SetupConnection.password);
    }catch(SQLException e){
      e.printStackTrace();
    }
    //home.displayHomepage();
  }
}
