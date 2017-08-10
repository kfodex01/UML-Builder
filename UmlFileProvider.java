package umlbuilder;


/**
 * Creates a text file with the generated Uml
 *
 * @author Kevin Fode
 * @version (1.0
 */

import java.util.HashSet;
import java.util.Iterator;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.BufferedReader;

public class UmlFileProvider
{
    
    //variables
    private Node startingNode;
    private EditMode thisEditMode;
    private String path = "uml.txt";
    private boolean append_to_file = false;
    
    /**
     * Saves the UML generated to a file.
     * @param thisEditMode The program's edit mode.
     */
    public void saveFile(EditMode thisEditMode)
    {
        
        this.thisEditMode = thisEditMode;
        this.startingNode = thisEditMode.getStartNode();
        if (startingNode == null)
        {
            System.out.println("No UML detected for saving.");
            return;
        }
        System.out.println("Generating a list of nodes...");
        Node[] nodeList = this.generateNodeList();
        System.out.println("Found " + nodeList.length + " nodes.");
        System.out.print("Mapping nodes...");
        HashMap<Node, Integer> nodeMap = this.convertNodeListToMap(nodeList);
        System.out.println("done.");
        System.out.println("Writing to file.");
        try
        {
            this.saveToFile(nodeList, nodeMap);
        }
        catch (IOException e)
        {
            System.out.println("Error.");
        }
        
    }
    
    /**
     * Generates a list of nodes
     * @return an array of all the nodes.
     */
    private Node[] generateNodeList()
    {
        
        //variables
        HashSet<Node> nodeHashSet = new HashSet();
        
        this.followNodePath(startingNode, nodeHashSet);
        Node[] nodeList = new Node[nodeHashSet.size()];
        Iterator iterator = nodeHashSet.iterator();
        for(int i = 0; i < nodeList.length; i++)
        {
            nodeList[i] = (Node)iterator.next();
        }
        return nodeList;
        
    }
    
    /**
     * Follows each node path, copying each unique node.
     */
    private void followNodePath(Node currentNode, HashSet nodeHashSet)
    {
        
        if(nodeHashSet.add(currentNode))
        {
            String[] conditions = currentNode.getConditions();
            for(String thisCondition : conditions)
            {
                this.followNodePath(currentNode.getNextNode(thisCondition), nodeHashSet);
            }
        }
        
    }
    
    /**
     * Creates a HashMap from a Node[].
     * @param nodeList A list of nodes.
     * @return a hashmap of all nodes, using the node as a key and an Integer as the value.
     */
    private HashMap<Node, Integer> convertNodeListToMap(Node[] nodeList)
    {
        
        //variables
        HashMap<Node, Integer> thisMap = new HashMap();
        int i = 1;
        
        for(Node thisNode : nodeList)
        {
            thisMap.put(thisNode, i);
            i++;
        }
        
        return thisMap;
        
    }
    
    /**
     * Creates a UML save file.
     * @param nodeList A list of nodes.
     * @param nodeMap A HashMap of nodes.
     * @return true if saved, false if not.
     */
    private boolean saveToFile(Node[] nodeList,
    HashMap<Node, Integer> nodeMap) throws IOException
    {
        
        FileWriter write = new FileWriter(path, append_to_file);
        PrintWriter printLine = new PrintWriter(write);
        String textLine = "" + nodeMap.get(startingNode);
        printLine.printf("%s" + "%n", textLine);
        textLine = "";
        Node[] linkedNodes = thisEditMode.getLinkedNodes();
        for(Node thisNode : linkedNodes)
        {
            textLine = textLine.concat("" + nodeMap.get(thisNode) + " ");
        }
        printLine.printf("%s" + "%n", textLine);
        
        for(Node thisNode : nodeList)
        {
            textLine = "" + (int)(nodeMap.get(thisNode)) + " " + thisNode.getText();
            printLine.printf("%s" + "%n", textLine);
            for(String condition : thisNode.getConditions())
            {
                textLine = "*" + condition;
                printLine.printf("%s" + "%n", textLine);
                textLine = "*" + nodeMap.get(thisNode.getNextNode(condition));
                printLine.printf("%s" + "%n", textLine);
            }
            
        }
        printLine.close();
        
        return false;
        
    }
    
