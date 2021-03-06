package com.xtu.controller;

import com.sun.istack.internal.NotNull;
import com.xtu.DB.RunsRepository;
import com.xtu.DB.UsersRepository;
import com.xtu.DB.dto.StatusDTO;
import com.xtu.DB.entity.UsersEntity;
import com.xtu.DB.vo.RankEntityVO;
import com.xtu.DB.vo.RankVO;
import com.xtu.DB.vo.StatusEntityVO;
import com.xtu.DB.vo.StatusVO;
import com.xtu.constant.Constant;
import com.xtu.constant.Pages;
import com.xtu.tools.MyFileUtils;
import com.xtu.tools.OUT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * 主页界面
 * Created by Ilovezilian on 2017/4/13.
 */
@Controller
public class MainController {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RunsRepository runsRepository;

    /**
     * 导航页
     *
     * @return
     */
    @RequestMapping(value = {"/", "/" + Pages.INDEX}, method = RequestMethod.GET)
    public String index() {
        OUT.prt("request:", Pages.INDEX);
        return Pages.INDEX;
    }

    /**
     * 导航栏
     *
     * @return
     */
    @RequestMapping(value = "/" + Pages.NAVIGATION)
    public String showNavigation() {
        OUT.prt("request:", Pages.NAVIGATION);
        String res = Pages.NAVIGATION;
        return res;
    }

    /**
     * 导航栏II
     *
     * @return
     */
    @RequestMapping(value = "/" + Pages.NAVIGATION_SECOND)
    public String showNavigationSecond() {
        OUT.prt("request:", Pages.NAVIGATION_SECOND);
        String res = Pages.NAVIGATION_SECOND;
        return res;
    }

    /**
     * 排行榜界面
     *
     * @param start
     * @param model
     * @return
     */
    @RequestMapping(value = {"/" + Pages.RANK_LIST, "/" + Pages.RANK_LIST + "/{start}"}, method = RequestMethod.GET)
    public String showRankList(
            @PathVariable("start") int start,
            Model model) {
        OUT.prt("request", Pages.RANK_LIST);

        RankVO rankVO = runsRepository.queryRankList(start);
        List<RankEntityVO> entityList = rankVO.getEntityList();
        for (int i = 0; i < entityList.size(); i++) {
            RankEntityVO vo = entityList.get(i);
            vo.setRank(i + 1);
            vo.setRatio(vo.getAcProblemsNum() * 100 / vo.getSubmitProblemsNum());
        }
        model.addAttribute("vo", rankVO);
        OUT.prt("vo", rankVO);

        String res = Pages.RANK_LIST;
        return res;
    }

    /**
     * 显示运行结果状态
     *
     * @param start
     * @param statusDTO
     * @param model
     * @return
     */
    @RequestMapping(value = "/" + Pages.STATUS + "/{start}")
    public String showStatus(
            @PathVariable("start") int start,
            StatusDTO statusDTO,
            Model model) {
        OUT.prt("request", Pages.STATUS);
        OUT.prt("statusDTO", statusDTO);
        StatusVO vo = runsRepository.queryStatusList(start, statusDTO);
        model.addAttribute("vo", vo);
        OUT.prt("vo", vo);

        String res = Pages.STATUS;
        return res;
    }

    /**
     * 代码详情界面
     *
     * @param runId
     * @param model
     * @param principal
     * @return
     */
    @RequestMapping(value = "/" + Pages.CODE_DETAIL_PAGE + "/{runId}", method = RequestMethod.GET)
    public String CodePost(
            @PathVariable("runId") int runId,
            Model model,
            Principal principal) {
        OUT.prt("request", Pages.CODE_DETAIL_PAGE);
        OUT.prt("runId", runId);
        String id = principal.getName();
        OUT.prt("id", id);
        StatusEntityVO vo = runsRepository.queryCode(runId, id);

        UsersEntity usersEntity = usersRepository.findOne(id);

        if (vo.getId().equals(id)
                || vo.getOpen() == 1
                || usersEntity.getRoleId() == Constant.ADMIN
                || usersEntity.getRoleId() == Constant.TEACHER) {
        } else {
            vo = new StatusEntityVO();
        }
        model.addAttribute("entity", vo);
        OUT.prt("vo", vo);
        String res = Pages.CODE_DETAIL_PAGE;
        return res;
    }

    /**
     * 上传文件界面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.GET)
    public String uploadsFiles(
            Model model) {
        OUT.prt("request:", "uploadFile");
        model.addAttribute("usersEntity", new UsersEntity());
        return "uploadFile";
    }

    /**
     * 上传文件请求
     *
     * @param zip
     * @param model
     * @return
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadsFilesPost(
            @RequestPart("zip") @NotNull MultipartFile zip,
            Model model) {
        OUT.prt("post:", "uploadFile");
        try {
            MyFileUtils.unzipFile(zip.getInputStream());
//            zip.transferTo(new File("D:/uploadstmp/" + (System.currentTimeMillis() / 100)+ zip.getOriginalFilename().substring(zip.getOriginalFilename().lastIndexOf("\\") + 1)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("usersEntity", new UsersEntity());
        return "uploadFile";
    }

    /**
     * 管理问题界面
     *
     * @return
     */
    @RequestMapping(value = "/" + Pages.PROBLEM_MANAGER, method = RequestMethod.GET)
    public String managerProblem() {
        OUT.prt("request", Pages.PROBLEM_MANAGER);
        String res = Pages.TEACHER + "/" + Pages.PROBLEM_MANAGER;
        return res;
    }

    /**
     * 显示题库
     *
     * @return
     */
    @RequestMapping(value = "/" + Pages.SHOW_TEST_PAGE, method = RequestMethod.GET)
    public String showTest() {
        String res = Pages.TEACHER + "/" + Pages.SHOW_TEST_PAGE;
        return res;
    }
}
