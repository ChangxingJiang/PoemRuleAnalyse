package main;

import java.awt.*;                      //�������ڴ����û�����ͻ���ͼ��ͼ���������
import javax.swing.*;                   //����֧��

class Window extends JFrame{
	private static final long serialVersionUID = 4876287045280354524L;
	//[���ڳ���]
	int screenx=0,screeny=0;                      //����������Ļˮƽ�ֱ���(���)����ֱ�ֱ���(�߶�)
	String path=new String("");                   //�������������Ŀ¼��ַ
	int windowx=0,windowy=0;                      //���������������ϽǺ����ꡢ������
	
	//[���ڱ���]
	int nowWindow=0;                              //����������ǰ�򿪽���(1Ϊ������;2Ϊ���ɷ���)
	
	//[��������]
	Font song15b=new Font("����",Font.BOLD,15);   //�����塿����15�żӴ�(Font.BOLD�Ӵ�)
	Font song20b=new Font("����",Font.BOLD,20);   //�����塿����20�żӴ�(Font.BOLD�Ӵ�)
	Font yh15=new Font("΢���ź�",Font.PLAIN,15); //�����塿΢���ź�15��(Font.PLAIN��ͨ����)
	Font yh20=new Font("΢���ź�",Font.PLAIN,20); //�����塿΢���ź�20��(Font.PLAIN��ͨ����)
	Font yh24=new Font("΢���ź�",Font.PLAIN,24); //�����塿΢���ź�24��(Font.PLAIN��ͨ����)
	
	//[�����������]
	JPanel Main=new JPanel();                     //��������(����1)�������������
	JPanel Analyse=new JPanel();                  //��������(����2)����ʫ�Ľ����������
	JPanel Database=new JPanel();                 //��������(����3)���ݿ���������������
	
	//[�����ı���]
	TextArea inputPoem;                           //���ı���(����2�����ɷ���)ʫ�������ı���
	TextArea outputAnalyse;                       //���ı���(����2�����ɷ���)ʫ����������ı���
	TextArea databaseArea;                        //���ı���(����3�����ݿ����)���ݿ���ʱ��ʾ�ı���
	//[�����ı���]
	JTextField search;                            //���ı���(����3�����ݿ����)���ݿ������ı���
	
	//[��ʼ��������]
	Base base=new Base();                         //��ʼ���ײ���(��)
	
	//�����������Զ���������
	Window(){
		InitializeWindow();                                           //����ʼ�������ڻ�������
		DrawMainWindow();                                             //�����ơ�����������
		setVisible(true);                                             //�����ơ�������ʾ
	}
	
	//����ʼ�������ڶ���(�ֱ��ʡ���ַ������λ�á����ڻ�������)
	public void InitializeWindow(){
		//[��ȡ��Ļ�ֱ���]
		Toolkit tool=getToolkit();                                    //��ʼ��������Toolkit(֧�ֶ�ȡ��ʾ���ֱ���)
		Dimension dim=tool.getScreenSize();                           //��ȡ��ʾ���ֱ���(dim.widthΪ���,dim.heightΪ�߶�)
		screenx=dim.width;
		screeny=dim.height;
		//[��ǰ��ַ��ȡ]
		path=base.FileOReadNowPath();                                 //��ȡ��ǰ��ַ
		//[����λ�ü���]
		windowx=(screenx-1024)/2;                                     //���㴰�����ϽǺ�����
		windowy=(screeny-768)/2;                                      //���㴰�����Ͻ�������
		//[���ڻ�������]
		setTitle("����ʫ��");                                         //�����塿��������
		setSize(1024,768);                                            //�����塿���ڳߴ�
		setLocation(windowx,windowy);                                 //�����塿����λ��
		setUndecorated(true);                                         //�����塿�Ƿ�ȥ��������(trueΪȥ����falseΪ����)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);               //�����塿���ڹرհ�ť��Ч
//		setExtendedState(JFrame.MAXIMIZED_BOTH);                      //�����塿���ڵ���չ״̬(NORMALĬ��;ICONIFIED��С��;MAXIMIZED_HORIZˮƽ�������;MAXIMIZED_VERT��ֱ�������;MAXIMIZED_BOTHˮƽ�������ֵ�������)
//		setResizable(false);                                          //�����塿���ô��ڴ�СΪ���ɱ�
//		setLayout(new FlowLayout());                                  //�����塿��ʽ���ֹ�����(��JPanel����ʾ��ͻ)
//		AWTUtilities.setWindowOpaque(this,false);                     //�����塿��Ļ͸����(���ȶ��������ڶ�)
	}
		
