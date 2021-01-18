package com.cosium.html;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

public class HtmlToXhtmlConversion {

  private final String encoding;
  private final String tagElementToWrapDocumentIn;

  public HtmlToXhtmlConversion(String encoding, String tagElementToWrapDocumentIn) {
    this.encoding = requireNonNull(encoding);
    this.tagElementToWrapDocumentIn = tagElementToWrapDocumentIn;
  }

  public void execute(String baseUri, InputStream html, OutputStream xhtml) throws IOException {
    Document document = Jsoup.parse(html, encoding, baseUri, Parser.xmlParser());

    if (StringUtils.isNotBlank(tagElementToWrapDocumentIn)) {
      document =
          Jsoup.parse(
              "<"
                  + tagElementToWrapDocumentIn
                  + ">"
                  + document.html()
                  + "</"
                  + tagElementToWrapDocumentIn
                  + ">",
              baseUri,
              Parser.xmlParser());
    }

    document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
    IOUtils.write(document.html().getBytes(encoding), xhtml);
  }
}
