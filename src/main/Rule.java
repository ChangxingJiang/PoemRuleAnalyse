package main;

class Rule{
	//��������д����
	String path=new String("");                   //record�ļ���ַ
	String sen[]=new String[20];                  //ʫ�����
	int extent[]=new int[20];                     //ʫ�䳤��
	int line=0;                                   //ʫ������
	long length=0,index=0;                        //�ļ����ȣ��ļ���������(���ɿ�ʼ)
	
	//���㲿����д����
	int rhyme[][]=new int[8][7];                  //ʫ�䵥���ϲ����
	int tone[][]=new int[8][7];                   //ʫ�䵥��ƽ�ƣ�0Ϊδ֪��1Ϊƽ����2Ϊ����
	int toneall[]=new int[8];                     //ʫ��ȫ��ƽ��
	
	//����������д����
	int stand[]=new int[8];                       //(standard)�Ƿ�����0Ϊ������11ƽƽ�ţ�12ƽ�ƽţ�21��ƽ�ţ�22���ƽ�
	int change[]=new int[8];                      //�Ƿ���0Ϊ�ޱ仯��11ƽƽ�ű��12ƽ�ƽű��21��ƽ�ű��22���ƽű��
	boolean sticky[]=new boolean[8];              //��������ʧ��ʧ�һ�ɼ�¼��ʧ�ĺ�һ�䣩����ȷΪtrue������Ϊfalse
	int endtone[]=new int[8];                     //ÿ��ĩβƽ��(11ΪӦƽʵƽ��12ΪӦƽʵ�ƣ�21ΪӦ��ʵƽ��22ΪӦ��ʵ��)
	int same[]=new int[8];                        //��������ƽ��״̬��0��ȷ��1���Զ�����ƽ����ͬ��2���Զ�����ƽ����ͬ��3����������ƽ����ͬ
	int wrong[]=new int[8];                       //�Ƿ��־ȣ�0Ϊ�������־ȣ�21Ϊ��ƽ�־�
												  //121Ϊƽ�ƽ������������оȣ�122Ϊƽ�ƽ�����������û��
												  //123Ϊƽ�ƽŵ������������оȣ�124Ϊƽ�ƽŵ�����������û��
												  //221Ϊ���ƽ������ֶԵ����оȣ�222Ϊ���ƽ������ֶԵ���δ��
	boolean alone[]=new boolean[8];               //�Ƿ��ƽ�����ǹ�ƽ����false���ǹ�ƽ����true
	
	//[��ʼ��������]
	Base base=new Base();                         //��ʼ���ײ���
	FileODo fileODo=new FileODo();                //��ʼ���ļ��������(��)
	Rhyme Search=new Rhyme(1);                    //��ʼ���ϲ�(ƽˮ��)��
	
	Rule(){}//����������
	
	//�����롿��ȡrecord�ļ�(��ȡrecord�ļ��е�ʫ����Ϣ)
	public void ReadFromPath(String _path){
		path=_path;
		FileIDo fileIDo=new FileIDo(path);                            //��ʼ���ļ��ڲ�����(��)
		String temp=new String("");
		temp=fileIDo.Read(10,3);                                   //��record�ļ���ȡʫ������(����)
		line=base.CharToFigure(temp);
		temp=fileIDo.Read(42,5);                                   //��record�ļ���ȡ�ļ�����
		length=base.CharToFigure(temp);
		index=length;
		for(int i=0;i<line;i++){                                      //��record�ļ���ȡʫ������
			temp=fileIDo.Read(100+20*i,20);
			temp=temp.replace(" ","");                                //ɾ����ȡ�����еĿո񲿷�
			sen[i]=temp;
		}
		InitializeVariable();
	}
	
