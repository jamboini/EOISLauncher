package net.elementsmodcolab;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import net.elementsmodcolab.launcherData.CodeCore;
import net.elementsmodcolab.launcherData.MinecraftLauncher;

public class LauncherWindow {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	public boolean offline = false;
	public JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LauncherWindow window = new LauncherWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LauncherWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 843, 501);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		frame.setTitle("Elements of Insanity Launcher");
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception localException) {}
		
		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textField, -10, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		springLayout.putConstraint(SpringLayout.NORTH, lblUsername, 402, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblUsername, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		springLayout.putConstraint(SpringLayout.NORTH, lblPassword, 402, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblPassword, 278, SpringLayout.EAST, lblUsername);
		springLayout.putConstraint(SpringLayout.SOUTH, lblPassword, 416, SpringLayout.NORTH, frame.getContentPane());
		frame.getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		springLayout.putConstraint(SpringLayout.WEST, passwordField, 336, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, passwordField, -177, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textField, -12, SpringLayout.WEST, passwordField);
		springLayout.putConstraint(SpringLayout.SOUTH, passwordField, -10, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(passwordField);
		
		JButton button_1 = new JButton("Settings");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CodeCore.settingsDialogue();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, button_1, 0, SpringLayout.NORTH, textField);
		springLayout.putConstraint(SpringLayout.EAST, button_1, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(button_1);
		
		label = new JLabel("");
		springLayout.putConstraint(SpringLayout.WEST, label, 401, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, label, 0, SpringLayout.SOUTH, lblUsername);
		frame.getContentPane().add(label);
		
		JButton button = new JButton("Login");
		springLayout.putConstraint(SpringLayout.EAST, button, -10, SpringLayout.EAST, frame.getContentPane());
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
				
			}});
		springLayout.putConstraint(SpringLayout.SOUTH, button, -6, SpringLayout.NORTH, button_1);
		frame.getContentPane().add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -6, SpringLayout.NORTH, lblUsername);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 817, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(scrollPane);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setContentType("text/html");
		try { editorPane.setPage("http://mcupdate.tumblr.com"); } catch (IOException e1) {}
		editorPane.setEditable(false);
		scrollPane.setViewportView(editorPane);
	}
	
	public void login()
	{
		label.setText("Logging In...");
		try 
		{
			label.setText("");
			@SuppressWarnings("deprecation")
			String parameters = "user=" + URLEncoder.encode(textField.getText(), "UTF-8") + "&password=" + URLEncoder.encode(passwordField.getText(), "UTF-8") + "&version=" + 13; 
			String result = CodeCore.excutePost("https://login.minecraft.net/", parameters);
			
			if (result == null) {
				label.setText("Cannot connect to Minecraft.net");
				offline = true;
				return;
		    }
			
		      if (!result.contains(":")) {
		          if (result.trim().equals("Bad login")) {
		        	  label.setText("Login failed");
		          } else if (result.trim().equals("Old version")) {
		            label.setText("Outdated launcher");
		          } else {
		        	  label.setText(result);
		          }
				  offline = true;
		          return;
		        }
		      
		      String[] values = result.split(":");
		      System.out.println("Username: " + values[2]);
		      System.out.println("Session ID: " + values[3]);
		      String[] mc = new String[] {CodeCore.getWorkingDir(), values[2], values[3]};
		      boolean exists = (new File(CodeCore.getWorkingDir() + "/bin/minecraft.jar")).exists();
		      boolean exists1 = (new File(CodeCore.getWorkingDir() + "/bin/lwjgl.jar")).exists();
		      boolean exists2 = (new File(CodeCore.getWorkingDir() + "/bin/lwjgl_util.jar")).exists();
		      boolean exists3 = (new File(CodeCore.getWorkingDir() + "/bin/jinput.jar")).exists();
		      if (exists && exists1 && exists2 && exists3) 
		      {
		    	  MinecraftLauncher.init(mc);
		      } 
		      else 
		      {
		    	  installElementsOfInsanity();
		    	  MinecraftLauncher.init(mc);
		      }	      
		} 
		catch (Exception e1) {}
	}
	
	public void installElementsOfInsanity() throws Exception
	{
		label.setText("Downloading...");
		CodeCore.download();
		label.setText("Done!");
	}
}
