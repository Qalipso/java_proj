package mypack;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import com.sun.tools.javac.Main;
import mypack.ChangeStr;
import mypack.Replace;
import mypack.InputFile;
import mypack.AhoCorasick;
import mypack.HundlerWord;

import javax.swing.*;
import java.util.Arrays;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.text.MaskFormatter;

public class MainWindow extends JFrame {
    InputFile data;
    Replace[] replaceBase;
    ArrayList<String> text;
    private JButton openPatternButton = new JButton("Открыть файл Базы замен");
    private JButton openTextButton = new JButton("Открыть текст");
    private JButton saveTextButton = new JButton("Сохранить в файл");
    private JButton openDirButton = new JButton("Открыть папку с базами");
    private JButton helpButton = new JButton("О программе");
    private JButton optionsButton = new JButton("Открыть options");
    private JButton coeffUsedSortButton = new JButton("Отсортировать по показателю использованности");
    private JButton countUsedSortButton = new JButton("Отсортировать по кол-ву выполненных замен");
    private JToolBar toolBar = new JToolBar();
    private JLabel baseMinDisLabel = new JLabel("Введите базовую мин. дистанцию");
    private JLabel probMinDisLabel = new JLabel("Введите допустимую мин. дистанцию");
    private JLabel modifyELabel = new JLabel("Режим обработки е");
    private JLabel modifyULabel = new JLabel("Режим обработки ё,ю,я");
    private JLabel modifyZiLabel = new JLabel("Режим обработки слога ци");
    private JLabel probabilityLabel = new JLabel("Вероятность отказа от замены");
    private JLabel variantLabel = new JLabel("Добавление маркеров вариантов");
    private JLabel randUsedLabel = new JLabel("Рандомизация использованности");
    private JLabel randMinDisLabel = new JLabel("Рандомизация мин.дис");
    private JTextField randMinDisInput = new JTextField("", 5);
    private JTextField randUsedInput = new JTextField("", 5);


    private JRadioButton e1radio = new JRadioButton("без особенностей");
    private JRadioButton e2radio = new JRadioButton("е интерпретируются как э");
    private JRadioButton e3radio = new JRadioButton("е интерпретируются с условиями");
    private JRadioButton u1radio = new JRadioButton("без особенностей");
    private JRadioButton u2radio = new JRadioButton("обработка ё,ю,я как йо,йу,йа");
    private JRadioButton zi1radio = new JRadioButton("без особенностей");
    private JRadioButton zi2radio = new JRadioButton("цы интерпретируются как ци");
    private JRadioButton variant1radio = new JRadioButton("без особенностей");
    private JRadioButton variant2radio = new JRadioButton("вставка символов \\ и /");
    private JTextField baseMinDisInput = new JTextField("", 5);
    private JTextField probMinDisInput = new JTextField("", 5);
    private JTextField probabilityInput = new JTextField("", 5);
    private JTextArea statistics = new JTextArea(10, 30);