	//�����ơ����棺������
	public void DrawMainWindow(){
		nowWindow=1;
		Main.setLayout(null);                                         //��ղ��ֹ�����
		getContentPane().add(Main,BorderLayout.CENTER);               //�߽粼�ֹ�����(���Ĳ��֡�ʹ������ʱ����ɸ�������)
		//[���ƽ��汳��ͼ]-Background.png
		ImageIcon icon=new ImageIcon(path+"\\window\\Background.png");//д�롰�����汳��ͼ��·��
		JLabel back=new JLabel(icon);                                 //���������汳��ͼ�������ǩ
		back.setBounds(0,0,1024,768);                                 //�����ǩλ�á���С(��Դ���λ��)
		//[���ƽ�����ͼ]-Main.png
		icon=new ImageIcon(path+"\\window\\Main.png");                //д�롰�����汳��ͼ��·��
		JLabel main=new JLabel(icon);                                 //���������汳��ͼ�������ǩ
		main.setBounds(97,108,893,591);                               //�����ǩλ�á���С(��Դ���λ��)
		back.add(main);                                               //����ͼ���ŵ�����ͼ��
		Main.add(back);                                               //����ͼ�����������������
		Main.validate();                                              //��֤�������������������	
		Main.setVisible(true);                                        //��ʾ�������������
	}
	
	//�����ơ����棺���ɷ���
	public void DrawAnalyseWindow(){
		nowWindow=2;
		Analyse.setLayout(null);                                      //��ղ��ֹ�����
		getContentPane().add(Analyse,BorderLayout.CENTER);            //�߽粼�ֹ�����(���Ĳ��֡�ʹ������ʱ����ɸ�������)
		//[���ƽ��汳��ͼ]-Background.png
		ImageIcon icon=new ImageIcon(path+"\\window\\Analyse.png");   //д�롰�������汳��ͼ��·��
		JLabel back=new JLabel(icon);                                 //�����������汳��ͼ�������ǩ
		back.setBounds(0,0,1024,768);                                 //�����ǩλ�á���С(��Դ���λ��)
		//[�����ı���]-(����2�����ɷ���)ʫ�������ı���
		inputPoem=new TextArea("",28,10,TextArea.SCROLLBARS_NONE);    //�����ı������ı�������+�ı���ÿ���ַ���+�ı�������+������(SCROLLBARS_BOTH��ֱ��ˮƽ��������SCROLLBARS_VERTICAL_ONLYΪ��ֱ��������SCROLLBARS_HORIZONAL_ONLYˮƽ��������SCROLLBARS_NONE�޹�����)
		inputPoem.setText("[������Ҫ������ʫ��]");                    //�����ı�����ʼ�ı�
		inputPoem.setFont(yh24);                                      //�����ı�������(΢���ź�24��)
		inputPoem.setForeground(Color.black);                         //�����ı���������ɫ
//		inputPoem.addTextListener(this);                              //�����ı������ı�����(��ҪTextListener֧��)
		inputPoem.setBackground(new Color(255,250,245));              //�����ı���������ɫ
		inputPoem.setEditable(true);                                  //�����ı������ɱ༭(trueΪ�ɱ༭��falseΪ���ɱ༭)
		inputPoem.setBounds(55,154,450,470);                          //�����ı���λ��
		inputPoem.selectAll();                                        //ѡ���ı�����ȫ���ı�
		back.add(inputPoem);                                          //��ʫ�������ı������ŵ�����ͼ��
		//[�����ı���]-(����2�����ɷ���)ʫ����������ı���
		outputAnalyse=new TextArea("",20,20,TextArea.SCROLLBARS_NONE);//�����ı������ı�������+�ı���ÿ���ַ���+�ı�������+������(SCROLLBARS_BOTH��ֱ��ˮƽ��������SCROLLBARS_VERTICAL_ONLYΪ��ֱ��������SCROLLBARS_HORIZONAL_ONLYˮƽ��������SCROLLBARS_NONE�޹�����)
		outputAnalyse.setFont(yh20);                                  //�����ı�������(΢���ź�20��)
		outputAnalyse.setForeground(Color.black);                     //�����ı���������ɫ
		outputAnalyse.setBackground(new Color(255,250,245));          //�����ı���������ɫ
		outputAnalyse.setEditable(false);                             //�����ı������ɱ༭(trueΪ�ɱ༭��falseΪ���ɱ༭)
		outputAnalyse.setBounds(518,154,407,470);                     //�����ı���λ��
		back.add(outputAnalyse);                                      //��ʫ����������ı������ŵ�����ͼ��
		Analyse.add(back);                                            //����ͼ�����������������
		Analyse.validate();                                           //��֤�������������������	
		Analyse.setVisible(true);                                     //��ʾ�������������
	}
	
