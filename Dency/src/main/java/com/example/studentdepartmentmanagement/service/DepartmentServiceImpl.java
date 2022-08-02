package com.example.studentdepartmentmanagement.service;

import com.example.studentdepartmentmanagement.constant.MessageConstant;
import com.example.studentdepartmentmanagement.decorator.Department;
import com.example.studentdepartmentmanagement.decorator.DepartmentFilter;
import com.example.studentdepartmentmanagement.decorator.FilterSortRequest;
import com.example.studentdepartmentmanagement.exception.AlreadyExistException;
import com.example.studentdepartmentmanagement.exception.EmptyException;
import com.example.studentdepartmentmanagement.exception.NotFoundException;
import com.example.studentdepartmentmanagement.model.DepartmentModel;
import com.example.studentdepartmentmanagement.model.DepartmentSortBy;
import com.example.studentdepartmentmanagement.model.StudentModel;
import com.example.studentdepartmentmanagement.reponse.DepartmentResponse;
import com.example.studentdepartmentmanagement.repository.DepartmentRepository;
import com.example.studentdepartmentmanagement.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    DepartmentRepository departmentRepository;


    @Override
    public List<DepartmentModel> getAllDepartment() throws NotFoundException {
        List<DepartmentModel> departmentModel = departmentRepository.findAllBySoftDeleteFalse();

        if (departmentModel.isEmpty()) {
            throw new EmptyException(MessageConstant.DEPARTMENT_IS_EMPTY);
        } else {
            List<DepartmentModel> departmentModels=new ArrayList<>();
            for (DepartmentModel model : departmentModel) {
                DepartmentModel departmentModel1 = new DepartmentModel();
                BeanUtils.copyProperties(model, departmentModel1);
                departmentModels.add(departmentModel1);
                }
            return departmentModels;
        }
    }

    @Override
    public Department getDepartment(String id) {
        Optional<DepartmentModel> departmentModel = departmentRepository.findByIdAndSoftDeleteIsFalse(id);
        if (departmentModel.isPresent()) {
            DepartmentModel DepartmentModel1 = departmentModel.get();
            departmentRepository.save(DepartmentModel1);
            return modelMapper.map(DepartmentModel1, Department.class);
        } else {
            throw new NotFoundException(MessageConstant.DEPARTMENT_ID_NOT_FOUND);
        }
    }

    @Override
    public void deleteDepartment(String id, String DepartmentId) {
        Optional<DepartmentModel> departmentModel = departmentRepository.findByIdAndSoftDeleteIsFalse(id);

        if (departmentModel.isPresent()) {
            DepartmentModel departmentModel1 = departmentModel.get();
            departmentModel1.setSoftDelete(true);
            departmentRepository.save(departmentModel1);

            List<StudentModel> studentModels = studentRepository.findByDepartmentIdAndSoftDeleteIsFalse(id);
            System.out.println(studentModels.size());
            studentService.deleteAllStudent(studentModels);
        } else {
            throw new NotFoundException(MessageConstant.DEPARTMENT_ID_NOT_FOUND);
        }
    }

    @Override
    public void deleteAll() {
        departmentRepository.deleteAll();
    }

    @Override
    public DepartmentModel addOrUpdateDepartment(Department department, String id) {
        if (id != null) {
            Optional<DepartmentModel> departmentModel = departmentRepository.findByIdAndSoftDeleteIsFalse(id);
            if (departmentModel.isPresent()) {
                DepartmentModel departmentModel1 = departmentModel.get();
                departmentModel1.setDepartmentName(department.getDepartmentName());
                departmentModel1.setDescription(department.getDescription());
                departmentRepository.save(departmentModel1);
                return modelMapper.map(departmentModel1, DepartmentModel.class);
            } else {
                throw new NotFoundException(MessageConstant.DEPARTMENT_ID_NOT_FOUND);
            }
           }
        else {
            boolean exists = departmentRepository.existsByDepartmentNameAndSoftDeleteFalse(department.getDepartmentName());
            if (exists) {
                throw new AlreadyExistException(MessageConstant.DEPARTMENT_NAME_EXIST);
            }
            DepartmentModel departmentModel = new DepartmentModel();
            BeanUtils.copyProperties(department, departmentModel);
            return departmentRepository.save(departmentModel);
        }
    }

    @Override
    public Page<DepartmentResponse> getAllDepartmentWithFilterAndSort(DepartmentFilter departmentFilter,
                                                                      FilterSortRequest.SortRequest<DepartmentSortBy> sort, PageRequest  pagination) {
      return departmentRepository.findAllDepartmentByFilterAndSortAndPage(departmentFilter,sort,pagination);
    }
}

