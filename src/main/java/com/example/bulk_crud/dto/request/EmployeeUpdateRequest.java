package com.example.bulk_crud.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeUpdateRequest {
    @NotNull
    private Long id;

    @NotEmpty(message = "First Name must not be null.")
    @Size(min = 5, max = 50, message = "This field should have 5-50 character.")
    private String firstName;

    @NotEmpty(message = "Last Name must not be null.")
    @Size(min = 5, max = 50, message = "This field should have 5-50 character.")
    private String lastName;

    @NotEmpty(message = "Branch must not be null.")
    @Size(min = 5, max = 50, message = "This field should have 5-50 character.")
    private String branch;
}
