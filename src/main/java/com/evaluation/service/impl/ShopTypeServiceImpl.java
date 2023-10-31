package com.evaluation.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.json.JSONUtil;
import com.evaluation.entity.ShopType;
import com.evaluation.mapper.ShopTypeMapper;
import com.evaluation.service.IShopTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.evaluation.utils.RedisConstants.CACHE_SHOPTYPE_KEY;
import static com.evaluation.utils.RedisConstants.CACHE_SHOPTYPE_TTL;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public List<ShopType> queryTypeList() {

        String key = CACHE_SHOPTYPE_KEY;
        List<ShopType> typeList=new ArrayList<>();

        if(stringRedisTemplate.hasKey(key)){
            Long size = stringRedisTemplate.opsForList().size(key);

            for (int i = 0; i < size; i++) {
                typeList.add(JSONUtil.toBean(stringRedisTemplate.opsForList().index(key,i), ShopType.class));
            }
            return typeList;
        }

        typeList = this.query().orderByAsc("sort").list();

        for (int i = 0; i < typeList.size(); i++) {
            stringRedisTemplate.opsForList().rightPush(key,JSONUtil.toJsonStr(typeList.get(i)));
        }
        stringRedisTemplate.expire(key,CACHE_SHOPTYPE_TTL, TimeUnit.MINUTES);
        return typeList;
    }
}
