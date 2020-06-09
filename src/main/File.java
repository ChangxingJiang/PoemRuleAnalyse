package main;

import java.io.*;                       //字符流支持
import java.io.File;                    //文件读写支持

class FileIDo{
	//[定义变量]
	String path=new String("");                   //【变量】工作路径
	boolean inspect=false;                        //【变量】工作路径是否存在检测
	
	//[初始化程序类]
	Base base=new Base();                         //初始化底层类(☆)
	
	FileIDo(){}//【构造器】(无参数构造器)
	
	//【构造器】(输入操作工作路径)
	FileIDo(String _path){
		path=_path;                                                   //保存工作路径
		File file=new File(path);
		inspect=file.exists();                                        //判断文件是否存在(true为存在，false为不存在)
	}
	
	//【构造器】修改当前工作路径(输入新的路径)
	public void ChangeTo(String _path){
		path=_path;                                                   //保存工作路径
		File file=new File(path);
		inspect=file.exists();                                        //判断文件是否存在(true为存在，false为不存在)
	}
	
	//【文件】文件填"空"初始化(输入需要初始化到的文件长度)
	public void Initialize(long length){
		File file=new File(path);
		long now=file.length();
		for(long i=now;i<length;){
			if((length-now)>=128){                                    //当剩余128个字节以上需要填写时
				Write(i,"                                                                                                                                ");
				i=i+128;
			}else if((length-now)>=64){                               //当剩余64个字节以上需要填写时
				Write(i,"                                                                ");
				i=i+64;
			}else if((length-now)>=32){                               //当剩余32个字节以上需要填写时
				Write(i,"                                ");
				i=i+16;
			}else if((length-now)>=16){                               //当剩余16个字节以上需要填写时
				Write(i,"                ");
				i=i+16;
			}else if((length-now)>=8){                                //当剩余8个字节以上需要填写时
				Write(i,"        ");
				i=i+8;
			}else if((length-now)>=4){                                //当剩余4个字节以上需要填写时
				Write(i,"    ");
				i=i+4;
			}else if((length-now)>=2){                                //当剩余2个字节以上需要填写时
				Write(i,"  ");
				i=i+2;
			}else{                                                    //当剩余1个字节需要填写时
				Write(i," ");
				i++;
			}
		}
	}
	
	//【文件】读取文件（输入文件开始读取位置、读取长度，返回读取的内容）
	public String Read(long index,int extent){
		String back=new String("");
		RandomAccessFile file=null;
		File filelong=new File(path);
		long length=filelong.length();                                //读取文件长度(字节数)
		if((index+extent)>length){                                    //读取坐标超过文件长度
			if(length>index){                                         //若读取坐标位于文件内，读取到文件结尾
				extent=(int)(length-index);
			}
			else{
				return null;
			}
		}
		if((index+extent)>length||index<0||extent<=0){                //判断是否符合读取标准
			if(index<0){                                              //读取坐标为负数
				System.out.print("读取坐标为负数，");
			}
			if(extent<=0){                                            //读取长度为负数
				System.out.print("读取长度为负数，");
			}
			System.out.println("读取失败");
		}
		else{
			try{
				file=new RandomAccessFile(path, "r");                 //打开一个随机访问文件流，按只读方式("rw"为读写)
				file.seek(index);                                     //将读文件的开始位置移到index位置
				byte[] bytes=new byte[extent];
				file.read(bytes);                                     //将一次读取的字节数赋给byteread
				back=new String(bytes);
			}
			catch(IOException e){
				System.out.println("读取读取出现其他异常");
			}
			finally{
				if(file!=null){
					try{file.close();}
					catch(IOException e1){
						System.out.println("读取读取关闭异常");
					}
				}
			}
		}
		return back;
    }
	
	//【文件】文件写入(输入文件开始写入坐标、写入内容)
	public void Write(long index,String content){
		RandomAccessFile file=null;
		byte bytes[]=content.getBytes();                              //将content转化为字节串
		try {
            file=new RandomAccessFile(path,"rw");                     //打开一个随机访问文件流，按只读方式("rw"为读写)
			//long fileLength=file.length();                            //文件长度，字节数
			file.seek(index);                                         //将读文件的开始位置移到index位置。
			file.write(bytes);                                        //文件写入
		}
		catch(IOException e){
            base.ErrorStop("文件写入异常:FileWIndex");
        }
		finally{
            if(file!=null){
                try{
                    file.close();                                     //关闭文件
                }
				catch(IOException e1){
                }
            }
        }
    }
	
