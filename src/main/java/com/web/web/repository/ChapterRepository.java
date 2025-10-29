package com.web.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.web.entity.Chapter;
import com.web.web.entity.TextBook;
@Repository
public interface ChapterRepository extends JpaRepository<Chapter,Integer> {
    public Page<Chapter> findByTextBook(TextBook book,Pageable pageable);
}
