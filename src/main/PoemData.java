package main;

class PoemData{
	//[��ǰʫ�����]
	String from=new String();                     //ʫ�ĳ���
	String name=new String();                     //ʫ��
	String author=new String();                   //������
	int line=0;                                   //ʫ����
	String sen[]=new String[1000];                //ʫ������
	int extent[]=new int[1000];                   //ʫ�䳤��
	
	//[TXT��ȡ��ʱ����]
	String temp=new String();                     //��ʱ��ȡTXTһ�д洢����
	boolean reading=false;                        //��ȡģʽ
	boolean cname=false,cauthor=false,ctext=false;//����ʫ�Ƿ������ʫ�������ߡ�ʫ��
	
	//[��ʼ��������]
	FileODo fileODo=new FileODo();                //��ʼ���ļ��������(��)
	InnerData data=new InnerData();               //��ʼ���������ݿ�(��)
	Base base=new Base();                         //��ʼ���ײ���(��)

	PoemData(){}//�����������޲���������
	
	//�����ݿ���ز�������������ȡʫ�⡢��������ز���
	//[���ݿ�]�����յ�ʫ��(��data�ļ����½���һ��ʫ����Ϊ0��ʫ��)
	public void FoundPoemBase(){
		String path=new String(""),time=new String("");
		path=base.FileOReadNowPath();                                 //��ȡ��ǰ��ַ
		path=path+"\\data\\Poem.dat";                                 //���뵱ǰ��ַ��data�ļ���
		fileODo.FoundFile(path,true);                                 //[FileODo]�����ļ�
		FileIDo fileIDo=new FileIDo(path);                            //��ʼ���ļ��ڲ�����(��)
		fileIDo.Initialize(200);                                      //[FileIDo]�ļ���"��"��ʼ��
		fileIDo.Write(0,"AA");                                        //[FileIDo]д��ʫ��汾
		time=base.GetTime();                                          //[Base]��ȡϵͳʱ��
		fileIDo.Write(02,time);                                       //[FileIDo]д��ʱ��
		fileIDo.Write(16,Integer.toString(200,10));                   //[FileIDo]д��ʫ�ⳤ��
		fileIDo.Write(25,Integer.toString(0,10));                     //[FileIDo]д��ʫ��ʫ����
	}
	
	//[���ݿ�]�����յ�������(��data�ļ����½���һ��ʫ����Ϊ0��������)
	public void FoundIndexBase(){
		String path=new String(""),time=new String("");
		path=base.FileOReadNowPath();                                 //��ȡ��ǰ��ַ
		path=path+"\\data\\Index.dat";                                //���뵱ǰ��ַ��record�ļ���
		fileODo.FoundFile(path,true);                                 //[FileODo]�����ļ�
		FileIDo fileIDo=new FileIDo(path);                            //��ʼ���ļ��ڲ�����(��)
		fileIDo.Initialize(200);                                      //[FileIDo]�ļ���"��"��ʼ��
		fileIDo.Write(0,"AA");                                        //[FileIDo]д��������汾
		time=base.GetTime();                                          //[Base]��ȡϵͳʱ��
		fileIDo.Write(02,time);                                       //[FileIDo]д��ʱ��
		fileIDo.Write(16,Integer.toString(0,10));                     //[FileIDo]��¼��(ʫ����)
	}
	
