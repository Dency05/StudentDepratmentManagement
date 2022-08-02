package com.example.studentdepartmentmanagement.controller;

import com.example.studentdepartmentmanagement.decorator.*;
import com.example.studentdepartmentmanagement.exception.AlreadyExistException;
import com.example.studentdepartmentmanagement.exception.EmptyException;
import com.example.studentdepartmentmanagement.exception.NotFoundException;
import com.example.studentdepartmentmanagement.model.DepartmentModel;
import com.example.studentdepartmentmanagement.model.DepartmentSortBy;
import com.example.studentdepartmentmanagement.reponse.DepartmentResponse;
import com.example.studentdepartmentmanagement.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department")

public class DepartmentController {

    @Autowired
    DepartmentService departmentService;
    @Autowired
    GeneralHelper generalHelper;



    @RequestMapping(name = "addOrUpdateDepartment",value = "/add",method = RequestMethod.POST)
    public DataResponse<DepartmentModel> addOrUpdateDepartment(@RequestBody Department department,@RequestParam(required = false) String id) throws Exception {
        DataResponse<DepartmentModel> dataResponse = new DataResponse<>();
        try {
        dataResponse.setData(departmentService.addOrUpdateDepartment(department,id));
        dataResponse.setStatus(Response.getSuccessfullyResponse());
        }
        catch (NotFoundException e){
            dataResponse.setStatus(Response.getNotFoundResponse(e.getMessage()));
        }catch (AlreadyExistException e){
            dataResponse.setStatus(Response.getAlreadyExitException(e.getMessage()));
        }
        return dataResponse;
    }

    @RequestMapping(name = "getAllDepartment",value = "/getAll",method = RequestMethod.GET)
    public ListResponse<DepartmentModel> getAllDepartment(){
        ListResponse<DepartmentModel> listResponse= new ListResponse<>();
        try {
            listResponse.setData(departmentService.getAllDepartment());
            listResponse.setStatus(Response.getSuccessResponse());
        }
        catch (EmptyException e){
            listResponse.setStatus(Response.getEmptyException(e.getMessage()));
        }
        return listResponse;
    }

    @RequestMapping(name = "getDepartment",value = "/get",method = RequestMethod.GET)
    public DataResponse<Department> getDepartment(@RequestParam String id) throws Exception {
        DataResponse<Department> dataResponse = new DataResponse<>();
        try{
        dataResponse.setData(departmentService.getDepartment(id));
        dataResponse.setStatus(Response.getOkResponse());}
        catch (NotFoundException e){
            dataResponse.setStatus(Response.getNotFoundResponse(e.getMessage()));
        }
        return dataResponse;
   }

    @RequestMapping(name = "deleteDepartment",value = "/delete",method = RequestMethod.GET)
    public DataResponse<Object> deleteDepartment(@RequestParam String id,@RequestParam(required = false) String DepartmentId) throws Exception {
        DataResponse<Object> dataResponse = new DataResponse<>();
        try{
            departmentService.deleteDepartment(id,DepartmentId);
            dataResponse.setStatus(Response.getDeleteid());
        }
        catch (NotFoundException e){
            dataResponse.setStatus(Response.getNotFoundResponse(e.getMessage()));
         }
         return dataResponse;
    }

    @RequestMapping(name = "deleteAll",value = "/deleteAll",method = RequestMethod.GET)
    public DataResponse<Object> deleteAll(){
        DataResponse<Object> dataResponse= new DataResponse<>();
        departmentService.deleteAll();
        dataResponse.setStatus(Response.getDeleteid());
        return dataResponse;
    }

    @RequestMapping(name = "getDepartmentByPaginatation",value = "/getAll/filter",method = RequestMethod.POST)
    public PageResponse<DepartmentResponse> getDepartmentByPaginatation(@RequestBody FilterSortRequest<DepartmentFilter, DepartmentSortBy> filterSortRequest){
        PageResponse<DepartmentResponse> pageResponse = new PageResponse<>();
        DepartmentFilter filter = filterSortRequest.getFilter();
        Page<DepartmentResponse> departmentResponses= departmentService.getAllDepartmentWithFilterAndSort(filter,
                filterSortRequest.getSort(),
                generalHelper.getPagination(filterSortRequest.getPage().getPage(),filterSortRequest.getPage().getLimit()));
        pageResponse.setData(departmentResponses);
        pageResponse.setStatus(Response.getOhkResponse());
       return pageResponse;
    }
}
