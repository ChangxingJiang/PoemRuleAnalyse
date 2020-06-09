package main;

class PoemData{
	//[当前诗句变量]
	String from=new String();                     //诗文出处
	String name=new String();                     //诗名
	String author=new String();                   //作者名
	int line=0;                                   //诗句数
	String sen[]=new String[1000];                //诗句内容
	int extent[]=new int[1000];                   //诗句长度
	
	//[TXT读取临时变量]
	String temp=new String();                     //临时读取TXT一行存储变量
	boolean reading=false;                        //读取模式
	boolean cname=false,cauthor=false,ctext=false;//单首诗是否检索到诗名、作者、诗句
	
	//[初始化程序类]
	FileODo fileODo=new FileODo();                //初始化文件外操作类(★)
	InnerData data=new InnerData();               //初始化内置数据库(☆)
	Base base=new Base();                         //初始化底层类(☆)

	PoemData(){}//【构造器】无参数构造器
	
	//【数据库相关操作】建立、读取诗库、索引库相关操作
	//[数据库]建立空的诗库(在data文件夹下建立一个诗文数为0的诗库)
	public void FoundPoemBase(){
		String path=new String(""),time=new String("");
		path=base.FileOReadNowPath();                                 //读取当前地址
		path=path+"\\data\\Poem.dat";                                 //进入当前地址下data文件夹
		fileODo.FoundFile(path,true);                                 //[FileODo]创建文件
		FileIDo fileIDo=new FileIDo(path);                            //初始化文件内操作类(★)
		fileIDo.Initialize(200);                                      //[FileIDo]文件填"空"初始化
		fileIDo.Write(0,"AA");                                        //[FileIDo]写入诗库版本
		time=base.GetTime();                                          //[Base]读取系统时间
		fileIDo.Write(02,time);                                       //[FileIDo]写入时间
		fileIDo.Write(16,Integer.toString(200,10));                   //[FileIDo]写入诗库长度
		fileIDo.Write(25,Integer.toString(0,10));                     //[FileIDo]写入诗库诗文数
	}
	
	//[数据库]建立空的索引库(在data文件夹下建立一个诗文数为0的索引库)
	public void FoundIndexBase(){
		String path=new String(""),time=new String("");
		path=base.FileOReadNowPath();                                 //读取当前地址
		path=path+"\\data\\Index.dat";                                //进入当前地址下record文件夹
		fileODo.FoundFile(path,true);                                 //[FileODo]创建文件
		FileIDo fileIDo=new FileIDo(path);                            //初始化文件内操作类(★)
		fileIDo.Initialize(200);                                      //[FileIDo]文件填"空"初始化
		fileIDo.Write(0,"AA");                                        //[FileIDo]写入索引库版本
		time=base.GetTime();                                          //[Base]读取系统时间
		fileIDo.Write(02,time);                                       //[FileIDo]写入时间
		fileIDo.Write(16,Integer.toString(0,10));                     //[FileIDo]记录数(诗文数)
	}
	
