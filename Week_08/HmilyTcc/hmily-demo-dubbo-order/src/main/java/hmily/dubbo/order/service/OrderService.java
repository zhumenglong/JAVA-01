package hmily.dubbo.order.service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface OrderService {

    String orderPay(Integer count, BigDecimal amount);

    String mockInventoryWithTryException(Integer count, BigDecimal amount);

    @Transactional
    String mockInventoryWithTryTimeout(Integer count, BigDecimal amount);

    String mockInventoryWithConfirmTimeout(Integer count, BigDecimal amount);

    String mockInventoryWithConfirmException(Integer count, BigDecimal amount);

}
