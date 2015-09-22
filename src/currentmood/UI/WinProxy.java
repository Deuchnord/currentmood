package currentmood.UI;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class WinProxy extends JFrame {
	
	JLabel lIP, lPort, lUser, lPassword;
	JPanel panel;
	JTextField tfProxy, tfPort, tfUser, tfPassword;
	JButton bOK, bCancel;
	JCheckBox bUseProxy;
	
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
	

}
