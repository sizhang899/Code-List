import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class TaskPanel extends JPanel {
    private Task task;
    private JLabel taskLabel;
    private JButton doneButton;
    private JButton deleteButton;

    public TaskPanel(Task task) {
        this.task = task;
        this.setLayout(new BorderLayout());

        taskLabel = new JLabel(task.toString(), SwingConstants.CENTER);
        doneButton = new JButton("Mark as Done");
        deleteButton = new JButton("Delete");
        taskLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));


        doneButton.addActionListener(e -> {
            task.markAsCompleted();
            taskLabel.setText("    " +task.toString());
            doneButton.setEnabled(false);
        });

        deleteButton.addActionListener(e -> {
            // When clicked, the panel itself will be removed by the main GUI
            this.setVisible(false);
        });

        this.add(taskLabel, BorderLayout.WEST);
        this.add(doneButton, BorderLayout.EAST);
        this.add(deleteButton, BorderLayout.AFTER_LAST_LINE);
    }
    public void setText(String x){
        taskLabel.setText(x);
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }
}
