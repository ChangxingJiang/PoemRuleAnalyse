package main;

public class Poem {
	public static void main(String[] args) {
		//����Ԥ����
		LoadJDBCDriver();                                                       //JDBC��������(MySQL���ݿ�ʹ��֧��)
		CheckDat();
		//����������
		System.out.println("��ӭ�򿪻���ʫ��");
		News news=new News();
		news.AddMouse();
		//Antithesis antithesis=new Antithesis();
		//antithesis.DataConnect();                                             //[Antithesis]����MySQL���ݿ�����
		Rhyme rhyme=new Rhyme();                                                //��ʼ���ϲ���(�����)
		//rhyme.DataConnect();                                                  //[Antithesis]����MySQL���ݿ�����
		rhyme.DDLConn();
		//rhyme.DDLDropTable();
		rhyme.DDLCreateTable();
		//rhyme.DMLInsert("��",1);
		//rhyme.DMLShow();
		rhyme.STPCloseConnection();
	}
	
	//JDBC��������(MySQL���ݿ�ʹ��֧��)
	public static void LoadJDBCDriver(){
		String JDBC_DRIVER="com.mysql.jdbc.Driver";                             //JDBC������ַ
		try{
			Class.forName(JDBC_DRIVER);                                         //����JDBC��������
			System.out.println("JDBC�������سɹ�����");
		}
		catch(ClassNotFoundException e){
			System.out.println("JDBC��������ʧ�ܣ����ݿ⹦�ܽ�ʧЧ��");
		}
	}
	
	//���������ݿ�����(PoemDataģ�顢Rhymeģ��֧��)
	public static boolean CheckDat(){
		FileODo fileODo=new FileODo();                                          //��ʼ���ļ��������(��)
		Base base=new Base();                                                   //��ʼ���ײ���(��)
		String path=base.FileOReadNowPath();
		if(!fileODo.Exist(path+"\\data\\rhnName.dat")){
			System.out.println("rhnName.dat��ʧ���ϲ����Ʋ�ѯ����ʧЧ��");
			return false;
		}
		if(!fileODo.Exist(path+"\\data\\Poem.dat")||!fileODo.Exist(path+"\\data\\Index.dat")){
			System.out.println("Poem.dat��Index.dat�ļ���ʧ��ʫ�⹦��ʧЧ��");
			return false;
		}
		System.out.println("�������ݿ���ͨ������");
		return true;
	}
}