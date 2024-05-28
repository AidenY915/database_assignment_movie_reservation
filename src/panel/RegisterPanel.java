package panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTree;

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

		JLabel titleLabel = new JLabel("회원가입");
		JLabel idLabel = new JLabel("ID");
		JLabel passwordLabel = new JLabel("PW");
		JLabel passwordCheckLabel = new JLabel("PW Check");
		JLabel userNameLabel = new JLabel("이름");
		JLabel phoneNoLabel = new JLabel("휴대폰 번호");
		JLabel emailLabel = new JLabel("이메일");

		JTextField idTextField = new JTextField();
		JTextField passwordTextField = new JTextField();
		JTextField passwordCheckField = new JTextField();
		JTextField userNameField = new JTextField();
		JTextField phoneNoField = new JTextField();
		JTextField emailField = new JTextField();

		add(titleLabel);
		add(idLabel);
		add(idTextField);
		add(passwordLabel);
		add(passwordTextField);
		add(passwordCheckLabel);
		add(passwordCheckField);
		add(userNameField);
		add(userNameLabel);
		add(phoneNoLabel);
		add(phoneNoField);
		add(emailLabel);
		add(emailField);

		titleLabel.setBounds(450, 40, 100, 50);
		idLabel.setBounds(350, 130, 80, 15);
		idTextField.setBounds(450, 120, 200, 30);
		passwordLabel.setBounds(350, 180, 80, 15);
		passwordTextField.setBounds(450, 170, 200, 30);
		passwordCheckLabel.setBounds(350, 230, 80, 15);
		passwordCheckField.setBounds(450, 220, 200, 30);
		userNameLabel.setBounds(350, 280, 80, 15);
		userNameField.setBounds(450, 270, 200, 30);
		phoneNoLabel.setBounds(350, 330, 80, 15);
		phoneNoField.setBounds(450, 320, 200, 30);
		emailLabel.setBounds(350, 380, 80, 15);
		emailField.setBounds(450, 370, 200, 30);

		JButton idCheckButton = new IdCheckButton(idTextField);
		add(idCheckButton);
		idCheckButton.setBounds(700, 120, 130, 30);

		JButton registerButton = new RegisterButton(idTextField, passwordCheckField, userNameField, phoneNoField,
				emailField);
		add(registerButton);
		registerButton.setBounds(350, 450, 300, 50);

		KeyListener registerKeyListener = new RegisterKeyListener(registerButton);
		idTextField.addKeyListener(registerKeyListener);
		passwordTextField.addKeyListener(registerKeyListener);
		registerButton.addKeyListener(registerKeyListener);

	}

}

class IdCheckButton extends JButton {
	private JTextField idTextField;
	private Service service = Service.getService();

	IdCheckButton(JTextField idTextField) {
		super("ID 중복 확인");
		this.idTextField = idTextField;
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				performIdCheck();

			}
		});
	}

	private void performIdCheck() {
		String id = idTextField.getText();
		boolean isDuplicated = service.isIdDuplicated(id);
		if (isDuplicated) {
			JOptionPane.showMessageDialog(null, "아이디가 중복되었습니다.");
		} else {
			JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.");
		}
	}
}

class RegisterButton extends JButton {
	private JTextField idTextField;
	private JTextField passwordCheckField;
	private JTextField userNameField;
	private JTextField phoneNoField;
	private JTextField emailField;
	private Service service = Service.getService();

	RegisterButton(JTextField idTextField, JTextField passwordCheckField, JTextField userNameField,
			JTextField phoneNoField, JTextField emailField) {
		super("회원가입");
		this.idTextField = idTextField;
		this.passwordCheckField = passwordCheckField;
		this.userNameField = userNameField;
		this.phoneNoField = phoneNoField;
		this.emailField = emailField;

		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				performRegister();
			}
		});

	}

	private void performRegister() {

		MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
		boolean registerRslt = service.register(idTextField.getText(), passwordCheckField.getText(),
				userNameField.getText(), phoneNoField.getText(), emailField.getText());

		if (registerRslt == true) {
//			로그인페이지로 이동
			frame.changePanel(frame.getUserLoginPanel());
		} else {
//			아이디 중복되니깐 알려주고 다시 가입하라고 함.
		}

	}

}

class RegisterKeyListener implements KeyListener {
	private JButton registerBtn;

	public RegisterKeyListener(JButton registerBtn) {
		super();
		this.registerBtn = registerBtn;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == '\n') {
			registerBtn.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