	//�����롿��ȡ�ַ���(��ȡrecord�ļ��е�ʫ����Ϣ)
	public void ReadFromString(String poem){
		poem=poem.replace(" ","");
		poem=poem.replace("\r\n","");
		int ico=0,ipo=0,safe=0;
		while(ico!=-1||ipo!=-1){
			ico=poem.indexOf("��");                                   //��temp������"��",>=0Ϊ����,=-1Ϊ������
			ipo=poem.indexOf("��");                                   //��temp������"��",>=0Ϊ����,=-1Ϊ������
			if(ipo==-1&&ico!=-1){
				sen[line]=poem.substring(0,ico);                      //��ȡʫ��
				poem=poem.substring((ico+1));                         //��ȡʣ�ಿ��
				line++;
			}
			if(ico==-1&&ipo!=-1){
				sen[line]=poem.substring(0,ipo);                      //��ȡʫ��
				poem=poem.substring((ipo+1));                         //��ȡʣ�ಿ��
				line++;
			}
			if(ipo!=-1&&ipo<ico){
				sen[line]=poem.substring(0,ipo);                      //��ȡʫ��
				poem=poem.substring((ipo+1));                         //��ȡʣ�ಿ��
				line++;
			}
			if(ico!=-1&&ico<ipo){
				sen[line]=poem.substring(0,ico);                      //��ȡʫ��
				poem=poem.substring((ico+1));                         //��ȡʣ�ಿ��
				line++;
			}
			if(ico==-1&&ipo==-1){
				break;
			}
			safe++;                                                   //[ѭ����ȫ����]
			if(safe>1000){break;}
		}
		for(int i=0;i<line;i++){                                      //����ʫ�䳤��
			System.out.println(i+"="+sen[i]);
		}
		InitializeVariable();
		
	}
	
	//�����ߡ���ʼ������
	public void InitializeVariable(){
		for(int i=0;i<line;i++){                                      //����ʫ�䳤��
			extent[i]=sen[i].length();
		}
		for(int i=0;i<8;i++){
			toneall[i]=0;
			sticky[i]=true;
			endtone[i]=0;
			stand[i]=0;
			change[i]=0;
			same[i]=0;
		}
		for(int i=0;i<8;i++){
			for(int j=0;j<7;j++){
				rhyme[i][j]=0;
				tone[i][j]=0;
			}
		}
	}
	
	
	//��������ʫ����ɷ����������˿�(����ʫ���ϲ���ƽ�����ݼ�����״��)
	public String RuleEntrance(){
		boolean inspect=AnalyseDemand();          //����ʫ���Ƿ���ϸ��ɷ�����������
		String poem[][]=new String[20][10];       //ʫ�䵥��
		String temp=new String("");
		temp="";
		if(inspect==true){                                            //ʫ����ϸ��ɷ�����������
			//[���㲿��]
			for(int i=0;i<line;i++){                                  //����ʫ�䵥��
				for(int j=0;j<extent[i];j++){
					poem[i][j]=String.valueOf(sen[i].charAt(j));      //��ȡ��i+1�е�j+1����
				}
			}
			for(int i=0;i<line;i++){                                  //ʫ���ϲ���ż���
				for(int j=0;j<extent[i];j++){
					rhyme[i][j]=Search.RhymeSearchCode(poem[i][j]);   //��ѯ��ǰ�ֵ��ϲ�����
				}
			}
			for(int i=0;i<line;i++){                                  //ʫ��ƽ�Ƽ���		
				for(int j=0;j<extent[i];j++){
					tone[i][j]=Search.RhymeSearchTone(rhyme[i][j]);   //��ѯ��ǰ�����ƽ��
					temp=temp+Integer.toString(tone[i][j],10);
				}
				toneall[i]=base.CharToFigure(temp);
				temp="";
			}
			//[��������]
			AnalyseStricky();                                         //����ȫʫ���
			for(int i=0;i<line;i++){
				AnalyseEndTone(i);                                    //��������ĩβƽ��
				stand[i]=AnalyseStandard(i);                          //��������ʫ��������
				change[i]=AnalyseChange(i);                           //��������ʫ������
				same[i]=AnalyseSame(i);                               //��������ʫ��������ƽ��״̬
				wrong[i]=AnalyseWrong(i);                             //��������ʫ�־�
				alone[i]=AnalyseAlone(i);                             //������ƽ(�����۵�)
			}
			//[���۲���]
			temp=SayWholeRule();
			for(int i=0;i<line;i++){               //�����������
				temp=temp+Saysentence(i);
				if(i<(line-1)){temp=temp+"��";}
				else{temp=temp+"��";}
			}
			temp=temp+SayEndTone();   //����ȫʫ��βƽ��
			temp=temp+SayStricky();    //����ȫʫ������
			System.out.println(temp);
		}
		return temp;
	}
	
