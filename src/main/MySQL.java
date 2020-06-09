package main;

import java.sql.*;                                //����SQL���԰�

class MySQL{
	/** 
	 * @ClassName:MySQL(MySQL����)
	 * @Description:�ṩӦ��JDBC����MySQL�Ļ�������
	 * @Module:����ģ��;���ִ��ģ��;�쳣����ģ��
	 * @CreateDate:2016.11.10
	 * @Version:2.0
	**/
	//[���峣��]
	boolean debug=true;                           //���Կ���(trueΪ��)
	static final String USER="root";              //MySQL���ݿ��û���
	static final String PASS="pgybdc";            //MySQL���ݿ�����
	String JDBC_DRIVER="com.mysql.jdbc.Driver";   //JDBC������ַ
	String DB_URL="jdbc:mysql://localhost";
	//[�������]
	Connection conn=null;                         //���ӱ���
	Statement stmt=null;                          //��������
	//[��ʼ��������]
	Base base=new Base();                         //��ʼ���ײ���(��)
	
	//����������
	MySQL(){
		/*
		STPLoadDriver();//����ʼ������MySQL����

		STPOpenConnection("STUDENTS");//����ʼ����������
		CreateDatabase("STUDENTS");//����ʼ���������ݿ�
		DeleteTable("REGISTRATION");//��SQL��ɾ�����
		
		SetTable("REGISTRATION");
		Item("id","INTEGER",false,true);
		Item("first","VARCHAR(255)",true,false);
		Item("last","VARCHAR(255)",true,false);
		Item("age","INTEGER",true,false);
		CreateTable();                  //��SQL���������            
		
		//InsertOneRecord("Registration","100, 'Zara', 'Ali', 18");//��SQL�������¼
		
		String[] value=new String[100];
		value[0]="100, 'Zara', 'Ali', 18";
		value[1]="101, 'Mahnaz', 'Fatma', 25";
		value[2]="102, 'Zaid', 'Khan', 30";
		value[3]="103, 'Sumit', 'Mittal', 28";
		InsertManyRecord("Registration",value);	         //��SQL�����������¼(�������������������¼����)	
		
		String[] item=new String[10];
		item[0]="Iid";                //int����
		item[1]="Sfirst";  //int����
		item[2]="Slast";// String����
		item[3]="Iage";//String����
		ShowByItem("Registration",item);
		
		SearchRecordByInt("Registration",item,"id>=101");
		SearchRecordByInt("Registration",item,"first LIKE '%za%'");
		OrderRecord("Registration",item,"first","DESC");
		ChangeRecord("Registration","age=30","id in (100, 101)");
		
		InsertOneRecord("Registration","104, 'Zar3', 'Ala', 18");
		ShowByItem("Registration",item);
		
		//DeleteRecord("Registration","id=101");//��SQL��ɾ����¼
		//DeleteDatabase("STUDENTS");//��SQL��ɾ�����ݿ�
		STPCloseConnection();//���رա��ر�����		
		*/
	}
	
	/** @Module:����ģ��	 */	
	//������������JDBC����(���سɹ�����true������ʧ�ܷ���false)
	public boolean STPLoadDriver(){
		try{
			Class.forName(JDBC_DRIVER);                                         //����JDBC��������
			Show("MySQL-JDBC�������سɹ�����");
		}
		//��JDBC����ʧ�ܡ��쳣����
		catch(ClassNotFoundException e){
			Show("MySQL-JDBC�����쳣",":��δ֪ԭ���޷�����MySQL����������JDBC��װ����");
			return false;
		}
		return true;
	}
	
	//�������������ݿ⽨������(�������ݿ����ƣ����������Ƿ񴴽������سɹ�����true������ʧ�ܷ���false)
	public boolean STPOpenConnection(String name){
		String DB_URL="jdbc:mysql://localhost";
		DB_URL=DB_URL+"/"+name;                                                 //��д����DB_URL
		try{
			conn=DriverManager.getConnection(DB_URL,USER,PASS);                 //�������ӱ���(conn)���������ݿ������
			Show("�����ݿ⽨�����ӳɹ�����");
			stmt=conn.createStatement();                                        //��������(stmt)�����
		}
		//�����������ݿ����ӡ��쳣����
		catch(SQLException e1){
			try{
				conn=DriverManager.getConnection("jdbc:mysql://localhost",USER,PASS);
				stmt=conn.createStatement();                                    //��������(stmt)�����
				//CreateDatabase(name);                                           //�������ݿ�
				try{
					conn=DriverManager.getConnection(DB_URL,USER,PASS); 
					Show("Warning 1002:���ݿ�("+name+")�����ڴ����Ѿ��ؽ����ݿ�����޸�");
					stmt=conn.createStatement();                                //��������(stmt)�����
				}
				catch(SQLException e2){
					Show("Error 1002",":�ؽ����ݿ��޸���ʽʧ�ܣ�DB_URL�����쳣��������Ϊ��"+DB_URL+"���������ݿ��Ƿ����");
					return false;
				}
			}
			catch(Exception se){
				Show("Error 1002",":MySQL�û�������������޷����������ݿ������");
				return false;
			}
		}
		return true;
	}
	
