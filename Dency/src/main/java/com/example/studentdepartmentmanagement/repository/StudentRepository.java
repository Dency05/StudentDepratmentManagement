package com.example.studentdepartmentmanagement.repository;

import com.example.studentdepartmentmanagement.model.StudentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface StudentRepository extends MongoRepository<StudentModel,String>,StudentRepositoryCustom {
    Optional<StudentModel> findByIdAndSoftDeleteIsFalse(String id);

    List<StudentModel> findAllBySoftDeleteFalse();

    List<StudentModel> findByDepartmentIdAndSoftDeleteIsFalse(String id);
}
