package com.cloud.backend.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.cloud.backend.dao.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.cloud.backend.model.Menu;
import com.cloud.backend.service.MenuService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;
	@Autowired
	private RoleMenuDao roleMenuDao;
	@Autowired
	private SysUserPermissionDao sysUserPermissionDao;
	@Autowired
	private PermissionMenuDao permissionMenuDao;

	@Transactional
	@Override
	public void save(Menu menu) {
		menu.setCreateTime(new Date());
		menu.setUpdateTime(menu.getCreateTime());

		menuDao.save(menu);
		log.info("新增菜单：{}", menu);
	}

	@Transactional
	@Override
	public void update(Menu menu) {
		menu.setUpdateTime(new Date());

		menuDao.update(menu);
		log.info("修改菜单：{}", menu);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		Menu menu = menuDao.findById(id);

		menuDao.deleteByParentId(id);
		menuDao.delete(id);
		roleMenuDao.delete(null, id);

		log.info("删除菜单：{}", menu);
	}

	/**
	 * 给角色设置菜单<br>
	 * 我们这里采用先删除后插入，这样更简单
	 *
	 * @param roleId
	 * @param menuIds
	 */
	@Transactional
	@Override
	public void setMenuToRole(Long roleId, Set<Long> menuIds) {
		roleMenuDao.delete(roleId, null);

		if (!CollectionUtils.isEmpty(menuIds)) {
			menuIds.forEach(menuId -> {
				roleMenuDao.save(roleId, menuId);
			});
		}
	}

	@Override
	public List<Menu> findByRoles(Set<Long> roleIds) {
		return roleMenuDao.findMenusByRoleIds(roleIds);
	}

	@Override
	public List<Menu> findAll() {
		return menuDao.findAll();
	}

	@Override
	public Menu findById(Long id) {
		return menuDao.findById(id);
	}

	@Override
	public Set<Long> findMenuIdsByRoleId(Long roleId) {
		return roleMenuDao.findMenuIdsByRoleId(roleId);
	}

	@Override
	public Set<Long> findMenuIdsByUserId(Long userId) {
		return sysUserPermissionDao.findMenuIdsByUserId(userId);
	}

	@Override
	public List<Menu> findByUserId(Long userId) {
		List<Menu> result = permissionMenuDao.getMenusByUserId(userId);
		// menu.id = 0 是 所有页面
		Menu allMenuObj = new Menu();
		allMenuObj.setId(0L);

		List<Menu> allMenus = menuDao.findAll();
		// 如果设置了这个页面，表示有权查看所有页面
		if (result.contains(allMenuObj)) {
			return allMenus;
		}

		// 找出getParentId = 0的menu 放进结果中
		List<Menu> topMenus = result.parallelStream().map(i -> getTopParent(i, allMenus))
				.collect(Collectors.toList());
		result.addAll(topMenus);
		return result;
	}

	private Menu getTopParent(Menu menu, List<Menu> allMenus) {
		if (menu.getParentId().equals(0L)) {
			return menu;
		}
		Menu res = allMenus.parallelStream()
				.filter(i -> i.getId().equals(menu.getParentId()))
				.findFirst().get();
		if(res.getParentId().equals(0L)) {
			return res;
		} else {
			return getTopParent(res, allMenus);
		}
	}

//	@Override
//	public List<Menu> getChildMenus(Long menuId) {
//		List<Menu> result = menuDao.findChildMenus(menuId);
//		if (!CollectionUtils.isEmpty(result)) {
//			result.forEach(i -> );
//		}
//		return result;
//	}

	@Override
	public void setChild(Menu menu, List<Menu> menus) {
		List<Menu> child = menus.stream().filter(m -> m.getParentId().equals(menu.getId()))
				.collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(child)) {
			menu.setChild(child);
			// 2018.06.09递归设置子元素，多级菜单支持
			child.parallelStream().forEach(c -> {
				setChild(c, menus);
			});
		}
	}
}
