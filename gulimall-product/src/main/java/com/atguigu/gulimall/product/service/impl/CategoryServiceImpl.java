package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

//    @Autowired
//    CategoryDao categoryDao;

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //1、查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
        //2、组装成父子的树形结构
        //2.1)找到所有的一级分类
        List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity ->
            categoryEntity.getParentCid() == 0
          //子菜单也有子菜单
        ).map(menu->{
            menu.setChildren(getChildren(menu,entities));
            return menu;
            //菜单排序
        }).sorted((menu1,menu2)->{
            return (menu1.getSort() == null? 0 : menu1.getSort()) - (menu2.getSort() == null? 0 :menu2.getSort());
        }).collect(Collectors.toList());


        return level1Menus;
    }

    @Override
    public void removeMenusByIds(List<Long> list) {
        // TODO 1、需要检查当前删除的菜单，是否被引用，被引用则不可删除。

        //逻辑删除
        baseMapper.deleteBatchIds(list);
    }

    /**
     * @param :catelogId
     * @return Long
     * @author perse
     * @description TODO 找到catelog的完整路径[父/子/Sun]
     * @date 2023/6/19 20:13
     */
    @Override
    public Long[] findCatelogPath(Long catelogId) {
        //[2,25,225]
        List<Long>  paths=new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);
        //把获取到的paths[225,25,2]逆序变为[2,25,225]
        Collections.reverse(parentPath);

        return  parentPath.toArray(new Long[parentPath.size()]);
    }


    /**
     * @description TODO 级联更新所有关联的数据
     * @date 2023/6/21 16:40
     */
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }

    //225,25,2
    private List<Long> findParentPath(Long catelogId,List<Long> paths){
        //1.收集当前节点id
        paths.add(catelogId);
        //获取当前节点对象
        CategoryEntity byId=this.getById(catelogId);
        if(byId.getParentCid()!=0){
            //如果当前节点对象还有父节点
            findParentPath(byId.getParentCid(),paths);
        }
        //统计一个节点及其所有父节点的path.
        return paths;

    }

    //递归查找所有菜单的子菜单,root -> 当前菜单， all -> 所有菜单
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {
        //获取一级子菜单，
       List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            //如果查找的菜单的父菜单==root菜单的ID，说明查找的菜单是root的一级子菜单
           return categoryEntity.getParentCid().equals(root.getCatId());
           //找到二级子菜单，递归查找
        }).map(categoryEntity -> {
            categoryEntity.setChildren(getChildren(categoryEntity,all));
            return categoryEntity;
            //对菜单进行排序
       }).sorted((menu1,menu2)->{
            return (menu1.getSort() == null? 0 : menu1.getSort()) - (menu2.getSort() == null? 0 :menu2.getSort());
       }).collect(Collectors.toList());


       return children;
    }

}