	//[数据库]将类中变量写入数据库(输入是否清空当前变量数据，true为清空，false为不清空；返回数据是否成功)
	//计划：按字节截取存在问题
	public void WritePerPoem(boolean empty){
		String path=new String(""),temp=new String("");
		int irecord=0,precord=0;                                      //index库记录数、poem库记录数
		long lefile=0,leindex=0,count=0;                              //声明临时计算数、声明poem库指针，index库指针
		int lename=0,leauthor=0;                                      //诗名长度、作者名长度
		path=base.FileOReadNowPath();                                 //读取当前地址
		path=path+"\\data\\Poem.dat";                                 //进入当前地址下data文件夹peom文件
		FileIDo poem=new FileIDo(path);                               //初始化文件内操作类(★)
		path=base.FileOReadNowPath();                                 //读取当前地址
		path=path+"\\data\\Index.dat";                                //进入当前地址下record文件夹Index文件
		FileIDo index=new FileIDo(path);                              //初始化文件内操作类(★)
		lename=name.getBytes().length;
		leauthor=author.getBytes().length;
		for(int i=0;i<line;i++){                                      //分析诗句长度
			extent[i]=sen[i].getBytes().length;
		}
		//文件头修改
		temp=index.Read(16,5);                                        //[FileIDo]读取索引库记录数
		irecord=base.CharToFigure(temp);
		index.Write(16,Integer.toString(irecord+1,10));               //[FileIDo][Index文件头]写入索引库记录数
		System.out.println("——记录数:"+irecord+"\n");
		leindex=200+100*irecord;                                      //计算index库指针位置
		temp=poem.Read(16,9);                                         //[FileIDo]读取诗库文件长度
		lefile=base.CharToFigure(temp);
		temp=poem.Read(25,5);                                         //[FileIDo]读取诗库诗句数量
		precord=base.CharToFigure(temp);
		precord++;
		poem.Write(25,Integer.toString(precord,10));                  //[FileIDo][poem文件头]写入索引库记录数
		if(name.getBytes().length>14){                                //截取name的前14个字节
			temp=name.substring(0,7);
		}
		else{
			temp=name;
		}
		//诗库、索引库初始化
		index.Initialize(200+100*irecord+100);                        //[FileIDo]文件填"空"初始化
		count=lefile+lename+leauthor+19+2*line;
		for(int i=0;i<line;i++){
			count=count+extent[i];
		}
		poem.Initialize(count);                                       //[FileIDo]文件填"空"初始化
		//数据库写入
		index.Write(leindex,temp);                                    //[FileIDo][index]写入诗名摘要(七个字)
		index.Write(leindex+14,Long.toString(lefile+2));              //[FileIDo][index]写入诗名首坐标
		poem.Write(lefile,"①");                                      //[FileIDo][poem]写入诗名标志
		poem.Write(lefile+2,name);                                    //[FileIDo][poem]写入诗名
		lefile=lefile+2+lename;
		index.Write(leindex+22,Long.toString(lefile));                //[FileIDo][index]写入诗名尾坐标
		poem.Write(lefile,"②");                                      //[FileIDo][poem]写入来源标志
		index.Write(leindex+30,Long.toString(lefile+2));              //[FileIDo][index]写入来源头坐标
		poem.Write(lefile+2,from);                                    //[FileIDo][poem]写入来源
		poem.Write(lefile+12,"③");                                   //[FileIDo][poem]写入作者标志
		poem.Write(lefile+14,author);                                 //[FileIDo][poem]写入作者
		lefile=lefile+14+leauthor;
		index.Write(leindex+38,Long.toString(lefile));                //[FileIDo][index]写入作者尾坐标
		poem.Write(lefile,"④");
		index.Write(leindex+46,Long.toString(lefile+2));              //[FileIDo][index]写入诗文首坐标
		poem.Write(lefile+2,Integer.toString(line,10));               //[FileIDo][poem]写入诗文-诗句数
		for(int i=0;i<line;i++){
			poem.Write(lefile+5+2*i,Integer.toString(extent[i],10));  //[FileIDo][poem]写入诗文-诗句长度
		}
		count=0;                                                      //重置count
		for(int i=0;i<line;i++){
			poem.Write(lefile+5+2*line+count,sen[i]);                 //[FileIDo][poem]写入诗文-诗句长度
			count=count+extent[i];
		}
		lefile=lefile+5+2*line+count;
		index.Write(leindex+54,Long.toString(lefile));                //[FileIDo][index]写入诗文尾坐标
		if(empty==true){
			ResetVariate();
		}
		poem.Write(16,Long.toString(lefile));                         //[FileIDo][poem文件头]poem写入文件长度
	}
	
	//[数据库]读取数据库至类中变量(输入要读取的诗文编号，将数据读入PoemData类全局变量中)
	public boolean ReadOnePoem(int number){
		boolean back=true;                                            //定义返回变量
		long nfist=0,nlast=0,ffirst=0,alast=0,sfirst=0;       //声明index读取六个坐标(slast=0)
		String path=new String(""),temp=new String("");
		int record=0,count=0;
		ResetVariate();                                               //重置对象内写入变量
		path=base.FileOReadNowPath();                                 //读取当前地址
		path=path+"\\data\\Poem.dat";                                 //进入当前地址下data文件夹peom文件
		FileIDo poem=new FileIDo(path);                               //初始化文件内操作类(★)
		path=base.FileOReadNowPath();                                 //读取当前地址
		path=path+"\\data\\Index.dat";                                //进入当前地址下record文件夹Index文件
		FileIDo index=new FileIDo(path);                              //初始化文件内操作类(★)
		temp=index.Read(16,5);                                        //[FileIDo][index]读取记录数
//		System.out.println("记录数:"+temp);                           //[测试元件]
//		System.out.println("number:"+number);                         //[测试元件]
		record=base.CharToFigure(temp);
		if(number>=record){
			System.out.println("读取数值超过数据库记录数，无法读取");
			back=false;
		}
		else{
			//读取index库中坐标
			temp=index.Read(200+100*number+14,8);                     //[FileIDo][index]读取诗名首坐标
			nfist=base.CharToFigure(temp);
			temp=index.Read(200+100*number+22,8);                     //[FileIDo][index]读取诗名尾坐标
			nlast=base.CharToFigure(temp);
			temp=index.Read(200+100*number+30,8);                     //[FileIDo][index]读取来源首坐标
			ffirst=base.CharToFigure(temp);
			temp=index.Read(200+100*number+38,8);                     //[FileIDo][index]读取作者尾坐标
			alast=base.CharToFigure(temp);
			temp=index.Read(200+100*number+46,8);                     //[FileIDo][index]读取诗文首坐标
			sfirst=base.CharToFigure(temp);
			//temp=index.Read(200+100*number+54,8);                     //[FileIDo][index]读取诗文尾坐标
			//slast=base.CharToFigure(temp);
			//读取poem库中内容
			name=poem.Read(nfist,(int)(nlast-nfist));                 //[FileIDo][poem]读取诗名
			from=poem.Read(ffirst,10);                                //[FileIDo][poem]读取来源
			author=poem.Read((ffirst+12),(int)(alast-ffirst-12));     //[FileIDo][poem]读取作者
			temp=poem.Read(sfirst,3);                                 //[FileIDo][poem]读取诗文行数
			line=base.CharToFigure(temp);
			for(int i=0;i<line;i++){
				temp=poem.Read(sfirst+2*i+3,2);                       //[FileIDo][poem]读取诗文-诗句长度
				extent[i]=base.CharToFigure(temp);
				sen[i]=poem.Read(sfirst+2*(line)+3+count,extent[i]);  //[FileIDo][poem]读取诗文
				count=count+extent[i];
			}
		}
		return back;
	}
	
