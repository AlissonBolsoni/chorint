package br.com.alissonbolsoni.chorint.services.impl;

import br.com.alissonbolsoni.chorint.services.ProcessConvertion;
import br.com.alissonbolsoni.chorint.utils.Log;

public class ProcessConvertionFactory {
  public ProcessConvertion create() {
    return new ProcessConvertionImpl(new FileConverterFactory().create(), new Log());
  }
}
