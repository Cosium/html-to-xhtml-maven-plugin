package com.cosium.html;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.Test;

class HtmlToXhtmlConversionTest {

  @Test
  void test() throws URISyntaxException {
    File output = Files.newTemporaryFile();

    new HtmlToXhtmlConversion(
            "UTF-8",
            Paths.get(getClass().getResource("/sample.html").toURI()).toString(),
            output.getAbsolutePath())
        .execute();

    assertThat(Paths.get(getClass().getResource("/sample.xhtml").toURI()))
        .hasSameContentAs(output.toPath());
  }
}
