package com.example;

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension;
import org.spockframework.runtime.model.FeatureInfo;

/**
 * @author Ivan Khalopik
 */
public class RepeatExtension extends AbstractAnnotationDrivenExtension<Repeat> {

  @Override
  public void visitFeatureAnnotation(Repeat annotation, FeatureInfo feature) {
    feature.addInterceptor(new RepeatInterceptor(annotation.value()));
  }
}
