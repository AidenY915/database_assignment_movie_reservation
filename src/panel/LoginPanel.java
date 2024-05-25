package panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import frame.MovieReservationFrame;
import service.Service;

public class LoginPanel extends MovieReservationPanel {
	public LoginPanel() {
		super();
		setLayout(null);

		JLabel idLabel = new JLabel("ID");
		JLabel passwordLabel = new JLabel("PW");
		JTextField idTextField = new JTextField();
		JTextField passwordTextField = new JTextField();
		add(idLabel);
		add(idTextField);
		add(passwordLabel);
		add(passwordTextField);
		idLabel.setBounds(100, 100, 40, 20);
		idTextField.setBounds(140, 100, 100, 20);
		passwordLabel.setBounds(100, 140, 40, 20);
		passwordTextField.setBounds(140, 140, 100, 20);

		JButton loginButton = new LoginButton(idTextField, passwordTextField);
		add(loginButton);
		loginButton.setBounds(110, 200, 100, 50);

		KeyListener loginKeyListener = new LoginKeyListener(loginButton);
		idTextField.addKeyListener(loginKeyListener);
		passwordTextField.addKeyListener(loginKeyListener);
		loginButton.addKeyListener(loginKeyListener);
	}
}

class LoginButton extends JButton {
	private JTextField idTextField;
	private JTextField passwordTextField;
	private Service service = Service.getService();

	LoginButton(JTextField idTextField, JTextField passwordTextField) {
		super("로그인");
		this.idTextField = idTextField;
		this.passwordTextField = passwordTextField;
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				performLogin();
				passwordTextField.setText("");

			}

		});
	}

	private void performLogin() {
		MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
		int loginRslt = service.login(idTextField.getText(), passwordTextField.getText(),0);
		if (loginRslt == 0)
			return;
		else if (loginRslt == 1) {
			// frame.changePanel(사용자 페이지);
		} else if (loginRslt == 2) {
			// frame.changePanel(관리자 페이지);
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
};

// 비밀번호를 입력하고 엔터를 누를시 로그인 버튼을 누른것과 동일한 작업 수행