	//������������ʫ���Ƿ���ϸ��ɷ�����������(��ʫ����ϸ��ɷ�������������ȫ���ġ�����ʫ�������������棻��֮Ϊ��)
	private boolean AnalyseDemand(){
		boolean inspect=true;
		for(int i=0;i<line;i++){                                      //����ʫ���Ƿ����ȫ���ĸ�ʽ
			sen[i]=sen[i].replace(" ","");
			if(sen[i].getBytes().length==2*sen[i].length()){}         //���sen[i]�Ƿ�Ϊȫ����
			else{
				System.out.println("��"+i+"���з���������");
				inspect=false;
			}
		}
		if(line!=4&&line!=8){                                         //����ʫ���Ƿ�Ϊ�ľ��˾�
			inspect=false;
		}
		if(extent[0]!=5&&extent[0]!=7){                               //����ʫ���һ���Ƿ������Ի�����
			inspect=false;
		}
		for(int i=0;i<line-1;i++){                                    //����ÿ��ʫ�������Ƿ���ͬ
			if(extent[i]!=extent[i+1]){
				inspect=false;
			}
		}
		return inspect;
	}
	
	//������������ȫʫ������(����ȫʫ��������ʧ��ʧ�һ�ɼ�¼��ʧ��ʧ�ĺ�һ�䣻[�ƻ�]�ϲ����ԡ����ԣ���������ٶ�)
	public void AnalyseStricky(){
		int time=line/2;                                              //���������Ĵ���
		if(extent[0]==5){                                             //����ģʽ��������
			for(int i=0;i<time;i++){  
				if(tone[2*i][1]!=tone[2*i+1][1]&tone[2*i][3]!=tone[2*i+1][3]){
					sticky[2*i+1]=true;                                 //��2*i���2*i+1���
				}
				else{
					sticky[2*i+1]=false;
				}
			}
		}
		if(extent[0]==7){   //����ģʽ��������
			for(int i=0;i<time;i++){  
				if(tone[2*i][1]!=tone[2*i+1][1]&tone[2*i][3]!=tone[2*i+1][3]&tone[2*i][5]!=tone[2*i+1][5]){
					sticky[2*i+1]=true;                                 //��2*i���2*i+1���
				}
				else{
					sticky[2*i+1]=false;
				}
			}
		}
		if(extent[0]==5){   //����ģʽ�������
			for(int i=0;i<time-1;i++){  
				if(tone[2*i+1][1]==tone[2*i+2][1]&tone[2*i+1][3]==tone[2*i+2][3]){
					sticky[2*i+2]=true;                               //��2*i���2*i+1���
				}
				else{
					sticky[2*i+2]=false;
				}
			}
		}
		if(extent[0]==7){   //����ģʽ�������
			for(int i=0;i<time-1;i++){  
				if(tone[2*i+1][1]==tone[2*i+2][1]&tone[2*i+1][3]==tone[2*i+2][3]&tone[2*i+1][5]==tone[2*i+2][5]){
					sticky[2*i+2]=true;                               //��2*i���2*i+1���
				}
				else{
					sticky[2*i+2]=false;
				}
			}
		}	
	}
	
	//����������������ĩβƽ��(������l���βƽ���Ƿ���ȷ������ӦΪ�������Ծ�ӦΪƽ����δ�����׾����������)
	private void AnalyseEndTone(int l){
		if(l%2==0){                                                   //lΪż��������Ӧ��
			if(tone[l][extent[0]-1]==2){
				endtone[l]=22;                                        //Ӧ��Ϊ��
			}
			else{
				if(l==0){                                             //������׾�
					endtone[l]=11;
				}
				else{
					endtone[l]=21;                                    //������׾䣺Ӧ��Ϊƽ
				}
			}
		}
		if(l%2==1){                                                   //lΪ�������Ծ�Ӧƽ
			if(tone[l][extent[0]-1]==1){
				endtone[l]=11;                                        //Ӧ��Ϊ��
			}
			else{
				endtone[l]=12;                                        //Ӧ��Ϊƽ
			}
		}
	}	
	