	//���رա��ر�����(�ɹ�����true��ʧ�ܷ���false)
	public boolean STPCloseConnection(){
		try{
			if(stmt!=null){
				stmt.close();
			}
			if(conn!=null){
				conn.close();
			}
		}
		catch(SQLException se){
			Show("Error 1003",":���ݿ����ӹر�ʧ��");
			return false;
		}
		Show("���ݿ����ӹرճɹ�");
		return true;
	}
	
	/** @Module:SQL��䷭���� */
	//��SQL���������ݿ�(�������ݿ����ƣ��ɹ�����true��ʧ�ܷ���false)
	public boolean CreateDatabase(String name){
		String SQL="CREATE DATABASE "+name;
		if(!SQLDo(SQL)){
			Show("Error 1011",":���ݿ�("+name+")����ʧ��");
			NameTest(name);
			return false;
		}
		return true;
	}
	
	//��SQL���������
	//[����]��񴴽���ʱ����
	String name=new String("");                   //[����]��ʱ�洢�������
	String tableSQL=new String("");               //[����]��ʱ�洢SQL���
	String primary=null;                          //[����]��ʱ�洢PRIMARY KEY���
	boolean primaryKey=false;                     //[����]��ʱ�洢�Ƿ���PRIMARY KEY���
	int number=1;                                 //[����]��ʱ�洢�ֶ�����
	//[����]������(���������ƣ�����ɹ�����true������ʧ�ܷ���false)
	public void SetTable(String _name){
		name=_name;
		tableSQL="CREATE TABLE "+name+" (";                                     //����SQL���(�������)
	}
	//[����]�������ֶ�(�����ֶ������ֶ����ͣ��Ƿ��Ϊ�գ��Ƿ�Ϊ����)
	public void Item(String name,String type,boolean empty,boolean key){
		String SQL=name;
		if(type.equals("int")||type.equals("INTEGER")){                         //ʶ�������͸�ʽ
			SQL=SQL+" INTEGER";
		}
		if(type.indexOf("String")!=-1||type.indexOf("VARCHAR")!=-1){            //ʶ���ַ�����ʽ
			SQL=SQL+" VARCHAR"+type.substring(type.indexOf("("));
		}
		if(empty==false){                                                       //ʶ���ֶβ���Ϊ�ն���
			SQL=SQL+" not NULL";
		}
		SQL=SQL+", ";
		if(key==true){                                                          //�����������
			if(primary==null){
				primary="PRIMARY KEY("+name+"))";
			}
			else{
				Show("Error 1042",":��ǰ���ж��������ܾ������������ǰ����Ϊ:"+primary);
			}
		}
		tableSQL=tableSQL+SQL;
	}
	//[����]ִ�д������
	public boolean CreateTable(){
		if(!SQLDo(tableSQL+primary)){
			Show("Error 1012",":���ݿ�("+name+")��񴴽�ʧ�ܣ�����SQL��䣺"+(tableSQL+primary));
			return false;
		}
		return true;		
	}
	
	//��SQL�����뵥����¼(�������������������¼���ݣ��ɹ�����true��ʧ�ܷ���false)
	public boolean InsertOneRecord(String name,String value){
		Show("������"+name+"�������¼("+value+")");
		String SQL="INSERT INTO "+name+" VALUES ("+value+")";                             //����SQL���(�����¼)
		if(!SQLDo(SQL)){
			Show("Error 1014",":�ڱ��("+name+")�в����¼ʧ��");
			NameTest(name);
			return false;
		}
		return true;
	}
	
	//��SQL�����������¼(�������������������¼���ݣ��ɹ�����true��ʧ�ܷ���false)
	public boolean InsertManyRecord(String name,String value[]){
		int length=base.SArrayActualL(value);                                             //[Base]�ַ�������ʵ����Ч����
		for(int i=0;i<length;i++){
			String SQL="INSERT INTO "+name+" VALUES("+value[i]+")";                       //����SQL���(�����¼)
			if(!SQLDo(SQL)){
				Show("Error 1014",":�ڱ��("+name+")�в��������¼(��"+i+"��)ʧ��");
				NameTest(name);
				return false;
			}
		}
		return true;
	}
	
