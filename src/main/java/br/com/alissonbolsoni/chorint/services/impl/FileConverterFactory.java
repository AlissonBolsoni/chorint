package br.com.alissonbolsoni.chorint.services.impl;

import br.com.alissonbolsoni.chorint.services.FileConverter;
import br.com.alissonbolsoni.chorint.utils.Log;

public class FileConverterFactory {
  public FileConverter create() {
    return new FileConverterImpl(new Log());
  }
}
