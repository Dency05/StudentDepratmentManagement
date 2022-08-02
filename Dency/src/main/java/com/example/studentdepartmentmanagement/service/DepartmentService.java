package com.example.studentdepartmentmanagement.service;

import com.example.studentdepartmentmanagement.decorator.Department;
import com.example.studentdepartmentmanagement.decorator.DepartmentFilter;
import com.example.studentdepartmentmanagement.decorator.FilterSortRequest;
import com.example.studentdepartmentmanagement.exception.NotFoundException;
import com.example.studentdepartmentmanagement.model.DepartmentModel;
import com.example.studentdepartmentmanagement.model.DepartmentSortBy;
import com.example.studentdepartmentmanagement.reponse.DepartmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface DepartmentService {

    List<DepartmentModel> getAllDepartment() throws NotFoundException;

    Department getDepartment(String id) throws Exception;

    void deleteDepartment(String id, String DepartmentId) throws Exception;

    void deleteAll();

    DepartmentModel addOrUpdateDepartment(Department department, String id) throws Exception;

    Page<DepartmentResponse> getAllDepartmentWithFilterAndSort(DepartmentFilter departmentFilter, FilterSortRequest.SortRequest
            <DepartmentSortBy>sort, PageRequest pagination);
}
