package com.example.studentdepartmentmanagement.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListResponse<T> {
    List<T> data;
    Response status;
}
