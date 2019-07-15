package mypack;
import java.awt.*;
import javax.swing.*;
public class Help extends JFrame{
    private JLabel verLabel = new JLabel("Кандзизатор, версия 1.0");
    private JLabel aboutDisLabel = new JLabel("Об авторах:");
    private JLabel universityLabel = new JLabel("СПбГЭТУ \"ЛЭТИ\"");
    private JLabel fioDisLabel = new JLabel("Шаталов Эдуард & Миннинг Михаил,");
    private JLabel fioDisLabel2 = new JLabel("Фирсов М.А.");
    private JLabel dataLabel = new JLabel("2019");
    public Help(){
        super("О программе");
        //System.out.println();
        this.setBounds(100,100,400,200);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Container container = getContentPane();
        container.setLayout(new GridBagLayout());
        GridBagConstraints c =  new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill   = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth  = GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        container.add(verLabel,c);
        container.add(aboutDisLabel,c);
        container.add(universityLabel,c);
        container.add(fioDisLabel,c);
        container.add(fioDisLabel2,c);
        container.add(new JLabel(" "),c);
        container.add(dataLabel,c);
    }
}
