package cn.com.self.controller;

import cn.com.self.domain.TbSysUser;
import cn.com.self.dto.BaseResult;
import cn.com.self.service.AdminService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 登录
     * @param loginCode
     * @param password
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public BaseResult login(String loginCode, String password){
        BaseResult baseResult = checkLogin(loginCode, password);  //检查账号和密码是否为空
        if(baseResult != null) return baseResult;

        TbSysUser tbSysUser = adminService.login(loginCode, password);

        if(tbSysUser != null){  //登录成功
            return BaseResult.ok(tbSysUser);
        }
        else {  //登录失败
            return BaseResult.notOk(Lists.newArrayList(
                    new BaseResult.Error("", "登录失败")
            ));
        }
    }

    /**
     * 检查账号或密码是否为空
     * @param loginCode
     * @param password
     * @return
     */
    private BaseResult checkLogin(String loginCode, String password){  //
        BaseResult baseResult = null;

        if(StringUtils.isBlank(loginCode) || StringUtils.isBlank(password)){
            baseResult = new BaseResult().notOk(Lists.newArrayList(
                    new BaseResult.Error("loginError","账号或密码为空")
            ));
        }

        return baseResult;
    }
}
