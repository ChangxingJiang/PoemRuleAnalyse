package main;

class InnerData{
	//����������
	InnerData(){}
	
	//���ϲ���ƽˮ�������(������������֣���������ƽˮ�ϱ��)
	public int DataWaterChar(String character){
		int index=-1,code=-1;										  //�ж��룻index������rhyme�̶���Ϊ���ֶ�Ӧƽˮ�ϱ��
		String water[]=new String[106];						          //����ƽˮ���ַ�����
		water[0]="��ͬͭͩͲͯ��ͫ�����ҳ����ֳ��Թ�������������������¡�չ���������������������̴�ͨ�������������ڧ����ۺ���ܶ�����������ܺ�����޴д���";
		water[1]="��ũ���������ɳ�����ӹ����ӺŨ�شӷ�����׷�����㼹�����ٯ����ܭ��Ӷ�������������ݹ���ŧ������خ���ͮ";
		water[2]="��������׽�˫����ǻײ��׮����";
		water[3]="绻�׵���ή��Ƣ��������ʬ�������������¶������������������֫����ʨ�����ɶ���������ظ��������������ɸ������������������������������̨����ا����˥Ω����������Ϸ���δ������������ί������������۪֧֦��Ϊ�����鱮������Ƥ����ʩ֪�۳ع�Σ��ʦ�˳�ü��֥֮ʱʫ����Ǵ��������ɼ�˿˾��ҽ�˼�̳����άش�����ܯ�ִ��ż�֬������ʬ�괶���Ȳ�ƣ�ı���ި���������˭˹˽�����۴�������������Ӽ�˥׶�����������׷";
		water[4]="΢ޱ�ͻջ�ΤΧ��Υ������糷ɷ���������ܻ�������ϡϣ������Ρ������������";
		water[5]="�����������ճ�������������ѳ�����������������®¿���������������������Ҿ�������ٺ���磳��������ƥ����Ϳ��";
		water[6]="��������ۻ���������������������������������������������������������������������ģ���Ѻ��������������¹�������ͽ;Ϳݱͼ��ū����������¬�������ڿݶ���خ��������۾�������������������¦����������歾�ġ�������������������������������ĸ��ܽ�­���ۥ����Ĥ����Ż������٪����ų����";
		water[7]="������������������̵�صڮ�������������������������������Ϭ˻˺��ܱ��������Ϫ����Я������";
		water[8]="�ѽ�Ь�Ʋ��β��Ľ���г���ŹԻ�����٭����ի������۽�ி��ܿ���ٽ";
		water[9]="�һֿ���ػ�ö÷ýú���״ߴݶ��㱭�����ƿ�����̨̦�òŲĲƲ����������ֲ�̥�������໵�����Ⱘ����ڶ��Φ�������ø�����";
		water[10]="����������н���������������������������������䳾�´�����Ƶƻ������޽�������ƶ������������������ѮѲѱ�������������������ѭ�����׻��ѯ���ݷܧ��������ꥱ�������������������������۴����ױ��";
		water[11]="�������Ʒշַ׷ҷٷ�Ⱥȹ�����ڽ��ѫѬ��Ѭ����ܿ���Ա�������������ޭ";
		water[12]="ԪԭԴ԰Գԯ̹����ެ��������ԩ������������������׶ض���������豼��������Ժ۸���������Ԯ�ᦷ�����ԧ������������ݥ�ո���������ԬԹ������������紿";
		water[13]="���������鵥���Ѳ�̴̲̳���иɸθ͸��������������軸�������������ܹٹ۹�������ͻ������������̯̾��������á������������ݸ⵰赧���ķ������������";
		water[14]="ɾ�������廹������徰�߰������������ɽ���������������ڨ��";
		water[15]="��ǰǧ������������������������������ǣ������Ԩ��ñ�����ȪǨ����Ǯ��Ȼ����������������ƪƫ��ȫ���Դ���Ե�Ǧ�����괬�ѱ�רԲԱ������Ȩȭ�������ѽ����������پ����ƽ��ڹȬ����׾���꧵���������Ѣ夵���۳�ȼ�����";
		water[16]="����������������������ɽ������������Ңô��������������������۽�������������ңҦҡҥ��������쭱����ư����èҪ������������زƯƮ����٬����������ۿ�����������������ώ������������";
		water[17]="�ȳ�����é��������س�������������ð�����������������������ҧ�������ʽ���ٮץ���";
		water[18]="�������ֵ������������������θ޺��հ�����ݸ����ɦë����ɧ��Ҹ������Ӳ����߶����������������������޶��ҥ";
		water[19]="����޺Ӹ갢�Ͳ��ƿ��Ӷ����ܺɹ�ĥ�ݺ̸����٢�����ǿ�ڭ����ɯ������Ħħ�����Ķ�Ŷ���ô��������٤���ɷ���������������������";
		water[20]="�黨ϼ�Ҳ軪ɳ�����߹�баѿ���ɴѻ�ڲ�������������Ŀ�ͼ�Ү����ղ����Ϻ��������ѽ�����������ү���������������Ѿ������Щ�����ߵ�Ҭ����뻮�������";
		water[21]="����������������������������ױ����˪�س����������ʹ�����������ׯ�Ʋֻ�װ������������罴���â��������ǹ�������ƿ�ǿ�����ԲԿ�����з�������������Ǽ�콪���ֽ���𦽫ǽɣ������������������������������谲��̷������˻Ȼ�������ɲ׸ٿ������ɥä��æã������ŵ����Ѱ��������Ϻ�����������ȿ����Կ������������Ǿ���������װ����";
		water[22]="������ä���������Ӣ����ƽ����������������Ө���������������澨ӭ�к����å���Ⱦ�ݺӣ�������������羫��ݼ캾�ӯ���ӪӤӧ���ʢ�ǳϳʳ�������������������ٳ��ǹ�������޿��ɶ�����������������������ƺ��";
		water[23]="�ྭ������������ͤͥ͢����ͣ��������ܰ������������������������������������͡ڤ������ƿ��Ƽӫө��������";
		water[24]="����ة������籱���ӥӦӬ���ų���ʤ����ƾ�Ծ��������Ƶǵ�ɮ�������������������������ٺ��ޫ����";
		water[25]="������������������������������ţ�������������۳����ٱ�������������𯲻������������ıĲ��ì����کŽŸ�¥¦��͵ͷͶ�����ı��������������������������������ٴ�����ŷ§������������Ż������¨ظ��";
		water[26]="��Ѱ�������������������������������������������������ȳ�����ǭ짽��ɭ���ܳ���տ";
		water[27]="��̶̷�����������ֺ�����ᰲ�̰̽���述�̸���������̲���۰��������";
		water[28]="������������ռ��ǫ����ǩհ��������̼�Ǳ����ճ������������ղ����ǭմɻռ������";
		water[29]="�̼�����ҷ���ɼ�ෲ�����Ƕ������";
		water[30]="������������Ͱ��£������";
		water[31]="�������¤¢ӵ��������ڣ������ӿӻٸӼ�ֹ���������";
		water[32]="���۰�������";
		water[33]="ֻֽ�������Ƶ����ұ˻�ί������粴������¶�������澳޳����ϴ�����ָּ����������ذ������ˮΨֹ����ϲ�Ѽ͹��Ʊ��������ʸ��������ڳ���ֺ�������������ʷʹʻ��������ٵ�����ʿ��ٹʼ���̳��������������Ʀַ�����б�����";
		water[34]="β��έ��򳼸ΰ��쳷̷������";
		water[35]="����������������������������������������ƴ�����Ů���ܾ�����������޾ھ������������ž�ڪ���ȥ";
		water[36]="���������踸���Ļ��Źɼ����������׻�����������¨±Ŭ�ǻ��۸�������³�ֶ������������������師�������Ʋ������丬��ȡ������������������²��������ڬ�����ŭ�ڼ���������߶�����ç��";
		water[37]="��������������ϴۡ��ڮ�����㩵�������";
		water[38]="з�⺧�������ǰڹհ���";
		water[39]="�߻ڸĲɲʺ������������������������ڱ��ؾ�伻��ÿ����";
		water[40]="��������������׼����������������������⾽����������Ǵ�˱�����";
		water[41]="�Ƿ��̷�������㢷޷�����";
		water[42]="��Զ����Է������������������������������ܿҿ�Ȧ��繻������";
		water[43]="��ů�����̹��»��������ɢ�鵮�趶�٩���̹̻��������";
		water[44]="���۰����׫ջ��������ݸ��";
		water[45]="ϳ��ǲǳ��ת��Ȯѡ������չ���׭������Լ�������������ջ�����������˱������䵥���������������Ѣ��Ǯ������";
		water[46]="С������������������������ޤ������������������������������ز������";
		water[47]="�ɱ�î��צ���ӽ�����毳�";
		water[48]="𩱦�������Ϻõ��������յ��������ֿ���ɩ���ʱ��ᱤ�ٲ�껺���������������������";
		water[49]="�������������Ⱥɿɿ�������������������������������ϻ���涵�����";
		water[50]="������Ұ���߹���дк��ұҲ�Ѽּ������������׽����������";
		water[51]="�������������������ʽ���������쵴㯷ŷ����������Ƶ���ˬ�������̻ϻ�ç�߷Ľ�������Գ����������������������ų�����";
		water[52]="��Ӱ�������쾳�����������ҳ��ӱ������ʡ�Ҿ�۫�ͱ��ӱ����������侸��";
		water[53]="�ľ���ͦͧ��������ٵȶ����ֿ�����";
		water[54]="�о����ֿ�ĸ�����Ѹ������ø���������ҷ����ż��ź�̺��㹸Ķ��ź����������ĵ��Ͽ�ŷ����ȡťݬ����ĳ��Ĵ����������������Ȥ����Ź";
		water[55]="������Ʒ�����������������������";
		water[56]="����魵��ࢿ��Ҹ�򥺳̺����Ƕ";
		water[57]="���������ռ���Ⱦ�ڵ��Ƚ�����ٽ�������Ǹ��ٲ";
		water[58]="����������տն�����ۺ���Ǹ";
		water[59]="���ηﶴ��Ū����ʹ�����з����տظ��ú���";
		water[60]="����������ͳ��������ٺ�����ӷ�Ӻ���";
		water[61]="筽���ײ�����";
		water[62]="���µ���־��˼��������������λϷ������α�����Ǽ�����������ʹ���������ı�����׹�����ܴ�����˧�޼�˯��������ô���������������ʶ��־������������ֲ֯��ʳ������ܲ���鼽��������ȱ�Ա������ֿ�ճ�������ʾ�����������Ʃ����������ʩ��ֵ���ή˾�������ʼ��ɪ��";
		water[63]="δζ����ѷ�ξηοεκθμν�����ȼ������������";
		water[64]="����ȥ�������Ԧ��������ԥ��ˡ����������Ԥ�����Ѿڳ���Ůڪ죳���";
		water[65]="��·¸¶�����ȶɸ��������ؾ���ŭ���������ùʹ˹;�ĹĺĽļעפ��ԣ�����ס���⻤���ʾ�ȤȢ�������������������׸��������Ԣ����������������������������������㲥�����";
		water[66]="���Ƽ������������õ��ջݻ۱ҹ��ͼ��������е۱α���������ϵ��������׺��ϸ˰����ޥ���������ɼ���ϵ��ҷ��������ǳ����޼������������˵����������׸���й�޵߽������յ���ҽ";
		water[67]="̩�����Ǵ������̺�����氬���λ����ڻ�̫̭���������";
		water[68]="�Թ�и��������ծ�ֻ�������е�ݿ������ܰ�����������ɱ�Ƚ�������կ";
		water[69]="������������������鱳��˶Էϻ������������������ʹ�᷷θ��翮��������㣴�����������������òɻش��Ա�����";
		water[70]="����ӡ������������˳���޽����������˴�߽�Ѷط��Ѹ˲׻��������������ϳ�誳���Ѵ���￣������������";
		water[71]="����������ѵ��ܷ޿�������㳽�����۩Ա����";
		water[72]="Ը��Թ�����׽������ٽ���Ȱ��ȯ����ѷ�۷���Զ�����޶�۱����Ȧ";
		water[73]="�������Ѷ���̾�ɹ�ɢ�ε������ù�밸��̿�������ڹ��ᣲ�費������������ο���������������϶����������á̯٩��̲��";
		water[74]="���㻼���л������λ�ջ�ߴ�������ڨ�á���ް�۲�դ��";
		water[75]="�������ر��ս��ɿ�Ŵ�����ѡԺ������������������߾����ߵ����ѣ��ٻ����������Ƭ��Ǵ��Ե����Ԯ��襵�����������䴩�罦���ǣ��������ǲ����շת��";
		water[76]="ХЦ��������گ����Ҫ��ҫ���������������ڽ��Ф��������н�����Ư�������ҡ��Լ����";
		water[77]="Ч��òУТ���ױ������ֽѽ����ֽϳ��þ�";
		water[78]="��ñ������������¸�ھ���õ����Ͱ���������ð�������ɰðĸ���۬��츿���";
		water[79]="���������߿���������Щ���ʹ������ĥ�������Ի����";
		water[80]="��ҹ��л鿰���Ͼ��屼���������ٻ��������ڼ�թ�������������Ȳ��¿���жк��է����";
		water[81]="�������ཫ״���˳��ÿ�׳�����̳����έ�ϰ��������������������ֿ����𿺿�������������ɥ���������������������й���������ڿ�ӷ��������󳥵���������";
		water[82]="�����������Ծ�ʢ��ʥӽ����ӳ����֣����������ƸںӾ��ٻӲ���ɸ����ӭ�����֤�첢��";
		water[83]="������ʤ��Ӧ����������������Ө֤���˾���͢��ͥ����ʣƾ���ȵʵ�";
		water[84]="嶺���������������฻�޶�©ª�������ï����θ������ָ����ȳ�������٧��񼶺�����������Ź�͸���������߸�ڸ�������Ѿξ�������������������Ž����ޢ�������ͺ��ۺ��";
		water[85]="�����������߽����������������������";
		water[86]="�����ĵ���������ݲ�壵�����";
		water[87]="�޽������ĵ�ռ�����ٵ�Ƿ��������ɻ��մ����氳Ǳ��";
		water[88]="�ݼ���Ѵ����׬պ����Ƿ��վ";
		water[89]="��ľ��Ŀ����»�������¹����½���������޶����������챸������������޷���¾�����������Ķ��������ף´�����������ĸ���ͺ���������ôر��������������������µع����������������������Ǹ����";
		water[90]="����������������¼�����̶�����������ٴ�������ԡ���������������ָ���";
		water[91]="������������׽˷��׿�������������㱢���ȷ��ߪ�����ҩ����ѧ";
		water[92]="���ձʳ���ʵ����һ��Ҽ��������������ʧ��������������ɪϥƥ������߳��ʭϤ����ڵ��٥�����ϱ�ֶ������󰼵����˧��ۤ����������";
		water[93]="���������������禸�ڰ���������������̾�ξε";
		water[94]="�¹Ƿ���Խ��û������߿�����Ъͻ�����귤��ާ��ګ�����أ����������Ͳ�͹��������������ܥԻڦ";
		water[95]="�´�ĩ����Ѷ�ָ�ĭ�θ�ʲ�������Ĩ����̢���޺Ȱ�̡�����������������";
		water[96]="�����λ��˲�ɱɲ���ꩽո�Ϲ��ˢ��";
		water[97]="м��ѩ�����ҽ�Ѩ˵Ѫ�������Ⱦ�������׾�����޾�й��ҭ�ܳ����������ٳ�����׺�ľ�����Ш����������������ߢ�������㳺�ν��꡵���ֶ��������";
		water[98]="ҩ�������������׾���Լ��ȸĻ������������Ծ����������������ȴ��ȵ��ŵ��ĮԿ��Ű�ӻ񲴲����������»�˸Ī����̶���㡲����ű����Ĥ����������������������į����������ج���Ǹ�����������";
		water[99]="İʯ�Ͱ��󲮼�լϯ�̼߱����۲����������ǻ���ϦҺ���϶�滭�ٱٳ��׸Ｙ�������ʾ��Ӹ���դխ������ϧƧ��ҴҸ�Ͳ�����ժ������������������������˶�����ت�����������ϯ��ϫ��զ���ݰ�Ī�������";
		water[100]="�������������ѵе���ϭ���ŵ�������������ժ��ݶ�ݵӵĳ����������������ʵ���������������";
		water[101]="ְ����ʳʴɫ����ī��Ϣֱ�ñ��ڲ�����������ʽ����ֲֳ�������Ĭ֯���������������ʶ�ƿ�������߮���ð������ؽ��߯��Ϩ�����������";
		water[102]="���������ؼ�����ʪϰ��ʮʰʲϮ����ɬ��Ҿ֭����ִ��������������";
		water[103]="������������������ظ�������̤����������";
		water[104]="Ҷ����뺽���檵�����ݼ�������Э���Խ�����Ю��������������������";
		water[105]="Ǣ��Ͽ����ҵ��ϻѹѼ���ӽ�в��Ѻ������ǡգ�������";
		for(int i=0;i<106;i++){
			index=water[i].indexOf(character);			              //��water��������ǰ��,>=0Ϊ����,=-1Ϊ������
			if(index>-1){
				code=i;
				break;				
			}
			index=-1;
		}
		return code;
	}
	
