package com.ibase.marshall.view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;

import com.marshall.gen.GenerateFactory;
import com.marshall.gen.bean.Resource;
import com.marshall.gen.helper.DataBaseHelper;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

/**
 * 
 * @author yunlong
 *
 */
public class Main {
	// 数据库用户名
	public static final String DATABASE_USERNAME = "root";
	// 数据库密码
	public static final String DATABASE_PASSWORD = "root3306";
	// 数据库连接地址
	public static final String DATABASE_URL = "jdbc:mysql://118.24.70.239:3306/ssm";//jdbc:oracle:thin:@//192.168.10.32:1521/orcl";
	// 模块路径 例如 com.baidu
	public static final String PACKAGE_PATH = "com.youmeek.ssm";
	// 模块名称，例如 mainWebService
	public static final String MODULE_NAME = "manage";
	// 源代码根路径文件夹名称，默认为src
	public static final String RESOURCE_TARGET = "src";
	// web服务的文件夹 默认为webapp
	public static final String WEBAPP_PATH = "webapp";
	// jdbc 数据库驱动
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";//"oracle.jdbc.driver.OracleDriver";

	protected Shell shell;
	private Text txtUrl;
	private Text txtUserName;
	private Text txtPWD;
	private Text txtDataBaseName;
	private Text txtBussiPackage;
	private Text txtEntityPackage;
	private Text txtSrc;
	private Text txtWebroot;
	private Text txtDriver;
	private Text txtFilePath;
	/**
	 * 注释
	 */
	private Text textDescription;
	/**
	 * 注释标题
	 */
	private Text textDescTitle;
	private Text txtGenPath;
	private Text txtSelect;
	private java.util.List<String> tempTableNames = null;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Main window = new Main();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(637, 485);
		shell.setText("平台架构代码生成器_V1.0");

		Group groupGen = new Group(shell, SWT.NONE);
		groupGen.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.BOLD));
		groupGen.setText("final:生成CODE");
		groupGen.setBounds(10, 335, 601, 102);

		Label label_10 = new Label(groupGen, SWT.NONE);
		label_10.setText("生成路径");
		label_10.setBounds(10, 27, 54, 17);

		txtGenPath = new Text(groupGen, SWT.BORDER);
		txtGenPath.setText("D:\\out\\platform-output");
		txtGenPath.setBounds(70, 24, 157, 23);

		Button btnSelectGenPath = new Button(groupGen, SWT.NONE);
		btnSelectGenPath.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(shell, SWT.SELECTED);
				String selecteddir = dialog.open();
				if (selecteddir != null) {
					txtGenPath.setText(selecteddir);
				} else {
					MessageBox messageBox = new MessageBox(shell);
					messageBox.setMessage("请选择路径!");
					messageBox.open();
				}
			}
		});
		btnSelectGenPath.setText("选择路径");
		btnSelectGenPath.setBounds(233, 22, 71, 27);

		final Button btnEntity = new Button(groupGen, SWT.CHECK);
		btnEntity.setSelection(true);
		btnEntity.setBounds(321, 27, 54, 17);
		btnEntity.setText("pojo");

		final Button btnMapper = new Button(groupGen, SWT.CHECK);
		btnMapper.setSelection(true);
		btnMapper.setText("Mapper");
		btnMapper.setBounds(381, 27, 65, 17);

		final Button btnService = new Button(groupGen, SWT.CHECK);
		btnService.setSelection(true);
		btnService.setText("Service");
		btnService.setBounds(459, 27, 62, 17);

		final Button btnController = new Button(groupGen, SWT.CHECK);
		btnController.setSelection(true);
		btnController.setText("controller");
		btnController.setBounds(527, 27, 74, 17);

		Group groupConfig = new Group(shell, SWT.NONE);
		groupConfig.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.BOLD));
		groupConfig.setText("第一步:配置信息");
		groupConfig.setBounds(10, 10, 603, 168);

		Label label = new Label(groupConfig, SWT.NONE);
		label.setBounds(27, 28, 41, 17);
		label.setText("库类型");

		final Combo combo = new Combo(groupConfig, SWT.NONE);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (combo.getText() != null && combo.getText().toLowerCase().equals("oracle")) {
					txtDriver.setText("oracle.jdbc.driver.OracleDriver");
				} else if (combo.getText() != null && combo.getText().toLowerCase().equals("mysql")) {
					txtDriver.setText("com.mysql.jdbc.Driver");
				} else if (combo.getText() != null && combo.getText().toLowerCase().equals("sqlserver")) {
					txtDriver.setText("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				}
			}
		});
		combo.setItems(new String[] { "Oracle", "MySql", "SQLServer" });
		combo.setBounds(74, 25, 88, 17);
		// 默认选中第0个
		combo.select(1);

		Label label_1 = new Label(groupConfig, SWT.NONE);
		label_1.setText("数据库地址");
		label_1.setBounds(172, 28, 61, 17);

		txtUrl = new Text(groupConfig, SWT.BORDER);
		txtUrl.setText(DATABASE_URL);
		txtUrl.setBounds(239, 25, 353, 23);

		Label label_2 = new Label(groupConfig, SWT.NONE);
		label_2.setText("用户名");
		label_2.setBounds(27, 67, 41, 17);

		txtUserName = new Text(groupConfig, SWT.BORDER);
		txtUserName.setText(DATABASE_USERNAME);
		txtUserName.setBounds(74, 64, 88, 23);

		Label label_3 = new Label(groupConfig, SWT.NONE);
		label_3.setText("密码");
		label_3.setBounds(172, 67, 24, 17);

