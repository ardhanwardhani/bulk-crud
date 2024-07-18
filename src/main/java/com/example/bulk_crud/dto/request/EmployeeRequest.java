package com.example.bulk_crud.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeRequest {
    @Size(min = 5, max = 50, message = "First Name must not be null. This field should have 5-50 character.")
    private String firstName;

    @Size(min = 5, max = 50, message = "Last Name must not be null. This field should have 5-50 character.")
    private String lastName;

    @Size(min = 5, max = 50, message = "Branch must not be null. This field should have 5-50 character.")
    private String branch;
}