	//����������������ʫ��������(������l���Ƿ�Ϊ���������񷵻������ţ��������񷵻�-1)
	private int AnalyseStandard(int l){
		int stand=-1;
		if(extent[0]==5){                                             //�������
			if(tone[l][1]==2&tone[l][2]==1&tone[l][3]==1&tone[l][4]==2){stand=12;}//(��)��ƽƽ��
			if(tone[l][1]==1&tone[l][2]==1&tone[l][3]==2&tone[l][4]==2){stand=22;}//(ƽ)ƽƽ����
			if(tone[l][1]==2&tone[l][2]==2&tone[l][3]==1&tone[l][4]==1){stand=11;}//(��)����ƽƽ
			if(tone[l][0]==1&tone[l][1]==1&tone[l][2]==2&tone[l][3]==2&tone[l][4]==1){stand=21;}//ƽƽ����ƽ
		}
		if(extent[0]==7){                                             //�������
			if(tone[l][1]==1&tone[l][3]==2&tone[l][4]==1&tone[l][5]==1&tone[l][6]==2){stand=12;}//(ƽ)ƽ(��)��ƽƽ��
			if(tone[l][1]==2&tone[l][3]==1&tone[l][4]==1&tone[l][5]==2&tone[l][6]==2){stand=22;}//(��)��(ƽ)ƽƽ����
			if(tone[l][1]==1&tone[l][3]==2&tone[l][4]==2&tone[l][5]==1&tone[l][6]==1){stand=11;}//(ƽ)ƽ(��)����ƽƽ
			if(tone[l][1]==2&tone[l][2]==1&tone[l][3]==1&tone[l][4]==2&tone[l][5]==2&tone[l][6]==1){stand=21;}//(��)��ƽƽ����ƽ
		}
		return stand;
	}
	
	//����������������ʫ������(������l���Ƿ�Ϊ��ͨ����Ǳ�񷵻������ţ����Ǳ�񷵻�-1)
	private int AnalyseChange(int l){
		int change=-1;
		if(extent[0]==5){                                             //�������
			if(tone[l][1]==2&tone[l][2]==2&tone[l][3]==1&tone[l][4]==2){change=12;}//(��)����ƽ��
			if(tone[l][0]==1&tone[l][1]==1&tone[l][2]==2&tone[l][3]==2&tone[l][4]==2){change=22;}//ƽƽ������
			if(tone[l][1]==2&tone[l][2]==1&tone[l][3]==1&tone[l][4]==1){change=11;}//(��)��ƽƽƽ
			if(tone[l][0]==1&tone[l][1]==1&tone[l][2]==1&tone[l][3]==2&tone[l][4]==1){change=21;}//ƽƽƽ��ƽ
		}
		if(extent[0]==7){                                             //�������
			if(tone[l][1]==1&tone[l][3]==2&tone[l][4]==2&tone[l][5]==1&tone[l][6]==2){change=12;}//(ƽ)ƽ(��)����ƽ��
			if(tone[l][1]==2&tone[l][2]==1&tone[l][3]==1&tone[l][4]==2&tone[l][5]==2&tone[l][6]==2){change=22;}//(��)��ƽƽ������
			if(tone[l][1]==1&tone[l][3]==2&tone[l][4]==1&tone[l][5]==1&tone[l][6]==1){change=11;}//(ƽ)ƽ(��)��ƽƽƽ
			if(tone[l][1]==2&tone[l][2]==1&tone[l][3]==1&tone[l][4]==1&tone[l][5]==2&tone[l][6]==1){change=21;}//(��)��ƽƽƽ��ƽ
		}
		return change;
	}
	
	//����������������ʫ�����������ƽ��״̬(������l���������ƽ��״̬������ƽ���Ƿ���ͬ������û�������򷵻�0)
	private int AnalyseSame(int l){
		int same=0;
		if(extent[0]==5){                                             //�������
			if(tone[l][1]==tone[l][3]){same=1;}                       //���Եڶ��ֵ�����ƽ����ͬ			
		}
		if(extent[0]==7){                                             //�������
			if(tone[l][1]==tone[l][3]){same=2;}                       //���Եڶ��ֵ�����ƽ����ͬ
			if(tone[l][3]==tone[l][5]){same=3;}                       //���Ե����ֵ�����ƽ����ͬ
		}
		return same;
	}
	
