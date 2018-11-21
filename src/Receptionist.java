import java.util.Scanner;

class Receptionist{
	String UserId;
    public void displayReceptionistLanding(String user_id){
    UserId = user_id;
    Scanner t= new Scanner(System.in);
    Home home= new Home();
    Employee employee= new Employee();

    System.out.println("Please select one of the following");
    System.out.println("1. Profile\n" + " 2. View Customer Profile" + "\n" + "3. Register Car" + "\n" + "4. Service History" + "\n" + "5. Schedule Service" + "\n" + "6. Reschedule Service" + "\n" + "7. Invoices" + "\n"
                        +"8. Daily Task Update Inventory" + "\n" + "9. Daily Task- Record Deliveries" + "\n" + "10. Logout");

    int user_choice = t.nextInt();

    switch(user_choice){
      case 1: employee.displayProfile(user_id);
      case 2: employee.viewCustomerProfile(user_id);
      case 3: registerCar();
      case 4: serviceHistory();
      case 5: scheduleService();
      case 6: rescheduleService();
      case 7: invoices();
      case 8: updateInventory();
      case 9: recordDeliverables();
      case 10: home.displayHomepage();
      default: System.out.println("Please enter a valid choice");

    }
  }

  public void registerCar(){
    Scanner t= new Scanner(System.in);

    System.out.println("Please enter the following details");
    // run queries to add the following details into database
    System.out.print("A. Customer email address:\t");
    String customer_email= t.next();
    System.out.println("");

    System.out.print("B. License Plate:\t");
    String license_plate= t.next();
    System.out.println("");

    System.out.print("C. Purchase Date:\t");
    String purchase_date= t.next();
    System.out.println("");

    System.out.print("D. Make:\t");
    String make= t.next();
    System.out.println("");

    System.out.print("E. Model:\t");
    String model= t.next();
    System.out.println("");

    System.out.print("F. Year:\t");
    String year= t.next();
    System.out.println("");

    System.out.print("G. Current Mileage:\t");
    String current_mileage= t.next();
    System.out.println("");

    System.out.print("H. Last Service Date (optional):\t");
    String last_service_date= t.next();
    System.out.println("");

    System.out.println("Please select one of the following");
    System.out.println("1. Register" + "\n" + "2. Cancel");

    int user_choice= t.nextInt();
    switch (user_choice) {
      case 1:
            System.out.println("Car Successfully Added");
            //save the user info to database
            displayReceptionistLanding(UserId);

      case 2: displayReceptionistLanding(UserId);// do not add anything to db
      default: System.out.println("Enter a valid choice");
    }
  }

  public void serviceHistory(){
    Scanner t= new Scanner(System.in);

    System.out.println("Please enter the following details");
    // run queries to add the following details into database
    System.out.print("A. Customer email address:\t");
    String customer_email= t.next();
    System.out.println("");

    /* based on the customer_email, display the following:

    service_id, license_plate, service_type, mechanic_name, service_start-date/time,
    service_end-date/time(expected or actual), service_status(Pending/Ongoing/Complete) */

    System.out.println("Go Back? Enter 1");
    displayReceptionistLanding(UserId);
  }

  public void scheduleService(){
    Scanner t= new Scanner(System.in);

    System.out.println("Please enter the following details");
    // run queries to add the following details into database
    System.out.print("A. Customer email address:\t");
    String customer_email= t.next();
    System.out.println("");

    System.out.print("B. License Plate:\t");
    String license_plate= t.next();
    System.out.println("");

    System.out.print("C. Current Mileage:\t");
    String current_mileage= t.next();
    System.out.println("");

    System.out.print("D. Mechanic Name(Optional):\t");
    String mechanic_name= t.next();
    System.out.println("");

    //based on the above entered data, provide the users following options
    System.out.println("Please select one of the following");
    System.out.println("1. Schedule Maintenance" + "\n" + " 2. Schedule Repair" + "\n" + "3. Go Back");

    int user_choice= t.nextInt();
    switch (user_choice) {

      case 1: scheduleMaintenance();
break;
      case 2: scheduleRepair();
      break;
      case 3: displayReceptionistLanding(UserId);
      break;
      default: System.out.println("Enter a valid choice");

    }
 }
    public void scheduleMaintenance(){
      Scanner t= new Scanner(System.in);

      System.out.println("Please select one of the following");
      System.out.println("1. Find Service Date" + "\n" + " 2. Go Back");

      int user_choice= t.nextInt();
      switch (user_choice) {
        case 1: findServiceDate();
        case 2: scheduleService();
        default: System.out.println("Enter a valid choice");
      }
    }

    public void findServiceDate(){
      Scanner t= new Scanner(System.in);
      //display the 2 earliest available dates from the db
      System.out.println("Please select one of the following");
      System.out.println("1. Schedule on Date" + "\n" + " 2. Go Back");

      int user_choice= t.nextInt();
      switch (user_choice) {

        case 1:
                System.out.println("Service appointment successfully saved");
                scheduleService();

        case 2: scheduleMaintenance();
        default: System.out.println("Enter a valid choice");
      }
    }