	//��SQL�����ֶ�(��)ѡȡ��ʾ���ݿ�����(���������ƣ�������ʾ�ֶ�(�������Ͷ���)���ɹ�����true��ʧ�ܷ���false)
	public String ShowTable(String name,String item[]){
		int length=base.SArrayActualL(item);                                    //[Base]�ַ�������ʵ����Ч����
		Show(name+"�����Ч�ֶ�Ϊ"+length+"��");
		String SQL="SELECT ";
		for(int i=0;i<length-1;i++){
			SQL=SQL+item[i].substring(1)+", ";
		}
		SQL=SQL+item[length-1].substring(1)+" FROM "+name;
		return SQL;
	}
	
	//��SQL�����ֶ�(��)ѡȡ��ʾ���ݿ�����(���������ƣ�������ʾ�ֶ�(�������Ͷ���)�������������ɹ�����true��ʧ�ܷ���false)
	public String ShowTable(String name,String item[],String condition){
		int length=base.SArrayActualL(item);                                    //[Base]�ַ�������ʵ����Ч����
		String SQL="SELECT ";
		for(int i=0;i<length-1;i++){
			SQL=SQL+item[i].substring(1)+", ";
		}
		SQL=SQL+item[length-1].substring(1)+" FROM "+name;
		SQL=SQL+" WHERE "+condition;
		return SQL;
	}
	
	/*��SQL����¼������ʾ(���������ƣ���ʾ�ֶ�(�������Ͷ���)�����������ֶΣ������׼���ɹ�����true��ʧ�ܷ���false)
	public String OrderRecord(String name,String item[],String orderitem,String order){
		int length=base.SArrayActualL(item);                                    //[Base]�ַ�������ʵ����Ч����
		String SQL="SELECT ";
		for(int i=0;i<length-1;i++){
			SQL=SQL+item[i].substring(1)+", ";
		}
		SQL=SQL+item[length-1].substring(1)+" FROM "+name;
		SQL=SQL+" ORDER BY "+orderitem+" "+order;                               //DESC����ASC����
		return SQL;
	}*/
	
	//��SQL���޸ļ�¼(���������ƣ��޸�Ŀ�꣬�޸��������ɹ�����true��ʧ�ܷ���false)
	public boolean ChangeRecord(String name,String aim,String condition){
		String SQL="UPDATE "+name+" SET "+aim+" WHERE "+condition;
		if(!SQLDo(SQL)){
			Show("Error 1015",":�ڱ��("+name+")���޸ļ�¼ʧ��");
			NameTest(name);
			return false;
		}
		return true;
	}
	
	//��SQL��ɾ�����(����ɾ���������ƣ��ɹ�����true��ʧ�ܷ���false)
	public boolean DeleteTable(String name){
		String SQL="DROP TABLE "+name;                                                //����SQL���(ɾ�����ݿ�)
		if(!SQLDo(SQL)){
			Show("Error 1013",":���ݿ�("+name+")����ʧ��");
			NameTest(name);
			return false;
		}
		return true;
	}
	
	//��SQL��ɾ����¼(���������ƣ�ɾ��Ŀ�ꣻ�ɹ�����true��ʧ�ܷ���false)
	public boolean DeleteRecord(String name,String aim){
		String SQL="DELETE FROM "+name+" Where "+aim;
		if(!SQLDo(SQL)){
			Show("Error 1016",":�ڱ��("+name+")��ɾ����¼("+aim+")ʧ��");
			NameTest(name);
			return false;
		}
		return true;
	}
	
	//��SQL��ɾ�����ݿ�(���������ƣ��ɹ�����true��ʧ�ܷ���false)
	public boolean DeleteDatabase(String name){
		String SQL="DROP DATABASE "+name;
		if(!SQLDo(SQL)){
			Show("Error 1017",":ɾ�����ݿ�("+name+")ʧ��");
			NameTest(name);
			return false;
		}
		return true;
	}
	
