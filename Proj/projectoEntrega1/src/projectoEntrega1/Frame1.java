package projectoEntrega1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import projectoEntrega1.Exceptions.InvalidDomainException;
import projectoEntrega1.Exceptions.InvalidSizeException;
import projectoEntrega1.Models.Classifier;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JRadioButton;

public class Frame1 extends JFrame {

	public JPanel contentPane;
	public JTextField txtInserirNomeou;
	public JRadioButton rdbtnNewRadioButton;
	public JLabel lblNewLabel;
	public double[] res;
	public JTextField txtInserirValores;
	public JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 frame = new Frame1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 300, 928, 710);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(175, 238, 238));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Treinar Modelo");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtInserirNomeou.getText();
				if (name.length() > 4) {
				    if(name.substring(name.length() - 4) != ".csv") {
				    	name = name + ".csv";
				    }
				}
				try {
					Classifier classifier = new Classifier(name, 0.01, rdbtnNewRadioButton.isSelected());
					double[] res = classifier.getAccuracyBin();
					String txt = "Accuracy: " + String.valueOf(res[0]) + " Recall: " +  String.valueOf(res[1]) + " Performance Threshold: " + String.valueOf(res[2]) + " F1-Score: " + String.valueOf(res[3]);
					lblNewLabel.setText(txt);
					Thread.sleep(2000);
					btnNewButton.setVisible(false);
					txtInserirNomeou.setVisible(false);
					rdbtnNewRadioButton.setVisible(false);
					btnNewButton_1.setVisible(true);
					txtInserirValores.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(300, 450, 250, 50);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Esta ferramenta de diagn\u00F3stico r\u00E1pido n\u00E3o dever\u00E1, de forma alguma, substituir uma consulta m\u00E9dica e deve apenas ser utilizada de forma recreativa.");
		this.lblNewLabel = lblNewLabel;
		lblNewLabel.setBackground(Color.GRAY);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 510, 894, 87);
		panel.add(lblNewLabel);
		
		txtInserirNomeou = new JTextField();
		txtInserirNomeou.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtInserirNomeou.setHorizontalAlignment(SwingConstants.CENTER);
		txtInserirNomeou.setText("Inserir nome (ou ficheiro)");
		txtInserirNomeou.setBounds(300, 330, 250, 50);
		panel.add(txtInserirNomeou);
		txtInserirNomeou.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Diagn\u00F3stico R\u00E1pido Baseado em MRFTS ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel_1.setBounds(40, 24, 823, 68);
		panel.add(lblNewLabel_1);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Save Model Weights");
		this.rdbtnNewRadioButton = rdbtnNewRadioButton;
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnNewRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setBounds(592, 466, 152, 30);
		panel.add(rdbtnNewRadioButton);
		
		JButton btnNewButton_1 = new JButton("Diagnosticar");
		this.btnNewButton_1 = btnNewButton_1;
		btnNewButton_1.setVisible(false);
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnNewButton_1.setBounds(300, 450, 250, 50);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sl = txtInserirValores.getText();
				int a[] = new int[sl.length()/2+1];
				for(int i = 0; i<sl.length()/2+1; i=i+2) {
				    char result = sl.charAt(i);
				    int res = Integer.valueOf(result);
					a[i] = res - 48;
				}
				String name = "name" + ".txt";
				FileInputStream f;
				try {
					f = new FileInputStream(new File(name));
					ObjectInputStream o = new ObjectInputStream(f);
					Classifier classifier = (Classifier) o.readObject();
					//for(int i :classifier.data.getDomain()) {System.out.println(i);}
					System.out.println(a[0]);
					lblNewLabel.setText("Classe mai provável é: " + classifier.classify(a));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvalidSizeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvalidDomainException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnNewButton_1);
		
		txtInserirValores = new JTextField();
		txtInserirValores.setVisible(false);
		txtInserirValores.setText("Inserir Valores");
		txtInserirValores.setBounds(300, 330, 246, 50);
		panel.add(txtInserirValores);
		txtInserirValores.setColumns(10);
		
	}
}
