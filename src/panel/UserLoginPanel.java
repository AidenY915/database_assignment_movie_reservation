package panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import frame.MovieReservationFrame;
import service.Service;

public class UserLoginPanel extends MovieReservationPanel {
	public UserLoginPanel() {
		super();
	}

	@Override
	public void init() {
		setLayout(null);

		JLabel titleLabel = new JLabel("사용자 로그인");
		JLabel orLabel = new JLabel("=====================또는=====================");

		JTextField idTextField = new JTextField("ID를 입력하세요");
		JTextField passwordTextField = new JTextField("패스워드를 입력하세요");

		idTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {

				if (idTextField.getText().equals("ID를 입력하세요")) {
					idTextField.setText("");

				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (idTextField.getText().equals("")) {
					idTextField.setText("ID를 입력하세요");

				}
			}
		});

		passwordTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (passwordTextField.getText().equals("패스워드를 입력하세요")) {
					passwordTextField.setText("");

				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (passwordTextField.getText().equals("")) {
					passwordTextField.setText("패스워드를 입력하세요");

				}
			}
		});

		add(titleLabel);
		add(idTextField);
		add(passwordTextField);
		add(orLabel);

		titleLabel.setBounds(440, 40, 100, 50);
		orLabel.setBounds(330, 280, 330, 20);

		idTextField.setBounds(330, 100, 330, 50);
		passwordTextField.setBounds(330, 160, 330, 50);

		JButton loginButton = new UserLoginButton(idTextField, passwordTextField);
		add(loginButton);
		loginButton.setBounds(330, 220, 330, 50);

		JButton moveToRegisterButton = new MoveToRegisterButton(1);
		add(moveToRegisterButton);
		moveToRegisterButton.setBounds(330, 320, 330, 50);

		KeyListener loginKeyListener = new LoginKeyListener(loginButton);
		idTextField.addKeyListener(loginKeyListener);
		passwordTextField.addKeyListener(loginKeyListener);
		loginButton.addKeyListener(loginKeyListener);
	}
}

class UserLoginButton extends JButton {
	private JTextField idTextField;
	private JTextField passwordTextField;

	UserLoginButton(JTextField idTextField, JTextField passwordTextField) {
		super("로그인");
		this.idTextField = idTextField;
		this.passwordTextField = passwordTextField;
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				performLogin();
				passwordTextField.setText("");

			}

		});

	}

	private void performLogin() {
		Service service = Service.getService();
		MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
		int loginRslt = service.login(idTextField.getText(), passwordTextField.getText(), 0);
		System.out.println(loginRslt);
		if (loginRslt == -1)
			return;
		else if (loginRslt == 0) {
			frame.changePanel(frame.getMovieListPanel());
		}

	}
}

class LoginKeyListener implements KeyListener {

	private JButton loginBtn;

	public LoginKeyListener(JButton loginBtn) {
		super();
		this.loginBtn = loginBtn;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == '\n')
			loginBtn.doClick();
	}
}
