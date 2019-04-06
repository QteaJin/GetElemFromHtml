package com.task.training.getElement.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.task.training.getElement.App;

public class FindElementServiceImpl implements FindElementService {

	public Element getModelElementFromHtml(String htmlPath) {

		Document document = createDocumentFromFile(htmlPath);

		Optional<Element> button = Optional.of(document.getElementById(App.MODEL_ID));

		if (!button.isPresent()) {

			new Exception("Nothing was find");
		}
		return button.get();
	}

	public Element compareModelElementWithHtml(Element modelElement, Elements elements) {

		for (Element element : elements) {
			if ((element.attr("rel").equals("next") || element.attr("rel").equals("done"))) {
				if (element.className().equals("btn btn-warning")) {
					continue;
				}

				return element;
			}
		}

		return null;
	}

	public Elements getAllElementsLikeModelElement(Element modelElement, String html) {
		Document document = createDocumentFromFile(html);
		Elements elements = document.select("a.btn");

		return elements;
	}

	private Document createDocumentFromFile(String htmlPath) {

		Document document = null;

		try {
			document = Jsoup.parse(new File(htmlPath), "utf8");
		} catch (IOException e) {

			e.printStackTrace();
		}

		return document;

	}

	public void saveResultPathToFile(Element element) {

		String resultPath = createResultPath(element);
		System.out.println(resultPath);
		File resultPathFile = new File("resultPath.txt");

		try {
			if (!resultPathFile.exists()) {
				resultPathFile.createNewFile();
				resultPathFile.setWritable(true);
			}

			FileWriter writer = new FileWriter(resultPathFile);
			writer.write(resultPath);
			writer.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private String createResultPath(Element element) {

		StringBuilder stringBuilder = new StringBuilder();
		List<String> pathToElemArray = new ArrayList<>();
		pathToElemArray.add(element.nodeName());

		while (element.hasParent()) {

			element = element.parent();
			stringBuilder.append(element.nodeName());
			if (element.elementSiblingIndex() != 0) {
				stringBuilder.append(" [").append(element.elementSiblingIndex()).append("]");
			}
			stringBuilder.append(" > ");
			pathToElemArray.add(stringBuilder.toString());
			stringBuilder.setLength(0);

		}
		System.out.println();
		Collections.reverse(pathToElemArray);
		pathToElemArray.remove(0);
		String changeLast = pathToElemArray.get(pathToElemArray.size() - 1);
		String changed = changeLast.replaceAll(" > ", "");
		pathToElemArray.set(pathToElemArray.size() - 1, changed);
		stringBuilder.setLength(0);
		for (String string : pathToElemArray) {
			stringBuilder.append(string);
		}

		return stringBuilder.toString();

	}

}
