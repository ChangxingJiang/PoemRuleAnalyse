package main;

class Rule{
	//构造器填写变量
	String path=new String("");                   //record文件地址
	String sen[]=new String[20];                  //诗句变量
	int extent[]=new int[20];                     //诗句长度
	int line=0;                                   //诗句行数
	long length=0,index=0;                        //文件长度；文件格律坐标(格律开始)
	
	//计算部分填写变量
	int rhyme[][]=new int[8][7];                  //诗句单字韵部编号
	int tone[][]=new int[8][7];                   //诗句单字平仄：0为未知，1为平音，2为仄音
	int toneall[]=new int[8];                     //诗句全局平仄
	
	//分析部分填写变量
	int stand[]=new int[8];                       //(standard)是否正格：0为非正格；11平平脚；12平仄脚；21仄平脚；22仄仄脚
	int change[]=new int[8];                      //是否变格：0为无变化；11平平脚变格；12平仄脚变格；21仄平脚变格；22仄仄脚变格
	boolean sticky[]=new boolean[8];              //对黏情况（失对失黏一律记录在失黏的后一句），正确为true，错误为false
	int endtone[]=new int[8];                     //每句末尾平仄(11为应平实平；12为应平实仄；21为应仄实平；22为应仄实仄)
	int same[]=new int[8];                        //二四六字平仄状态：0正确；1五言二四字平仄相同；2七言二四字平仄相同；3七言四六字平仄相同
	int wrong[]=new int[8];                       //是否拗救：0为无特殊拗救；21为孤平拗救
												  //121为平仄脚四字用仄且有救，122为平仄脚四字用仄且没救
												  //123为平仄脚第三字用仄且有救，124为平仄脚第四字用仄且没救
												  //221为仄仄脚三四字对调且有救，222为仄仄脚三四字对调且未救
	boolean alone[]=new boolean[8];               //是否孤平：不是孤平返回false；是孤平返回true
	
	//[初始化程序类]
	Base base=new Base();                         //初始化底层类
	FileODo fileODo=new FileODo();                //初始化文件外操作类(★)
	Rhyme Search=new Rhyme(1);                    //初始化韵部(平水韵)类
	
	Rule(){}//【构造器】
	
	//【读入】读取record文件(读取record文件中的诗句信息)
	public void ReadFromPath(String _path){
		path=_path;
		FileIDo fileIDo=new FileIDo(path);                            //初始化文件内操作类(★)
		String temp=new String("");
		temp=fileIDo.Read(10,3);                                   //从record文件读取诗句行数(数量)
		line=base.CharToFigure(temp);
		temp=fileIDo.Read(42,5);                                   //从record文件读取文件长度
		length=base.CharToFigure(temp);
		index=length;
		for(int i=0;i<line;i++){                                      //从record文件读取诗句内容
			temp=fileIDo.Read(100+20*i,20);
			temp=temp.replace(" ","");                                //删除读取内容中的空格部分
			sen[i]=temp;
		}
		InitializeVariable();
	}
	
	//【读入】读取字符串(读取record文件中的诗句信息)
	public void ReadFromString(String poem){
		poem=poem.replace(" ","");
		poem=poem.replace("\r\n","");
		int ico=0,ipo=0,safe=0;
		while(ico!=-1||ipo!=-1){
			ico=poem.indexOf("，");                                   //在temp里搜索"，",>=0为存在,=-1为不存在
			ipo=poem.indexOf("。");                                   //在temp里搜索"。",>=0为存在,=-1为不存在
			if(ipo==-1&&ico!=-1){
				sen[line]=poem.substring(0,ico);                      //截取诗句
				poem=poem.substring((ico+1));                         //截取剩余部分
				line++;
			}
			if(ico==-1&&ipo!=-1){
				sen[line]=poem.substring(0,ipo);                      //截取诗句
				poem=poem.substring((ipo+1));                         //截取剩余部分
				line++;
			}
			if(ipo!=-1&&ipo<ico){
				sen[line]=poem.substring(0,ipo);                      //截取诗句
				poem=poem.substring((ipo+1));                         //截取剩余部分
				line++;
			}
			if(ico!=-1&&ico<ipo){
				sen[line]=poem.substring(0,ico);                      //截取诗句
				poem=poem.substring((ico+1));                         //截取剩余部分
				line++;
			}
			if(ico==-1&&ipo==-1){
				break;
			}
			safe++;                                                   //[循环安全保护]
			if(safe>1000){break;}
		}
		for(int i=0;i<line;i++){                                      //分析诗句长度
			System.out.println(i+"="+sen[i]);
		}
		InitializeVariable();
		
	}
	
