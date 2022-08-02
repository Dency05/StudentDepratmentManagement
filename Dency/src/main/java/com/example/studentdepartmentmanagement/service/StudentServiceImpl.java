package com.example.studentdepartmentmanagement.service;

import com.example.studentdepartmentmanagement.constant.MessageConstant;
import com.example.studentdepartmentmanagement.decorator.FilterSortRequest;
import com.example.studentdepartmentmanagement.decorator.Student;
import com.example.studentdepartmentmanagement.decorator.StudentFilter;
import com.example.studentdepartmentmanagement.exception.EmptyException;
import com.example.studentdepartmentmanagement.exception.NotFoundException;
import com.example.studentdepartmentmanagement.model.DepartmentModel;
import com.example.studentdepartmentmanagement.model.DepartmentType;
import com.example.studentdepartmentmanagement.model.StudentModel;
import com.example.studentdepartmentmanagement.model.StudentSortBy;
import com.example.studentdepartmentmanagement.reponse.StudentResponse;
import com.example.studentdepartmentmanagement.repository.DepartmentRepository;
import com.example.studentdepartmentmanagement.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<StudentModel> getAllStudent() throws NotFoundException {
        List<StudentModel> studentModel = studentRepository.findAllBySoftDeleteFalse();
        if (studentModel.isEmpty()) {
            throw new EmptyException(MessageConstant.STUDENT_IS_EMPTY);
        } else {
            return studentModel;
        }
    }

    @Override
    public Student getStudent(String id){
        Optional<StudentModel> studentModel = studentRepository.findByIdAndSoftDeleteIsFalse(id);
        if (studentModel.isPresent()) {
            StudentModel studentModel1 = studentModel.get();
            studentRepository.save(studentModel1);
            return modelMapper.map(studentModel1, Student.class);
        }
        else {
            throw new NotFoundException(MessageConstant.STUDENT_ID_NOT_FOUND);
        }

    }

     @Override
      public Object deleteStudent(String id){
        Optional<StudentModel> studentModel = studentRepository.findByIdAndSoftDeleteIsFalse(id);
        if (studentModel.isPresent()){
            StudentModel student1 = studentModel.get();
            student1.setSoftDelete(true);
            studentRepository.save(student1);
        }
        else {
            throw new NotFoundException(MessageConstant.STUDENT_ID_NOT_FOUND);
        }
        return null;
       }

        @Override
        public Object deleteAll() {
         studentRepository.deleteAll();
            return null;
        }

        @Override
        public StudentModel addorupdateStudent(Student student, DepartmentType departmentType, String id) {

            if(id!=null){
                Optional<StudentModel> studentModel = studentRepository.findByIdAndSoftDeleteIsFalse(id);
                if (studentModel.isPresent()){
                    StudentModel studentModel1= studentModel.get();
                    studentModel1.setStudentName(student.getStudentName());
                    studentModel1.setAge(student.getAge());
                    studentRepository.save(studentModel1);
                    return modelMapper.map(studentModel1,  StudentModel.class);
                }
                else {
                    throw new NotFoundException(MessageConstant.STUDENT_ID_NOT_FOUND);
                }
            }
            else{
                DepartmentModel departmentModel = departmentRepository.findByDepartmentNameAndSoftDeleteIsFalse(departmentType.toString());
                if (departmentModel ==null){
                    throw new NotFoundException(MessageConstant.DEPARTMENT_TYPE_NOT_FOUND);
                }
                StudentModel studentModel = modelMapper.map(student,StudentModel.class);
                studentModel.setDepartmentId(departmentModel.getId());
                studentModel.setDepartmentType(departmentType);
                return  studentRepository.save(studentModel);
            }}

    @Override
    public void deleteAllStudent(List<StudentModel> studentModels) {
        if(!studentModels.isEmpty()){
            for (StudentModel studentModel : studentModels){
                studentModel.setSoftDelete(true);
            }
            studentRepository.saveAll(studentModels);
        }
    }

    @Override
    public List<StudentModel> findfieldwithsorting(String field) {
        List<StudentModel> studentModel = studentRepository.findAllBySoftDeleteFalse();
        if (studentModel.isEmpty()) {
            throw new EmptyException(MessageConstant.STUDENT_IS_EMPTY);
        } else {
            return studentRepository.findAll(Sort.by(Sort.Direction.ASC, field));
        }
    }

    @Override
    public Page<StudentModel> findStudentwithPagination(int offset,int pagesize) {
        return studentRepository.findAll(PageRequest.of(offset, pagesize));
    }


    @Override
    public Page<StudentModel> findStudentwithPaginationAndSorting(int offset, int pagesize, String field) {
        //error
        //return studentRepository.findAll(PageRequest.of(offset, pagesize).withSort(Sort.by(field)));
        return null;
    }

    @Override
    public Page<StudentResponse> getAllStudentWithFilterAndSort(StudentFilter filter, FilterSortRequest.SortRequest<StudentSortBy> sort, PageRequest pagination) {
        return studentRepository.findAllStudentByPagination(filter,sort,pagination);
    }
}
















