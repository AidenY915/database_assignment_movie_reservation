package panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import frame.MovieReservationFrame;
import service.Service;

public class RegisterPanel extends MovieReservationPanel {

	public RegisterPanel() {
		super();
		init();
	}

	@Override
	public void init() {
		removeAll();
		
		setLayout(null);
		

		JLabel idLabel = new JLabel("ID");
		JLabel passwordLabel = new JLabel("PW");
		JLabel passwordCheckLabel= new JLabel("Check");
		JTextField idTextField = new JTextField();
		JTextField passwordTextField = new JTextField();
		JTextField passwordCheckField = new JTextField();

		add(idLabel);
		add(idTextField);
		add(passwordLabel);
		add(passwordTextField);
		add(passwordCheckLabel);
		add(passwordCheckField);
		idLabel.setBounds(100, 100, 40, 20);
		idTextField.setBounds(140, 100, 100, 20);
		passwordLabel.setBounds(100, 140, 40, 20);
		passwordTextField.setBounds(140, 140, 100, 20);
		passwordCheckLabel.setBounds(100,180, 40,20);
		passwordCheckField.setBounds(140, 180, 100, 20);

		JButton registerButton = new RegisterButton(idTextField, passwordTextField, passwordCheckField);
		add(registerButton);
		registerButton.setBounds(110, 200, 100, 50);
		
		KeyListener registerKeyListener = new RegisterKeyListener(registerButton);
		idTextField.addKeyListener(registerKeyListener);
		passwordTextField.addKeyListener(registerKeyListener);
		registerButton.addKeyListener(registerKeyListener);
		
	}

}

class RegisterButton extends JButton {
	private JTextField idTextField;
	private JTextField passwordTextField;
	private JTextField passwordCheckField;
	private Service service = Service.getService();

	RegisterButton(JTextField idTextField, JTextField passwordTextField, JTextField passwordCheckField) {
		super("회원가입");
		this.idTextField = idTextField;
		this.passwordTextField = passwordTextField;
		this.passwordCheckField = passwordCheckField;
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				performRegister();
				passwordTextField.setText("");
				passwordCheckField.setText("");
			}
		});

	}

	private void performRegister() {

		MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
		boolean registerRslt = service.register(idTextField.getText(), passwordTextField.getText());
		
		
		
		
		if (registerRslt == true) {
//			로그인페이지로 이동
			frame.changePanel(frame.getUserLoginPanel());
		} else {
//			아이디 중복되니깐 알려주고 다시 가입하라고 함.
		}	

	}

}

class RegisterKeyListener implements KeyListener{
	private JButton registerBtn;
	
	public RegisterKeyListener(JButton registerBtn) {
		super();
		this.registerBtn=registerBtn;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar()=='\n') {
			registerBtn.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	
}

