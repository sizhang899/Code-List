import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ToDoListManager {
    private final JPanel taskPanel;
    private String[] totalsecs;
    private ArrayList<TaskPanel> taskPanels;
    static int interval;
    static Timer timer;

    public static void main(String[] args) {
        new ToDoListManager();
    }

    public ToDoListManager() {
        JFrame frame = new JFrame("Class To-Do List Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        taskPanels = new ArrayList<>();
        taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(taskPanel);

        JTextField taskField = new JTextField(20);
        JButton addButton = new JButton("Add Task");
        JLabel statusLabel = new JLabel("Status: Ready");

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("New Task:"));
        inputPanel.add(taskField);
        inputPanel.add(addButton);

        addButton.addActionListener(e -> {
            String taskDescription = taskField.getText();
            if (!taskDescription.isEmpty()) {
                Task task = new Task("    " + taskDescription);
                TaskPanel taskPanelComponent = new TaskPanel(task);
                taskPanels.add(taskPanelComponent);
                taskPanel.add(taskPanelComponent);
                taskPanel.revalidate();
                taskPanel.repaint();

                String[] options = {"DO IMMEDIATELY", "URGENT", "Do soon", "chill"};
                int choice = JOptionPane.showOptionDialog(null, "How URGENT is this task?", "Urgency", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                switch (choice) {
                    case 0:
                        taskPanelComponent.setBackground(Color.red);
                        break;
                    case 1:
                        taskPanelComponent.setBackground(Color.ORANGE);
                        break;
                    case 2:
                        taskPanelComponent.setBackground(Color.BLUE);
                        break;
                    case 3:
                        taskPanelComponent.setBackground(Color.GREEN);
                        break;
                }
                // Show the clock and update task panel after time selection
                new TimeSelectorClock(selectedTime -> {
                    // Update task description with the selected time

                    String[] totalsecs = selectedTime.split(":");
                    int hourtosec = Integer.parseInt(totalsecs[0])*3600;
                    int minutetosec = Integer.parseInt(totalsecs[1])*60;
                    int secs = Integer.parseInt(totalsecs[2]);

                    int delay = 1000;
                    int period = 1000;
                    timer = new Timer();
                    interval = hourtosec+minutetosec+secs;
                    timer.scheduleAtFixedRate(new TimerTask() {

                        public void run() {
                            int saveinterval = interval;
                            int sectohour = interval/3600;
                            interval = interval%3600;
                            int sectominute = interval/60;
                            interval = interval%60;
                            int sectosec = interval;
                            interval = saveinterval;
                            System.out.println(sectohour+":"+sectominute+":"+sectosec);
                            setInterval();

                        }
                    }, delay, period);
                    task.setDescription("    " + taskDescription + " - " + selectedTime);
                    taskPanelComponent.setText(task.toString());
                    taskPanel.revalidate();
                    taskPanel.repaint();
                });

                // Add action to delete button
                taskPanelComponent.getDeleteButton().addActionListener(e2 -> {
                    taskPanel.remove(taskPanelComponent);
                    taskPanels.remove(taskPanelComponent);
                    taskPanel.revalidate();
                    taskPanel.repaint();
                });

                taskField.setText("");
                statusLabel.setText("Status: Task Added");
            } else {
                statusLabel.setText("Status: Please enter a task.");
            }
        });

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(statusLabel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
    private static final int setInterval() {
        if (interval == 1){
            timer.cancel();
        }

        return --interval;
    }
}
