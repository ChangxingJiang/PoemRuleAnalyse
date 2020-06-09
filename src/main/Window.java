package main;

import java.awt.*;                      //包含用于创建用户界面和绘制图形图像的所有类
import javax.swing.*;                   //窗口支持

class Window extends JFrame{
	private static final long serialVersionUID = 4876287045280354524L;
	//[窗口常量]
	int screenx=0,screeny=0;                      //【常量】屏幕水平分辨率(宽度)、垂直分辨率(高度)
	String path=new String("");                   //【常量】程序根目录地址
	int windowx=0,windowy=0;                      //【常量】窗口左上角横坐标、纵坐标
	
	//[窗口变量]
	int nowWindow=0;                              //【变量】当前打开界面(1为主窗口;2为格律分析)
	
	//[定义字体]
	Font song15b=new Font("宋体",Font.BOLD,15);   //【字体】宋体15号加粗(Font.BOLD加粗)
	Font song20b=new Font("宋体",Font.BOLD,20);   //【字体】宋体20号加粗(Font.BOLD加粗)
	Font yh15=new Font("微软雅黑",Font.PLAIN,15); //【字体】微软雅黑15号(Font.PLAIN普通字体)
	Font yh20=new Font("微软雅黑",Font.PLAIN,20); //【字体】微软雅黑20号(Font.PLAIN普通字体)
	Font yh24=new Font("微软雅黑",Font.PLAIN,24); //【字体】微软雅黑24号(Font.PLAIN普通字体)
	
	//[定义面板容器]
	JPanel Main=new JPanel();                     //【容器】(界面1)主界面面板容器
	JPanel Analyse=new JPanel();                  //【容器】(界面2)分析诗文界面面板容器
	JPanel Database=new JPanel();                 //【容器】(界面3)数据库操作界面面板容器
	
	//[定义文本区]
	TextArea inputPoem;                           //【文本】(界面2：格律分析)诗文输入文本区
	TextArea outputAnalyse;                       //【文本】(界面2：格律分析)诗文评价输出文本区
	TextArea databaseArea;                        //【文本】(界面3：数据库操作)数据库临时显示文本区
	//[定义文本框]
	JTextField search;                            //【文本】(界面3：数据库分析)数据库搜索文本框
	
	//[初始化程序类]
	Base base=new Base();                         //初始化底层类(☆)
	
	//【构造器】自动打开主界面
	Window(){
		InitializeWindow();                                           //【初始化】窗口基本设置
		DrawMainWindow();                                             //【绘制】窗口主界面
		setVisible(true);                                             //【绘制】窗口显示
	}
	
	//【初始化】窗口定义(分辨率、地址、窗口位置、窗口基本设置)
	public void InitializeWindow(){
		//[读取屏幕分辨率]
		Toolkit tool=getToolkit();                                    //初始化抽象超类Toolkit(支持读取显示器分辨率)
		Dimension dim=tool.getScreenSize();                           //读取显示器分辨率(dim.width为宽度,dim.height为高度)
		screenx=dim.width;
		screeny=dim.height;
		//[当前地址读取]
		path=base.FileOReadNowPath();                                 //读取当前地址
		//[窗口位置计算]
		windowx=(screenx-1024)/2;                                     //计算窗口左上角横坐标
		windowy=(screeny-768)/2;                                      //计算窗口左上角纵坐标
		//[窗口基本设置]
		setTitle("机器诗人");                                         //【定义】窗口名称
		setSize(1024,768);                                            //【定义】窗口尺寸
		setLocation(windowx,windowy);                                 //【定义】窗口位置
		setUndecorated(true);                                         //【定义】是否去除标题栏(true为去除，false为保留)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);               //【定义】窗口关闭按钮生效
//		setExtendedState(JFrame.MAXIMIZED_BOTH);                      //【定义】窗口的扩展状态(NORMAL默认;ICONIFIED最小化;MAXIMIZED_HORIZ水平方向最大化;MAXIMIZED_VERT垂直方向最大化;MAXIMIZED_BOTH水平方向和数值方向都最大化)
//		setResizable(false);                                          //【定义】设置窗口大小为不可变
//		setLayout(new FlowLayout());                                  //【定义】流式布局管理器(与JPanel的显示冲突)
//		AWTUtilities.setWindowOpaque(this,false);                     //【定义】屏幕透明化(不稳定，问题众多)
	}
		
