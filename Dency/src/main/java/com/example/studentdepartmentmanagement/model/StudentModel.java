package com.example.studentdepartmentmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Student")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentModel {

    @Id
    String id;
    String StudentName;
    String age;
    String departmentId;
    DepartmentType departmentType;

    @JsonIgnore
    boolean softDelete =false;
}