	//【工具】初始化变量
	public void InitializeVariable(){
		for(int i=0;i<line;i++){                                      //分析诗句长度
			extent[i]=sen[i].length();
		}
		for(int i=0;i<8;i++){
			toneall[i]=0;
			sticky[i]=true;
			endtone[i]=0;
			stand[i]=0;
			change[i]=0;
			same[i]=0;
		}
		for(int i=0;i<8;i++){
			for(int j=0;j<7;j++){
				rhyme[i][j]=0;
				tone[i][j]=0;
			}
		}
	}
	
	
	//【分析】诗句格律分析总引导端口(分析诗句韵部、平仄数据及格律状况)
	public String RuleEntrance(){
		boolean inspect=AnalyseDemand();          //分析诗句是否符合格律分析基础条件
		String poem[][]=new String[20][10];       //诗句单字
		String temp=new String("");
		temp="";
		if(inspect==true){                                            //诗句符合格律分析基础条件
			//[计算部分]
			for(int i=0;i<line;i++){                                  //分析诗句单字
				for(int j=0;j<extent[i];j++){
					poem[i][j]=String.valueOf(sen[i].charAt(j));      //截取第i+1行第j+1个字
				}
			}
			for(int i=0;i<line;i++){                                  //诗句韵部编号计算
				for(int j=0;j<extent[i];j++){
					rhyme[i][j]=Search.RhymeSearchCode(poem[i][j]);   //查询当前字的韵部编码
				}
			}
			for(int i=0;i<line;i++){                                  //诗句平仄计算		
				for(int j=0;j<extent[i];j++){
					tone[i][j]=Search.RhymeSearchTone(rhyme[i][j]);   //查询当前编码的平仄
					temp=temp+Integer.toString(tone[i][j],10);
				}
				toneall[i]=base.CharToFigure(temp);
				temp="";
			}
			//[分析部分]
			AnalyseStricky();                                         //分析全诗对黏
			for(int i=0;i<line;i++){
				AnalyseEndTone(i);                                    //分析单句末尾平仄
				stand[i]=AnalyseStandard(i);                          //分析格律诗单句正格
				change[i]=AnalyseChange(i);                           //分析格律诗单句变格
				same[i]=AnalyseSame(i);                               //分析格律诗二四六字平仄状态
				wrong[i]=AnalyseWrong(i);                             //分析格律诗拗救
				alone[i]=AnalyseAlone(i);                             //分析孤平(王力观点)
			}
			//[评价部分]
			temp=SayWholeRule();
			for(int i=0;i<line;i++){               //分析各句格律
				temp=temp+Saysentence(i);
				if(i<(line-1)){temp=temp+"；";}
				else{temp=temp+"。";}
			}
			temp=temp+SayEndTone();   //分析全诗句尾平仄
			temp=temp+SayStricky();    //分析全诗对黏情况
			System.out.println(temp);
		}
		return temp;
	}
	
	//【分析】分析诗句是否符合格律分析基础条件(若诗句符合格律分析基础条件（全中文、近体诗字数），返回真；反之为假)
	private boolean AnalyseDemand(){
		boolean inspect=true;
		for(int i=0;i<line;i++){                                      //分析诗句是否符合全中文格式
			sen[i]=sen[i].replace(" ","");
			if(sen[i].getBytes().length==2*sen[i].length()){}         //检查sen[i]是否为全中文
			else{
				System.out.println("第"+i+"句有非中文内容");
				inspect=false;
			}
		}
		if(line!=4&&line!=8){                                         //分析诗句是否为四句或八句
			inspect=false;
		}
		if(extent[0]!=5&&extent[0]!=7){                               //分析诗句第一句是否是五言或七言
			inspect=false;
		}
		for(int i=0;i<line-1;i++){                                    //分析每句诗句字数是否相同
			if(extent[i]!=extent[i+1]){
				inspect=false;
			}
		}
		return inspect;
	}
	
