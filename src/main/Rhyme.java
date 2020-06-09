package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Rhyme{
	/** 
	 * @ClassName:Rhyme(�ϲ�)
	 * @Description:���ϲ����д洢����
	 * @Module:����ģ��;�ӿ�ģ��;�洢��ѯ(DML)ģ��;����(DDL)ģ��
	 * @CreateDate:2016.07.12
	 * @Version:1.0
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
	int cat=1;                                    //�ϲ����ࣺ1Ϊƽˮ�ϣ�2Ϊ�������ϣ�3Ϊ��ԭ����
	String[] itemW=new String[5];                 //ƽˮ�ϴ洢���ֶνṹ
	
	//[��ʼ��������]
	MySQL mySQL=new MySQL();                      //MySQL������(��)
	Base base=new Base();                         //��ʼ���ײ���(��)
	InnerData data=new InnerData();               //�������ݿ��ʼ��(��)
	
	//����������
	Rhyme(){}
	
	/** @Module:����ģ��	 */
	/*������������MySQL���ݿ�����(�ɹ�����true��ʧ�ܷ���false)
	public boolean DataConnect(){
		if(!mySQL.STPLoadDriver()){                                             //MySQL-����MySQL����
			System.out.println("Error 2101:Rhyme���ݿ�JDBC���������쳣");
			return false;
		}
		String path="Rhyme";
		if(!mySQL.STPOpenConnection(path)){                                     //MySQL-��Antithesis���ݿ⽨������
			System.out.println("Error 2101:���ݿ������쳣");
			return false;
		}
		return true;
	}*/
	
	/** @Module:�ӿ�ģ��	 */
	//���ӿڡ���ȡ�û���������
	public boolean IFSReadOrder(String order){
		Showln("�ϲ�����ģ��ӵ������"+order);
		String sign=order.substring(0,1);                                       //��ȡ�����ʶ��
		if(sign.equals("A")){                                                   //�ж��Ƿ�Ϊ���ݿ����ģʽ
			int rhn=base.CharToFigure(order.substring(1,order.indexOf(":")));   //��ȡ�����е��ϲ���
			String chars=order.substring((order.indexOf(":"))+1);               //��ȡ�����еĺ���
			IFSAddRhyme(chars,rhn);                                             //Rhyme-IFS-���ݿ��������
		}
		if(sign.equals("W")){
			DMLShow();
		}
		if(sign.equals("S")){
			int rhn[]=DMLSearch(order.substring(1));
			for(int i=0;i<3;i++){
				if(rhn[i]!=-1){
					Showln("'"+order.substring(1)+"'����"+GetRhnName(rhn[i])+"("+rhn[0]+")��");
				}
			}
		}
		return true;
	}
	
	//���ӿڡ����ݿ��������(�����ַ��������ϲ���)
	public boolean IFSAddRhyme(String chars,int rhn){
		while(chars!=null){
			DMLInsert(chars.substring(0,1),rhn);                                //Rhyme-DML-���ϲ������һ����¼
			if(chars.length()>1){
				chars=chars.substring(1);
			}
			else{
				break;
			}
		}		
		return true;
	}	
	
	/** @Module:�洢(DML)ģ��	 */
	//��DML���ϲ������һ����¼(�����ַ����ϲ��ţ��ɹ�����true��ʧ�ܷ���false)
	public boolean DMLInsert(String chars,int rhn){
		//�������ַ��Ƿ��Ѿ������ݿ��г���
		String SQL=
				"SELECT chars, rhn1, rhn2, rhn3" +
				" FROM "+GetTableName()+
				" WHERE chars='"+chars+"'";                                     //[SQL]��ѯ�����ַ�(��ȡ������)
		Showln("����ִ��SQL(SELECT)��䣺"+SQL);
		try{
			ResultSet result=stst.executeQuery(SQL);                            //��ȡSQLִ�н����result����
			//���㵱ǰresult�еļ�¼��
			int rowCount=0;
			try{
				result.last();                                                  //��ָ���ƶ������һ����¼
				rowCount=result.getRow();
			}
			catch(SQLException e){
				Showln("��ȡresult��¼��ʧ��");
			}
			result.first();
			//�����ݿ���û�и��ַ�ʱ�������ַ��ļ�¼
			if(rowCount==0){
				SQL="INSERT INTO "+GetTableName()+
					" VALUES ('"+chars+"', "+rhn+", -1, -1)";                   //[SQL]���뵱ǰ�ַ����ϲ���
				Showln("����ִ��SQL(INSERT)��䣺"+SQL);
				stit.executeUpdate(SQL);
			}
			//�����ݿ����Ѿ��и��ַ�ʱ���ϲ����ѱ���¼
			if(rowCount==1){
				boolean dup=false;                                              //����Ƿ��ظ�����
				int dbrhn[]=new int[3];                                         //��ʱ�洢�ϲ��ű���
				dbrhn[0]=result.getInt("rhn1");
				dbrhn[1]=result.getInt("rhn2");
				dbrhn[2]=result.getInt("rhn3");
				for(int i=0;i<3;i++){
					if(dbrhn[i]==rhn){
						dup=true;
					}
				}
				if(dup==true){                                                  //�����ݿ����Ѿ���¼���ϲ���
					Showln("��ǰ�ַ����ϲ��ѱ���¼��������¼");
				}
				else{                                                           //�����ݿ���û�м�¼���ϲ���
					for(int i=0;i<3;i++){
						if(dbrhn[i]==0){
							SQL="UPDATE "+GetTableName()+
								" SET rhn"+(i+1)+"='"+rhn+"'"+
								" WHERE chars='"+chars+"'";                     //[SQL]��д�ϲ����ڸ�����¼�п��ϲ���λ��
							Showln("����ִ��SQL(INSERT)��䣺"+SQL);
							stit.executeUpdate(SQL);
							break;
						}
						//�����ϲ���λ�ö��Ѿ�����ʱ���Զ�����ѭ��
					}
				}
			}
			//�����ݿ��и����ݴ����쳣
			if(rowCount>1||rowCount<0){
				SQL="DELETE FROM "+GetTableName()+
					" WHERE chars='"+chars+"'";                                 //ɾ�������쳣��¼
				stit.executeUpdate(SQL);
			}
			result.close();
			return true;
		}
		catch(SQLException e){
			Showln("��"+GetTableName()+"�ϲ����в����ַ�:"+chars+"("+rhn+")ʧ�ܣ�");
			return false;
		}
	}
	
	//��DML�����ϲ��������ַ����ϲ���(�����ַ����ϲ��ţ��ɹ�����true��ʧ�ܷ���false)
	public int[] DMLSearch(String chars){
		int rhn[]=new int[3];
		//�������ַ��Ƿ��Ѿ������ݿ��г���
		String SQL=
				"SELECT chars, rhn1, rhn2, rhn3" +
				" FROM "+GetTableName()+
				" WHERE chars='"+chars+"'";                                     //[SQL]��ѯ�����ַ�(��ȡ������)
		Showln("����ִ��SQL(SELECT)��䣺"+SQL);
		try{
			ResultSet result=stst.executeQuery(SQL);                            //��ȡSQLִ�н����result����
			//���㵱ǰresult�еļ�¼��
			int rowCount=0;
			try{
				result.last();                                                  //��ָ���ƶ������һ����¼
				rowCount=result.getRow();
			}
			catch(SQLException e){
				Showln("��ȡresult��¼��ʧ��");
			}
			result.first();
			//�����ݿ���û�и��ַ�ʱ�������ַ��ļ�¼
			if(rowCount==0){
				rhn[0]=-1;
				rhn[1]=-1;
				rhn[2]=-1;
				Showln("û��������'"+chars+"'���ϲ�");
			}
			//�����ݿ����Ѿ��и��ַ�ʱ���ϲ����ѱ���¼
			if(rowCount==1){
				rhn[0]=result.getInt("rhn1");
				rhn[1]=result.getInt("rhn2");
				rhn[2]=result.getInt("rhn3");
			}
		}
		catch(SQLException e){
			Showln("��"+GetTableName()+"�ϲ����������ַ�'"+chars+"'ʧ�ܣ�");
			rhn[0]=-1;
			rhn[1]=-1;
			rhn[2]=-1;
		}
		return rhn;
	}
	
	//��DML����ʾ��ǰ�ϲ�������������
	public boolean DMLShow(){
		String SQL=
				"SELECT chars, rhn1, rhn2, rhn3" +
				" FROM "+GetTableName();                                        //[SQL]��ʾ��ǰ�ϲ����ȫ������
		Show("����ִ��SQL(SELECT)��䣺"+SQL+"����");
		try{
			ResultSet result=stst.executeQuery(SQL);
			Showln("\n****"+GetTableName()+"�ϲ��������****");
			while(result.next()){
				Show("�ַ���"+result.getString("chars")+"  ");
				Show("�ϲ�1��"+result.getString("rhn1")+"  ");
				Show("�ϲ�2��"+result.getString("rhn2")+"  ");
				Showln("�ϲ�3��"+result.getString("rhn3"));
			}
			result.close();
			return true;
		}
		catch(SQLException e){
			Showln("��ʾ��ǰ�ϲ����ȫ������ʧ�ܣ�");
			return false;
		}
	}
	
	/** @Module:����(DDL)ģ��	 */
	//��DDL�������ݿ⽨������
	public boolean DDLConn(){
		try{
			conn=DriverManager.getConnection(DB_DML,USER,PASS);                 //�������ӱ���(conn)���������ݿ������
			Showln("�����ݿ⽨�����ӳɹ�����");
			stst=conn.createStatement();                                        //��������(stst)�����
			stit=conn.createStatement();
		}
		//�����������ݿ����ӡ��쳣����
		catch(SQLException e1){
			if(DDLCreateDataBase()){
				try{
					conn=DriverManager.getConnection(DB_DML,USER,PASS);         //�������ӱ���(conn)���������ݿ������
					Showln("�����ݿ⽨�����ӳɹ���");
					stst=conn.createStatement();                                //��������(stst)�����
					stit=conn.createStatement();
				}
				catch(SQLException e2){
					return false;
				}
			}
			return false;
		}
		return true;
	}
	
	//��DDL���ر�����(�ɹ�����true��ʧ�ܷ���false)
	public boolean STPCloseConnection(){
		try{
			if(stst!=null){
				stst.close();
			}
			if(stit!=null){
				stit.close();
			}
			if(conn!=null){
				conn.close();
			}
			Showln("�ϲ����ݿ����ӹرճɹ�����");
			return true;
		}
		catch(Exception e){
			Showln("���ݿ����ӹر�ʧ�ܣ�");
			return false;
		}
	}
	
	//��DDL�������ϲ����ݿ�
	public boolean DDLCreateDataBase(){
		String SQL="CREATE DATABASE Rhyme";
		Show("����ִ��SQL(CREATE)��䣺"+SQL+"����");
		try{
			conn=DriverManager.getConnection(DB_DDL,USER,PASS);                 //�������ӱ���(conn)���������ݿ������
			stst=conn.createStatement();
			stst.executeUpdate(SQL);
			Showln("���ݿ�Rhyme�����ɹ�����");
		}
		catch(SQLException se){
			Showln("���ݿ�Rhyme����ʧ�ܣ�");
		}
		return true;
	}
	
	//��DDL�������ϲ����
	public boolean DDLCreateTable(){
		String SQL=
				"CREATE TABLE "+GetTableName()+" "+
				"(chars VARCHAR(4) not NULL, " +
				"rhn1 INTEGER, " +
				"rhn2 INTEGER, " +
				"rhn3 INTEGER, " +
				"PRIMARY KEY (chars))";
		Show("����ִ��SQL(CREATE)��䣺"+SQL+"����");
		try{
			stst.executeUpdate(SQL);
			Showln("����"+GetTableName()+"���ɹ�����");
			return true;
		}
		catch(SQLException se){
			Showln("����"+GetTableName()+"���ʧ�ܣ�");
			return false;
		}
	}
	
	//��DDL��ɾ���ϲ����
	public boolean DDLDropTable(){
		String SQL=
				"DROP TABLE "+GetTableName();
		Show("����ִ��SQL(DROP)��䣺"+SQL+"����");
		try{
			stst.executeUpdate(SQL);
			Showln("ɾ��"+GetTableName()+"���ɹ�����");
			return true;
		}
		catch(SQLException se){
			Showln("ɾ��"+GetTableName()+"���ʧ�ܣ�");
			return false;
		}
	}

	//��Base����ȡ��ǰ�ϲ����ͱ������(�����ϲ���)
	public String GetRhnName(int rhn){
		String path=base.FileOReadNowPath();
		FileIDo fileIDo=new FileIDo(path+"\\data\\rhnName.dat");                //��ʼ���ļ��ڲ�����(��)
		String back=fileIDo.Read(0,8);
		back=back.replace("��","");
		return back;
	}
	
	//��Base����ȡ��ǰ�ϲ����ͱ������
	private String GetTableName(){
		String name=new String("");
		if(cat==1){
			name="Water";
		}
		return name;
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
	
	
	
	
	
	//���������������ϲ�������(�ϲ�����)
	Rhyme(int _cat){
		itemW[0]="Schars";               //int����
		itemW[1]="Iwater1";              //Int����
		itemW[2]="Iwater2";              //Int����
		itemW[3]="Iwater2";              //int����
		if(_cat==1||_cat==2||_cat==3){
			cat=_cat;
		}
		else{
			System.out.println("�ϲ������������ϲ��������ת��Ĭ�ϣ�ƽˮ��");
			cat=1;
		}
	}
	
	//����ѯ����ѯ�������
	public int RhymeSearchCode(String character){
		int code=-1;
		switch(cat){
			case 0:code=-1;break;
			case 1:code=data.DataWaterChar(character);
			case 2:break;
			case 3:break;
			default:break;
		}
		return code;
	}
	
	//����ѯ�������ѯ�������
	public String RhymeSearchName(int _code){
		int code=_code;
		String rhymeName=new String("");
		if(cat==1){
			if(code<0||code>105){
				System.out.println("�ϲ�������Ч");
			}
			else{
				rhymeName=data.DataWaterCode(code);
			}
		}
		return rhymeName;
	}
	
	//����ѯ�������ѯƽ�����
	public int RhymeSearchTone(int _code){
		int code=_code;
		int tone=0;                                                   //Toneƽ�Ʊ�����0Ϊδ֪��1Ϊƽ����2Ϊ����
		if(cat==1){
			if(code<0||code>105){
				System.out.println("�ϲ�������Ч");
			}
			else{
				if(code<31){
					tone=1;
				}
				else{
					tone=2;
				}
			}
		}
		return tone;
	}
}