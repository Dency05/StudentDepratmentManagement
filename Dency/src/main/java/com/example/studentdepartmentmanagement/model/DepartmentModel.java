package com.example.studentdepartmentmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;

@Document(collection = "Department")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentModel {

    @Id
    String id;

    String departmentName;

    String Description;

    @JsonIgnore
    boolean softDelete = false;
}
