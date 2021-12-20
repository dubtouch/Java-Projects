import java.util.ArrayList;
import java.util.List;

class BinarySearchTree {
    Node root;

    BinarySearchTree() {
        root = null;
    }

    void insertRec(Node current, String value, String facts) {
        if (root == null) {
            root = new Node(value, facts);
        } else if (current.yes == null) {
            current.yes = new Node(value, facts);
        } else {
            current.no = new Node(value, facts);
        }
    }

    int maxTreeHeight(Node current) {
        if (current == null) return - 1;
        return Math.max(maxTreeHeight(current.yes), maxTreeHeight(current.no)) + 1;
    }

    int minTreeHeight(Node current) {
        if (current == null) return - 1;
        return Math.min(minTreeHeight(current.yes), minTreeHeight(current.no)) + 1;
    }

    void printTree(Node current) {
        if (current == null) return;
        System.out.println(current.value);
        printTree(current.yes);
        printTree(current.no);
    }

    List<String> printAllAnimals(Node current) {
        ArrayList<String> list = new ArrayList<>();
        if (current.yes == null && current.no == null) {
            list.add(current.value.split(" ")[current.value.split(" ").length - 1]);
            return list;
        }
        list.addAll(printAllAnimals(current.yes));
        list.addAll(printAllAnimals(current.no));
        return list;
    }

    public List<Node> calculateStatistics(Node current) {
        List<Node> list = new ArrayList<>();
        if (current == null) return List.of();
        list.add(current);
        list.addAll(calculateStatistics(current.no));
        list.addAll(calculateStatistics(current.yes));
        return list;
    }

    public int totalNodesDepth(Node current, int depth) {
        if (current.yes == null && current.no == null) return depth;
        return totalNodesDepth(current.yes, depth + 1) + totalNodesDepth(current.no, depth + 1);
    }

    public Node searchAnimal(Node current, String animal) {
        if ( current == null || current.value.equals(animal)) return current;
        Node left = searchAnimal(current.no, animal);
        Node right = searchAnimal(current.yes, animal);
        return left == null ? right : left;
    }
}
