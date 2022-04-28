package br.com.alissonbolsoni.chorint.services.impl;

import static java.util.Objects.isNull;

import br.com.alissonbolsoni.chorint.domains.entity.Order;
import br.com.alissonbolsoni.chorint.domains.entity.Product;
import br.com.alissonbolsoni.chorint.domains.entity.UserOrder;
import br.com.alissonbolsoni.chorint.services.FileConverter;
import br.com.alissonbolsoni.chorint.utils.Log;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileConverterImpl implements FileConverter {

  private static final String DATE_FORMAT = "yyyyMMdd";
  private static final String CONCAT_CHAR = "-";
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

  private final Log log;

  @Override
  public List<UserOrder> convert(List<String> lines) {

    if (!lines.isEmpty() && canConvert(lines.get(0))) {

      final var usersMap = new HashMap<Integer, UserOrder>();
      final var ordersMap = new HashMap<String, Order>();
      lines.forEach(
          line -> {
            final var userIdStr = line.substring(0, 10);
            final var userId = Integer.parseInt(userIdStr);
            final var userName = line.substring(10, 55).trim();
            final var orderIdStr = line.substring(55, 65);
            final var orderId = Integer.parseInt(orderIdStr);
            final var productId = Integer.parseInt(line.substring(65, 75));
            final var productValue = Double.parseDouble(line.substring(75, 87));
            final var date = LocalDate.parse(line.substring(87), formatter);

            final var userMap = usersMap.get(userId);
            if (isNull(userMap)) {
              usersMap.put(userId, new UserOrder(userId, userName, new ArrayList<>()));
            }

            final var userOrderId = userIdStr.concat(CONCAT_CHAR).concat(orderIdStr);
            final var orderMap = ordersMap.get(userOrderId);
            if (isNull(orderMap)) {
              final List<Product> products = new ArrayList<>();
              products.add(new Product(productId, productValue));
              final var order = new Order(orderId, productValue, date, products);
              ordersMap.put(userOrderId, order);
            } else {
              orderMap.increaseTotal(productValue);
              orderMap.getProducts().add(new Product(productId, productValue));
            }
          });

      ordersMap.forEach(
          (k, v) -> {
            final var userIdMap =
                Arrays.stream(k.split(CONCAT_CHAR)).collect(Collectors.toList()).get(0);

            final var user = usersMap.get(Integer.parseInt(userIdMap));
            user.getOrders().add(v);
          });

      return new ArrayList<>(usersMap.values());

    } else {
      log.error("File is empty");
      return Collections.emptyList();
    }
  }
}
