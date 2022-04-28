package br.com.alissonbolsoni.chorint.services.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class FileManagerImplTest {

  @Test
  void try_factory_class() {
    // given

    // when
    assertDoesNotThrow(
        () -> {
          final var fileManager = new FileManagerFactory().create();

          assertNotNull(fileManager);
        });

    // then
  }
}
