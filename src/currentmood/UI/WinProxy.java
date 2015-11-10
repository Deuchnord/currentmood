package currentmood.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import currentmood.util.Proxy;

public class WinProxy extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lIP, lPort, lUser, lPassword;
	JPanel panel, panelForButton;
	JTextField tfProxy, tfPort, tfUser, tfPassword;
	JButton bOK, bCancel, bSetProxyUnivLille1;
	JCheckBox bUseProxy;
	Proxy proxySetting;
	Win winMother;
	
	public WinProxy(Win mother)
	{
		
		this.winMother=mother;
		this.setTitle("#CurrentMood - Modification du proxy");
		this.setSize(500,300);
		this.setResizable(false);
		this.panel = new JPanel();
		this.panel.setLayout( new BoxLayout(this.panel,BoxLayout.Y_AXIS));
		this.panelForButton = new JPanel();
		this.panelForButton.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.lIP = new JLabel("Adresse IP : ");
		this.lPort = new JLabel("Numéro du port : ");
		this.lUser = new JLabel("Utilisateur : ");
		this.lPassword = new JLabel("Mot de passe : ");
		this.tfProxy = new JTextField();
		this.tfProxy.setEnabled(false);
		this.tfPort= new JTextField();
		this.tfPort.setEnabled(false);
		this.tfUser = new JTextField();
		this.tfUser.setEnabled(false);
		this.tfPassword = new JTextField();
		this.tfPassword.setEnabled(false);
		this.bOK = new JButton("OK");
		this.bOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				WinProxy.this.winMother.cmTwitter.setProxy(WinProxy.this.changeProxy(WinProxy.this.tfProxy.getText(),WinProxy.this.tfPort.getText(), WinProxy.this.tfUser.getText(), WinProxy.this.tfPassword.getText()));
				//WinProxy.this.winMother.cmTwitter.connect();
				WinProxy.this.dispose();
			}
		});
		this.bCancel = new JButton("Annuler");
		this.bCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				WinProxy.this.dispose();
				
			}
		});
		
		this.bSetProxyUnivLille1 = new JButton("Univ Lille 1");
		this.bSetProxyUnivLille1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				WinProxy.this.tfProxy.setText("cache-etu.univ-lille1.fr");
				WinProxy.this.tfPort.setText("3128");
				if(!WinProxy.this.bUseProxy.isSelected())
					WinProxy.this.bUseProxy.doClick();
			}
		});
		
		this.bUseProxy= new JCheckBox("Utiliser un serveur proxy");
		this.bUseProxy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				WinProxy.this.enableField(WinProxy.this.bUseProxy.isSelected());
				
			}
		});
		this.panel.add(this.lIP);
		this.panel.add(this.tfProxy);
		this.panel.add(this.lPort);
		this.panel.add(this.tfPort);
		this.panel.add(this.lUser);
		this.panel.add(this.tfUser);
		this.panel.add(this.lPassword);
		this.panel.add(this.tfPassword);
		this.panel.add(bUseProxy);
		this.panelForButton.add(bOK);
		this.panelForButton.add(bCancel);
		this.panelForButton.add(bSetProxyUnivLille1);
		this.add(this.panelForButton,BorderLayout.SOUTH);
		
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
	
	private void enableField(boolean usingProxy)
	{
		this.tfProxy.setEnabled(usingProxy);
		this.tfPort.setEnabled(usingProxy);
		this.tfUser.setEnabled(usingProxy);
		this.tfPassword.setEnabled(usingProxy);
	}
	
	

}
