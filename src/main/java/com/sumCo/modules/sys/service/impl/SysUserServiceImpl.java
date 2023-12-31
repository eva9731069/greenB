package com.sumCo.modules.sys.service.impl;

import com.sumCo.common.Constant;
import com.sumCo.modules.sys.dao.SysMenuDao;
import com.sumCo.modules.sys.dao.SysUserDao;
import com.sumCo.modules.sys.entity.SysMenu;
import com.sumCo.modules.sys.entity.SysUser;
import com.sumCo.modules.sys.redis.SysUserRedis;
import com.sumCo.modules.sys.service.SysRoleService;
import com.sumCo.modules.sys.service.SysUserRoleService;
import com.sumCo.modules.sys.service.SysUserService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuDao sysMenuDao;

    @Autowired
    private SysUserRedis sysUserRedis;

    @Override
    public List<String> queryAllPerms(Long userId) {
        return sysUserDao.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return sysUserDao.queryAllMenuId(userId);
    }

    @Override
    public SysUser queryByUserName(String username) {
        SysUser sysUser = sysUserRedis.get(username);
        if (sysUser == null) {
            sysUser = sysUserDao.queryByUserName(username);
            sysUserRedis.saveOrUpdate(sysUser);
        }
        return sysUser;
    }

    @Override
    public SysUser queryObject(Long id) {
        SysUser sysUser = sysUserRedis.get(id);
        if (sysUser == null) {
            sysUser = sysUserDao.queryObject(id);
            sysUserRedis.saveOrUpdate(sysUser);
        }
        return sysUser;
    }

    @Override
    public List<SysUser> queryList(Map<String, Object> map) {
        return sysUserDao.queryList(map);
    }

    @Override
    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\eva97\\Downloads\\";
        List<SysUser> list = sysUserDao.queryJsReport();
        File file = ResourceUtils.getFile("classpath:empList.jrxml");
        JasperReport jsReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        Map<String, Object> map = new HashMap<>();
        map.put("Jasper", "Java tech");//必要參數，可是看不出作用是什麼

        JasperPrint jsPrint = JasperFillManager.fillReport(jsReport, map, dataSource);

        if(reportFormat.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jsPrint,path+"empList.pdf");
        }
        if(reportFormat.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToPdfFile(jsPrint,path+"empList.html");
        }

        return "匯出report成功";
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysUserDao.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(SysUser user) {

        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(user.getPassword());
        user.setSalt(salt);
        sysUserDao.save(user);

        sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList(), user.getRoleName());

        sysUserRedis.saveOrUpdate(user);
    }

    @Override
    @Transactional
    public void update(SysUser user) {
        sysUserRedis.delete(user);

        if (StringUtils.isBlank(user.getPassword())) {
            throw new NullPointerException("密碼不可為空");
        }

        user.setPassword(user.getPassword());

        sysUserDao.update(user);

        sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList(), user.getRoleName());
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] ids) {
        for (Long id : ids) {
            SysUser sysUser = queryObject(id);
            sysUserRedis.delete(sysUser);
        }

        sysUserDao.deleteBatch(ids);
        sysUserDao.deleteBatchTwo(ids);


        sysUserRoleService.deleteBatch(ids);
    }

    @Override
    @Transactional
    public int updatePassword(SysUser user, String password, String newPassword) {
        sysUserRedis.delete(user);

        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("password", password);
        map.put("newPassword", newPassword);
        return sysUserDao.updatePassword(map);
    }

    @Override
    public Set<String> getUserPermissions(Long userId) {
        List<String> permsList;


        //系統管理員，擁有最高權限
        if (userId == Constant.SUPER_ADMIN) {
            List<SysMenu> menuList = sysMenuDao.queryList(new HashMap<>());
            permsList = new ArrayList<>(menuList.size());
            for (SysMenu menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
        }


        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }


}