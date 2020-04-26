package com.hanseld.servord.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hanseld.servord.domain.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

}
