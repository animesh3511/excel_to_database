package com.example.employee.serviceimpl;

import com.example.employee.model.Employee;
import com.example.employee.model.request.EmployeeRequest;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

   @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Object saveOrUpdate(EmployeeRequest employeeRequest) {

        List<Employee> list = null;

        if (employeeRepository.existsById(employeeRequest.getEmpId()))
        {


            Employee employee = employeeRepository.findById(employeeRequest.getEmpId()).get();

            employee.setEmpId(employeeRequest.getEmpId());

            employee.setSalary(employeeRequest.getSalary());

            employee.setUserName(employeeRequest.getUserName());

            for (String s : employeeRequest.getShares())
            {

             employee.setShares(s);


             employeeRepository.save(employee);
            }


            //employeeRepository.save(employee);
            return "Employee updated succesfully";


        }
        else
        {

          Employee employee = new Employee();


          employee.setEmpId(employeeRequest.getEmpId());
          employee.setUserName(employeeRequest.getUserName());
          employee.setSalary(employeeRequest.getSalary());

          for (String s : employeeRequest.getShares())
          {

              employee.setShares(s);



              employeeRepository.save(employee);
          }



//          employeeRepository.save(employee);

          return "Employee saved";

        }


    }
}

