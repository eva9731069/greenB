package com.sumCo.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumCo.common.Constant;
import com.sumCo.common.Constant.MenuType;
import com.sumCo.modules.sys.dao.SysMenuDao;
import com.sumCo.modules.sys.entity.SysMenu;
import com.sumCo.modules.sys.service.SysMenuService;
import com.sumCo.modules.sys.service.SysUserService;

import java.util.*;

@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuDao sysMenuDao;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<SysMenu> queryListByParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenu> menuList = queryListByParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }

        List<SysMenu> userMenuList = new ArrayList<>();
        for (SysMenu menu : menuList) {
            if (menuIdList.contains(menu.getId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenu> queryListByParentId(Long parentId) {
        return sysMenuDao.queryListByParentId(parentId);
    }

    @Override
    public List<SysMenu> queryNotButtonList() {
        return sysMenuDao.queryNotButtonList();
    }

    @Override
    public List<SysMenu> getUserMenuList(Long userId) {
        //系統管理員，擁有最高權限
        if (userId == Constant.SUPER_ADMIN) {
            return getAllMenuList(null);
        }


        List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public SysMenu queryObject(Long id) {
        return sysMenuDao.queryObject(id);
    }

    @Override
    public List<SysMenu> queryList(Map<String, Object> map) {
        return sysMenuDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysMenuDao.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(SysMenu menu) {
        sysMenuDao.save(menu);
    }

    @Override
    @Transactional
    public void update(SysMenu menu) {
        sysMenuDao.update(menu);
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] ids) {
        sysMenuDao.deleteBatch(ids);
    }

    @Override
    public List<SysMenu> queryUserList(Long userId) {
        return sysMenuDao.queryUserList(userId);
    }


    private List<SysMenu> getAllMenuList(List<Long> menuIdList) {

        List<SysMenu> menuList = queryListByParentId(0L, menuIdList);

        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }


    private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<Long> menuIdList) {
        List<SysMenu> subMenuList = new ArrayList<SysMenu>();

        for (SysMenu entity : menuList) {
            if (entity.getType() == MenuType.CATALOG.getValue()) {
                entity.setList(getMenuTreeList(queryListByParentId(entity.getId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }
}
