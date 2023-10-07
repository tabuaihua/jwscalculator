package gitops.jwscalculator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JwsCalculator {

	public static void main(String[] args) {
		CalculatorUI ui = new CalculatorUI("JavaWebStart GuiCalculator + 2021212106 陈世军");
		ui.setVisible(true);
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

class CalculatorUI extends JFrame {
	private JTextField textField = new JTextField("0");

	private JPanel panel = new JPanel(new GridLayout(4, 4));
	private JButton bt11 = new JButton("7");
	private JButton bt12 = new JButton("8");
	private JButton bt13 = new JButton("9");
	private JButton bt14 = new JButton("/");
	private JButton bt21 = new JButton("4");
	private JButton bt22 = new JButton("5");
	private JButton bt23 = new JButton("6");
	private JButton bt24 = new JButton("*");
	private JButton bt31 = new JButton("1");
	private JButton bt32 = new JButton("2");
	private JButton bt33 = new JButton("3");
	private JButton bt34 = new JButton("-");
	private JButton bt41 = new JButton("0");
	private JButton bt42 = new JButton(".");
	private JButton bt43 = new JButton("=");
	private JButton bt44 = new JButton("+");
	JButton jb[] = { bt11, bt12, bt13, bt14, bt21, bt22, bt23, bt24,
			bt31, bt32, bt33, bt34, bt41, bt42, bt43, bt44 };

	class MyHandler implements ActionListener {
		String str = "";

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();

			if (source == bt43) {
				str = str + "= " + CalcString.getRes(str);
				textField.setText(str);

				int res = JOptionPane.showConfirmDialog(panel, "Calculated, Goon ?", "", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					str = "";
					textField.setText("0");
				} else
					System.exit(0);

			} else {
				str = str + source.getText();
				textField.setText(str);
			}
		}
	}

	public CalculatorUI(String title) {
		super(title);
		this.ComponentInit();
		MyHandler myHandler = new MyHandler();
		this.ListenerAdd(myHandler);
		this.setLocation(300, 200);
		this.setSize(600, 450);
	}

	private void ComponentInit() {
		this.add(textField, BorderLayout.NORTH);
		textField.setPreferredSize(new Dimension(300, 80));
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setFont(new Font("楷体", Font.PLAIN, 32));
		textField.setEditable(false);

		this.add(panel, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(300, 350));
		for (int i = 0; i < 16; i++) {
			jb[i].setFont(new Font("楷体", Font.BOLD, 32));
			panel.add(jb[i]);
		}

	}

	private void ListenerAdd(MyHandler myHandler) {
		for (int i = 0; i < 16; i++) {
			jb[i].addActionListener(myHandler);
		}
	}
}

class CalcString {
	public static double getRes(String str) {
		String s = "";
		double n1 = 0, n2, res = 0;
		char op = 0;

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != '+' && str.charAt(i) != '-' && str.charAt(i) != '*' && str.charAt(i) != '/') {
				s = s + str.charAt(i);
			} else {
				n1 = Double.parseDouble(s);
				op = str.charAt(i);
				s = "";
			}
		}
		n2 = Double.parseDouble(s);

		switch (op) {
			case '+':
				res = n1 + n2;
				break;
			case '-':
				res = n1 - n2;
				break;
			case '*':
				res = n1 * n2;
				break;
			case '/':
				res = n1 / n2;
				break;
			default:
		}

		return res;
	}
}