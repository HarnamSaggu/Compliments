package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        launch();
    }

    public static ArrayList<String> loadText(String path) {
        File f = new File(path);
        if (!f.exists()) f = new File("src/" + path);
        if (!f.exists()) {
            return new ArrayList<>();
        }
        try {
            Scanner scanner = new Scanner(f);
            ArrayList<String> ret = new ArrayList<>();
            while (scanner.hasNextLine()) {
                ret.add(scanner.nextLine().replaceAll("\n", ""));
            }
            return ret;
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static void open(String text) {
        JFrame frame = new JFrame("Have a compliment :)");
        Random rand = new Random();
        frame.setLocation(rand.nextInt(800) + 50, rand.nextInt(500) + 50);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel label = new JLabel(text);
        label.setFont(new Font("Serif", Font.PLAIN, 15));
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(label);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        frame.toFront();
        frame.setAlwaysOnTop(false);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) frame.dispose();
            }
        });
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                Thread.sleep(4_000);
                frame.dispose();
                return null;
            }
        }.execute();
    }

    public static void launch() {
        JFrame frame = new JFrame("Compliments™");
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(50, 50, 305, 145);
        JLabel label = new JLabel("<html>" +
                "Hello!<br>" +
                "Click [start] to boost your self esteem. Enjoy :)<br>" +
                "These compliments are from the internet.<br>" +
                "Its the computer complimenting you btw" +
                "</html>");
        label.setBounds(10, 0, 1000, 70);
        JButton start = new JButton("Start");
        start.setBounds(10, 70, 135, 30);
        JButton end = new JButton("Close program");
        end.setBounds(150, 70, 135, 30);
        frame.add(end);
        frame.add(start);
        frame.add(label);
        frame.setVisible(true);

        end.addActionListener(e -> {
            System.exit(0);
        });

        start.addActionListener(e -> {
            new SwingWorker<>() {
                @Override
                protected Object doInBackground() throws Exception {
                    start.setEnabled(false);
                    ArrayList<String> compliments = loadText("compliments.txt");
                    int min =  35_000;   //inclusive
                    int max = 100_000;   //exclusive
                    Random rand = new Random();
                    String output;
                    while (true) {
                        if (false) break; // this allows it to compile as its no longer seen as infitnite i think
                        try {
                            Thread.sleep(rand.nextInt(max - min) + min);
                        } catch (InterruptedException error1) {
                            error1.printStackTrace();
                        }
                        output = compliments.get(rand.nextInt(compliments.size()));
                        open(output);
                        System.out.println(output);
                    }
                    start.setEnabled(true);
                    return null;
                }
            }.execute();
        });
    }
}
