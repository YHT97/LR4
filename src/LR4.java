import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class LR4 {
    private static int[] getArray() {
        int[] array = new int[3];
        for (int i = 0; i < 3; i++) {
            array[i] = (int) (Math.random() * 200) - 100;
        }
        return array;
    }

    private static String RandString() {
        String alp = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = new Random();
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            build.append(alp.charAt(rand.nextInt(alp.length())));
        }
        return build.toString();
    }

    public static void main(String[] args) {

        TreeNode<Data> tree = new TreeNode<>();
        tree.addChild(new Data());

    }

    public static class Data {
        public int[] index;
        public int data;
        public String str;

        public Data() {
            this.index = getArray();
            this.data = (int) (Math.random() * 200 - 100);
            this.str = RandString();
        }
    }

    public static class TreeNode<T> implements Iterable<TreeNode<T>> {

        private final List<TreeNode<T>> elementsIndex;
        public T data;
        public TreeNode<T> parent;
        public List<TreeNode<T>> children;

        public TreeNode(/*T data*/) {
            //this.data = data;
            this.children = new LinkedList<>();
            this.elementsIndex = new LinkedList<>();
            this.elementsIndex.add(this);
        }

        public boolean isRoot() {
            return parent == null;
        }

        public boolean isLeaf() {
            return children.size() == 0;
        }

        public void addChild(T child) {
            TreeNode<T> childNode = new TreeNode<>();
            childNode.parent = this;
            this.children.add(childNode);
            this.registerChildForSearch(childNode);
        }

        public int getLevel() {
            if (this.isRoot())
                return 0;
            else
                return parent.getLevel() + 1;
        }

        private void registerChildForSearch(TreeNode<T> node) {
            elementsIndex.add(node);
            if (parent != null)
                parent.registerChildForSearch(node);
        }

        public TreeNode<T> findTreeNode(Comparable<T> cmp) {
            for (TreeNode<T> element : this.elementsIndex) {
                T elData = element.data;
                if (cmp.compareTo(elData) == 0)
                    return element;
            }

            return null;
        }

        @Override
        public String toString() {
            return data != null ? data.toString() : "[data null]";
        }

        public Iterator<TreeNode<T>> iterator() {
            return new TreeNodeIter<>(this);
        }
    }

    public static class TreeNodeIter<T> implements Iterator<TreeNode<T>> {

        private final TreeNode<T> treeNode;
        private final Iterator<TreeNode<T>> childrenCurNodeIter;
        private ProcessStages doNext;
        private TreeNode<T> next;
        private Iterator<TreeNode<T>> childrenSubNodeIter;
        public TreeNodeIter(TreeNode<T> treeNode) {
            this.treeNode = treeNode;
            this.doNext = ProcessStages.ProcessParent;
            this.childrenCurNodeIter = treeNode.children.iterator();
        }

        @Override
        public boolean hasNext() {

            if (this.doNext == ProcessStages.ProcessParent) {
                this.next = this.treeNode;
                this.doNext = ProcessStages.ProcessChildCurNode;
                return true;
            }

            if (this.doNext == ProcessStages.ProcessChildCurNode) {
                if (childrenCurNodeIter.hasNext()) {
                    TreeNode<T> childDirect = childrenCurNodeIter.next();
                    childrenSubNodeIter = childDirect.iterator();
                    this.doNext = ProcessStages.ProcessChildSubNode;
                    return hasNext();
                } else {
                    this.doNext = null;
                    return false;
                }
            }

            if (this.doNext == ProcessStages.ProcessChildSubNode) {
                if (childrenSubNodeIter.hasNext()) {
                    this.next = childrenSubNodeIter.next();
                    return true;
                } else {
                    this.next = null;
                    this.doNext = ProcessStages.ProcessChildCurNode;
                    return hasNext();
                }
            }

            return false;
        }

        @Override
        public TreeNode<T> next() {
            return this.next;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        enum ProcessStages {
            ProcessParent, ProcessChildCurNode, ProcessChildSubNode
        }

    }

}
