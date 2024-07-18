package com.example.bulk_crud.dto.request;

import lombok.Data;

import java.util.List;
@Data
public class BulkActionRequest {
    private List<EmployeeRequest> listCreate;
    private List<EmployeeUpdateRequest> listUpdate;
    private List<Long> listDelete;
}

