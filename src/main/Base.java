package main;

import java.io.*;                       //字符流支持
import java.io.BufferedReader;          //CMD页面输入支持
import java.io.File;                    //文件读写支持
import java.io.InputStreamReader;       //TXT文件读写支持
import java.text.*;                     //当前时间读取支持(SimpleDateFormat)
import java.util.Date;

class Base{
	BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));//CMD读写工具
	
	Base(){}//Base类构造器
	
	//【文件】读取当前地址(返回当前主类所在地址)
	public String FileOReadNowPath(){
		String path=new String("");
		File file=new File("."); 
        try{
			path=file.getCanonicalPath();                             //得到当前地址
		}
		catch(IOException e){
			System.out.println("读取当前地址异常：FileOReadNowPath");
		}
 		return path;
	}
	
	//【系统】获取当前系统时间
	public String GetTime(){
		Date date=new Date();
		SimpleDateFormat nowtime = new SimpleDateFormat("yyyyMMddHHmmss");
		String time=nowtime.format(date);
		return time;
	}
		
	//【工具】字符串转化为数字(将输入的字符串转化为int格式数字，若不能识别则返回的int数值为-1(自动屏蔽掉输入的空格))
	public int CharToFigure(String temp){
		int figure=-1;
		temp=temp.replace(" ","");
		try {                                                         //将字符串转换为数字
			figure=Integer.valueOf(temp).intValue();                  //将读入的数字转化为int格式
		}
		catch (NumberFormatException e) {
		}
		return figure;
	}
	
	//【工具】判断是否为数字(如果都是数字返回-1，如果不是返回第一个不是数字的index)
	public int IsChar(String judge){
		int index=-1;
		for(int i=0;i<judge.length();i++){  
			int chr=judge.charAt(i);  
			if(chr<48||chr>57){
				index=i;
				break;
			}
		}  
		return index;  
	}
	
	//【工具】读出地址中的文件名(不包括后缀)(输入文件地址，返回无地址、无后缀的文件名)
	public String ReadFileName(String path){
		int road=0,safe=0;
		String name=new String("");
		while(road!=-1){                                              //截取文件地址
			road=path.indexOf("\\");
			path=path.substring(road);
			safe++;                                                   //[循环安全记录]
			if(safe>100){break;}                                      //循环次数超过100则强行跳出
		}
		int suffit=path.indexOf(".");
		name=path.substring(0,suffit);
		return name;
	}
		
	//【底层】收集CMD用户录入信息
	public String Read(){
		String temp=new String("");
		try{                                                                          //读取输入的诗句
			temp=reader.readLine();
		}
		catch(Exception e){
			System.out.println("Base-ReadCMD:读出输入内容出现异常");
		}
		return temp;
	}
		
	//【底层】CMD中断停止
	public void ErrorStop(String exception){
		System.out.println(exception+"——回车键继续");
		Read();
	}
	
	//【底层】根据字节数裁减字符串(输入目标字符串、要截取的字符数；返回截取位置)
	public int CutStringByte(String aim,int number){
		String temp=new String("");
		int back=0;
		if(number<1){
			System.out.println("Base-CutStringByte:截取字符串数不合标准");
		}
		else{
			for(int i=1;i<1000;i++){
				temp=aim.substring(0,i);
				if(temp.getBytes().length>number){
					back=i-1;
					break;
				}
			}
		}
		return back;
	}
	
	//【底层】字符串数组实际有效长度(输入字符串组，返回字符串中有内容的字符串数量)
	public int SArrayActualL(String string[]){
		int back=0;
		for(int i=0;i<string.length;i++){                             //读取item中有效长度
			if(string[i]==null){                                      //当下一条字符串为空时跳出循环(结束写入)
				break;
			}
			back=i;
		}
		back=back+1;
		return back;
	}
}