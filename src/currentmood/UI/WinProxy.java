package currentmood.UI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import currentmood.util.Proxy;

public class WinProxy extends JFrame {
	
	JLabel lIP, lPort, lUser, lPassword;
	JPanel panel;
	JTextField tfProxy, tfPort, tfUser, tfPassword;
	JButton bOK, bCancel;
	JCheckBox bUseProxy;
	Proxy proxySetting;
	
	public WinProxy()
	{
		
	
		this.setTitle("#CurrentMood - Modification du proxy");
		this.setSize(500,300);
		this.panel = new JPanel();
		this.panel.setLayout( new BoxLayout(this.panel,BoxLayout.Y_AXIS));
		this.lIP = new JLabel("Adresse IP : ");
		this.lPort = new JLabel("Numéro du port : ");
		this.lUser = new JLabel("Utilisateur : ");
		this.lPassword = new JLabel("Mot de passe : ");
		this.tfProxy = new JTextField();
		this.tfProxy.setSize(700,70);
		this.tfPort= new JTextField();
		this.tfUser = new JTextField();
		this.tfPassword = new JTextField();
		this.bOK = new JButton("OK");
		this.bOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				WinProxy.this.proxySetting = WinProxy.this.changeProxy(WinProxy.this.tfProxy.getText(),WinProxy.this.tfPort.getText(), WinProxy.this.tfUser.getText(), WinProxy.this.tfPassword.getText());
				
			}
		});
		this.bCancel = new JButton("Annuler");
		this.bUseProxy= new JCheckBox("Utiliser un serveur proxy");
		this.panel.add(this.lIP);
		this.panel.add(this.tfProxy);
		this.panel.add(this.lPort);
		this.panel.add(this.tfPort);
		this.panel.add(this.lUser);
		this.panel.add(this.tfUser);
		this.panel.add(this.lPassword);
		this.panel.add(this.tfPassword);
		this.panel.add(bUseProxy);
		this.panel.add(bOK);
		this.panel.add(bCancel);
		this.add(this.panel, BorderLayout.CENTER);
		this.setVisible(true);
	
	}
	
	private Proxy changeProxy(String ip, String port, String user, String password)
	{
		if(this.bUseProxy.isSelected())
		{
			if(user.isEmpty())
				return new Proxy(ip,Integer.parseInt(port));
			else 
				return new Proxy(ip, Integer.parseInt(port), user, password);
		
		}
		else return new Proxy();
	}
	

}
