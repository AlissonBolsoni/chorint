package br.com.alissonbolsoni.chorint.domains.entity;

import br.com.alissonbolsoni.chorint.domains.dtos.UserOrderDto;
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
public class UserOrder {

  private int userId;
  private String name;
  private List<Order> orders;

  public UserOrderDto toDto() {
    return new UserOrderDto(
        userId, name, orders.stream().map(Order::toDto).collect(Collectors.toList()));
  }
}
