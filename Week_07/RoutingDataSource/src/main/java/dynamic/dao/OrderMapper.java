package dynamic.dao;

import dynamic.entity.OrderDO;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {

    OrderDO selectById(@Param("orderId") Integer orderId);

    Integer updateById(@Param("entity") OrderDO orderDO);
}
