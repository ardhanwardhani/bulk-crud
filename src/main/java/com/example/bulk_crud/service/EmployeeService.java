package com.example.bulk_crud.service;

import com.example.bulk_crud.dto.request.BulkActionRequest;
import com.example.bulk_crud.dto.request.EmployeeRequest;
import com.example.bulk_crud.dto.request.EmployeeUpdateRequest;
import com.example.bulk_crud.dto.response.EmployeeResponse;
import com.example.bulk_crud.model.Employee;
import com.example.bulk_crud.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeResponse> getAllEmployees(){
        List<Employee> employeeList = employeeRepository.findAll();

        return employeeList.stream()
                .map(employee -> new EmployeeResponse(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getBranch()))
                .collect(Collectors.toList());
    }

    public EmployeeResponse getEmployee(Long id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isEmpty()){
            throw new RuntimeException("Employee Not Found");
        }

        Employee employee = optionalEmployee.get();
        return new EmployeeResponse(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getBranch());
    }

    public EmployeeResponse createEmployee(EmployeeRequest request){
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setBranch(request.getBranch());
        employee.setCreatedAt(new Date());
        Employee savedEmployee = employeeRepository.save(employee);

        return new EmployeeResponse(savedEmployee.getId(), savedEmployee.getFirstName(), savedEmployee.getLastName(), savedEmployee.getBranch());
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isEmpty()){
            throw new RuntimeException("Employee not found");
        }

        Employee employee = optionalEmployee.get();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setBranch(request.getBranch());
        employee.setUpdatedAt(new Date());
        Employee savedEmployee = employeeRepository.save(employee);

        return new EmployeeResponse(savedEmployee.getId(), savedEmployee.getFirstName(), savedEmployee.getLastName(), savedEmployee.getBranch());
    }

    public void deleteEmployee(Long id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isEmpty()){
            throw new RuntimeException("Employee not found");
        }

        employeeRepository.deleteById(id);
    }

    @Transactional
    public HashMap<String, String> bulkAction(BulkActionRequest request){
        HashMap<String, String> response = new HashMap<>();

        List<EmployeeRequest> listCreate = request.getListCreate();
        if (listCreate != null){
            for (EmployeeRequest createRequest: listCreate){
                Employee employee = new Employee();
                employee.setFirstName(createRequest.getFirstName());
                employee.setLastName(createRequest.getLastName());
                employee.setBranch(createRequest.getBranch());
                employee.setCreatedAt(new Date());
                employeeRepository.save(employee);
            }
            response.put("created", listCreate.size() + " employees created");
        }

        List<EmployeeUpdateRequest> listUpdate = request.getListUpdate();
        if (listUpdate != null){
            for (EmployeeUpdateRequest updateRequest: listUpdate){
                Employee employee = employeeRepository.findById(updateRequest.getId())
                        .orElseThrow(() -> new RuntimeException("Employee with ID " + updateRequest.getId() + " not found"));
                employee.setFirstName(updateRequest.getFirstName());
                employee.setLastName(updateRequest.getLastName());
                employee.setBranch(updateRequest.getBranch());
                employee.setUpdatedAt(new Date());
                employeeRepository.save(employee);
            }
            response.put("updated", listUpdate.size() + " employees updated");
        }

        List<Long> listDelete = request.getListDelete();
        if (listDelete != null){
//            for (Long id: listDelete){
//                employeeRepository.deleteById(id);
//            }
            employeeRepository.deleteAllById(listDelete);
            response.put("deleted", listDelete.size() + " employees deleted");
        }

        return response;
    }
}
