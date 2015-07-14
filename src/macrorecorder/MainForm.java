/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macrorecorder;

import de.ksquared.system.keyboard.GlobalKeyListener;
import de.ksquared.system.keyboard.KeyAdapter;
import de.ksquared.system.keyboard.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class MainForm {
	protected JButton btnStart;
	protected JButton btnStop;
	protected JButton btnPlay;
	protected static boolean isRecording = false;
	protected Thread t;
	public void run() {
//		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Macro Recording");
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(386, 100);
		frame.setResizable(false);
		
		this.btnStart = new JButton("Start Recording");
		this.btnStart.setBounds(0, 0, 140, 25);
		this.btnStart.addActionListener((ActionEvent e) -> {
			this.start(e);
		});

		this.btnStop = new JButton("Stop Recording");
		this.btnStop.setBounds(140, 0, 140, 25);
		this.btnStop.addActionListener((ActionEvent e) -> {
			this.stop(e);
		});

		this.btnPlay = new JButton("Play");
		this.btnPlay.setBounds(280, 0, 100, 25);
		this.btnPlay.addActionListener((ActionEvent e) -> {
			this.play(e);
		});

		frame.add(this.btnStart);
		frame.add(this.btnStop);
		frame.add(this.btnPlay);
		frame.setVisible(true);
	}

	public void start(ActionEvent e) {
		this.btnStart.setEnabled(false);
		MainForm.isRecording = true;
		this.recording();
	}

	public void stop(ActionEvent e) {
		this.btnStart.setEnabled(true);
		MainForm.isRecording = false;
	}

	public void play(ActionEvent e) {
	}

	public void recording() {
		new GlobalKeyListener().addKeyListener(new KeyAdapter() {
//			@Override public void keyPressed(KeyEvent event) { 
//				System.out.println(event);
//			}

			@Override public void keyReleased(KeyEvent event) {
				System.out.println((char)event.getVirtualKeyCode());

//				if(event.getVirtualKeyCode()==KeyEvent.VK_ADD
//				&& event.isCtrlPressed())
//					System.out.println("CTRL+ADD was just released (CTRL is still pressed)");
			}
		});
	}
}