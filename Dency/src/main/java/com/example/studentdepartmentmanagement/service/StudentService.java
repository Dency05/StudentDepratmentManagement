package com.example.studentdepartmentmanagement.service;

import com.example.studentdepartmentmanagement.decorator.FilterSortRequest;
import com.example.studentdepartmentmanagement.decorator.Student;
import com.example.studentdepartmentmanagement.decorator.StudentFilter;
import com.example.studentdepartmentmanagement.exception.NotFoundException;
import com.example.studentdepartmentmanagement.model.DepartmentType;
import com.example.studentdepartmentmanagement.model.StudentModel;
import com.example.studentdepartmentmanagement.model.StudentSortBy;
import com.example.studentdepartmentmanagement.reponse.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

    List<StudentModel> getAllStudent() throws NotFoundException;

    Student getStudent(String id) throws Exception;

    Object deleteStudent(String id) throws Exception;

    Object deleteAll();

    StudentModel addorupdateStudent(Student student,DepartmentType departmentType, String id) throws Exception;

    void deleteAllStudent(List<StudentModel> studentModels);

    List<StudentModel> findfieldwithsorting(String field);

    Page<StudentModel> findStudentwithPagination(int offset,int pagesize);

    Page<StudentModel> findStudentwithPaginationAndSorting(int offset, int pagesize, String field);

    Page<StudentResponse> getAllStudentWithFilterAndSort(StudentFilter filter, FilterSortRequest.SortRequest<StudentSortBy> sort, PageRequest pagination);
}
