package mypack;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.ButtonGroup;

public class MainWindow extends JFrame {
    InputFile data;
    Replace[] replaceBase;
    ArrayList<String> text;
    Map<Integer, Integer> groupCount;
    HashMap<String, Float> veropropuskov;
    private JButton openPatternButton = new JButton("Открыть файл Базы замен");
    private JButton openTextButton = new JButton("Открыть текст");
    private JButton saveTextButton = new JButton("Сохранить в файл");
    private JButton openDirButton = new JButton("Открыть папку с базами");
    private JButton helpButton = new JButton("О программе");
    private JButton optionsButton = new JButton("Открыть options");
    private JButton coeffUsedSortButton = new JButton("Отсортировать по показателю использованности");
    private JButton countUsedSortButton = new JButton("Отсортировать по кол-ву выполненных замен");
    private JButton groupSortButton = new JButton("Отсортировать по группам");
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
    private JTextField randMinDisInput = new JTextField("0.0", 5);
    private JTextField randUsedInput = new JTextField("0.0", 5);


    private JRadioButton e1radio = new JRadioButton("без особенностей");
    private JRadioButton e2radio = new JRadioButton("е интерпретируются как э");
    private JRadioButton e3radio = new JRadioButton("е интерпретируются с условиями");
    private JRadioButton u1radio = new JRadioButton("без особенностей");
    private JRadioButton u2radio = new JRadioButton("обработка ё,ю,я как йо,йу,йа");
    private JRadioButton zi1radio = new JRadioButton("без особенностей");
    private JRadioButton zi2radio = new JRadioButton("цы интерпретируются как ци");
    private JRadioButton variant1radio = new JRadioButton("без особенностей");
    private JRadioButton variant2radio = new JRadioButton("вставка символов \\ и /");
    private JTextField baseMinDisInput = new JTextField("0", 5);
    private JTextField probMinDisInput = new JTextField("0", 5);
    private JTextField probabilityInput = new JTextField("0.0", 5);
    private JLabel pathPatternLabel = new JLabel("База замен:");
    private JLabel pathTextLabel = new JLabel("Текст:");
    private JLabel pathOptionLabel = new JLabel("Options:");

    private JTextField pathPatternInput = new JTextField("");
    private JTextField pathTextInput = new JTextField("");
    private JTextField pathOptionInput = new JTextField("");
    private JTextArea statistics = new JTextArea(15, 50);

