package br.com.alissonbolsoni.chorint.usecases.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.alissonbolsoni.chorint.exceptions.ArgumentNullOrEmptyException;
import br.com.alissonbolsoni.chorint.services.FileManager;
import br.com.alissonbolsoni.chorint.services.ProcessConvertion;
import br.com.alissonbolsoni.chorint.templates.FileConvertedTemplate;
import br.com.alissonbolsoni.chorint.usecases.NormalizeFile;
import br.com.alissonbolsoni.chorint.utils.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class NormalizeFileImplTest {

  private NormalizeFile usecase;
  @Mock ProcessConvertion processConvertion;
  @Mock ObjectMapper mapper;
  @Mock FileManager fileManager;
  @Mock Log log;

  @BeforeEach
  void before() {
    this.usecase = new NormalizeFileImpl(processConvertion, mapper, fileManager, log);
    Mockito.lenient().doNothing().when(log).error(anyString(), any());
  }

  @Test
  void arguments_must_be_not_null() {
    // given

    // when
    assertThrows(ArgumentNullOrEmptyException.class, () -> this.usecase.execute(null));

    // then
  }

  @Test
  void arguments_must_be_not_empty() {
    // given
    final String[] arguments = new String[] {};
    // when
    assertThrows(ArgumentNullOrEmptyException.class, () -> this.usecase.execute(arguments));

    // then
  }

  @Test
  void file_not_exists() {
    // given
    final String[] arguments = new String[] {"./file"};

    // when
    Mockito.doNothing().when(log).info(anyString(), any());
    assertDoesNotThrow(() -> this.usecase.execute(arguments));

    // then
    verify(log, times(1)).info(eq("Finish process because has nothing to process"), any());
    verify(log, times(0)).error(anyString(), any());
    verify(processConvertion, times(0)).processFiles(anyList());
  }

  @SneakyThrows
  @Test
  void process_path_fail_when_convert_to_json() {
    // given
    final var path = "./files";
    final String[] arguments = new String[] {path};
    final var file = new File(path).toPath();
    final var files = Files.list(file).collect(Collectors.toList());
    final var fileConverted = FileConvertedTemplate.basic();

    // when
    when(fileManager.getAllFilesFromPath(any(Path.class))).thenReturn(files);
    when(processConvertion.processFiles(files))
        .thenReturn(Collections.singletonList(fileConverted));
    when(mapper.writeValueAsString(anyList())).thenThrow(JsonProcessingException.class);

    assertDoesNotThrow(() -> this.usecase.execute(arguments));

    // then
    verify(log, times(0)).info(anyString(), any());
    verify(log, times(1)).error(eq("Fail to convert file {} to json. Error message: {}"), any());
    verify(processConvertion, times(1)).processFiles(files);
    verify(mapper, times(1)).writeValueAsString(anyList());
    verify(fileManager, times(0)).createFileWithContent(anyString(), anyString(), anyString());
  }

  @SneakyThrows
  @Test
  void process_path_fail_when_save_file() {
    // given
    final var path = "./files";
    final String[] arguments = new String[] {path};
    final var file = new File(path).toPath();
    final var files = Files.list(file).collect(Collectors.toList());
    final var fileConverted = FileConvertedTemplate.basic();

    // when
    when(fileManager.getAllFilesFromPath(any(Path.class))).thenReturn(files);
    when(processConvertion.processFiles(files))
        .thenReturn(Collections.singletonList(fileConverted));
    when(mapper.writeValueAsString(anyList())).thenReturn("json");
    doThrow(IOException.class)
        .when(fileManager)
        .createFileWithContent(anyString(), anyString(), anyString());

    assertDoesNotThrow(() -> this.usecase.execute(arguments));

    //    "Fail to create file {}. Error message: {}", fileName, e.getMessage()

    // then
    verify(log, times(0)).info(anyString(), any());
    verify(log, times(1)).error(eq("Fail to create file {}. Error message: {}"), any());
    verify(processConvertion, times(1)).processFiles(files);
    verify(mapper, times(1)).writeValueAsString(anyList());
    verify(fileManager, times(1)).createFileWithContent(anyString(), anyString(), anyString());
  }

  @SneakyThrows
  @Test
  void process_path() {
    // given
    final var path = "./files";
    final String[] arguments = new String[] {path};
    final var file = new File(path).toPath();
    final var files = Files.list(file).collect(Collectors.toList());
    final var fileConverted = FileConvertedTemplate.basic();

    // when
    when(fileManager.getAllFilesFromPath(any(Path.class))).thenReturn(files);
    when(processConvertion.processFiles(files))
        .thenReturn(Collections.singletonList(fileConverted));
    when(mapper.writeValueAsString(anyList())).thenReturn("json");
    doNothing().when(fileManager).createFileWithContent(anyString(), anyString(), anyString());

    assertDoesNotThrow(() -> this.usecase.execute(arguments));

    // then
    verify(processConvertion, times(1)).processFiles(files);
    verify(mapper, times(1)).writeValueAsString(anyList());
    verify(fileManager, times(1)).createFileWithContent(anyString(), anyString(), anyString());
  }

  @SneakyThrows
  @Test
  void process_file() {
    // given
    final var path = "./files/data_1.txt";
    final String[] arguments = new String[] {path};
    final var file = new File(path).toPath();
    final var files = Collections.singletonList(file);
    final var fileConverted = FileConvertedTemplate.basic();

    // when
    when(fileManager.getAllFilesFromPath(any(Path.class))).thenReturn(files);
    when(processConvertion.processFiles(files))
        .thenReturn(Collections.singletonList(fileConverted));
    when(mapper.writeValueAsString(anyList())).thenReturn("json");
    doNothing().when(fileManager).createFileWithContent(anyString(), anyString(), anyString());

    assertDoesNotThrow(() -> this.usecase.execute(arguments));

    // then
    verify(processConvertion, times(1)).processFiles(files);
    verify(mapper, times(1)).writeValueAsString(anyList());
    verify(fileManager, times(1)).createFileWithContent(anyString(), anyString(), anyString());
  }

  @Test
  void try_factory_class() {
    // given

    // when
    assertDoesNotThrow(
        () -> {
          final var normalizeFile = new NormalizeFileFactory().create();

          assertNotNull(normalizeFile);
        });

    // then
  }
}
