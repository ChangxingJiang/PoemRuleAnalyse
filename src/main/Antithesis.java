package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class Antithesis{
	/** 
	 * @ProjectName:Poem(机器诗人)
	 * @Package:main
	 * @ClassName:Antithesis(对仗)
	 * @Description:进行对仗方面的计算
	 * @Module:启动模块;接口模块;句子相关对仗性分析模块(相关性分+句子位置加成+权重缩减算法+平仄分析);查重模块;存储模块
	 * @Author:长行
	 * @CreateDate:2016.11.16
	 * @Version:1.0
	 * @interface:
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
	//[初始化程序类]
	MySQL mySQL=new MySQL();                      //MySQL操作类(★)
	
	//【构造器】
	Antithesis(){}
	
	/** @Module:启动模块	 */	
	//【启动】构建MySQL数据库连接(成功返回true，失败返回false)
	public boolean DataConnect(){
		if(!mySQL.STPLoadDriver()){                                                       //[MySQL]加载MySQL驱动
			System.out.println("Error 2101:Antithesis数据库JDBC加载驱动异常");
			return false;
		}
		String path="Antithesis";
		if(!mySQL.STPOpenConnection(path)){                                               //[MySQL]与Antithesis数据库建立连接
			System.out.println("Error 2101:数据库连接异常");
			return false;
		}
		return true;
	}
	
	/** @Module:查重模块	 */	
	//【查重】检查当前词是否重复(输入查询句(上联:下联)，是否记忆当前句；重复返回true，不重复返回false)
	public boolean DataCheckOnce(String sentence,boolean write){
		return true;
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
}