	//【分析】分析全诗对黏情况(分析全诗对黏情况，失对失黏一律记录在失对失黏的后一句；[计划]合并五言、七言，提高运算速度)
	public void AnalyseStricky(){
		int time=line/2;                                              //对黏各分析的次数
		if(extent[0]==5){                                             //五言模式：分析对
			for(int i=0;i<time;i++){  
				if(tone[2*i][1]!=tone[2*i+1][1]&tone[2*i][3]!=tone[2*i+1][3]){
					sticky[2*i+1]=true;                                 //第2*i句和2*i+1句对
				}
				else{
					sticky[2*i+1]=false;
				}
			}
		}
		if(extent[0]==7){   //七言模式：分析对
			for(int i=0;i<time;i++){  
				if(tone[2*i][1]!=tone[2*i+1][1]&tone[2*i][3]!=tone[2*i+1][3]&tone[2*i][5]!=tone[2*i+1][5]){
					sticky[2*i+1]=true;                                 //第2*i句和2*i+1句对
				}
				else{
					sticky[2*i+1]=false;
				}
			}
		}
		if(extent[0]==5){   //五言模式：分析黏
			for(int i=0;i<time-1;i++){  
				if(tone[2*i+1][1]==tone[2*i+2][1]&tone[2*i+1][3]==tone[2*i+2][3]){
					sticky[2*i+2]=true;                               //第2*i句和2*i+1句黏
				}
				else{
					sticky[2*i+2]=false;
				}
			}
		}
		if(extent[0]==7){   //七言模式：分析黏
			for(int i=0;i<time-1;i++){  
				if(tone[2*i+1][1]==tone[2*i+2][1]&tone[2*i+1][3]==tone[2*i+2][3]&tone[2*i+1][5]==tone[2*i+2][5]){
					sticky[2*i+2]=true;                               //第2*i句和2*i+1句黏
				}
				else{
					sticky[2*i+2]=false;
				}
			}
		}	
	}
	
	//【分析】分析单句末尾平仄(分析第l句结尾平仄是否正确：出句应为仄音，对句应为平音（未考虑首句入律情况）)
	private void AnalyseEndTone(int l){
		if(l%2==0){                                                   //l为偶数，出句应仄
			if(tone[l][extent[0]-1]==2){
				endtone[l]=22;                                        //应仄为仄
			}
			else{
				if(l==0){                                             //如果是首句
					endtone[l]=11;
				}
				else{
					endtone[l]=21;                                    //如果非首句：应仄为平
				}
			}
		}
		if(l%2==1){                                                   //l为奇数，对句应平
			if(tone[l][extent[0]-1]==1){
				endtone[l]=11;                                        //应仄为仄
			}
			else{
				endtone[l]=12;                                        //应仄为平
			}
		}
	}	
	
	//【分析】分析格律诗单句正格(分析第l句是否为正格，是正格返回正格编号，不是正格返回-1)
	private int AnalyseStandard(int l){
		int stand=-1;
		if(extent[0]==5){                                             //五言情况
			if(tone[l][1]==2&tone[l][2]==1&tone[l][3]==1&tone[l][4]==2){stand=12;}//(仄)仄平平仄
			if(tone[l][1]==1&tone[l][2]==1&tone[l][3]==2&tone[l][4]==2){stand=22;}//(平)平平仄仄
			if(tone[l][1]==2&tone[l][2]==2&tone[l][3]==1&tone[l][4]==1){stand=11;}//(仄)仄仄平平
			if(tone[l][0]==1&tone[l][1]==1&tone[l][2]==2&tone[l][3]==2&tone[l][4]==1){stand=21;}//平平仄仄平
		}
		if(extent[0]==7){                                             //七言情况
			if(tone[l][1]==1&tone[l][3]==2&tone[l][4]==1&tone[l][5]==1&tone[l][6]==2){stand=12;}//(平)平(仄)仄平平仄
			if(tone[l][1]==2&tone[l][3]==1&tone[l][4]==1&tone[l][5]==2&tone[l][6]==2){stand=22;}//(仄)仄(平)平平仄仄
			if(tone[l][1]==1&tone[l][3]==2&tone[l][4]==2&tone[l][5]==1&tone[l][6]==1){stand=11;}//(平)平(仄)仄仄平平
			if(tone[l][1]==2&tone[l][2]==1&tone[l][3]==1&tone[l][4]==2&tone[l][5]==2&tone[l][6]==1){stand=21;}//(仄)仄平平仄仄平
		}
		return stand;
	}
	
