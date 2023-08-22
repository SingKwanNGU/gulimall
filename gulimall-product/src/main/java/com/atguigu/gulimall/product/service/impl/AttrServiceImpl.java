package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.constant.ProductConstant;
import com.atguigu.gulimall.product.entity.*;
import com.atguigu.gulimall.product.vo.AttrGroupRelationVo;
import com.atguigu.gulimall.product.vo.AttrRespVo;
import com.atguigu.gulimall.product.vo.AttrVo;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.AttrDao;
import com.atguigu.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationServiceImpl relationService;

    @Autowired
    AttrGroupServiceImpl attrGroupService;

    @Autowired
    CategoryServiceImpl categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity=new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        //1 保存基本数据
        this.save(attrEntity);
        //2 保存关联关系  如果是基本信息类型才需要
        if(attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attr.getAttrGroupId()!=null){
            AttrAttrgroupRelationEntity relationEntity=new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationService.getBaseMapper().insert(relationEntity);
        }



    }

    @Override
    public PageUtils queryBaseAttr(Map<String, Object> params, Long catelogId, String type) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("attr_type","base".equalsIgnoreCase(type)?ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode():ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        //根据查询条件进行对queryWrapper的修改
        //如果catelogId不为0，进行的是精确查询，根据catelogId进行查询分类
        if(catelogId!=0){
            queryWrapper.eq("catelog_id",catelogId);
        }
        //模糊匹配查询条件获取
        String key = (String) params.get("key");
        if(StringUtils.hasText(key)){
            queryWrapper.and((wrapper)->{
               wrapper.eq("attr_id",key).or().like("attr_name",key);
            });
        }


        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );

        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> attrRespVos = records.stream().map((attrEntity) -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);
            //1 设置分类和分组的名字
            //1.1 先注入service层，使用AttrAttrgroupRelationServiceImpl relationService查出attr_id(属性id)
            //1.2 根据attrId查出他所属的attrGroupId分组,在根据他所属的attrGroupId分组查出attrGroupEntity
            //1.3 判断是否非空并进行set方法赋值
            if("base".equalsIgnoreCase(type)){
                AttrAttrgroupRelationEntity attrId = relationService.getBaseMapper().selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
                if (attrId != null && attrId.getAttrGroupId()!=null ) {
                    //获取到attrGroupId
                    Long attrGroupId = attrId.getAttrGroupId();
                    //根据attrGroupId查询出attrGroupEntity
                    AttrGroupEntity attrGroupEntity = attrGroupService.getBaseMapper().selectById(attrGroupId);
                    //set赋值
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }


            CategoryEntity categoryEntity = categoryService.getBaseMapper().selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }


            return attrRespVo;
        }).collect(Collectors.toList());

        //总结，使用添加了GroupName、CatelogName的新结果集attrRespVos取代原来的结果集
        pageUtils.setList(attrRespVos);
        return pageUtils;

    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        //先根据attrId查询出attrEntity
        AttrEntity attrEntity = this.getById(attrId);
        //将attrEntity封装到返回的AttrRespVo类型的respVo里
        AttrRespVo respVo=new AttrRespVo();
        BeanUtils.copyProperties(attrEntity,respVo);

        //TODO 获取attrGroupId、catelogId、catelogPath、attrGroupName、CatelogName
        //并封装进respVo里

        if(attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            //分组信息（属性分组关系查询-->属性分组ID-->属性分组实体-->属性分组组名）
            AttrAttrgroupRelationEntity attrAttrgroupRelation = relationService.getBaseMapper().selectById(attrId);
            if(attrAttrgroupRelation!=null) {

                Long attrGroupId = attrAttrgroupRelation.getAttrGroupId();
                respVo.setAttrGroupId(attrGroupId);
                AttrGroupEntity attrGroupEntity = attrGroupService.getBaseMapper().selectById(attrGroupId);
                if(attrGroupEntity!=null){
                    String attrGroupName = attrGroupEntity.getAttrGroupName();
                    respVo.setGroupName(attrGroupName);
                }
            }
        }


        //分类信息(获取catelogId-->catelogPath、分类实体-->分类实体名)
        Long catelogId = attrEntity.getCatelogId();
        if(catelogId!=null){
            Long[] catelogPath = categoryService.findCatelogPath(catelogId);
            respVo.setCatelogPath(catelogPath);
            CategoryEntity categoryEntity = categoryService.getBaseMapper().selectById(catelogId);
            if(categoryEntity!=null){
                respVo.setCatelogName(categoryEntity.getName());
            }
        }

        return respVo;
    }


    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        //把传送回来详细的attr信息封装到attrEntity,更新attrEntity里包含的所有属性的基本的信息,并更新attrEntity未包含的数据库中的attrId和attrGroupId。
        AttrEntity attrEntity=new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.updateById(attrEntity);

        if(attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            //修改分组关联信息
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrId(attr.getAttrId());
            relationEntity.setAttrGroupId(attr.getAttrGroupId());

            //需要进行一次判断，如果根据attrid查询有无关联属性，无则添加，有则更新。
            Integer count = relationService.getBaseMapper().selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            if (count > 0) {
                //说明有结果，有关联属性，使用修改操作update

                relationService.getBaseMapper().update(relationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            } else {
                relationService.getBaseMapper().insert(relationEntity);
            }
        }

    }

    @Override//根据分组Id attrgroupId查出所有关联的属性
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        List<AttrAttrgroupRelationEntity> entities = relationService.getBaseMapper().selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));

        List<Long> attrIds = entities.stream().map((attr) -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());

        if(attrIds==null || attrIds.size()==0){
            return null;
        }
        Collection<AttrEntity> attrEntities = this.listByIds(attrIds);
        return (List<AttrEntity>) attrEntities;
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
        List<AttrAttrgroupRelationEntity> entities = Arrays.asList(vos).stream().map((item) -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        relationService.getBaseMapper().deleteBatchRelation(entities);
    }

    //获取当前分组没有关联的所有属性
    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {
        //1 当前分组只能关联自己所属的分类里的所有属性
        AttrGroupEntity attrGroupEntity = attrGroupService.getBaseMapper().selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();//获取当前分组的所属分类
        //2 当前分组只能关联别的分组没有引用的属性
        //2.1 根据catelogId获取当前分类下的其他分组
        List<AttrGroupEntity> group = attrGroupService.getBaseMapper().selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<Long> collect = group.stream().map((item) -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());
        //2.2 根据其他分组attrgroupId获取它关联的属性

            List<AttrAttrgroupRelationEntity> groupId = relationService.getBaseMapper().selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", collect));
            List<Long> attrIds = groupId.stream().map((item) -> {
                return item.getAttrId();
            }).collect(Collectors.toList());//获取到其他分组的所有属性的attrid集合

        //2.3 使用1中的catelogId获取到当前分类的所有属性 - 2.2中获取到的所有属性
        QueryWrapper<AttrEntity> wrapper=new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId).eq("attr_type",ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if(attrIds!=null && attrIds.size()>0){
            wrapper.notIn("attr_id", attrIds);
        }
        String key=(String)params.get("key");
        if(StringUtils.hasText(key)){
            wrapper.and((w)->{
                w.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page=this.page(new Query<AttrEntity>().getPage(params),wrapper);
        PageUtils pageUtils=new PageUtils(page);
        return pageUtils;


    }



}