package com.cosium.html;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HtmlToXhtmlConversionTest {

  @Test
  @DisplayName("Convert")
  void test1() throws IOException {
    ByteArrayOutputStream output = new ByteArrayOutputStream();

    new HtmlToXhtmlConversion("UTF-8", null)
        .execute(
            "",
            new ByteArrayInputStream("<form novalidate></form>".getBytes(StandardCharsets.UTF_8)),
            output);

    assertThat(output.toString("UTF-8")).isXmlEqualTo("<form novalidate=\"\"></form>");
  }

  @Test
  @DisplayName("Convert with enclosing element")
  void test2() throws IOException {
    ByteArrayOutputStream output = new ByteArrayOutputStream();

    new HtmlToXhtmlConversion("UTF-8", "div")
        .execute(
            "",
            new ByteArrayInputStream("<div></div><span></span>".getBytes(StandardCharsets.UTF_8)),
            output);

    assertThat(output.toString("UTF-8")).isXmlEqualTo("<div><div></div><span></span></div>");
  }
}
