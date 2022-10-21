package com.metoo.nspm.core.mapper;

import com.metoo.nspm.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    Order getObjByOrderId(Long orderId);

    Order getObjByOrderNo(String orderNo);

    int save(Order instance);
}
