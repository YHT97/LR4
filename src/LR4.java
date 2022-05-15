import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LR4 {
    public static void main(String[] args) {
        Node<Data> tree = new Node<>(new Data());
        for (int i = 0;i<3;i++){
            tree.addChild(new Node<>(new Data()));
        }
        for(Node<Data> child : tree.getChildren()){
            for(int i =0;i<3;i++){
                child.addChild(new Node<>(new Data()));
            }
        }
        printTree(tree,"  ");
        tree.remove(tree.getChildren().get(0));
        System.out.println("-----------------------------------------------------------------");
        printTree(tree,"  ");

    }
    private static <T> void printTree(Node<T> node, String appender) {
        System.out.println(appender + node.getData());
        node.getChildren().forEach(each -> printTree(each, appender + appender));
    }
    static class Data {
        public int[] index;
        public int data;
        public String str;

        public Data() {
            this.index = getArray();
            this.data = (int)(Math.random()*200-100);
            this.str = RandString();
        }
    }

    static int[] getArray() {
        int[] array = new int[3];
        for (int i = 0; i < 3; i++) {
            array[i] = (int) (Math.random() * 200) - 100;
        }
        return array;
    }
    public static String RandString(){
        String alp = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = new Random();
        StringBuilder build = new StringBuilder();
        for(int i=0;i<10;i++){
            build.append(alp.charAt(rand.nextInt(alp.length())));
        }
        return build.toString();
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
            Node<T> root = child.getChildren().get(child.getChildren().size()-1);
            root.setParent(child.getParent());
            child.getChildren().remove(child.getChildren().size()-1);
            root.children.addAll(child.getChildren());
            for(int i=0;i<child.getParent().getChildren().size();i++){
                if(child.getParent().getChildren().get(i)==child){
                    child.getParent().getChildren().set(i,root);
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