package com.example.studentdepartmentmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public enum DepartmentSortBy {
    DEPARTMENT_NAME("departmentName");

    @JsonIgnore
    private String value;

    DepartmentSortBy(String value) {
        this.value = value;
    }
}
