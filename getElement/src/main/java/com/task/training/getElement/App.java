package com.task.training.getElement;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.task.training.getElement.service.FindElementService;
import com.task.training.getElement.service.FindElementServiceImpl;

public class App {
	private static FindElementService findElementService = new FindElementServiceImpl();
	public final static String MODEL_ID = "make-everything-ok-button";

	public static void main(String[] args) {

		String sampleHtmlPath = args[0];
		String diffHtmlPath = args[1];

		Element modelElement = findElementService.getModelElementFromHtml(sampleHtmlPath);
		Elements allElements = findElementService.getAllElementsLikeModelElement(modelElement, diffHtmlPath);
		Element resultElement = findElementService.compareModelElementWithHtml(modelElement, allElements);
		findElementService.saveResultPathToFile(resultElement);
	}
}