	//[数据库]在数据库中查询诗文(利用诗文名称；输入搜索诗名部分，返回包含该部分的所有诗名)
	public String[] SearchByName(String sname){
		String back[]=new String[1000];
		boolean test=true;
		int i=0,index=0,number=0;
		while(test==true){
			test=ReadOnePoem(i);                                      //[数据库]读取数据库至类中变量
			index=name.indexOf(sname);	                              //在name中搜索sname
			if(index!=-1){
				back[number]=name;
				number++;
			}
			i++;
			System.out.println(i);
		}
		return back;
	}
	
	//[数据库]在数据库中查询诗文(利用作者名称；输入搜索诗名部分，返回包含该部分的所有诗名)
	public String[] SearchByAuthor(String sauthor){
		String back[]=new String[1000];
		boolean test=true;
		int i=0,index=0,number=0;
		while(test==true){
			test=ReadOnePoem(i);                                      //[数据库]读取数据库至类中变量
			index=author.indexOf(sauthor);	                              //在name中搜索sauthor
			if(index!=-1){
				back[number]=name;
				number++;
			}
			i++;
			System.out.println(i);
		}
		return back;
	}
		
	//【TXT相关操作】读取TXT文件内的诗文内容
	//[TXT]读取TXT中的诗文内容(读取ReadIn.txt文件中的诗句，读取至诗库、索引库)
	public void TXTRead(){
		String path=new String("");
		path=base.FileOReadNowPath()+"\\ReadIn.txt";
		FileIDo fileIDo=new FileIDo(path);                            //初始化文件内操作类(★)
		int wait=0;                                                   //即时空行数
		for(long i=0;i<10000000;i++){
			temp=fileIDo.ReadTXT(i);                                  //[FileIDo]读取一行TXT文档
			if(temp!=null&&temp!=" "){
				wait=0;
				int next=TXTFindName();                               //判断是否出现新的诗句
				if(next==1||next==2){                                 //搜索到开头符
					if(reading==true){                                //写入当前诗句并清空变量
						if(name.equals("")){                          //如果诗名为空则直接清空变量，不再读写
							ResetVariate();
						}
						else{
							System.out.print(from);
							WritePerPoem(true);
						}
					}
					else{
						reading=true;
					}
				}
				TXTReadFrom();                                        //读取诗句出处
				if(next==2){                                          //如果仅有诗名开头符
					i++;
					temp=temp+fileIDo.ReadTXT(i);
					TXTReadName();                                    //读取诗名
				}
				else{
					TXTReadName();                                    //读取诗名
				}
				TXTReadSentence();                                    //读取诗句
				TXTReadAuthor();                                      //读取作者
			}
			else{                                                     //如果该行为空
				wait++;
				if(wait>50){                                          //如果超过50个连续空行，则跳出
					break;
				}
			}
		}
	}
	
	//[TXT]从TXT读取内容中判断有无诗名存在(返回0为没有诗名，返回1为有诗名，返回2为只有开头符，返回3为只有结尾符)
	private int TXTFindName(){
		int back=0;                                                   //定义返回变量
		int left=temp.indexOf("【");
		int right=temp.indexOf("】");
		if(left!=-1&&right!=-1){                                      //有开头符、结尾符
			back=1;
		}
		else if(left!=-1&&right==-1){                                 //有开头符、没有结尾符
			back=2;
		}
		else if(left==-1&&right!=-1){                                 //没有开头符、有结尾符
			back=3;
		}
		return back;
	}
	