	//【分析】分析格律诗单句变格(分析第l句是否为普通变格，是变格返回正格编号，不是变格返回-1)
	private int AnalyseChange(int l){
		int change=-1;
		if(extent[0]==5){                                             //五言情况
			if(tone[l][1]==2&tone[l][2]==2&tone[l][3]==1&tone[l][4]==2){change=12;}//(仄)仄仄平仄
			if(tone[l][0]==1&tone[l][1]==1&tone[l][2]==2&tone[l][3]==2&tone[l][4]==2){change=22;}//平平仄仄仄
			if(tone[l][1]==2&tone[l][2]==1&tone[l][3]==1&tone[l][4]==1){change=11;}//(仄)仄平平平
			if(tone[l][0]==1&tone[l][1]==1&tone[l][2]==1&tone[l][3]==2&tone[l][4]==1){change=21;}//平平平仄平
		}
		if(extent[0]==7){                                             //七言情况
			if(tone[l][1]==1&tone[l][3]==2&tone[l][4]==2&tone[l][5]==1&tone[l][6]==2){change=12;}//(平)平(仄)仄仄平仄
			if(tone[l][1]==2&tone[l][2]==1&tone[l][3]==1&tone[l][4]==2&tone[l][5]==2&tone[l][6]==2){change=22;}//(仄)仄平平仄仄仄
			if(tone[l][1]==1&tone[l][3]==2&tone[l][4]==1&tone[l][5]==1&tone[l][6]==1){change=11;}//(平)平(仄)仄平平平
			if(tone[l][1]==2&tone[l][2]==1&tone[l][3]==1&tone[l][4]==1&tone[l][5]==2&tone[l][6]==1){change=21;}//(仄)仄平平平仄平
		}
		return change;
	}
	
	//【分析】分析格律诗单句二四六字平仄状态(分析第l句二四六字平仄状态（相邻平仄是否相同），若没有问题则返回0)
	private int AnalyseSame(int l){
		int same=0;
		if(extent[0]==5){                                             //五言情况
			if(tone[l][1]==tone[l][3]){same=1;}                       //五言第二字第四字平仄相同			
		}
		if(extent[0]==7){                                             //七言情况
			if(tone[l][1]==tone[l][3]){same=2;}                       //七言第二字第四字平仄相同
			if(tone[l][3]==tone[l][5]){same=3;}                       //七言第四字第六字平仄相同
		}
		return same;
	}
	
	//【分析】分析格律诗单句拗救(分析第l句拗救情况，若没有拗救则返回0)
	private int AnalyseWrong(int l){
		int wrong=0;
		if(extent[0]==5){                                             //五言情况
			if(tone[l][0]==1&tone[l][1]==1&tone[l][2]==2&tone[l][3]==1&tone[l][4]==2){//仄仄脚三四字对调
				if(tone[l][1]==2&tone[l][2]==2&tone[l][3]==1&tone[l][4]==1){//对句有救
					wrong=221;
				}
				else{wrong=222;}                                      //对句未救
			}
			if(tone[l][1]==2&tone[l][3]==2&tone[l][4]==2){            //平仄脚四字用仄
				if(tone[l][2]==1){                                    //对句有救
					wrong=121;
				}
				else{wrong=122;}                                      //对句未救
			}
			if(tone[l][1]==2&tone[l][2]==2&tone[l][3]==1&tone[l][4]==2){//平仄脚第三字用仄
				if(tone[l][2]==1){                                    //对句有救
					wrong=123;
				}
				else{wrong=124;}                                      //对句未救
			}
			if(tone[l][0]==2&tone[l][1]==1&tone[l][2]==1&tone[l][3]==2&tone[l][4]==1){//孤平拗救
				wrong=21;
			}
		}
		if(extent[0]==7){                                             //七言情况
			if(tone[l][1]==2&tone[l][2]==1&tone[l][3]==1&tone[l][4]==2&tone[l][5]==1&tone[l][6]==2){//仄仄脚三四字对调
				if(tone[l][1]==1&tone[l][3]==2&tone[l][4]==2&tone[l][5]==1&tone[l][6]==1){//对句有救
					wrong=221;
				}
				else{wrong=222;}                                      //对句未救
			}
			if(tone[l][1]==1&tone[l][3]==2&tone[l][5]==2&tone[l][6]==2){//平仄脚四字用仄
				if(tone[l][4]==1){                                    //对句有救
					wrong=121;
				}
				else{wrong=122;}                                      //对句未救
			}
			if(tone[l][1]==1&tone[l][3]==2&tone[l][4]==2&tone[l][5]==1&tone[l][6]==2){//平仄脚第三字用仄
				if(tone[l][4]==1){                                    //对句有救
					wrong=123;
				}
				else{wrong=124;}                                      //对句未救
			}
			if(tone[l][1]==2&tone[l][2]==2&tone[l][3]==1&tone[l][4]==1&tone[l][5]==2&tone[l][6]==1){//孤平拗救
				wrong=21;
			}
		}
		return wrong;
	}
	
