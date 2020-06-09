package main;

import java.sql.*;                                //导入SQL语言包

class MySQL{
	/** 
	 * @ClassName:MySQL(MySQL操作)
	 * @Description:提供应用JDBC连接MySQL的基本操作
	 * @Module:启动模块;语句执行模块;异常处理模块
	 * @CreateDate:2016.11.10
	 * @Version:2.0
	**/
	//[定义常量]
	boolean debug=true;                           //调试开关(true为打开)
	static final String USER="root";              //MySQL数据库用户名
	static final String PASS="pgybdc";            //MySQL数据库密码
	String JDBC_DRIVER="com.mysql.jdbc.Driver";   //JDBC驱动地址
	String DB_URL="jdbc:mysql://localhost";
	//[定义变量]
	Connection conn=null;                         //连接变量
	Statement stmt=null;                          //操作变量
	//[初始化程序类]
	Base base=new Base();                         //初始化底层类(☆)
	
	//【构造器】
	MySQL(){
		/*
		STPLoadDriver();//【开始】加载MySQL驱动

		STPOpenConnection("STUDENTS");//【开始】建立连接
		CreateDatabase("STUDENTS");//【开始】创建数据库
		DeleteTable("REGISTRATION");//【SQL】删除表格
		
		SetTable("REGISTRATION");
		Item("id","INTEGER",false,true);
		Item("first","VARCHAR(255)",true,false);
		Item("last","VARCHAR(255)",true,false);
		Item("age","INTEGER",true,false);
		CreateTable();                  //【SQL】创建表格            
		
		//InsertOneRecord("Registration","100, 'Zara', 'Ali', 18");//【SQL】插入记录
		
		String[] value=new String[100];
		value[0]="100, 'Zara', 'Ali', 18";
		value[1]="101, 'Mahnaz', 'Fatma', 25";
		value[2]="102, 'Zaid', 'Khan', 30";
		value[3]="103, 'Sumit', 'Mittal', 28";
		InsertManyRecord("Registration",value);	         //【SQL】插入多条记录(输入操作表格名，插入记录内容)	
		
		String[] item=new String[10];
		item[0]="Iid";                //int类型
		item[1]="Sfirst";  //int类型
		item[2]="Slast";// String类型
		item[3]="Iage";//String类型
		ShowByItem("Registration",item);
		
		SearchRecordByInt("Registration",item,"id>=101");
		SearchRecordByInt("Registration",item,"first LIKE '%za%'");
		OrderRecord("Registration",item,"first","DESC");
		ChangeRecord("Registration","age=30","id in (100, 101)");
		
		InsertOneRecord("Registration","104, 'Zar3', 'Ala', 18");
		ShowByItem("Registration",item);
		
		//DeleteRecord("Registration","id=101");//【SQL】删除记录
		//DeleteDatabase("STUDENTS");//【SQL】删除数据库
		STPCloseConnection();//【关闭】关闭连接		
		*/
	}
	
	/** @Module:启动模块	 */	
	//【启动】加载JDBC驱动(加载成功返回true，加载失败返回false)
	public boolean STPLoadDriver(){
		try{
			Class.forName(JDBC_DRIVER);                                         //申请JDBC加载驱动
			Show("MySQL-JDBC驱动加载成功……");
		}
		//“JDBC连接失败”异常处理
		catch(ClassNotFoundException e){
			Show("MySQL-JDBC连接异常",":因未知原因无法加载MySQL驱动，请检查JDBC安装环境");
			return false;
		}
		return true;
	}
	
