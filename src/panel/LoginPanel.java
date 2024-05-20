package panel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
				int loginRslt = service.login(idTextField.getText(), passwordTextField.getText());
				if(loginRslt == 0) return;
				else if(loginRslt == 1){
//					frame.changePanel(사용자 페이지);
				}else if(loginRslt == 2) {
//					frame.changePanel(관리자 페이지);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
	}
	
}
