CREATE TABLE Service_Center (
    CenterID varchar(8) NOT NULL PRIMARY KEY,
    Center_Name varchar(20) NOT NULL,
    Center_Address varchar(100),
    Center_Tele varchar(15)
);

CREATE TABLE Inventory (
PartID int ,
Part_Name varchar(30),
Car_model varchar(15),
CenterID varchar(8),
Current_quantity int,
Threshold_Min int,
Min_Order int,
CONSTRAINT PK_Inventory PRIMARY KEY ( CenterID, PartID,Car_model),
CONSTRAINT FK_Inventory FOREIGN KEY (CenterID) REFERENCES Service_Center(CenterID) ON  DELETE CASCADE
);

CREATE TABLE Customer (
    CustID int PRIMARY KEY,
    Cust_Name varchar(30),
    Cust_email varchar(30) NOT NULL UNIQUE,
    Cust_address varchar(50),
    Cust_Phone number(15)
);



CREATE TABLE Employee (
EmpID varchar(15) PRIMARY KEY,
Role int,
Emp_Name varchar(30),
Emp_email varchar(40) NOT NULL UNIQUE,
Emp_address varchar(50),
Emp_Phone varchar(20),
Emp_start_date varchar(50)
);


CREATE TABLE Monthly_Paid_Emp (
    EmpID varchar(15),
    Salary varchar(15),
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
    Basic_Service_Name varchar(40),
    Labour_Charge_Per_Hour float,
    Hours_Required float,
    CONSTRAINT PK_BasicService PRIMARY KEY ( BasicServiceID)
);

Create table PartRequired_car_model (
BasicServiceID int,
car_model varchar(35),
Part_Required int,
Part_quantity_required int,
CONSTRAINT PK_PartRequired PRIMARY KEY ( BasicServiceID, car_model),
CONSTRAINT FK_PartRequired FOREIGN KEY (BasicServiceID)REFERENCES BasicService(BasicServiceID) ON  DELETE CASCADE
);

Create table Basic_service_mapping (
car_model varchar(35),
Service_Type varchar(10),
miles int,
Basic_services_used varchar(40),
CONSTRAINT PK_Basic_service_mapping PRIMARY KEY (car_model,Service_Type)
);

create table Repair_service_mapping (
repair_id int primary key,
repair_name varchar(50),
Dignostic_solution varchar(80),
Diagnostic_fee int,
Basic_services_used varchar(40)
);



CREATE TABLE Mechanic_Emp
(
EmpID varchar(15) PRIMARY KEY,
Wages int,
Hours_Worked int CHECK(Hours_Worked<=11*15),
CONSTRAINT FK_EMPID FOREIGN KEY (EmpID)
REFERENCES Employee(EmpID) ON  DELETE CASCADE
);



CREATE TABLE Cars
(
LicensePlateID  varchar(20) PRIMARY KEY,
Car_Type varchar(40),
Date_Purchase varchar(15),
Last_Mileage int,
Type_Recent_Service varchar(40),
Date_Recent_Service Varchar(15)
);



CREATE TABLE Repair
(
Repair_Id int,
ServiceID int PRIMARY KEY,
Problem_Cause varchar(50),
Diagnostic_Fees float,
CONSTRAINT FK_Repair FOREIGN KEY (ServiceID)
REFERENCES Service(ServiceID) ON  DELETE CASCADE,
CONSTRAINT FK_Repair_1 FOREIGN KEY (Repair_Id)
REFERENCES Repair_service_mapping(Repair_Id) ON  DELETE CASCADE
);



CREATE TABLE Maintenance
(
ServiceID int PRIMARY KEY,
Service_Type varchar(5),
CONSTRAINT FK_Maintenance FOREIGN KEY (ServiceID)
REFERENCES Service(ServiceID) ON DELETE CASCADE
);

CREATE TABLE OrderPart(
CenterID varchar(10),
OrderID int PRIMARY KEY,
Order_date varchar(15),
Part_ID  int NOT NULL,
quantity int,
Status varchar(20),
Expected_date varchar(15),
Actual_date varchar(15),
car_make varchar(30),
CONSTRAINT FK_PtID FOREIGN KEY (Part_ID,CenterID,car_make)
REFERENCES Inventory(PartId,CenterId, Car_model) ON DELETE CASCADE
);

