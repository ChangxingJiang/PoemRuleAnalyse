package main;

import java.io.*;                       //�ַ���֧��
import java.io.BufferedReader;          //CMDҳ������֧��
import java.io.File;                    //�ļ���д֧��
import java.io.InputStreamReader;       //TXT�ļ���д֧��
import java.text.*;                     //��ǰʱ���ȡ֧��(SimpleDateFormat)
import java.util.Date;

class Base{
	BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));//CMD��д����
	
	Base(){}//Base�๹����
	
	//���ļ�����ȡ��ǰ��ַ(���ص�ǰ�������ڵ�ַ)
	public String FileOReadNowPath(){
		String path=new String("");
		File file=new File("."); 
        try{
			path=file.getCanonicalPath();                             //�õ���ǰ��ַ
		}
		catch(IOException e){
			System.out.println("��ȡ��ǰ��ַ�쳣��FileOReadNowPath");
		}
 		return path;
	}
	
	//��ϵͳ����ȡ��ǰϵͳʱ��
	public String GetTime(){
		Date date=new Date();
		SimpleDateFormat nowtime = new SimpleDateFormat("yyyyMMddHHmmss");
		String time=nowtime.format(date);
		return time;
	}
		
	//�����ߡ��ַ���ת��Ϊ����(��������ַ���ת��Ϊint��ʽ���֣�������ʶ���򷵻ص�int��ֵΪ-1(�Զ����ε�����Ŀո�))
	public int CharToFigure(String temp){
		int figure=-1;
		temp=temp.replace(" ","");
		try {                                                         //���ַ���ת��Ϊ����
			figure=Integer.valueOf(temp).intValue();                  //�����������ת��Ϊint��ʽ
		}
		catch (NumberFormatException e) {
		}
		return figure;
	}
	
	//�����ߡ��ж��Ƿ�Ϊ����(����������ַ���-1��������Ƿ��ص�һ���������ֵ�index)
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
	
	//�����ߡ�������ַ�е��ļ���(��������׺)(�����ļ���ַ�������޵�ַ���޺�׺���ļ���)
	public String ReadFileName(String path){
		int road=0,safe=0;
		String name=new String("");
		while(road!=-1){                                              //��ȡ�ļ���ַ
			road=path.indexOf("\\");
			path=path.substring(road);
			safe++;                                                   //[ѭ����ȫ��¼]
			if(safe>100){break;}                                      //ѭ����������100��ǿ������
		}
		int suffit=path.indexOf(".");
		name=path.substring(0,suffit);
		return name;
	}
		
	//���ײ㡿�ռ�CMD�û�¼����Ϣ
	public String Read(){
		String temp=new String("");
		try{                                                                          //��ȡ�����ʫ��
			temp=reader.readLine();
		}
		catch(Exception e){
			System.out.println("Base-ReadCMD:�����������ݳ����쳣");
		}
		return temp;
	}
		
	//���ײ㡿CMD�ж�ֹͣ
	public void ErrorStop(String exception){
		System.out.println(exception+"�����س�������");
		Read();
	}
	
	//���ײ㡿�����ֽ����ü��ַ���(����Ŀ���ַ�����Ҫ��ȡ���ַ��������ؽ�ȡλ��)
	public int CutStringByte(String aim,int number){
		String temp=new String("");
		int back=0;
		if(number<1){
			System.out.println("Base-CutStringByte:��ȡ�ַ��������ϱ�׼");
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
	
	//���ײ㡿�ַ�������ʵ����Ч����(�����ַ����飬�����ַ����������ݵ��ַ�������)
	public int SArrayActualL(String string[]){
		int back=0;
		for(int i=0;i<string.length;i++){                             //��ȡitem����Ч����
			if(string[i]==null){                                      //����һ���ַ���Ϊ��ʱ����ѭ��(����д��)
				break;
			}
			back=i;
		}
		back=back+1;
		return back;
	}
}