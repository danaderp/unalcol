package edu.wayne.cs.severe.redress2.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * @author ojcchar
 * @version 1.0
 * @created 23-Mar-2014 12:28:48
 */
public class XpathSrcMLUtils {

	private static XPathFactory factory = null;
	private static XPath xPath;

	static {
		factory = XPathFactory.newInstance();
		xPath = factory.newXPath();

		HashMap<String, String> prefMap = new HashMap<String, String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("a", "http://www.sdml.info/srcML/src");
				put("cpp", "http://www.sdml.info/srcML/cpp");
			}
		};
		SimpleNamespaceContext namespaces = new SimpleNamespaceContext(prefMap);
		xPath.setNamespaceContext(namespaces);
	}

	public static InputSource getInputSource(File file)
			throws FileNotFoundException {
		return new InputSource(new FileInputStream(file));
	}

	public static NodeList getResultXpath(String expr, InputSource inputSource)
			throws XPathExpressionException {
		XPathExpression xExpr = xPath.compile(expr);
		NodeList evaluate = (NodeList) xExpr.evaluate(inputSource,
				XPathConstants.NODESET);
		return evaluate;
	}

	public static String getResultXpathstring(String expr,
			InputSource inputSource) throws XPathExpressionException {
		XPathExpression xExpr = xPath.compile(expr);
		return xExpr.evaluate(inputSource);
	}

}// end XpathUtils