import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class TimeSelectorClock {
    private int hours = 0;
    private int minutes = 0;
    private int seconds = 0;

    public TimeSelectorClock(Consumer<String> onTimeSelected) {
        // Create the frame
        JFrame frame = new JFrame("Select Time");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new BorderLayout());

        // Create a label to display the selected time
        JLabel timeLabel = new JLabel(getFormattedTime(), SwingConstants.CENTER);
        timeLabel.setFont(new Font("Helvetica", Font.PLAIN, 40));

        // Create buttons for hours, minutes, and seconds
        JButton increaseHourButton = new JButton("+ Hour");
        JButton decreaseHourButton = new JButton("- Hour");
        JButton increaseMinuteButton = new JButton("+ Minute");
        JButton decreaseMinuteButton = new JButton("- Minute");
        JButton increaseSecondButton = new JButton("+ Second");
        JButton decreaseSecondButton = new JButton("- Second");
        // Create a "Done" button to finalize the time selection
        JButton doneButton = new JButton("Done");

        // Add action listeners to update time
        increaseHourButton.addActionListener(e -> {
            hours = (hours + 1) % 24;  // Wraps around to 0 after 23
            updateTimeLabel(timeLabel);
        });

        decreaseHourButton.addActionListener(e -> {
            hours = (hours - 1 + 24) % 24;
            updateTimeLabel(timeLabel);
        });

        increaseMinuteButton.addActionListener(e -> {
            minutes = (minutes + 1) % 60;
            updateTimeLabel(timeLabel);
        });

        decreaseMinuteButton.addActionListener(e -> {
            minutes = (minutes - 1 + 60) % 60;
            updateTimeLabel(timeLabel);
        });

        increaseSecondButton.addActionListener(e -> {
            seconds = (seconds + 1) % 60;  // Wraps around to 0 after 59
            updateTimeLabel(timeLabel);
        });

        decreaseSecondButton.addActionListener(e -> {
            seconds = (seconds - 1 + 60) % 60;  // Wraps around to 59 after 0
            updateTimeLabel(timeLabel);
        });

        // "Done" button to return selected time to the caller
        doneButton.addActionListener(e -> {
            String selectedTime = getFormattedTime();
            onTimeSelected.accept(selectedTime);
            frame.dispose(); // Close the clock window after selection
        });

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2));
        buttonPanel.add(increaseHourButton);
        buttonPanel.add(decreaseHourButton);
        buttonPanel.add(increaseMinuteButton);
        buttonPanel.add(decreaseMinuteButton);
        buttonPanel.add(increaseSecondButton);
        buttonPanel.add(decreaseSecondButton);

        // Add components to the frame
        frame.add(timeLabel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(doneButton, BorderLayout.SOUTH);

        // Show the frame
        frame.setVisible(true);
    }

    // Helper to update the time display
    private void updateTimeLabel(JLabel timeLabel) {
        timeLabel.setText(getFormattedTime());
    }

    // Format the time as HH:MM:SS
    private String getFormattedTime() {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
