package mypack;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import  mypack.Replace;
import mypack.InputFile;
import javax.swing.*;

public class MainWindow extends JFrame {
        InputFile data;
        Replace[] replaceBase;
        private JButton openPatternButton = new JButton("Открыть Базу замен");
        private JButton openTextButton = new JButton("Открыть текст");
        private JButton saveTextButton = new JButton("Сохранить в файл");
        private  JToolBar toolBar = new JToolBar();
        private JLabel baseMinDisLabel = new JLabel("Введите базовую мин. дистанцию");
        private JLabel probMinDisLabel = new JLabel("Введите допустимую мин. дистанцию");
        private JLabel modifyELabel = new JLabel("Режим обработки е");
        private JLabel modifyULabel = new JLabel("Режим обработки ё,ю,я");
        private JLabel modifyZiLabel = new JLabel("Режим обработки слога ци");
        private JLabel probabilityLabel = new JLabel("Вероятность отказа от замены");
        private JLabel variantLabel = new JLabel("Добавление маркеров вариантов");
        private JRadioButton e1radio = new JRadioButton("без особенностей");
    private JRadioButton e2radio = new JRadioButton("е интерпретируются как э");
    private JRadioButton e3radio = new JRadioButton("е интерпретируются с условиями");
    private JRadioButton u1radio = new JRadioButton("без особенностей");
    private JRadioButton u2radio = new JRadioButton("обработка ё,ю,я как йо,йу,йа");
    private JRadioButton zi1radio = new JRadioButton("без особенностей");
    private JRadioButton zi2radio = new JRadioButton("цы интерпретируются как ци");
    private JRadioButton variant1radio = new JRadioButton("без особенностей");
    private JRadioButton variant2radio = new JRadioButton("вставка символов \\ и /");
    private JTextField baseMinDisInput = new JTextField("",5);
    private JTextField probMinDisInput = new JTextField("", 5);
    private JTextField probabilityInput = new JTextField("", 5);


    public MainWindow() {
            super("Simple Example");
            this.setBounds(100,100,1000,700);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        openTextButton.setEnabled(false);
        saveTextButton.setEnabled(false);
            //JFileChooser fileChooser = new JFileChooser();
            //addFileChooserListeners();
            openPatternButton.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFileChooser fileopen = new JFileChooser();
                            int ret = fileopen.showDialog(null, "Открыть файл");
                            if (ret == JFileChooser.APPROVE_OPTION) {
                                File file = fileopen.getSelectedFile();
                                data = new InputFile(file.toPath().toString());
                                replaceBase =  data.getReplace(0,0,1);
                                openTextButton.setEnabled(true);
                                for (int i = 0;i<replaceBase.length;i++)
                                    System.out.println(replaceBase[i].replacement+" "+replaceBase[i].substitute+" "+replaceBase[i].priority+" "+replaceBase[i].minDis+" "+replaceBase[i].type + " " + replaceBase[i].coeffOfUsed);
                                //System.out.println(text);

                                //ahaCorasickText(text.get(0), replaceBase);
                                //for (int i = 0;i<replaceBase.length;i++)
                                //System.out.println(replaceBase[i].replacement+" "+replaceBase[i].substitute+" "+replaceBase[i].priority+" "+replaceBase[i].minDis+" "+replaceBase[i].type + " " + replaceBase[i].coeffOfUsed);



                            }
                        }
                    }
            );
            openTextButton.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFileChooser fileopen = new JFileChooser();
                            int ret = fileopen.showDialog(null, "Открыть файл");
                            if (ret == JFileChooser.APPROVE_OPTION) {
                                File file = fileopen.getSelectedFile();
                                ArrayList<String> text;
                                text = data.getText(file.toPath().toString(), 1, 1, 1);
                                System.out.println(text);
                                saveTextButton.setEnabled(true);

                            }
                        }
                    });
            saveTextButton.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("22222");
                        }
                    }
            );
            //setLayout(new GridLayout(1,1));
            toolBar.setFloatable(false);
            toolBar.setSize(new Dimension(this.getWidth(),100));
            toolBar.add(openPatternButton);
            toolBar.add(openTextButton);
            toolBar.add(saveTextButton);
            toolBar.setPreferredSize(new Dimension(this.getWidth(),100));
            add(toolBar, BorderLayout.NORTH);
            Container container1 = getContentPane();
            container1.setLayout(new FlowLayout());
            Container container = new Container();
            container.setLayout(new GridLayout(3,3,50,10));
           // setLayout(new GridBagLayout());
            //GridBagConstraints c = new GridBagConstraints();
            JPanel panelE = new JPanel(new GridLayout(4,1));
            panelE.add(modifyELabel);
            panelE.add(e1radio);
            panelE.add(e2radio);
            panelE.add(e3radio);

        JPanel panelU = new JPanel(new GridLayout(3,1));
        panelU.add(modifyULabel);
        panelU.add(u1radio);
        panelU.add(u2radio);

        JPanel panelZi = new JPanel(new GridLayout(3,1));
        panelZi.add(modifyZiLabel);
        panelZi.add(zi1radio);
        panelZi.add(zi2radio);

        JPanel panelVariable = new JPanel(new GridLayout(3,1));
        panelVariable.add(variantLabel);
        panelVariable.add(variant1radio);
        panelVariable.add(variant2radio);

        JPanel panelBaseMin = new JPanel(new GridLayout(2,1));
        panelBaseMin.add(baseMinDisLabel);
        panelBaseMin.add(baseMinDisInput);

        JPanel panelProbMin = new JPanel(new GridLayout(2,1));
        panelProbMin.add(probMinDisLabel);
        panelProbMin.add(probMinDisInput);

        JPanel panelProb = new JPanel(new GridLayout(2,1));
        panelProb.add(probabilityLabel);
        panelProb.add(probabilityInput);


        container.add(panelBaseMin);
        container.add(panelProbMin);
        container.add(panelProb);
            container.add(panelE);
            container.add(panelU);
            container.add(panelZi);
            container.add(panelVariable);


            container1.add(toolBar,BorderLayout.NORTH);
            container1.add(container);


