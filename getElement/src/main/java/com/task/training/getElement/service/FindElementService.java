package com.task.training.getElement.service;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface FindElementService {

	Element getModelElementFromHtml(String html);

	Element compareModelElementWithHtml(Element modelElement, Elements elements);

	Elements getAllElementsLikeModelElement(Element modelElement, String html);

	void saveResultPathToFile(Element element);

}
