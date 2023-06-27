package com.e1i5.stackOverflow.question.service;

import com.e1i5.stackOverflow.exception.BusinessLogicException;
import com.e1i5.stackOverflow.exception.ExceptionCode;
import com.e1i5.stackOverflow.member.entity.Member;

import com.e1i5.stackOverflow.question.entity.Tag;
import com.e1i5.stackOverflow.question.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepositoryNew) {
        this.tagRepository = tagRepositoryNew;
    }

    public Tag createTag(Tag tagNew) {
        verifyExistTag(tagNew.getTagName());
        return tagRepository.save(tagNew);
    }

    public void verifyExistTag(String tagName) { //태그네임과 같은지 검증
        Optional<Tag> findTag = tagRepository.findByTagName(tagName);
        if (findTag.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.TAG_EXISTS);
        }
    }

    public List<Tag> findMemberTags(Member member) {
        return tagRepository.findAllByMember(member);
    }
}