	//【启动】与数据库建立连接(输入数据库名称，若不存在是否创建；加载成功返回true，加载失败返回false)
	public boolean STPOpenConnection(String name){
		String DB_URL="jdbc:mysql://localhost";
		DB_URL=DB_URL+"/"+name;                                                 //填写完整DB_URL
		try{
			conn=DriverManager.getConnection(DB_URL,USER,PASS);                 //申请连接变量(conn)建立与数据库的连接
			Show("与数据库建立连接成功……");
			stmt=conn.createStatement();                                        //操作变量(stmt)活动声明
		}
		//“建立与数据库连接”异常处理
		catch(SQLException e1){
			try{
				conn=DriverManager.getConnection("jdbc:mysql://localhost",USER,PASS);
				stmt=conn.createStatement();                                    //操作变量(stmt)活动声明
				//CreateDatabase(name);                                           //创建数据库
				try{
					conn=DriverManager.getConnection(DB_URL,USER,PASS); 
					Show("Warning 1002:数据库("+name+")不存在错误，已经重建数据库完成修复");
					stmt=conn.createStatement();                                //操作变量(stmt)活动声明
				}
				catch(SQLException e2){
					Show("Error 1002",":重建数据库修复方式失败；DB_URL内容异常，其内容为："+DB_URL+"；请检查数据库是否存在");
					return false;
				}
			}
			catch(Exception se){
				Show("Error 1002",":MySQL用户名或密码错误，无法构建与数据库的连接");
				return false;
			}
		}
		return true;
	}
	
	//【关闭】关闭连接(成功返回true，失败返回false)
	public boolean STPCloseConnection(){
		try{
			if(stmt!=null){
				stmt.close();
			}
			if(conn!=null){
				conn.close();
			}
		}
		catch(SQLException se){
			Show("Error 1003",":数据库连接关闭失败");
			return false;
		}
		Show("数据库连接关闭成功");
		return true;
	}
	
	/** @Module:SQL语句翻译器 */
	//【SQL】创建数据库(输入数据库名称；成功返回true，失败返回false)
	public boolean CreateDatabase(String name){
		String SQL="CREATE DATABASE "+name;
		if(!SQLDo(SQL)){
			Show("Error 1011",":数据库("+name+")创建失败");
			NameTest(name);
			return false;
		}
		return true;
	}
	
	//【SQL】创建表格
	//[变量]表格创建临时变量
	String name=new String("");                   //[变量]临时存储表格名称
	String tableSQL=new String("");               //[变量]临时存储SQL语句
	String primary=null;                          //[变量]临时存储PRIMARY KEY语句
	boolean primaryKey=false;                     //[变量]临时存储是否发现PRIMARY KEY语句
	int number=1;                                 //[变量]临时存储字段数量
	//[程序]定义表格(输入表格名称；定义成功返回true，定义失败返回false)
	public void SetTable(String _name){
		name=_name;
		tableSQL="CREATE TABLE "+name+" (";                                     //制作SQL语句(创建表格)
	}
	//[程序]定义表格字段(输入字段名，字段类型，是否可为空，是否为主键)
	public void Item(String name,String type,boolean empty,boolean key){
		String SQL=name;
		if(type.equals("int")||type.equals("INTEGER")){                         //识别数字型格式
			SQL=SQL+" INTEGER";
		}
		if(type.indexOf("String")!=-1||type.indexOf("VARCHAR")!=-1){            //识别字符串格式
			SQL=SQL+" VARCHAR"+type.substring(type.indexOf("("));
		}
		if(empty==false){                                                       //识别字段不能为空定义
			SQL=SQL+" not NULL";
		}
		SQL=SQL+", ";
		if(key==true){                                                          //分析主键情况
			if(primary==null){
				primary="PRIMARY KEY("+name+"))";
			}
			else{
				Show("Error 1042",":当前已有多主键，拒绝添加主键，当前主键为:"+primary);
			}
		}
		tableSQL=tableSQL+SQL;
	}
	//[程序]执行创建表格
	public boolean CreateTable(){
		if(!SQLDo(tableSQL+primary)){
			Show("Error 1012",":数据库("+name+")表格创建失败，请检查SQL语句："+(tableSQL+primary));
			return false;
		}
		return true;		
	}
	
	//【SQL】插入单条记录(输入操作表格名，插入记录内容；成功返回true，失败返回false)
	public boolean InsertOneRecord(String name,String value){
		Show("正在向"+name+"表格插入记录("+value+")");
		String SQL="INSERT INTO "+name+" VALUES ("+value+")";                             //制作SQL语句(插入记录)
		if(!SQLDo(SQL)){
			Show("Error 1014",":在表格("+name+")中插入记录失败");
			NameTest(name);
			return false;
		}
		return true;
	}
	