    public MainWindow() {
        super("Simple Example");
        //System.out.println();
        this.setBounds(100, 100, 950, 580);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        countUsedSortButton.setEnabled(false);
        coeffUsedSortButton.setEnabled(false);

        statistics.setEditable(false);
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

        toolBar.setFloatable(false);
        toolBar.add(openPatternButton);
        toolBar.add(openDirButton);
        toolBar.add(openTextButton);
        toolBar.add(saveTextButton);
        toolBar.add(optionsButton);
        toolBar.add(helpButton);
        toolBar.setPreferredSize(new Dimension(this.getWidth() - 20, 50));
        add(toolBar, BorderLayout.NORTH);
        Container container1 = getContentPane();
        container1.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        Container container = new Container();
        container.setLayout(new GridLayout(3, 5, 50, 10));

        baseMinDisInput.setPreferredSize(new Dimension(80, 10));
        probMinDisInput.setPreferredSize(new Dimension(80, 10));
        probabilityInput.setPreferredSize(new Dimension(80, 10));
        randMinDisInput.setPreferredSize(new Dimension(80, 10));
        JPanel panelE = new JPanel(new GridLayout(4, 1));
        panelE.setPreferredSize(new Dimension(150, 50));
        panelE.add(modifyELabel);
        panelE.add(e1radio);
        panelE.add(e2radio);
        panelE.add(e3radio);

        JPanel panelU = new JPanel(new GridLayout(3, 1));
        panelU.setPreferredSize(new Dimension(200, 100));
        panelU.add(modifyULabel);
        panelU.add(u1radio);
        panelU.add(u2radio);

        JPanel panelZi = new JPanel(new GridLayout(3, 1));
        panelZi.setPreferredSize(new Dimension(150, 50));
        panelZi.add(modifyZiLabel);
        panelZi.add(zi1radio);
        panelZi.add(zi2radio);

        JPanel panelVariable = new JPanel(new GridLayout(3, 1));
        panelVariable.setPreferredSize(new Dimension(150, 50));
        panelVariable.add(variantLabel);
        panelVariable.add(variant1radio);
        panelVariable.add(variant2radio);

        JPanel panelBaseMin = new JPanel(new GridLayout(2, 1));
        panelBaseMin.setPreferredSize(new Dimension(150, 50));
        panelBaseMin.add(baseMinDisLabel);
        panelBaseMin.add(baseMinDisInput);

        JPanel panelProbMin = new JPanel(new GridLayout(2, 1));
        panelProbMin.setPreferredSize(new Dimension(150, 50));
        panelProbMin.add(probMinDisLabel);
        panelProbMin.add(probMinDisInput);

        JPanel panelProb = new JPanel(new GridLayout(2, 1));
        panelProb.setPreferredSize(new Dimension(150, 50));
        panelProb.add(probabilityLabel);
        panelProb.add(probabilityInput);

        JPanel panelRandUsed = new JPanel(new GridLayout(2, 1));
        panelRandUsed.setPreferredSize(new Dimension(150, 50));
        panelRandUsed.add(randUsedLabel);
        panelRandUsed.add(randUsedInput);

        JPanel panelRandMinDis = new JPanel(new GridLayout(2, 1));
        panelRandMinDis.setPreferredSize(new Dimension(150, 50));
        panelRandMinDis.add(randMinDisLabel);
        panelRandMinDis.add(randMinDisInput);

        container.add(panelBaseMin);
        container.add(panelProbMin);
        container.add(panelProb);
        container.add(panelRandUsed);
        container.add(panelRandMinDis);
        container.add(panelE);
        container.add(panelU);
        container.add(panelZi);
        container.add(panelVariable);

        container1.add(container, c);
        //container1.setPreferredSize(new Dimension(500, 500));
        Container statisticPanel = new Container();
        statisticPanel.setLayout(new GridBagLayout());

        statisticPanel.add(new Label("Статистика"), c);
        statisticPanel.add(statistics, c);
        statisticPanel.add(new JScrollPane(statistics));
        statisticPanel.add(coeffUsedSortButton, c);
        statisticPanel.add(countUsedSortButton, c);
        container1.add(statisticPanel, c);

        ButtonGroup groupE = new ButtonGroup();
        groupE.add(e1radio);
        groupE.add(e2radio);
        groupE.add(e3radio);

        e2radio.setSelected(true);

        ButtonGroup groupU = new ButtonGroup();
        groupU.add(u1radio);
        groupU.add(u2radio);

        u2radio.setSelected(true);

        ButtonGroup groupZi = new ButtonGroup();
        groupZi.add(zi1radio);
        groupZi.add(zi2radio);

        zi1radio.setSelected(true);

        ButtonGroup groupVar = new ButtonGroup();
        groupVar.add(variant1radio);
        groupVar.add(variant2radio);

        variant1radio.setSelected(true);
        openPatternButton.addActionListener((e) -> {
                    replaceBase = null;
                    JFileChooser fileopen = new JFileChooser(new File(MainWindow.class.getProtectionDomain().getCodeSource().getLocation().getPath()));
                    int ret = fileopen.showDialog(null, "Открыть файл");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File file = fileopen.getSelectedFile();
                        try {
                            data = new InputFile(file.toPath().toString());
                        } catch (Throwable ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "MainWindow", JOptionPane.INFORMATION_MESSAGE);

                        }
                        int baseMinDis;
                        int probMinDis;
                        double probability;
                        double randmindis;
                        double randused;

                        try {
                            if (baseMinDisInput.getText().equals(""))
                                baseMinDis = 0;
                            else
                                baseMinDis = Integer.parseInt(baseMinDisInput.getText());
                            if (probMinDisInput.getText().equals(""))
                                probMinDis = 0;
                            else
                                probMinDis = Integer.parseInt(baseMinDisInput.getText());
                            if (probabilityInput.getText().equals(""))
                                probability = 0;
                            else if ((Double.parseDouble(probabilityInput.getText()) <= 1) && (Double.parseDouble(probabilityInput.getText()) >= 0))
                                probability = Double.parseDouble(probabilityInput.getText());
                            else
                                throw new NumberFormatException();
                            if (randMinDisInput.getText().equals(""))
                                randmindis = 0;
                            else
                                randmindis = Double.parseDouble(randMinDisInput.getText());
                            if (randUsedInput.getText().equals(""))
                                randused = 0;
                            else
                                randused = Double.parseDouble(randUsedInput.getText());

                            try {
                                replaceBase = data.getReplace(baseMinDis, probMinDis, Integer.parseInt(groupU.getSelection().getActionCommand()), probability, randmindis, randused);
                            } catch (Throwable ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage() + " файла " + file.getName(), "MainWindow", JOptionPane.INFORMATION_MESSAGE);
                            }
                            if (replaceBase != null) {
                                openTextButton.setEnabled(true);
                            }

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Ошибка ввода значений в полях", "MainWindow", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Throwable exe) {
                            JOptionPane.showMessageDialog(null, exe.getMessage() + " файла " + fileopen.getName(), "MainWindow", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
        );

        openTextButton.addActionListener((e) -> {
            text = null;
            JFileChooser fileopen = new JFileChooser(new File(MainWindow.class.getProtectionDomain().getCodeSource().getLocation().getPath()));
            int ret = fileopen.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                try {
                    text = data.getText(file.toPath().toString(), Integer.parseInt(groupE.getSelection().getActionCommand()), Integer.parseInt(groupU.getSelection().getActionCommand()), Integer.parseInt(groupZi.getSelection().getActionCommand()));
                } catch (Throwable ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "MainWindow", JOptionPane.INFORMATION_MESSAGE);
                }
                saveTextButton.setEnabled(true);
                System.out.println(text);
            }
        });

