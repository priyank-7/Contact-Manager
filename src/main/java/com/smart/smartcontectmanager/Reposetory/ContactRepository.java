package com.smart.smartcontectmanager.Reposetory;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smart.smartcontectmanager.Entity.Contect;


@Repository
public interface ContactRepository extends JpaRepository<Contect, Integer>{
    
    // pagenation..

    @Query("from Contect as c where c.user.id =:userId")
    // Pageable has two information 1.current-page and 2.record per page
    public Page<Contect> findContactByUser(@Param("userId")int userId, Pageable pageable);


}
