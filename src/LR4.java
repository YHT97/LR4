import java.util.*;

class TreeMain {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Node<String> root = new Node<>("root", 0);
        Node<String> temp = new Node<>("temp", -1);
        Node<String> parent = null;
        int index = 0;
        boolean flag = false;
        String gap = "    ";
        System.out.println("""
                1 - Generate tree
                2 - Output all tree
                3 - Search for an input element
                0 - Exit
                """);

        while (!flag) {
            System.out.print("Enter the number of operation: ");
            int OperationNumber = scan.nextInt();
            switch (OperationNumber) {
                case 1 -> {
                    System.out.print(gap + "Enter the tree length: ");
                    int length = scan.nextInt();
                    for (int i = 0; i < length; i++) {
                        parent = root.addChild(new Node<>(getString(5), index++));
                        for (int j = 0; j < length; j++) {
                            Node<String> leaf = parent.addChild(new Node<>(getString(5), index++));
                        }
                    }
                }
                case 2 -> {
                    System.out.println();
                    printTree(root, "-");
                    System.out.println();
                }
                /*Need refactor*/
                case 3 -> {
                    System.out.print(gap + "Enter the remove data index: ");
                    int newIndex = scan.nextInt();
                    root.Tree(root, temp, newIndex);
                    root.remove(temp);
                }
                case 0 -> flag = true;
            }
        }

    }

    public static String getString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder Sbuffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(52);
            Sbuffer.append(str.charAt(number));
        }
        return Sbuffer.toString();
    }

    private static <T> void printTree(Node<T> node, String appender) {
        System.out.println(appender + node.getData() + " (" + node.index + ")");
        node.getChildren().forEach(each ->  printTree(each, appender + appender));
    }

    public static class Node<T> {

        private  int index = 0;

        private T data = null;

        private final List<Node<T>> children = new ArrayList<>();

        private Node<T> parent = null;

        public Node(T data, int index) {
            this.data = data;
            this.index = index;
        }

        public Node<T> addChild(Node<T> child) {
            child.setParent(this);
            child.index++;
            this.children.add(child);
            return child;
        }

        private void remove(Node<T> child){//add index control
            if(child.children.size()>0) {
                Node<T> root = child.getChildren().get(child.getChildren().size()-1);
                root.setParent(child.getParent());
                child.getChildren().remove(child.getChildren().size() - 1);
                root.children.addAll(child.getChildren());
                for (int i = 0; i < child.getParent().getChildren().size(); i++) {
                    if (child.getParent().getChildren().get(i).index == child.index) {
                        child.getParent().getChildren().set(i, root);
                    }
                }
            }else{
                for (int i = 0; i < child.getParent().getChildren().size(); i++) {
                    if (child.getParent().getChildren().get(i).index == child.index) {
                        child.getParent().getChildren().remove(i);
                    }
                }
            }
        }

        private void Tree(Node<T> node, Node<T> tmp, int index){
            if(node.index==index){
                //tmp=node;
                tmp.setIndex(node.getIndex());
                tmp.setData(node.getData());
                tmp.setParent(node.getParent());
                tmp.addChildren(node.getChildren());
            }
            Node<T> finalTmp = tmp;
            node.getChildren().forEach(each->Tree(each, finalTmp,index));

        }

        public void addChildren(List<Node<T>> children) {
            children.forEach(each -> each.setParent(this));
            this.children.addAll(children);
        }

        public List<Node<T>> getChildren() {
            return children;
        }

        public T getData() {
            return data;
        }

        public int getIndex() {
            return index;
        }

        public void setData(T data) {
            this.data = data;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        private void setParent(Node<T> parent) {
            this.parent = parent;
        }

        public Node<T> getParent() {
            return parent;
        }

    }
}