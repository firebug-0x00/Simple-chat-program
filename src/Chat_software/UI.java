package Chat_software;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import javax.swing.*;

//A�����û���Ϣ
//B���뷿��ɹ����ط������г�Ա������
//C���뷿��ʧ��
//D������Ϣ
//E���س�Ա�����˿�
//F��ԱID��Ϣ
//G��Ա������Ϣ
//H��Ա����ָ��
//I����ر�

class Member{
	public String memberName = null;
	public InetAddress memberIp = null;
	public int port = -1;
	public int flag = 0;
}

public class UI extends JFrame{
	private JPanel log = new JPanel();			//������½���
	private JPanel search = new JPanel();		//�����������
	private JPanel room = new JPanel();			//�����������
	private JPanel cardPanel = new JPanel();	//������Ƭ���
	private JPanel chatPanel = new JPanel();  	//�����������
	//������Ա����	
	Member[] member = new Member[100];			
	{
		for(int i=0; i<100; i++)
		{
			member[i] = new Member();
		}
	}
	Button searb = new Button("����������");		//�������������Ұ�ť
	Button roomb = new Button("����������");		//�������������Ұ�ť
	CardLayout cardLayout = new CardLayout();	//������Ƭ���ֶ���
	
	//������������
	Label ipLabel = new Label("�����������ҵ�ַ��Ϣ", Label.CENTER);	//����IP��ַ��ʾ��ǩ
	JPanel ipInPanel = new JPanel();							//����IP�������
	Label ipInLabel = new Label("IP��ַ");						//����IP������ʾ��ǩ
	JTextField ipInText = new JTextField(20);					//����IP��ַ�����ı���
	JPanel sButtPanel = new JPanel();							//������ť���
	Button ipSearButt = new Button("ȷ��");						//����������ť
	Button backButt = new Button("����");							//�������ذ�ť
	JTextField nameText = new JTextField();						//�����ǳ��ı���
	JTextField portText = new JTextField();						//�����˿��ı���
	JPanel LabPanel = new JPanel(); 							//������ǩ���
	Label nameInLabel = new Label("�ǳ�");						//�����ǳ���ʾ��ǩ
	Label portInLabel = new Label("�˿�");						//�����˿���ʾ��ǩ
	
	
	//room��������
	JPanel showPanel = new JPanel();						//������ʾ���
	JTextArea chatContent = new JTextArea(17, 41);			//�������������ı���
	JTextField ipShow = new JTextField();					//����IP��ʾ�ı���
	JTextArea inputArea = new JTextArea(5, 41); 			//���������ı���
	JPanel rButtPanel = new JPanel(); 						//������ť���
	Button sentButt = new Button("����"); 					//�������Ͱ�ť
	Button quitButt = new Button("�˳�");						//�����˳���ť
	JTextArea memberShow = new JTextArea(22, 15); 			//������Ա��ʾ�ı���
	JPanel leftPanel = new JPanel();                        //����������
	JPanel rightPanel = new JPanel();                       //�����ұ����
	Label memberLabel = new Label("��Ա", Label.CENTER);		//������Ա��ǩ
	
	//�������
	JPanel chatShowPanel = new JPanel();						//������ʾ���
	JTextArea chatChatContent = new JTextArea(19, 41);			//�������������ı���
	JTextArea chatInputArea = new JTextArea(5, 41); 			//���������ı���
	JPanel chatRButtPanel = new JPanel(); 						//������ť���
	Button chatSentButt = new Button("����"); 					//�������Ͱ�ť
	Button chatQuitButt = new Button("�˳�");						//�����˳���ť
	JTextArea chatMemberShow = new JTextArea(22, 15); 			//������Ա��ʾ�ı���
	JPanel chatLeftPanel = new JPanel();                        //����������
	JPanel chatRightPanel = new JPanel();                       //�����ұ����
	Label chatMemberLabel = new Label("��Ա", Label.CENTER);		//������Ա��ǩ
	
