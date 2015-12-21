import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SpellCheck {
    public static class HashNode{
        private int key;
        private String word;
        private int cnt;
        private HashNode next;
        public HashNode(int key, String word){
            this.key= key;
            this.word= word;
            this.cnt=0;
            this.next=null;
        }
        public int getCnt(){
            return this.cnt;
        }
        public int getKey(){
            return this.key;
        }
        public String getWord(){
            return this.word;
        }
        public HashNode getNext(){
            return next;
        }
        public void setNext(HashNode next){
            this.next=next;
        }
        public void setCnt(){
            this.cnt++;
        }
    };
    public static class DoubleLinkedList {
        public Node head;
        public static class Node{
            private String word;
            private int cnt;
            public Node pre;
            public Node next;
            public Node(){
                word=null;
                cnt=0;
                pre=null;
                next=null;
            }
            public String getWord(){
                return this.word;
            }
            public int getCnt(){
                return this.cnt;
            }
            public void setWord(String w){
                this.word=w;
            }
            public void setCnt(int c){
                this.cnt=c;
            }
        }
        public DoubleLinkedList(){
            head=null;
        }
        public void add(String w, int c){
            if(head==null){
                head = new Node();
                head.setCnt(c);
                head.setWord(w);
                return;
            }
            Node tmp=new Node();
            tmp.setCnt(c);
            tmp.setWord(w);
            Node p;
            Node q=new Node();
            for(p=head;p!=null;p=p.next){
                q=p;
                if(p.getCnt()<c) break;
            };
            if(p!=null){
                if(p!=head){
                    p.pre.next=tmp;
                }else{ head= tmp;}
                tmp.pre=p.pre;
                tmp.next=p.next;
                if(p.next!=null)p.next.pre=tmp;
            }
            else{ q.next=tmp;
                tmp.pre=q;
            }
        }
        public void display(){
            int count=0;
            if(head==null) return;
            for(Node p=head;p!=null;p=p.next){
                if(count<100)
                    System.out.println(p.getWord()+":"+p.getCnt());
                else break;
                count++;
            }
        }
    };
    public static class HashLinearChaining{
        private HashNode[] table;
        private int size;
        private int count;
        public DoubleLinkedList top100;
        public HashLinearChaining(int size){
            table= new HashNode[size];
            for(int i=0;i<size;i++)
                table[i]=null;
            this.size=size;
            this.count=0;
            top100=new DoubleLinkedList();
        }
        public int hash(String s){
            int sum=s.length();
            for(int i=0;i<s.length();i++)
                sum=sum+s.charAt(i)^sum>>13;
            return sum;
        }
        public void add(int key, String word) {
            int hash = (key % size);
            if (table[hash] == null){
                table[hash] = new HashNode(key, word);
                table[hash].setCnt();
            }
            else {
                HashNode node = table[hash];
                while (node.getNext() != null){
                    if (node.getWord().equals(word)){
                        node.setCnt();
                        return;
                    }
                    node = node.getNext();
                }
                node.setNext(new HashNode(key, word));
                node.getNext().setCnt();
            }
        }
        public boolean search(String word){
            word=word.replaceAll("'", "");
            int code= this.hash(word);
            code=(code%size);
            if(table[code]==null) return false;
            else{ HashNode node=table[code];
                while (node.getNext() != null){
                    if (node.getWord().equals(word)){
                        return true;
                    }
                    node = node.getNext();
                }
            }
            return false;
        }
        public void displayAll(){
            for (int i = 0; i < size; i++){
                if (table[i] != null) {
                    HashNode now=table[i];
                    while (now!=null){
                        count++;
                        //System.out.println(now.getWord());
                        now=now.getNext();
                    }
                }
            }
            System.out.println("Total number:"+count);
        }
        public void displayTop100(){
            for (int i = 0; i < size; i++){
                if (table[i] != null) {
                    HashNode now=table[i];
                    while (now!=null){
                        this.top100.add(now.getWord(), now.getCnt());
                        now=now.getNext();
                    }
                }
            }
            top100.display();
        }
    };
    public static void main(String[] args) throws IOException {

        HashLinearChaining dict=new HashLinearChaining(997);
        String word = null;
        int key;
        LineNumberReader reader = null;
        long begintime = System.currentTimeMillis();
        System.out.println("Loading Dictionary");
        try {
            reader = new LineNumberReader(new FileReader("src/wordsEn.txt"));

            while ((word=reader.readLine()) != null){
                key=dict.hash(word);
                dict.add(key, word);
            }
        } catch (Exception ex) {
            System.out.println(word);
            return;
        } finally {
            if(reader != null)
                reader.close();
        }
        long endtime = System.currentTimeMillis();
        long costTime = (endtime - begintime);
        System.out.println("Dictionary Loaded:"+costTime+"ms");
        Pattern pattern= Pattern.compile("[a-zA-Z0-9']");
        HashLinearChaining wordcount=new HashLinearChaining(997);
        HashLinearChaining misspelled= new HashLinearChaining(997);
        try {
            reader = new LineNumberReader(new FileReader("src/CompletWorksofShakespeare.txt"));
            while ((word=reader.readLine()) != null){
                String[] words = word.split("\\s+");
                for (String s : words) {
                    String temp="";
                    Matcher matcher = pattern.matcher(s);
                    while (matcher.find()) temp+=matcher.group().toLowerCase().replace("'", "");
                    if(!temp.equals("")){
                        int code = wordcount.hash(temp);
                        wordcount.add(code, temp);
                        if(!dict.search(temp)){
                            code= misspelled.hash(temp);
                            misspelled.add(code, temp);
                        }
                    }
                }

            }

        } catch (Exception ex) {
            System.out.println();
            return;
        } finally {
            if(reader != null)
                reader.close();
        }
        System.out.println("Misspelled words:");
        misspelled.displayAll();
        System.out.println("Top100 words:");
        wordcount.displayTop100();
    }

}
