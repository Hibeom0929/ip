import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Univus {
    private static Scanner scanner;
    private static List<Task> store;
    public Univus() {
        this.scanner = new Scanner(System.in);
        this.store = new ArrayList<>();
    }
    public void greet() {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Univus");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
    }
    public void bye() {
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
    public void echo(String message) throws UnivusException {
        if (message.equals("bye")){
            bye();
            scanner.close();
        } else if (message.equals("list")) {
            System.out.println("____________________________________________________________");
            int index = 1;
            for (Task msg : store) {
                System.out.println(index + "." + msg.toString());
                index++;
            }
            System.out.println("____________________________________________________________");
        } else if (message.matches("mark\\s\\d+")) {
            try {
                int index = Integer.parseInt(message.split(" ")[1]);

                if (index > store.size() || index < 1) {
                    throw new UnivusException("OOPS!!! Wrong Index!");
                }
                Task task = store.get(index - 1);
                if (task.isDone()) {
                    throw new UnivusException("Already Marked!");
                } else {
                    task.mark();
                    System.out.println("____________________________________________________________");
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("\t" + task.toString());
                    System.out.println("____________________________________________________________");
                }
            } catch (UnivusException error) {
                System.out.println("____________________________________________________________");
                System.out.println(error.getMessage());
                System.out.println("____________________________________________________________");
            }
        } else if (message.matches("unmark\\s\\d+")) {
            try {
                int index = Integer.parseInt(message.split(" ")[1]);
                if (index > store.size() || index < 1) {
                    throw new UnivusException("OOPS!!! Wrong Index!");
                }
                Task task = store.get(index - 1);
                if (!task.isDone()) {
                    throw new UnivusException("Already Unmarked!");
                } else {
                    task.unMark();
                    System.out.println("____________________________________________________________");
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("\t" + task.toString());
                    System.out.println("____________________________________________________________");
                }
            } catch (UnivusException error) {
                System.out.println("____________________________________________________________");
                System.out.println(error.getMessage());
                System.out.println("____________________________________________________________");
            }
        } else if (message.startsWith("todo")) {
            try {
                if (message.equals("todo")) {
                    throw new UnivusException("OOPS!!! The description of a todo cannot be empty.");
                } else {
                    ToDos todo = new ToDos(message);
                    store.add(todo);
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("\t" + todo.toString());
                    System.out.println("Now you have " + store.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }
            } catch (UnivusException error) {
                System.out.println("____________________________________________________________");
                System.out.println(error.getMessage());
                System.out.println("____________________________________________________________");
            }
        } else if (message.startsWith("deadline")) {
            try {
                if (message.equals("deadline")) {
                    throw new UnivusException("OOPS!!! The description of a deadline cannot be empty.");
                } else {
                    String dueDate = message.split("/")[1];
                    String description = message.split("/")[0];
                    Deadlines deadline = new Deadlines(description, dueDate);
                    store.add(deadline);
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("\t" + deadline.toString());
                    System.out.println("Now you have " + store.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }
            } catch (UnivusException error) {
                System.out.println("____________________________________________________________");
                System.out.println(error.getMessage());
                System.out.println("____________________________________________________________");
            }
        } else if (message.startsWith("event")) {
            try {
                if (message.equals("event")) {
                    throw new UnivusException("OOPS!!! The description of a deadline cannot be empty.");
                } else {
                    String description = message.split("/")[0];
                    String start = message.split("/")[1];
                    String end = message.split("/")[2];
                    Events event = new Events(description, start, end);
                    store.add(event);
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("\t" + event.toString());
                    System.out.println("Now you have " + store.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }
            } catch (UnivusException error) {
                System.out.println("____________________________________________________________");
                System.out.println(error.getMessage());
                System.out.println("____________________________________________________________");
            }
        } else if (message.matches("delete\\s\\d+")) {
            try {
                int index = Integer.parseInt(message.split(" ")[1]);
                if (index > store.size() || index < 1) {
                    throw new UnivusException("OOPS!!! Wrong Index!");
                } else {
                    Task task = store.remove(index - 1);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Noted. I've removed this task:");
                    System.out.println("\t" + task.toString());
                    System.out.println("Now you have " + store.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }
            } catch (UnivusException error) {
                System.out.println("____________________________________________________________");
                System.out.println(error.getMessage());
                System.out.println("____________________________________________________________");
            }
        } else {
            try {
                throw new UnivusException("OOPS!!! I'm sorry, but I don't know what that means...");
            } catch (UnivusException error) {
                System.out.println("____________________________________________________________");
                System.out.println(error.getMessage());
                System.out.println("____________________________________________________________");
            }
        }
    }
    public void saveToFile(String filename, List<Task> store) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Task task : store) {
                writer.println(task.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Task> loadFromFile(String filename) {
        List<Task> store = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String message;
            while ((message = reader.readLine()) != null) {
                if (message.startsWith("[T]")) {
                    int index = message.lastIndexOf("]");
                    String description = "todo" + message.substring(index + 1);
                    ToDos todo = new ToDos(description);
                    store.add(todo);
                } else if (message.startsWith("[D]")) {
                    int startIndex = message.lastIndexOf("]");
                    int endIndex = message.lastIndexOf("(");
                    int timeIndex = message.lastIndexOf(":");
                    String dueDate = "by" + message.substring(timeIndex + 1, message.length() - 1);
                    String description = message.substring(startIndex + 2, endIndex);
                    Deadlines deadline = new Deadlines(description, dueDate);
                    store.add(deadline);
                } else if (message.startsWith("[E]")) {
                    int startIndex = message.lastIndexOf("]");
                    int endIndex = message.lastIndexOf("(");
                    int startIdx = message.indexOf(":");
                    int endIdx = message.lastIndexOf(":");
                    String description = message.substring(startIndex + 2, endIndex);
                    String start = "from" + message.substring(startIdx + 1, endIdx - 2);
                    String end = "to" + message.substring(endIdx + 1, message.length() - 1);
                    Events event = new Events(description, start, end);
                    store.add(event);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found. Creating an empty list.");
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return store;
    }

    public static void main(String[] args) throws UnivusException {
        Univus univus = new Univus();
        univus.greet();
        store = univus.loadFromFile("./data/Univus.txt");
        while (true) {
            String message = scanner.nextLine();
            univus.echo(message);
            if (message.equals("bye")) {
                break;
            }
        }
        univus.saveToFile("./data/Univus.txt", store);
    }
}
