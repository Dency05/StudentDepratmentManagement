package com.example.studentdepartmentmanagement.controller;

import com.example.studentdepartmentmanagement.decorator.*;
import com.example.studentdepartmentmanagement.exception.EmptyException;
import com.example.studentdepartmentmanagement.exception.NotFoundException;
import com.example.studentdepartmentmanagement.model.*;
import com.example.studentdepartmentmanagement.reponse.StudentResponse;
import com.example.studentdepartmentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    StudentService studentService;
    @Autowired
    GeneralHelper generalHelper;

    @RequestMapping(name = "addorupdateStudent", value = "/add", method = RequestMethod.POST)
    public DataResponse<StudentModel> addorupdateStudent(@RequestBody Student student, @RequestParam(required = false) DepartmentType departmentType, @RequestParam(required = false) String id) throws Exception {
        DataResponse<StudentModel> dataResponse = new DataResponse<>();
        try {
            dataResponse.setData(studentService.addorupdateStudent(student, departmentType, id));
            dataResponse.setStatus(Response.getSuccessfullyResponse());
        } catch (NotFoundException e) {
            dataResponse.setStatus(Response.getNotFoundResponse(e.getMessage()));
        }
        return dataResponse;
    }

    @RequestMapping(name = "getAllStudent", value = "/getAll", method = RequestMethod.GET)
    public ListResponse<StudentModel> getAllStudent() throws NotFoundException {
        ListResponse<StudentModel> listResponse = new ListResponse<>();
        try {
            listResponse.setData(studentService.getAllStudent());
            listResponse.setStatus(Response.getResponse());
        } catch (EmptyException e) {
            listResponse.setStatus(Response.getEmptyStudentException(e.getMessage()));
        }
        return listResponse;
    }

    @RequestMapping(name = "getStudent", value = "/get", method = RequestMethod.GET)
    public DataResponse<Student> getStudent(@RequestParam String id) throws Exception {
        DataResponse<Student> dataResponse = new DataResponse<>();
        try {
            dataResponse.setData(studentService.getStudent(id));
            dataResponse.setStatus(Response.getOkResponse());
        } catch (NotFoundException e) {
            dataResponse.setStatus(Response.getNotFoundResponse(e.getMessage()));
        }
        return dataResponse;
    }

    @RequestMapping(name = "deleteStudent", value = "/delete", method = RequestMethod.GET)
    public DataResponse<Object> deleteStudent(@RequestParam String id) throws Exception {
        DataResponse<Object> dataResponse = new DataResponse<>();
        try {
            dataResponse.setData(studentService.deleteStudent(id));
            dataResponse.setStatus(Response.getDeletedid());
        } catch (NotFoundException e) {
            dataResponse.setStatus(Response.getNotFoundResponse(e.getMessage()));
        }
        return dataResponse;
    }

    @RequestMapping(name = "deleteAll", value = "/deleteAll", method = RequestMethod.GET)
    public DataResponse<Object> deleteAll() {
        DataResponse<Object> dataResponse = new DataResponse<>();
        dataResponse.setData(studentService.deleteAll());
        dataResponse.setStatus(Response.getDeletedid());
        return dataResponse;
    }

    @RequestMapping(name = "findfieldwithsorting", value = "/sort", method = RequestMethod.GET)
    public ListResponse<StudentModel> findfieldwithsorting(@RequestParam String field) {
        ListResponse<StudentModel> listResponse = new ListResponse<>();
        try {
            listResponse.setData(studentService.findfieldwithsorting(field));
            listResponse.setStatus(Response.sorting());
        } catch (EmptyException e) {
            listResponse.setStatus(Response.getEmptyStudentException(e.getMessage()));
        }
        return listResponse;
    }

    @RequestMapping(name = "findStudentwithPagination", value = "/pagination", method = RequestMethod.GET)
    public Page<StudentModel> findStudentwithPagination(@RequestParam int offset, @RequestParam int pagesize) {
        return studentService.findStudentwithPagination(offset, pagesize);
    }

    @RequestMapping(name = "findStudentwithPaginationAndSorting", value = "/paginationAndsorting", method = RequestMethod.GET)
    public Page<StudentModel> findStudentwithPaginationAndSorting(@RequestParam(required = false) int offset,
                                                                  @RequestParam(required = false) int pagesize, @RequestParam(required = false) String field) {
        return studentService.findStudentwithPaginationAndSorting(offset, pagesize, field);
    }

    @RequestMapping(name = "getStudentByPaginatation", value = "/getAll/filter", method = RequestMethod.POST)
    public PageResponse<StudentResponse> getStudentByPaginatation(@RequestBody FilterSortRequest<StudentFilter, StudentSortBy> filterSortRequest) {
        PageResponse<StudentResponse> pageResponse = new PageResponse<>();
        StudentFilter filter = filterSortRequest.getFilter();
        Page<StudentResponse> studentResponses = studentService.getAllStudentWithFilterAndSort(filter,
                filterSortRequest.getSort(),
                generalHelper.getPagination(filterSortRequest.getPage().getPage(), filterSortRequest.getPage().getLimit()));
        pageResponse.setData(studentResponses);
        pageResponse.setStatus(Response.getOhkResponse());
        return pageResponse;

    }
}
