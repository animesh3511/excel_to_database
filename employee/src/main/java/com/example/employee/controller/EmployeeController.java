package com.example.employee.controller;

import com.example.employee.model.request.EmployeeRequest;
import com.example.employee.model.response.CustomEntityResponse;
import com.example.employee.model.response.EntityResponse;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<?>saveOrUpdate(@RequestBody EmployeeRequest employeeRequest)
    {

        try{

            return new ResponseEntity(new EntityResponse(employeeService.saveOrUpdate(employeeRequest),0), HttpStatus.OK);

        }catch (Exception e)
        {

          return new ResponseEntity(new CustomEntityResponse(e.getMessage(),-1),HttpStatus.BAD_REQUEST);

        }



    }




}