	UI(){
		
		//�ı�swing�ؼ��ķ��Ϊ��ǰϵͳ(windows 10)���Ĵ���
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		//UI����
		this.setLayout(new GridLayout(1, 1));	//��岼������Ϊ���񲼾�
		this.setTitle("������");				
		this.setSize(750, 525);					//���ô��ڴ�С
		this.setLocationRelativeTo(null);		//���ô��ڳ���λ��
		this.add(cardPanel);					//��ӿ�Ƭ���
		
		cardPanel.setLayout(cardLayout);		//���ÿ�Ƭ���Ĳ���Ϊ��Ƭ����
		cardPanel.add(log, "log");				//��ӵ�½���
		cardPanel.add(search, "search");		//����������
		cardPanel.add(room, "room");			//��ӷ������
		cardPanel.add(chatPanel, "chatPanel"); 	//����������
		
		log.setLayout(new GridLayout(1, 2)); 			//���õ�½���Ϊ���粼��ģʽ
		log.setFont(new Font("����", Font.BOLD, 40));		//���õ�½���������С
		log.add(searb);									//��Ӽ��������Ұ�ť
		log.add(roomb);									//��Ӵ��������Ұ�ť
		
		//Ϊ���밴ť������������
		searb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "search");	//��ʾ�������
			}
		});
		
		//Ϊ���䰴ť������������
		roomb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//���������û������Լ����ǳ�
				String roomUserName = JOptionPane.showInputDialog( "����������ǳ�" );
				if(roomUserName!=null && !roomUserName.equals(""))
				{
					member[0].memberName = new String(roomUserName);
					member[0].flag = 1;
					memberShow.setText(roomUserName);
					//�л�����
					cardLayout.show(cardPanel, "room");		//��ʾ�������
					chatContent.append("\t"+"-------"+"��Ա��"+member[0].memberName+" "+"�Ѵ�������"
					+"-------"+"\n");
					roomback();
				}
			}
		});
		
		
		//�����������
		search.setLayout(new BorderLayout());						//�����������Ϊ�߽粼��
		
		ipLabel.setFont(new Font("����", Font.BOLD, 40));				//���ñ�ǩ�����С
		ipInLabel.setFont(new Font("����", Font.BOLD, 30));	 		//����IP������ʾ�����С
		nameInLabel.setFont(new Font("����", Font.BOLD, 30));	 		//�����ǳ���ʾ��ǩ�����С
		portInLabel.setFont(new Font("����", Font.BOLD, 30));	 		//���ö˿���ʾ��ǩ�����С
		ipInText.setFont(new Font("����", Font.BOLD, 30)); 			//����IP�ı���������С
		ipInPanel.setLayout(new BorderLayout());	 				//����IP������岼��Ϊ�߽粼��
		sButtPanel.setLayout(new FlowLayout());	 					//���ð�ť��岼��Ϊ��ʽ����
		ipSearButt.setFont(new Font("����", Font.BOLD, 30));	 		//����������ť����
		backButt.setFont(new Font("����", Font.BOLD, 30)); 			//���÷��ذ�ť����
		nameText.setFont(new Font("����", Font.BOLD, 30));	 		//�����ǳ��ı�������
		nameText.setText("��������5���֣�");  				//�����ǳ��ı�����ʾ
		portText.setFont(new Font("����", Font.BOLD, 30));	 		//���ö˿��ı�������
		portText.setText("");  										//���ö˿��ı�����ʾ
		LabPanel.setLayout(new BorderLayout()); 					//���ñ�ǩ��岼��Ϊ�߽粼��
		
		search.add(ipLabel, BorderLayout.NORTH);					//���IP��ʾ��ǩ��λ�������ڱ���
		search.add(ipInPanel, BorderLayout.CENTER);					//���IP������壬λ���������в�
		search.add(sButtPanel, BorderLayout.SOUTH);					//��Ӱ�ť��壬λ���������ϲ�
		search.add(LabPanel, BorderLayout.WEST); 					//��ӱ�ǩ��壬λ������������
		
		ipInPanel.add(ipInText, BorderLayout.CENTER); 				//���IP�ı�������λ�����м�
		ipInPanel.add(nameText, BorderLayout.NORTH); 				//����ǳ��ı������壬����λ���ڱ���
		ipInPanel.add(portText, BorderLayout.SOUTH); 				//��Ӷ˿��ı������壬����λ�����ϲ�
		
		LabPanel.add(nameInLabel, BorderLayout.NORTH);				//����ǳ���ʾ��ǩ������λ���ڱ���
		LabPanel.add(ipInLabel, BorderLayout.CENTER); 				//���IP������ʾ������λ�����м�
		LabPanel.add(portInLabel, BorderLayout.SOUTH);				//��Ӷ˿���ʾ��ǩ��λ���������ϲ�
		
		sButtPanel.add(ipSearButt);									//���������ť
		sButtPanel.add(backButt);									//��ӷ��ذ�ť
		
		//Ϊ���ذ�ť������������
		backButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "log");					//��ʾ��¼���
			}
		});
		
		//Ϊȷ����ť������������
		ipSearButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchback();
			}
		});
		
		
		//�����������
		room.setLayout(new BorderLayout()); 					//����������óɱ߽粼��
		
		showPanel.setLayout(new BorderLayout());       			//��ʾ�������Ϊ�߽粼��
		chatContent.setEditable(false); 						//���������ı������óɲ��ɱ༭
		ipShow.setEditable(false); 								//IP��ʾ�ı������óɲ��ɱ༭
		memberShow.setEditable(false); 							//��Ա��ʾ�ı������óɲ��ɱ༭
		ipShow.setFont(new Font("����", Font.BOLD, 30));	 		//����IP��ʾ�ı�������
		rButtPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); //���ð�ť���Ϊ��ʽ����
		sentButt.setFont(new Font("����", Font.PLAIN, 15)); 		//���÷��Ͱ�ť����
		quitButt.setFont(new Font("����", Font.PLAIN, 15)); 		//�����˳���ť����
		chatContent.setFont(new Font("����", Font.PLAIN, 15));	//�������������ı��������С
		chatContent.setBackground(Color.LIGHT_GRAY);		  	//�������������ı���ı���ɫ
		memberShow.setFont(new Font("����", Font.PLAIN, 15));		//���ó�Ա��ʾ�ı��������С
		memberShow.setBackground(Color.GRAY);		  			//���ó�Ա��ʾ�ı���ı���ɫ
		leftPanel.setLayout(new BorderLayout()); 				//����������Ϊ�߽粼��
		inputArea.setFont(new Font("����", Font.PLAIN, 15));		//���������ı��������С
		rightPanel.setLayout(new BorderLayout());       		//�ұ��������Ϊ�߽粼��
		memberLabel.setFont(new Font("����", Font.PLAIN, 15));	//���ó�Ա��ǩ�����С
		
		room.add(leftPanel, BorderLayout.CENTER); 				//��������壬λ���������в�
		room.add(rightPanel, BorderLayout.EAST); 				//����ұ���壬λ�������ڶ���
		
		leftPanel.add(showPanel, BorderLayout.NORTH);			//�����ʾ��壬λ�������ڱ���
		leftPanel.add(inputArea, BorderLayout.CENTER); 			//��������ı���λ���������в�
		leftPanel.add(rButtPanel, BorderLayout.SOUTH); 			//��Ӱ�ť��壬λ���������ϲ�
		
		rightPanel.add(memberLabel, BorderLayout.NORTH); 		//��ӳ�Ա��ǩ��λ�������ڱ���
		rightPanel.add(memberShow, BorderLayout.CENTER); 		//��ӳ�Ա��ʾ�ı���λ�������ڶ���
		
		showPanel.add(chatContent, BorderLayout.CENTER);		//������������ı���λ���������м�
		showPanel.add(ipShow, BorderLayout.NORTH); 				//���IP��ʾ�ı���λ�������ڱ���
		
		rButtPanel.add(quitButt);    							//��ӷ��ذ�ť
		rButtPanel.add(sentButt); 								//��ӷ��Ͱ�ť
		
		
		
		//Ϊ�˳���ť������������
		quitButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				roomQuitButtBack();
				cardLayout.show(cardPanel, "log");				//��ʾ��¼���
			}
		});
		
		//Ϊ���Ͱ�ť������������
		ipSearButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//���Ͱ�ť���º�Ĳ���
			}
		});
		
		
		//�����������
		chatPanel.setLayout(new BorderLayout()); 						//����������óɱ߽粼��
		
		chatShowPanel.setLayout(new BorderLayout());       				//��ʾ�������Ϊ�߽粼��
		chatChatContent.setEditable(false); 							//���������ı������óɲ��ɱ༭
		chatMemberShow.setEditable(false); 								//��Ա��ʾ�ı������óɲ��ɱ༭
		chatRButtPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); 	//���ð�ť���Ϊ��ʽ����
		chatSentButt.setFont(new Font("����", Font.PLAIN, 15)); 			//���÷��Ͱ�ť����
		chatQuitButt.setFont(new Font("����", Font.PLAIN, 15)); 			//�����˳���ť����
		chatChatContent.setFont(new Font("����", Font.PLAIN, 15));		//�������������ı��������С
		chatChatContent.setBackground(Color.LIGHT_GRAY);		  		//�������������ı���ı���ɫ
		chatMemberShow.setFont(new Font("����", Font.PLAIN, 15));			//���ó�Ա��ʾ�ı��������С
		chatMemberShow.setBackground(Color.GRAY);		  				//���ó�Ա��ʾ�ı���ı���ɫ
		chatLeftPanel.setLayout(new BorderLayout()); 					//����������Ϊ�߽粼��
		chatInputArea.setFont(new Font("����", Font.PLAIN, 15));			//���������ı��������С
		chatRightPanel.setLayout(new BorderLayout());       			//�ұ��������Ϊ�߽粼��
		chatMemberLabel.setFont(new Font("����", Font.PLAIN, 15));		//���ó�Ա��ǩ�����С
			
		chatPanel.add(chatLeftPanel, BorderLayout.CENTER); 				//��������壬λ���������в�
		chatPanel.add(chatRightPanel, BorderLayout.EAST); 				//����ұ���壬λ�������ڶ���
		
		chatLeftPanel.add(chatShowPanel, BorderLayout.NORTH);			//�����ʾ��壬λ�������ڱ���
		chatLeftPanel.add(chatInputArea, BorderLayout.CENTER); 			//��������ı���λ���������в�
		chatLeftPanel.add(chatRButtPanel, BorderLayout.SOUTH); 			//��Ӱ�ť��壬λ���������ϲ�
		
		chatRightPanel.add(chatMemberLabel, BorderLayout.NORTH); 		//��ӳ�Ա��ǩ��λ�������ڱ���
		chatRightPanel.add(chatMemberShow, BorderLayout.CENTER); 		//��ӳ�Ա��ʾ�ı���λ�������ڶ���
		
		chatShowPanel.add(chatChatContent, BorderLayout.CENTER);		//������������ı���λ���������м�
		
		chatRButtPanel.add(chatQuitButt);    							//��ӷ��ذ�ť
		chatRButtPanel.add(chatSentButt); 								//��ӷ��Ͱ�ť
		
		
		
		//Ϊ�˳���ť������������
		chatQuitButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int remotePort = Integer.parseInt(portText.getText());							
				String ipAddress = ipInText.getText();
				InetAddress remoteAddress = null;
				try {
					remoteAddress = InetAddress.getByName(ipAddress);
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				chatQuitButtBack(remoteAddress, remotePort);
				cardLayout.show(cardPanel, "log");						//��ʾ��¼���
			}
		});
		
		//Ϊ���Ͱ�ť������������
		chatSentButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int remotePort = Integer.parseInt(portText.getText());							
				String ipAddress = ipInText.getText();
				InetAddress remoteAddress = null;
				try 
				{
					remoteAddress = InetAddress.getByName(ipAddress);
				} 
				catch (UnknownHostException e1) 
				{
					e1.printStackTrace();
				}
				chatSentButtBack(remoteAddress, remotePort);
			}
		});
		
		
		//���ڹرռ�����
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Window window = (Window) e.getComponent();
				window.dispose();
				//������������������ڣ�����ر�
				if(pubChatDs!=null && !pubChatDs.isClosed())
				{
					int remotePort = Integer.parseInt(portText.getText());							
					String ipAddress = ipInText.getText();
					InetAddress remoteAddress = null;
					try {
						remoteAddress = InetAddress.getByName(ipAddress);
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					}
					chatQuitButtBack(remoteAddress, remotePort);
				}
				//��������������������÷�������ر�
				if(pubRoomDs!=null && !pubRoomDs.isClosed())
				{
					roomQuitButtBack();
				}
			}
		});
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		this.setVisible(true);
		
	}
	
	DatagramSocket pubRoomDs = null;
	void roomback(){
		int port;
		String localIp = null;
		InetAddress localAddress;
		
		try 
		{
			DatagramSocket roomDs = new DatagramSocket();     	//��������socket
			DatagramSocket returnDs = new DatagramSocket();		//��������socket
			pubRoomDs = roomDs;
			localAddress = InetAddress.getLocalHost();			
			port = roomDs.getLocalPort();
			localIp = "IP:"+localAddress.getHostAddress()+"�˿ڣ�"+port;
			ipShow.setText(localIp);
			
			new Thread() 
			{
				DatagramPacket roomDp; 				//����������Ϣ��
				DatagramPacket returnDp;			//����������Ϣ��
				public void run() 
				{
					byte[] buf = new byte[1024];
					byte[] temp = new byte[1024];						//��ʱ�洢������
					char[] $temp = null;
					roomDp = new DatagramPacket(buf, buf.length);
					
					while(!roomDs.isClosed()) 
					{
						try 
						{
							//�յ����ݺ�Ĵ���
							roomDs.receive(roomDp);
							temp = roomDp.getData();
							//byte����ת����char����
							{
								Charset cs = Charset.forName("GBK");
						        ByteBuffer bb = ByteBuffer.allocate(temp.length);
						        bb.put(temp);
						        bb.flip();
						        CharBuffer cb = cs.decode(bb);
						        $temp = cb.array();
							}
							//����յ�����A����������߲�����������Ӧ����
							if($temp[0]=='A')
							{
								int count = 1;
								//������Ա���飬�ҵ��ǿ��е�Ԫ�أ������־��Ϊ1,��Ϊ�丳ֵ,����B
								for(count = 1; count < 100; count++)
								{
									if(member[count].flag == 0)
									{
										member[count].memberIp = roomDp.getAddress();
										member[count].memberName = new String(roomDp.getData(), 
												1,roomDp.getLength()-1);
										member[count].port = roomDp.getPort();
										member[count].flag = 1;
										//�����û������Ķ˿ں�
										String E = "E"+member[count].port;
										returnDp = new DatagramPacket(E.getBytes(), 
												E.getBytes().length, member[count].memberIp, 
												member[count].port);
										returnDs.send(returnDp);
										//�����û�ID
										String F = "F"+count;
										returnDp = new DatagramPacket(F.getBytes(), 
												F.getBytes().length, member[count].memberIp, 
												member[count].port);
										returnDs.send(returnDp);
										//ˢ�³�Ա�򣬲����������г�Ա��Ϣ����
										{
											String memberString = new String();
											for(int i = 0; i<100; i++)
											{
												if(member[i].flag == 1)
												{
													memberString = memberString
															+member[i].memberName+"\n";
												}
											}
											//�����ı�����ʾ������Ϣ
											String newMemberMessage = new String("\t"+"-------"+"��Ա��"+member[count].memberName
													+" "+"������"+"-------"+"\n");
											chatContent.append(newMemberMessage);
											chatContent.setCaretPosition(chatContent.getText().length());
											memberShow.setText(memberString);
											String B = "B"+memberString;
											//�򷿼��������������еĳ�Ա���͸��º����Ϣ
											for(int j=1; j<100; j++)
											{
												if(member[j].flag == 1)
												{
													returnDp = new DatagramPacket(B.getBytes(), 
													B.getBytes().length, member[j].memberIp, 
													member[j].port);
													returnDs.send(returnDp);
													//���ͳ�Ա������Ϣ
													String D = "D"+newMemberMessage;
													returnDp = new DatagramPacket(D.getBytes(), 
															D.getBytes().length, member[j].memberIp, 
															member[j].port);
													returnDs.send(returnDp);
												}
											}
										}
										break;
									}
								}
								//���countΪ100��˵����Ա�Ѿ����ˣ�����C
								if(count == 100)
								{
									String C = new String("C");
									returnDp = new DatagramPacket(C.getBytes(), 
											C.getBytes().length, roomDp.getAddress(), 
											roomDp.getPort());
									returnDs.send(returnDp);
								}
							}
							//����յ�����Ϣ��D��ͷ
							else if($temp[0]=='D')
							{
								//��ʾ�������ı����У�����������Ա�㲥
								String chatMessage = new String(roomDp.getData(), 1,
										roomDp.getLength()-1);
								//�����ı�����ʾ��Ϣ
								chatContent.append(chatMessage);
								chatContent.setCaretPosition(chatContent.getText().length());
								//��������Ա�㲥��ͷΪD����Ϣ
								String D = "D"+chatMessage;
								for(int j=1; j<100; j++)
								{
									if(member[j].flag == 1)
									{
										returnDp = new DatagramPacket(D.getBytes(), 
												D.getBytes().length, member[j].memberIp, 
												member[j].port);
										returnDs.send(returnDp);
									}
								}
								
							}
							//����յ�����Ϣ��G��ͷ
							else if($temp[0] == 'G')
							{
								//���յ���ID��Ϣת����int��
								String IDStr = new String(roomDp.getData(), 1,
										roomDp.getLength()-1);
								int ID = Integer.parseInt(IDStr);
								//�����߳�Ա�´�����ָ��
								String H = new String("H");
								returnDp = new DatagramPacket(H.getBytes(), 
										H.getBytes().length, member[ID].memberIp, 
										member[ID].port);
								returnDs.send(returnDp);
								//�ı�����ʾ������Ϣ
								String outLine = new String("\t"+"*******"+"��Ա:"
										+member[ID].memberName+" "+"������"+"*******"+"\n");
								chatContent.append(outLine);
								chatContent.setCaretPosition(chatContent.getText().length());
								//��Ա������ɾ�����߳�Ա��Ϣ
								member[ID].memberIp = null;
								member[ID].memberName = null;
								member[ID].port = -1;
								member[ID].flag = 0;
								//�������Ա�㲥������Ϣ�����³�Ա�ı���
								String D = "D"+outLine;
								String $B = new String();
								for(int j=0; j<100; j++)
								{
									if(member[j].memberName!=null)
									{
										$B = $B+member[j].memberName+"\n";
									}
								}
								memberShow.setText($B);			//���·����Ա�ı���
								String B = "B"+$B;
								for(int i=1; i<100; i++)
								{
									if(member[i].flag == 1)
									{
										returnDp = new DatagramPacket(D.getBytes(), 
												D.getBytes().length, member[i].memberIp, 
												member[i].port);
										returnDs.send(returnDp);
										returnDp = new DatagramPacket(B.getBytes(), 
												B.getBytes().length, member[i].memberIp, 
												member[i].port);
										returnDs.send(returnDp);
									}
								}
							}
							//����յ�I��Ϣ����رռ���������ճ�Ա���л�����
							else if($temp[0] == 'I')
							{
								roomDs.close();
								returnDs.close();
								for(int i=0; i<100; i++)
								{
									if(member[i].flag == 1)
									{
										member[i].memberIp = null;
										member[i].memberName = null;
										member[i].port = -1;
										member[i].flag = 0;
									}
								}
								cardLayout.show(cardPanel, "log");						//��ʾ��¼���
							}
						} 
						catch (IOException e)
						{
							e.printStackTrace();
						}						
					}
				}
			}.start();
		} 
		catch (UnknownHostException | SocketException e) 
		{
			e.printStackTrace();
		}
	}
	
	String publicUserName = null;
	String pubID = null;
	void searchback() {
		int port = Integer.parseInt(portText.getText());							
		String ipAddress = ipInText.getText();
		String userName = nameText.getText();
		publicUserName = new String(userName);
		byte[] buf = new byte[1024];
		int listenPort = -1;
		//�ж�IP��ַ�뻧���Ƿ�Ϊ��
		if(ipAddress==null
				||ipAddress.trim().equals("")
				||userName.trim().equals("��������5���֣�")
				||userName.length()>5
				||userName.trim().equals(""))
		{
			ipInText.setText("������IP��ַ");
			nameText.setText("��������5���֣�");
			portText.setText("������˿ں�");  								
		}
		
		try 
		{
			String fM = "A"+userName;				//���A��Ϊ��ͷ����ʾ����һ��������Ϣ
			InetAddress remoteAddress = InetAddress.getByName(ipAddress);
			DatagramSocket searchDs = new DatagramSocket();
			DatagramPacket searchDp = new DatagramPacket(fM.getBytes(), fM.getBytes().length
					, remoteAddress, port);
			DatagramPacket receDp = new DatagramPacket(buf, buf.length);
			if(searchDs!=null&&!searchDs.isClosed())
			{
				searchDs.send(searchDp);				//�򷿼䷢���û���Ϣ
				//���ܷ��䷵�ص��û���Ϣ�������д�������������Ϣ
				for(int i=0; i<4; i++)
				{
					searchDs.receive(receDp);
					byte[] temp = new byte[1024];
					char[] $temp = null;
					temp = receDp.getData();
					//byte����ת����char����
					{
						Charset cs = Charset.forName("GBK");
						ByteBuffer bb = ByteBuffer.allocate(temp.length);
			        	bb.put(temp);
			        	bb.flip();
			        	CharBuffer cb = cs.decode(bb);
			        	$temp = cb.array();
					}
					//������ص���Ϣ��B,��˵�����뷿��ɹ����л���壬��ʾ�����Ա
					if($temp[0] == 'B')
					{
						cardLayout.show(cardPanel, "chatPanel");				//��ʾ�������
						String memberString = new String(receDp.getData(), 1, 
								receDp.getLength()-1);
						chatMemberShow.setText(memberString);
					}
					//�������C��˵�����뷿��ʧ�ܣ������Ա����
					else if($temp[0] == 'C')
					{
						JOptionPane.showMessageDialog(null, "�����Ա����");  
					}
					//�������D���򽫷�����Ϣ��ʾ�������ı�����
					else if($temp[0] == 'D')
					{
						String chatMessage = new String(receDp.getData(), 1,
								receDp.getLength()-1);
						chatChatContent.append(chatMessage);
						chatChatContent.setCaretPosition(chatChatContent.getText().length());
					}
					//�������E���򽫶˿ںŸ�ֵ��¼����
					else if($temp[0] == 'E')
					{
						String $listenPort = new String(receDp.getData(), 1,
								receDp.getLength()-1);
						 listenPort = Integer.parseInt($listenPort);
					}
					//������ص���F����ID��¼����
					else if($temp[0] == 'F')
					{
						String ID = new String(receDp.getData(), 1,
								receDp.getLength()-1);
						pubID = new String(ID);
					}
				}
				searchDs.close();
				chatBack(listenPort);	
			}
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	DatagramSocket pubChatDs = null;
	//����ҳ���̨��������
	void chatBack(int port) {
		
		try 
		{
			//���������
			DatagramSocket chatDs = new DatagramSocket(port);
			pubChatDs = chatDs;
			new Thread()
			{
				
				DatagramPacket chatDp;
				public void run() {
					
					byte[] buf = new byte[1024];
					chatDp = new DatagramPacket(buf, buf.length);
					//ѭ������������
					while(chatDs!=null && !chatDs.isClosed())
					{
						//��������
						try
						{
							chatDs.receive(chatDp);
						} 
						catch (IOException e)
						{
							e.printStackTrace();
						}
						
						byte[] temp = new byte[1024];
						char[] $temp = null;
						temp = chatDp.getData();
						//byte����ת����char����
						{
							Charset cs = Charset.forName("GBK");
							ByteBuffer bb = ByteBuffer.allocate(temp.length);
							bb.put(temp);
							bb.flip();
							CharBuffer cb = cs.decode(bb);
							$temp = cb.array();
						}
						//������ص���Ϣ��B����ˢ����ʾ�����Ա
						if($temp[0] == 'B')
						{
							String memberString = new String(chatDp.getData(), 1, 
									chatDp.getLength()-1);
							chatMemberShow.setText(memberString);
						}
						//�������D���򽫷�����Ϣ��ʾ�������ı�����
						else if($temp[0] == 'D')
						{
							String chatMessage = new String(chatDp.getData(), 1,
									chatDp.getLength()-1);
							chatChatContent.append(chatMessage);
							chatChatContent.setCaretPosition(chatChatContent.getText().length());
						}
						//�������H����رռ��������л�����
						else if($temp[0] == 'H')
						{
							chatDs.close();
							cardLayout.show(cardPanel, "log");						//��ʾ��¼���
						}
						//�������I���򷿼�رգ�����ı��򣬵�����ʾ���л�����
						else if($temp[0] == 'I')
						{
							JOptionPane.showMessageDialog(null, "�����ѹر�"); 
							chatChatContent.setText("");
							chatMemberShow.setText("");
							chatInputArea.setText("");
							ipInText.setText("������IP��ַ");
							nameText.setText("��������5���֣�");
							portText.setText("������˿ں�");  
							cardLayout.show(cardPanel, "log");						//��ʾ��¼���
						}
					}
				} 
			}.start();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	//����ҳ�淢�Ͱ�ť��̨
	void chatSentButtBack(InetAddress ip, int port) {
		//����D��ͷ����Ϣ
		String $sentMessage = chatInputArea.getText();
		String sentMessage = publicUserName+":\n"+$sentMessage+"\n";
		String D = "D"+sentMessage;
		DatagramPacket sentButtDp = new DatagramPacket(D.getBytes(), 
				D.getBytes().length, ip, port);
		try 
		{
			DatagramSocket sentButtDs = new DatagramSocket();
			sentButtDs.send(sentButtDp);
			sentButtDs.close();
			chatInputArea.setText("");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	//����ҳ���˳���ť��̨
	void chatQuitButtBack(InetAddress ip, int port) 
	{
		//�������ҳ�����������û�йرգ����򷿼䷢�Ϳ�ͷΪG����Ϣ+��ԱID�����������Ϣ������������ı���ͳ�Ա�ı�����������ҳ��
		if(!pubChatDs.isClosed())
		{
			String G = "G"+pubID;
			DatagramPacket cQBBDp = new DatagramPacket(G.getBytes(), 
					G.getBytes().length, ip, port);
			try 
			{
				pubChatDs.send(cQBBDp);
				chatChatContent.setText("");
				chatMemberShow.setText("");
				chatInputArea.setText("");
				ipInText.setText("������IP��ַ");
				nameText.setText("��������5���֣�");
				portText.setText("������˿ں�");  
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	//Ϊ�����˳���ť��Ӻ�̨����
	void roomQuitButtBack()
	{
		//�����г�Ա�㲥Iָ��
		String I = new String("I");
		try {
			DatagramSocket roomCloseDs = new DatagramSocket();
			DatagramPacket roomCloseDp = null;
			for(int i=1; i<100; i++)
			{
				if(member[i].flag == 1)
				{
					roomCloseDp = new DatagramPacket(I.getBytes(), 
								I.getBytes().length, member[i].memberIp, member[i].port);
					roomCloseDs.send(roomCloseDp);
				}
			}
			roomCloseDp = new DatagramPacket(I.getBytes(), 
					I.getBytes().length, InetAddress.getLocalHost(), roomCloseDs.getLocalPort());
			roomCloseDs.send(roomCloseDp);
			//�ı���ȫ�����
			chatContent.setText("");
			ipShow.setText("");
			inputArea.setText("");
			memberShow.setText("");
			//�л�����
			roomCloseDs.close();
			cardLayout.show(cardPanel, "log");				//��ʾ��¼���
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