    public MainWindow() {
        super("Simple Example");
        //System.out.println();
        this.setBounds(100, 100, 950, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        countUsedSortButton.setEnabled(false);
        coeffUsedSortButton.setEnabled(false);
        groupSortButton.setEnabled(false);
        coeffUsedSortButton.setMaximumSize(new Dimension(100,100));
        countUsedSortButton.setMaximumSize(new Dimension(100,100));

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
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
       // c.gridx = GridBagConstraints.RELATIVE;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 1;
        c.ipady = 1;
        c.weightx = 0.1;
        c.weighty = 0.0;
        Container container = new Container();
        container.setLayout(new GridLayout(3, 5, 50, 10));

        baseMinDisInput.setPreferredSize(new Dimension(80, 10));
        probMinDisInput.setPreferredSize(new Dimension(80, 10));
        probabilityInput.setPreferredSize(new Dimension(80, 10));
        randMinDisInput.setPreferredSize(new Dimension(80, 10));
        JPanel panelE = new JPanel(new GridLayout(4, 1));
        //panelE.setPreferredSize(new Dimension(150, 50));
        panelE.add(modifyELabel);
        panelE.add(e1radio);
        panelE.add(e2radio);
        panelE.add(e3radio);

        JPanel panelU = new JPanel(new GridLayout(3, 1));
        //panelU.setPreferredSize(new Dimension(200, 100));
        panelU.add(modifyULabel);
        panelU.add(u1radio);
        panelU.add(u2radio);

        JPanel panelZi = new JPanel(new GridLayout(3, 1));
        //panelZi.setPreferredSize(new Dimension(150, 50));
        panelZi.add(modifyZiLabel);
        panelZi.add(zi1radio);
        panelZi.add(zi2radio);

        JPanel panelVariable = new JPanel(new GridLayout(3, 1));
        //panelVariable.setPreferredSize(new Dimension(150, 50));
        panelVariable.add(variantLabel);
        panelVariable.add(variant1radio);
        panelVariable.add(variant2radio);

        JPanel panelBaseMin = new JPanel(new GridLayout(2, 1));
       // panelBaseMin.setPreferredSize(new Dimension(150, 50));
        panelBaseMin.add(baseMinDisLabel);
        panelBaseMin.add(baseMinDisInput);

        JPanel panelProbMin = new JPanel(new GridLayout(2, 1));
        //panelProbMin.setPreferredSize(new Dimension(150, 50));
        panelProbMin.add(probMinDisLabel);
        panelProbMin.add(probMinDisInput);

        JPanel panelProb = new JPanel(new GridLayout(2, 1));
        //panelProb.setPreferredSize(new Dimension(150, 50));
        panelProb.add(probabilityLabel);
        panelProb.add(probabilityInput);

        JPanel panelRandUsed = new JPanel(new GridLayout(2, 1));
        //panelRandUsed.setPreferredSize(new Dimension(150, 50));
        panelRandUsed.add(randUsedLabel);
        panelRandUsed.add(randUsedInput);

        JPanel panelRandMinDis = new JPanel(new GridLayout(2, 1));
        //panelRandMinDis.setPreferredSize(new Dimension(150, 50));
        panelRandMinDis.add(randMinDisLabel);
        panelRandMinDis.add(randMinDisInput);

        JPanel panelPattern = new JPanel(new GridLayout(2, 1));
        JPanel panelText = new JPanel(new GridLayout(2, 1));
        JPanel panelOptions = new JPanel(new GridLayout(2, 1));

        pathOptionInput.setPreferredSize(new Dimension(100,50));
        pathOptionInput.setEditable(false);
        panelOptions.add(pathOptionLabel);
        panelOptions.add(pathOptionInput);

        pathPatternInput.setPreferredSize(new Dimension(100,50));
        pathPatternInput.setEditable(false);
        panelPattern.add(pathPatternLabel);
        panelPattern.add(pathPatternInput);
        pathTextInput.setPreferredSize(new Dimension(100,50));
        pathTextInput.setEditable(false);
        panelText.add(pathTextLabel);
        panelText.add(pathTextInput);
        container.add(panelPattern);
        container.add(panelText);
        container.add(panelOptions);
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


        //statisticPanel.setPreferredSize(new Dimension(this.getWidth()-40,500));
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.anchor = GridBagConstraints.CENTER;
        statisticPanel.add(new Label("Статистика"), c);
        c.gridheight = 2;
        statisticPanel.add(statistics, c);
        c.gridheight = 1;
        //statistics.setPreferredSize(new Dimension(this.getWidth()-40,500));
        statisticPanel.add(new JScrollPane(statistics));
        statisticPanel.add(coeffUsedSortButton, c);
        statisticPanel.add(countUsedSortButton, c);
        statisticPanel.add(groupSortButton,c);
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
                    int ret = fileopen.showDialog(null, "Открыть файл базы замен");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File file = fileopen.getSelectedFile();
                        try {
                            data = new InputFile(file.toPath().toString());
                        } catch (Throwable ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "MainWindow", JOptionPane.INFORMATION_MESSAGE);

                        }
                        pathPatternInput.setText(file.toPath().toString());
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
            int ret = fileopen.showDialog(null, "Открыть файл исходного текста");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                pathTextInput.setText(file.toPath().toString());
                try {
                    text = data.getPreparedText(file.toPath().toString(), Integer.parseInt(groupE.getSelection().getActionCommand()), Integer.parseInt(groupU.getSelection().getActionCommand()), Integer.parseInt(groupZi.getSelection().getActionCommand()));
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
                            System.out.println(text);
                            groupCount = new HashMap<>();
                            //fw.print(0xEF);
                            fw.write('\ufeff');
                            //fw.write(0xBF);
                            for (int i = 0; i < text.size(); i++) {
                                fw.write(tmp.getFinStr(ahaCorasickText(text.get(i), replaceBase, groupCount, Integer.parseInt(groupVar.getSelection().getActionCommand())),Integer.parseInt(groupU.getSelection().getActionCommand()))+ System.lineSeparator());
                                System.out.println(groupCount);
                            }
                            countUsedSortButton.setEnabled(true);
                            coeffUsedSortButton.setEnabled(true);
                            groupSortButton.setEnabled(true);
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
                        pathPatternInput.setText(folder.toPath().toString());
                        if (folder.isDirectory()) {
                            ArrayList<Replace> tmparr = new ArrayList<>();
                            File[] filesInDir = folder.listFiles();
                            for (File f : filesInDir) {
                                if (f.getName() == "options.txt") {
                                    try {
                                        veropropuskov = InputFile.loadOptions(f.toPath().toString());
                                    } catch (Throwable exe) {
                                        JOptionPane.showMessageDialog(null, exe.getMessage(), "MainWindow", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                } else {
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
                                    } catch (NumberFormatException ex) {
                                        errs = true;
                                        JOptionPane.showMessageDialog(null, "Ошибка ввода значений в полях", "MainWindow", JOptionPane.INFORMATION_MESSAGE);
                                    } catch (Throwable exe) {
                                        errs = true;
                                        JOptionPane.showMessageDialog(null, exe.getMessage() + " файла " + f.getName(), "MainWindow", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                    if (errs)
                                        break;

                                    if (!errs) {
                                        replaceBase = tmparr.toArray(new Replace[tmparr.size()]);
                                        openTextButton.setEnabled(true);
                                        for (int i = 0; i < replaceBase.length; i++)
                                            System.out.println(replaceBase[i].replacement + " " + replaceBase[i].substitute + " приоритет: " + replaceBase[i].priority + " мин дис: " + replaceBase[i].minDis + " важность: " + replaceBase[i].importance + " " + replaceBase[i].coeffOfUsed);
                                    }
                                }
                            }
                        }
                    }
                }
        );

        helpButton.addActionListener((e) -> {
                    Help w = new Help();
                    w.setVisible(true);
                }
        );

        optionsButton.addActionListener((e) -> {
            JFileChooser fileopen = new JFileChooser(new File(MainWindow.class.getProtectionDomain().getCodeSource().getLocation().getPath()));
            int ret = fileopen.showDialog(null, "Открыть options");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                pathOptionInput.setText(file.toPath().toString());
                try {
                    veropropuskov = InputFile.loadOptions(file.toPath().toString());
                } catch (Throwable exe) {
                    JOptionPane.showMessageDialog(null, exe.getMessage(), "MainWindow", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        coeffUsedSortButton.addActionListener((e) -> {
                    GroupReplaces[] groups = new GroupReplaces[groupCount.size()];
                    StringBuilder tmpStr = new StringBuilder();
                    for (int i = 0; i < groupCount.size(); i++) {
                        groups[i] = new GroupReplaces(replaceBase, groupCount, i);
                    }

                    for (int i = 0; i < groupCount.size(); i++) {
                        Arrays.sort(groups[i].replacesInGr, (o1, o2) -> compareCoef(o1, o2));
                        tmpStr.append(groups[i].printC() + "\n");
                    }

                    statistics.setText(tmpStr.toString());
                }
        );

        countUsedSortButton.addActionListener((e) -> {
                    GroupReplaces[] groups = new GroupReplaces[groupCount.size()];
                    StringBuilder tmpStr = new StringBuilder();
                    for (int i = 0; i < groupCount.size(); i++) {
                        groups[i] = new GroupReplaces(replaceBase, groupCount, i);
                    }

                    for (int i = 0; i < groupCount.size(); i++) {
                        Arrays.sort(groups[i].replacesInGr, (o1, o2) -> compareCount(o1, o2));
                        tmpStr.append(groups[i].print() + "\n");
                    }

                    statistics.setText(tmpStr.toString());
                }
        );

        groupSortButton.addActionListener((e) ->{

            GroupReplaces[] groups = new GroupReplaces[groupCount.size()];
            StringBuilder tmpStr = new StringBuilder();
            for (int i = 0; i < groupCount.size(); i++) {
                groups[i] = new GroupReplaces(replaceBase, groupCount, i);
            }

            Arrays.sort(groups, (o1, o2) -> compareGroup(o1, o2));

            for (int i = 0; i < groupCount.size(); i++) {
                tmpStr.append(groups[i].printG() + "\n");
            }

           statistics.setText(tmpStr.toString());
        });
    }

    public String ahaCorasickText(String text, Replace[] replaces, Map<Integer, Integer> groupCount, int mark) {
        AhoCorasick ahoCorasick = new AhoCorasick();
        for (int k = 0; k < replaces.length; k++) {
            ahoCorasick.addToBohr(replaces[k].replacement);
        }
        String[] words = text.split(" ");
        StringBuilder res = new StringBuilder();
        Pattern pattern = Pattern.compile("[а-яА-Я]");
        for (int i = 0; i < words.length; i++) {
            Matcher matcher = pattern.matcher(words[i]);
            if (matcher.find()) {
                HundlerWord hw = new HundlerWord(words[i], replaces, groupCount, mark);
                ahoCorasick.findInd(words[i], hw);

                for (String key : hw.indexes.keySet()) {
                    Collections.shuffle(hw.indexes.get(key));
                }

                if (veropropuskov != null) {
                    // вычеркиваем некоторые найденные фрагменты
                    for (String key : hw.indexes.keySet()) {
                        if (veropropuskov.containsKey(key) && veropropuskov.get(key) > 0) {
                            int lenInd = hw.indexes.get(key).size();
                            for (int j = 0; j < lenInd; j++) {
                                if (HundlerWord.experiment(veropropuskov.get(key))) {
                                    hw.indexes.get(key).remove(0);
                                }
                            }
                        }
                    }
                }


                // Добавление измененного слова в результат
                res.append(hw.launch() + " ");
            }
            else if (words[i].equals(""))
                res.append(" ");
            else
                res.append(words[i]+" ");

        }

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
    public static int compareIntReverse(int o1, int o2) {
        return o1 - o2;
    }

    public static int compareGroup(GroupReplaces o1, GroupReplaces o2) {return o2.countRep - o1.countRep;}
}
