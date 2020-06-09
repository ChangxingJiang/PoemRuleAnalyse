package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class Antithesis{
	/** 
	 * @ProjectName:Poem(����ʫ��)
	 * @Package:main
	 * @ClassName:Antithesis(����)
	 * @Description:���ж��̷���ļ���
	 * @Module:����ģ��;�ӿ�ģ��;������ض����Է���ģ��(����Է�+����λ�üӳ�+Ȩ�������㷨+ƽ�Ʒ���);����ģ��;�洢ģ��
	 * @Author:����
	 * @CreateDate:2016.11.16
	 * @Version:1.0
	 * @interface:
	**/
	//[���峣��]
	boolean debug=true;                           //���Կ���(trueΪ��)
	static final String USER="root";              //MySQL���ݿ��û���
	static final String PASS="pgybdc";            //MySQL���ݿ�����
	static String DB_DDL="jdbc:mysql://localhost";//��дDDL(���ݶ�������)������ַ
	static String DB_DML="jdbc:mysql://localhost/Rhyme";//��дDML(���ݲ�������)������ַ
	//[�������]
	Connection conn=null;                         //���ӱ���
	Statement stst=null;                          //��������(SELECTר��)
	Statement stit=null;                          //��������(INSECTר��)
	//[��ʼ��������]
	MySQL mySQL=new MySQL();                      //MySQL������(��)
	
	//����������
	Antithesis(){}
	
	/** @Module:����ģ��	 */	
	//������������MySQL���ݿ�����(�ɹ�����true��ʧ�ܷ���false)
	public boolean DataConnect(){
		if(!mySQL.STPLoadDriver()){                                                       //[MySQL]����MySQL����
			System.out.println("Error 2101:Antithesis���ݿ�JDBC���������쳣");
			return false;
		}
		String path="Antithesis";
		if(!mySQL.STPOpenConnection(path)){                                               //[MySQL]��Antithesis���ݿ⽨������
			System.out.println("Error 2101:���ݿ������쳣");
			return false;
		}
		return true;
	}
	
	/** @Module:����ģ��	 */	
	//�����ء���鵱ǰ���Ƿ��ظ�(�����ѯ��(����:����)���Ƿ���䵱ǰ�䣻�ظ�����true�����ظ�����false)
	public boolean DataCheckOnce(String sentence,boolean write){
		return true;
	}
	
	//��Base��������ʾ(���������ʾ����)
	private void Show(String test){
		if(debug==true){
			System.out.print(test);
		}
	}
	
	//��Base��������ʾ(���������ʾ����)
	private void Showln(String test){
		if(debug==true){
			System.out.println(test);
		}
	}
}