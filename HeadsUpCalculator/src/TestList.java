
//package creinsquote.tests.general;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.TreeMap;

/**
 * @author i850487
 *
 */
public class TestList {

	public static void main(String[] args) throws IOException, ParseException {

		TestList reporter = new TestList();

		Map<String, List<String>> jobsMap = new HashMap<String, List<String>>();
		List<String> tcmmList = new ArrayList<String>();
		List<String> tcmmList1 = new ArrayList<String>();
		List<String> tcmmList2 = new ArrayList<String>();
		List<String> tcmmList3 = new ArrayList<String>();

		tcmmList.add("TCMM12345");
		tcmmList.add("TCMM45215");
		jobsMap.put("CreateInsuranceQuote:ABV", tcmmList);

		tcmmList1.add("TCMM3333");
		tcmmList1.add("TCMM4444");
		tcmmList1.add("TCMM5555");
		jobsMap.put("CreateInsuranceQuote:Story", tcmmList1);

		tcmmList2.add("TCMM7777");
		tcmmList2.add("TCMM8888");
		tcmmList2.add("TCMM9999");
		jobsMap.put("MyGroupInsuranceQuote:ALL", tcmmList2);

		tcmmList3.add("TCMM121212");
		tcmmList3.add("TCMM212121");
		tcmmList3.add("TCMM767676");
		jobsMap.put("CreateInsuranceQuote:AZZ", tcmmList3);

		Map<String, List<String>> map = new TreeMap<String, List<String>>(jobsMap);
//		reporter.tableRunnerNew(jobsMap);
		reporter.tableRunnerNew(map);
	}

	private void tableRunnerNew(Map<String, List<String>> jobsMap) {

		String tableWidth = "width:100%";
		StringBuilder htmlTable = new StringBuilder("<html><table style=" + tableWidth + ">\n");
		htmlTable.append("<th>Job Title</th><th>Suite</th><th>TCMM Number</th>\n");
		for (String jobTitleToSuiteString : jobsMap.keySet()) {
			String[] tokens = jobTitleToSuiteString.split(":");
			String jobTitle = tokens[0];
			String suite = tokens[1];
			htmlTable.append("<tr>");

			htmlTable.append("<td>");
			htmlTable.append(jobTitle);
			htmlTable.append("</td>");
			htmlTable.append("<td>");
			htmlTable.append(suite);
			htmlTable.append("</td>");
			htmlTable.append("<td>");
			List<String> tcmmList = jobsMap.get(jobTitleToSuiteString);
			for (String tcmm : tcmmList) {
				htmlTable.append(tcmm);
				htmlTable.append("<br>");
			}
			htmlTable.append("</td>");
			htmlTable.append("</tr>\n");
		}
		htmlTable.append("</table></html>");
		System.out.println(htmlTable);

	}

	public void tableBuilder(String version) {
		String tableWidth = "width:100%";
		StringBuilder htmlTable = new StringBuilder();
		htmlTable.append(
				"<html><head><style>table {    font-family: arial, sans-serif;    border-collapse: collapse;    width: 100%;}\n");
		htmlTable.append("td, th {    border: 1px solid #dddddd;    text-align: left;    padding: 8px;}\n");
		htmlTable.append("tr:nth-child(even) {    background-color: #dddddd;}\n");
		htmlTable.append("</style></head><body>\n");
		htmlTable.append("<table>  <tr>    <th>Jenkins Job Name</th>    <th>Suite</th>    <th>TCMM Number</th>  </tr>");
		htmlTable.append("</table></body></html>");
		System.out.println(htmlTable.toString());
	}

	public void tableRunner(ArrayList<ArrayList<String>> tableData) {
		Map<String, List<String>> jobsMap = new HashMap<String, List<String>>();
		Map<String, String> jobTitleToSuiteMap = new HashMap<String, String>();

		for (List<String> innerList : tableData) {
			String jenkinsJobName = innerList.get(0);
			String suite = innerList.get(1);
			jobTitleToSuiteMap.put(jenkinsJobName, suite);
			List<String> newTcmmList = new ArrayList<String>();
			newTcmmList = innerList.subList(2, innerList.size());
			jobsMap.put(jenkinsJobName, newTcmmList);
		}

		String tableWidth = "width:100%";
		StringBuilder htmlTable = new StringBuilder("<html><table style=" + tableWidth + ">\n");
		htmlTable.append("<th>Job Title</th><th>Suite</th><th>TCMM Number</th>\n");
		for (String jobTitle : jobTitleToSuiteMap.keySet()) {
			htmlTable.append("<tr>");

			htmlTable.append("<td>");
			htmlTable.append(jobTitle);
			htmlTable.append("</td>");
			htmlTable.append("<td>");
			htmlTable.append(jobTitleToSuiteMap.get(jobTitle));
			htmlTable.append("</td>");
			htmlTable.append("<td>");
			List<String> tcmmL = jobsMap.get(jobTitle);

			for (String tcmm : tcmmL) {
				htmlTable.append(tcmm);
				htmlTable.append("<br>");
			}
			htmlTable.append("</td>");
			htmlTable.append("</tr>\n");
		}
		htmlTable.append("</table></html>");
		System.out.println(htmlTable);
	}
}