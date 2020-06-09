package main;

public class Poem {
	public static void main(String[] args) {
		//程序预处理
		LoadJDBCDriver();                                                       //JDBC驱动加载(MySQL数据库使用支持)
		CheckDat();
		//主程序启动
		System.out.println("欢迎打开机器诗人");
		News news=new News();
		news.AddMouse();
		//Antithesis antithesis=new Antithesis();
		//antithesis.DataConnect();                                             //[Antithesis]构建MySQL数据库连接
		Rhyme rhyme=new Rhyme();                                                //初始化韵部类(★★★☆)
		//rhyme.DataConnect();                                                  //[Antithesis]构建MySQL数据库连接
		rhyme.DDLConn();
		//rhyme.DDLDropTable();
		rhyme.DDLCreateTable();
		//rhyme.DMLInsert("孔",1);
		//rhyme.DMLShow();
		rhyme.STPCloseConnection();
	}
	
	//JDBC驱动加载(MySQL数据库使用支持)
	public static void LoadJDBCDriver(){
		String JDBC_DRIVER="com.mysql.jdbc.Driver";                             //JDBC驱动地址
		try{
			Class.forName(JDBC_DRIVER);                                         //申请JDBC加载驱动
			System.out.println("JDBC驱动加载成功……");
		}
		catch(ClassNotFoundException e){
			System.out.println("JDBC驱动加载失败！数据库功能将失效！");
		}
	}
	
	//检查基础数据库完整(PoemData模块、Rhyme模块支持)
	public static boolean CheckDat(){
		FileODo fileODo=new FileODo();                                          //初始化文件外操作类(★)
		Base base=new Base();                                                   //初始化底层类(☆)
		String path=base.FileOReadNowPath();
		if(!fileODo.Exist(path+"\\data\\rhnName.dat")){
			System.out.println("rhnName.dat丢失，韵部名称查询功能失效！");
			return false;
		}
		if(!fileODo.Exist(path+"\\data\\Poem.dat")||!fileODo.Exist(path+"\\data\\Index.dat")){
			System.out.println("Poem.dat或Index.dat文件丢失，诗库功能失效！");
			return false;
		}
		System.out.println("基础数据库检查通过……");
		return true;
	}
}