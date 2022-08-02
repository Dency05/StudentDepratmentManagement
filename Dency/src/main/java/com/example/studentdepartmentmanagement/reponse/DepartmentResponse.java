package com.example.studentdepartmentmanagement.reponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponse {
    @Id
    String id;

    String departmentName;

    String Description;

    @JsonIgnore
    boolean softDelete = false;
}
