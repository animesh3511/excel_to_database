package com.example.employee.service;

import com.example.employee.model.request.EmployeeRequest;

public interface EmployeeService {


    Object saveOrUpdate(EmployeeRequest employeeRequest);
}
