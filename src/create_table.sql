CREATE TABLE Service_Center (
    CenterID varchar(255) NOT NULL PRIMARY KEY,
    Center_Name varchar(255) NOT NULL,
    Center_Address varchar(255),
    Center_Tele varchar(255)
);

CREATE TABLE Inventory (
PartID int ,
Part_Name varchar(255),
Car_model varchar(100),
CenterID varchar(255),
Current_quantity varchar(255),
Threshold_Min int,
Min_Order int,
CONSTRAINT PK_Inventory PRIMARY KEY ( CenterID, PartID,Car_model),
CONSTRAINT FK_Inventory FOREIGN KEY (CenterID) REFERENCES Service_Center(CenterID) ON  DELETE CASCADE
);

CREATE TABLE Customer (
    CustID int PRIMARY KEY,
    Cust_Name varchar(255),
    Cust_email varchar(255) NOT NULL UNIQUE,
    Cust_address varchar(255),
    Cust_Phone number(10)
);



CREATE TABLE Employee (
EmpID varchar(255) PRIMARY KEY,
Role int,
Emp_Name varchar(255),
Emp_email varchar(255) NOT NULL UNIQUE,
Emp_address varchar(255),
Emp_Phone varchar(20),
Emp_start_date varchar(50)
);


CREATE TABLE Monthly_Paid_Emp (
    EmpID varchar(255),
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
EmpID varchar(255) PRIMARY KEY,
Wages int,
Hours_Worked int CHECK(Hours_Worked<=11*15),
CONSTRAINT FK_EMPID FOREIGN KEY (EmpID)
REFERENCES Employee(EmpID) ON  DELETE CASCADE
);



CREATE TABLE Cars
(
LicensePlateID  varchar(255) PRIMARY KEY,
Car_Type varchar(255),
Date_Purchase varchar(255),
Last_Mileage int,
Type_Recent_Service varchar(255),
Date_Recent_Service Varchar(50)
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
CenterID varchar(255),
OrderID int PRIMARY KEY,
Order_date varchar(255),
Part_ID  int NOT NULL,
quantity int,
Status varchar(255),
Expected_date varchar(255),
Actual_date varchar(255),
car_make varchar(255),
CONSTRAINT FK_PtID FOREIGN KEY (Part_ID,CenterID,car_make)
REFERENCES Inventory(PartId,CenterId, Car_model) ON DELETE CASCADE
);

CREATE TABLE Notification
(
OrderID int,
NotificationID int ,
Notification_date varchar(255),
description varchar(255),
CONSTRAINT PK_Notification PRIMARY KEY (OrderID, NotificationID),
CONSTRAINT FK_Notification FOREIGN KEY (OrderID)
REFERENCES OrderPart (OrderID) ON DELETE CASCADE
);

CREATE TABLE Distributor
(
DistID varchar(255),
DistName varchar(50),
Delivery_Window  int,
PartID int,
CONSTRAINT PK_Distributor PRIMARY KEY (DistID, PartID)
);



CREATE TABLE Appointment
(
AppointmentNo int PRIMARY KEY,
App_Date varchar(255),
start_slot int,
end_slot int
);

CREATE TABLE Login
(
LoginID varchar(255) PRIMARY KEY,
Password  varchar(255) NOT NULL,
Role varchar(255) NOT NULL
);


CREATE TABLE GoesTo (
   LicensePlateID varchar(255),
   CustID int,
   CenterID varchar(255),
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
senderiID varchar(255),
CenterId_receiver varchar(255),
OrderID int,
partID int,
CONSTRAINT PK_Makes PRIMARY KEY ( CenterId_receiver,OrderID),
CONSTRAINT FK_Makes_OrderID  FOREIGN KEY (OrderID) REFERENCES OrderPart(OrderID)
ON DELETE CASCADE
);



CREATE TABLE Provides (
    CenterID varchar(255),
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




CREATE TABLE WorksAt(
EmpID varchar(255),
CenterID varchar(255),
CONSTRAINT PK_WorksAt PRIMARY KEY (EmpID, CenterID),
CONSTRAINT FK_WorksAt_EmpID FOREIGN KEY (EmpID) REFERENCES Employee(EmpID)
ON DELETE CASCADE,
CONSTRAINT FK_WorksAt_Service_CenterID FOREIGN KEY (CenterID) REFERENCES Service_Center(CenterID)
ON DELETE CASCADE
);


CREATE TABLE Parts_Mapping (
PartID int ,
Part_Name varchar(255),
Car_Company varchar(255) NOT NULL,
Price int,
Warranty int,
CONSTRAINT PK_Parts_Mapping PRIMARY KEY (PartID, Car_Company)
);
