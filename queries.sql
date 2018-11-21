Receptionist->profile->view profile
SELECT E.EmpID, E.Emp_Name, E.Emp_address, E.Emp_email, E.Emp_Phone, S.Center_Name, E.Role, E.Start_Date, M.Salary
FROM Employee E, Monthly_Paid_Emp M, WorksAt W, Service_Center S
WHERE
W.CenterID = S.CenterID AND
E.EmpID = M.EmpID

Receptionist->profile->update_profile

UPDATE Employee
SET Emp_Name =?
WHERE EmpID =?

UPDATE Employee
SET Emp_address =?
WHERE EmpID =?

UPDATE Employee
SET Emp_email =?
WHERE EmpID =?

UPDATE Employee
SET Emp_Phone =?
WHERE EmpID =?

UPDATE Login
SET Password =?
WHERE LoginID =?

Receptionist->viewCustomer

SELECT Cu.CustID, Cu.Cust_Name, Cu.Cust_email, Cu.Cust_address, Cu.Cust_Phone, C.LicensePlateID, C.Car_Type, C.Date_Purchase, C.Last_Mileage, C.Type_Recent_Service, C.OwnedBy, C.Date_Recent_Service
FROM Customer Cu, Cars C, GoesTo G
WHERE
Cust_email =? AND Cu.CustID = G.CustID AND G.LicensePlateID = C.LicensePlateID

Receptionist->registerCar(Mohit?Aditya)
Receptionist->scheduleService
Receptionist-> rescheduleService

Receptionist->invoices
SELECT S.ServiceID, A.start_slot, A., R.LicensePlateID, R.Mechanic_Preferance, 
