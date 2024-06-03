package panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import service.Service;
import frame.MovieReservationFrame;

public class UserRegisterPanel extends MovieReservationPanel {

    public UserRegisterPanel() {
        super();
        init();
    }

    @Override
    public void init() {
        removeAll();
        setLayout(null);

        int flag = 0;
        JLabel titleLabel = new JLabel("일반회원 회원가입");
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

        IdCheckButton idCheckButton = new IdCheckButton(idTextField);
        add(idCheckButton);
        idCheckButton.setBounds(700, 120, 130, 30);

        RegisterButton registerButton = new RegisterButton(idTextField, passwordTextField, passwordCheckField, userNameField, phoneNoField, emailField, flag, idCheckButton);
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

    public class IdCheckButton extends JButton {
        private JTextField idTextField;
        private Service service = Service.getService();
        private boolean isIdChecked = false;

        public IdCheckButton(JTextField idTextField) {
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
                isIdChecked = false;
            } else {
                JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.");
                isIdChecked = true;
            }
        }

        public boolean isIdChecked() {
            return isIdChecked;
        }
    }

    public class RegisterButton extends JButton {
        private JTextField idTextField;
        private JTextField passwordTextField;
        private JTextField passwordCheckField;
        private JTextField userNameField;
        private JTextField phoneNoField;
        private JTextField emailField;
        private int isAdmin;
        private Service service = Service.getService();
        private IdCheckButton idCheckButton;

        public RegisterButton(JTextField idTextField, JTextField passwordTextField, JTextField passwordCheckField,
                       JTextField userNameField, JTextField phoneNoField, JTextField emailField, int isAdmin,
                       IdCheckButton idCheckButton) {
            super("회원가입");
            this.idTextField = idTextField;
            this.passwordTextField = passwordTextField;
            this.passwordCheckField = passwordCheckField;
            this.userNameField = userNameField;
            this.phoneNoField = phoneNoField;
            this.emailField = emailField;
            this.isAdmin = isAdmin;
            this.idCheckButton = idCheckButton;

            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    performRegister();
                }
            });
        }

        private void performRegister() {
            String id = idTextField.getText();
            String password = passwordTextField.getText();
            String passwordCheck = passwordCheckField.getText();
            String userName = userNameField.getText();
            String phoneNo = phoneNoField.getText();
            String email = emailField.getText();

            if (id.isEmpty() || password.isEmpty() || passwordCheck.isEmpty() || userName.isEmpty() || phoneNo.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "모든 필드를 입력하세요.");
                return;
            }

            if (!password.equals(passwordCheck)) {
                JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");
                return;
            }

            if (!idCheckButton.isIdChecked()) {
                JOptionPane.showMessageDialog(null, "ID 중복 확인을 해주세요.");
                return;
            }

            boolean registerResult = service.register(id, password, userName, phoneNo, email, isAdmin);

            if (registerResult) {
                JOptionPane.showMessageDialog(null, "회원가입 성공!");
                MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
                frame.changePanel(frame.getLoginChoicePanel());
            } else {
                JOptionPane.showMessageDialog(null, "회원가입 실패: 다시 시도해 주세요.");
            }
        }
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
