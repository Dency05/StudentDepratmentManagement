package com.example.studentdepartmentmanagement.repository;

import com.example.studentdepartmentmanagement.decorator.FilterSortRequest;
import com.example.studentdepartmentmanagement.decorator.StudentFilter;
import com.example.studentdepartmentmanagement.model.StudentSortBy;
import com.example.studentdepartmentmanagement.reponse.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface StudentRepositoryCustom {
    Page<StudentResponse>findAllStudentByPagination(StudentFilter studentFilter,
                                                    FilterSortRequest.SortRequest<StudentSortBy>sort, PageRequest pagination);

}
