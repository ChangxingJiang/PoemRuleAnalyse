package main;

import java.awt.event.*;
import javax.swing.JFrame;

class News extends JFrame{
	private static final long serialVersionUID = 1L;
	//[初始化程序类]
	Rule rule=new Rule();                         //初始化格律分析类(★★★★)
	Rhyme rhyme=new Rhyme();                      //初始化韵部类(★★★☆)
	Antithesis antithesis=new Antithesis();       //初始化对仗类(★★★☆)
	PoemData poemData=new PoemData();             //初始化诗库类(★★)
	Window window=new Window();                   //初始化窗口类(★)
	FileODo fileODo=new FileODo();                //初始化文件外操作类(★)
	FileIDo fileIDo=new FileIDo();                //初始化文件内操作类(★)
	InnerData data=new InnerData();               //初始化内置数据库(☆)
	Base base=new Base();                         //初始化底层类(☆)
	
	//【构造器】
	News(){}
	
	//【消息接受、发送核心处理器开关】
	public void AddMouse(){
		ListenMouse();
	}	
	
	//【消息接受、发送核心处理器】
	//[消息接收器]鼠标消息接收器
	public void ListenMouse(){
		window.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){  
				if(e.getButton()==MouseEvent.BUTTON1){                //点击鼠标左键  
					int x=e.getX();
					int y=e.getY();
					ListenMouseLB(x,y);
				}
				if(e.getButton()==MouseEvent.BUTTON2){               //点击鼠标滑轮  
					int x=e.getX();
					int y=e.getY();
					ListenMouseRO(x,y);
				}
				if(e.getButton()==MouseEvent.BUTTON3){               //点击鼠标右键  
					int x=e.getX();
					int y=e.getY();
					System.out.println("坐标：x="+x+";y="+y);         //【测试】点坐标收集器(相对窗口左上角)
					ListenMouseRB(x,y);
				}
			} 
		});
	}

	//[消息发送器]鼠标左键消息发送器(输入鼠标左键x,y坐标)
	public void ListenMouseLB(int x,int y){
		ButtonCloseAll(x,y);                                          //【按钮-通用】关闭窗口
		ButtonMainWindow(x,y);                                        //【按钮-通用】主界面开关
		ButtonAnalyseWindow(x,y);                                     //【按钮-通用】分析格律界面开关
		ButtonDataBaseWindow(x,y);                                    //【按钮-通用】数据库操作界面开关
		ButtonTester(x,y);                                            //【按钮-通用】测试开关
		//[界面2：格律分析按钮]
		if(window.nowWindow==2){
			ButtonAnalysePoem(x,y);                                   //【按钮-界面2】诗句分析(Rule接口)
			ButtonClearInputPoem(x,y);                                //【按钮-界面2】页面清除
		}
		//[界面3：数据库操作按钮]
		if(window.nowWindow==3){
			ButtonBuildPoemData(x,y);                                 //【按钮-界面3】重建诗库数据库
			ButtonReadTXT(x,y);                                       //【按钮-界面3】从ReadIn文件中读取
			ButtonSearch(x,y);                                        //【按钮-界面3】数据库搜索
			ButtonRhyme(x,y);                                         //【按钮-界面3】韵部分析提示
		}
	}
	
	//[消息发送器]鼠标右键消息发送器(输入鼠标右键x,y坐标)
	public void ListenMouseRB(int x,int y){		
	}
	
	//[消息发送器]鼠标滚轮消息发送器(输入鼠标滚轮x,y坐标)
	public void ListenMouseRO(int x,int y){
	}
	
	//【按钮判断操作器】
	//[按钮-通用]关闭窗口(输入鼠标左键x,y坐标)
	public void ButtonCloseAll(int x,int y){
		if(x>941&&x<978&&y>52&&y<73){                                 //判断鼠标点击落在按钮上
			System.exit(0);                                           //关闭窗口(设置关闭方式0为正常)
		}
	}
	
	//[按钮-通用]界面开关(输入鼠标左键x,y坐标)
	public void ButtonMainWindow(int x,int y){
		if(x>348&&x<423&&y>57&&y<75){                                 //判断鼠标点击落在按钮上
			if(window.nowWindow!=1){
				window.DrawCloseNowWindow();                          //【绘制】关闭当前界面
				window.DrawMainWindow();                              //【绘制】窗口主界面
			}
		}
	}
	
	//[按钮-通用]分析格律界面开关(输入鼠标左键x,y坐标)
	public void ButtonAnalyseWindow(int x,int y){
		if(x>456&&x<520&&y>57&&y<75){                                 //判断鼠标点击落在按钮上
			if(window.nowWindow!=2){
				window.DrawCloseNowWindow();                          //【绘制】关闭当前界面
				window.DrawAnalyseWindow();                           //【绘制】格律分析窗口
			}
		}
	}
	
	//[按钮-通用]数据库操作界面开关(输入鼠标左键x,y坐标)
	public void ButtonDataBaseWindow(int x,int y){
		if(x>582&&x<658&&y>56&&y<75){                                 //判断鼠标点击落在按钮上
			if(window.nowWindow!=3){
				window.DrawCloseNowWindow();                          //【绘制】关闭当前界面
				window.DrawDatabaseWindow();                          //【绘制】界面：数据库操作
			}
		}
	}
	
	//[按钮-通用]测试开关；左上角茶杯图标(输入鼠标左键x,y坐标)
	public void ButtonTester(int x,int y){
		if(x>24&&x<92&&y>36&&y<94){                                   //判断鼠标点击落在按钮上
		}
	}
	
	//[按钮-界面2]诗句分析[Rule](输入鼠标左键x,y坐标)
	public void ButtonAnalysePoem(int x,int y){
		if(x>188&&x<283&&y>636&&y<661){                               //判断鼠标点击落在按钮上
			String poem=window.inputPoem.getText();                   //【消息】读取"诗文输入文本区"输入内容
			rule.ReadFromString(poem);                                //[Rule]提交输入内容进行分析
			String analyse=rule.RuleEntrance();                       //[Rule]接受分析结果
			window.outputAnalyse.setText(analyse);                    //【显示】"诗文评价输出文本区"显示评价内容
		}
	}
	
	//[按钮-界面2]页面清除(输入鼠标左键x,y坐标)
	public void ButtonClearInputPoem(int x,int y){
		if(x>291&&x<359&&y>636&&y<661){                               //判断鼠标点击落在按钮上
			window.inputPoem.setText(null);                           //【显示】清空"诗文输入文本区"
			window.outputAnalyse.setText(null);                       //【显示】清空"诗文评价输出文本区"
		}
	}
	
	//[按钮-界面3]重建诗库数据库(输入鼠标左键x,y坐标)
	public void ButtonBuildPoemData(int x,int y){
		if(x>62&&x<159&&y>193&&y<217){                                //判断鼠标点击落在按钮上
			poemData.FoundPoemBase();                                 //[PoemData]建立空的诗库
			window.ShowdatabaseArea("成功建立空的诗库……\n");
			poemData.FoundIndexBase();                                //[PoemData]建立空的索引库
			window.ShowdatabaseArea("成功建立空的索引库……\n");
		}
	}
	
	//[按钮-界面3]从ReadIn.txt文件中读取(输入鼠标左键x,y坐标)
	public void ButtonReadTXT(int x,int y){
		if(x>62&&x<159&&y>217&&y<247){                                //判断鼠标点击落在按钮上
			window.ShowdatabaseArea("正在从ReadIn.txt文件中读取……\n");
			poemData.TXTRead();                                       //[PoemData]读取ReadIn文件中的内容
			window.ShowdatabaseArea("从文件中读取成功……\n");
		}
	}
	
	//[按钮-界面3]数据库搜索(输入鼠标左键x,y坐标)
	public void ButtonSearch(int x,int y){
		if(x>906&&x<960&&y>131&&y<148){                               //判断鼠标点击落在按钮上
			String search=window.search.getText();                    //【消息】读取"数据库搜索文本框"输入内容
			int test=AnalyseKind(search);
			if(test==1){                                              //→数字编码搜索
				LinkPDSearchCode(search);                             //[PoemData]数据库编码搜索查询
			}
			if(test==2){
				LinkPDSearchName(search.substring(1));
			}
			if(test==3){
				rhyme.DDLConn();
				rhyme.IFSReadOrder(search.substring(3));
				rhyme.STPCloseConnection();
			}
		}
	}
	
	//[按钮-界面3]韵部分析提示(输入鼠标左键x,y坐标)
	public void ButtonRhyme(int x,int y){
		if(x>333&&x<398&&y>86&&y<100){                                //判断鼠标点击落在按钮上
			window.search.setText("RH-");
			window.ShowdatabaseArea("****韵部分析用法索引****\n");
			window.ShowdatabaseArea("①添加汉字到韵部：'RH-'+A韵部号+':'+汉字'\n");
			window.ShowdatabaseArea("②显示全部数据库：'RH-'+W\n");
		}
	}
	
	//【接口模块】调用其他对象模块
	//[PoemData]数据库编码搜索(输入搜索字符串，直接显示)
	public void LinkPDSearchCode(String search){
		int number=base.CharToFigure(search);
		window.ShowdatabaseArea("[查找编号]"+search+"......\r\n");
		poemData.ReadOnePoem(number);                                 //[PoemData]读取数据库至类中变量
		String from=poemData.LinkReadFrom();                          //[PoemData]读取当前操作诗文-诗文出处
		String name=poemData.LinkReadName();                          //[PoemData]读取当前操作诗文-诗名
		String author=poemData.LinkReadAuthor();                      //[PoemData]读取当前操作诗文-作者名
		int line=poemData.LinkReadLine();                             //[PoemData]读取当前操作诗文-诗句数
		String sen[]=poemData.LinkReadSentence();                     //[PoemData]读取当前操作诗文-诗句内容
		window.ShowdatabaseArea("诗文出处："+from+"\r\n");
		window.ShowdatabaseArea("诗文名称："+name+"\r\n");
		window.ShowdatabaseArea("诗文作者："+author+"\r\n");
		window.ShowdatabaseArea("诗文内容：\r\n");
		for(int i=0;i<line;i++){
			String temp=new String("");
			if(i%2==0){
				temp="，";
			}
			else{
				temp="。\r\n";
			}
			window.ShowdatabaseArea(sen[i]+temp);
		}
	}
	
	//[PoemData]数据库诗名搜索(输入搜索诗名，直接显示)
	public void LinkPDSearchName(String search){
		window.ShowdatabaseArea("[查找诗名]"+search+"......\r\n");
		String name[]=poemData.SearchByName(search);                  //[PoemData]在数据库中查询诗文(利用诗文名称)
		for(int i=1;i<101;i++){
			if(name[i-1]!=null){
				window.ShowdatabaseArea(Integer.toString(i,10)+":"+name[i-1]+"\r\n");
			}
			else{
				break;
			}
		}
	}
	
	//[工具]分析数据库搜索类型(输入搜索字符串，返回类型:1为编号查询,2为诗名查询)
	private int AnalyseKind(String search){
		int back=0;
		//[检测是否为数字编码搜索]
		int test=base.IsChar(search);                                 //[Base]判断是否为数字
		String sign=search.substring(0,1);                            //截取标识符
		if(test==-1){
			back=1;
		}
		if(sign.equals("#")){
			back=2;
		}
		sign=search.substring(0,2);                            //截取标识符
		if(sign.equals("RH")){                //韵部分析状态
			back=3;
		}
		return back;
	}	
}