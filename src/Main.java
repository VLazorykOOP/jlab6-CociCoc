import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Clock {
    private int hour;
    private int minute;

    public Clock(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void incrementHour() {
        hour = (hour + 1) % 24;
    }

    public void decrementHour() {
        hour = (hour + 23) % 24;
    }

    public void incrementMinute() {
        minute = (minute + 1) % 60;
        if (minute == 0) {
            incrementHour();
        }
    }

    public void decrementMinute() {
        minute = (minute + 59) % 60;
        if (minute == 59) {
            decrementHour();
        }
    }
}

class ClockPanel extends JPanel {
    private Clock clock;
    private JLabel label;

    public ClockPanel(Clock clock) {
        this.clock = clock;
        label = new JLabel(getTimeString(), SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 40));
        add(label);
    }

    public void updateClock() {
        label.setText(getTimeString());
    }

    private String getTimeString() {
        return String.format("%02d:%02d", clock.getHour(), clock.getMinute());
    }
}

class ButtonPanel extends JPanel {
    private Clock clock;

    public ButtonPanel(Clock clock) {
        this.clock = clock;
        setLayout(new GridLayout(2, 2, 10, 10));

        JButton incHourButton = new JButton("+ Hour");
        incHourButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clock.incrementHour();
                ((ClockPanel) getParent().getComponent(0)).updateClock();
            }
        });
        add(incHourButton);

        JButton decHourButton = new JButton("- Hour");
        decHourButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clock.decrementHour();
                ((ClockPanel) getParent().getComponent(0)).updateClock();
            }
        });
        add(decHourButton);

        JButton incMinButton = new JButton("+ Minute");
        incMinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clock.incrementMinute();
                ((ClockPanel) getParent().getComponent(0)).updateClock();
            }
        });
        add(incMinButton);

        JButton decMinButton = new JButton("- Minute");
        decMinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clock.decrementMinute();
                ((ClockPanel) getParent().getComponent(0)).updateClock();
            }
        });
        add(decMinButton);
    }
}

public class Main {
    public static void main(String[] args) {
        Clock clock = new Clock(12, 0);
        JFrame frame = new JFrame("Analog Clock");

        ClockPanel clockPanel = new ClockPanel(clock);
        frame.add(clockPanel, BorderLayout.CENTER);

        ButtonPanel buttonPanel = new ButtonPanel(clock);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}