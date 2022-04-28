package br.com.alissonbolsoni.chorint.domains.entity;

import br.com.alissonbolsoni.chorint.domains.dtos.OrderDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {

  private int orderId;
  private Double total;
  private LocalDate date;
  private List<Product> products;

  public void increaseTotal(final double value) {
    this.total = this.total + value;
  }

  public OrderDto toDto() {
    final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("2021-12-01");

    return new OrderDto(
        orderId,
        total,
        dateFormat.format(date),
        products.stream().map(Product::toDto).collect(Collectors.toList()));
  }
}