	//[���ݿ�]�����б���д�����ݿ�(�����Ƿ���յ�ǰ�������ݣ�trueΪ��գ�falseΪ����գ����������Ƿ�ɹ�)
	//�ƻ������ֽڽ�ȡ��������
	public void WritePerPoem(boolean empty){
		String path=new String(""),temp=new String("");
		int irecord=0,precord=0;                                      //index���¼����poem���¼��
		long lefile=0,leindex=0,count=0;                              //������ʱ������������poem��ָ�룬index��ָ��
		int lename=0,leauthor=0;                                      //ʫ�����ȡ�����������
		path=base.FileOReadNowPath();                                 //��ȡ��ǰ��ַ
		path=path+"\\data\\Poem.dat";                                 //���뵱ǰ��ַ��data�ļ���peom�ļ�
		FileIDo poem=new FileIDo(path);                               //��ʼ���ļ��ڲ�����(��)
		path=base.FileOReadNowPath();                                 //��ȡ��ǰ��ַ
		path=path+"\\data\\Index.dat";                                //���뵱ǰ��ַ��record�ļ���Index�ļ�
		FileIDo index=new FileIDo(path);                              //��ʼ���ļ��ڲ�����(��)
		lename=name.getBytes().length;
		leauthor=author.getBytes().length;
		for(int i=0;i<line;i++){                                      //����ʫ�䳤��
			extent[i]=sen[i].getBytes().length;
		}
		//�ļ�ͷ�޸�
		temp=index.Read(16,5);                                        //[FileIDo]��ȡ�������¼��
		irecord=base.CharToFigure(temp);
		index.Write(16,Integer.toString(irecord+1,10));               //[FileIDo][Index�ļ�ͷ]д���������¼��
		System.out.println("������¼��:"+irecord+"\n");
		leindex=200+100*irecord;                                      //����index��ָ��λ��
		temp=poem.Read(16,9);                                         //[FileIDo]��ȡʫ���ļ�����
		lefile=base.CharToFigure(temp);
		temp=poem.Read(25,5);                                         //[FileIDo]��ȡʫ��ʫ������
		precord=base.CharToFigure(temp);
		precord++;
		poem.Write(25,Integer.toString(precord,10));                  //[FileIDo][poem�ļ�ͷ]д���������¼��
		if(name.getBytes().length>14){                                //��ȡname��ǰ14���ֽ�
			temp=name.substring(0,7);
		}
		else{
			temp=name;
		}
		//ʫ�⡢�������ʼ��
		index.Initialize(200+100*irecord+100);                        //[FileIDo]�ļ���"��"��ʼ��
		count=lefile+lename+leauthor+19+2*line;
		for(int i=0;i<line;i++){
			count=count+extent[i];
		}
		poem.Initialize(count);                                       //[FileIDo]�ļ���"��"��ʼ��
		//���ݿ�д��
		index.Write(leindex,temp);                                    //[FileIDo][index]д��ʫ��ժҪ(�߸���)
		index.Write(leindex+14,Long.toString(lefile+2));              //[FileIDo][index]д��ʫ��������
		poem.Write(lefile,"��");                                      //[FileIDo][poem]д��ʫ����־
		poem.Write(lefile+2,name);                                    //[FileIDo][poem]д��ʫ��
		lefile=lefile+2+lename;
		index.Write(leindex+22,Long.toString(lefile));                //[FileIDo][index]д��ʫ��β����
		poem.Write(lefile,"��");                                      //[FileIDo][poem]д����Դ��־
		index.Write(leindex+30,Long.toString(lefile+2));              //[FileIDo][index]д����Դͷ����
		poem.Write(lefile+2,from);                                    //[FileIDo][poem]д����Դ
		poem.Write(lefile+12,"��");                                   //[FileIDo][poem]д�����߱�־
		poem.Write(lefile+14,author);                                 //[FileIDo][poem]д������
		lefile=lefile+14+leauthor;
		index.Write(leindex+38,Long.toString(lefile));                //[FileIDo][index]д������β����
		poem.Write(lefile,"��");
		index.Write(leindex+46,Long.toString(lefile+2));              //[FileIDo][index]д��ʫ��������
		poem.Write(lefile+2,Integer.toString(line,10));               //[FileIDo][poem]д��ʫ��-ʫ����
		for(int i=0;i<line;i++){
			poem.Write(lefile+5+2*i,Integer.toString(extent[i],10));  //[FileIDo][poem]д��ʫ��-ʫ�䳤��
		}
		count=0;                                                      //����count
		for(int i=0;i<line;i++){
			poem.Write(lefile+5+2*line+count,sen[i]);                 //[FileIDo][poem]д��ʫ��-ʫ�䳤��
			count=count+extent[i];
		}
		lefile=lefile+5+2*line+count;
		index.Write(leindex+54,Long.toString(lefile));                //[FileIDo][index]д��ʫ��β����
		if(empty==true){
			ResetVariate();
		}
		poem.Write(16,Long.toString(lefile));                         //[FileIDo][poem�ļ�ͷ]poemд���ļ�����
	}
	
