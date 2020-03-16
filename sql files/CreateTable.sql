CREATE TABLE Service_Center (
    CenterID int NOT NULL PRIMARY KEY,
    Center_Name varchar(255) NOT NULL,
    Center_Address varchar(255),
    Center_Tele varchar(255)
);

CREATE TABLE Inventory (
    CenterID int,
	Car_model varchar(100),
    PartID int ,
    Part_Name varchar(255),
    Current_quantity varchar(255),
    Unit_price float,
    Threshold_Min int,
    Min_Order int,
	Warranty int,
CONSTRAINT PK_Inventory PRIMARY KEY ( CenterID, PartID),
CONSTRAINT FK_Inventory FOREIGN KEY (CenterID)REFERENCES Service_Center(CenterID) ON  DELETE CASCADE   
);

CREATE TABLE Customer (
    CustID int PRIMARY KEY,
    Cust_Name varchar(255),
    Cust_email varchar(255) NOT NULL UNIQUE,
    Cust_address varchar(255),
    Cust_Phone number(10)
);

CREATE TABLE Employee (
    EmpID int PRIMARY KEY,
    Emp_Name varchar(255),
    Emp_email varchar(255) NOT NULL UNIQUE,
    Emp_address varchar(255),
    Emp_Phone number(10),
	Emp_start_date date
);


CREATE TABLE Monthly_Paid_Emp (
    EmpID int,
    Salary varchar(255),
    PRIMARY KEY (EmpID),
    CONSTRAINT FK_Monthly_Paid_Emp FOREIGN KEY (EmpID)
    REFERENCES Employee(EmpID) ON  DELETE CASCADE
);

CREATE TABLE Service
(
ServiceID int PRIMARY KEY,
Total_Fees float ,
Total_Hours float
);


CREATE TABLE BasicService (
    BasicServiceID int,
    Basic_Service_Name varchar(255),
    Labour_Charge_Per_Hour float,
    Hours_Required float,  
    CONSTRAINT PK_BasicService PRIMARY KEY ( BasicServiceID)          
);

Create table PartRequired_car_model (
BasicServiceID int,
car_model varchar(100),
Part_Required int,
Part_quantity_required int,
CONSTRAINT PK_PartRequired PRIMARY KEY ( BasicServiceID, car_model),
CONSTRAINT FK_PartRequired FOREIGN KEY (BasicServiceID)REFERENCES BasicService(BasicServiceID) ON  DELETE CASCADE
);

Create table Basic_service_mapping (
car_model varchar(100),
Service_Type varchar(10),
miles int,
Basic_services_used varchar(1000),
CONSTRAINT PK_Basic_service_mapping PRIMARY KEY (car_model,Service_Type)
);

create table Repair_service_mapping (
repair_id int primary key,
repair_name varchar(100),
Dignostic_solution varchar(100),
Diagnostic_fee int,
Basic_services_used varchar(1000)
);



CREATE TABLE Mechanic_Emp
(
EmpID int PRIMARY KEY,
Wages float,
Hours_Worked float CHECK(Hours_Worked<=11*15),
CONSTRAINT FK_EMPID FOREIGN KEY (EmpID)
REFERENCES Employee(EmpID) ON  DELETE CASCADE
);


CREATE TABLE Cars
(
LicensePlateID  varchar(255) PRIMARY KEY,
Car_Type varchar(255),
Date_Purchase DATE,
Last_Mileage int,
Type_Recent_Service varchar(255),
Date_Recent_Service DATE
);



CREATE TABLE Repair
(
Repair_Id int,
ServiceID int PRIMARY KEY,
Problem_Cause varchar(255),
Diagnostic_Fees float,
CONSTRAINT FK_Repair FOREIGN KEY (ServiceID)
REFERENCES Service(ServiceID) ON  DELETE CASCADE,
CONSTRAINT FK_Repair_1 FOREIGN KEY (Repair_Id)
REFERENCES Repair_service_mapping(Repair_Id) ON  DELETE CASCADE
);





CREATE TABLE Maintenance
(
ServiceID int PRIMARY KEY,
Service_Type varchar(255),
CONSTRAINT FK_Maintenance FOREIGN KEY (ServiceID)
REFERENCES Service(ServiceID) ON DELETE CASCADE
);

CREATE TABLE OrderPart(
CenterID int,
OrderID int PRIMARY KEY,
Order_date DATE,
Part_ID  int NOT NULL,
quantity int,
Status varchar(255),
Expected_date date,
Actual_date date,
CHECK(quantity>25),
CONSTRAINT FK_PtID FOREIGN KEY (Part_ID,CenterID)
REFERENCES Inventory(PartId,CenterId) ON DELETE CASCADE
);

CREATE TABLE Notification
(
OrderID int,
NotificationID int ,
Notification_date date,
description varchar(255),
CONSTRAINT PK_Notification PRIMARY KEY (OrderID, NotificationID),
CONSTRAINT FK_Notification FOREIGN KEY (OrderID)
REFERENCES OrderPart (OrderID) ON DELETE CASCADE
);

CREATE TABLE Distributor
(
DistID int PRIMARY KEY ,
Delivery_Window  int,
DistName varchar(50)
);

CREATE TABLE Appointment
(
AppointmentNo int PRIMARY KEY,
App_Date DATE,
start_time varchar(255)
);

CREATE TABLE Login
(
LoginID int PRIMARY KEY,
Password  varchar(255) NOT NULL,
Role varchar(255) NOT NULL
);




CREATE TABLE  Has
(
PartID int,
CenterID int ,
CONSTRAINT PK_PID PRIMARY KEY (PartID, CenterID),
CONSTRAINT FK_PID FOREIGN KEY (PartID,centerId)
REFERENCES Inventory(PartID,centerId) ON DELETE CASCADE
);



