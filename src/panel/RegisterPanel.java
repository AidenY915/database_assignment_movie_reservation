package panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RegisterPanel extends JFrame{
	public RegisterPanel() {
		super("회원가입");
		setLayout(null);
		
		JLabel idLabel = new JLabel("ID");
		JLabel passwordLabel = new JLabel("PW");
		JTextField idTextField = new JTextField();
		JTextField passwordTextField = new JTextField();
		JTextField passwordCheckField= new JTextField();
		
		
		
		add(idLabel);
		add(idTextField);
		add(passwordLabel);
		add(passwordTextField);
		add(passwordCheckField);
		idLabel.setBounds(100, 100, 40, 20);
		idTextField.setBounds(140, 100, 100, 20);
		passwordLabel.setBounds(100, 140, 40, 20);
		passwordTextField.setBounds(140, 140, 100, 20);
		passwordCheckField.setBounds(140,180,100,20);
		
		JButton registerButton=new RegisterButton(idTextField,passwordTextField,passwordCheckField)
		
		
		
		
		
	}

}
