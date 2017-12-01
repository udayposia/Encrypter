import java.awt.event.*;
import java.awt.*;
import java.io.*;
class Soft implements ActionListener
{
	File F;
	Label l1;
	Label l2,password,warning;
	FileInputStream fin;
	FileOutputStream fout;
	Frame f,eframe,dframe;
	Button encrypt,decrypt,submit,goback,b6,b;
	TextField epf,dpf,passwordfield,warningfield;
	char code[];
	int checksum;
	Soft()
	{
		String codee="1)ecr`/pt";
		code=codee.toCharArray();
		l1=new Label("Path: ");
		l2=new Label("Path: ");
		f=new Frame("Main Window");
		Color c=new Color(64,164,223);
		f.setBackground(c);
		l1.setBounds(40,50,50,50);
		l2.setBounds(40,200,50,50);
		f.add(l1);f.add(l2);
		encrypt=new Button("Encrypt");
		decrypt=new Button("Decrypt");
		epf=new TextField();
		epf.setBounds(100,60,200,25);
		dpf=new TextField();
		dpf.setBounds(100,210,200,25);
		encrypt.setBounds(100,100,50,30);
		decrypt.setBounds(100,250,50,30);
		
		FileDialog fdl=new FileDialog(f,"Select file" );
	    b6=new Button("Open File");
	    b6.setBounds(305,60, 60, 30);
	    b6.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent ev){
	    		 fdl.setVisible(true);
	 	        epf.setText(fdl.getDirectory() + fdl.getFile());

	    	}
	    });
	    f.add(b6);
	    FileDialog fdl2=new FileDialog(f,"Select file" );
	    b=new Button("Open File");
	    b.setBounds(305,210 , 60, 30);
	    b.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent ev){
	    		 fdl2.setVisible(true);
	 	        dpf.setText(fdl2.getDirectory() + fdl2.getFile());

	    	}
	    });
	    f.add(b);
		f.add(epf);
		f.add(dpf);
		f.add(encrypt);
		f.add(decrypt);
		encrypt.addActionListener(this);
		decrypt.addActionListener(this);
		f.setSize(400,400);
		f.setLocationRelativeTo(null);
		f.setLayout(null);
		f.setVisible(true);
		f.addWindowListener(new WindowAdapter()
				{
					public void windowClosing(WindowEvent e)
					{
						f.dispose();
					}
				});
		
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==encrypt)
		{
			if(epf.getText().length()<2)
			{}
			else{
			f.setVisible(false);
			eframe=new Frame("Encryption");
			Color c=new Color(64,164,223);
			eframe.setBounds(475,170, 400, 400);
			eframe.setBackground(c);
			password=new Label("Password:");
			warning=new Label("Warning:");
			passwordfield=new TextField();
			warningfield=new TextField();
			warningfield.setEditable(false);
			submit=new Button("submit");
			eframe.add(password);eframe.add(passwordfield);
			eframe.add(submit);
			password.setBounds(70,80,60,25);
			passwordfield.setBounds(140,80,120,30);
			warning.setBounds(40,200,80,50);
			warningfield.setBounds(130,200,200,30);
			submit.setBounds(130,130,50,30);
			goback=new Button("Back");
			goback.setBounds(10,40,50,25);
			eframe.add(goback);
			eframe.setLocationRelativeTo(null);
			goback.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							f.setVisible(true);
							eframe.setVisible(false);
						}
					});
			submit.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							if(passwordfield.getText().length()<8)
							{
								eframe.add(warning);eframe.add(warningfield);
								warningfield.setText("Password length must be 8 characters");
							}
							else
							{
								int count=0;
								boolean set=true;
								try{fin=new FileInputStream(epf.getText());
								for(int i=0;i<code.length;i++)
								{
									if(code[i]==(char)fin.read())
										count++;
									if(count==code.length)
										set=false;
								}}catch(IOException error){}
								
								eframe.add(warning);eframe.add(warningfield);
							if(set)
							{	warning.setText("Status:");
								warningfield.setText("Encryption Started");
								
								try
								{
									encryptData(epf.getText(),passwordfield.getText());
								}catch(IOException error){};
								warningfield.setText("File Encrypted!");
							}else
							{
								warning.setText("warning");
								warningfield.setText("File already encrypted!");
							}
							}
						}
					});
			//eframe.setSize(400,400);
			eframe.setLayout(null);
			eframe.setVisible(true);
			eframe.addWindowListener(new WindowAdapter()
					{
						public void windowClosing(WindowEvent e)
						{
							eframe.dispose();
						}
					});
			
		}}
		if(e.getSource()==decrypt)
		{
			if(dpf.getText().length()<2){}
			else{
			f.setVisible(false);
			dframe=new Frame("Encryption");
			Color c=new Color(64,164,223);
			dframe.setBackground(c);
			dframe.setBounds(475,170, 400, 400);
			password=new Label("Password:");
			warning=new Label("Warning:");
			passwordfield=new TextField();
			warningfield=new TextField();
			warningfield.setEditable(false);
			submit=new Button("Submit");
			dframe.add(password);dframe.add(passwordfield);
			dframe.add(submit);
			password.setBounds(80,80,65,25);
			passwordfield.setBounds(140,80,120,25);
			warning.setBounds(40,200,80,50);
			warningfield.setBounds(130,200,200,30);
			submit.setBounds(140,120,50,25);
			goback=new Button("Back");
			goback.setBounds(10,40,50,30);
			dframe.add(goback);
			dframe.setLocationRelativeTo(null);
			goback.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							f.setVisible(true);
							dframe.setVisible(false);
						}
					});
			submit.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							try{
								int n=decryptData(dpf.getText(),passwordfield.getText());
								dframe.add(warning);dframe.add(warningfield);
								if(n==0)
								{
									warning.setText("warning:");
									warningfield.setText("File is already decrypted!");
								}
								if(n==1)
								{
									warning.setText("Error:");
									warningfield.setText("Password Incorrect!!");
								}
								if(n==2)
								{
									warning.setText("Status");
									warningfield.setText("Data Decrypted!");
								}
								
							}catch(IOException error){}
						}
					});
			//dframe.setSize(400,400);
			dframe.setLayout(null);
			dframe.setVisible(true);
			dframe.addWindowListener(new WindowAdapter()
					{
						public void windowClosing(WindowEvent e)
						{
							dframe.dispose();
						}
					});
		}}
	}
	public void encryptData(String path,String pass) throws IOException
	{
		checksum=0;
		char passchar[]=pass.toCharArray();
		for(int i=0;i<passchar.length;i++)
		{
			checksum=checksum+passchar[i];
		}
		fin=new FileInputStream(path);
		F=new File(path);
		char tempdata[]=new char[(int)F.length()];
		for(int i=0;i<tempdata.length;i++)
		{
			tempdata[i]=(char)fin.read();
		}
		fin.close();
		fout=new FileOutputStream(path);
		for(int i=0;i<code.length;i++)
		{
			fout.write(code[i]);
		}
		char c;
		for(int i=0;i<passchar.length;i++)
		{
			passchar[i]+=53;
			c=(char)(passchar[i]);
			fout.write(c);
		}
		for(int i=0;i<tempdata.length;i++)
		{
			fout.write(tempdata[i]+checksum);
		}
		fout.close();
	}
	public int decryptData(String path,String pass) throws IOException
	{
		checksum=0;
		fin=new FileInputStream(path);
		F=new File(path);
		for(int i=0;i<code.length;i++)
		{
			if(code[i]!=(char)fin.read())
				return 0;
		}
		char passchar[]=pass.toCharArray();
		for(int i=0;i<passchar.length;i++)
			checksum=checksum+passchar[i];
		char filepass[]=new char[passchar.length];
		char c;
		for(int i=0;i<passchar.length;i++)
		{
			filepass[i]=(char)(fin.read()-53);
			c=(char)(passchar[i]);
			if(filepass[i]!=c)
			{
				return 1;
			}
		}
		char tempdata[]=new char[(int)F.length()-passchar.length-code.length];
		for(int i=0;i<tempdata.length;i++)
		{
			tempdata[i]=(char)fin.read();
		}
		fin.close();
		fout=new FileOutputStream(path);
		for(int i=0;i<tempdata.length;i++)
		{
			fout.write(tempdata[i]-checksum);
		}
		fout.close();
		return 2;
	}
}

class Software
{
	public static void main(String[] args)
	{
		new Soft();
	}
}