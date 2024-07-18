package com.example.bulk_crud.controller;

import com.example.bulk_crud.dto.request.BulkActionRequest;
import com.example.bulk_crud.dto.request.EmployeeRequest;
import com.example.bulk_crud.dto.response.EmployeeResponse;
import com.example.bulk_crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(){
        List<EmployeeResponse> employeeResponses = employeeService.getAllEmployees();

        return ResponseEntity.ok(employeeResponses);
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Long id){
        EmployeeResponse response = employeeService.getEmployee(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest request){
        EmployeeResponse response = employeeService.createEmployee(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest request){
        EmployeeResponse response = employeeService.updateEmployee(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<HashMap<String, String>> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Employee deleted successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bulk-action")
    public ResponseEntity<HashMap<String, String>> bulkAction(@RequestBody BulkActionRequest request){
        HashMap<String, String> response = employeeService.bulkAction(request);
        return ResponseEntity.ok(response);
    }
}
