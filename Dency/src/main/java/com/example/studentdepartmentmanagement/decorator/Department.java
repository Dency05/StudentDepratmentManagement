package com.example.studentdepartmentmanagement.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @NotNull(message = "Name may not be null")
    String departmentName;

    String Description;
}
