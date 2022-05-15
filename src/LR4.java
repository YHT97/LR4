import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LR4 {
    public static void main(String[] args) {
        Node<Integer> tree = new Node<>((int) (Math.random() * 100) -100);
        for (int i = 0;i<3;i++){
            tree.addChild(new Node<>((int) (Math.random() * 100) -100));
        }
        for(Node<Integer> child : tree.getChildren()){
            for(int i =0;i<3;i++){
                child.addChild(new Node<>((int) (Math.random() * 100) -100));
            }
        }
        printTree(tree,"*");
        tree.remove(tree.getChildren().get(0).getChildren().get(0));
        System.out.println("-----------------------------------------------------------------");
        printTree(tree,"*");



    }
    private static <T> void printTree(Node<T> node, String appender) {
        System.out.println(appender + node.getData());
        node.getChildren().forEach(each -> printTree(each, appender + appender));
    }

    public static class Node<T> {

        private T data = null;

        private final List<Node<T>> children = new ArrayList<>();

        private Node<T> parent = null;

        public Node(T data) {
            this.data = data;
        }

        public void addChild(Node<T> child) {
            child.setParent(this);
            this.children.add(child);
        }

        private void addChildren(List<Node<T>> children) {
            children.forEach(each -> each.setParent(this));
            this.children.addAll(children);
        }

        private void remove(Node<T> child){
            if(child.children.size()>0) {
            Node<T> root = child.getChildren().get(child.getChildren().size()-1);
            root.setParent(child.getParent());
                child.getChildren().remove(child.getChildren().size() - 1);
                root.children.addAll(child.getChildren());
                for (int i = 0; i < child.getParent().getChildren().size(); i++) {
                    if (child.getParent().getChildren().get(i) == child) {
                        child.getParent().getChildren().set(i, root);
                    }
                }
            }else{
                for (int i = 0; i < child.getParent().getChildren().size(); i++) {
                    if (child.getParent().getChildren().get(i) == child) {
                        child.getParent().getChildren().remove(i);
                    }
                }
            }
        }

        private List<Node<T>> getChildren() {
            return children;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        private void setParent(Node<T> parent) {
            this.parent = parent;
        }

        public Node<T> getParent() {
            return parent;
        }

    }
















}