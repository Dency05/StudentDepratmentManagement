package com.example.studentdepartmentmanagement.repository;

import com.example.studentdepartmentmanagement.decorator.DepartmentFilter;
import com.example.studentdepartmentmanagement.decorator.FilterSortRequest;
import com.example.studentdepartmentmanagement.model.DepartmentSortBy;
import com.example.studentdepartmentmanagement.reponse.DepartmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepositoryCustom {
    Page<DepartmentResponse> findAllDepartmentByFilterAndSortAndPage(DepartmentFilter departmentFilter,
                                                                     FilterSortRequest.SortRequest<DepartmentSortBy>sort, PageRequest pagination);
}