	//��SQL�����ֶ�(��)ѡȡ��ʾ���ݿ�����(���������ƣ�������ʾ�ֶ�(�������Ͷ���)���ɹ�����true��ʧ�ܷ���false)
	public boolean ShowByItem(String name,String item[]){
		String SQL=new String("");
		System.out.println("�������ݿ�������ʾ��¼����");
		int length=base.SArrayActualL(item);                                              //[Base]�ַ�������ʵ����Ч����
		try{
			//����SQL���(��ȡ���ݿ��¼)
			stmt=conn.createStatement();                                                  //�������������
			SQL="SELECT ";
			for(int i=0;i<length-1;i++){
				SQL=SQL+item[i].substring(1)+", ";
			}
			SQL=SQL+item[length-1].substring(1)+" FROM "+name;
			//��ʾSQL���ִ�н��
			if(!ShowResult(SQL,length,item)){
				System.out.println("Error 1009:ShowResult����ʧ�ܣ�����SQL��䣺"+SQL);
				return false;
			}
			System.out.println("���ݿ���������ʾ�ɹ���");
		}
		catch(Exception e){
			if(DB_URL.substring(13).indexOf("/")==-1){
				System.out.println("Error 1008:��ȡ���ݿ�·�����Ϸ�����ȡʧ�ܣ�����DB_URL������("+DB_URL+")");
				return false;
			}
			System.out.println("Error 1008:����ԭ��������ݿ���������ʾʧ�ܣ�����SQL��䣺"+SQL);
			return false;
		}
		return true;
	}
	
	//��SQL�����ֶε�����(SQL���)����¼ѡȡ(��)��ʾ���ݿ�����(���������ƣ���ʾ�ֶ�(�������Ͷ���)���������������ɹ�����true��ʧ�ܷ���false)
	public boolean SearchRecordByInt(String name,String item[],String feature){
		String SQL=new String("");
		System.out.println("�������ݿ�����ѡ���ѯ��¼����");
		int length=base.SArrayActualL(item);                                              //[Base]�ַ�������ʵ����Ч����
		try{
			stmt=conn.createStatement();                                                  //�������������
			//����SQL���(��ȡ���ݿ��¼)
			SQL="SELECT ";
			for(int i=0;i<length-1;i++){
				SQL=SQL+item[i].substring(1)+", ";
			}
			SQL=SQL+item[length-1].substring(1)+" FROM "+name;
			SQL=SQL+" WHERE "+feature;
			//��ʾSQL���ִ�н��
			if(!ShowResult(SQL,length,item)){
				System.out.println("Error 1009:ShowResult����ʧ�ܣ�����SQL��䣺"+SQL);
				return false;
			}
			System.out.println("���ݿ���ѡ���ѯ��¼�ɹ���");
		}
		catch(Exception e){
			if(DB_URL.substring(13).indexOf("/")==-1){
				System.out.println("Error 1008:���ݿ�·�����Ϸ�����ȡʧ�ܣ�����DB_URL������("+DB_URL+")");
				return false;
			}
			System.out.println("Error 1008:����ԭ��������ݿ���������ʾʧ�ܣ�����SQL��䣺"+SQL);
			return false;
		}
		return true;
	}
	
	//��SQl����¼������ʾ(���������ƣ���ʾ�ֶ�(�������Ͷ���)�����������ֶΣ������׼���ɹ�����true��ʧ�ܷ���false)
	public boolean OrderRecord(String name,String item[],String orderitem,String order){
		String SQL=new String("");
		System.out.println("��ʼ���ݿ������򡭡�");
		int length=base.SArrayActualL(item);                                              //[Base]�ַ�������ʵ����Ч����
		try{
			stmt=conn.createStatement();                                                  //�������������
			//����SQL���(��ȡ���ݿ��¼)
			SQL="SELECT ";
			for(int i=0;i<length-1;i++){
				SQL=SQL+item[i].substring(1)+", ";
			}
			SQL=SQL+item[length-1].substring(1)+" FROM "+name;
			SQL=SQL+" ORDER BY "+orderitem+" "+order;                                     //DESC����ASC����
			//��ʾSQL���ִ�н��
			if(!ShowResult(SQL,length,item)){
				System.out.println("Error 1009:ShowResult����ʧ�ܣ�����SQL��䣺"+SQL);
				return false;
				}
			System.out.println("���ݿ�������ɹ���");
		}
		catch(Exception e){
			if(DB_URL.substring(13).indexOf("/")==-1){
				System.out.println("Error 1008:���ݿ�·�����Ϸ�����ȡʧ�ܣ�����DB_URL������("+DB_URL+")");
				return false;
			}
			System.out.println("Error 1008:����ԭ��������ݿ���������ʾʧ�ܣ�����SQL��䣺"+SQL);
			return false;
		}
		return true;
	}
	