	//[TXT]从TXT读取内容中读出来源(全唐诗专用；读取成功返回true，失败返回false)
	private boolean TXTReadFrom(){
		boolean back=true;
		String book=new String(""),number=new String("");
		int i=temp.indexOf("卷");			                          //在temp里搜索"卷",>=0为存在,=-1为不存在
		int j=temp.indexOf("_");			                          //在temp里搜索"_",>=0为存在,=-1为不存在
		if(i!=-1&&j!=-1){
			book=temp.substring((i+1),j);                             //截取卷数编号
			number=temp.substring((j+1),(j+5));                       //截取卷内序号
			int temp=base.IsChar(number);                             //判断number是否为数字
			number=number.substring(0,temp);                          //截取number中是数字的部分
			from="QT"+book+"卷"+number;
		}
		else{
			back=false;
		}
		return back;
	}
	
	//[TXT]从TXT读取内容中读出诗名(读取成功返回true，失败返回false)
	private boolean TXTReadName(){
		boolean back=true;
		int left=temp.indexOf("【");			                      //在temp里搜索"【",>=0为存在,=-1为不存在
		int right=temp.indexOf("】");			                      //在temp里搜索"】",>=0为存在,=-1为不存在
		if(left!=-1&&right!=-1){                                      //如果"【""】"都搜到
			name=temp.substring((left+1),right);                      //截取第indexs行第indexe个字
			temp=temp.substring((right+1));                           //将诗名部分从temp内截去
			cname=true;
		}
		else{
			back=false;
		}
		return back;
	}
	
	//[TXT]从TXT读取内容中读出作者(读取成功返回true，失败返回false)
	private boolean TXTReadAuthor(){
		boolean back=false;
		if(cname==true&&cauthor==false&&ctext==false){                //已检索诗名，未检索作者、正文
			int length=temp.length();
			if(length>1&&length<5){                                   //该行剩余长度为2-4字                                    
				author=temp;
				cauthor=true;
				back=true;
			}
		}
		return back;
	}
	
	//[TXT]从TXT读取内容中读出诗句(读取成功返回true，失败返回false)
	private boolean TXTReadSentence(){
		boolean back=false;
		int ico=0,ipo=0,safe=0;
		while(ico!=-1||ipo!=-1){
			ico=temp.indexOf("，");                                   //在temp里搜索"，",>=0为存在,=-1为不存在
			ipo=temp.indexOf("。");                                   //在temp里搜索"。",>=0为存在,=-1为不存在
//			System.out.print("ico:"+ico+";ipo:"+ipo);
			if(line>=1000){                                           //诗句超过1000句时，拒绝读入
				break;
			}
			if(ipo==-1&&ico!=-1){
				sen[line]=temp.substring(0,ico);                      //截取诗句
				temp=temp.substring((ico+1));                         //截取剩余部分
				ctext=true;
				back=true;
				line++;
			}
			if(ico==-1&&ipo!=-1){
				sen[line]=temp.substring(0,ipo);                      //截取诗句
				temp=temp.substring((ipo+1));                         //截取剩余部分
				ctext=true;
				back=true;
				line++;
			}
			if(ipo!=-1&&ipo<ico){
				sen[line]=temp.substring(0,ipo);                      //截取诗句
				temp=temp.substring((ipo+1));                         //截取剩余部分
				ctext=true;
				back=true;
				line++;
			}
			if(ico!=-1&&ico<ipo){
				sen[line]=temp.substring(0,ico);                      //截取诗句
				temp=temp.substring((ico+1));                         //截取剩余部分
				ctext=true;
				back=true;
				line++;
			}
			if(ico==-1&&ipo==-1){
				break;
			}
			safe++;                                                   //[循环安全保护]
			if(safe>100){break;}
		}
		return back;
	}
	
	//【接口模块】与其他对象传参模块
	//[接口]读取当前操作诗文-诗文出处
	public String LinkReadFrom(){
		String back=from;
		return back;
	}
	
	//[接口]读取当前操作诗文-诗名
	public String LinkReadName(){
		String back=name;
		return back;
	}
	
	//[接口]读取当前操作诗文-作者名
	public String LinkReadAuthor(){
		String back=author;
		return back;
	}
	
	//[接口]读取当前操作诗文-诗句数
	public int LinkReadLine(){
		int back=line;
		return back;
	}
	
	//[接口]读取当前操作诗文-诗句内容
	public String[] LinkReadSentence(){
		String back[]=new String[1000];
		for(int i=0;i<line;i++){
			back[i]=sen[i];
		}
		return sen;
	}
	
	//[工具]重置对象内写入变量
	private void ResetVariate(){
		from="";
		name="";
		author="";
		line=0;
		for(int i=0;i<100;i++){
			sen[i]="";
			extent[i]=0;
		}
		cname=false;
		cauthor=false;
		ctext=false;
	}
}