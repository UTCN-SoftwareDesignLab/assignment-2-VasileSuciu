package demo.service.sale;

import demo.model.validation.Notification;

public interface SaleService {

    Notification<Boolean> makeSale(String title, int quantity);

}