	//����������������ʫ�����־�(������l���־��������û���־��򷵻�0)
	private int AnalyseWrong(int l){
		int wrong=0;
		if(extent[0]==5){                                             //�������
			if(tone[l][0]==1&tone[l][1]==1&tone[l][2]==2&tone[l][3]==1&tone[l][4]==2){//���ƽ������ֶԵ�
				if(tone[l][1]==2&tone[l][2]==2&tone[l][3]==1&tone[l][4]==1){//�Ծ��о�
					wrong=221;
				}
				else{wrong=222;}                                      //�Ծ�δ��
			}
			if(tone[l][1]==2&tone[l][3]==2&tone[l][4]==2){            //ƽ�ƽ���������
				if(tone[l][2]==1){                                    //�Ծ��о�
					wrong=121;
				}
				else{wrong=122;}                                      //�Ծ�δ��
			}
			if(tone[l][1]==2&tone[l][2]==2&tone[l][3]==1&tone[l][4]==2){//ƽ�ƽŵ���������
				if(tone[l][2]==1){                                    //�Ծ��о�
					wrong=123;
				}
				else{wrong=124;}                                      //�Ծ�δ��
			}
			if(tone[l][0]==2&tone[l][1]==1&tone[l][2]==1&tone[l][3]==2&tone[l][4]==1){//��ƽ�־�
				wrong=21;
			}
		}
		if(extent[0]==7){                                             //�������
			if(tone[l][1]==2&tone[l][2]==1&tone[l][3]==1&tone[l][4]==2&tone[l][5]==1&tone[l][6]==2){//���ƽ������ֶԵ�
				if(tone[l][1]==1&tone[l][3]==2&tone[l][4]==2&tone[l][5]==1&tone[l][6]==1){//�Ծ��о�
					wrong=221;
				}
				else{wrong=222;}                                      //�Ծ�δ��
			}
			if(tone[l][1]==1&tone[l][3]==2&tone[l][5]==2&tone[l][6]==2){//ƽ�ƽ���������
				if(tone[l][4]==1){                                    //�Ծ��о�
					wrong=121;
				}
				else{wrong=122;}                                      //�Ծ�δ��
			}
			if(tone[l][1]==1&tone[l][3]==2&tone[l][4]==2&tone[l][5]==1&tone[l][6]==2){//ƽ�ƽŵ���������
				if(tone[l][4]==1){                                    //�Ծ��о�
					wrong=123;
				}
				else{wrong=124;}                                      //�Ծ�δ��
			}
			if(tone[l][1]==2&tone[l][2]==2&tone[l][3]==1&tone[l][4]==1&tone[l][5]==2&tone[l][6]==1){//��ƽ�־�
				wrong=21;
			}
		}
		return wrong;
	}
	
	//��������������ƽ(�����۵�)
	//�����۵㣺�����Ծ��У�������ֻ��һ��ƽ���֣��Ҳ��������ڣ������Ծ��У�����������һ��������ƽ���֣�������ƽ���ֲ����ڡ�
	//˵����ʹ�������۵㣬�����Ƿ�Ϊ��ƽ�������ǹ�ƽ����false���ǹ�ƽ����true
	private boolean AnalyseAlone(int l){
		boolean alone=false;
		if(extent[0]==5){                                             //�������
			int j=0;
			for(int i=0;i<4;i++){                                     //ͳ������ʫ�г��Ͻ����ж��ٸ�ƽ����
				if(tone[l][i]==1){
					j++;
				}
			}
			if(j==1&tone[l][3]==2){                                   //����ƽ����λ��
				alone=true;
			}
		}
		if(extent[0]==7){                                             //�������
			int j=0,index=0;
			for(int i=0;i<6;i++){                                     //ͳ������ʫ�г��Ͻ����ж��ٸ�ƽ����
				if(tone[l][i]==1){
					j++;
					index=i;
				}
			}
			if(j==1|j==2){                                            //��һ��������ƽ����
				if(tone[l][5]==2&tone[l][index-1]==2){                //�ж������Ƿ�����
					alone=true;
				}
			}
		}
		return alone;
	}
	
