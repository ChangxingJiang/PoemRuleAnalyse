package main;

import java.io.*;                       //�ַ���֧��
import java.io.File;                    //�ļ���д֧��

class FileIDo{
	//[�������]
	String path=new String("");                   //������������·��
	boolean inspect=false;                        //������������·���Ƿ���ڼ��
	
	//[��ʼ��������]
	Base base=new Base();                         //��ʼ���ײ���(��)
	
	FileIDo(){}//����������(�޲���������)
	
	//����������(�����������·��)
	FileIDo(String _path){
		path=_path;                                                   //���湤��·��
		File file=new File(path);
		inspect=file.exists();                                        //�ж��ļ��Ƿ����(trueΪ���ڣ�falseΪ������)
	}
	
	//�����������޸ĵ�ǰ����·��(�����µ�·��)
	public void ChangeTo(String _path){
		path=_path;                                                   //���湤��·��
		File file=new File(path);
		inspect=file.exists();                                        //�ж��ļ��Ƿ����(trueΪ���ڣ�falseΪ������)
	}
	
	//���ļ����ļ���"��"��ʼ��(������Ҫ��ʼ�������ļ�����)
	public void Initialize(long length){
		File file=new File(path);
		long now=file.length();
		for(long i=now;i<length;){
			if((length-now)>=128){                                    //��ʣ��128���ֽ�������Ҫ��дʱ
				Write(i,"                                                                                                                                ");
				i=i+128;
			}else if((length-now)>=64){                               //��ʣ��64���ֽ�������Ҫ��дʱ
				Write(i,"                                                                ");
				i=i+64;
			}else if((length-now)>=32){                               //��ʣ��32���ֽ�������Ҫ��дʱ
				Write(i,"                                ");
				i=i+16;
			}else if((length-now)>=16){                               //��ʣ��16���ֽ�������Ҫ��дʱ
				Write(i,"                ");
				i=i+16;
			}else if((length-now)>=8){                                //��ʣ��8���ֽ�������Ҫ��дʱ
				Write(i,"        ");
				i=i+8;
			}else if((length-now)>=4){                                //��ʣ��4���ֽ�������Ҫ��дʱ
				Write(i,"    ");
				i=i+4;
			}else if((length-now)>=2){                                //��ʣ��2���ֽ�������Ҫ��дʱ
				Write(i,"  ");
				i=i+2;
			}else{                                                    //��ʣ��1���ֽ���Ҫ��дʱ
				Write(i," ");
				i++;
			}
		}
	}
	
	//���ļ�����ȡ�ļ��������ļ���ʼ��ȡλ�á���ȡ���ȣ����ض�ȡ�����ݣ�
	public String Read(long index,int extent){
		String back=new String("");
		RandomAccessFile file=null;
		File filelong=new File(path);
		long length=filelong.length();                                //��ȡ�ļ�����(�ֽ���)
		if((index+extent)>length){                                    //��ȡ���곬���ļ�����
			if(length>index){                                         //����ȡ����λ���ļ��ڣ���ȡ���ļ���β
				extent=(int)(length-index);
			}
			else{
				return null;
			}
		}
		if((index+extent)>length||index<0||extent<=0){                //�ж��Ƿ���϶�ȡ��׼
			if(index<0){                                              //��ȡ����Ϊ����
				System.out.print("��ȡ����Ϊ������");
			}
			if(extent<=0){                                            //��ȡ����Ϊ����
				System.out.print("��ȡ����Ϊ������");
			}
			System.out.println("��ȡʧ��");
		}
		else{
			try{
				file=new RandomAccessFile(path, "r");                 //��һ����������ļ�������ֻ����ʽ("rw"Ϊ��д)
				file.seek(index);                                     //�����ļ��Ŀ�ʼλ���Ƶ�indexλ��
				byte[] bytes=new byte[extent];
				file.read(bytes);                                     //��һ�ζ�ȡ���ֽ�������byteread
				back=new String(bytes);
			}
			catch(IOException e){
				System.out.println("��ȡ��ȡ���������쳣");
			}
			finally{
				if(file!=null){
					try{file.close();}
					catch(IOException e1){
						System.out.println("��ȡ��ȡ�ر��쳣");
					}
				}
			}
		}
		return back;
    }
	
	//���ļ����ļ�д��(�����ļ���ʼд�����ꡢд������)
	public void Write(long index,String content){
		RandomAccessFile file=null;
		byte bytes[]=content.getBytes();                              //��contentת��Ϊ�ֽڴ�
		try {
            file=new RandomAccessFile(path,"rw");                     //��һ����������ļ�������ֻ����ʽ("rw"Ϊ��д)
			//long fileLength=file.length();                            //�ļ����ȣ��ֽ���
			file.seek(index);                                         //�����ļ��Ŀ�ʼλ���Ƶ�indexλ�á�
			file.write(bytes);                                        //�ļ�д��
		}
		catch(IOException e){
            base.ErrorStop("�ļ�д���쳣:FileWIndex");
        }
		finally{
            if(file!=null){
                try{
                    file.close();                                     //�ر��ļ�
                }
				catch(IOException e1){
                }
            }
        }
    }
	
