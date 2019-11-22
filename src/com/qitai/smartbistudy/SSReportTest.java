package com.qitai.smartbistudy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import smartbi.sdk.ClientConnector;
import smartbi.sdk.service.simplereport.Parameter;
import smartbi.sdk.service.spreadsheetreport.SSReport;

public class SSReportTest {

	public static void main(String[] args) {
		doExportPDF();
	}

	public static void doExportPDF(){
		ClientConnector connector = new ClientConnector("http://192.168.10.95:8089/SouthGISBI");
		boolean open = connector.open("admin", "12345");
///SouthGISBI/vision/openresource.jsp?resid='+distinguished+'&paramsInfo='+params
		String resid ="I2c905ac7016b40ce40ce4180016b4c13df0e2f48";
		File file = new File("D:\\1.PDF");
		String fileName = "1.PDF";
		if (open) {
			SSReport report = new SSReport(connector);
			//OutputParameter.I4028e4820164ff76ff76220601652e27bdd7141e.jid
			report.open(resid);
			List<Parameter> list = report.getParamList();

			report.setParamValue(list.get(0).getId(),"2019110400006","2019110400006");


			FileOutputStream fileOutputStream = null;

			try {
				fileOutputStream = new FileOutputStream(file);

				report.doExport("PDF", fileOutputStream);	//大写格式
				fileOutputStream.flush();
				fileOutputStream.close();
				connector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("登录失败");
		}

		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
		}
	}

	/**
	 * 导出电子表格
	 */
	public static void doExport() {
		ClientConnector connector = new ClientConnector("http://localhost:6688/SouthGISBI");
		boolean open = connector.open("admin", "12345");
		if (open) {
			SSReport report = new SSReport(connector);
			report.open("I40280981015b8a518a51aaee015b8a814ac405da");
			// 有参数时设置参数，参数id是数据集的输出参数id
			report.setParamValue("OutputParameter.I4028818a015a59335933fbe2015a5aad320e1cb6.chanpinleibie", "饮料", "饮料");

			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(new File("D:\\1111.xlsx"));
				report.doExport("EXCEL2007", fileOutputStream);	//大写格式
				fileOutputStream.flush();
				fileOutputStream.close();
				connector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("登录失败");
		}
	}
	
	/**
	 * 导出电子表格
	 */
	public static void doExport2() {
		ClientConnector connector = new ClientConnector("http://192.168.1.10:16201/smartbi");
		boolean open = connector.open("admin", "admin");
		if (open) {
			SSReport report = new SSReport(connector);
			report.open("I4028818a01600f990f99496e016029afb1fa6aab");
			// 多数据集共用参数，通过接口获取参数id再设置参数
			List<Parameter> paramList = report.getParamList();
            for(int i =0;i<paramList.size();i++){
                String name = paramList.get(i).getName();    //参数名称
                String id = paramList.get(i).getId();    //参数id
                if("产品类别".equals(name)){
                    report.setParamValue(id, "调味品","调味品");
                }
            }
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(new File("D:\\1111.xlsx"));
				report.doExport("EXCEL2007", fileOutputStream);	//大写格式
				fileOutputStream.flush();
				fileOutputStream.close();
				connector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("登录失败");
		}
	}
}