	//【TXT】读取一行TXT文档(输入读取行数，返回读取内容)(单行最长不超过1024字节)
	//[暂存变量]
	long sline=0;
	long sindex=0;
	//[程序]
	public String ReadTXT(long line){
		String temp=null;                                             //临时读取变量
		long index=0;                                                 //坐标指针
		int search=0;                                                 //查找坐标
		long safe=0;                                                  //循环保护
		if(sline==line&&sindex!=0){                                   //【回忆】存储记录为当前行
			temp=Read(sindex,1024);                                   //
			if(temp==null){
				return null;
			}
			search=temp.indexOf("\r\n");			                  //在temp中搜索当前"\r\n"，>=0为存在,=-1为不存在
			if(search==-1){                                           //若未搜索到\r\n
				sline=sline+1;                                        //【记忆】存储下一行行数
				sindex=sindex+temp.getBytes().length;
			}
			if(search==0){                                            //若搜索到"\r\n"位于首字节
				temp=null;
				sindex=sindex+2;
			}
			if(search>0){
				temp=temp.substring(0,search);
				sindex=sindex+temp.getBytes().length+2;               //【记忆】存储下一行起始处文件坐标
			}
			sline=line+1;                                             //【记忆】存储下一行行数
		}
		else{                                                         //【记忆】没有存储记录或存储记录在读取行数后
			if(sline==0||sline>line){                           
				sline=0;
				sindex=0;
			}
			long i=sline;                                             //临时行数记录
			index=sindex;
			while(safe<1000000){
				temp=Read(index,1024);
				if(temp==null){
					return null;
				}
				search=temp.indexOf("\r\n");			              //在temp中搜索当前"\r\n"，>=0为存在,=-1为不存在
				if(search==-1){                                       //若未搜索到\r\n
					sline=i+1;                                        //【记忆】存储下一行行数
					sindex=sindex+temp.getBytes().length;
				}
				if(search>=0){                                        //搜索到\r\n
					temp=temp.substring(0,search);
					index=index+temp.getBytes().length+2;
				}
				if(i==line){
					sline=i+1;                                        //【记忆】存储下一行行数
					sindex=index;                                     //【记忆】存储下一行起始处文件坐标
					break;
				}
				i++;
				safe++;                                               //[循环安全保护]
			}
		}
//		System.out.println("line:"+line+"——内容为："+temp);           //[测试显示]读取TXT单行结果
		return temp;
	}
}



/*////////////////////////////////文件外操作///////////////////////////////////
@@作者：长行
@@建立时间：2016.10.28
@@调用级别：★
/////////////////////////////////////////////////////////////////////////////*/
class FileODo{
	//[初始化程序类]
	Base base=new Base();                         //初始化底层类(☆)
	
	FileODo(){}//【构造器】
	
	//【文件】创建文件夹(输入文件夹地址路径)
	public void FoundPath(String path){
		File file=new File(path);
		if(!file.exists()){                                           //判断文件夹是否存在
			file.mkdir();                                             //创建文件夹
	    }
	}
	
	//【文件】创建文件(输入创建文件的地址、是否重建(true为替换原文件，false为保留原文件))
	public void FoundFile(String path,boolean rebuild){
		boolean temp=true,execute=true;                               //声明临时变量、执行变量
		File file=new File(path);
		temp=file.exists();                                           //判断文件是否存在：false为不存在
		if(temp==true){
			if(rebuild==false){                                       //文件存在且保留原文件
				execute=false;
			}
			else{
				DeleteFile(path);                                     //删除原文件
			}
		}
		temp=file.getParentFile().exists();                           //判断文件所在目录是否存在：true为存在
		if(temp==false){                                              //若父目录不存在
			temp=file.getParentFile().mkdirs();                       //创建父目录
			if(temp==false){                                          //若父目录创建失败
				execute=false;
			}
		}
		if(execute==true){
			try{  
				file.createNewFile();                                 //创建文件
			}
			catch(IOException e){
				System.out.println("文件创建失败");  
			}
		}
	}
	
	//【文件】判断文件是否存在(输入文件路径，返回是否存在且为文件(存在返回true，不存在返回false))
	public boolean Exist(String path){
		File file=new File(path);
		boolean Test=false;
		boolean existFile=file.exists();                              //判断文件是否存在
		boolean isFile=file.isFile();                                 //判断是否为文件
		if(existFile==true&&isFile==true){
			Test=true;
		}
		return Test;
	}
	
	//【文件】删除文件(输入删除文件路径，返回是否删除成功)
	public boolean DeleteFile(String path){  
		File file=new File(path);  
		boolean temp=file.exists();                                   //判断目录或文件是否存在：false为正常 
		if(temp==true){                                                
			temp=file.isFile();                                       //判断是否为文件
			if(temp==true){
				file.delete();                                        //删除文件
			}
		}
		return temp;
	}
	
	//【文件】寻找文件最小空编号(输入文件路径、文件名前半部分、后缀，返回最小编号)
	public int LeastNumber(String path,String name,String suffix){
		int empty=0;
		FoundPath(path);                                              //确认存在该文件夹
		String frontName=path+name;
		String tempName=new String("");
		boolean tempTest=true;
		for(int i=1;i<999999;i++){
			tempName=frontName+Integer.toString(i,10)+suffix;
			tempTest=Exist(tempName);                                 //判断文件是否存在
			if(tempTest==false){
				empty=i;
				break;
			}
		}
		if(tempTest==true){
			System.out.println("在1000000以下未发现有效空编号");
		}
		return empty;
	}
}