	//��TXT����ȡһ��TXT�ĵ�(�����ȡ���������ض�ȡ����)(�����������1024�ֽ�)
	//[�ݴ����]
	long sline=0;
	long sindex=0;
	//[����]
	public String ReadTXT(long line){
		String temp=null;                                             //��ʱ��ȡ����
		long index=0;                                                 //����ָ��
		int search=0;                                                 //��������
		long safe=0;                                                  //ѭ������
		if(sline==line&&sindex!=0){                                   //�����䡿�洢��¼Ϊ��ǰ��
			temp=Read(sindex,1024);                                   //
			if(temp==null){
				return null;
			}
			search=temp.indexOf("\r\n");			                  //��temp��������ǰ"\r\n"��>=0Ϊ����,=-1Ϊ������
			if(search==-1){                                           //��δ������\r\n
				sline=sline+1;                                        //�����䡿�洢��һ������
				sindex=sindex+temp.getBytes().length;
			}
			if(search==0){                                            //��������"\r\n"λ�����ֽ�
				temp=null;
				sindex=sindex+2;
			}
			if(search>0){
				temp=temp.substring(0,search);
				sindex=sindex+temp.getBytes().length+2;               //�����䡿�洢��һ����ʼ���ļ�����
			}
			sline=line+1;                                             //�����䡿�洢��һ������
		}
		else{                                                         //�����䡿û�д洢��¼��洢��¼�ڶ�ȡ������
			if(sline==0||sline>line){                           
				sline=0;
				sindex=0;
			}
			long i=sline;                                             //��ʱ������¼
			index=sindex;
			while(safe<1000000){
				temp=Read(index,1024);
				if(temp==null){
					return null;
				}
				search=temp.indexOf("\r\n");			              //��temp��������ǰ"\r\n"��>=0Ϊ����,=-1Ϊ������
				if(search==-1){                                       //��δ������\r\n
					sline=i+1;                                        //�����䡿�洢��һ������
					sindex=sindex+temp.getBytes().length;
				}
				if(search>=0){                                        //������\r\n
					temp=temp.substring(0,search);
					index=index+temp.getBytes().length+2;
				}
				if(i==line){
					sline=i+1;                                        //�����䡿�洢��һ������
					sindex=index;                                     //�����䡿�洢��һ����ʼ���ļ�����
					break;
				}
				i++;
				safe++;                                               //[ѭ����ȫ����]
			}
		}
//		System.out.println("line:"+line+"��������Ϊ��"+temp);           //[������ʾ]��ȡTXT���н��
		return temp;
	}
}



/*////////////////////////////////�ļ������///////////////////////////////////
@@���ߣ�����
@@����ʱ�䣺2016.10.28
@@���ü��𣺡�
/////////////////////////////////////////////////////////////////////////////*/
class FileODo{
	//[��ʼ��������]
	Base base=new Base();                         //��ʼ���ײ���(��)
	
	FileODo(){}//����������
	
	//���ļ��������ļ���(�����ļ��е�ַ·��)
	public void FoundPath(String path){
		File file=new File(path);
		if(!file.exists()){                                           //�ж��ļ����Ƿ����
			file.mkdir();                                             //�����ļ���
	    }
	}
	
	//���ļ��������ļ�(���봴���ļ��ĵ�ַ���Ƿ��ؽ�(trueΪ�滻ԭ�ļ���falseΪ����ԭ�ļ�))
	public void FoundFile(String path,boolean rebuild){
		boolean temp=true,execute=true;                               //������ʱ������ִ�б���
		File file=new File(path);
		temp=file.exists();                                           //�ж��ļ��Ƿ���ڣ�falseΪ������
		if(temp==true){
			if(rebuild==false){                                       //�ļ������ұ���ԭ�ļ�
				execute=false;
			}
			else{
				DeleteFile(path);                                     //ɾ��ԭ�ļ�
			}
		}
		temp=file.getParentFile().exists();                           //�ж��ļ�����Ŀ¼�Ƿ���ڣ�trueΪ����
		if(temp==false){                                              //����Ŀ¼������
			temp=file.getParentFile().mkdirs();                       //������Ŀ¼
			if(temp==false){                                          //����Ŀ¼����ʧ��
				execute=false;
			}
		}
		if(execute==true){
			try{  
				file.createNewFile();                                 //�����ļ�
			}
			catch(IOException e){
				System.out.println("�ļ�����ʧ��");  
			}
		}
	}
	
	//���ļ����ж��ļ��Ƿ����(�����ļ�·���������Ƿ������Ϊ�ļ�(���ڷ���true�������ڷ���false))
	public boolean Exist(String path){
		File file=new File(path);
		boolean Test=false;
		boolean existFile=file.exists();                              //�ж��ļ��Ƿ����
		boolean isFile=file.isFile();                                 //�ж��Ƿ�Ϊ�ļ�
		if(existFile==true&&isFile==true){
			Test=true;
		}
		return Test;
	}
	
	//���ļ���ɾ���ļ�(����ɾ���ļ�·���������Ƿ�ɾ���ɹ�)
	public boolean DeleteFile(String path){  
		File file=new File(path);  
		boolean temp=file.exists();                                   //�ж�Ŀ¼���ļ��Ƿ���ڣ�falseΪ���� 
		if(temp==true){                                                
			temp=file.isFile();                                       //�ж��Ƿ�Ϊ�ļ�
			if(temp==true){
				file.delete();                                        //ɾ���ļ�
			}
		}
		return temp;
	}
	
	//���ļ���Ѱ���ļ���С�ձ��(�����ļ�·�����ļ���ǰ�벿�֡���׺��������С���)
	public int LeastNumber(String path,String name,String suffix){
		int empty=0;
		FoundPath(path);                                              //ȷ�ϴ��ڸ��ļ���
		String frontName=path+name;
		String tempName=new String("");
		boolean tempTest=true;
		for(int i=1;i<999999;i++){
			tempName=frontName+Integer.toString(i,10)+suffix;
			tempTest=Exist(tempName);                                 //�ж��ļ��Ƿ����
			if(tempTest==false){
				empty=i;
				break;
			}
		}
		if(tempTest==true){
			System.out.println("��1000000����δ������Ч�ձ��");
		}
		return empty;
	}
}