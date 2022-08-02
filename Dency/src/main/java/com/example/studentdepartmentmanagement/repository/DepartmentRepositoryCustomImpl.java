package com.example.studentdepartmentmanagement.repository;

import com.example.studentdepartmentmanagement.common.utils.AggregationUtils;
import com.example.studentdepartmentmanagement.decorator.CountQueryResult;
import com.example.studentdepartmentmanagement.decorator.DepartmentFilter;
import com.example.studentdepartmentmanagement.decorator.FilterSortRequest;
import com.example.studentdepartmentmanagement.model.DepartmentSortBy;
import com.example.studentdepartmentmanagement.reponse.DepartmentResponse;
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

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class  DepartmentRepositoryCustomImpl implements DepartmentRepositoryCustom {

    @Autowired
   MongoTemplate mongoTemplate;

    @Override
    public Page<DepartmentResponse> findAllDepartmentByFilterAndSortAndPage(DepartmentFilter departmentFilter, FilterSortRequest.SortRequest<DepartmentSortBy> sort, PageRequest pagination) {
        List<AggregationOperation> operations = filterAggregation(departmentFilter,sort,pagination,true);
        //Created Aggregation operation
        Aggregation aggregation = newAggregation(operations);

        List<DepartmentResponse> departments = mongoTemplate.aggregate(aggregation, "Department",DepartmentResponse.class).getMappedResults();

        // Find Count
        List<AggregationOperation> operationForCount = filterAggregation(departmentFilter,sort,pagination,false);
        operationForCount.add(group().count().as("count"));
        operationForCount.add(project("count"));
        Aggregation aggregationCount = newAggregation(DepartmentResponse.class, operationForCount);
        AggregationResults<CountQueryResult> countQueryResults = mongoTemplate.aggregate(aggregationCount , "Department", CountQueryResult.class);
        long count = countQueryResults.getMappedResults().size() == 0 ? 0 : countQueryResults.getMappedResults().get(0).getCount();
        return PageableExecutionUtils.getPage(
                departments,
                pagination,
                () -> count);
    }

    private List<AggregationOperation> filterAggregation(DepartmentFilter departmentFilter, FilterSortRequest.SortRequest<DepartmentSortBy> sort, PageRequest pagination, boolean addPage) {
        List<AggregationOperation> operations = new ArrayList<>();

        operations.add(match(getCriteria(departmentFilter,operations)));

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


        Map<String,Object>  let = new HashMap<>();
        let.put("department","$toString._id");

        List<Document> pipeline = new ArrayList<>();
        pipeline.add(new Document("$match",
                new Document("$expr",
                        new Document("$and",
                                new Document("$eq",Arrays.asList("$departmentId","$$department"))
                        )
                )
        ));
        operations.add(AggregationUtils.lookup("Student",let,pipeline,"Department"));
        return operations;

    }
    private Criteria getCriteria(DepartmentFilter departmentFilter, List<AggregationOperation> operations) {
        Criteria criteria = new Criteria();
        operations.add(new CustomAggregationOperation(
                new Document("$addFields",
                        new Document("search",
                                new Document("$concat",Arrays.asList(
                                        new Document("$ifNull", Arrays.asList("$departmentName","")),
                                        "|@|",new Document("$ifNull",Arrays.asList("$Description","")))
                                )
                        )
                ))
        );

        if (!StringUtils.isEmpty(departmentFilter.getSearch())) {
            departmentFilter.setSearch(departmentFilter.getSearch().replaceAll("\\|@\\|", ""));
            departmentFilter.setSearch(departmentFilter.getSearch().replaceAll("\\|@@\\|", ""));
            criteria = criteria.orOperator(
                    Criteria.where("search").regex(".*" + departmentFilter.getSearch() + ".*", "i")
            );
        }

        if(!StringUtils.isEmpty(departmentFilter.getId())){
            criteria= criteria.and("_id").in(departmentFilter.getId());
        }

        criteria = criteria.and("softDelete").is(false);
        return criteria;
    }
}


