package com.iCourse.user.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iCourse.user.bean.StuQualificate;

public interface StuQualificateDao extends PagingAndSortingRepository<StuQualificate , Long> ,JpaSpecificationExecutor<StuQualificate>{

}
