package com.example.studentdepartmentmanagement.repository;

import com.example.studentdepartmentmanagement.decorator.CountQueryResult;
import com.example.studentdepartmentmanagement.decorator.FilterSortRequest;
import com.example.studentdepartmentmanagement.decorator.StudentFilter;
import com.example.studentdepartmentmanagement.model.StudentSortBy;
import com.example.studentdepartmentmanagement.reponse.StudentResponse;
import com.example.studentdepartmentmanagement.service.CustomAggregationOperation;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
@Repository
public class StudentRepositoryCustomImpl implements StudentRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Page<StudentResponse> findAllStudentByPagination(StudentFilter studentFilter, FilterSortRequest.SortRequest<StudentSortBy> sort, PageRequest pagination) {
        List<AggregationOperation> operations = filterAggregation(studentFilter,sort,pagination,true);
        //Created Aggregation operation
        Aggregation aggregation = newAggregation(operations);
        List<StudentResponse> students= mongoTemplate.aggregate(aggregation, "Student",StudentResponse.class).getMappedResults();

        // Find Count
        List<AggregationOperation> operationForCount = filterAggregation(studentFilter,sort,pagination,false);
        operationForCount.add(group().count().as("count"));
        operationForCount.add(project("count"));
        Aggregation aggregationCount = newAggregation(StudentResponse.class, operationForCount);
        AggregationResults<CountQueryResult> countQueryResults = mongoTemplate.aggregate(aggregationCount , "Student", CountQueryResult.class);
        long count = countQueryResults.getMappedResults().size() == 0 ? 0 : countQueryResults.getMappedResults().get(0).getCount();
        return PageableExecutionUtils.getPage(
                students,
                pagination,
                () -> count);

    }

    private List<AggregationOperation> filterAggregation(StudentFilter studentFilter, FilterSortRequest.SortRequest<StudentSortBy> sort, PageRequest pagination, boolean addPage) {
        List<AggregationOperation> operations = new ArrayList<>();

        operations.add(match(getCriteria(studentFilter,operations)));

        if(addPage){
            //sorting
            if(sort!=null && sort.getSortBy() != null && sort.getOrderBy() != null) {
                operations.add(new SortOperation(Sort.by(sort.getOrderBy(), sort.getSortBy().getValue())));
            }
            if(pagination!=null) {
                operations.add(skip(pagination.getOffset()));
                operations.add(limit(pagination.getPageSize()));
            }
        }
        return operations;
    }


    private Criteria getCriteria(StudentFilter studentFilter, List<AggregationOperation> operations) {
        Criteria criteria = new Criteria();
        operations.add(new CustomAggregationOperation(
                new Document("$addFields",
                        new Document("search",
                                new Document("$concat", Arrays.asList(
                                        new Document("$ifNull", Arrays.asList("$StudentName","")),
                                        "|@|",new Document("$ifNull",Arrays.asList("$age","")))
                                )
                        )
                ))
        );

        if (!StringUtils.isEmpty(studentFilter.getSearch())) {
          studentFilter.setSearch(studentFilter.getSearch().replaceAll("\\|@\\|", ""));
           studentFilter.setSearch(studentFilter.getSearch().replaceAll("\\|@@\\|", ""));
            criteria = criteria.orOperator(
                    Criteria.where("search").regex(".*" + studentFilter.getSearch() + ".*")
            );
        }

        if(!StringUtils.isEmpty(studentFilter.getId())){
            criteria= criteria.and("_id").in(studentFilter.getId());
        }

        criteria = criteria.and("softDelete").is(false);
        return criteria;
    }
}
