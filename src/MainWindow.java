package mypack;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import mypack.ChangeStr;
import  mypack.Replace;
import mypack.InputFile;
import mypack.AhoCorasick;
import mypack.HundlerWord;
import javax.swing.*;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.text.MaskFormatter;

public class MainWindow extends JFrame {
        InputFile data;
        Replace[] replaceBase;
        ArrayList<String> text;
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
        e1radio.setActionCommand("0");
        e2radio.setActionCommand("1");
        e3radio.setActionCommand("2");
        u1radio.setActionCommand("0");
        u2radio.setActionCommand("1");
        zi1radio.setActionCommand("0");
        zi2radio.setActionCommand("1");
        variant1radio.setActionCommand("0");
        variant2radio.setActionCommand("1");

            //JFileChooser fileChooser = new JFileChooser();
            //addFileChooserListeners();

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
                e2radio.setSelected(true);
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
        openPatternButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileopen = new JFileChooser();
                        int ret = fileopen.showDialog(null, "Открыть файл");
                        if (ret == JFileChooser.APPROVE_OPTION) {
                            File file = fileopen.getSelectedFile();
                            data = new InputFile(file.toPath().toString());
                            int baseMinDis;
                            int probMinDis;
                            try{
                                if (baseMinDisInput.getText().equals("") )
                                    baseMinDis = 0;
                                else
                                    baseMinDis = Integer.parseInt(baseMinDisInput.getText());
                                if (probMinDisInput.getText().equals(""))
                                    probMinDis = 0;
                                else
                                    probMinDis = Integer.parseInt(baseMinDisInput.getText());
                                try {
                                    replaceBase = data.getReplace(baseMinDis, probMinDis, Integer.parseInt(groupU.getSelection().getActionCommand()));
                                }
                                catch (Throwable ex){
                                    System.out.println(ex.getMessage()+ " файла "+ file.getName());
                                }
                                if (replaceBase != null){
                                    openTextButton.setEnabled(true);
                                    for (int i = 0;i<replaceBase.length;i++)
                                        System.out.println(replaceBase[i].replacement+" "+replaceBase[i].substitute+" "+replaceBase[i].priority+" "+replaceBase[i].minDis+" "+replaceBase[i].importance + " " + replaceBase[i].coeffOfUsed);

                                }
                                else {

                                }
                            }
                            catch (NumberFormatException ex){
                                System.out.println("Ошибка ввода значений");
                            }

                            //System.out.println(baseMinDisInput.getText());

                             //System.out.println(text);


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

                            text = data.getText(file.toPath().toString(), Integer.parseInt(groupE.getSelection().getActionCommand()), Integer.parseInt(groupU.getSelection().getActionCommand()), Integer.parseInt(groupZi.getSelection().getActionCommand()));
                            System.out.println(text);

                            saveTextButton.setEnabled(true);
                        }
                    }
                });
        saveTextButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //for (int i = 0;i<data.text.size();i++) {
                        //    ahaCorasickText(data.text.get(i), replaceBase);
                        //}

                        for (int i = 0;i<replaceBase.length;i++)
                        System.out.println(replaceBase[i].replacement+" "+replaceBase[i].substitute+" "+replaceBase[i].priority+" "+replaceBase[i].minDis+" "+replaceBase[i].importance + " " + replaceBase[i].coeffOfUsed);
                        ChangeStr tmp = new ChangeStr();
//                        for (int i = 0;i<text.size();i++)
//                            System.out.println(tmp.getFinStr(text.get(i)));
//                        JFileChooser fileopen = new JFileChooser();
//                        int ret = fileopen.showDialog(null, "сохранить файл");
//                        if (ret == JFileChooser.APPROVE_OPTION) {
//                            File file = fileopen.getSelectedFile();
                        JFileChooser fc = new JFileChooser();
                        if ( fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
                            try ( FileWriter fw = new FileWriter(fc.getSelectedFile()) ) {
                                for (int i = 0;i<text.size();i++) {
                                    fw.write(tmp.getFinStr(ahaCorasickText(text.get(i), replaceBase)));
                                }


                            }
                            catch (IOException exe) {
                                System.out.println("Всё погибло!");
                            }
                        }
//                        try(FileWriter writer = new FileWriter("notes3.txt", false))
//                        {
//                            // запись всей строки
//                            String text = "Hello Gold!";
//                            writer.write(text);
//                            // запись по символам
//                            writer.append('\n');
//                            writer.append('E');
//
//                            writer.flush();
//                        }
//                        catch(IOException ex){
//
//                            System.out.println(ex.getMessage());
//                        }

                    }
                }
        );
        }
    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }

    public static String ahaCorasickText(String text, Replace[] replaces) {
        AhoCorasick ahoCorasick;
        String[] words = text.split(" ");
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            // Ахо-Карасик
            ahoCorasick = new AhoCorasick();
            for (int k = 0; k < replaces.length; k++) {
                ahoCorasick.addToBohr(replaces[k].replacement);
            }

            HundlerWord hw = new HundlerWord(words[i]);
            ahoCorasick.findInd(words[i], hw);

            // Обработка слова
            System.out.print("-----> " + words[i] + " :");
            //hw.printIndexes();

           //System.out.println(hw.launch(replaces));
             res.append(hw.launch(replaces)+" ");
        }
        return res.toString();
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
