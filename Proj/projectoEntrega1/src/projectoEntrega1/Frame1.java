package projectoEntrega1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import projectoEntrega1.Exceptions.FileNotCSVException;
import projectoEntrega1.Exceptions.InvalidDomainException;
import projectoEntrega1.Exceptions.InvalidSizeException;
import projectoEntrega1.Models.Classifier;
import projectoEntrega1.Models.Dataset;

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
	public JTextField txtInserirValores;
	public JButton btnNewButton_1;
	public JButton btnNewButton_2;

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
		btnNewButton.setBorder(new RoundedBorder(10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtInserirNomeou.getText();
				if(!name.substring(name.length() - 4).equals(".csv")) {
				    	lblNewLabel.setText("Ficheiro deve estar em formato .csv.");
				    	lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
				    	lblNewLabel.setForeground(Color.RED);
				    }
				else {
				try {
					Classifier classifier = new Classifier(name, 0.01, rdbtnNewRadioButton.isSelected());
					double[] res = classifier.getAccuracyBin(new Dataset(name));
					lblNewLabel.setForeground(Color.BLACK);
					lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
					String txt = "<html> Accuracy: " + String.valueOf(res[0]) + "<br> Recall: " +  String.valueOf(res[1]) + "<br> Performance Threshold: " + String.valueOf(res[2]) + "<br> F1-Score: " + String.valueOf(res[3]) + "<br> Nota: Carregar em voltar atrás irá apagar permanentemente os pesos. <br> Após carregar no botão será necessário treinar novamente o modelo.";
					lblNewLabel.setText(txt);
					btnNewButton.setVisible(false);
					txtInserirNomeou.setVisible(false);
					rdbtnNewRadioButton.setVisible(false);
					btnNewButton_1.setVisible(true);
					txtInserirValores.setVisible(true);
					btnNewButton_2.setVisible(true);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					lblNewLabel.setText("<html> Ficheiro não encontrado: <br> Certifique-se que o filepath se encontra correto.");
					lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
					lblNewLabel.setForeground(Color.RED);
				} catch (Exception e2) {
					e2.printStackTrace();
					lblNewLabel.setText("<html> Ocorreu um erro não identificado. <br> Por favor tente outra vez.");
					lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
					lblNewLabel.setForeground(Color.RED);
				}}
			}
		});
		btnNewButton.setBounds(329, 355, 250, 50);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("<html> Esta ferramenta de diagn\u00F3stico r\u00E1pido n\u00E3o dever\u00E1, de forma alguma, <br> substituir uma consulta m\u00E9dica e deve apenas ser utilizada de forma recreativa"
				+ ". <br> Selecione o botão \"guardar pesos\" no caso de pretender usar o modelo para classificação.");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblNewLabel = lblNewLabel;
		lblNewLabel.setBackground(Color.GRAY);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(0, 435, 904, 177);
		panel.add(lblNewLabel);
		
		txtInserirNomeou = new JTextField();
		txtInserirNomeou.setBackground(Color.LIGHT_GRAY);
		txtInserirNomeou.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
		txtInserirNomeou.setHorizontalAlignment(SwingConstants.CENTER);
		txtInserirNomeou.setText("Inserir filepath do ficheiro a analisar.");
		txtInserirNomeou.setBounds(205, 197, 496, 102);
		panel.add(txtInserirNomeou);
		txtInserirNomeou.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Diagn\u00F3stico R\u00E1pido Baseado em MRFTS ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel_1.setBounds(46, 69, 823, 68);
		panel.add(lblNewLabel_1);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Guardar Pesos");
		this.rdbtnNewRadioButton = rdbtnNewRadioButton;
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnNewRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setBorder(new RoundedBorder(10));
		rdbtnNewRadioButton.setBounds(621, 369, 152, 30);
		panel.add(rdbtnNewRadioButton);
		
		JButton btnNewButton_1 = new JButton("Diagnosticar");
		this.btnNewButton_1 = btnNewButton_1;
		btnNewButton_1.setVisible(false);
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnNewButton_1.setBorder(new RoundedBorder(10));
		btnNewButton_1.setBounds(329, 355, 250, 50);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = txtInserirValores.getText();
				String [] sl = s.split(",");
				int a[] = new int[sl.length];
				for(int i = 0; i<sl.length; i++) {
					a[i] = Integer.parseInt(sl[i]);
					System.out.println(a[i]);
				}
				String name = txtInserirNomeou.getText().substring(0, txtInserirNomeou.getText().length()-4);
				name = name + ".txt";
				try {
					FileInputStream f = new FileInputStream(new File(name));
					ObjectInputStream o = new ObjectInputStream(f);
					Classifier classifier = (Classifier) o.readObject();
					lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
					lblNewLabel.setForeground(Color.BLACK);
					int res = classifier.classify(a);
					if(res == 1) {lblNewLabel.setText("<html> De acordo com o modelo, encontra-se infetado. <br> Classe mais provável: 1.");}
					else {lblNewLabel.setText("<html> De acordo com o modelo, encontra-se saudável. <br> Classe mais provável: 0.");}
				} catch (FileNotFoundException e1) {
					lblNewLabel.setText("Ficheiro não encontrado: Certifique-se que gravou os dados do modelo.");
					lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
					lblNewLabel.setForeground(Color.RED);
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (InvalidSizeException e1) {
					lblNewLabel.setText("<html> Os dados inseridos não podem ser avaliados. <br> Razão: O vetor apresentado é incompatível com o dataset.");
					lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
					lblNewLabel.setForeground(Color.RED);
					e1.printStackTrace();
				} catch (InvalidDomainException e1) {
					lblNewLabel.setText("<html> Os dados inseridos não podem ser avaliados. <br>  Razão: Uma das variáveis não se encontra no domínio pretendido.");
					lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
					lblNewLabel.setForeground(Color.RED);
					lblNewLabel.setText(e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnNewButton_1);
		
		txtInserirValores = new JTextField();
		txtInserirValores.setHorizontalAlignment(SwingConstants.CENTER);
		txtInserirValores.setFont(new Font("Malgun Gothic", Font.PLAIN, 19));
		txtInserirValores.setVisible(false);
		txtInserirValores.setText("Inserir Valores (separados por v\u00EDrgulas e sem espa\u00E7os)");
		txtInserirValores.setBounds(205, 197, 496, 102);
		panel.add(txtInserirValores);
		txtInserirValores.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("Voltar Atr\u00E1s");
		this.btnNewButton_2 = btnNewButton_2;
		btnNewButton_2.setBounds(621, 369, 152, 27);
		btnNewButton_2.setVisible(false);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtInserirNomeou.getText().substring(0, txtInserirNomeou.getText().length()-4);
				name = name + ".txt";
				File file = new File(name);
				file.delete();
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
				lblNewLabel.setForeground(Color.BLACK);
				btnNewButton_2.setVisible(false);
				btnNewButton_1.setVisible(false);
				txtInserirValores.setVisible(false);
				btnNewButton.setVisible(true);
				txtInserirNomeou.setVisible(true);
				rdbtnNewRadioButton.setVisible(true);
				txtInserirNomeou.setText("Inserir filepath do ficheiro a analisar.");
				lblNewLabel.setText("<html> Esta ferramenta de diagn\u00F3stico r\u00E1pido n\u00E3o dever\u00E1, de forma alguma, <br> substituir uma consulta m\u00E9dica e deve apenas ser utilizada de forma recreativa. <br> Selecione o botão \"guardar pesos\" no caso de pretender usar o modelo para classificação.");
			}});
		panel.add(btnNewButton_2);
		
	}
}
