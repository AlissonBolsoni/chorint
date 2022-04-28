package br.com.alissonbolsoni.chorint.usecases.impl;

import br.com.alissonbolsoni.chorint.services.impl.FileManagerFactory;
import br.com.alissonbolsoni.chorint.services.impl.ProcessConvertionFactory;
import br.com.alissonbolsoni.chorint.usecases.NormalizeFile;
import br.com.alissonbolsoni.chorint.utils.Log;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NormalizeFileFactory {
  public NormalizeFile create() {
    return new NormalizeFileImpl(
        new ProcessConvertionFactory().create(),
        new ObjectMapper(),
        new FileManagerFactory().create(),
        new Log());
  }
}
