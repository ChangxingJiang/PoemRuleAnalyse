package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Rhyme{
	/** 
	 * @ClassName:Rhyme(韵部)
	 * @Description:对韵部进行存储分析
	 * @Module:启动模块;接口模块;存储查询(DML)模块;操作(DDL)模块
	 * @CreateDate:2016.07.12
	 * @Version:1.0
	**/	
	//[定义常量]
	boolean debug=true;                           //调试开关(true为打开)
	static final String USER="root";              //MySQL数据库用户名
	static final String PASS="pgybdc";            //MySQL数据库密码
	static String DB_DDL="jdbc:mysql://localhost";//填写DDL(数据定义语言)工作地址
	static String DB_DML="jdbc:mysql://localhost/Rhyme";//填写DML(数据操纵语言)工作地址
	//[定义变量]
	Connection conn=null;                         //连接变量
	Statement stst=null;                          //操作变量(SELECT专用)
	Statement stit=null;                          //操作变量(INSECT专用)
	int cat=1;                                    //韵部种类：1为平水韵，2为词林正韵，3为中原音韵
	String[] itemW=new String[5];                 //平水韵存储库字段结构
	
	//[初始化程序类]
	MySQL mySQL=new MySQL();                      //MySQL操作类(★)
	Base base=new Base();                         //初始化底层类(☆)
	InnerData data=new InnerData();               //内置数据库初始化(☆)
	
	//【构造器】
	Rhyme(){}
	
	/** @Module:启动模块	 */
	/*【启动】构建MySQL数据库连接(成功返回true，失败返回false)
	public boolean DataConnect(){
		if(!mySQL.STPLoadDriver()){                                             //MySQL-加载MySQL驱动
			System.out.println("Error 2101:Rhyme数据库JDBC加载驱动异常");
			return false;
		}
		String path="Rhyme";
		if(!mySQL.STPOpenConnection(path)){                                     //MySQL-与Antithesis数据库建立连接
			System.out.println("Error 2101:数据库连接异常");
			return false;
		}
		return true;
	}*/
	
	/** @Module:接口模块	 */
	//【接口】读取用户输入命令
	public boolean IFSReadOrder(String order){
		Showln("韵部分析模块接到命令——"+order);
		String sign=order.substring(0,1);                                       //截取命令标识符
		if(sign.equals("A")){                                                   //判断是否为数据库添加模式
			int rhn=base.CharToFigure(order.substring(1,order.indexOf(":")));   //读取命令中的韵部号
			String chars=order.substring((order.indexOf(":"))+1);               //读取命令中的汉字
			IFSAddRhyme(chars,rhn);                                             //Rhyme-IFS-数据库添加命令
		}
		if(sign.equals("W")){
			DMLShow();
		}
		if(sign.equals("S")){
			int rhn[]=DMLSearch(order.substring(1));
			for(int i=0;i<3;i++){
				if(rhn[i]!=-1){
					Showln("'"+order.substring(1)+"'属于"+GetRhnName(rhn[i])+"("+rhn[0]+")韵");
				}
			}
		}
		return true;
	}
	
	//【接口】数据库添加命令(输入字符、输入韵部号)
	public boolean IFSAddRhyme(String chars,int rhn){
		while(chars!=null){
			DMLInsert(chars.substring(0,1),rhn);                                //Rhyme-DML-在韵部表插入一条记录
			if(chars.length()>1){
				chars=chars.substring(1);
			}
			else{
				break;
			}
		}		
		return true;
	}	
	
	/** @Module:存储(DML)模块	 */
	//【DML】韵部表插入一条记录(输入字符、韵部号；成功返回true，失败返回false)
	public boolean DMLInsert(String chars,int rhn){
		//搜索该字符是否已经在数据库中出现
		String SQL=
				"SELECT chars, rhn1, rhn2, rhn3" +
				" FROM "+GetTableName()+
				" WHERE chars='"+chars+"'";                                     //[SQL]查询输入字符(读取所有列)
		Showln("正在执行SQL(SELECT)语句："+SQL);
		try{
			ResultSet result=stst.executeQuery(SQL);                            //读取SQL执行结果至result变量
			//计算当前result中的记录数
			int rowCount=0;
			try{
				result.last();                                                  //将指针移动到最后一条记录
				rowCount=result.getRow();
			}
			catch(SQLException e){
				Showln("读取result记录数失败");
			}
			result.first();
			//当数据库中没有该字符时创建该字符的记录
			if(rowCount==0){
				SQL="INSERT INTO "+GetTableName()+
					" VALUES ('"+chars+"', "+rhn+", -1, -1)";                   //[SQL]插入当前字符、韵部号
				Showln("正在执行SQL(INSERT)语句："+SQL);
				stit.executeUpdate(SQL);
			}
			//当数据库中已经有该字符时该韵部号已被记录
			if(rowCount==1){
				boolean dup=false;                                              //检查是否重复变量
				int dbrhn[]=new int[3];                                         //临时存储韵部号变量
				dbrhn[0]=result.getInt("rhn1");
				dbrhn[1]=result.getInt("rhn2");
				dbrhn[2]=result.getInt("rhn3");
				for(int i=0;i<3;i++){
					if(dbrhn[i]==rhn){
						dup=true;
					}
				}
				if(dup==true){                                                  //若数据库中已经记录该韵部号
					Showln("当前字符、韵部已被记录，跳过记录");
				}
				else{                                                           //若数据库中没有记录该韵部号
					for(int i=0;i<3;i++){
						if(dbrhn[i]==0){
							SQL="UPDATE "+GetTableName()+
								" SET rhn"+(i+1)+"='"+rhn+"'"+
								" WHERE chars='"+chars+"'";                     //[SQL]填写韵部号在该条记录中空韵部号位置
							Showln("正在执行SQL(INSERT)语句："+SQL);
							stit.executeUpdate(SQL);
							break;
						}
						//三个韵部号位置都已经填满时，自动跳出循环
					}
				}
			}
			//当数据库中该数据存在异常
			if(rowCount>1||rowCount<0){
				SQL="DELETE FROM "+GetTableName()+
					" WHERE chars='"+chars+"'";                                 //删除所有异常记录
				stit.executeUpdate(SQL);
			}
			result.close();
			return true;
		}
		catch(SQLException e){
			Showln("向"+GetTableName()+"韵部表中插入字符:"+chars+"("+rhn+")失败！");
			return false;
		}
	}
	
	//【DML】在韵部中搜索字符的韵部号(输入字符、韵部号；成功返回true，失败返回false)
	public int[] DMLSearch(String chars){
		int rhn[]=new int[3];
		//搜索该字符是否已经在数据库中出现
		String SQL=
				"SELECT chars, rhn1, rhn2, rhn3" +
				" FROM "+GetTableName()+
				" WHERE chars='"+chars+"'";                                     //[SQL]查询输入字符(读取所有列)
		Showln("正在执行SQL(SELECT)语句："+SQL);
		try{
			ResultSet result=stst.executeQuery(SQL);                            //读取SQL执行结果至result变量
			//计算当前result中的记录数
			int rowCount=0;
			try{
				result.last();                                                  //将指针移动到最后一条记录
				rowCount=result.getRow();
			}
			catch(SQLException e){
				Showln("读取result记录数失败");
			}
			result.first();
			//当数据库中没有该字符时创建该字符的记录
			if(rowCount==0){
				rhn[0]=-1;
				rhn[1]=-1;
				rhn[2]=-1;
				Showln("没有搜索到'"+chars+"'的韵部");
			}
			//当数据库中已经有该字符时该韵部号已被记录
			if(rowCount==1){
				rhn[0]=result.getInt("rhn1");
				rhn[1]=result.getInt("rhn2");
				rhn[2]=result.getInt("rhn3");
			}
		}
		catch(SQLException e){
			Showln("在"+GetTableName()+"韵部表中搜索字符'"+chars+"'失败！");
			rhn[0]=-1;
			rhn[1]=-1;
			rhn[2]=-1;
		}
		return rhn;
	}
	
	//【DML】显示当前韵部表中所有数据
	public boolean DMLShow(){
		String SQL=
				"SELECT chars, rhn1, rhn2, rhn3" +
				" FROM "+GetTableName();                                        //[SQL]显示当前韵部表格全部数据
		Show("正在执行SQL(SELECT)语句："+SQL+"——");
		try{
			ResultSet result=stst.executeQuery(SQL);
			Showln("\n****"+GetTableName()+"韵部表格内容****");
			while(result.next()){
				Show("字符："+result.getString("chars")+"  ");
				Show("韵部1："+result.getString("rhn1")+"  ");
				Show("韵部2："+result.getString("rhn2")+"  ");
				Showln("韵部3："+result.getString("rhn3"));
			}
			result.close();
			return true;
		}
		catch(SQLException e){
			Showln("显示当前韵部表格全部数据失败！");
			return false;
		}
	}
	
	/** @Module:操作(DDL)模块	 */
	//【DDL】与数据库建立连接
	public boolean DDLConn(){
		try{
			conn=DriverManager.getConnection(DB_DML,USER,PASS);                 //申请连接变量(conn)建立与数据库的连接
			Showln("与数据库建立连接成功……");
			stst=conn.createStatement();                                        //操作变量(stst)活动声明
			stit=conn.createStatement();
		}
		//“建立与数据库连接”异常处理
		catch(SQLException e1){
			if(DDLCreateDataBase()){
				try{
					conn=DriverManager.getConnection(DB_DML,USER,PASS);         //申请连接变量(conn)建立与数据库的连接
					Showln("与数据库建立连接成功！");
					stst=conn.createStatement();                                //操作变量(stst)活动声明
					stit=conn.createStatement();
				}
				catch(SQLException e2){
					return false;
				}
			}
			return false;
		}
		return true;
	}
	
	//【DDL】关闭连接(成功返回true，失败返回false)
	public boolean STPCloseConnection(){
		try{
			if(stst!=null){
				stst.close();
			}
			if(stit!=null){
				stit.close();
			}
			if(conn!=null){
				conn.close();
			}
			Showln("韵部数据库连接关闭成功……");
			return true;
		}
		catch(Exception e){
			Showln("数据库连接关闭失败！");
			return false;
		}
	}
	
	//【DDL】创建韵部数据库
	public boolean DDLCreateDataBase(){
		String SQL="CREATE DATABASE Rhyme";
		Show("正在执行SQL(CREATE)语句："+SQL+"——");
		try{
			conn=DriverManager.getConnection(DB_DDL,USER,PASS);                 //申请连接变量(conn)建立与数据库的连接
			stst=conn.createStatement();
			stst.executeUpdate(SQL);
			Showln("数据库Rhyme创建成功……");
		}
		catch(SQLException se){
			Showln("数据库Rhyme创建失败！");
		}
		return true;
	}
	
	//【DDL】创建韵部表格
	public boolean DDLCreateTable(){
		String SQL=
				"CREATE TABLE "+GetTableName()+" "+
				"(chars VARCHAR(4) not NULL, " +
				"rhn1 INTEGER, " +
				"rhn2 INTEGER, " +
				"rhn3 INTEGER, " +
				"PRIMARY KEY (chars))";
		Show("正在执行SQL(CREATE)语句："+SQL+"——");
		try{
			stst.executeUpdate(SQL);
			Showln("创建"+GetTableName()+"表格成功……");
			return true;
		}
		catch(SQLException se){
			Showln("创建"+GetTableName()+"表格失败！");
			return false;
		}
	}
	
	//【DDL】删除韵部表格
	public boolean DDLDropTable(){
		String SQL=
				"DROP TABLE "+GetTableName();
		Show("正在执行SQL(DROP)语句："+SQL+"——");
		try{
			stst.executeUpdate(SQL);
			Showln("删除"+GetTableName()+"表格成功……");
			return true;
		}
		catch(SQLException se){
			Showln("删除"+GetTableName()+"表格失败！");
			return false;
		}
	}

	//【Base】获取当前韵部类型表格名称(输入韵部号)
	public String GetRhnName(int rhn){
		String path=base.FileOReadNowPath();
		FileIDo fileIDo=new FileIDo(path+"\\data\\rhnName.dat");                //初始化文件内操作类(★)
		String back=fileIDo.Read(0,8);
		back=back.replace("　","");
		return back;
	}
	
	//【Base】获取当前韵部类型表格名称
	private String GetTableName(){
		String name=new String("");
		if(cat==1){
			name="Water";
		}
		return name;
	}
	
	//【Base】调试显示(输入调试显示内容)
	private void Show(String test){
		if(debug==true){
			System.out.print(test);
		}
	}
	
	//【Base】调试显示(输入调试显示内容)
	private void Showln(String test){
		if(debug==true){
			System.out.println(test);
		}
	}
	
	
	
	
	
	//【构造器】定义韵部构造器(韵部类型)
	Rhyme(int _cat){
		itemW[0]="Schars";               //int类型
		itemW[1]="Iwater1";              //Int类型
		itemW[2]="Iwater2";              //Int类型
		itemW[3]="Iwater2";              //int类型
		if(_cat==1||_cat==2||_cat==3){
			cat=_cat;
		}
		else{
			System.out.println("韵部构造器接受韵部编码错误，转入默认：平水韵");
			cat=1;
		}
	}
	
	//【查询】查询编码入口
	public int RhymeSearchCode(String character){
		int code=-1;
		switch(cat){
			case 0:code=-1;break;
			case 1:code=data.DataWaterChar(character);
			case 2:break;
			case 3:break;
			default:break;
		}
		return code;
	}
	
	//【查询】编码查询名称入口
	public String RhymeSearchName(int _code){
		int code=_code;
		String rhymeName=new String("");
		if(cat==1){
			if(code<0||code>105){
				System.out.println("韵部编码无效");
			}
			else{
				rhymeName=data.DataWaterCode(code);
			}
		}
		return rhymeName;
	}
	
	//【查询】编码查询平仄入口
	public int RhymeSearchTone(int _code){
		int code=_code;
		int tone=0;                                                   //Tone平仄变量：0为未知，1为平音，2为仄音
		if(cat==1){
			if(code<0||code>105){
				System.out.println("韵部编码无效");
			}
			else{
				if(code<31){
					tone=1;
				}
				else{
					tone=2;
				}
			}
		}
		return tone;
	}
}