    /**
     * Loads a file into memory.
     */
    public void loadFile(EditMode thisEditMode)
    {
        
        // gets contents of file
        System.out.println("Loading UML...");
        ArrayList<String> fileLines = new ArrayList<>();
        try
        {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String s;
            while((s = br.readLine()) != null)
            {
                fileLines.add(s);
            }
            fr.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("No UML file found.");
            return;
        }
        catch(IOException e)
        {
            System.out.println("Error.");
            return;
        }
        
        // verifies file wasn't empty
        if(fileLines.isEmpty())
        {
            return;
        }
        
        // converting ArrayList into array
        String[] linesArray = new String[fileLines.size()];
        linesArray = fileLines.toArray(linesArray);
        
        /**
         * Important Note:
         * linesArray[0] contains the number of the starting node
         * linesArray[1] contains a list of linked nodes, seperated by spaces
         * each following line may contain the following:
         * if the line starts with a number, it is the number of the node followed
         * by a space, and the node text.
         * if the line starts with a *, it will be condition text followed by
         * another line which starts with a *, followed by the number of the node
         * the condition links to.
         */
        // figure out number of nodes
        int numberOfNodes = 0;
        for(int i = 2; i < linesArray.length; i++)
        {
            if(linesArray[i].length() > 0 &&
            !linesArray[i].substring(0,1).equals("*"))
            {
                numberOfNodes++;
            }
        }
        
        // create an array of nodes
        System.out.println("Creating Nodes...");
        Node[] nodeList = new Node[numberOfNodes];
        int currentNode = 0;
        for(int i = 2; i < linesArray.length; i++)
        {
            if(linesArray[i].length() > 0 &&
            !linesArray[i].substring(0,1).equals("*"))
            {
                int endOfNumber = linesArray[i].indexOf(" ");
                nodeList[currentNode] =
                new Node(linesArray[i].substring(endOfNumber + 1));
                currentNode++;
            }
        }
        
        // set the starting node
        System.out.println("Setting starting node...");
        int startingNode = Integer.parseInt(linesArray[0]);
        // counting for the number from linesArray starts at 1
        startingNode--;
        thisEditMode.setStartNode(nodeList[startingNode]);
        
        // set the list of linked nodes
        System.out.println("Setting linked nodes...");
        if(!linesArray[1].equals(" "))
        {
            ArrayList<Node> linkedNodes = new ArrayList<>();
            int numberStart = 0;
            for(int i = 0; i < linesArray[1].length(); i++)
            {
                if(linesArray[1].substring(i, i + 1).equals(" "))
                {
                    int thisNumber = Integer.parseInt(linesArray[1]
                    .substring(numberStart, i));
                    thisNumber--;
                    linkedNodes.add(nodeList[thisNumber]);
                    numberStart = i + 1;
                }
            }
            Node[] linkedNodesArray = new Node[linkedNodes.size()];
            linkedNodesArray = linkedNodes.toArray(linkedNodesArray);
            thisEditMode.setLinkedNodes(linkedNodesArray);
        }
        
        // linking nodes
        System.out.println("Linking nodes...");
        currentNode = -1;
        for(int i = 2; i < linesArray.length; i++)
        {
            if(linesArray[i].length() > 0 &&
            !linesArray[i].substring(0,1).equals("*"))
            {
                currentNode++;
            }
            else
            {
                String condition = linesArray[i].substring(1);
                i++;
                int nodeNumber = Integer.parseInt(linesArray[i].substring(1));
                nodeNumber--;
                nodeList[currentNode].setNextNode(condition, nodeList[nodeNumber]);
            }
        }
        System.out.println("UML loaded.");
        
    }
    
}