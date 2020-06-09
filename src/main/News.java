package main;

import java.awt.event.*;
import javax.swing.JFrame;

class News extends JFrame{
	private static final long serialVersionUID = 1L;
	//[��ʼ��������]
	Rule rule=new Rule();                         //��ʼ�����ɷ�����(�����)
	Rhyme rhyme=new Rhyme();                      //��ʼ���ϲ���(�����)
	Antithesis antithesis=new Antithesis();       //��ʼ��������(�����)
	PoemData poemData=new PoemData();             //��ʼ��ʫ����(���)
	Window window=new Window();                   //��ʼ��������(��)
	FileODo fileODo=new FileODo();                //��ʼ���ļ��������(��)
	FileIDo fileIDo=new FileIDo();                //��ʼ���ļ��ڲ�����(��)
	InnerData data=new InnerData();               //��ʼ���������ݿ�(��)
	Base base=new Base();                         //��ʼ���ײ���(��)
	
	//����������
	News(){}
	
	//����Ϣ���ܡ����ͺ��Ĵ��������ء�
	public void AddMouse(){
		ListenMouse();
	}	
	
	//����Ϣ���ܡ����ͺ��Ĵ�������
	//[��Ϣ������]�����Ϣ������
	public void ListenMouse(){
		window.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){  
				if(e.getButton()==MouseEvent.BUTTON1){                //���������  
					int x=e.getX();
					int y=e.getY();
					ListenMouseLB(x,y);
				}
				if(e.getButton()==MouseEvent.BUTTON2){               //�����껬��  
					int x=e.getX();
					int y=e.getY();
					ListenMouseRO(x,y);
				}
				if(e.getButton()==MouseEvent.BUTTON3){               //�������Ҽ�  
					int x=e.getX();
					int y=e.getY();
					System.out.println("���꣺x="+x+";y="+y);         //�����ԡ��������ռ���(��Դ������Ͻ�)
					ListenMouseRB(x,y);
				}
			} 
		});
	}

	//[��Ϣ������]��������Ϣ������(����������x,y����)
	public void ListenMouseLB(int x,int y){
		ButtonCloseAll(x,y);                                          //����ť-ͨ�á��رմ���
		ButtonMainWindow(x,y);                                        //����ť-ͨ�á������濪��
		ButtonAnalyseWindow(x,y);                                     //����ť-ͨ�á��������ɽ��濪��
		ButtonDataBaseWindow(x,y);                                    //����ť-ͨ�á����ݿ�������濪��
		ButtonTester(x,y);                                            //����ť-ͨ�á����Կ���
		//[����2�����ɷ�����ť]
		if(window.nowWindow==2){
			ButtonAnalysePoem(x,y);                                   //����ť-����2��ʫ�����(Rule�ӿ�)
			ButtonClearInputPoem(x,y);                                //����ť-����2��ҳ�����
		}
		//[����3�����ݿ������ť]
		if(window.nowWindow==3){
			ButtonBuildPoemData(x,y);                                 //����ť-����3���ؽ�ʫ�����ݿ�
			ButtonReadTXT(x,y);                                       //����ť-����3����ReadIn�ļ��ж�ȡ
			ButtonSearch(x,y);                                        //����ť-����3�����ݿ�����
			ButtonRhyme(x,y);                                         //����ť-����3���ϲ�������ʾ
		}
	}
	
	//[��Ϣ������]����Ҽ���Ϣ������(��������Ҽ�x,y����)
	public void ListenMouseRB(int x,int y){		
	}
	
	//[��Ϣ������]��������Ϣ������(����������x,y����)
	public void ListenMouseRO(int x,int y){
	}
	
	//����ť�жϲ�������
	//[��ť-ͨ��]�رմ���(����������x,y����)
	public void ButtonCloseAll(int x,int y){
		if(x>941&&x<978&&y>52&&y<73){                                 //�ж���������ڰ�ť��
			System.exit(0);                                           //�رմ���(���ùرշ�ʽ0Ϊ����)
		}
	}
	
	//[��ť-ͨ��]���濪��(����������x,y����)
	public void ButtonMainWindow(int x,int y){
		if(x>348&&x<423&&y>57&&y<75){                                 //�ж���������ڰ�ť��
			if(window.nowWindow!=1){
				window.DrawCloseNowWindow();                          //�����ơ��رյ�ǰ����
				window.DrawMainWindow();                              //�����ơ�����������
			}
		}
	}
	
	//[��ť-ͨ��]�������ɽ��濪��(����������x,y����)
	public void ButtonAnalyseWindow(int x,int y){
		if(x>456&&x<520&&y>57&&y<75){                                 //�ж���������ڰ�ť��
			if(window.nowWindow!=2){
				window.DrawCloseNowWindow();                          //�����ơ��رյ�ǰ����
				window.DrawAnalyseWindow();                           //�����ơ����ɷ�������
			}
		}
	}
	
	//[��ť-ͨ��]���ݿ�������濪��(����������x,y����)
	public void ButtonDataBaseWindow(int x,int y){
		if(x>582&&x<658&&y>56&&y<75){                                 //�ж���������ڰ�ť��
			if(window.nowWindow!=3){
				window.DrawCloseNowWindow();                          //�����ơ��رյ�ǰ����
				window.DrawDatabaseWindow();                          //�����ơ����棺���ݿ����
			}
		}
	}
	
	//[��ť-ͨ��]���Կ��أ����Ͻǲ豭ͼ��(����������x,y����)
	public void ButtonTester(int x,int y){
		if(x>24&&x<92&&y>36&&y<94){                                   //�ж���������ڰ�ť��
		}
	}
	
	//[��ť-����2]ʫ�����[Rule](����������x,y����)
	public void ButtonAnalysePoem(int x,int y){
		if(x>188&&x<283&&y>636&&y<661){                               //�ж���������ڰ�ť��
			String poem=window.inputPoem.getText();                   //����Ϣ����ȡ"ʫ�������ı���"��������
			rule.ReadFromString(poem);                                //[Rule]�ύ�������ݽ��з���
			String analyse=rule.RuleEntrance();                       //[Rule]���ܷ������
			window.outputAnalyse.setText(analyse);                    //����ʾ��"ʫ����������ı���"��ʾ��������
		}
	}
	
	//[��ť-����2]ҳ�����(����������x,y����)
	public void ButtonClearInputPoem(int x,int y){
		if(x>291&&x<359&&y>636&&y<661){                               //�ж���������ڰ�ť��
			window.inputPoem.setText(null);                           //����ʾ�����"ʫ�������ı���"
			window.outputAnalyse.setText(null);                       //����ʾ�����"ʫ����������ı���"
		}
	}
	
	//[��ť-����3]�ؽ�ʫ�����ݿ�(����������x,y����)
	public void ButtonBuildPoemData(int x,int y){
		if(x>62&&x<159&&y>193&&y<217){                                //�ж���������ڰ�ť��
			poemData.FoundPoemBase();                                 //[PoemData]�����յ�ʫ��
			window.ShowdatabaseArea("�ɹ������յ�ʫ�⡭��\n");
			poemData.FoundIndexBase();                                //[PoemData]�����յ�������
			window.ShowdatabaseArea("�ɹ������յ������⡭��\n");
		}
	}
	
	//[��ť-����3]��ReadIn.txt�ļ��ж�ȡ(����������x,y����)
	public void ButtonReadTXT(int x,int y){
		if(x>62&&x<159&&y>217&&y<247){                                //�ж���������ڰ�ť��
			window.ShowdatabaseArea("���ڴ�ReadIn.txt�ļ��ж�ȡ����\n");
			poemData.TXTRead();                                       //[PoemData]��ȡReadIn�ļ��е�����
			window.ShowdatabaseArea("���ļ��ж�ȡ�ɹ�����\n");
		}
	}
	
	//[��ť-����3]���ݿ�����(����������x,y����)
	public void ButtonSearch(int x,int y){
		if(x>906&&x<960&&y>131&&y<148){                               //�ж���������ڰ�ť��
			String search=window.search.getText();                    //����Ϣ����ȡ"���ݿ������ı���"��������
			int test=AnalyseKind(search);
			if(test==1){                                              //�����ֱ�������
				LinkPDSearchCode(search);                             //[PoemData]���ݿ����������ѯ
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
	
	//[��ť-����3]�ϲ�������ʾ(����������x,y����)
	public void ButtonRhyme(int x,int y){
		if(x>333&&x<398&&y>86&&y<100){                                //�ж���������ڰ�ť��
			window.search.setText("RH-");
			window.ShowdatabaseArea("****�ϲ������÷�����****\n");
			window.ShowdatabaseArea("����Ӻ��ֵ��ϲ���'RH-'+A�ϲ���+':'+����'\n");
			window.ShowdatabaseArea("����ʾȫ�����ݿ⣺'RH-'+W\n");
		}
	}
	
	//���ӿ�ģ�顿������������ģ��
	//[PoemData]���ݿ��������(���������ַ�����ֱ����ʾ)
	public void LinkPDSearchCode(String search){
		int number=base.CharToFigure(search);
		window.ShowdatabaseArea("[���ұ��]"+search+"......\r\n");
		poemData.ReadOnePoem(number);                                 //[PoemData]��ȡ���ݿ������б���
		String from=poemData.LinkReadFrom();                          //[PoemData]��ȡ��ǰ����ʫ��-ʫ�ĳ���
		String name=poemData.LinkReadName();                          //[PoemData]��ȡ��ǰ����ʫ��-ʫ��
		String author=poemData.LinkReadAuthor();                      //[PoemData]��ȡ��ǰ����ʫ��-������
		int line=poemData.LinkReadLine();                             //[PoemData]��ȡ��ǰ����ʫ��-ʫ����
		String sen[]=poemData.LinkReadSentence();                     //[PoemData]��ȡ��ǰ����ʫ��-ʫ������
		window.ShowdatabaseArea("ʫ�ĳ�����"+from+"\r\n");
		window.ShowdatabaseArea("ʫ�����ƣ�"+name+"\r\n");
		window.ShowdatabaseArea("ʫ�����ߣ�"+author+"\r\n");
		window.ShowdatabaseArea("ʫ�����ݣ�\r\n");
		for(int i=0;i<line;i++){
			String temp=new String("");
			if(i%2==0){
				temp="��";
			}
			else{
				temp="��\r\n";
			}
			window.ShowdatabaseArea(sen[i]+temp);
		}
	}
	
	//[PoemData]���ݿ�ʫ������(��������ʫ����ֱ����ʾ)
	public void LinkPDSearchName(String search){
		window.ShowdatabaseArea("[����ʫ��]"+search+"......\r\n");
		String name[]=poemData.SearchByName(search);                  //[PoemData]�����ݿ��в�ѯʫ��(����ʫ������)
		for(int i=1;i<101;i++){
			if(name[i-1]!=null){
				window.ShowdatabaseArea(Integer.toString(i,10)+":"+name[i-1]+"\r\n");
			}
			else{
				break;
			}
		}
	}
	
	//[����]�������ݿ���������(���������ַ�������������:1Ϊ��Ų�ѯ,2Ϊʫ����ѯ)
	private int AnalyseKind(String search){
		int back=0;
		//[����Ƿ�Ϊ���ֱ�������]
		int test=base.IsChar(search);                                 //[Base]�ж��Ƿ�Ϊ����
		String sign=search.substring(0,1);                            //��ȡ��ʶ��
		if(test==-1){
			back=1;
		}
		if(sign.equals("#")){
			back=2;
		}
		sign=search.substring(0,2);                            //��ȡ��ʶ��
		if(sign.equals("RH")){                //�ϲ�����״̬
			back=3;
		}
		return back;
	}	
}