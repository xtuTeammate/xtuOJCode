package com.xtu.controller;

import com.xtu.DB.ContestProblemsRepository;
import com.xtu.DB.ContestRepository;
import com.xtu.DB.dto.CreateTestDTO;
import com.xtu.DB.entity.ContestProblemsEntity;
import com.xtu.DB.entity.ContestsEntity;
import com.xtu.constant.Pages;
import com.xtu.tools.DateUtil;
import com.xtu.tools.OUT;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Ilovezilian on 2017/4/18.
 */
@Controller
@RequestMapping(value = "/" + Pages.TEST)
public class TestController {
    @Autowired
    ContestRepository contestRepository;
    @Autowired
    ContestProblemsRepository contestProblemsRepository;

    @RequestMapping(value = {"/" + Pages.TEST, "/" + Pages.TEST + "/{id}"}, method = RequestMethod.GET)
    public String showTest(
            @PathVariable("id") Integer contestId,
            Model model) {
        OUT.prt("request", Pages.TEST);
        // TODO: 2017/4/18 select from db
        String res = Pages.TEST + "/" + Pages.TEST;
        return res;
    }

    @RequestMapping(value = "/" + Pages.TOTAL_TEST, method = RequestMethod.GET)
    public String showTotalTest() {
        OUT.prt("request", Pages.TOTAL_TEST);
        // TODO: 2017/4/18 select from db
        String res = Pages.TEST + "/" + Pages.TOTAL_TEST;
        return res;
    }

    @RequestMapping(value = "/" + Pages.CREATE_TEST, method = RequestMethod.GET)
    public String createTest() {
        OUT.prt("request", Pages.CREATE_TEST);
        String res = Pages.TEST + "/" + Pages.CREATE_TEST;
        return res;
    }

    @Transactional
    @RequestMapping(value = {"/" + Pages.CREATE_TEST, "/" + Pages.MODIFY_TEST}, method = RequestMethod.POST)
    public String createTestPost(
            CreateTestDTO createTestDTO,
//            @NotNull @Valid CreateTestDTO createTestDTO,
            Model model) {
        OUT.prt("post", Pages.CREATE_TEST);
        OUT.prt("post", createTestDTO);
//        createTestDTO.setStartTime(new Timestamp(createTestDTO.getStartTimeStr()));
//        createTestDTO.setFrozenStartTime(new Timestamp(createTestDTO.getFrozenStartTimeStr()));
//        createTestDTO.setEndTime(new Timestamp(createTestDTO.getEndTimeStr()));
        createTestDTO.setStartTime(DateUtil.getTimestamp(createTestDTO.getStartTimeStr()));
        createTestDTO.setFrozenStartTime(DateUtil.getTimestamp(createTestDTO.getFrozenStartTimeStr()));
        createTestDTO.setEndTime(DateUtil.getTimestamp(createTestDTO.getEndTimeStr()));
        // TODO: 2017/4/18 insert into db
        ContestsEntity contestsEntity = new ContestsEntity();
        BeanUtils.copyProperties(createTestDTO, contestsEntity);

        contestRepository.save(contestsEntity);
        ContestProblemsEntity entity = new ContestProblemsEntity();
        int contestId = contestsEntity.getContestId();
        entity.setContestId(contestsEntity.getContestId());

        String problems = createTestDTO.getProblemsList();
        contestProblemsRepository.deleteNoToMax(contestId);
        for (String s : problems.split("TAT")) {
            String[] ss = s.split("QAQ");
            entity.setNo(Byte.parseByte(ss[0]));
            entity.setProblemId(Integer.parseInt(ss[1]));
            entity.setScore(Byte.parseByte(ss[2]));
            contestProblemsRepository.insert(entity);
        }

        String res = Pages.TEST + "/" + Pages.TOTAL_TEST;
        return res;
    }

    @RequestMapping(value = "/" + Pages.JOIN_TEST, method = RequestMethod.GET)
    public String joinTest(
            // TODO: 2017/4/18 create entity
            Model model) {
        OUT.prt("request", Pages.JOIN_TEST);
        // TODO: 2017/4/18 insert into db
        String res = Pages.TEST + "/" + Pages.JOIN_TEST;
        return res;
    }
}
