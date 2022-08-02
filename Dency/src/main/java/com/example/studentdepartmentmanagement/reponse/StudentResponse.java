package com.example.studentdepartmentmanagement.reponse;

import com.example.studentdepartmentmanagement.model.DepartmentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    @Id
    String id;
    String StudentName;
    String age;
    String departmentId;
    DepartmentType departmentType;

    @JsonIgnore
    boolean softDelete =false;
}
