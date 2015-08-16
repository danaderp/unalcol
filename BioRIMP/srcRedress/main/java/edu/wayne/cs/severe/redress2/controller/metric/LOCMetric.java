package edu.wayne.cs.severe.redress2.controller.metric;

import java.io.File;
import java.util.List;

import org.apache.xml.dtm.ref.DTMNodeList;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.exception.MetricException;
import edu.wayne.cs.severe.redress2.utils.ExceptionUtils;
import edu.wayne.cs.severe.redress2.utils.XpathSrcMLUtils;

/**
 * Lines Of Code (LOC) metric calculator. Comments and blank lines are not
 * considered.
 * 
 * @author ojcchar
 * @version 1.0
 * @created 23-Mar-2014 12:28:47
 */
public class LOCMetric extends CodeMetric {

	private static final String ACRONYM = "LOC";

	public LOCMetric(List<TypeDeclaration> sysTypeDcls, HierarchyBuilder builder) {
		super(sysTypeDcls, builder);
	}

	public LOCMetric() {
	}

	@Override
	public double computeMetric(TypeDeclaration typeDcl) throws MetricException {
		try {
			File compUnitFile = typeDcl.getCompUnit().getSrcFile();
			InputSource inputSource = XpathSrcMLUtils
					.getInputSource(compUnitFile);

			// xpath expression to get the nodes from the file
			String xpExpr = "//a:class/a:name[text()=\"" + typeDcl.getName()
					+ "\"]/..";
			if (typeDcl.isHasParams()) {
				xpExpr = "//a:class/a:name/a:name[text()=\""
						+ typeDcl.getName() + "\"]/../..";
			}

			// get the code
			NodeList nodeList = XpathSrcMLUtils.getResultXpath(xpExpr,
					inputSource);
			String code = ((DTMNodeList) nodeList).getDTMIterator().toString();

			return MetricUtils.countLOC(code);

		} catch (Exception e) {
			MetricException ex = new MetricException(e.getMessage());
			ExceptionUtils.addStackTrace(e, ex);
			throw ex;
		}
	}

	@Override
	public String getMetricAcronym() {
		return ACRONYM;
	}

}// end LOCMetric