	//[���ݿ�]��ȡ���ݿ������б���(����Ҫ��ȡ��ʫ�ı�ţ������ݶ���PoemData��ȫ�ֱ�����)
	public boolean ReadOnePoem(int number){
		boolean back=true;                                            //���巵�ر���
		long nfist=0,nlast=0,ffirst=0,alast=0,sfirst=0;       //����index��ȡ��������(slast=0)
		String path=new String(""),temp=new String("");
		int record=0,count=0;
		ResetVariate();                                               //���ö�����д�����
		path=base.FileOReadNowPath();                                 //��ȡ��ǰ��ַ
		path=path+"\\data\\Poem.dat";                                 //���뵱ǰ��ַ��data�ļ���peom�ļ�
		FileIDo poem=new FileIDo(path);                               //��ʼ���ļ��ڲ�����(��)
		path=base.FileOReadNowPath();                                 //��ȡ��ǰ��ַ
		path=path+"\\data\\Index.dat";                                //���뵱ǰ��ַ��record�ļ���Index�ļ�
		FileIDo index=new FileIDo(path);                              //��ʼ���ļ��ڲ�����(��)
		temp=index.Read(16,5);                                        //[FileIDo][index]��ȡ��¼��
//		System.out.println("��¼��:"+temp);                           //[����Ԫ��]
//		System.out.println("number:"+number);                         //[����Ԫ��]
		record=base.CharToFigure(temp);
		if(number>=record){
			System.out.println("��ȡ��ֵ�������ݿ��¼�����޷���ȡ");
			back=false;
		}
		else{
			//��ȡindex��������
			temp=index.Read(200+100*number+14,8);                     //[FileIDo][index]��ȡʫ��������
			nfist=base.CharToFigure(temp);
			temp=index.Read(200+100*number+22,8);                     //[FileIDo][index]��ȡʫ��β����
			nlast=base.CharToFigure(temp);
			temp=index.Read(200+100*number+30,8);                     //[FileIDo][index]��ȡ��Դ������
			ffirst=base.CharToFigure(temp);
			temp=index.Read(200+100*number+38,8);                     //[FileIDo][index]��ȡ����β����
			alast=base.CharToFigure(temp);
			temp=index.Read(200+100*number+46,8);                     //[FileIDo][index]��ȡʫ��������
			sfirst=base.CharToFigure(temp);
			//temp=index.Read(200+100*number+54,8);                     //[FileIDo][index]��ȡʫ��β����
			//slast=base.CharToFigure(temp);
			//��ȡpoem��������
			name=poem.Read(nfist,(int)(nlast-nfist));                 //[FileIDo][poem]��ȡʫ��
			from=poem.Read(ffirst,10);                                //[FileIDo][poem]��ȡ��Դ
			author=poem.Read((ffirst+12),(int)(alast-ffirst-12));     //[FileIDo][poem]��ȡ����
			temp=poem.Read(sfirst,3);                                 //[FileIDo][poem]��ȡʫ������
			line=base.CharToFigure(temp);
			for(int i=0;i<line;i++){
				temp=poem.Read(sfirst+2*i+3,2);                       //[FileIDo][poem]��ȡʫ��-ʫ�䳤��
				extent[i]=base.CharToFigure(temp);
				sen[i]=poem.Read(sfirst+2*(line)+3+count,extent[i]);  //[FileIDo][poem]��ȡʫ��
				count=count+extent[i];
			}
		}
		return back;
	}
	
	//[���ݿ�]�����ݿ��в�ѯʫ��(����ʫ�����ƣ���������ʫ�����֣����ذ����ò��ֵ�����ʫ��)
	public String[] SearchByName(String sname){
		String back[]=new String[1000];
		boolean test=true;
		int i=0,index=0,number=0;
		while(test==true){
			test=ReadOnePoem(i);                                      //[���ݿ�]��ȡ���ݿ������б���
			index=name.indexOf(sname);	                              //��name������sname
			if(index!=-1){
				back[number]=name;
				number++;
			}
			i++;
			System.out.println(i);
		}
		return back;
	}
	
