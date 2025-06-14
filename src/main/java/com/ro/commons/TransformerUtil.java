package com.ro.commons;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TransformerUtil<T> {
	
	public T transform(String resourcePath, Map<String, Object> properties) throws IOException {
		ClassPathResource resource = new ClassPathResource(resourcePath, TransformerUtil.class);
		File file = resource.getFile();
		Binding binding = initBinding(properties);
		Script script = new GroovyShell(binding).parse(file);
		T obj = (T) script.evaluate(file);
		return obj;
	}

	private Binding initBinding(Map<String, Object> properties) {
		Binding binding = new Binding();
		properties.entrySet().stream().forEach(e -> binding.setProperty(e.getKey(), e.getValue()));
		return binding;
	}
}