        saveTextButton.addActionListener((e) -> {
                    ChangeStr tmp = new ChangeStr();
                    JFileChooser fc = new JFileChooser(new File(MainWindow.class.getProtectionDomain().getCodeSource().getLocation().getPath()));
                    if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        try (FileWriter fw = new FileWriter(fc.getSelectedFile())) {
                            for (int i = 0; i < text.size(); i++) {
                                fw.write(tmp.getFinStr(ahaCorasickText(text.get(i), replaceBase, Integer.parseInt(groupVar.getSelection().getActionCommand())),Integer.parseInt(groupU.getSelection().getActionCommand()))+ System.lineSeparator());
                            }
                            countUsedSortButton.setEnabled(true);
                            coeffUsedSortButton.setEnabled(true);
                        } catch (IOException exe) {
                            JOptionPane.showMessageDialog(null, "Ошибка в записи файла", "MainWindow", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
        );

        openDirButton.addActionListener((e) -> {
                    replaceBase = null;
                    boolean errs = false;
                    int baseMinDis;
                    int probMinDis;
                    double probability;
                    double randmindis;
                    double randused;

                    JFileChooser fileopen = new JFileChooser(new File(MainWindow.class.getProtectionDomain().getCodeSource().getLocation().getPath()));
                    fileopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int ret = fileopen.showDialog(null, "Открыть папку с базами замен");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File folder = fileopen.getSelectedFile();
                        if (folder.isDirectory()) {
                            ArrayList<Replace> tmparr = new ArrayList<>();
                            File[] filesInDir = folder.listFiles();
                            for (File f : filesInDir) {
                                try {
                                    if (baseMinDisInput.getText().equals(""))
                                        baseMinDis = 0;
                                    else
                                        baseMinDis = Integer.parseInt(baseMinDisInput.getText());
                                    if (probMinDisInput.getText().equals(""))
                                        probMinDis = 0;
                                    else
                                        probMinDis = Integer.parseInt(baseMinDisInput.getText());
                                    if (probabilityInput.getText().equals(""))
                                        probability = 0;
                                    else if ((Double.parseDouble(probabilityInput.getText()) <= 1) && (Double.parseDouble(probabilityInput.getText()) >= 0))
                                        probability = Double.parseDouble(probabilityInput.getText());
                                    else
                                        throw new NumberFormatException();
                                    if (randMinDisInput.getText().equals(""))
                                        randmindis = 0;
                                    else
                                        randmindis = Double.parseDouble(randMinDisInput.getText());
                                    if (randUsedInput.getText().equals(""))
                                        randused = 0;
                                    else
                                        randused = Double.parseDouble(randUsedInput.getText());

                                    data = new InputFile(f.toPath().toString());
                                    try {
                                        tmparr.addAll(Arrays.asList(data.getReplace(baseMinDis, probMinDis, Integer.parseInt(groupU.getSelection().getActionCommand()), probability, randmindis, randused)));
                                    } catch (Throwable ex) {
                                        JOptionPane.showMessageDialog(null, ex.getMessage() + " файла " + f.getName(), "MainWindow", JOptionPane.INFORMATION_MESSAGE);
                                        errs = true;
                                    }
                                }
                                catch (NumberFormatException ex) {
                                    errs = true;
                                    JOptionPane.showMessageDialog(null, "Ошибка ввода значений в полях", "MainWindow", JOptionPane.INFORMATION_MESSAGE);
                                } catch (Throwable exe) {
                                    errs = true;
                                    JOptionPane.showMessageDialog(null, exe.getMessage() + " файла " + f.getName(), "MainWindow", JOptionPane.INFORMATION_MESSAGE);
                                }
                                if (errs)
                                    break;
                            }
                            if (!errs) {
                                replaceBase = tmparr.toArray(new Replace[tmparr.size()]);
                                openTextButton.setEnabled(true);
                                for (int i = 0; i < replaceBase.length; i++)
                                    System.out.println(replaceBase[i].replacement + " " + replaceBase[i].substitute + " приоритет: " + replaceBase[i].priority + " мин дис: " + replaceBase[i].minDis + " важность: " + replaceBase[i].importance + " " + replaceBase[i].coeffOfUsed);
                            }
                        }
                    }
                }
        );

        helpButton.addActionListener((e) -> {
                    mypack.Help w = new mypack.Help();
                    w.setVisible(true);
                }
        );

        coeffUsedSortButton.addActionListener((e) -> {
                    Arrays.sort(replaceBase, (o1, o2) -> compareCoef(o1, o2));
                    StringBuilder tmpStr = new StringBuilder();
                    for (int i = 0; i < replaceBase.length; i++) {
                        tmpStr.append(replaceBase[i].replacement + " " + replaceBase[i].substitute + " коэфф. исп." + replaceBase[i].coeffOfUsed + "\n");
                    }
                    statistics.setText(tmpStr.toString());

                }
        );

        countUsedSortButton.addActionListener((e) -> {
                    Arrays.sort(replaceBase, (o1, o2) -> compareCount(o1, o2));
                    StringBuilder tmpStr = new StringBuilder();
                    for (int i = 0; i < replaceBase.length; i++) {
                        tmpStr.append(replaceBase[i].replacement + " " + replaceBase[i].substitute + " успешных замен:" + replaceBase[i].countGood + " несостоявшихся замен:" + replaceBase[i].countBad + " фиктывные замены: " + replaceBase[i].countFake + "\n");
                    }
                    statistics.setText(tmpStr.toString());
                }
        );
        optionsButton.addActionListener((e) -> {
            JFileChooser fileopen = new JFileChooser(new File(MainWindow.class.getProtectionDomain().getCodeSource().getLocation().getPath()));
            fileopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int ret = fileopen.showDialog(null, "Открыть options");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                /*
                    file - открытый options файл
                 */
            }
        });
    }