	//���ϲ���ƽˮ�ϱ�š����ƶ�Ӧ��(����ƽˮ�ϱ��룬����ƽˮ������)
	public String DataWaterCode(int code){	
		String name=new String("");							          //���巵�ر���
		switch(code){
			case -1:name="fail";break;
			case 0:name="һ��";break;
			case 1:name="����";break;
			case 2:name="����";break;
			case 3:name="��֧";break;
			case 4:name="��΢";break;
			case 5:name="����";break;
			case 6:name="����";break;
			case 7:name="����";break;
			case 8:name="�ż�";break;
			case 9:name="ʮ��";break;
			case 10:name="ʮһ��";break;
			case 11:name="ʮ����";break;
			case 12:name="ʮ��Ԫ";break;
			case 13:name="ʮ�ĺ�";break;
			case 14:name="ʮ��ɾ";break;
			case 15:name="һ��";break;
			case 16:name="����";break;
			case 17:name="����";break;
			case 18:name="�ĺ�";break;
			case 19:name="���";break;
			case 20:name="����";break;
			case 21:name="����";break;
			case 22:name="�˸�";break;
			case 23:name="����";break;
			case 24:name="ʮ��";break;
			case 25:name="ʮһ��";break;
			case 26:name="ʮ����";break;
			case 27:name="ʮ����";break;
			case 28:name="ʮ����";break;
			case 29:name="ʮ����";break;
			case 30:name="һ��";break;
			case 31:name="����";break;
			case 32:name="����";break;
			case 33:name="��ֽ";break;
			case 34:name="��β";break;
			case 35:name="����";break;
			case 36:name="����";break;
			case 37:name="����";break;
			case 38:name="��з";break;
			case 39:name="ʮ��";break;
			case 40:name="ʮһ��";break;
			case 41:name="ʮ����";break;
			case 42:name="ʮ����";break;
			case 43:name="ʮ�ĺ�";break;
			case 44:name="ʮ����";break;
			case 45:name="ʮ��ϳ";break;
			case 46:name="ʮ����";break;
			case 47:name="ʮ����";break;
			case 48:name="ʮ���";break;
			case 49:name="��ʮ��";break;
			case 50:name="��ʮһ��";break;
			case 51:name="��ʮ����";break;
			case 52:name="��ʮ����";break;
			case 53:name="��ʮ����";break;
			case 54:name="��ʮ����";break;
			case 55:name="��ʮ����";break;
			case 56:name="��ʮ�߸�";break;
			case 57:name="��ʮ�˼�";break;
			case 58:name="��ʮ���R";break;
			case 59:name="һ��";break;
			case 60:name="����";break;
			case 61:name="���";break;
			case 62:name="�Č�";break;
			case 63:name="��δ";break;
			case 64:name="����";break;
			case 65:name="����";break;
			case 66:name="����";break;
			case 67:name="��̩";break;
			case 68:name="ʮ��";break;
			case 69:name="ʮһ��";break;
			case 70:name="ʮ����";break;
			case 71:name="ʮ����";break;
			case 72:name="ʮ��Ը";break;
			case 73:name="ʮ�庲";break;
			case 74:name="ʮ����";break;
			case 75:name="ʮ����";break;
			case 76:name="ʮ��Х";break;
			case 77:name="ʮ��Ч";break;
			case 78:name="��ʮ��";break;
			case 79:name="��ʮһ��";break;
			case 80:name="��ʮ���l";break;
			case 81:name="��ʮ����";break;
			case 82:name="��ʮ�ľ�";break;
			case 83:name="��ʮ�徶";break;
			case 84:name="��ʮ���";break;
			case 85:name="��ʮ����";break;
			case 86:name="��ʮ�˿�";break;
			case 87:name="��ʮ����";break;
			case 88:name="��ʮ��";break;
			case 89:name="һ��";break;
			case 90:name="����";break;
			case 91:name="����";break;
			case 92:name="����";break;
			case 93:name="����";break;
			case 94:name="����";break;
			case 95:name="����";break;
			case 96:name="����";break;
			case 97:name="��м";break;
			case 98:name="ʮҩ";break;
			case 99:name="ʮһİ";break;
			case 100:name="ʮ����";break;
			case 101:name="ʮ��ְ";break;
			case 102:name="ʮ�ļ�";break;
			case 103:name="ʮ���";break;
			case 104:name="ʮ��Ҷ";break;
			case 105:name="ʮ��Ǣ";break;
			default:name="fail";break;
		}		
		return name;
	}	
}