	//【绘制】界面：主窗口
	public void DrawMainWindow(){
		nowWindow=1;
		Main.setLayout(null);                                         //清空布局管理器
		getContentPane().add(Main,BorderLayout.CENTER);               //边界布局管理器(中心部分、使用两个时会造成覆盖现象)
		//[绘制界面背景图]-Background.png
		ImageIcon icon=new ImageIcon(path+"\\window\\Background.png");//写入“主界面背景图”路径
		JLabel back=new JLabel(icon);                                 //将“主界面背景图”画入标签
		back.setBounds(0,0,1024,768);                                 //定义标签位置、大小(相对窗口位置)
		//[绘制界面主图]-Main.png
		icon=new ImageIcon(path+"\\window\\Main.png");                //写入“主界面背景图”路径
		JLabel main=new JLabel(icon);                                 //将“主界面背景图”画入标签
		main.setBounds(97,108,893,591);                               //定义标签位置、大小(相对窗口位置)
		back.add(main);                                               //将主图叠放到背景图上
		Main.add(back);                                               //将总图加入主界面面板容器
		Main.validate();                                              //验证此容器及其所有子组件	
		Main.setVisible(true);                                        //显示主界面面板容器
	}
	
	//【绘制】界面：格律分析
	public void DrawAnalyseWindow(){
		nowWindow=2;
		Analyse.setLayout(null);                                      //清空布局管理器
		getContentPane().add(Analyse,BorderLayout.CENTER);            //边界布局管理器(中心部分、使用两个时会造成覆盖现象)
		//[绘制界面背景图]-Background.png
		ImageIcon icon=new ImageIcon(path+"\\window\\Analyse.png");   //写入“分析界面背景图”路径
		JLabel back=new JLabel(icon);                                 //将“分析界面背景图”画入标签
		back.setBounds(0,0,1024,768);                                 //定义标签位置、大小(相对窗口位置)
		//[定义文本区]-(窗口2：格律分析)诗文输入文本区
		inputPoem=new TextArea("",28,10,TextArea.SCROLLBARS_NONE);    //定义文本区：文本区内容+文本区每行字符数+文本区行数+滚动条(SCROLLBARS_BOTH垂直、水平滚动条，SCROLLBARS_VERTICAL_ONLY为垂直滚动条，SCROLLBARS_HORIZONAL_ONLY水平滚动条，SCROLLBARS_NONE无滚动条)
		inputPoem.setText("[请输入要分析的诗文]");                    //设置文本区初始文本
		inputPoem.setFont(yh24);                                      //设置文本区字体(微软雅黑24号)
		inputPoem.setForeground(Color.black);                         //设置文本区字体颜色
//		inputPoem.addTextListener(this);                              //设置文本区内文本监听(需要TextListener支持)
		inputPoem.setBackground(new Color(255,250,245));              //设置文本区背景颜色
		inputPoem.setEditable(true);                                  //设置文本区不可编辑(true为可编辑，false为不可编辑)
		inputPoem.setBounds(55,154,450,470);                          //设置文本区位置
		inputPoem.selectAll();                                        //选中文本区的全部文本
		back.add(inputPoem);                                          //将诗文输入文本区叠放到背景图上
		//[定义文本区]-(窗口2：格律分析)诗文评价输出文本区
		outputAnalyse=new TextArea("",20,20,TextArea.SCROLLBARS_NONE);//定义文本区：文本区内容+文本区每行字符数+文本区行数+滚动条(SCROLLBARS_BOTH垂直、水平滚动条，SCROLLBARS_VERTICAL_ONLY为垂直滚动条，SCROLLBARS_HORIZONAL_ONLY水平滚动条，SCROLLBARS_NONE无滚动条)
		outputAnalyse.setFont(yh20);                                  //设置文本区字体(微软雅黑20号)
		outputAnalyse.setForeground(Color.black);                     //设置文本区字体颜色
		outputAnalyse.setBackground(new Color(255,250,245));          //设置文本区背景颜色
		outputAnalyse.setEditable(false);                             //设置文本区不可编辑(true为可编辑，false为不可编辑)
		outputAnalyse.setBounds(518,154,407,470);                     //设置文本区位置
		back.add(outputAnalyse);                                      //将诗文评价输出文本区叠放到背景图上
		Analyse.add(back);                                            //将总图加入主界面面板容器
		Analyse.validate();                                           //验证此容器及其所有子组件	
		Analyse.setVisible(true);                                     //显示主界面面板容器
	}
	
