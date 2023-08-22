package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.service.AttrAttrgroupRelationService;
import com.atguigu.gulimall.product.service.AttrService;
import com.atguigu.gulimall.product.vo.AttrGroupRelationVo;
import com.atguigu.gulimall.product.vo.AttrGroupWithAttrsVo;
import com.atguigu.gulimall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.AttrGroupDao;
import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.service.AttrGroupService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {
    @Lazy
    @Autowired
    AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        String key=(String)params.get("key");
        //此时我们要查询的SQL语句为
        //select * from pms_attr_group where catelog_id=？ and (attr_group_id=key or attr_group_name like %key%)
        //等值查询，先给catelog_id赋值为catelogId
        QueryWrapper<AttrGroupEntity> wrapper=new QueryWrapper<AttrGroupEntity>();
        //对关键字key进行判断， 非空进入此支路
        if(!StringUtils.isEmpty(key)){
            wrapper.and((obj)->{
                obj.eq("attr_group_id",key).or().like("attr_group_name",key);
            });
        }
        //如果没传catelogId就查询所有的分页信息
        if(catelogId == 0){
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    wrapper
            );
            return new PageUtils(page);
        }else {
            //此时catelogId不为0，还要获取params里是否传了关键字，进行也要进行判断。
            wrapper.eq("catelog_id",catelogId);

            //将从params获取到的分页信息和查询条件wrapper封装到page
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
            //利用工具类给page的属性赋值做好封装。
            return  new PageUtils(page);

        }

    }

    /**
     * @param catelogId: 三级分类id
     * @return List<AttrGroupWithAttrsVo>
     * @author perse
     * @description TODO 根据catelogId查出当前分类下的所有属性分组，以及属性分组下的所有属性，封装成List<AttrGroupWithAttrsVo>返回
     * @date 2023/6/23 20:16
     */
    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {
        //1.查询当前分类下的属性分组信息
        List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        //2.查出每个分组下的所有属性(先获取分组id->attrGroupId-->再去attr_attrgroup_relation表里查出所有关联的属性)
        List<AttrGroupWithAttrsVo> collect=new ArrayList<>();
        if(attrGroupEntities!=null){
                collect = attrGroupEntities.stream().map((group) -> {
                AttrGroupWithAttrsVo attrsVo = new AttrGroupWithAttrsVo();
                BeanUtils.copyProperties(group,attrsVo);//把groupId复制到attrsVo
                List<AttrEntity> attrs = attrService.getRelationAttr(attrsVo.getAttrGroupId());
                if(attrs!=null){
                    //把attrs设置进去
                    attrsVo.setAttrs(attrs);
                }
                return attrsVo;
            }).collect(Collectors.toList());
        }


        return collect;

    }


}