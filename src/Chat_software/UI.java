package Chat_software;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import javax.swing.*;

//A发送用户信息
//B进入房间成功返回房间所有成员的名称
//C加入房间失败
//D聊天信息
//E返回成员监听端口
//F成员ID信息
//G成员下线信息
//H成员下线指令
//I房间关闭

class Member{
	public String memberName = null;
	public InetAddress memberIp = null;
	public int port = -1;
	public int flag = 0;
}

public class UI extends JFrame{
	private JPanel log = new JPanel();			//创建登陆面板
	private JPanel search = new JPanel();		//创建搜索面板
	private JPanel room = new JPanel();			//创建房间面板
	private JPanel cardPanel = new JPanel();	//创建卡片面板
	private JPanel chatPanel = new JPanel();  	//创建聊天面板
	//创建成员数组	
	Member[] member = new Member[100];			
	{
		for(int i=0; i<100; i++)
		{
			member[i] = new Member();
		}
	}
	Button searb = new Button("加入聊天室");		//创建加入聊天室按钮
	Button roomb = new Button("创建聊天室");		//创建创建聊天室按钮
	CardLayout cardLayout = new CardLayout();	//创建卡片布局对象
	
	//搜索面板的内容
	Label ipLabel = new Label("请输入聊天室地址信息", Label.CENTER);	//创建IP地址提示标签
	JPanel ipInPanel = new JPanel();							//创建IP输入面板
	Label ipInLabel = new Label("IP地址");						//创建IP输入提示标签
	JTextField ipInText = new JTextField(20);					//创建IP地址输入文本框
	JPanel sButtPanel = new JPanel();							//创建按钮面板
	Button ipSearButt = new Button("确定");						//创建搜索按钮
	Button backButt = new Button("返回");							//创建返回按钮
	JTextField nameText = new JTextField();						//创建昵称文本框
	JTextField portText = new JTextField();						//创建端口文本框
	JPanel LabPanel = new JPanel(); 							//创建标签面板
	Label nameInLabel = new Label("昵称");						//创建昵称提示标签
	Label portInLabel = new Label("端口");						//创建端口提示标签
	
	
	//room面板的内容
	JPanel showPanel = new JPanel();						//创建显示面板
	JTextArea chatContent = new JTextArea(17, 41);			//创建聊天内容文本域
	JTextField ipShow = new JTextField();					//创建IP显示文本框
	JTextArea inputArea = new JTextArea(5, 41); 			//创建输入文本域
	JPanel rButtPanel = new JPanel(); 						//创建按钮面板
	Button sentButt = new Button("发送"); 					//创建发送按钮
	Button quitButt = new Button("退出");						//创建退出按钮
	JTextArea memberShow = new JTextArea(22, 15); 			//创建成员显示文本域
	JPanel leftPanel = new JPanel();                        //创建左边面板
	JPanel rightPanel = new JPanel();                       //创建右边面板
	Label memberLabel = new Label("成员", Label.CENTER);		//创建成员标签
	
	//聊天面板
	JPanel chatShowPanel = new JPanel();						//创建显示面板
	JTextArea chatChatContent = new JTextArea(19, 41);			//创建聊天内容文本域
	JTextArea chatInputArea = new JTextArea(5, 41); 			//创建输入文本域
	JPanel chatRButtPanel = new JPanel(); 						//创建按钮面板
	Button chatSentButt = new Button("发送"); 					//创建发送按钮
	Button chatQuitButt = new Button("退出");						//创建退出按钮
	JTextArea chatMemberShow = new JTextArea(22, 15); 			//创建成员显示文本域
	JPanel chatLeftPanel = new JPanel();                        //创建左边面板
	JPanel chatRightPanel = new JPanel();                       //创建右边面板
	Label chatMemberLabel = new Label("成员", Label.CENTER);		//创建成员标签
	