//		txtPWD = new Text(groupConfig, SWT.BORDER | SWT.PASSWORD);
		txtPWD = new Text(groupConfig, SWT.BORDER);
		txtPWD.setText(DATABASE_PASSWORD);
		txtPWD.setBounds(204, 64, 91, 23);

		Label label_4 = new Label(groupConfig, SWT.NONE);
		label_4.setText("数据库名");
		label_4.setBounds(301, 67, 48, 17);

		txtDataBaseName = new Text(groupConfig, SWT.BORDER);
		txtDataBaseName.setText("ssm");
		txtDataBaseName.setBounds(355, 64, 88, 23);

		Button button = new Button(groupConfig, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// to test database connection
				Map<String, Object> param = new HashMap<String, Object>();
				Boolean isOK = true;
				MessageBox messageBox = new MessageBox(shell);
				if (txtUrl.getText() == null || txtUrl.getText().equals("")) {
					messageBox.setMessage("请填写数据库地址!");
					if (messageBox.open() == SWT.YES)
						isOK = false;
				} else if (txtUserName.getText() == null || txtUserName.getText().equals("")) {
					messageBox.setMessage("请填写数据库用户名!");
					if (messageBox.open() == SWT.YES)
						isOK = false;
				} else if (txtPWD.getText() == null || txtPWD.getText().equals("")) {
					messageBox.setMessage("请填写数据库密码!");
					if (messageBox.open() == SWT.YES)
						isOK = false;
				} else if (txtDriver.getText() == null || txtDriver.getText().equals("")) {
					messageBox.setMessage("请填写数据库驱动!");
					if (messageBox.open() == SWT.YES)
						isOK = false;
				} else if (combo.getText() == null || combo.getText().equals("")) {
					messageBox.setMessage("请选择数据库类型!");
					if (messageBox.open() == SWT.YES)
						isOK = false;
				} else if (txtDataBaseName.getText() == null || txtDataBaseName.getText().equals("")) {
					messageBox.setMessage("请填写数据库名称!");
					if (messageBox.open() == SWT.YES)
						isOK = false;
				} else {
					param.put("url", txtUrl.getText());
					param.put("username", txtUserName.getText());
					param.put("password", txtPWD.getText());
					param.put("drivename", txtDriver.getText());
					param.put("DBtype", combo.getText());
					DataBaseHelper db = new DataBaseHelper(param);
					try {
						Connection con = db.getConnection();
						if (con == null) {
							messageBox.setMessage("数据库连接失败!");
							messageBox.open();
						} else {
							messageBox.setMessage("数据库连接成功!");
							messageBox.open();
						}
					} catch (Exception e2) {
						messageBox.setMessage("数据库连接失败!" + e2);
						messageBox.open();
					}
				}
			}
		});
		button.setBounds(251, 131, 71, 27);
		button.setText("测试连接");

		Label label_5 = new Label(groupConfig, SWT.NONE);
		label_5.setText("包路径");
		label_5.setBounds(27, 103, 48, 17);

		txtBussiPackage = new Text(groupConfig, SWT.BORDER);
		txtBussiPackage.setText(PACKAGE_PATH);
		txtBussiPackage.setBounds(74, 100, 88, 23);

		Label label_6 = new Label(groupConfig, SWT.NONE);
		label_6.setText("模块包名");
		label_6.setBounds(172, 103, 48, 17);

		txtEntityPackage = new Text(groupConfig, SWT.BORDER);
		txtEntityPackage.setText(MODULE_NAME);
		txtEntityPackage.setBounds(229, 100, 66, 23);

		Label label_7 = new Label(groupConfig, SWT.NONE);
		label_7.setText("源根目录");
		label_7.setBounds(301, 103, 48, 17);

		txtSrc = new Text(groupConfig, SWT.BORDER);
		txtSrc.setText(RESOURCE_TARGET);
		txtSrc.setBounds(355, 100, 88, 23);

		Label lblWeb = new Label(groupConfig, SWT.NONE);
		lblWeb.setText("web目录");
		lblWeb.setBounds(449, 103, 48, 17);

		txtWebroot = new Text(groupConfig, SWT.BORDER);
		txtWebroot.setText(WEBAPP_PATH);
		txtWebroot.setBounds(504, 100, 88, 23);

		/*
		 * Button button_2 = new Button(groupConfig, SWT.NONE);
		 * button_2.addSelectionListener(new SelectionAdapter() {
		 * 
		 * @Override public void widgetSelected(SelectionEvent e) { // to save
		 * properties MessageBox messageBox = new MessageBox(shell); if
		 * (txtUrl.getText() == null || txtUrl.getText().equals("")) {
		 * messageBox.setMessage("请填写数据库地址!"); messageBox.open(); } else if
		 * (txtUserName.getText() == null || txtUserName.getText().equals("")) {
		 * messageBox.setMessage("请填写数据库用户名!"); messageBox.open(); } else if
		 * (txtPWD.getText() == null || txtPWD.getText().equals("")) {
		 * messageBox.setMessage("请填写数据库密码!"); messageBox.open(); } else if
		 * (txtDriver.getText() == null || txtDriver.getText().equals("")) {
		 * messageBox.setMessage("请填写数据库驱动!"); messageBox.open(); } else if
		 * (combo.getText() == null || combo.getText().equals("")) {
		 * messageBox.setMessage("请选择数据库类型!"); messageBox.open(); } else if
		 * (txtBussiPackage.getText() == null ||
		 * txtBussiPackage.getText().equals("")) {
		 * messageBox.setMessage("请填写包路径!"); messageBox.open(); } else if
		 * (txtEntityPackage.getText() == null ||
		 * txtEntityPackage.getText().equals("")) {
		 * messageBox.setMessage("请填写模块包名!"); messageBox.open(); } else if
		 * (txtSrc.getText() == null || txtSrc.getText().equals("")) {
		 * messageBox.setMessage("请填写源根目录!"); messageBox.open(); } else if
		 * (txtWebroot.getText() == null || txtWebroot.getText().equals("")) {
		 * messageBox.setMessage("请填写web目录!"); messageBox.open(); } else if
		 * (txtDataBaseName.getText() == null ||
		 * txtDataBaseName.getText().equals("")) {
		 * messageBox.setMessage("请填写数据库名称!"); messageBox.open(); } else {
		 * Resource res = new Resource();
		 * res.setBussipackage(txtBussiPackage.getText());
		 * res.setDatabase_name(txtDataBaseName.getText());
		 * res.setDatabase_type(combo.getText());
		 * res.setDiver_name(txtDriver.getText());
		 * res.setEntity_package(txtEntityPackage.getText());
		 * res.setPassword(txtPWD.getText()); res.setUrl(txtUrl.getText());
		 * res.setUsername(txtUserName.getText());
		 * res.setWeb_root(txtWebroot.getText());
		 * res.setSource_root(txtSrc.getText()); try {
		 * ResourceHelper.writeProperties(res); messageBox.setMessage("保存成功!");
		 * messageBox.open(); } catch (Exception ex) { ex.printStackTrace();
		 * messageBox.setMessage("保存失败!" + ex); messageBox.open(); } } } });
		 */
		/*
		 * button_2.setText("保存配置"); button_2.setBounds(306, 131, 71, 27);
		 */

		Label label_8 = new Label(groupConfig, SWT.NONE);
		label_8.setText("驱动");
		label_8.setBounds(449, 67, 32, 17);

		txtDriver = new Text(groupConfig, SWT.BORDER);
		txtDriver.setText(JDBC_DRIVER);
		txtDriver.setBounds(484, 64, 108, 23);

		Group groupTable = new Group(shell, SWT.NONE);
		groupTable.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.BOLD));
		groupTable.setText("第二步:选择数据表信息");
		groupTable.setBounds(10, 199, 280, 125);

		final Label lbcount = new Label(groupTable, SWT.NONE);
		lbcount.setBounds(24, 75, 71, 17);

		final List listTables = new List(groupTable, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		listTables.setBounds(122, 48, 148, 67);
		listTables.setItems(new String[] {});

		Button btnGetTable = new Button(groupTable, SWT.NONE);
		btnGetTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Resource res = ResourceHelper.init();
				Resource res = new Resource();
				// 读取数据库信息
				res.setDatabase_name(txtDataBaseName.getText());
				res.setDatabase_type(combo.getText());
				res.setDiver_name(txtDriver.getText());
				res.setPassword(txtPWD.getText());
				res.setUrl(txtUrl.getText());
				res.setUsername(txtUserName.getText());

				// 读取工程信息
				res.setBussipackage(txtBussiPackage.getText());
				res.setEntity_package(txtEntityPackage.getText());
				res.setWeb_root(txtWebroot.getText());
				res.setSource_root(txtSrc.getText());
				res.setCodepath(res.getSource_root() + "/" + res.getBussipackage() + "/");
				res.setBussiPackageUrl(res.getBussipackage().replace(".", File.separator));

				DataBaseHelper db = new DataBaseHelper(res);
				int count = 0;
				try {
					listTables.removeAll();
					java.util.List<String> lTable = db.getTables();
					tempTableNames = lTable;
					for (String table : lTable) {
						listTables.add(table);
						count++;
					}
					lbcount.setText("表数:" + count);
				} catch (Exception es) {
					es.printStackTrace();
					MessageBox messageBox = new MessageBox(shell);
					messageBox.setMessage("获取表失败!" + es);
					messageBox.open();
				}
			}
		});
		btnGetTable.setBounds(24, 48, 71, 27);
		btnGetTable.setText("获取表信息");

		txtSelect = new Text(groupTable, SWT.BORDER);
		txtSelect.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String word = txtSelect.getText().toLowerCase();
				ArrayList<String> temp = new ArrayList<String>();
				if (tempTableNames != null && !word.equals("")) {
					for (String table : tempTableNames) {
						if (table.toLowerCase().indexOf(word) >= 0) {
							temp.add(table);
						}
					}
					if (temp != null && temp.size() > 0) {
						listTables.removeAll();
						int count = 0;
						for (String table : temp) {
							listTables.add(table);
							count++;
						}
						lbcount.setText("表数:" + count);
					} else {
						listTables.removeAll();
						listTables.add("无");
						lbcount.setText("表数:0");
					}
				} else if (tempTableNames != null && word.equals("")) {
					listTables.removeAll();
					int count = 0;
					for (String table : tempTableNames) {
						listTables.add(table);
						count++;
					}
					lbcount.setText("表数:" + count);
				}
				// listTables.setSelection(key);
			}
		});
		txtSelect.setBounds(122, 19, 148, 23);

		Group groupTemp = new Group(shell, SWT.NONE);
		groupTemp.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.BOLD));
		groupTemp.setText("第三步:配置模板");
		groupTemp.setBounds(296, 199, 315, 125);

		Label label_9 = new Label(groupTemp, SWT.NONE);
		label_9.setBounds(10, 26, 54, 17);
		label_9.setText("模板地址");

		txtFilePath = new Text(groupTemp, SWT.BORDER);
		txtFilePath.setBounds(70, 23, 157, 23);

		/****/
		Label label_11 = new Label(groupTemp, SWT.NONE);
		label_11.setBounds(15, 65, 54, 17);
		label_11.setText("注释标题");
		textDescTitle = new Text(groupTemp, SWT.BORDER);
		textDescTitle.setBounds(70, 60, 157, 23);

		Label label_12 = new Label(groupTemp, SWT.NONE);
		label_12.setBounds(15, 85, 54, 17);
		label_12.setText("注释描述");
		textDescription = new Text(groupTemp, SWT.BORDER);
		textDescription.setBounds(70, 80, 157, 60);

		Button btnSelectFile = new Button(groupTemp, SWT.NONE);
		btnSelectFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(shell, SWT.SELECTED);
				// dialog.setFilterNames (new String [] {"Batch Files",
				// "All Files (*.*)"});
				// dialog.setFilterExtensions (new String [] {"*.bat", "*.*"});
				// //Windows wild cards
				// dialog.setFilterPath ("c:\\"); //Windows path
				// dialog.setFileName ("fred.bat");
				String selecteddir = dialog.open();
				if (selecteddir != null) {
					txtFilePath.setText(selecteddir);
				} else {
					MessageBox messageBox = new MessageBox(shell);
					messageBox.setMessage("请选择文件夹!");
					messageBox.open();
				}
			}
		});
		btnSelectFile.setText("选择文件夹");
		btnSelectFile.setBounds(233, 21, 71, 27);

		Button btnNewButton = new Button(groupGen, SWT.NONE);
		btnNewButton.setBounds(224, 55, 99, 37);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				String[] tableNames = listTables.getSelection();
				MessageBox messageBox = new MessageBox(shell);
				if (textDescTitle.getText() == null || textDescTitle.getText().equals("")) {
					messageBox.setMessage("请填写注释!");
					messageBox.open();
					return;
				}

				if (tableNames.length > 0) {
					GenerateFactory g = new GenerateFactory();
					if (txtGenPath.getText() != null && !txtGenPath.getText().equals(""))
						g.setProjectPath(txtGenPath.getText());
					g.setGenController(btnController.getSelection());
					g.setGenMapper(btnMapper.getSelection());
					g.setGenEntity(btnEntity.getSelection());
					g.setGenService(btnService.getSelection());

					Resource res = new Resource();
					// 读取数据库信息
					res.setDatabase_name(txtDataBaseName.getText());
					res.setDatabase_type(combo.getText());
					res.setDiver_name(txtDriver.getText());
					res.setPassword(txtPWD.getText());
					res.setUrl(txtUrl.getText());
					res.setUsername(txtUserName.getText());
					res.setDescription(textDescription.getText());
					res.setTextDescTitle(textDescTitle.getText());

					// 读取工程信息
					res.setBussipackage(txtBussiPackage.getText());
					res.setEntity_package(txtEntityPackage.getText());
					res.setWeb_root(txtWebroot.getText());
					res.setSource_root(txtSrc.getText());
					res.setCodepath(res.getSource_root() + "/" + res.getBussipackage() + "/");
					res.setBussiPackageUrl((res.getBussipackage()+"."+txtEntityPackage.getText()).replace(".", File.separator));

					try {
						g.multiGenerate(tableNames, tableNames, txtFilePath.getText(), res);
						messageBox.setMessage("生成成功!");
						messageBox.open();
					} catch (Exception eq) {
						eq.printStackTrace();
						messageBox.setMessage("生成失败!" + eq);
						messageBox.open();
					}
				} else {
					messageBox.setMessage("请选择数据库表!");
					messageBox.open();
				}
			}
		});
		btnNewButton.setText("一键生成");

		/*
		 * Resource res = ResourceHelper.init(); txtUrl.setText(res.getUrl());
		 * txtDriver.setText(res.getDiver_name());
		 * if(res.getDatabase_type().toLowerCase().equals("oracle"))
		 * combo.select(0); else
		 * if(res.getDatabase_type().toLowerCase().equals("mysql"))
		 * combo.select(1); txtUserName.setText(res.getUsername());
		 * txtPWD.setText(res.getPassword());
		 * txtDataBaseName.setText(res.getDatabase_name());
		 * txtBussiPackage.setText(res.getBussipackage());
		 * txtEntityPackage.setText(res.getEntity_package());
		 * txtSrc.setText(res.getSource_root());
		 * txtWebroot.setText(res.getWeb_root());
		 */
	}
}