	//�����ơ����棺���ݿ����
	public void DrawDatabaseWindow(){
		nowWindow=3;
		Database.setLayout(null);                                     //��ղ��ֹ�����
		getContentPane().add(Database,BorderLayout.CENTER);           //�߽粼�ֹ�����(���Ĳ��֡�ʹ������ʱ����ɸ�������)
		//[���ƽ��汳��ͼ]-Database.png
		ImageIcon icon=new ImageIcon(path+"\\window\\Database.png");  //д�롰�����汳��ͼ��·��
		JLabel back=new JLabel(icon);                                 //���������汳��ͼ�������ǩ
		back.setBounds(0,0,1024,768);                                 //�����ǩλ�á���С(��Դ���λ��)
		//[�����ı���]-(����3�����ݿ����)���ݿ���ʱ��ʾ�ı���
		databaseArea=new TextArea("",20,20,TextArea.SCROLLBARS_NONE); //�����ı������ı�������+�ı���ÿ���ַ���+�ı�������+������(SCROLLBARS_BOTH��ֱ��ˮƽ��������SCROLLBARS_VERTICAL_ONLYΪ��ֱ��������SCROLLBARS_HORIZONAL_ONLYˮƽ��������SCROLLBARS_NONE�޹�����)
		databaseArea.setText("���ݿ���ʱ��ʾ����\n");                   //�����ı�����ʼ�ı�
		databaseArea.setFont(yh15);                                   //�����ı�������(΢���ź�15��)
		databaseArea.setForeground(Color.black);                      //�����ı���������ɫ
		databaseArea.setBackground(new Color(255,250,245));           //�����ı���������ɫ
		databaseArea.setEditable(false);                              //�����ı������ɱ༭(trueΪ�ɱ༭��falseΪ���ɱ༭)
		databaseArea.setBounds(296,177,667,455);                      //�����ı���λ��
		back.add(databaseArea);                                       //�����ݿ���ʱ��ʾ�ı������ŵ�����ͼ��
		//[�����ı���]-(����3�����ݿ����)���ݿ������ı���
		search=new JTextField("",60);                                 //�����ı����ı�������+�ı���ÿ���ַ���
		search.setFont(yh15);                                         //�����ı�������(΢���ź�15��)
		search.setForeground(Color.black);                            //�����ı���������ɫ
		search.setBackground(new Color(255,250,245));                 //�����ı��򱳾���ɫ
		search.setEditable(true);                                     //�����ı��򲻿ɱ༭(trueΪ�ɱ༭��falseΪ���ɱ༭)
		search.setBounds(320,132,572,17);                             //�����ı���λ��
		search.setHorizontalAlignment(JTextField.LEFT);               //�����ı�����뷽ʽ(JTextField.LEFT�����,JTextField.CENTER����,JTextField.RIGHT�Ҷ���)
 		back.add(search);                                             //�����ݿ������ı�����ŵ�����ͼ��
		Database.add(back);                                           //����ͼ�����������������
		Database.validate();                                          //��֤�������������������	
		Database.setVisible(true);                                    //��ʾ�������������
	}
	
	//�����ơ����棺�رյ�ǰ����
	public void DrawCloseNowWindow(){
		if(nowWindow==1){                                             //�������״̬
			Main.setVisible(false);                                   //�����ơ��ر�������
		}
		if(nowWindow==2){                                             //���ɷ��������״̬
			Analyse.setVisible(false);                                //�����ơ��رո��ɷ�������
		}
		if(nowWindow==3){                                             //���ɷ��������״̬
			Database.setVisible(false);                               //�����ơ��ر����ݿ��������
		}
	}
	
	//����ʾ�����ݿ���ʱ��ʾ�ı���
	public void ShowdatabaseArea(String show){
		databaseArea.append(show);
	}
}