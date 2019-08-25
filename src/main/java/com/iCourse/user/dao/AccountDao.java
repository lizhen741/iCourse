package com.iCourse.user.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iCourse.user.bean.Account;

public interface AccountDao  extends PagingAndSortingRepository<Account , Long> ,JpaSpecificationExecutor<Account>{
	
	@Query(value="select * from t_account where account_num = ?1",nativeQuery=true)
	List<Account> findAccountByStudentNum(String account);

	

	@Query("from Account a where a.account_num = ?1")
	Account findByAccount(String account_num);


	@Modifying 
	@Query("update Account a set " +
            "a.name = CASE WHEN (LENGTH(trim(:#{#account.name})) < 1 ) THEN a.name ELSE :#{#account.name} END ," +
            "a.qq = CASE WHEN (LENGTH(trim(:#{#account.qq})) < 1 ) THEN a.qq ELSE :#{#account.qq} END " +
            "where a.id = :#{#account.id}")
	void settingInfo(Account account);

	@Query("from Account a where a.email = ?1")
	Account findbyEmail(String mailStr);


	@Modifying 
	@Query("update Account a set a.head_photo = ?2 where a.id = ?1 ")
	void settingHeadPhoto(Long account_id,String url);

}