CREATE TABLE Notification
(
OrderID int,
NotificationID int ,
Notification_date varchar(15),
description varchar(80),
CONSTRAINT PK_Notification PRIMARY KEY (OrderID, NotificationID),
CONSTRAINT FK_Notification FOREIGN KEY (OrderID)
REFERENCES OrderPart (OrderID) ON DELETE CASCADE
);

CREATE TABLE Distributor
(
DistID varchar(10),
DistName varchar(20),
Delivery_Window  int,
PartID int,
CONSTRAINT PK_Distributor PRIMARY KEY (DistID, PartID)
);



CREATE TABLE Appointment
(
AppointmentNo int PRIMARY KEY,
App_Date varchar(15),
start_slot int,
end_slot int,
mechanic varchar2(30)
);

CREATE TABLE Login
(
LoginID varchar(30) PRIMARY KEY,
Password  varchar(30) NOT NULL,
Role varchar(2) NOT NULL
);


CREATE TABLE GoesTo (
   LicensePlateID varchar(20),
   CustID int,
   CenterID varchar(15)
);


CREATE TABLE Lates (
    NotificationID int,
    OrderID int,
    CONSTRAINT PK_Lates PRIMARY KEY (NotificationID , OrderID),
    CONSTRAINT FK_Lates_NotificationID FOREIGN KEY (NotificationID,OrderId) REFERENCES Notification(NotificationID , OrderID)
    ON DELETE CASCADE
);


CREATE TABLE Makes(
senderID varchar(15),
CenterId_receiver varchar(15),
OrderID int,
partID int,
CONSTRAINT PK_Makes PRIMARY KEY ( CenterId_receiver,OrderID),
CONSTRAINT FK_Makes_OrderID  FOREIGN KEY (OrderID) REFERENCES OrderPart(OrderID)
ON DELETE CASCADE
);



CREATE TABLE Provides (
    CenterID varchar(15),
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
    CONSTRAINT FK_Contains_ServiceID FOREIGN KEY (ServiceID) REFERENCES Service(ServiceID),
    CONSTRAINT FK_Contains_Appointmentno  FOREIGN KEY (Appointmentno) REFERENCES Appointment(Appointmentno)
);





CREATE TABLE Request(
AppointmentNo int,
LicensePlateID varchar(20),
Current_Mileage float,
Mechanic_Preference varchar(40),
CONSTRAINT PK_Request PRIMARY KEY (Appointmentno,LicensePlateID),
CONSTRAINT FK_Request_Appointmentno FOREIGN KEY (Appointmentno) REFERENCES Appointment(Appointmentno)
ON DELETE CASCADE,
CONSTRAINT FK_Request_LicensePlateID FOREIGN KEY (LicensePlateID) REFERENCES Cars(LicensePlateID)
ON DELETE CASCADE
);




CREATE TABLE WorksAt(
EmpID varchar(15),
CenterID varchar(15),
CONSTRAINT PK_WorksAt PRIMARY KEY (EmpID, CenterID),
CONSTRAINT FK_WorksAt_EmpID FOREIGN KEY (EmpID) REFERENCES Employee(EmpID)
ON DELETE CASCADE,
CONSTRAINT FK_WorksAt_Service_CenterID FOREIGN KEY (CenterID) REFERENCES Service_Center(CenterID)
ON DELETE CASCADE
);


CREATE TABLE Parts_Mapping (
PartID int ,
Part_Name varchar(30),
Car_Company varchar(20) NOT NULL,
Price int,
Warranty int,
CONSTRAINT PK_Parts_Mapping PRIMARY KEY (PartID, Car_Company)
);


create or replace procedure notifyOrder
is 
notifyId number;
Cursor c1 is
select ORDERID from orderpart where To_DATE(expected_date,'DD-MM-YYYY')<(select SYSDATE from dual) and Actual_Date is null;
begin
FOR ord in c1
   LOOP
	  select max(NOTIFICATIONID)+1 into notifyId from notification;
      insert into notification values (ord.ORDERID,notifyId,To_char(SYSDATE,'DD-MM_YYYY'),'Order is due. Please contact sender');
	  commit;
   END LOOP;
End notifyOrder;
/

CREATE OR REPLACE TRIGGER appointment1
AFTER DELETE on service
FOR EACH ROW
DECLARE
value integer;
BEGIN
select a.appointmentno into  value from linkedto l, appointment a WHERE l.appointmentno=a.appointmentno AND l.serviceid= :old.serviceid;
DELETE from linkedto where appointmentno = value; 
DELETE FROM appointment where appointmentno = value;
END;
/