	//[���ݿ�]�����ݿ��в�ѯʫ��(�����������ƣ���������ʫ�����֣����ذ����ò��ֵ�����ʫ��)
	public String[] SearchByAuthor(String sauthor){
		String back[]=new String[1000];
		boolean test=true;
		int i=0,index=0,number=0;
		while(test==true){
			test=ReadOnePoem(i);                                      //[���ݿ�]��ȡ���ݿ������б���
			index=author.indexOf(sauthor);	                              //��name������sauthor
			if(index!=-1){
				back[number]=name;
				number++;
			}
			i++;
			System.out.println(i);
		}
		return back;
	}
		
	//��TXT��ز�������ȡTXT�ļ��ڵ�ʫ������
	//[TXT]��ȡTXT�е�ʫ������(��ȡReadIn.txt�ļ��е�ʫ�䣬��ȡ��ʫ�⡢������)
	public void TXTRead(){
		String path=new String("");
		path=base.FileOReadNowPath()+"\\ReadIn.txt";
		FileIDo fileIDo=new FileIDo(path);                            //��ʼ���ļ��ڲ�����(��)
		int wait=0;                                                   //��ʱ������
		for(long i=0;i<10000000;i++){
			temp=fileIDo.ReadTXT(i);                                  //[FileIDo]��ȡһ��TXT�ĵ�
			if(temp!=null&&temp!=" "){
				wait=0;
				int next=TXTFindName();                               //�ж��Ƿ�����µ�ʫ��
				if(next==1||next==2){                                 //��������ͷ��
					if(reading==true){                                //д�뵱ǰʫ�䲢��ձ���
						if(name.equals("")){                          //���ʫ��Ϊ����ֱ����ձ��������ٶ�д
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
				TXTReadFrom();                                        //��ȡʫ�����
				if(next==2){                                          //�������ʫ����ͷ��
					i++;
					temp=temp+fileIDo.ReadTXT(i);
					TXTReadName();                                    //��ȡʫ��
				}
				else{
					TXTReadName();                                    //��ȡʫ��
				}
				TXTReadSentence();                                    //��ȡʫ��
				TXTReadAuthor();                                      //��ȡ����
			}
			else{                                                     //�������Ϊ��
				wait++;
				if(wait>50){                                          //�������50���������У�������
					break;
				}
			}
		}
	}
	
	//[TXT]��TXT��ȡ�������ж�����ʫ������(����0Ϊû��ʫ��������1Ϊ��ʫ��������2Ϊֻ�п�ͷ��������3Ϊֻ�н�β��)
	private int TXTFindName(){
		int back=0;                                                   //���巵�ر���
		int left=temp.indexOf("��");
		int right=temp.indexOf("��");
		if(left!=-1&&right!=-1){                                      //�п�ͷ������β��
			back=1;
		}
		else if(left!=-1&&right==-1){                                 //�п�ͷ����û�н�β��
			back=2;
		}
		else if(left==-1&&right!=-1){                                 //û�п�ͷ�����н�β��
			back=3;
		}
		return back;
	}
	
	//[TXT]��TXT��ȡ�����ж�����Դ(ȫ��ʫר�ã���ȡ�ɹ�����true��ʧ�ܷ���false)
	private boolean TXTReadFrom(){
		boolean back=true;
		String book=new String(""),number=new String("");
		int i=temp.indexOf("��");			                          //��temp������"��",>=0Ϊ����,=-1Ϊ������
		int j=temp.indexOf("_");			                          //��temp������"_",>=0Ϊ����,=-1Ϊ������
		if(i!=-1&&j!=-1){
			book=temp.substring((i+1),j);                             //��ȡ�������
			number=temp.substring((j+1),(j+5));                       //��ȡ�������
			int temp=base.IsChar(number);                             //�ж�number�Ƿ�Ϊ����
			number=number.substring(0,temp);                          //��ȡnumber�������ֵĲ���
			from="QT"+book+"��"+number;
		}
		else{
			back=false;
		}
		return back;
	}
	
	//[TXT]��TXT��ȡ�����ж���ʫ��(��ȡ�ɹ�����true��ʧ�ܷ���false)
	private boolean TXTReadName(){
		boolean back=true;
		int left=temp.indexOf("��");			                      //��temp������"��",>=0Ϊ����,=-1Ϊ������
		int right=temp.indexOf("��");			                      //��temp������"��",>=0Ϊ����,=-1Ϊ������
		if(left!=-1&&right!=-1){                                      //���"��""��"���ѵ�
			name=temp.substring((left+1),right);                      //��ȡ��indexs�е�indexe����
			temp=temp.substring((right+1));                           //��ʫ�����ִ�temp�ڽ�ȥ
			cname=true;
		}
		else{
			back=false;
		}
		return back;
	}
	
	//[TXT]��TXT��ȡ�����ж�������(��ȡ�ɹ�����true��ʧ�ܷ���false)
	private boolean TXTReadAuthor(){
		boolean back=false;
		if(cname==true&&cauthor==false&&ctext==false){                //�Ѽ���ʫ����δ�������ߡ�����
			int length=temp.length();
			if(length>1&&length<5){                                   //����ʣ�೤��Ϊ2-4��                                    
				author=temp;
				cauthor=true;
				back=true;
			}
		}
		return back;
	}
	
	//[TXT]��TXT��ȡ�����ж���ʫ��(��ȡ�ɹ�����true��ʧ�ܷ���false)
	private boolean TXTReadSentence(){
		boolean back=false;
		int ico=0,ipo=0,safe=0;
		while(ico!=-1||ipo!=-1){
			ico=temp.indexOf("��");                                   //��temp������"��",>=0Ϊ����,=-1Ϊ������
			ipo=temp.indexOf("��");                                   //��temp������"��",>=0Ϊ����,=-1Ϊ������
//			System.out.print("ico:"+ico+";ipo:"+ipo);
			if(line>=1000){                                           //ʫ�䳬��1000��ʱ���ܾ�����
				break;
			}
			if(ipo==-1&&ico!=-1){
				sen[line]=temp.substring(0,ico);                      //��ȡʫ��
				temp=temp.substring((ico+1));                         //��ȡʣ�ಿ��
				ctext=true;
				back=true;
				line++;
			}
			if(ico==-1&&ipo!=-1){
				sen[line]=temp.substring(0,ipo);                      //��ȡʫ��
				temp=temp.substring((ipo+1));                         //��ȡʣ�ಿ��
				ctext=true;
				back=true;
				line++;
			}
			if(ipo!=-1&&ipo<ico){
				sen[line]=temp.substring(0,ipo);                      //��ȡʫ��
				temp=temp.substring((ipo+1));                         //��ȡʣ�ಿ��
				ctext=true;
				back=true;
				line++;
			}
			if(ico!=-1&&ico<ipo){
				sen[line]=temp.substring(0,ico);                      //��ȡʫ��
				temp=temp.substring((ico+1));                         //��ȡʣ�ಿ��
				ctext=true;
				back=true;
				line++;
			}
			if(ico==-1&&ipo==-1){
				break;
			}
			safe++;                                                   //[ѭ����ȫ����]
			if(safe>100){break;}
		}
		return back;
	}
	
	//���ӿ�ģ�顿���������󴫲�ģ��
	//[�ӿ�]��ȡ��ǰ����ʫ��-ʫ�ĳ���
	public String LinkReadFrom(){
		String back=from;
		return back;
	}
	
	//[�ӿ�]��ȡ��ǰ����ʫ��-ʫ��
	public String LinkReadName(){
		String back=name;
		return back;
	}
	
	//[�ӿ�]��ȡ��ǰ����ʫ��-������
	public String LinkReadAuthor(){
		String back=author;
		return back;
	}
	
	//[�ӿ�]��ȡ��ǰ����ʫ��-ʫ����
	public int LinkReadLine(){
		int back=line;
		return back;
	}
	
	//[�ӿ�]��ȡ��ǰ����ʫ��-ʫ������
	public String[] LinkReadSentence(){
		String back[]=new String[1000];
		for(int i=0;i<line;i++){
			back[i]=sen[i];
		}
		return sen;
	}
	
	//[����]���ö�����д�����
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