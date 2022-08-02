package com.example.studentdepartmentmanagement.repository;

import com.example.studentdepartmentmanagement.model.DepartmentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends MongoRepository<DepartmentModel,String>,DepartmentRepositoryCustom {
    Optional<DepartmentModel> findByIdAndSoftDeleteIsFalse(String id);

    List<DepartmentModel> findAllBySoftDeleteFalse();

    DepartmentModel findByDepartmentNameAndSoftDeleteIsFalse(String DepartmentName);

    boolean existsByDepartmentNameAndSoftDeleteFalse(String departmentName);

}
