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

    public void create(String subject, String content, SiteUser user, MultipartFile file) throws Exception {
        Question q = new Question();
        if (file.isEmpty()) {
            q.setSubject(subject);
            q.setContent(content);
            q.setCreateDate(LocalDateTime.now());
            q.setAuthor(user);
            this.questionRepository.save(q);
        } else{
            String projectPath = System.getProperty("user.dir") + "\\bike\\src\\main\\resources\\static\\files";
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);
            q.setFileName(fileName);
            q.setFilePath("/files/" + fileName);
            
            
            q.setSubject(subject);
            q.setContent(content);
            q.setCreateDate(LocalDateTime.now());
            q.setAuthor(user);
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
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);
            question.setFileName(fileName);
            question.setFilePath("/files/" + fileName);
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