	//【绘制】界面：数据库操作
	public void DrawDatabaseWindow(){
		nowWindow=3;
		Database.setLayout(null);                                     //清空布局管理器
		getContentPane().add(Database,BorderLayout.CENTER);           //边界布局管理器(中心部分、使用两个时会造成覆盖现象)
		//[绘制界面背景图]-Database.png
		ImageIcon icon=new ImageIcon(path+"\\window\\Database.png");  //写入“主界面背景图”路径
		JLabel back=new JLabel(icon);                                 //将“主界面背景图”画入标签
		back.setBounds(0,0,1024,768);                                 //定义标签位置、大小(相对窗口位置)
		//[定义文本区]-(界面3：数据库操作)数据库临时显示文本区
		databaseArea=new TextArea("",20,20,TextArea.SCROLLBARS_NONE); //定义文本区：文本区内容+文本区每行字符数+文本区行数+滚动条(SCROLLBARS_BOTH垂直、水平滚动条，SCROLLBARS_VERTICAL_ONLY为垂直滚动条，SCROLLBARS_HORIZONAL_ONLY水平滚动条，SCROLLBARS_NONE无滚动条)
		databaseArea.setText("数据库临时显示……\n");                   //设置文本区初始文本
		databaseArea.setFont(yh15);                                   //设置文本区字体(微软雅黑15号)
		databaseArea.setForeground(Color.black);                      //设置文本区字体颜色
		databaseArea.setBackground(new Color(255,250,245));           //设置文本区背景颜色
		databaseArea.setEditable(false);                              //设置文本区不可编辑(true为可编辑，false为不可编辑)
		databaseArea.setBounds(296,177,667,455);                      //设置文本区位置
		back.add(databaseArea);                                       //将数据库临时显示文本区叠放到背景图上
		//[定义文本框]-(界面3：数据库操作)数据库搜索文本框
		search=new JTextField("",60);                                 //定义文本框：文本框内容+文本框每行字符数
		search.setFont(yh15);                                         //设置文本框字体(微软雅黑15号)
		search.setForeground(Color.black);                            //设置文本框字体颜色
		search.setBackground(new Color(255,250,245));                 //设置文本框背景颜色
		search.setEditable(true);                                     //设置文本框不可编辑(true为可编辑，false为不可编辑)
		search.setBounds(320,132,572,17);                             //设置文本框位置
		search.setHorizontalAlignment(JTextField.LEFT);               //设置文本框对齐方式(JTextField.LEFT左对齐,JTextField.CENTER居中,JTextField.RIGHT右对齐)
 		back.add(search);                                             //将数据库搜索文本框叠放到背景图上
		Database.add(back);                                           //将总图加入主界面面板容器
		Database.validate();                                          //验证此容器及其所有子组件	
		Database.setVisible(true);                                    //显示主界面面板容器
	}
	
	//【绘制】界面：关闭当前界面
	public void DrawCloseNowWindow(){
		if(nowWindow==1){                                             //主界面打开状态
			Main.setVisible(false);                                   //【绘制】关闭主界面
		}
		if(nowWindow==2){                                             //格律分析界面打开状态
			Analyse.setVisible(false);                                //【绘制】关闭格律分析界面
		}
		if(nowWindow==3){                                             //格律分析界面打开状态
			Database.setVisible(false);                               //【绘制】关闭数据库操作界面
		}
	}
	
	//【显示】数据库临时显示文本区
	public void ShowdatabaseArea(String show){
		databaseArea.append(show);
	}
}