//            Container containerE = this.getContentPane();
//                containerE.setLayout(new GridLayout(2,1));
//                containerE.add(modifyELabel);
                ButtonGroup groupE = new ButtonGroup();
                groupE.add(e1radio);
                groupE.add(e2radio);
                groupE.add(e3radio);
                //add(groupE,c);
//                containerE.add(e1radio);
                e1radio.setSelected(true);
//                containerE.add(e2radio);
//                containerE.add(e3radio);

//            Container containerU = this.getContentPane();
//            //containerU.setLayout(new GridLayout(2,1));
//            containerU.add(modifyULabel);
            ButtonGroup groupU = new ButtonGroup();
            groupU.add(u1radio);
            groupU.add(u2radio);
//            containerU.add(u1radio);
//            containerU.add(u2radio);
            u2radio.setSelected(true);
//
//            Container containerZi = this.getContentPane();
//            //containerZi.setLayout(new GridLayout(2,1));
//            containerZi.add(modifyZiLabel);
            ButtonGroup groupZi = new ButtonGroup();
            groupZi.add(zi1radio);
            groupZi.add(zi2radio);
//            containerZi.add(zi1radio);
//            containerZi.add(zi2radio);
            zi1radio.setSelected(true);
//
//            Container containerVariable = this.getContentPane();
//            //containerVariable.setLayout(new GridLayout(2,1));
//            containerVariable.add(variantLabel);
            ButtonGroup groupVar = new ButtonGroup();
            groupVar.add(variant1radio);
            groupVar.add(variant2radio);
//            containerVariable.add(variant1radio);
//            containerVariable.add(variant2radio);
            variant1radio.setSelected(true);
//
//            Container containerBaseMin = this.getContentPane();
//            containerBaseMin.setLayout(new GridLayout(2,1));
//            containerBaseMin.add(baseMinDisLabel);
//            containerBaseMin.add(baseMinDisInput);
//
//            Container containerProbMin = this.getContentPane();
//            containerProbMin.setLayout(new GridLayout(2,1));
//            containerProbMin.add(probMinDisLabel);
//            containerProbMin.add(probMinDisInput);
//
//            Container containerProb = this.getContentPane();
//            containerProb.setLayout(new GridLayout(2,1));
//            containerProb.add(probabilityLabel);
//            containerProb.add(probabilityInput);
            //button.addActionListener(new ButtonEventListener());
            //container.add(button);

        }

        class ButtonEventListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String message = "";
                message += "Button was pressed\n";
                //message += "Text is " + input.getText() + "\n";
               // message += (radio1.isSelected()?"Radio #1":"Radio #2")
                        //+ " is selected\n";
               // message += "CheckBox is " + ((check.isSelected())
                //        ?"checked":"unchecked");
                JOptionPane.showMessageDialog(null,
                        message,
                        "Output",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }
}
