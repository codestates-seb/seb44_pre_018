package com.e1i5.stackOverflow.question.repository;


import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.question.entity.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    // 특정 QuestionId 의 모든 태그를 찾아서 리스트로 불러옴

    @Query("SELECT t FROM Tag t WHERE t.question.questionId = :questionId")
    List<Tag> findAllByQuestion(@Param("questionId") long questionId);
    Optional<Tag> findByTagName(String tagName);

    List<Tag> findAllByMember(Member member);
}


