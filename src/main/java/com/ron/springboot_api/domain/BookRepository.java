package com.ron.springboot_api.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

//    @Transactional
//    @Modifying
//    @Query("DELETE FROM Book b WHERE b.id = ?1")
//    void delete(Long id);
}