	//【分析】分析孤平(王力观点)
	//王力观点：在五言句中，除韵外只有一个平声字，且不与韵相邻；在七言句中，除了韵外有一个或两个平声字，且两个平声字不相邻。
	//说明：使用王力观点，分析是否为孤平，若不是孤平返回false，是孤平返回true
	private boolean AnalyseAlone(int l){
		boolean alone=false;
		if(extent[0]==5){                                             //五言情况
			int j=0;
			for(int i=0;i<4;i++){                                     //统计五言诗中除韵脚外有多少个平音字
				if(tone[l][i]==1){
					j++;
				}
			}
			if(j==1&tone[l][3]==2){                                   //分析平音字位置
				alone=true;
			}
		}
		if(extent[0]==7){                                             //七言情况
			int j=0,index=0;
			for(int i=0;i<6;i++){                                     //统计七言诗中除韵脚外有多少个平音字
				if(tone[l][i]==1){
					j++;
					index=i;
				}
			}
			if(j==1|j==2){                                            //有一个或两个平音字
				if(tone[l][5]==2&tone[l][index-1]==2){                //判断两字是否相邻
					alone=true;
				}
			}
		}
		return alone;
	}
	
	//【评价】作出对格律的总体评价(对格律诗整体进行权重评价，返回评价)
	private String SayWholeRule(){
		String temp=new String("");
		int mark=0;   //最终评分
		int marksta=0,markc=0,marka=0;  //正格个数，变格个数，孤平个数
		int markw=0,markww=0;    //拗句救回个数，拗救没救个数
		int marksti=0,marke=0,marksa=0;  //失对、失黏个数，句尾平仄错误个数，二四六字同平仄个数
		for(int i=0;i<line;i++){
			if(stand[i]!=0){marksta++;}
			if(change[i]!=0){markc++;}
			if(wrong[i]==21||wrong[i]==121||wrong[i]==123||wrong[i]==221){markw++;}
			if(wrong[i]==122||wrong[i]==124||wrong[i]==222){markww++;}
			if(sticky[i]==false){marksti++;}
			if(endtone[i]==12||endtone[i]==21){marke++;}
			if(same[i]!=0&&wrong[i]==0){marksa++;}
			if(alone[i]==true){marka++;}
		}
		mark=20*marksta+15*markc+10*markw-50*(markww+marka+marksa)-30*marksti-100*marke;
		if(mark>=line*10){temp="这首诗格律非常工整。";}
		if(mark>=0&&mark<line*10){temp="这首诗格律较为工整。";}
		if(mark>=line*(-10)&&mark<0){temp="这首诗格律存在严重问题。";}
		if(mark<line*(-10)){temp="这首诗是一首古体诗或拗句诗。";}		
		return temp;
	}
	
	//【评价】作出对格律诗句尾平仄的评价(评价格律诗全诗的句尾平仄，返回评价)
	private String SayEndTone(){
		String temp=new String("");
		for(int i=0;i<line;i++){
			if(i!=0){
				if(endtone[i]==12){temp="第"+Integer.toString((i+1),10)+"句应平而仄，";}
				if(endtone[i]==21){temp="第"+Integer.toString((i+1),10)+"句应仄而平，";}
			}
		}
		return temp;	
	}	
	
