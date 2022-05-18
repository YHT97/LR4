import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class LR4 {
    public static void main(String[] args) {
        Node<Integer> Tmp = new Node<>(0,-1);
        Node<Integer> tree = new Node<>((int) (Math.random() * 100) -100,0);
        int index=0;
        for (int i = 0;i<3;i++){
            tree.addChild(new Node<>((int) (Math.random() * 100) -100,++index));

        }
        for(Node<Integer> child : tree.getChildren()){
            for(int i =0;i<3;i++){
                child.addChild(new Node<>((int) (Math.random() * 100) -100,++index));
            }
        }
        printTree(tree,"*");
        System.out.println("-----------------------------------------------------------------");
        printTree(tree,"*");
        tree.Tree(tree,Tmp,1);
        System.out.println(Tmp);


    }

    private static <T> void printTree(Node<T> node, String appender) {
        System.out.println(appender + node.data + appender + node.index);
        node.getChildren().forEach(each -> printTree(each, appender + appender));
    }

    public static class Node<T> {
        private  int index = 0;
        private T data = null;

        private final List<Node<T>> children = new ArrayList<>();

        private Node<T> parent = null;

        public Node(T data,int index) {
            this.data = data;
            this.index = index;
        }

        public void addChild(Node<T> child) {
            //child.index=++index;
            child.setParent(this);
            this.children.add(child);
        }

        private void addChildren(List<Node<T>> children) {
            children.forEach(each -> each.setParent(this));
            this.children.addAll(children);
        }

        private void Tree(Node<T> node,Node<T> tmp,int index){
            if(node.index==index){
                tmp=node;
            }
            Node<T> finalTmp = tmp;
            node.getChildren().forEach(each -> Tree(each, finalTmp,index));

        }


        private void remove(Node<T> child){//add index control
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