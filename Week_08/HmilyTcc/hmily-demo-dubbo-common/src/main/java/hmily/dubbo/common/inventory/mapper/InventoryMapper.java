package hmily.dubbo.common.inventory.mapper;

import hmily.dubbo.common.inventory.dto.InventoryDTO;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryMapper {

    @Update("update inventory set total_inventory = total_inventory - #{count}," +
            " lock_inventory= lock_inventory + #{count} " +
            " where product_id =#{productId} and total_inventory >= #{count} ")
    int decrease(InventoryDTO inventoryDTO);

    @Update("update inventory set " +
            " lock_inventory = lock_inventory - #{count} " +
            " where product_id =#{productId} and lock_inventory >= #{count} ")
    int confirm(InventoryDTO inventoryDTO);

    @Update("update inventory set total_inventory = total_inventory + #{count}," +
            " lock_inventory= lock_inventory - #{count} " +
            " where product_id =#{productId}  and lock_inventory >= #{count} ")
    int cancel(InventoryDTO inventoryDTO);
}
