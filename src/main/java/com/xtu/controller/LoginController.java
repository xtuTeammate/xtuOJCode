package com.xtu.controller;

import com.xtu.DB.UsersRepository;
import com.xtu.DB.entity.UsersEntity;
import com.xtu.constant.Constant;
import com.xtu.constant.Pages;
import com.xtu.tools.OUT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Ilovezilian on 2017/4/13.
 */
@Controller
public class LoginController {

    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping(value = "/" + Pages.LOGIN, method = RequestMethod.GET)
    public String login(Model model) {
        OUT.prt("login get", Pages.LOGIN);
        model.addAttribute(new UsersEntity());
        String res = Pages.LOGIN;
        return res;
    }

    @RequestMapping(value = {"/" + Pages.LOGIN, "/" + Pages.INFO}, method = RequestMethod.POST)
    public String loginPost(
            @NotNull @Valid UsersEntity usersEntiy,
            Errors errors,
            RedirectAttributes model) {
        OUT.prt("request", Pages.LOGIN + "," + Pages.INFO);
        OUT.prt("userEntity", usersEntiy);

        String res;
        if (errors.hasErrors()) {
            res = Pages.LOGIN;
            return res;
        }

        if (model.containsAttribute("usersEntity")) {
            res = Pages.INFO;
            return res;
        }
        // TODO: 2017/4/13 select form DB
        UsersEntity usersEntiyDB = usersRepository.findOne(usersEntiy.getId());

        if (null == usersEntiyDB) {
            model.addAttribute("message", "wrong use id or password");
            res = Pages.LOGIN;
            return res;
        }
        if (!usersEntiy.getPassword().equals(usersEntiyDB.getPassword())) {
            model.addAttribute("message", "password error");
            res = Pages.LOGIN;
            return res;
        }

//        int cont = usersRepository.count().intValue();
        model.addFlashAttribute("usersEntity from db", usersEntiyDB);
        res = "redirect:/" + getRoleType(usersEntiyDB.getRoleId()) + "/" + Pages.MAIN_PAGE;
        return res;
    }

    @RequestMapping(value = "/" + Pages.INFO, method = RequestMethod.GET)
    public String showUsersInfo(
            @NotNull @Valid UsersEntity usersEntiy,
            Errors errors,
            Model model) {
        OUT.prt("request", Pages.INFO);
        String res;
        if (!model.containsAttribute("usersEntity")) {
            res = Pages.LOGIN;
        } else {
            res = Pages.INFO;
        }
        return res;
    }

    @RequestMapping(value = "/" + Pages.REGISTER, method = RequestMethod.GET)
    public String register() {
        OUT.prt("request", Pages.REGISTER);
        String res = Pages.REGISTER;
        return res;
    }

    @RequestMapping(value = "/" + Pages.REGISTER, method = RequestMethod.POST)
    public String processRegistration(
            @NotNull @Valid UsersEntity usersEntity,
            Errors errors) {
        OUT.prt("request", Pages.REGISTER);
        OUT.prt("register user", usersEntity);
        String res;
        if (errors.hasErrors()) {
            res = Pages.REGISTER;
            return res;
        }
//        // TODO: 2017/4/12  save user profile to db
//        usersRepository.save(usersEntity);
        usersEntity.setRoleId(Constant.STUDENT);

        res = "redirect:/" + Pages.INFO;
        return res;
    }

    @RequestMapping(value = "/" + Pages.FORGET_PASSWORD, method = RequestMethod.GET)
    public String forgetPassword() {
        OUT.prt("request", Pages.FORGET_PASSWORD);
        String res = Pages.FORGET_PASSWORD;
        return res;
    }

    @RequestMapping(value = "/" + Pages.MODIFY_PASSWORD, method = RequestMethod.GET)
    public String modifyPassword() {
        OUT.prt("request", Pages.MODIFY_PASSWORD);
        String res = Pages.MODIFY_PASSWORD;
        return res;
    }

    @RequestMapping(value = "/" + Pages.MODIFY_USER_INFO, method = RequestMethod.GET)
    public String modifyUserInfo() {
        OUT.prt("request", Pages.MODIFY_USER_INFO);
        String res = Pages.MODIFY_USER_INFO;
        return res;
    }

    private String getRoleType(int roleId) {
        String res;
        if (roleId == Constant.ADMIN) {
            res = Pages.ADMIN;
        } else if (roleId == Constant.TEACHER) {
            res = Pages.TEACHER;
        } else {
            res = Pages.STUDENT;
        }
        return res;
    }

}
