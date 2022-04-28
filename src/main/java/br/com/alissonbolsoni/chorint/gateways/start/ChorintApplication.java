package br.com.alissonbolsoni.chorint.gateways.start;

import br.com.alissonbolsoni.chorint.usecases.impl.NormalizeFileFactory;

public class ChorintApplication {

  public static void main(String[] args) {

    final var normalizeFile = new NormalizeFileFactory().create();

    normalizeFile.execute(args);
  }
}
