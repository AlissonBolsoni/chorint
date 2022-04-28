package br.com.alissonbolsoni.chorint.services.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.alissonbolsoni.chorint.services.FileConverter;
import br.com.alissonbolsoni.chorint.templates.UserOrderTemplate;
import br.com.alissonbolsoni.chorint.utils.Log;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class ProcessConvertionImplTest {

  private ProcessConvertionImpl processConvertion;
  @Mock FileConverter fileConverter;
  @Mock Log log;

  @BeforeEach
  void before() {
    this.processConvertion = new ProcessConvertionImpl(fileConverter, log);
    Mockito.lenient().doNothing().when(log).error(anyString(), any());
  }

  @Test
  void process_a_file_list() {
    // given
    final var path1 = "./files/data_1.txt";
    final var path2 = "./files/data_1.txt";
    final var file1 = new File(path1).toPath();
    final var file2 = new File(path2).toPath();
    final var files = Arrays.asList(file1, file2);

    when(fileConverter.convert(anyList())).thenReturn(UserOrderTemplate.basicList());

    // when
    assertDoesNotThrow(
        () -> {
          final var convertedList = processConvertion.processFiles(files);

          assertFalse(convertedList.isEmpty());
        });

    // then
    verify(log, times(0)).error(eq("Can not open the file"), any());
  }

  @SneakyThrows
  @Test
  void get_exception_when_try_to_get_lines() {
    // given
    final var path = "./files/data_12.txt";
    final var file = new File(path).toPath();
    final var files = Collections.singletonList(file);

    //     when
    assertDoesNotThrow(
        () -> {
          final var convertedList = processConvertion.processFiles(files);

          assertTrue(convertedList.isEmpty());
        });

    // then
    verify(log, times(1)).error(eq("Can not open the file"), any());
  }

  @Test
  void try_factory_class() {
    // given

    // when
    assertDoesNotThrow(
        () -> {
          final var processConvertion = new ProcessConvertionFactory().create();

          assertNotNull(processConvertion);
        });

    // then
  }
}