CREATE TABLE GoesTo (
    LicensePlateID varchar(255),
    CustID int,
     CenterID int,    
    CONSTRAINT PK_GOESTO PRIMARY KEY (LicensePlateID, CustID,  CenterID),
   CONSTRAINT FK_GOESTO_LicensePlateID FOREIGN KEY (LicensePlateID) REFERENCES Cars(LicensePlateID)
    ON DELETE CASCADE,
  CONSTRAINT FK_GOESTO_CustID  FOREIGN KEY (CustID) REFERENCES CUSTOMER(CustID)
    ON DELETE CASCADE,     
    CONSTRAINT FK_GOESTO_CenterID FOREIGN KEY (CenterID) REFERENCES  Service_Center ( CenterID)
    ON DELETE CASCADE          
);


CREATE TABLE Lates (    
    NotificationID int,
    OrderID int,
    CONSTRAINT PK_Lates PRIMARY KEY (NotificationID , OrderID),
    CONSTRAINT FK_Lates_NotificationID FOREIGN KEY (NotificationID,OrderId) REFERENCES Notification(NotificationID , OrderID)
    ON DELETE CASCADE          
);


CREATE TABLE Makes(    
CenterID_sender int,
CenterId_receiver int,
OrderID int,
DistID int,
CONSTRAINT PK_Makes PRIMARY KEY ( CenterId_receiver,OrderID),
CONSTRAINT FK_Makes_CenterID_send FOREIGN KEY ( CenterID_sender) REFERENCES Service_Center( CenterID)
ON DELETE CASCADE,
CONSTRAINT FK_Makes_CenterID_rec FOREIGN KEY ( CenterId_receiver) REFERENCES Service_Center( CenterID)
ON DELETE CASCADE,
CONSTRAINT FK_Makes_OrderID  FOREIGN KEY (OrderID) REFERENCES OrderPart(OrderID)
ON DELETE CASCADE,
CONSTRAINT FK_Makes_DistID FOREIGN KEY (DistID) REFERENCES Distributor(DistID)
ON DELETE CASCADE           
);

CREATE TABLE Contains(
    BasicServiceID int,
    ServiceID int,
    CONSTRAINT PK_Contains_PID PRIMARY KEY (BasicServiceID,ServiceID),
    CONSTRAINT FK_Contains_BasicServiceID FOREIGN KEY (BasicServiceID,ServiceID) REFERENCES BasicService(BasicServiceID,ServiceID)
    ON DELETE CASCADE
);

CREATE TABLE Provides ( 
    CenterID int,
    ServiceID int,  
    CONSTRAINT PK_Provides PRIMARY KEY ( CenterID , ServiceID),
    CONSTRAINT FK_Provides_CenterID FOREIGN KEY ( CenterID) REFERENCES Service_Center( CenterID)
    ON DELETE CASCADE,
    CONSTRAINT FK_Provides_ServiceID FOREIGN KEY (ServiceID) REFERENCES Service(ServiceID)
    ON DELETE CASCADE           
);





CREATE TABLE LinkedTo(
    Appointmentno int,
    ServiceID int,
    CONSTRAINT PK_LinedTo PRIMARY KEY (Appointmentno, ServiceID ),
    CONSTRAINT FK_Contains_ServiceID FOREIGN KEY (ServiceID) REFERENCES Service(ServiceID)
    ON DELETE CASCADE,
    CONSTRAINT FK_Contains_Appointmentno  FOREIGN KEY (Appointmentno) REFERENCES Appointment(Appointmentno)
    ON DELETE CASCADE       
);



CREATE TABLE Uses(
    PartID int,
    ServiceID int,
     CenterID int,
    CONSTRAINT PK_Uses PRIMARY KEY (PartID, ServiceID, CenterID ),
   CONSTRAINT FK_Uses_ServiceID FOREIGN KEY (ServiceID) REFERENCES Service(ServiceID)
    ON DELETE CASCADE,
    CONSTRAINT FK_Uses_PIDCID FOREIGN KEY (PartID, CenterID) REFERENCES Inventory(PartID, CenterID)
    ON DELETE CASCADE      
);


CREATE TABLE Request(
AppointmentNo int,
LicensePlateID varchar(255),
Current_Mileage float,
Mechanic_Preference varchar(255),
CONSTRAINT PK_Request PRIMARY KEY (Appointmentno,LicensePlateID),
CONSTRAINT FK_Request_Appointmentno FOREIGN KEY (Appointmentno) REFERENCES Appointment(Appointmentno)
ON DELETE CASCADE,
CONSTRAINT FK_Request_LicensePlateID FOREIGN KEY (LicensePlateID) REFERENCES Cars(LicensePlateID)
ON DELETE CASCADE
);


CREATE TABLE Fulfills(
OrderID int,
PartID int,
CenterID int,
CONSTRAINT PK_Fulfill PRIMARY KEY (OrderID, PartID, CenterID),
CONSTRAINT FK_Fulfill_OrderID FOREIGN KEY (OrderID) REFERENCES OrderPart (OrderID)
ON DELETE CASCADE,
CONSTRAINT FK_Fulfill_PIDCID FOREIGN KEY (PartID,CenterID) REFERENCES Inventory(PartID,CenterID)
ON DELETE CASCADE
);


CREATE TABLE WorksAt(
EmpID int,
CenterID int,
CONSTRAINT PK_WorksAt PRIMARY KEY (EmpID, CenterID),
CONSTRAINT FK_WorksAt_EmpID FOREIGN KEY (EmpID) REFERENCES Employee(EmpID)
ON DELETE CASCADE,
CONSTRAINT FK_WorksAt_Service_CenterID FOREIGN KEY (CenterID) REFERENCES Service_Center(CenterID)
ON DELETE CASCADE
);










