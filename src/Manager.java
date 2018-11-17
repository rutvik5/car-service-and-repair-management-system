import java.util.Scanner;

public class Manager{
	
	
	  Employee emp=new Employee();
	  Home home = new Home();
	  
  public void displayManagerLanding(){
	  Scanner t = new Scanner(System.in);
	  System.out.println("Please select one of the following");
	  System.out.println("1. Profile\n" + 
	  		"2. View Customer\n" + 
	  		"Profile\n" + 
	  		"3. Add New\n" + 
	  		"Employees\n" + 
	  		"4. Payroll\n" + 
	  		"5. Inventory\n" + 
	  		"6. Orders\n" + 
	  		"7. Notifications\n" + 
	  		"8. New Car Model\n" + 
	  		"9. Car Service\n" + 
	  		"Details\n" + 
	  		"10. Service History\n" + 
	  		"11. Invoices\n" + 
	  		"12. Logout");
	  
	  int user_choice = t.nextInt();
	  switch(user_choice){
      case 1: emp.displayProfile();
      break;
      case 2: emp.viewCustomerProfile();
      break;
      case 3: addEmployee();
      break;
      case 4: payroll();
      break;
      case 5: inventory();
      break;
      case 6: orders();
      break;
      case 7: notifications();
      break;
      case 8: addNewCarModel();
      break;
      case 9: getCarServiceDetails();
      break;
      case 10:getServiceHistory();
      break;
      case 11:getInvoices();
      break;
      case 12:home.displayHomepage();
      break;
      default: System.out.println("Please enter a valid choice");
      break;
    }
	  t.close();
	  
  }


private void addEmployee () {
	  Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
	  displayManagerLanding();
	  t.close();
  }
  
  private void payroll() {
	  Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
	  displayManagerLanding();
	  t.close();
  }
  
  private void inventory() {
	  Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
	  displayManagerLanding();
	  t.close();
  }
  
  private void orders() {
	  Scanner t = new Scanner(System.in);
	  System.out.println("1. Order History\n" + 
	  		"2. New Order\n" + 
	  		"3. Go Back");
	  int choice = t.nextInt();
	  switch (choice) {
	  case 1: orderHistory();
	  break;
	  case 2: newOrder();
	  break;
	  case 3: displayManagerLanding();
	  break;
	  default: System.out.println("Please enter a valid choice");
	  }
	  t.close();
  }
  
  private void orderHistory() {
	  Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  orders();
	  t.close();
  } 
  
  private void newOrder() {
	  Scanner t = new Scanner(System.in);
	  System.out.println("1. Place Order\n" + 
		  		"2. Go Back");
	  int choice = t.nextInt();
	  switch (choice) {
	  case 1: placeorder();
	  break;
	  case 2: orders();
	  break;
	  default: System.out.println("Please enter a valid choice");
	  }
	  t.close();
  } 
  
  private void placeorder() {
	  Scanner t = new Scanner(System.in);
	  System.out.println("Please enter part id:");
	  int partId = t.nextInt();
	  System.out.println("Please enter part quantity");
	  int quantity = t.nextInt();
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  newOrder();
	  t.close();
  }
  
  private void notifications() {
	  Scanner t = new Scanner(System.in);
	  System.out.println("1. Order ID\n" + 
	  		"2. Go back");
	  int choice = t.nextInt();
	  switch (choice) {
	  case 1: getOrderDetails();
	  break;
	  case 2: displayManagerLanding();
	  break;
	  default: System.out.println("Please enter a valid choice");
	  }
	  t.close();
  }

private void getOrderDetails() {
	Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  notifications();
	  t.close();
}

private void addNewCarModel() {
	Scanner t = new Scanner(System.in);
	  System.out.println("1. Add Car\n" + 
	  		"2. Go back");
	  int choice = t.nextInt();
	  switch (choice) {
	  case 1: addCarModel();
	  break;
	  case 2: displayManagerLanding();
	  break;
	  default: System.out.println("Please enter a valid choice");
	  }
	  t.close();
	
}

private void addCarModel() {
	Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  addNewCarModel();
	  t.close();
	
}

private void getCarServiceDetails() {
	Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  displayManagerLanding();
	  t.close();
	
}

private void getServiceHistory() {
	Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  displayManagerLanding();
	  t.close();
}

private void getInvoices() {
	Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  displayManagerLanding();
	  t.close();
}


  
  
}