	UI(){
		
		//改变swing控件的风格为当前系统(windows 10)风格的代码
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		//UI设置
		this.setLayout(new GridLayout(1, 1));	//面板布局设置为网格布局
		this.setTitle("聊天室");				
		this.setSize(750, 525);					//设置窗口大小
		this.setLocationRelativeTo(null);		//设置窗口出现位置
		this.add(cardPanel);					//添加卡片面板
		
		cardPanel.setLayout(cardLayout);		//设置卡片面板的布局为卡片布局
		cardPanel.add(log, "log");				//添加登陆面板
		cardPanel.add(search, "search");		//添加搜索面板
		cardPanel.add(room, "room");			//添加房间面板
		cardPanel.add(chatPanel, "chatPanel"); 	//添加聊天面板
		
		log.setLayout(new GridLayout(1, 2)); 			//设置登陆面板为网络布局模式
		log.setFont(new Font("黑体", Font.BOLD, 40));		//设置登陆面板的字体大小
		log.add(searb);									//添加加入聊天室按钮
		log.add(roomb);									//添加创建聊天室按钮
		
		//为加入按钮创建鼠标监听器
		searb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "search");	//显示搜索面板
			}
		});
		
		//为房间按钮创建鼠标监听器
		roomb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//创建房间用户输入自己的昵称
				String roomUserName = JOptionPane.showInputDialog( "请输入你的昵称" );
				if(roomUserName!=null && !roomUserName.equals(""))
				{
					member[0].memberName = new String(roomUserName);
					member[0].flag = 1;
					memberShow.setText(roomUserName);
					//切换界面
					cardLayout.show(cardPanel, "room");		//显示房间面板
					chatContent.append("\t"+"-------"+"成员："+member[0].memberName+" "+"已创建房间"
					+"-------"+"\n");
					roomback();
				}
			}
		});
		
		
		//搜索面板设置
		search.setLayout(new BorderLayout());						//搜索面板设置为边界布局
		
		ipLabel.setFont(new Font("黑体", Font.BOLD, 40));				//设置标签字体大小
		ipInLabel.setFont(new Font("黑体", Font.BOLD, 30));	 		//设置IP输入提示字体大小
		nameInLabel.setFont(new Font("黑体", Font.BOLD, 30));	 		//设置昵称提示标签字体大小
		portInLabel.setFont(new Font("黑体", Font.BOLD, 30));	 		//设置端口提示标签字体大小
		ipInText.setFont(new Font("黑体", Font.BOLD, 30)); 			//设置IP文本框的字体大小
		ipInPanel.setLayout(new BorderLayout());	 				//设置IP输入面板布局为边界布局
		sButtPanel.setLayout(new FlowLayout());	 					//设置按钮面板布局为流式布局
		ipSearButt.setFont(new Font("黑体", Font.BOLD, 30));	 		//设置搜索按钮字体
		backButt.setFont(new Font("黑体", Font.BOLD, 30)); 			//设置返回按钮字体
		nameText.setFont(new Font("黑体", Font.BOLD, 30));	 		//设置昵称文本框字体
		nameText.setText("（不超过5个字）");  				//设置昵称文本框提示
		portText.setFont(new Font("黑体", Font.BOLD, 30));	 		//设置端口文本框字体
		portText.setText("");  										//设置端口文本框提示
		LabPanel.setLayout(new BorderLayout()); 					//设置标签面板布局为边界布局
		
		search.add(ipLabel, BorderLayout.NORTH);					//添加IP提示标签，位置设置在北部
		search.add(ipInPanel, BorderLayout.CENTER);					//添加IP输入面板，位置设置在中部
		search.add(sButtPanel, BorderLayout.SOUTH);					//添加按钮面板，位置设置在南部
		search.add(LabPanel, BorderLayout.WEST); 					//添加标签面板，位置设置在西部
		
		ipInPanel.add(ipInText, BorderLayout.CENTER); 				//添加IP文本框，设置位置在中间
		ipInPanel.add(nameText, BorderLayout.NORTH); 				//添加昵称文本框字体，设置位置在北部
		ipInPanel.add(portText, BorderLayout.SOUTH); 				//添加端口文本框字体，设置位置在南部
		
		LabPanel.add(nameInLabel, BorderLayout.NORTH);				//添加昵称提示标签，设置位置在北部
		LabPanel.add(ipInLabel, BorderLayout.CENTER); 				//添加IP输入提示，设置位置在中间
		LabPanel.add(portInLabel, BorderLayout.SOUTH);				//添加端口提示标签，位置设置在南部
		
		sButtPanel.add(ipSearButt);									//添加搜索按钮
		sButtPanel.add(backButt);									//添加返回按钮
		
		//为返回按钮创建鼠标监听器
		backButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "log");					//显示登录面板
			}
		});
		
		//为确定按钮创建鼠标监听器
		ipSearButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchback();
			}
		});
		
		
		//房间面板设置
		room.setLayout(new BorderLayout()); 					//房间面板设置成边界布局
		
		showPanel.setLayout(new BorderLayout());       			//显示面板设置为边界布局
		chatContent.setEditable(false); 						//聊天内容文本域设置成不可编辑
		ipShow.setEditable(false); 								//IP显示文本框设置成不可编辑
		memberShow.setEditable(false); 							//成员显示文本域设置成不可编辑
		ipShow.setFont(new Font("黑体", Font.BOLD, 30));	 		//设置IP显示文本框字体
		rButtPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); //设置按钮面板为流式布局
		sentButt.setFont(new Font("黑体", Font.PLAIN, 15)); 		//设置发送按钮字体
		quitButt.setFont(new Font("黑体", Font.PLAIN, 15)); 		//设置退出按钮字体
		chatContent.setFont(new Font("黑体", Font.PLAIN, 15));	//设置聊天内容文本域字体大小
		chatContent.setBackground(Color.LIGHT_GRAY);		  	//设置聊天内容文本域的背景色
		memberShow.setFont(new Font("黑体", Font.PLAIN, 15));		//设置成员显示文本域字体大小
		memberShow.setBackground(Color.GRAY);		  			//设置成员显示文本域的背景色
		leftPanel.setLayout(new BorderLayout()); 				//左边面板设置为边界布局
		inputArea.setFont(new Font("黑体", Font.PLAIN, 15));		//设置输入文本域字体大小
		rightPanel.setLayout(new BorderLayout());       		//右边面板设置为边界布局
		memberLabel.setFont(new Font("黑体", Font.PLAIN, 15));	//设置成员标签字体大小
		
		room.add(leftPanel, BorderLayout.CENTER); 				//添加左边面板，位置设置在中部
		room.add(rightPanel, BorderLayout.EAST); 				//添加右边面板，位置设置在东部
		
		leftPanel.add(showPanel, BorderLayout.NORTH);			//添加显示面板，位置设置在北部
		leftPanel.add(inputArea, BorderLayout.CENTER); 			//添加输入文本域，位置设置在中部
		leftPanel.add(rButtPanel, BorderLayout.SOUTH); 			//添加按钮面板，位置设置在南部
		
		rightPanel.add(memberLabel, BorderLayout.NORTH); 		//添加成员标签，位置设置在北部
		rightPanel.add(memberShow, BorderLayout.CENTER); 		//添加成员显示文本域，位置设置在东部
		
		showPanel.add(chatContent, BorderLayout.CENTER);		//添加聊天内容文本域，位置设置在中间
		showPanel.add(ipShow, BorderLayout.NORTH); 				//添加IP显示文本框，位置设置在北部
		
		rButtPanel.add(quitButt);    							//添加返回按钮
		rButtPanel.add(sentButt); 								//添加发送按钮
		
		
		
		//为退出按钮创建鼠标监听器
		quitButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				roomQuitButtBack();
				cardLayout.show(cardPanel, "log");				//显示登录面板
			}
		});
		
		//为发送按钮创建鼠标监听器
		ipSearButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//发送按钮按下后的操作
			}
		});
		
		
		//聊天面板设置
		chatPanel.setLayout(new BorderLayout()); 						//聊天面板设置成边界布局
		
		chatShowPanel.setLayout(new BorderLayout());       				//显示面板设置为边界布局
		chatChatContent.setEditable(false); 							//聊天内容文本域设置成不可编辑
		chatMemberShow.setEditable(false); 								//成员显示文本域设置成不可编辑
		chatRButtPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); 	//设置按钮面板为流式布局
		chatSentButt.setFont(new Font("黑体", Font.PLAIN, 15)); 			//设置发送按钮字体
		chatQuitButt.setFont(new Font("黑体", Font.PLAIN, 15)); 			//设置退出按钮字体
		chatChatContent.setFont(new Font("黑体", Font.PLAIN, 15));		//设置聊天内容文本域字体大小
		chatChatContent.setBackground(Color.LIGHT_GRAY);		  		//设置聊天内容文本域的背景色
		chatMemberShow.setFont(new Font("黑体", Font.PLAIN, 15));			//设置成员显示文本域字体大小
		chatMemberShow.setBackground(Color.GRAY);		  				//设置成员显示文本域的背景色
		chatLeftPanel.setLayout(new BorderLayout()); 					//左边面板设置为边界布局
		chatInputArea.setFont(new Font("黑体", Font.PLAIN, 15));			//设置输入文本域字体大小
		chatRightPanel.setLayout(new BorderLayout());       			//右边面板设置为边界布局
		chatMemberLabel.setFont(new Font("黑体", Font.PLAIN, 15));		//设置成员标签字体大小
			
		chatPanel.add(chatLeftPanel, BorderLayout.CENTER); 				//添加左边面板，位置设置在中部
		chatPanel.add(chatRightPanel, BorderLayout.EAST); 				//添加右边面板，位置设置在东部
		
		chatLeftPanel.add(chatShowPanel, BorderLayout.NORTH);			//添加显示面板，位置设置在北部
		chatLeftPanel.add(chatInputArea, BorderLayout.CENTER); 			//添加输入文本域，位置设置在中部
		chatLeftPanel.add(chatRButtPanel, BorderLayout.SOUTH); 			//添加按钮面板，位置设置在南部
		
		chatRightPanel.add(chatMemberLabel, BorderLayout.NORTH); 		//添加成员标签，位置设置在北部
		chatRightPanel.add(chatMemberShow, BorderLayout.CENTER); 		//添加成员显示文本域，位置设置在东部
		
		chatShowPanel.add(chatChatContent, BorderLayout.CENTER);		//添加聊天内容文本域，位置设置在中间
		
		chatRButtPanel.add(chatQuitButt);    							//添加返回按钮
		chatRButtPanel.add(chatSentButt); 								//添加发送按钮
		
		
		
		//为退出按钮创建鼠标监听器
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
				cardLayout.show(cardPanel, "log");						//显示登录面板
			}
		});
		
		//为发送按钮创建鼠标监听器
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
		
		
		//窗口关闭监听器
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Window window = (Window) e.getComponent();
				window.dispose();
				//如果聊天界面监听流还在，则将其关闭
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
				//如果房间监听流还在则调用方法将其关闭
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
			DatagramSocket roomDs = new DatagramSocket();     	//创建监听socket
			DatagramSocket returnDs = new DatagramSocket();		//创建返回socket
			pubRoomDs = roomDs;
			localAddress = InetAddress.getLocalHost();			
			port = roomDs.getLocalPort();
			localIp = "IP:"+localAddress.getHostAddress()+"端口："+port;
			ipShow.setText(localIp);
			
			new Thread() 
			{
				DatagramPacket roomDp; 				//创建监听信息包
				DatagramPacket returnDp;			//创建返回信息包
				public void run() 
				{
					byte[] buf = new byte[1024];
					byte[] temp = new byte[1024];						//临时存储的数组
					char[] $temp = null;
					roomDp = new DatagramPacket(buf, buf.length);
					
					while(!roomDs.isClosed()) 
					{
						try 
						{
							//收到数据后的处理
							roomDs.receive(roomDp);
							temp = roomDp.getData();
							//byte数组转化成char数组
							{
								Charset cs = Charset.forName("GBK");
						        ByteBuffer bb = ByteBuffer.allocate(temp.length);
						        bb.put(temp);
						        bb.flip();
						        CharBuffer cb = cs.decode(bb);
						        $temp = cb.array();
							}
							//如果收到的是A则表明是上线操作，进行相应处理
							if($temp[0]=='A')
							{
								int count = 1;
								//遍历成员数组，找到是空闲的元素，将其标志改为1,并为其赋值,返回B
								for(count = 1; count < 100; count++)
								{
									if(member[count].flag == 0)
									{
										member[count].memberIp = roomDp.getAddress();
										member[count].memberName = new String(roomDp.getData(), 
												1,roomDp.getLength()-1);
										member[count].port = roomDp.getPort();
										member[count].flag = 1;
										//发送用户监听的端口号
										String E = "E"+member[count].port;
										returnDp = new DatagramPacket(E.getBytes(), 
												E.getBytes().length, member[count].memberIp, 
												member[count].port);
										returnDs.send(returnDp);
										//发送用户ID
										String F = "F"+count;
										returnDp = new DatagramPacket(F.getBytes(), 
												F.getBytes().length, member[count].memberIp, 
												member[count].port);
										returnDs.send(returnDp);
										//刷新成员域，并将房间所有成员信息返回
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
											//聊天文本域显示上线信息
											String newMemberMessage = new String("\t"+"-------"+"成员："+member[count].memberName
													+" "+"已上线"+"-------"+"\n");
											chatContent.append(newMemberMessage);
											chatContent.setCaretPosition(chatContent.getText().length());
											memberShow.setText(memberString);
											String B = "B"+memberString;
											//向房间里除自身外的所有的成员发送更新后的信息
											for(int j=1; j<100; j++)
											{
												if(member[j].flag == 1)
												{
													returnDp = new DatagramPacket(B.getBytes(), 
													B.getBytes().length, member[j].memberIp, 
													member[j].port);
													returnDs.send(returnDp);
													//发送成员上线信息
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
								//如果count为100则说明成员已经满了，返回C
								if(count == 100)
								{
									String C = new String("C");
									returnDp = new DatagramPacket(C.getBytes(), 
											C.getBytes().length, roomDp.getAddress(), 
											roomDp.getPort());
									returnDs.send(returnDp);
								}
							}
							//如果收到的信息是D开头
							else if($temp[0]=='D')
							{
								//显示在聊天文本域中，并向其他成员广播
								String chatMessage = new String(roomDp.getData(), 1,
										roomDp.getLength()-1);
								//聊天文本域显示消息
								chatContent.append(chatMessage);
								chatContent.setCaretPosition(chatContent.getText().length());
								//向其他成员广播开头为D的信息
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
							//如果收到的信息是G开头
							else if($temp[0] == 'G')
							{
								//将收到的ID信息转换成int型
								String IDStr = new String(roomDp.getData(), 1,
										roomDp.getLength()-1);
								int ID = Integer.parseInt(IDStr);
								//向下线成员下达下线指令
								String H = new String("H");
								returnDp = new DatagramPacket(H.getBytes(), 
										H.getBytes().length, member[ID].memberIp, 
										member[ID].port);
								returnDs.send(returnDp);
								//文本域显示下线信息
								String outLine = new String("\t"+"*******"+"成员:"
										+member[ID].memberName+" "+"已下线"+"*******"+"\n");
								chatContent.append(outLine);
								chatContent.setCaretPosition(chatContent.getText().length());
								//成员数组中删除下线成员信息
								member[ID].memberIp = null;
								member[ID].memberName = null;
								member[ID].port = -1;
								member[ID].flag = 0;
								//向其余成员广播下线信息，更新成员文本域
								String D = "D"+outLine;
								String $B = new String();
								for(int j=0; j<100; j++)
								{
									if(member[j].memberName!=null)
									{
										$B = $B+member[j].memberName+"\n";
									}
								}
								memberShow.setText($B);			//更新房间成员文本域
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
							//如果收到I消息，则关闭监听流，清空成员，切换界面
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
								cardLayout.show(cardPanel, "log");						//显示登录面板
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
		//判断IP地址与户名是否为空
		if(ipAddress==null
				||ipAddress.trim().equals("")
				||userName.trim().equals("（不超过5个字）")
				||userName.length()>5
				||userName.trim().equals(""))
		{
			ipInText.setText("请输入IP地址");
			nameText.setText("（不超过5个字）");
			portText.setText("请输入端口号");  								
		}
		
		try 
		{
			String fM = "A"+userName;				//添加A作为开头，表示则是一条上线信息
			InetAddress remoteAddress = InetAddress.getByName(ipAddress);
			DatagramSocket searchDs = new DatagramSocket();
			DatagramPacket searchDp = new DatagramPacket(fM.getBytes(), fM.getBytes().length
					, remoteAddress, port);
			DatagramPacket receDp = new DatagramPacket(buf, buf.length);
			if(searchDs!=null&&!searchDs.isClosed())
			{
				searchDs.send(searchDp);				//向房间发送用户信息
				//接受房间返回的用户信息，并进行处理，接受两次信息
				for(int i=0; i<4; i++)
				{
					searchDs.receive(receDp);
					byte[] temp = new byte[1024];
					char[] $temp = null;
					temp = receDp.getData();
					//byte数组转化成char数组
					{
						Charset cs = Charset.forName("GBK");
						ByteBuffer bb = ByteBuffer.allocate(temp.length);
			        	bb.put(temp);
			        	bb.flip();
			        	CharBuffer cb = cs.decode(bb);
			        	$temp = cb.array();
					}
					//如果返回的信息是B,则说明加入房间成功，切换面板，显示房间成员
					if($temp[0] == 'B')
					{
						cardLayout.show(cardPanel, "chatPanel");				//显示聊天面板
						String memberString = new String(receDp.getData(), 1, 
								receDp.getLength()-1);
						chatMemberShow.setText(memberString);
					}
					//如果返回C则说明加入房间失败，房间成员已满
					else if($temp[0] == 'C')
					{
						JOptionPane.showMessageDialog(null, "房间成员已满");  
					}
					//如果返回D，则将返回信息显示在聊天文本域中
					else if($temp[0] == 'D')
					{
						String chatMessage = new String(receDp.getData(), 1,
								receDp.getLength()-1);
						chatChatContent.append(chatMessage);
						chatChatContent.setCaretPosition(chatChatContent.getText().length());
					}
					//如果返回E，则将端口号赋值记录下来
					else if($temp[0] == 'E')
					{
						String $listenPort = new String(receDp.getData(), 1,
								receDp.getLength()-1);
						 listenPort = Integer.parseInt($listenPort);
					}
					//如果返回的是F，则将ID记录下来
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
	//聊天页面后台监听方法
	void chatBack(int port) {
		
		try 
		{
			//网络监听流
			DatagramSocket chatDs = new DatagramSocket(port);
			pubChatDs = chatDs;
			new Thread()
			{
				
				DatagramPacket chatDp;
				public void run() {
					
					byte[] buf = new byte[1024];
					chatDp = new DatagramPacket(buf, buf.length);
					//循环监听网络流
					while(chatDs!=null && !chatDs.isClosed())
					{
						//接受数据
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
						//byte数组转化成char数组
						{
							Charset cs = Charset.forName("GBK");
							ByteBuffer bb = ByteBuffer.allocate(temp.length);
							bb.put(temp);
							bb.flip();
							CharBuffer cb = cs.decode(bb);
							$temp = cb.array();
						}
						//如果返回的信息是B，则刷新显示房间成员
						if($temp[0] == 'B')
						{
							String memberString = new String(chatDp.getData(), 1, 
									chatDp.getLength()-1);
							chatMemberShow.setText(memberString);
						}
						//如果返回D则，则将返回信息显示在聊天文本域中
						else if($temp[0] == 'D')
						{
							String chatMessage = new String(chatDp.getData(), 1,
									chatDp.getLength()-1);
							chatChatContent.append(chatMessage);
							chatChatContent.setCaretPosition(chatChatContent.getText().length());
						}
						//如果返回H，则关闭监听流，切换界面
						else if($temp[0] == 'H')
						{
							chatDs.close();
							cardLayout.show(cardPanel, "log");						//显示登录面板
						}
						//如果返回I，则房间关闭，清空文本域，弹出提示框，切换界面
						else if($temp[0] == 'I')
						{
							JOptionPane.showMessageDialog(null, "房间已关闭"); 
							chatChatContent.setText("");
							chatMemberShow.setText("");
							chatInputArea.setText("");
							ipInText.setText("请输入IP地址");
							nameText.setText("（不超过5个字）");
							portText.setText("请输入端口号");  
							cardLayout.show(cardPanel, "log");						//显示登录面板
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
	
	//聊天页面发送按钮后台
	void chatSentButtBack(InetAddress ip, int port) {
		//发送D开头的消息
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
	
	//聊天页面退出按钮后台
	void chatQuitButtBack(InetAddress ip, int port) 
	{
		//如果聊天页面网络监听流没有关闭，则向房间发送开头为G的信息+成员ID，表达下线信息，，清空聊天文本域和成员文本域，重置搜索页面
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
				ipInText.setText("请输入IP地址");
				nameText.setText("（不超过5个字）");
				portText.setText("请输入端口号");  
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	//为房间退出按钮添加后台方法
	void roomQuitButtBack()
	{
		//向所有成员广播I指令
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
			//文本域全部清空
			chatContent.setText("");
			ipShow.setText("");
			inputArea.setText("");
			memberShow.setText("");
			//切换界面
			roomCloseDs.close();
			cardLayout.show(cardPanel, "log");				//显示登录面板
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
