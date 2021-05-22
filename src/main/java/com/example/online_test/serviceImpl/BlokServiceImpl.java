package com.example.online_test.serviceImpl;

import com.example.online_test.entity.*;
import com.example.online_test.payload.BlokRequest;
import com.example.online_test.repository.*;
import com.example.online_test.service.BlokService;
import com.example.online_test.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class BlokServiceImpl implements BlokService {

    @Autowired
    BlokRepository blokRepository;

    @Autowired
    SubjectsRepository subjectsRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    QuestionService questionService;

    @Autowired
    HistorySavedAnswersRepository historySavedAnswersRepository;

    @Override
    public Map create(String userId, BlokRequest blokRequest) {
        try {
            Blok blok = new Blok();
            Subjects subjects = subjectsRepository.getOne(blokRequest.getBlokFirstId());
            Map mapq = isProcessingBlokWithUserId(userId);
            if (mapq!=null){
                return mapq;
            }
            subjects.setParentsFirst(null);
            subjects.setParentsSecond(null);
            blok.setBlokFirst(subjects);
            blok.setQuestionFirstList(questionService.getRandomQuestionListBySubjectId(subjects.getId()));
            Subjects subjects1 = subjectsRepository.getOne(blokRequest.getBlokSecondId());
            subjects1.setParentsFirst(null);
            subjects1.setParentsSecond(null);
            blok.setBlokSecond(subjects1);
            blok.setQuestionSecondList(questionService.getRandomQuestionListBySubjectId(subjects1.getId()));
            Subjects subjects2 = subjectsRepository.getOne(blokRequest.getBlokThirdId());
            subjects2.setParentsFirst(null);
            subjects2.setParentsSecond(null);
            blok.setBlokThird(subjects2);
            blok.setQuestionThirdList(questionService.getRandomQuestionListBySubjectId(subjects2.getId()));
            blok.setUser(userRepository.findById(userId).get());
            Date date = new Date();
            blok.setFinalDate(date);
            if (blokRequest == null && userId == null) {
                return null;
            }
            Blok blok1 = blokRepository.save(blok);
            date = new Date(blok1.getCreateAt().getTime());
            LocalDateTime localDateTime = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            localDateTime = localDateTime.plusHours(3);
            Date date1 = new Date();
            date1.setDate(localDateTime.getDayOfMonth());
            date1.setYear(localDateTime.getYear() - 1900);
            date1.setMinutes(localDateTime.getMinute());
            date1.setSeconds(localDateTime.getSecond());
            date1.setHours(localDateTime.getHour());
            date1.setMonth(localDateTime.getMonthValue() - 1);
            blok1.setFinalDate(date1);
            Blok blokN = blokRepository.save(blok1);
            if (blokN == null) {
                return null;
            }
            HistorySavedAnswers savedAnswers = new HistorySavedAnswers();
            List<HistorySavedAnswers> savedAnswersList = new ArrayList<>();
            Question question = null;
            History history = new History();
            history.setUser(userRepository.findById(userId).get());
            history.setBlok(blokN);
            history.setBallAll(0.0);
            history.setPercentAll(0.0);
            history.setCountAll(0);
            history.setBallFirst(0.0);
            history.setCountFirst(0);
            history.setPercentFirst(0.0);
            history.setBallSecond(0.0);
            history.setCountSecond(0);
            history.setPercentSecond(0.0);
            history.setBallThird(0.0);
            history.setCountThird(0);
            history.setPercentThird(0.0);

            for (int i = 0; i < blokN.getQuestionFirstList().size(); i++) {
                savedAnswers = new HistorySavedAnswers();
                savedAnswers.setSelectedId(null);
                savedAnswers.setCorrectAnswerId(blokN.getQuestionFirstList().get(i).getCorrectAnswerId());
                savedAnswers.setQuestionId(blokN.getQuestionFirstList().get(i).getId());
                savedAnswersList.add(historySavedAnswersRepository.save(savedAnswers));
            }
            for (int i = 0; i < blokN.getQuestionSecondList().size(); i++) {

                savedAnswers = new HistorySavedAnswers();
                savedAnswers.setSelectedId(null);
                savedAnswers.setCorrectAnswerId(blokN.getQuestionSecondList().get(i).getCorrectAnswerId());
                savedAnswers.setQuestionId(blokN.getQuestionSecondList().get(i).getId());
                savedAnswersList.add(historySavedAnswersRepository.save(savedAnswers));
            }
            for (int i = 0; i < blokN.getQuestionThirdList().size(); i++) {

                savedAnswers = new HistorySavedAnswers();
                savedAnswers.setSelectedId(null);
                savedAnswers.setCorrectAnswerId(blokN.getQuestionThirdList().get(i).getCorrectAnswerId());
                savedAnswers.setQuestionId(blokN.getQuestionThirdList().get(i).getId());
                savedAnswersList.add(historySavedAnswersRepository.save(savedAnswers));
            }

            history.setCreateAt(blok.getFinalDate());
            history.setHistorySavedAnswers(
                    savedAnswersList
            );
            Date date2 = new Date();
            history.setSpentTime("00:00:00");
            historyRepository.save(history);

//            blokN.setBlokFirst(subjects);
//            blokN.setBlokSecond(subjects1);
//            blokN.setBlokThird(subjects2);

            Map<String, Object> map = new HashMap<>();
            map.put("blok", blokN);
            return map;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean saveAnswerReq(User user, String qId, String correctAnswerId){
        try {
            Question question1 = questionRepository.getOne(qId);
            Answer answer = answerRepository.getOne(correctAnswerId);
            if (question1==null || answer == null){
                return false;
            }
            boolean isTrue = false;
            if (question1.getCorrectAnswerId().equals(answer.getId())){
                isTrue = true;
            }
            Map map = isProcessingBlokWithUserId(user.getId());
            Blok blokProcessing = isProcessingBlokWithUserIdgetBlok(user.getId());
            History history = historyRepository.findByUserIdAndBlokId(user.getId(), blokProcessing.getId());

            HistorySavedAnswers savedAnswers = new HistorySavedAnswers();
            List<HistorySavedAnswers> savedAnswersList =history.getHistorySavedAnswers();
            Blok blok = blokRepository.getOne(blokProcessing.getId());
            Question question = null;

            boolean isHave = false;
            for (int i = 0; i < blok.getQuestionFirstList().size(); i++) {
                if (blok.getQuestionFirstList().get(i).getId().equals(question1.getId())){
                    for (int i1 = 0; i1 < savedAnswersList.size(); i1++) {
                        if (savedAnswersList.get(i1).getQuestionId().equals(question1.getId())){
                            if (savedAnswersList.get(i1).getCorrectAnswerId().equals(savedAnswersList.get(i1).getSelectedId())){
                                if (isTrue){
                                    history.setCountFirst(history.getCountFirst());
                                } else {
                                    history.setCountFirst(history.getCountFirst()-1);
                                };
                            } else {
                                if (isTrue){
                                    history.setCountFirst(1+history.getCountFirst());
                                }
                            }
                            savedAnswers=new HistorySavedAnswers();
                            savedAnswers = historySavedAnswersRepository.getOne(savedAnswersList.get(i1).getId());
                            savedAnswers.setSelectedId(answer.getId());
                            savedAnswersList.remove(i1);
                            savedAnswersList.add(historySavedAnswersRepository.save(savedAnswers));
                            isHave=true;
                        }
                    }
                    if (!isHave){
                        if (isTrue) {
                            history.setCountFirst(1+history.getCountFirst());
                        }
                        savedAnswers = new HistorySavedAnswers();
                        savedAnswers.setQuestionId(question.getId());
                        savedAnswers.setCorrectAnswerId(question.getCorrectAnswerId());
                        savedAnswers.setSelectedId(answer.getId());
                        savedAnswersList.add(historySavedAnswersRepository.save(savedAnswers));
                    }
                };
            }
            isHave=false;
            double a = (history.getCountFirst() * 100) / 30;
            history.setPercentFirst(a);
            history.setBallFirst(history.getCountFirst()*3.1);

            for (int i = 0; i < blok.getQuestionSecondList().size(); i++) {
                if (blok.getQuestionSecondList().get(i).getId().equals(question1.getId())){
                    for (int i1 = 0; i1 < savedAnswersList.size(); i1++) {
                        if (savedAnswersList.get(i1).getQuestionId().equals(question1.getId())){
                            if (savedAnswersList.get(i1).getCorrectAnswerId().equals(savedAnswersList.get(i1).getSelectedId())){
                                if (isTrue){
                                    history.setCountSecond(history.getCountSecond());
                                } else {
                                    history.setCountSecond(history.getCountSecond()-1);
                                };
                            } else {
                                if (isTrue){
                                    history.setCountSecond(1+history.getCountSecond());
                                }
                            }

                            savedAnswers=new HistorySavedAnswers();
                            savedAnswers = historySavedAnswersRepository.getOne(savedAnswersList.get(i1).getId());
                            savedAnswers.setSelectedId(answer.getId());
                            savedAnswersList.remove(i1);
                            savedAnswersList.add(historySavedAnswersRepository.save(savedAnswers));
                            isHave=true;
                        }
                    }
                    if (!isHave){
                        if (isTrue) {
                            history.setCountSecond(1+history.getCountSecond());
                        }
                        savedAnswers = new HistorySavedAnswers();
                        savedAnswers.setQuestionId(question.getId());
                        savedAnswers.setCorrectAnswerId(question.getCorrectAnswerId());
                        savedAnswers.setSelectedId(answer.getId());
                        savedAnswersList.add(historySavedAnswersRepository.save(savedAnswers));
                    }
                };
            }
            a = 0;
            a = (history.getCountSecond() * 100) / 30;
            history.setPercentSecond(a);
            history.setBallSecond(history.getCountSecond() * 2.1);
            isHave=false;

            for (int i = 0; i < blok.getQuestionThirdList().size(); i++) {
                if (blok.getQuestionThirdList().get(i).getId().equals(question1.getId())){
                    for (int i1 = 0; i1 < savedAnswersList.size(); i1++) {
                        if (savedAnswersList.get(i1).getQuestionId().equals(question1.getId())){
                            if (savedAnswersList.get(i1).getCorrectAnswerId().equals(savedAnswersList.get(i1).getSelectedId())){
                                if (isTrue){
                                    history.setCountThird(history.getCountThird());
                                } else {
                                    history.setCountThird(history.getCountThird()-1);
                                };
                            } else {
                                if (isTrue){
                                    history.setCountThird(1+history.getCountThird());
                                }
                            }

                            savedAnswers=new HistorySavedAnswers();
                            savedAnswers = historySavedAnswersRepository.getOne(savedAnswersList.get(i1).getId());
                            savedAnswers.setSelectedId(answer.getId());
                            savedAnswersList.remove(i1);
                            savedAnswersList.add(historySavedAnswersRepository.save(savedAnswers));

                            isHave=true;
                        }
                    }
                    if (!isHave){
                        if (isTrue) {
                            history.setCountThird(1+history.getCountThird());
                        }
                        savedAnswers = new HistorySavedAnswers();
                        savedAnswers.setQuestionId(question.getId());
                        savedAnswers.setCorrectAnswerId(question.getCorrectAnswerId());
                        savedAnswers.setSelectedId(answer.getId());
                        savedAnswersList.add(historySavedAnswersRepository.save(savedAnswers));
                    }
                };
            }
            a = 0;
            a = (history.getCountThird() * 100) / 30;
            history.setPercentSecond(a);
            history.setBallThird(history.getCountThird() * 1.1);

            history.setCountAll(history.getCountFirst() + history.getCountSecond() + history.getCountThird());
            history.setPercentAll((history.getBallFirst() + history.getBallSecond() + history.getBallThird())/189);
            history.setBallAll(history.getBallFirst() + history.getBallSecond() + history.getBallThird());

            history.setCreateAt(blok.getFinalDate());
            history.setHistorySavedAnswers(savedAnswersList);
            Date date = new Date();
            if (blok.getFinalDate().compareTo(date) < 0 ){
                history.setSpentTime("03:00:00");
            } else {
                long s = blok.getFinalDate().getTime()-date.getTime();
                long b = s/1000;
                long hour = b/3600;
                long hour1 = 3-b/3600;
                long minuet = (b-3600*hour)/60;
                long minuet1 = 60-(b-3600*hour)/60;
                long second = (b-3600*hour-minuet*60);
                long second1 = 60-(b-3600*hour-minuet*60);
                history.setSpentTime("0"+hour1+":"+minuet1+":"+second1);
            }
            ;
            return historyRepository.save(history)!=null;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Map isProcessingBlokWithUserId(String userId) {

        try {
            Date date = new Date();
            LocalDateTime localDateTime = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            localDateTime = localDateTime.plusHours(3);
            Date date1 = new Date();
            date1.setDate(localDateTime.getDayOfMonth());
            date1.setYear(localDateTime.getYear() - 1900);
            date1.setMinutes(localDateTime.getMinute());
            date1.setSeconds(localDateTime.getSecond());
            date1.setHours(localDateTime.getHour());
            date1.setMonth(localDateTime.getMonthValue() - 1);

            Blok blok = blokRepository.findByUserIdAndFinalDateLessThanAndFinalDateGreaterThan(userId, date1, date);
            if (blok == null) {
                return null;
            }
            List<Blok> blokList = blokRepository.findAllByUserId(userId);
            boolean isHave = false;
            for (int i = 0; i < blokList.size(); i++) {
                if (blokList.get(i).getId().equals(blok.getId())){
                    isHave = true;
                    break;
                }
            }
            if (!isHave){
                return null;
            }
            History history = historyRepository.findByUserIdAndBlokId(userId, blok.getId());
            Map<Object, Object> objectMap = new HashMap<>();
            objectMap.put("blok", blok);
            objectMap.put("savedAnswers", history.getHistorySavedAnswers());
            return objectMap;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Blok isProcessingBlokWithUserIdgetBlok(String userId) {

        try {
            Date date = new Date();
            LocalDateTime localDateTime = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            localDateTime = localDateTime.plusHours(3);
            Date date1 = new Date();
            date1.setDate(localDateTime.getDayOfMonth());
            date1.setYear(localDateTime.getYear() - 1900);
            date1.setMinutes(localDateTime.getMinute());
            date1.setSeconds(localDateTime.getSecond());
            date1.setHours(localDateTime.getHour());
            date1.setMonth(localDateTime.getMonthValue() - 1);
            Blok blok = blokRepository.findByUserIdAndFinalDateLessThanAndFinalDateGreaterThan(userId, date1, date);
            if (blok == null) {
                return null;
            }
            List<Blok> blokList = blokRepository.findAllByUserId(userId);
            boolean isHave = false;
            for (int i = 0; i < blokList.size(); i++) {
                if (blokList.get(i).getId().equals(blok.getId())){
                    isHave = true;
                    break;
                }
            }
            if (!isHave){
                return null;
            }
            return blok;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public History verifyAllTests(String userId, String blokId) {
        try {
            Blok blok = blokRepository.getOne(blokId);
            if (blok==null){
                return null;
            }
            History history = historyRepository.findByUserIdAndBlokId(userId, blokId);

            history.setCreateAt(blok.getFinalDate());
            Date date = new Date();
            if (blok.getFinalDate().compareTo(date) < 0 ){
                history.setSpentTime("03:00:00");
            } else {
                long s = blok.getFinalDate().getTime()-date.getTime();
                long b = s/1000;
                long hour = b/3600;
                long hour1 = 3-b/3600;
                long minuet = (b-3600*hour)/60;
                long minuet1 = 60-(b-3600*hour)/60;
                long second = (b-3600*hour-minuet*60);
                long second1 = 60-(b-3600*hour-minuet*60);
                history.setSpentTime("0"+hour1+":"+minuet1+":"+second1);
            }
            blok.setFinalDate(date);
            history.setBlok(blokRepository.save(blok));
            return historyRepository.save(history);
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