    public void scheduleRepair(){
      Scanner t= new Scanner(System.in);

      System.out.println("Please select one of the following encountered problem");
      System.out.println("1. Engine knock" + "\n" + "2. Car drifts in a particular direction" + "\n" + "3. Battery does not hold charge" + "\n" + "4. Black/unclean exhaust" + "\n"
      + "5. A/C- Heater not working" + "\n" + "6. Headlapms/Tail lamps not working" + "\n" + "7. Check engine light" + "\n" + "8.Go Back");

      int user_choice= t.nextInt();
      switch (user_choice) {
        //based on the repair display the report, 2 identifed service dates, mechanic_name
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8: scheduleService();
        default: System.out.println("Enter a valid choice");

        selectRepairDate();
      }
    }

    public void selectRepairDate(){
      Scanner t= new Scanner(System.in);

      System.out.println("Please select one of the following");
      System.out.println("1. Repair on Date" + "\n" + "2. Go Back");

      int user_choice= t.nextInt();
      switch (user_choice) {

        case 1:
                System.out.println("Repair appointment successfully saved");
                scheduleService();

        case 2: scheduleRepair();
        default: System.out.println("Enter a valid choice");
      }
    }

    public void rescheduleService(){
      Scanner t= new Scanner(System.in);

      System.out.println("Ask user to enter customer email-id and display the following: License Plate,"
                        +  "Service ID, Service Date, Service Type(Maintenance/Repair),"
                        +  "Service Details(Service A/B/C orProblem)");

      System.out.print("Enter the email\t");
      String customer_email= t.next();
      System.out.println("");

      System.out.println("Please select one of the following");
      System.out.println("1. Pick a Service" + "\n" + "2. Go Back");

      int user_choice= t.nextInt();
      switch (user_choice) {

        case 1:
                System.out.println("If the user chooses 1,ask him to enter one of the service IDs"
                                  +"to select the service to be rescheduled and then find two"
                                    +"earliest available maintenance/repair dates that are at least"
                                  +"one day after the current service date");
                pickService();

        case 2: displayReceptionistLanding(UserId);
        default: System.out.println("Enter a valid choice");
      }
    }

    public void pickService(){
      Scanner t= new Scanner(System.in);

      System.out.println("SQL: display the 2 identified dates and mechanic name");

      System.out.println("Please select one of the following");
      System.out.println("1. Reschedule Date" + "\n" + "2. Go Back");

      int user_choice= t.nextInt();
      switch (user_choice) {

        case 1:
                System.out.println("Ask the user to pick one fo the dates shown and add to db");
                displayReceptionistLanding(UserId);

        case 2: displayReceptionistLanding(UserId);
        default: System.out.println("Enter a valid choice");
      }

    }

    public void invoices(){
      Scanner t= new Scanner(System.in);

      System.out.println("Ask user to enter customer email-id and display the following: Service ID,"
                        +  "Service Start, Date/Time, Service End, Date/Time, Licence Plate,"
                        +  "Service Type, Mechanic Name, Parts Used in service with cost of each part,"
                        +  "Total labor hours, Labor wages per hour, Total Service Cost");

      System.out.print("Enter the email\t");
      String customer_email= t.next();
      System.out.println("");

      System.out.println("Please select the following");
      System.out.println("1.Go Back");
      int user_choice= t.nextInt();
      if (user_choice == 1) displayReceptionistLanding(UserId);
    }

    public void updateInventory(){
      Scanner t= new Scanner(System.in);

      System.out.println("Run a task to update the counts of parts to be used that day, basically adjusted"
                          +"(decrementing them) to reflect the fact the parts will be removed and actually"
                        +  "used that day. At the end, show a message displaying whether the task finished"
                        +  "running successfully or not.");

      System.out.println("Please select the following");
      System.out.println("1.Go Back");
      int user_choice= t.nextInt();
      if (user_choice == 1) displayReceptionistLanding(UserId);
    }

    public void recordDeliverables(){
      Scanner t= new Scanner(System.in);

      System.out.println("Please select one of the following");
      System.out.println("1. Enter Order ID (CSV)" + "\n" + "2. Go Back");

      int user_choice= t.nextInt();
      switch (user_choice) {

        case 1:
                System.out.print("Please enter Order ID (separate by comma for multiple ID's):\t");
                String order_id= t.next();

                System.out.println("Run a task to update the status of any pending orders whose items have"
                +"arrived to â€œcompleteâ€� and update their counts, and then show a message displaying whether the"
                +"task ran successfully or not.");

                System.out.println("Just before going back, change the status of any pending orders which"
                                    +"did not arrive and are past the delivery window to â€œdelayedâ€� and generate"
                                  +"  a notification to the manager");


        case 2: displayReceptionistLanding(UserId);
        default: System.out.println("Enter a valid choice");
      }

    }

}
