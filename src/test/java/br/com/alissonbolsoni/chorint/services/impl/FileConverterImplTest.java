package br.com.alissonbolsoni.chorint.services.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import br.com.alissonbolsoni.chorint.services.FileConverter;
import br.com.alissonbolsoni.chorint.utils.Log;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class FileConverterImplTest {

  private static final String LINE_USER_1_ORDER_1_PRODUCT_1 =
      "0000000070                              Palmer Prosacco00000007530000000003     1836.0020210308";
  private static final String LINE_USER_1_ORDER_1_PRODUCT_2 =
      "0000000070                              Palmer Prosacco00000007530000000004      618.0020210308";

  private static final String LINE_WITH_NOT_CORRECT_SIZE =
      "0000000070                              Palmer Prosacco00000007530000000004      618.79202103083";
  private static final String LINE_STARTS_WITH_CHAR =
      "A000000070                              Palmer Prosacco00000007530000000004      618.7920210308";
  private static final String LINE_FINISH_WITH_CHAR =
      "0000000070                              Palmer Prosacco00000007530000000004      618.792021030A";

  private FileConverter fileConverter;
  @Mock private Log log;

  @BeforeEach()
  void before() {
    fileConverter = new FileConverterImpl(log);
    Mockito.lenient().doNothing().when(log).error(anyString(), any());
  }

  @Test
  void not_convert_when_lines_is_empty() {
    //    given
    final List<String> lines = Collections.emptyList();

    //    when
    assertDoesNotThrow(
        () -> {
          final var converted = fileConverter.convert(lines);
          assertTrue(converted.isEmpty());
        });

    //    then
    verify(log, times(1)).error(eq("File is empty"));
  }

  @Test
  void not_convert_when_lines_has_not_correct_size() {
    //    given
    final List<String> lines = Collections.singletonList(LINE_WITH_NOT_CORRECT_SIZE);

    //    when
    assertDoesNotThrow(
        () -> {
          final var converted = fileConverter.convert(lines);
          assertTrue(converted.isEmpty());
        });

    //    then
    verify(log, times(1)).error(eq("File is empty"));
  }

  @Test
  void not_convert_when_lines_not_starts_with_number() {
    //    given
    final List<String> lines = Collections.singletonList(LINE_STARTS_WITH_CHAR);

    //    when
    assertDoesNotThrow(
        () -> {
          final var converted = fileConverter.convert(lines);
          assertTrue(converted.isEmpty());
        });

    //    then
    verify(log, times(1)).error(eq("File is empty"));
  }

  @Test
  void not_convert_when_lines_not_finish_with_number() {
    //    given
    final List<String> lines = Collections.singletonList(LINE_FINISH_WITH_CHAR);

    //    when
    assertDoesNotThrow(
        () -> {
          final var converted = fileConverter.convert(lines);
          assertTrue(converted.isEmpty());
        });

    //    then
    verify(log, times(1)).error(eq("File is empty"));
  }

  @Test
  void should_retun_one_user_order_with_one_order_and_two_products() {
    //    given
    final var lines = Arrays.asList(LINE_USER_1_ORDER_1_PRODUCT_1, LINE_USER_1_ORDER_1_PRODUCT_2);

    //    when
    assertDoesNotThrow(
        () -> {
          final var converted = fileConverter.convert(lines);
          assertFalse(converted.isEmpty());
          assertEquals(1, converted.size());
          assertEquals(1, converted.get(0).getOrders().size());
          assertEquals(2454.00, converted.get(0).getOrders().get(0).getTotal());
          assertEquals(2, converted.get(0).getOrders().get(0).getProducts().size());
        });

    //    then
    verify(log, times(0)).error(eq("File is empty"));
  }

  @Test
  void try_factory_class() {
    // given

    // when
    assertDoesNotThrow(
        () -> {
          final var fileConverter = new FileConverterFactory().create();

          assertNotNull(fileConverter);
        });

    // then
  }
}
