package panel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import panel.UserRegisterPanel.IdCheckButton;

public class AdminRegisterPanel extends MovieReservationPanel {

    public AdminRegisterPanel() {
        super();
    }

    @Override
    public void init() {
        removeAll();
        setLayout(null);

        int flag = 1;
        JLabel titleLabel = new JLabel("관리자 회원가입");
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

        UserRegisterPanel.IdCheckButton idCheckButton = new UserRegisterPanel.IdCheckButton(idTextField);
        add(idCheckButton);
        idCheckButton.setBounds(700, 120, 130, 30);

        UserRegisterPanel.RegisterButton registerButton = new UserRegisterPanel.RegisterButton(idTextField, passwordTextField, passwordCheckField, userNameField, phoneNoField, emailField, flag, idCheckButton);
        add(registerButton);
        registerButton.setBounds(350, 450, 300, 50);

        KeyListener registerKeyListener = new RegisterKeyListener(registerButton);
        idTextField.addKeyListener(registerKeyListener);
        passwordTextField.addKeyListener(registerKeyListener);
        passwordCheckField.addKeyListener(registerKeyListener);
        userNameField.addKeyListener(registerKeyListener);
        phoneNoField.addKeyListener(registerKeyListener);
        emailField.addKeyListener(registerKeyListener);
        registerButton.addKeyListener(registerKeyListener);
    }

    private class RegisterKeyListener implements KeyListener {
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
}
