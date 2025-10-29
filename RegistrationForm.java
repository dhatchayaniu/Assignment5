package assignment5;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;
public class RegistrationForm extends JFrame implements ActionListener {
    private JTextField txtName, txtEmail, txtPhone;
    private JComboBox<String> cbCourse;
    private JRadioButton rbMale, rbFemale, rbOther;
    private ButtonGroup genderGroup;
    private JCheckBox chkTerms;
    private JButton btnSubmit, btnReset;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    public RegistrationForm() {
        setTitle("Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(470, 350);
        setLocationRelativeTo(null); 
        setResizable(false);
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(main);
        JLabel lblTitle = new JLabel("Student Registration", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        main.add(lblTitle, BorderLayout.NORTH);
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        int row = 0;
        c.gridx = 0; c.gridy = row;
        form.add(new JLabel("Full Name:"), c);
        txtName = new JTextField();
        c.gridx = 1; c.gridy = row++; c.gridwidth = 2;
        form.add(txtName, c);
        c.gridwidth = 1;
        c.gridx = 0; c.gridy = row;
        form.add(new JLabel("Email:"), c);
        txtEmail = new JTextField();
        c.gridx = 1; c.gridy = row++; c.gridwidth = 2;
        form.add(txtEmail, c);
        c.gridwidth = 1;
        c.gridx = 0; c.gridy = row;
        form.add(new JLabel("Phone (10 digits):"), c);
        txtPhone = new JTextField();
        c.gridx = 1; c.gridy = row++; c.gridwidth = 2;
        form.add(txtPhone, c);
        c.gridwidth = 1;
        c.gridx = 0; 
        c.gridy = row;
        form.add(new JLabel("Course:"), c);
        cbCourse = new JComboBox<>(new String[] {
                "Select", "B.Tech", "B.Sc", "BCA", "MCA", "Diploma"
        });
        c.gridx = 1; c.gridy = row++; c.gridwidth = 2;
        form.add(cbCourse, c);
        c.gridwidth = 1;
        c.gridx = 0; c.gridy = row;
        form.add(new JLabel("Gender:"), c);
        rbMale = new JRadioButton("Male");
        rbFemale = new JRadioButton("Female");
        rbOther = new JRadioButton("Other");
        genderGroup = new ButtonGroup();
        genderGroup.add(rbMale); genderGroup.add(rbFemale); genderGroup.add(rbOther);
        JPanel pGender = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        pGender.add(rbMale); pGender.add(rbFemale); pGender.add(rbOther);
        c.gridx = 1; c.gridy = row++; c.gridwidth = 2;
        form.add(pGender, c);
        c.gridwidth = 1;
        c.gridx = 0; c.gridy = row;
        form.add(new JLabel("Accept Terms:"), c);
        chkTerms = new JCheckBox("I accept terms and conditions");
        c.gridx = 1; c.gridy = row++; c.gridwidth = 2;
        form.add(chkTerms, c);
        c.gridwidth = 1;
        main.add(form, BorderLayout.CENTER);
        JPanel pButtons = new JPanel();
        btnSubmit = new JButton("Submit");
        btnReset = new JButton("Reset");
        btnSubmit.addActionListener(this);
        btnReset.addActionListener(this);
        pButtons.add(btnSubmit);
        pButtons.add(btnReset);
        main.add(pButtons, BorderLayout.SOUTH);
        setDefaultBorders();
        setVisible(true);
    }
    private void setDefaultBorders() {
        LineBorder lb = new LineBorder(Color.GRAY, 1);
        txtName.setBorder(lb);
        txtEmail.setBorder(lb);
        txtPhone.setBorder(lb);
        cbCourse.setBorder(lb);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSubmit) {
            onSubmit();
        } else if (e.getSource() == btnReset) {
            onReset();
        }
    }
    private void onSubmit() {
        setDefaultBorders();
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String course = cbCourse.getSelectedItem().toString();
        String gender = null;
        if (rbMale.isSelected()) gender = "Male";
        else if (rbFemale.isSelected()) gender = "Female";
        else if (rbOther.isSelected()) gender = "Other";
        StringBuilder errors = new StringBuilder();
        if (name.isEmpty()) {
            errors.append("- Name is required.\n");
            txtName.setBorder(new LineBorder(Color.RED, 2));
        }
        if (email.isEmpty() || !EMAIL_PATTERN.matcher(email).matches()) {
            errors.append("- Enter a valid email address.\n");
            txtEmail.setBorder(new LineBorder(Color.RED, 2));
        }
        if (!phone.matches("\\d{10}")) {
            errors.append("- Phone must be exactly 10 digits (numbers only).\n");
            txtPhone.setBorder(new LineBorder(Color.RED, 2));
        }
        if (course.equals("Select")) {
            errors.append("- Please select a course.\n");
            cbCourse.setBorder(new LineBorder(Color.RED, 2));
        }
        if (gender == null) {
            errors.append("- Please select your gender.\n");
        }
        if (!chkTerms.isSelected()) {
            errors.append("- You must accept the terms and conditions.\n");
        }

        if (errors.length() > 0) {
            JOptionPane.showMessageDialog(this,
                    "Please fix the following errors:\n\n" + errors.toString(),
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        StringBuilder success = new StringBuilder();
        success.append("Registration Successful!\n\n");
        success.append("Name: ").append(name).append("\n");
        success.append("Email: ").append(email).append("\n");
        success.append("Phone: ").append(phone).append("\n");
        success.append("Course: ").append(course).append("\n");
        success.append("Gender: ").append(gender).append("\n");
        JOptionPane.showMessageDialog(this,
                success.toString(),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);

    }
    private void onReset() {
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        cbCourse.setSelectedIndex(0);
        genderGroup.clearSelection();
        chkTerms.setSelected(false);
        setDefaultBorders();
    }
    public static void main(String[] args) {
       
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(RegistrationForm::new);
    }
}
