package demo.service.sale;

import demo.model.validation.Notification;

public interface SaleService {

    Notification<Boolean> makeSale(Long id, int quantity);

}
