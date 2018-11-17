import java.util.Scanner;

public class Employee{
  public void viewCustomerProfile(){
	  
  }

public void displayProfile(){
	Scanner t = new Scanner(System.in);
	  System.out.println("Please select one of the following");
	  System.out.println("1. View Profile\n" + 
	  		"2. Update Profile\n" + 
	  		"3. Go Back");
	  int choice = t.nextInt();
	  switch (choice) {
	  case 1: viewProfile();
	  break;
	  case 2: updateProfile();
	  break;
	  case 3: redirectAccording();
	  break;
	  default: System.out.println("Please enter a valid choice");
	  displayProfile();
	  }
	  t.close();
  }

private void redirectAccording() {
	System.out.println("Add logic to show landing page according to role - now pointing to display page");
	new Home().displayHomepage();
}

private void updateProfile() {
	System.out.println("Please select an option from below"); 
	System.out.println("1. Name\n" + 
			"2. Address\n" + 
			"3. Email Address\n" + 
			"4. Phone Number\n" + 
			"5. Password\n" + 
			"6. Go Back");
	int choice;
	do {
		Scanner t =new Scanner(System.in);
		choice = t.nextInt();
	  switch (choice) {
	  case 1: updateName();
	  break;
	  case 2: updateAddress();
	  break;
	  case 3: updateEmail();
	  break;
	  case 4: updatePhone();
	  break;
	  case 5: updatePassword();
	  break;
	  case 6: displayProfile();
	  break;
	  }
	} while (choice !=6);
}

private void updatePassword() {
	// TODO Auto-generated method stub
	
}

private void updatePhone() {
	// TODO Auto-generated method stub
	
}

private void updateEmail() {
	// TODO Auto-generated method stub
	
}

private void updateAddress() {
	// TODO Auto-generated method stub
	
}

private void updateName() {
	// TODO Auto-generated method stub
	
}

private void viewProfile() {
	Scanner t = new Scanner(System.in);
	  System.out.println("1. Go Back");
	  int choice = t.nextInt();
	  if (choice ==1 )
		  displayProfile();
	  t.close();
	
}
}
