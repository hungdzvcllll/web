package com.web.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.HashMapChangeSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.web.dto.request.AnswerRequest;
import com.web.web.dto.response.Questions;
import com.web.web.dto.response.TestResponse;
import com.web.web.entity.StudySet;
import com.web.web.entity.StudySetItem;
import com.web.web.entity.Test;
import com.web.web.mapper.TestMapper;
import com.web.web.repository.StudySetItemRepository;
import com.web.web.repository.StudySetRepository;
import com.web.web.repository.TestRepository;

@Service
public class TestService {
    @Autowired
    TestRepository testRepo;
    @Autowired
    VipUserService vipService;
    @Autowired
    StudySetRepository setRepo;
    @Autowired
    StudySetItemRepository itemRepo;
    @Autowired
    FolderService folderService;
    @Autowired
    UserService userService;
    @Autowired
    TestMapper mapper;
    public  List<Questions> createQuiz(Integer studySetId) {
 
        StudySet set=setRepo.findById(studySetId).get();
        folderService.checkIfYouCanAccessFolder(set.getFolder().getId());
        vipService.checkIfYouAreVipNow();
        ArrayList<StudySetItem> items=itemRepo.findByStudySet(set);
        List<Questions> quiz = new ArrayList<>();
        Random rand = new Random();

        // Trộn ngẫu nhiên danh sách câu hỏi gốc
        Collections.shuffle(items);
        int total = items.size();

        // Lấy n câu hỏi đầu tiên cho bài kiểm tra
        
        if(total>4){
            for (StudySetItem item : items) {
                List<String> options = new ArrayList<>();
                options.add(item.getDefine()); // thêm đáp án đúng
                ArrayList<Integer> choosed=new ArrayList<Integer>();
                choosed.add(item.getId());
                int chooseAnswer=item.getId();
                ArrayList<String> listAnswer=new ArrayList<String>();
                listAnswer.add(item.getDefine());
                for(int i=0;;i++){
                    
                    int randNum=rand.nextInt(items.size());
                    chooseAnswer=items.get(randNum).getId();
                    if(!choosed.contains(chooseAnswer)){
                        choosed.add(chooseAnswer);
                        listAnswer.add(items.get(randNum).getDefine());
                    }
                    if(listAnswer.size()==4)
                        break;
                    
                }
                Collections.shuffle(listAnswer);
                quiz.add(new Questions(item.getId(),item.getConcept(),listAnswer));
            }
            return quiz;
        }
        else{
            ArrayList<String> listAnswer=new ArrayList<String>();
            for(StudySetItem item:items){
               listAnswer.add(item.getDefine());
            }
            for(StudySetItem item:items){
                quiz.add(new Questions(item.getId(),item.getConcept(),listAnswer)) ;
            }
            return quiz;
        }
    }
    public TestResponse submitTest(String name,Integer studySetId,ArrayList<AnswerRequest> answers){
        int trueCount=0;
        StudySet set=setRepo.findById(studySetId).get();
        folderService.checkIfYouCanAccessFolder(set.getFolder().getId());
        vipService.checkIfYouAreVipNow();
        ArrayList<StudySetItem> items=itemRepo.findByStudySet(set);
        HashSet<Integer> answered=new HashSet<Integer>();
        for(AnswerRequest answer:answers){
            StudySetItem item=itemRepo.findById(answer.getItemId()).get();
            
            if(item.getDefine().equals(answer.getAnswer())&&answered.contains(item.getId())==false){
                trueCount++;
                
            }
            answered.add(item.getId());
        }
        int percent=Math.round((float)trueCount/items.size()*100);
        Test test=testRepo.save(new Test(null,name,percent,userService.findCurrentUser(),set));
        return mapper.toDTO(test);
    }
    public TestResponse findById(Integer id){
        Test t=testRepo.findById(id).get();
        folderService.checkIfYouCanAccessFolder(t.getStudySet().getFolder().getId());
        return mapper.toDTO(testRepo.findById(id).get());
    }
    public Page<TestResponse> findByStudySet(Integer studySetId,Pageable pageable){
        StudySet set=setRepo.findById(studySetId).get();
        folderService.checkIfYouCanAccessFolder(set.getFolder().getId());
        return testRepo.findByStudySet(set,pageable).map(mapper::toDTO);
    }
    
    
}
