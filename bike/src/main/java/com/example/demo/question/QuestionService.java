package com.example.demo.question;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.demo.DataNotFoundException;
import com.example.demo.user.SiteUser;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
    
    private final QuestionRepository questionRepository;

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            Question question1 = question.get();
            // question1.setView(question1.getView() + 1);
            // this.questionRepository.save(question1);
            return question1;

        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, SiteUser user, MultipartHttpServletRequest file) throws Exception {
        Question q = new Question();
        int checkNum = 1;
        List<MultipartFile> fileList = file.getFiles("file");
        for (MultipartFile mf : fileList) {
            if(mf.isEmpty()) {
                checkNum=0;
            }
        }
        if (checkNum==0) {
            q.setSubject(subject);
            q.setContent(content);
            q.setCreateDate(LocalDateTime.now());
            q.setAuthor(user);
            this.questionRepository.save(q);
        } 
        else {
            q.setSubject(subject);
            q.setContent(content);
            q.setCreateDate(LocalDateTime.now());
            q.setAuthor(user);
            String projectPath = System.getProperty("user.dir") + "\\bike\\src\\main\\resources\\static\\files";
            for (MultipartFile mf : fileList) {
                UUID uuid = UUID.randomUUID();
                String originFileName = uuid + "_" + mf.getOriginalFilename();
                File saveFile = new File(projectPath, originFileName);
                mf.transferTo(saveFile);
                q.setFileName(originFileName);
                q.setFilePath("/files/" + originFileName);
            }
            this.questionRepository.save(q);
        }
        
    }

    public Page<Question> getList(int page, String kw){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAllByKeyword(kw, pageable);
    }

    public Page<Question> outDated(int page, String kw){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAllByKeyword(kw, pageable);
    }

    public Page<Question> Views(int page, String kw){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("view"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAllByKeyword(kw, pageable);
    }

    public void modify (Question question, String subject, String content, MultipartHttpServletRequest file) throws Exception {
        int checkNum = 1;
        List<MultipartFile> fileList = file.getFiles("file");
        for (MultipartFile mf : fileList) {
            if(mf.isEmpty()) {
                checkNum=0;
            }
        }
        if (checkNum==0) {
            question.setSubject(subject);
            question.setContent(content);
            question.setModifyDate(LocalDateTime.now());
            this.questionRepository.save(question);
        } 
        else {
            question.setSubject(subject);
            question.setContent(content);
            question.setModifyDate(LocalDateTime.now());

            String projectPath = System.getProperty("user.dir") + "\\bike\\src\\main\\resources\\static\\files";
            for (MultipartFile mf : fileList) {
                UUID uuid = UUID.randomUUID();
                String originFileName = uuid + "_" + mf.getOriginalFilename();
                File saveFile = new File(projectPath, originFileName);
                mf.transferTo(saveFile);
                question.setFileName(originFileName);
                question.setFilePath("/files/" + originFileName);
            }
            this.questionRepository.save(question);
        }  
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }

    @Transactional
    public int updateView(Integer id) {
        return this.questionRepository.updateView(id);
    }

}
