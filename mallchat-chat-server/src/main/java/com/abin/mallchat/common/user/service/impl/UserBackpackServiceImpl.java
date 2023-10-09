package com.abin.mallchat.common.user.service.impl;

import com.abin.mallchat.common.common.annotation.RedissonLock;
import com.abin.mallchat.common.common.domain.enums.IdempotentEnum;
import com.abin.mallchat.common.common.domain.enums.YesOrNoEnum;
import com.abin.mallchat.common.user.dao.UserBackpackDao;
import com.abin.mallchat.common.user.domain.entity.ItemConfig;
import com.abin.mallchat.common.user.domain.entity.UserBackpack;
import com.abin.mallchat.common.user.domain.enums.ItemTypeEnum;
import com.abin.mallchat.common.user.service.IUserBackpackService;
import com.abin.mallchat.common.user.service.cache.ItemCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserBackpackServiceImpl implements IUserBackpackService {
    @Autowired
    private UserBackpackDao userBackpackDao;
    @Autowired
    private ItemCache itemCache;

    @Override
    public void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        String idempotent = getIdempotent(itemId, idempotentEnum, businessId);
        doAcquireItem(uid, itemId, idempotent);
    }

    private String getIdempotent(Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        return String.format("%d_%d_%s", itemId, idempotentEnum.getType(), businessId);
    }

    @RedissonLock(key = "#idempotent", waitTime = 5000)
    private void doAcquireItem(Long uid, Long itemId, String idempotent) {
        // 一锁
        // 二判
        UserBackpack userBackpack = userBackpackDao.getByIdp(idempotent);
        if (Objects.nonNull(userBackpack)) {
            return;
        }
        ItemConfig itemConfig = itemCache.getById(itemId);
        if (ItemTypeEnum.BADGE.getType().equals(itemConfig.getType())) {
            Integer countByValidItemId = userBackpackDao.getCountByValidItemId(uid, itemId);
            if (countByValidItemId > 0) { // 已经有勋章了不放
                return;
            }
        }
        // 三更新
        UserBackpack insert = UserBackpack.builder()
                .uid(uid)
                .itemId(itemId)
                .status(YesOrNoEnum.NO.getStatus())
                .idempotent(idempotent)
                .build();
        userBackpackDao.save(insert);
        // todo 用户收到物品的事件
    }
}
