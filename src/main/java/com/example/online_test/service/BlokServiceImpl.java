package com.example.online_test.service;

import com.example.online_test.entity.*;
import com.example.online_test.entity.HistorySavedAnswers;
import com.example.online_test.payload.BlokRequest;
import com.example.online_test.payload.VerifingRequest;
import com.example.online_test.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlokServiceImpl implements BlokService {

    @Autowired
    BlokRepository blokRepository;

    @Autowired
    SubjectsRepository subjectsRepository;

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
    public Blok create(String userId, BlokRequest blokRequest) {
        try {
            Blok blok = new Blok();
            Subjects subjects = subjectsRepository.getOne(blokRequest.getBlokFirstId());
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
            blokN.setBlokFirst(subjects);
            blokN.setBlokSecond(subjects1);
            blokN.setBlokThird(subjects2);

            return blokN;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Blok isProcessingBlokWithUserId(String userId) {

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
            return blok;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public History verifyAllTests(String userId, String blokId, VerifingRequest verifingRequest) {

        try {
            HistorySavedAnswers savedAnswers = new HistorySavedAnswers();
            List<HistorySavedAnswers> savedAnswersList = new ArrayList<>();
            User user = userRepository.findById(userId).get();
            Blok blok = blokRepository.getOne(blokId);
            Question question = null;
            History history = new History();
            history.setUser(user);
            history.setBlok(blok);
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
            for (int i = 0; i < verifingRequest.getSubjectFirstAnswersList().size(); i++) {
                question = questionRepository.getOne(verifingRequest.getSubjectFirstAnswersList().get(i).getQuestionId());
                if (question.getCorrectAnswerId().equals(verifingRequest.getSubjectFirstAnswersList().get(i).getCorrectAnswerId())) {
                    history.setCountFirst(1+history.getCountFirst());
                }
                savedAnswers = new HistorySavedAnswers();
                savedAnswers.setQuestionId(verifingRequest.getSubjectFirstAnswersList().get(i).getQuestionId());
                savedAnswers.setCorrectAnswerId(question.getCorrectAnswerId());
                savedAnswers.setSelectedId(verifingRequest.getSubjectFirstAnswersList().get(i).getCorrectAnswerId());
                savedAnswersList.add(historySavedAnswersRepository.save(savedAnswers));
            }
            double a = (history.getCountFirst() * 100) / 30;
            history.setPercentFirst(a);
            history.setBallFirst(history.getCountFirst()*3.1);

            for (int i = 0; i < verifingRequest.getSubjectSecondAnswersList().size(); i++) {
                question = questionRepository.getOne(verifingRequest.getSubjectSecondAnswersList().get(i).getQuestionId());
                if (question.getCorrectAnswerId().equals(verifingRequest.getSubjectSecondAnswersList().get(i).getCorrectAnswerId())) {
                    history.setCountSecond(1+history.getCountSecond());
                }

                savedAnswers = new HistorySavedAnswers();
                savedAnswers.setQuestionId(verifingRequest.getSubjectSecondAnswersList().get(i).getQuestionId());
                savedAnswers.setCorrectAnswerId(question.getCorrectAnswerId());
                savedAnswers.setSelectedId(verifingRequest.getSubjectSecondAnswersList().get(i).getCorrectAnswerId());
                savedAnswersList.add(savedAnswers);
                savedAnswersList.add(historySavedAnswersRepository.save(savedAnswers));
            }
            a = 0;
            a = (history.getCountSecond() * 100) / 30;
            history.setPercentSecond(a);
            history.setBallSecond(history.getCountSecond() * 2.1);

            for (int i = 0; i < verifingRequest.getSubjectThirdAnswersList().size(); i++) {
                question = questionRepository.getOne(verifingRequest.getSubjectThirdAnswersList().get(i).getQuestionId());
                if (question.getCorrectAnswerId().equals(verifingRequest.getSubjectThirdAnswersList().get(i).getCorrectAnswerId())) {
                    history.setCountThird(1+history.getCountThird());
                }

                savedAnswers = new HistorySavedAnswers();
                savedAnswers.setQuestionId(verifingRequest.getSubjectThirdAnswersList().get(i).getQuestionId());
                savedAnswers.setCorrectAnswerId(question.getCorrectAnswerId());
                savedAnswers.setSelectedId(verifingRequest.getSubjectThirdAnswersList().get(i).getCorrectAnswerId());
                savedAnswersList.add(savedAnswers);
                savedAnswersList.add(historySavedAnswersRepository.save(savedAnswers));
            }
            a = 0;
            a = (history.getCountThird() * 100) / 30;
            history.setPercentSecond(a);
            history.setBallThird(history.getCountThird() * 1.1);

            history.setCountAll(history.getCountFirst() + history.getCountSecond() + history.getCountThird());
            history.setPercentAll(history.getPercentFirst() + history.getPercentSecond() + history.getPercentThird());
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
                long minuet = (b-3600*hour)/60;
                long second = (b-3600*hour-minuet*60);
                history.setSpentTime("0"+hour+":"+minuet+":"+second);
            }
            System.out.println(history);
            return historyRepository.save(history);
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }


}
