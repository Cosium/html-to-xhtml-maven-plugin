package com.cosium.html;

import static java.util.Optional.ofNullable;

import java.util.Collections;
import java.util.List;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "html-to-xhtml", defaultPhase = LifecyclePhase.GENERATE_RESOURCES, threadSafe = true)
public class HtmlToXhtmlMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project.build.sourceEncoding}")
  private String encoding;

  @Parameter(property = "htx.conversions")
  private List<HtmlToXhtmlConversion> conversions;

  @Override
  public void execute() {
    ofNullable(conversions)
        .orElseGet(Collections::emptyList)
        .forEach(conversion -> conversion.execute(encoding));
  }
}