	//【SQL】插入多条记录(输入操作表格名，插入记录内容；成功返回true，失败返回false)
	public boolean InsertManyRecord(String name,String value[]){
		int length=base.SArrayActualL(value);                                             //[Base]字符串数组实际有效长度
		for(int i=0;i<length;i++){
			String SQL="INSERT INTO "+name+" VALUES("+value[i]+")";                       //制作SQL语句(插入记录)
			if(!SQLDo(SQL)){
				Show("Error 1014",":在表格("+name+")中插入多条记录(第"+i+"条)失败");
				NameTest(name);
				return false;
			}
		}
		return true;
	}
	
	//【SQL】按字段(列)选取显示数据库内容(输入表格名称，输入显示字段(包含类型定义)；成功返回true，失败返回false)
	public String ShowTable(String name,String item[]){
		int length=base.SArrayActualL(item);                                    //[Base]字符串数组实际有效长度
		Show(name+"表格有效字段为"+length+"个");
		String SQL="SELECT ";
		for(int i=0;i<length-1;i++){
			SQL=SQL+item[i].substring(1)+", ";
		}
		SQL=SQL+item[length-1].substring(1)+" FROM "+name;
		return SQL;
	}
	
	//【SQL】按字段(列)选取显示数据库内容(输入表格名称，输入显示字段(包含类型定义)，搜索条件；成功返回true，失败返回false)
	public String ShowTable(String name,String item[],String condition){
		int length=base.SArrayActualL(item);                                    //[Base]字符串数组实际有效长度
		String SQL="SELECT ";
		for(int i=0;i<length-1;i++){
			SQL=SQL+item[i].substring(1)+", ";
		}
		SQL=SQL+item[length-1].substring(1)+" FROM "+name;
		SQL=SQL+" WHERE "+condition;
		return SQL;
	}
	
	/*【SQL】记录排序显示(输入表格名称，显示字段(包含类型定义)，排序依据字段，排序标准；成功返回true，失败返回false)
	public String OrderRecord(String name,String item[],String orderitem,String order){
		int length=base.SArrayActualL(item);                                    //[Base]字符串数组实际有效长度
		String SQL="SELECT ";
		for(int i=0;i<length-1;i++){
			SQL=SQL+item[i].substring(1)+", ";
		}
		SQL=SQL+item[length-1].substring(1)+" FROM "+name;
		SQL=SQL+" ORDER BY "+orderitem+" "+order;                               //DESC降序；ASC升序
		return SQL;
	}*/
	
	//【SQL】修改记录(输入表格名称，修改目标，修改条件；成功返回true，失败返回false)
	public boolean ChangeRecord(String name,String aim,String condition){
		String SQL="UPDATE "+name+" SET "+aim+" WHERE "+condition;
		if(!SQLDo(SQL)){
			Show("Error 1015",":在表格("+name+")中修改记录失败");
			NameTest(name);
			return false;
		}
		return true;
	}
	
	//【SQL】删除表格(输入删除表格的名称；成功返回true，失败返回false)
	public boolean DeleteTable(String name){
		String SQL="DROP TABLE "+name;                                                //制作SQL语句(删除数据库)
		if(!SQLDo(SQL)){
			Show("Error 1013",":数据库("+name+")创建失败");
			NameTest(name);
			return false;
		}
		return true;
	}
	
	//【SQL】删除记录(输入表格名称，删除目标；成功返回true，失败返回false)
	public boolean DeleteRecord(String name,String aim){
		String SQL="DELETE FROM "+name+" Where "+aim;
		if(!SQLDo(SQL)){
			Show("Error 1016",":在表格("+name+")中删除记录("+aim+")失败");
			NameTest(name);
			return false;
		}
		return true;
	}
	
	//【SQL】删除数据库(输入表格名称；成功返回true，失败返回false)
	public boolean DeleteDatabase(String name){
		String SQL="DROP DATABASE "+name;
		if(!SQLDo(SQL)){
			Show("Error 1017",":删除数据库("+name+")失败");
			NameTest(name);
			return false;
		}
		return true;
	}
	