	//【评价】作出对格律诗中单一一句的评价(评价格律诗中的一句，给出评价的句子叙述，返回评价)
	private String Saysentence(int i){
		String temp=new String("");
		boolean inspect=false;
		if(inspect==false){       //正格判断
			if(stand[i]==11){temp="第"+Integer.toString((i+1),10)+"句使用平平脚正格，";inspect=true;}
			if(stand[i]==12){temp="第"+Integer.toString((i+1),10)+"句使用平仄脚正格，";inspect=true;}
			if(stand[i]==21){temp="第"+Integer.toString((i+1),10)+"句使用仄平脚正格，";inspect=true;}
			if(stand[i]==22){temp="第"+Integer.toString((i+1),10)+"句使用仄仄脚正格，";inspect=true;}
		}
		if(inspect==false){      //变格判断
			if(change[i]==11){temp="第"+Integer.toString((i+1),10)+"句使用平平脚变格，";inspect=true;}
			if(change[i]==12){temp="第"+Integer.toString((i+1),10)+"句使用平仄脚变格，";inspect=true;}
			if(change[i]==21){temp="第"+Integer.toString((i+1),10)+"句使用仄平脚变格，";inspect=true;}
			if(change[i]==22){temp="第"+Integer.toString((i+1),10)+"句使用仄仄脚变格，";inspect=true;}
		}
		if(inspect==false){            //拗救判断
			if(wrong[i]==21){temp="第"+Integer.toString((i+1),10)+"句孤平拗救，";inspect=true;}
			if(wrong[i]==121){temp="第"+Integer.toString((i+1),10)+"句平仄脚第四字用仄且在对句救回，";inspect=true;}
			if(wrong[i]==122){temp="第"+Integer.toString((i+1),10)+"句平仄脚第四字用仄且在对句没有救回，";}
			if(wrong[i]==123){temp="第"+Integer.toString((i+1),10)+"句平仄脚第三字用仄且在对句救回，";inspect=true;}
			if(wrong[i]==124){temp="第"+Integer.toString((i+1),10)+"句平仄脚第三字用仄且在对句没有救回，";}
			if(wrong[i]==221){temp="第"+Integer.toString((i+1),10)+"句仄仄脚三四字对调且在对句救回，";inspect=true;}
			if(wrong[i]==222){temp="第"+Integer.toString((i+1),10)+"句仄仄脚三四字对调且在对句没有救回，";}
		}
		if(inspect==false){
			if(same[i]==1){temp=temp+"第"+Integer.toString((i+1),10)+"句二、四字平仄相同，";}
			if(same[i]==2){temp=temp+"第"+Integer.toString((i+1),10)+"句二、四字平仄相同，";}
			if(same[i]==3){temp=temp+"第"+Integer.toString((i+1),10)+"句四、六字平仄相同，";}
			if(alone[i]==true){temp=temp+"第"+Integer.toString((i+1),10)+"句犯孤平，";}
		}
		if(inspect==true){
			temp=temp+"正确";
		}
		else{
			temp=temp+"错误";
		}
		return temp;
	}
	
	//【评价】作出对格律诗对黏的评价(评价格律诗对黏，评价语句2句话;[计划]将两句话合并为一句话)
	private String SayStricky(){
		String sayd=new String(""),sayn=new String(""),temp=new String("");
		sayd="";
		sayn="";
		boolean d=true,n=true;  //定义对黏是否存在问题变量：d为对，n为黏，如对黏正确则为true，错误为false
		for(int i=0;i<line;i++){
			if(sticky[i]==false&&i!=0){  //判断是否为第一句
				if(i%2==0){   //为偶数，是出句
					if(n==true){   //如果此前没有出现失黏现象
						n=false;
						sayn=sayn+"第"+Integer.toString((i+1),10);
					}
					else{     //如果此前出现了失黏现象
						sayn=sayn+"、"+Integer.toString((i+1),10);
					}
				}
				else{   //为奇数，是对句
					if(d==true){               //如果此前没有出现过失对现象
						d=false;
						sayd=sayd+"第"+Integer.toString((i+1),10);
					}
					else{      //如果此前出现了失对现象
						sayd=sayd+"、"+Integer.toString((i+1),10);
					}
				}
			}
		}
		if(d==true){  //全诗没有失对现象
			sayd="全诗没有失对现象";
		}
		else{
			sayd=sayd+"句出现失对现象";
		}
		if(n==true){  //全诗没有失黏现象
			sayn="全诗没有失黏现象";
		}
		else{
			sayn=sayn+"句出现失黏现象";
		}
		temp=sayd+"，"+sayn;
		return temp;
	}
}

//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓//

/** 介绍 
 * @一句话：我是一个以软件工程师为理想的、曾经文艺的文科生
 */

/** 感想
 * @1：沈浩老师的网络爬虫很有意思，一般的爬虫以java和MySQL为内核，所以去学MySQL
 * @2：学校CUC的网络没有路由器连接功能，这个功能一般用汇编实现，所以没法学
 * @3：数新、媒调一共47人，往届重修高数的就有17个，通过率真的很低
 * @4：我想做毕业设计，不想做毕业论文
 */

/** 愿望
 * @1：建起程序猿协会社团
 * @2：完成“机器诗人”项目
 * @3：毕业时拥有成为一个软件工程师的技术
 */

//PS：这个界面是Eclipse，是一种为java提供IDE功能的编程软件
//↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑//