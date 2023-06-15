package com.e1i5.stackOverflow.comment.repository;

import com.e1i5.stackOverflow.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 주어진 질문 id에 해당하는 모든 댓글 조회
    List<Comment> findAllByQuestionId(long questionId);


}