    public static String ahaCorasickText(String text, Replace[] replaces, int mark) {
        AhoCorasick ahoCorasick;
        String[] words = text.split(" ");
        if (!Character.isLetter(words[0].charAt(0))) words[0] = words[0].substring(1);
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            // Ахо-Карасик
            ahoCorasick = new AhoCorasick();
            System.out.println(replaces);
            for (int k = 0; k < replaces.length; k++) {
                ahoCorasick.addToBohr(replaces[k].replacement);
            }

            HundlerWord hw = new HundlerWord(words[i], replaces, mark);
            ahoCorasick.findInd(words[i], hw);

            // Обработка слова
            //System.out.print("-----> " + words[i] + " :");
            //hw.printIndexes();

          //  System.out.println(hw.launch(replaces));
            res.append(hw.launch() + " ");
        }
//        for (int i = 0; i < replaces.length; i++)
//            System.out.println(replaces[i].replacement + " " + replaces[i].substitute + " " + replaces[i].priority + " " + replaces[i].minDis + " " + replaces[i].importance + " " + replaces[i].coeffOfUsed + " " + replaces[i].countGood + " " + replaces[i].countBad + " " + replaces[i].countFake);

        return res.toString();
    }

    public static int compareCoef(Replace o1, Replace o2) {
        if (Math.abs(o2.coeffOfUsed - o1.coeffOfUsed) < 0.00001) {
            return 0;
        } else {
            if (o1.coeffOfUsed < o2.coeffOfUsed) return -1;
            else return 1;
        }
    }

    public static int compareCount(Replace o1, Replace o2) {
        return ((o2.countGood == o1.countGood) ?

                ((o1.countBad == o2.countBad) ?

                        ((o2.countFake == o1.countFake) ? 0 : (o2.countFake - o1.countFake))

                        : (o2.countBad - o1.countBad))

                : (o2.countGood - o1.countGood));
    }
}