	//�����ۡ������Ը��ɵ���������(�Ը���ʫ�������Ȩ�����ۣ���������)
	private String SayWholeRule(){
		String temp=new String("");
		int mark=0;   //��������
		int marksta=0,markc=0,marka=0;  //�������������������ƽ����
		int markw=0,markww=0;    //�־�Ȼظ������־�û�ȸ���
		int marksti=0,marke=0,marksa=0;  //ʧ�ԡ�ʧ��������βƽ�ƴ����������������ͬƽ�Ƹ���
		for(int i=0;i<line;i++){
			if(stand[i]!=0){marksta++;}
			if(change[i]!=0){markc++;}
			if(wrong[i]==21||wrong[i]==121||wrong[i]==123||wrong[i]==221){markw++;}
			if(wrong[i]==122||wrong[i]==124||wrong[i]==222){markww++;}
			if(sticky[i]==false){marksti++;}
			if(endtone[i]==12||endtone[i]==21){marke++;}
			if(same[i]!=0&&wrong[i]==0){marksa++;}
			if(alone[i]==true){marka++;}
		}
		mark=20*marksta+15*markc+10*markw-50*(markww+marka+marksa)-30*marksti-100*marke;
		if(mark>=line*10){temp="����ʫ���ɷǳ�������";}
		if(mark>=0&&mark<line*10){temp="����ʫ���ɽ�Ϊ������";}
		if(mark>=line*(-10)&&mark<0){temp="����ʫ���ɴ����������⡣";}
		if(mark<line*(-10)){temp="����ʫ��һ�׹���ʫ���־�ʫ��";}		
		return temp;
	}
	
	//�����ۡ������Ը���ʫ��βƽ�Ƶ�����(���۸���ʫȫʫ�ľ�βƽ�ƣ���������)
	private String SayEndTone(){
		String temp=new String("");
		for(int i=0;i<line;i++){
			if(i!=0){
				if(endtone[i]==12){temp="��"+Integer.toString((i+1),10)+"��Ӧƽ���ƣ�";}
				if(endtone[i]==21){temp="��"+Integer.toString((i+1),10)+"��Ӧ�ƶ�ƽ��";}
			}
		}
		return temp;	
	}	
	
	//�����ۡ������Ը���ʫ�е�һһ�������(���۸���ʫ�е�һ�䣬�������۵ľ�����������������)
	private String Saysentence(int i){
		String temp=new String("");
		boolean inspect=false;
		if(inspect==false){       //�����ж�
			if(stand[i]==11){temp="��"+Integer.toString((i+1),10)+"��ʹ��ƽƽ������";inspect=true;}
			if(stand[i]==12){temp="��"+Integer.toString((i+1),10)+"��ʹ��ƽ�ƽ�����";inspect=true;}
			if(stand[i]==21){temp="��"+Integer.toString((i+1),10)+"��ʹ����ƽ������";inspect=true;}
			if(stand[i]==22){temp="��"+Integer.toString((i+1),10)+"��ʹ�����ƽ�����";inspect=true;}
		}
		if(inspect==false){      //����ж�
			if(change[i]==11){temp="��"+Integer.toString((i+1),10)+"��ʹ��ƽƽ�ű��";inspect=true;}
			if(change[i]==12){temp="��"+Integer.toString((i+1),10)+"��ʹ��ƽ�ƽű��";inspect=true;}
			if(change[i]==21){temp="��"+Integer.toString((i+1),10)+"��ʹ����ƽ�ű��";inspect=true;}
			if(change[i]==22){temp="��"+Integer.toString((i+1),10)+"��ʹ�����ƽű��";inspect=true;}
		}
		if(inspect==false){            //�־��ж�
			if(wrong[i]==21){temp="��"+Integer.toString((i+1),10)+"���ƽ�־ȣ�";inspect=true;}
			if(wrong[i]==121){temp="��"+Integer.toString((i+1),10)+"��ƽ�ƽŵ������������ڶԾ�Ȼأ�";inspect=true;}
			if(wrong[i]==122){temp="��"+Integer.toString((i+1),10)+"��ƽ�ƽŵ������������ڶԾ�û�оȻأ�";}
			if(wrong[i]==123){temp="��"+Integer.toString((i+1),10)+"��ƽ�ƽŵ������������ڶԾ�Ȼأ�";inspect=true;}
			if(wrong[i]==124){temp="��"+Integer.toString((i+1),10)+"��ƽ�ƽŵ������������ڶԾ�û�оȻأ�";}
			if(wrong[i]==221){temp="��"+Integer.toString((i+1),10)+"�����ƽ������ֶԵ����ڶԾ�Ȼأ�";inspect=true;}
			if(wrong[i]==222){temp="��"+Integer.toString((i+1),10)+"�����ƽ������ֶԵ����ڶԾ�û�оȻأ�";}
		}
		if(inspect==false){
			if(same[i]==1){temp=temp+"��"+Integer.toString((i+1),10)+"���������ƽ����ͬ��";}
			if(same[i]==2){temp=temp+"��"+Integer.toString((i+1),10)+"���������ƽ����ͬ��";}
			if(same[i]==3){temp=temp+"��"+Integer.toString((i+1),10)+"���ġ�����ƽ����ͬ��";}
			if(alone[i]==true){temp=temp+"��"+Integer.toString((i+1),10)+"�䷸��ƽ��";}
		}
		if(inspect==true){
			temp=temp+"��ȷ";
		}
		else{
			temp=temp+"����";
		}
		return temp;
	}
	