	//【SQL】按字段(列)选取显示数据库内容(输入表格名称，输入显示字段(包含类型定义)；成功返回true，失败返回false)
	public boolean ShowByItem(String name,String item[]){
		String SQL=new String("");
		System.out.println("正在数据库表格中显示记录……");
		int length=base.SArrayActualL(item);                                              //[Base]字符串数组实际有效长度
		try{
			//制作SQL语句(读取数据库记录)
			stmt=conn.createStatement();                                                  //操作变量活动声明
			SQL="SELECT ";
			for(int i=0;i<length-1;i++){
				SQL=SQL+item[i].substring(1)+", ";
			}
			SQL=SQL+item[length-1].substring(1)+" FROM "+name;
			//显示SQL语句执行结果
			if(!ShowResult(SQL,length,item)){
				System.out.println("Error 1009:ShowResult返回失败，请检查SQL语句："+SQL);
				return false;
			}
			System.out.println("数据库表格搜索显示成功！");
		}
		catch(Exception e){
			if(DB_URL.substring(13).indexOf("/")==-1){
				System.out.println("Error 1008:读取数据库路径不合法，读取失败，请检查DB_URL的内容("+DB_URL+")");
				return false;
			}
			System.out.println("Error 1008:其他原因造成数据库表格搜索显示失败，请检查SQL语句："+SQL);
			return false;
		}
		return true;
	}
	
	//【SQL】按字段的特征(SQL语句)按记录选取(行)显示数据库内容(输入表格名称，显示字段(包含类型定义)，搜索的特征；成功返回true，失败返回false)
	public boolean SearchRecordByInt(String name,String item[],String feature){
		String SQL=new String("");
		System.out.println("正在数据库表格中选择查询记录……");
		int length=base.SArrayActualL(item);                                              //[Base]字符串数组实际有效长度
		try{
			stmt=conn.createStatement();                                                  //操作变量活动声明
			//制作SQL语句(读取数据库记录)
			SQL="SELECT ";
			for(int i=0;i<length-1;i++){
				SQL=SQL+item[i].substring(1)+", ";
			}
			SQL=SQL+item[length-1].substring(1)+" FROM "+name;
			SQL=SQL+" WHERE "+feature;
			//显示SQL语句执行结果
			if(!ShowResult(SQL,length,item)){
				System.out.println("Error 1009:ShowResult返回失败，请检查SQL语句："+SQL);
				return false;
			}
			System.out.println("数据库表格选择查询记录成功！");
		}
		catch(Exception e){
			if(DB_URL.substring(13).indexOf("/")==-1){
				System.out.println("Error 1008:数据库路径不合法，读取失败，请检查DB_URL的内容("+DB_URL+")");
				return false;
			}
			System.out.println("Error 1008:其他原因造成数据库表格搜索显示失败，请检查SQL语句："+SQL);
			return false;
		}
		return true;
	}
	
	//【SQl】记录排序显示(输入表格名称，显示字段(包含类型定义)，排序依据字段，排序标准；成功返回true，失败返回false)
	public boolean OrderRecord(String name,String item[],String orderitem,String order){
		String SQL=new String("");
		System.out.println("开始数据库表格排序……");
		int length=base.SArrayActualL(item);                                              //[Base]字符串数组实际有效长度
		try{
			stmt=conn.createStatement();                                                  //操作变量活动声明
			//制作SQL语句(读取数据库记录)
			SQL="SELECT ";
			for(int i=0;i<length-1;i++){
				SQL=SQL+item[i].substring(1)+", ";
			}
			SQL=SQL+item[length-1].substring(1)+" FROM "+name;
			SQL=SQL+" ORDER BY "+orderitem+" "+order;                                     //DESC降序；ASC升序
			//显示SQL语句执行结果
			if(!ShowResult(SQL,length,item)){
				System.out.println("Error 1009:ShowResult返回失败，请检查SQL语句："+SQL);
				return false;
				}
			System.out.println("数据库表格排序成功！");
		}
		catch(Exception e){
			if(DB_URL.substring(13).indexOf("/")==-1){
				System.out.println("Error 1008:数据库路径不合法，读取失败，请检查DB_URL的内容("+DB_URL+")");
				return false;
			}
			System.out.println("Error 1008:其他原因造成数据库表格排序显示失败，请检查SQL语句："+SQL);
			return false;
		}
		return true;
	}
	
