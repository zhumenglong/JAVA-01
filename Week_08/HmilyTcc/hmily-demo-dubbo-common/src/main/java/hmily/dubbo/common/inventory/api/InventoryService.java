package hmily.dubbo.common.inventory.api;

import hmily.dubbo.common.inventory.dto.InventoryDTO;
import org.dromara.hmily.annotation.Hmily;

public interface InventoryService {

    @Hmily
    Boolean decrease(InventoryDTO inventoryDTO);

    @Hmily
    String mockWithTryException(InventoryDTO inventoryDTO);

    @Hmily
    Boolean mockWithTryTimeout(InventoryDTO inventoryDTO);

    @Hmily
    Boolean mockWithConfirmException(InventoryDTO inventoryDTO);

    @Hmily
    Boolean mockWithConfirmTimeout(InventoryDTO inventoryDTO);

}