	//��SQL����ʾSQL���ִ�н��(����SQL��䣬������ʾ�ֶ�����������ʾ�ֶ�(�������Ͷ���)���ɹ�����true��ʧ�ܷ���false)
	public boolean ShowResult(String SQL,int length,String item[]){
		try{
			ResultSet result=stmt.executeQuery(SQL);                                      //��ȡSQLִ�н����result����
			while(result.next()){
				//��ȡresult�����е���Ϣ����ʾ
				for(int i=0;i<length;i++){
					if(item[i].substring(0,1).equals("I")){
						int temp=result.getInt(item[i].substring(1));                     //��ȡint��ʽ�ı������
						System.out.print("INT-"+item[i].substring(1)+":"+temp+"  ");
					}
					if(item[i].substring(0,1).equals("S")){
						String temp=result.getString(item[i].substring(1));               //��ȡString��ʽ�ı������
						System.out.print("String-"+item[i].substring(1)+":"+temp+"  ");
					}
				}
				System.out.println("");
			}
			result.close();                                                               //�ر�����
		}
		catch(Exception e){
			if(DB_URL.substring(13).indexOf("/")==-1){
				System.out.println("Error 1009:·�����Ϸ�����ȡʧ�ܣ�����DB_URL������("+DB_URL+")");
				return false;
			}
			for(int i=0;i<length;i++){
				if(!item[i].substring(0,1).equals("I")&&!item[i].substring(0,1).equals("S")){
					System.out.println("Error 1009:�ֶ����Ͷ���δ��д����ʶ��");
				}
			}			
			System.out.println("Error 1009:����ԭ��������ݿ�����ʾʧ�ܣ�����SQL��䣺"+SQL);
			return false;
		}
		return true;
	}
	
	/** @Module:���ִ��ģ��  */
	//��ִ�С�SQL���(DDL:���ݶ�������)ִ��
	public boolean SQLDo(String SQL){
		Show("����ִ��(����)SQL:"+SQL);
		try{
			stmt.executeUpdate(SQL);
			return true;
		}
		catch(Exception e){
			Show("Error 1031",":SQLִ���쳣("+SQL+")");
			return false;
		}
	}
	
	//��SQL��SQL���(DML:���ݲ�������)ִ��(����SQL��䣬������ʾ�ֶ�(�������Ͷ���)���ɹ�����true��ʧ�ܷ���false)
	public boolean SQLDo(String SQL,String item[]){
		Show("����ִ��(��ʾ)SQL:"+SQL);
		int length=base.SArrayActualL(item);                                              //[Base]�ַ�������ʵ����Ч����
		try{
			ResultSet result=stmt.executeQuery(SQL);                                      //��ȡSQLִ�н����result����
			while(result.next()){
				//��ȡresult�����е���Ϣ����ʾ
				for(int i=0;i<length;i++){
					if(item[i].substring(0,1).equals("I")){
						int temp=result.getInt(item[i].substring(1));                     //��ȡint��ʽ�ı������
						System.out.print("INT-"+item[i].substring(1)+":"+temp+"  ");
					}
					if(item[i].substring(0,1).equals("S")){
						String temp=result.getString(item[i].substring(1));               //��ȡString��ʽ�ı������
						System.out.print("Str-"+item[i].substring(1)+":"+temp+"  ");
					}
				}
				System.out.println("");
			}
			result.close();                                                               //�ر�����
		}
		catch(Exception e){
			for(int i=0;i<length;i++){
				if(!item[i].substring(0,1).equals("I")&&!item[i].substring(0,1).equals("S")){
					Show("Error 1032",":��"+i+"���ֶ�����("+item[i]+")δ��д��δ��ʶ��");
				}
			}			
			Show("Error 1032",":����ԭ������ֶ����Ͷ�ȡʧ�ܣ�����SQL��䣺"+SQL);
			return false;
		}
		return true;
	}

	/** @Module:�쳣����ģ��  */
	//���쳣�������쳣�Լ��(�������֣��ϸ񷵻�true�����ϸ񷵻�false)
	private void NameTest(String name){
		if(name.indexOf("/")!=-1){                                                    //�����ڰ���"/"��ɴ���
			Show("Error 1041",":��������а����Ƿ��ַ������ɾ��ʧ�ܣ�����������("+name+")");
		}
	}
	
	//���쳣��������ʾ(�����ձ���ʾ���ݣ�������ʾ����)
	private void Show(String show,String test){
		if(debug==true){
			System.out.print(show);
			System.out.println(test);
		}
		else{
			System.out.println(show);
		}
	}
	
	//���쳣��������ʾ(���������ʾ����)
	private void Show(String test){
		if(debug==true){
			System.out.println(test);
		}
	}
}