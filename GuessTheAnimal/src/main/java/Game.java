import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.function.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    ResourceBundle appResource = ResourceBundle.getBundle("App");
    File file;
    ObjectMapper objectMapper;
    Random random = new Random();
    Set<String> positiveResponse = Set.of(appResource.getStringArray("answer.yes"));
    Set<String> negativeResponse = Set.of(appResource.getStringArray("answer.no"));
    String[] clarificationQuestion = appResource.getStringArray("ask.again");
    String[] farewellResponse = appResource.getStringArray("bye");
    Scanner scanner = new Scanner(System.in);
    BinarySearchTree tree = new BinarySearchTree();
    UnaryOperator<String> isItAnimal = (UnaryOperator<String>) appResource.getObject("animal.question");
    UnaryOperator<String> addAnimal = (UnaryOperator<String>) appResource.getObject("animal.name");
    Consumer<String> askQuestion = (Consumer<String>) appResource.getObject("ask.question");
    BiFunction<String, Boolean, String> printFacts = (BiFunction<String, Boolean, String>) appResource.getObject("game.printFacts");
    BiFunction<Matcher, Boolean, String> processFacts = (BiFunction<Matcher, Boolean, String>) appResource.getObject("process.facts");
    Pattern pattern = (Pattern) appResource.getObject("game.pattern");

    public Game(String format) {
        switch (format) {
            case "json" -> {
                this.objectMapper = new JsonMapper();
                this.file = Locale.getDefault().toString().equals("eo") ? new File("animals_eo.json") : new File("animals.json");
            }
            case "xml" -> {
                this.objectMapper = new XmlMapper();
                this.file = Locale.getDefault().toString().equals("eo") ? new File("animals_eo.xml") : new File("animals.xml");
            }
            case "yaml" -> {
                this.objectMapper = new YAMLMapper();
                this.file = Locale.getDefault().toString().equals("eo") ? new File("animals_eo.yaml") : new File("animals.yaml");
            }
        }
    }

    public void start() {
        if (!file.exists()) {
            System.out.println(appResource.getString("animal.wantLearn"));
            System.out.println(appResource.getString("animal.askFavorite"));
            tree.insertRec(tree.root, addAnimal.apply(scanner.nextLine().toLowerCase()), "");
        } else {
            try {
                tree.root = objectMapper.readValue(file, Node.class);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        greetUser();
        boolean flag = true;
        while (flag) {
            switch (printGameMenu()) {
                case 1 -> {
                    while (true) {
                        if (!play()) break;
                    }
                }
                case 2 -> printList(tree.printAllAnimals(tree.root));
                case 3 -> {
                    System.out.println(appResource.getString("animal.prompt"));
                    String animal = scanner.nextLine();
                    Node temp = tree.searchAnimal(tree.root, addAnimal.apply(animal));
                    if (temp == null) System.out.println(appResource.getString("tree.search.nofacts") + animal + ":");
                    else {
                        System.out.println(appResource.getString("tree.search.facts") + animal + ":");
                        System.out.println(temp.facts);
                    }
                }
                case 4 -> {
                    System.out.println(appResource.getString("tree.stats.title"));
                    List<Node> list = tree.calculateStatistics(tree.root);
                    if (list.isEmpty()) System.out.println(appResource.getString("tree.stats.empty"));
                    else {
                        int noAnimals = 0;
                        for (Node s : list) {
                            if (s.value.startsWith("a") || s.value.split(" ").length == 1) noAnimals++;
                        }
                        System.out.println(appResource.getString("tree.stats.root") + tree.root.value);
                        System.out.println(appResource.getString("tree.stats.nodes") + list.size());
                        System.out.println(appResource.getString("tree.stats.animals") + noAnimals);
                        System.out.println(appResource.getString("tree.stats.statements") + (list.size() - noAnimals));
                        System.out.println(appResource.getString("tree.stats.height") + tree.maxTreeHeight(tree.root));
                        System.out.println(appResource.getString("tree.stats.minimum") + tree.minTreeHeight(tree.root));
                        System.out.println(appResource.getString("tree.stats.average") + (String.format("%.1f", (double) (tree.totalNodesDepth(tree.root, 0)) / noAnimals)));
                    }
                }
                case 5 -> tree.printTree(tree.root);
                case 0 -> {
                    try {
                        objectMapper.writeValue(file, tree.root);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println(farewellResponse[random.nextInt(farewellResponse.length)]);
                    flag = false;
                }
            }
        }
    }

    private int printGameMenu() {
        System.out.println(appResource.getString("menu.property.title"));
        System.out.println();
        System.out.println(appResource.getString("menu.property.play"));
        System.out.println(appResource.getString("menu.property.list"));
        System.out.println(appResource.getString("menu.property.search"));
        System.out.println(appResource.getString("menu.property.statistics"));
        System.out.println(appResource.getString("menu.property.print"));
        System.out.println(appResource.getString("menu.property.exit"));
        int temp = scanner.nextInt();
        scanner.nextLine();
        return temp;
    }

    private boolean play() {
        System.out.println(appResource.getString("game.think"));
        System.out.println(appResource.getString("game.enter"));
        scanner.nextLine();
        Node current = tree.root;
        while (true) {
            String temp = current.value;
            if (current.isQuestion()) askQuestion.accept(temp);
            else System.out.println(isItAnimal.apply(temp));
            if (getAnswer()) {
                if (current.yes != null) current = current.yes;
                else {
                    System.out.println(appResource.getString("game.again"));
                    return getAnswer();
                }
            } else {
                if (current.no != null) current = current.no;
                else {
                    System.out.println(appResource.getString("game.giveUp"));
                    String userAnimal = addAnimal.apply(scanner.nextLine());
                    System.out.printf((appResource.getString("statement.prompt")) + "%n", addAnimal.apply(temp), addAnimal.apply(userAnimal));
                    String fact = scanner.nextLine();
                    Matcher matcher = pattern.matcher(fact);
                    while (!matcher.matches()) {
                        System.out.printf((appResource.getString("statement.prompt")) + "%n", addAnimal.apply(temp), addAnimal.apply(userAnimal));
                        fact = scanner.nextLine();
                        matcher = pattern.matcher(fact);
                    }

                    System.out.println(appResource.getString("game.isCorrect") + userAnimal + "?");
                    if (getAnswer()) {
                        current.value = fact;
                        tree.insertRec(current, userAnimal, current.facts + processFacts.apply(matcher, true));
                        tree.insertRec(current, temp, current.facts + processFacts.apply(matcher, false));
                        System.out.printf((printFacts.apply(matcher.group(1), true)) + "%n", getAnimal(userAnimal), matcher.group(2), getAnimal(temp), matcher.group(2), matcher.group(2));
                    } else {
                        current.value = fact;
                        tree.insertRec(current, temp, current.facts + processFacts.apply(matcher, true));
                        tree.insertRec(current, userAnimal, current.facts + processFacts.apply(matcher, false));
                        System.out.printf((printFacts.apply(matcher.group(1), false)) + "%n", getAnimal(userAnimal), matcher.group(2), getAnimal(temp), matcher.group(2), matcher.group(2));
                    }
                    System.out.println(appResource.getString("game.again"));
                    return getAnswer();
                }
            }
        }
    }

    private boolean getAnswer() {
        String answer = scanner.nextLine().toLowerCase().trim();
        if (answer.matches(".+[!.]")) answer = answer.substring(0, answer.length() - 1);
        while (!positiveResponse.contains(answer) && !negativeResponse.contains(answer)) {
            System.out.println(clarificationQuestion[random.nextInt(farewellResponse.length)]);
            answer = scanner.nextLine();
        }
        return positiveResponse.contains(answer);
    }

    private void greetUser() {
        if (!LocalTime.now().isBefore(LocalTime.of(5, 0))
                && LocalTime.now().isBefore(LocalTime.of(12, 0))) {
            System.out.println(appResource.getString("greeting.morning"));
        } else if (!LocalTime.now().isBefore(LocalTime.of(12, 0))
                && LocalTime.now().isBefore(LocalTime.of(18, 0))) {
            System.out.println(appResource.getString("greeting.afternoon"));
        } else System.out.println(appResource.getString("greeting.evening"));
        System.out.println(appResource.getString("welcome"));
    }

    private String getAnimal(String s) {
        if (s.startsWith("a ")) return s.substring(2);
        if (s.startsWith("an ")) return s.substring(3);
        if (s.startsWith("the ")) return s.substring(4);
        else return s;
    }

    private void printList(List<String> list) {
        Collections.sort(list);
        for (String s : list) {
            System.out.println(" - " + s);
        }
    }
}