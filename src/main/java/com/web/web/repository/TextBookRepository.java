package com.web.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.web.entity.TextBook;

@Repository
public interface TextBookRepository extends JpaRepository<TextBook,Integer> {
    public Page<TextBook> findAll(Pageable pageable);
}