	//【SQL】显示SQL语句执行结果(输入SQL语句，输入显示字段数，输入显示字段(包含类型定义)；成功返回true，失败返回false)
	public boolean ShowResult(String SQL,int length,String item[]){
		try{
			ResultSet result=stmt.executeQuery(SQL);                                      //读取SQL执行结果至result变量
			while(result.next()){
				//读取result变量中的信息并显示
				for(int i=0;i<length;i++){
					if(item[i].substring(0,1).equals("I")){
						int temp=result.getInt(item[i].substring(1));                     //读取int格式的表格内容
						System.out.print("INT-"+item[i].substring(1)+":"+temp+"  ");
					}
					if(item[i].substring(0,1).equals("S")){
						String temp=result.getString(item[i].substring(1));               //读取String格式的表格内容
						System.out.print("String-"+item[i].substring(1)+":"+temp+"  ");
					}
				}
				System.out.println("");
			}
			result.close();                                                               //关闭连接
		}
		catch(Exception e){
			if(DB_URL.substring(13).indexOf("/")==-1){
				System.out.println("Error 1009:路径不合法，读取失败，请检查DB_URL的内容("+DB_URL+")");
				return false;
			}
			for(int i=0;i<length;i++){
				if(!item[i].substring(0,1).equals("I")&&!item[i].substring(0,1).equals("S")){
					System.out.println("Error 1009:字段类型定义未填写或不能识别");
				}
			}			
			System.out.println("Error 1009:其他原因造成数据库结果显示失败，请检查SQL语句："+SQL);
			return false;
		}
		return true;
	}
	
	/** @Module:语句执行模块  */
	//【执行】SQL语句(DDL:数据定义语言)执行
	public boolean SQLDo(String SQL){
		Show("正在执行(操作)SQL:"+SQL);
		try{
			stmt.executeUpdate(SQL);
			return true;
		}
		catch(Exception e){
			Show("Error 1031",":SQL执行异常("+SQL+")");
			return false;
		}
	}
	
	//【SQL】SQL语句(DML:数据操作语言)执行(输入SQL语句，输入显示字段(包含类型定义)；成功返回true，失败返回false)
	public boolean SQLDo(String SQL,String item[]){
		Show("正在执行(显示)SQL:"+SQL);
		int length=base.SArrayActualL(item);                                              //[Base]字符串数组实际有效长度
		try{
			ResultSet result=stmt.executeQuery(SQL);                                      //读取SQL执行结果至result变量
			while(result.next()){
				//读取result变量中的信息并显示
				for(int i=0;i<length;i++){
					if(item[i].substring(0,1).equals("I")){
						int temp=result.getInt(item[i].substring(1));                     //读取int格式的表格内容
						System.out.print("INT-"+item[i].substring(1)+":"+temp+"  ");
					}
					if(item[i].substring(0,1).equals("S")){
						String temp=result.getString(item[i].substring(1));               //读取String格式的表格内容
						System.out.print("Str-"+item[i].substring(1)+":"+temp+"  ");
					}
				}
				System.out.println("");
			}
			result.close();                                                               //关闭连接
		}
		catch(Exception e){
			for(int i=0;i<length;i++){
				if(!item[i].substring(0,1).equals("I")&&!item[i].substring(0,1).equals("S")){
					Show("Error 1032",":第"+i+"条字段类型("+item[i]+")未填写或未能识别");
				}
			}			
			Show("Error 1032",":其他原因造成字段类型读取失败，请检查SQL语句："+SQL);
			return false;
		}
		return true;
	}

	/** @Module:异常处理模块  */
	//【异常】命名异常性检查(输入名字；合格返回true，不合格返回false)
	private void NameTest(String name){
		if(name.indexOf("/")!=-1){                                                    //名字内包含"/"造成错误
			Show("Error 1041",":表格名称中包含非法字符，表格删除失败，请检查表格名称("+name+")");
		}
	}
	
	//【异常】调试显示(输入普遍显示内容，调试显示内容)
	private void Show(String show,String test){
		if(debug==true){
			System.out.print(show);
			System.out.println(test);
		}
		else{
			System.out.println(show);
		}
	}
	
	//【异常】调试显示(输入调试显示内容)
	private void Show(String test){
		if(debug==true){
			System.out.println(test);
		}
	}
}