	//�����ۡ������Ը���ʫ��������(���۸���ʫ��𤣬�������2�仰;[�ƻ�]�����仰�ϲ�Ϊһ�仰)
	private String SayStricky(){
		String sayd=new String(""),sayn=new String(""),temp=new String("");
		sayd="";
		sayn="";
		boolean d=true,n=true;  //�������Ƿ�������������dΪ�ԣ�nΪ𤣬������ȷ��Ϊtrue������Ϊfalse
		for(int i=0;i<line;i++){
			if(sticky[i]==false&&i!=0){  //�ж��Ƿ�Ϊ��һ��
				if(i%2==0){   //Ϊż�����ǳ���
					if(n==true){   //�����ǰû�г���ʧ�����
						n=false;
						sayn=sayn+"��"+Integer.toString((i+1),10);
					}
					else{     //�����ǰ������ʧ�����
						sayn=sayn+"��"+Integer.toString((i+1),10);
					}
				}
				else{   //Ϊ�������ǶԾ�
					if(d==true){               //�����ǰû�г��ֹ�ʧ������
						d=false;
						sayd=sayd+"��"+Integer.toString((i+1),10);
					}
					else{      //�����ǰ������ʧ������
						sayd=sayd+"��"+Integer.toString((i+1),10);
					}
				}
			}
		}
		if(d==true){  //ȫʫû��ʧ������
			sayd="ȫʫû��ʧ������";
		}
		else{
			sayd=sayd+"�����ʧ������";
		}
		if(n==true){  //ȫʫû��ʧ�����
			sayn="ȫʫû��ʧ�����";
		}
		else{
			sayn=sayn+"�����ʧ�����";
		}
		temp=sayd+"��"+sayn;
		return temp;
	}
}

//��������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������//

/** ���� 
 * @һ�仰������һ�����������ʦΪ����ġ��������յ��Ŀ���
 */

/** ����
 * @1�������ʦ���������������˼��һ���������java��MySQLΪ�ںˣ�����ȥѧMySQL
 * @2��ѧУCUC������û��·�������ӹ��ܣ��������һ���û��ʵ�֣�����û��ѧ
 * @3�����¡�ý��һ��47�ˣ��������޸����ľ���17����ͨ������ĺܵ�
 * @4����������ҵ��ƣ���������ҵ����
 */

/** Ը��
 * @1���������ԳЭ������
 * @2����ɡ�����ʫ�ˡ���Ŀ
 * @3����ҵʱӵ�г�Ϊһ���������ʦ�ļ���
 */

//PS�����������Eclipse����һ��Ϊjava�ṩIDE���ܵı�����
//������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������//