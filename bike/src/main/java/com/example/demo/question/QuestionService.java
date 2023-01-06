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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
            return question1;

        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, SiteUser user, @RequestParam("file") MultipartFile file,
    @RequestParam("file2") MultipartFile file2, @RequestParam("file3") MultipartFile file3) throws Exception {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        if (!file.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "\\bike\\src\\main\\resources\\static\\files";
            UUID uuid = UUID.randomUUID();
            String originFileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, originFileName);
            file.transferTo(saveFile);
            q.setFileName(originFileName);
            q.setFilePath("/files/" + originFileName);
        } if(!file2.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "\\bike\\src\\main\\resources\\static\\files";
            UUID uuid = UUID.randomUUID();
            String originFileName = uuid + "_" + file2.getOriginalFilename();
            File saveFile = new File(projectPath, originFileName);
            file2.transferTo(saveFile);
            q.setFileName2(originFileName);
            q.setFilePath2("/files/" + originFileName);
        } if(!file3.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "\\bike\\src\\main\\resources\\static\\files";
            UUID uuid = UUID.randomUUID();
            String originFileName = uuid + "_" + file3.getOriginalFilename();
            File saveFile = new File(projectPath, originFileName);
            file3.transferTo(saveFile);
            q.setFileName3(originFileName);
            q.setFilePath3("/files/" + originFileName);
        }
        this.questionRepository.save(q);
        
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

    public void modify (Question question, String subject, String content, MultipartFile file) throws Exception {
        if (file.isEmpty()) {
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
            UUID uuid = UUID.randomUUID();
            String originFileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, originFileName);
            file.transferTo(saveFile);
            question.setFileName(originFileName);
            question.setFilePath("/files/" + originFileName);
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
