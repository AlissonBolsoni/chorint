package br.com.alissonbolsoni.chorint.templates;

import br.com.alissonbolsoni.chorint.domains.entity.Order;
import br.com.alissonbolsoni.chorint.domains.entity.Product;
import br.com.alissonbolsoni.chorint.domains.entity.UserOrder;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class UserOrderTemplate {

  public static List<UserOrder> basicList() {

    final var userOrder =
        new UserOrder(
            1,
            "name",
            Collections.singletonList(
                new Order(
                    1, 10.00, LocalDate.now(), Collections.singletonList(new Product(1, 10.00)))));

    return Collections.singletonList(userOrder);
  }
}
