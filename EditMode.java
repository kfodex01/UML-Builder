/**
 * The EditMode class contains all the functionality for editing UML diagrams.
 *
 * @author Kevin Fode
 * @version 1.0
 */

 package umlbuilder;
 
 //imports
 import java.util.Scanner;
 import java.util.HashSet;
 import java.util.Iterator;

public class EditMode
{
    
    // variables
    private Node startNode;
    private Node activeNode;
    private HashSet<Node> linkNodes = new HashSet();
    private String[] editMenuOptions = {"Edit the Current Node","Go to Next Node",
        "Add a Next Node", "Add a Linked Next Node", "Edit a Condition",
        "Make Node Linkable", "Delete a Next Node", "Return to Start", "Exit Edit Mode"};
    private final static int EDIT = 1;
    private final static int NEXT = 2;
    private final static int ADD = 3;
    private final static int ADD_LINKED = 4;
    private final static int CONDITION = 5;
    private final static int LINK = 6;
    private final static int DELETE = 7;
    private final static int START = 8;
    private final static int EXIT = 9;
    
    /**
     * Starts the Edit mode.
     * 
     */
    public void execute()
    {

        System.out.println("Welcome to Edit Mode");
        
        // checks to see if any UML is loaded.
        if (startNode == null)
        {
            System.out.println("No UML code detected. Would you like to start a new one?");
            int choice = new Menu(Menu.YES_NO).provideMenu();
            switch(choice)
            {
                case Menu.YES:
                this.newUml();
                break;
                
                case Menu.NO:
                return;
            }
        }
        
        //starts the loop for the Edit mode
        this.executeEditLoop();
        
    }
    
    /**
     * Sets up for creating a new UML
     */
    private void newUml()
    {
        
        startNode = new Node();
        activeNode = startNode;
        
    }
    
    /**
     * Engages the edit loop.
     */
    private void executeEditLoop()
    {
        
        boolean loop = true;
        while(loop)
        {
            System.out.println("Current Step: " + activeNode.getText());
            if(activeNode.getNumberOfNextNodes() > 1)
            {
                System.out.println();
                System.out.println("Conditions for next nodes:");
                System.out.println();
                String[] conditions = activeNode.getConditions();
                for(String thisValue : conditions)
                {
                    System.out.println("   " + thisValue);
                }
            }
            System.out.println();
            System.out.println("What would you like to do?");
            int choice = new Menu(editMenuOptions).provideMenu();
            switch(choice)
            {
                case EXIT:
                loop = false;
                break;
                
                case EDIT:
                this.editNodeText();
                break;
                
                case NEXT:
                this.moveToNextNode();
                break;
                
                case START:
                activeNode = startNode;
                break;
                
                case ADD:
                this.addNextNode();
                break;
                
                case CONDITION:
                this.editCondition();
                break;
                
                case LINK:
                this.makeLinkable();
                break;
                
                case ADD_LINKED:
                this.addLinkedNode();
                break;
                
                case DELETE:
                this.deleteNode();
                break;
            }
            
        }
        
    }
    
    /**
     * Allows the user to edit the current node's text.
     */
    private void editNodeText()
    {
        
        String newText = this.askQuestion("Please enter new text for this node (Leave blank to cancel):");
        if(!newText.equals(""))
        {
            activeNode.setText(newText);
        }
        
    }
    
    /**
     * Allows the user to navigate to the next node, or create one if one doesn't exist.
     * 
     */
    private void moveToNextNode()
    {
        
        // check to see if the current node has a next node
        if(!activeNode.hasNextNodes())
        {
            System.out.println("This node doesn't have a following node. Would you like to create one?");
            int choice = new Menu(Menu.YES_NO).provideMenu();
            switch(choice)
            {
                case Menu.YES:
                addNextNode();
                break;
                
                case Menu.NO:
                return;
            }

        }
        
        // moves to the next node
        activeNode = activeNode.getNextNode();
        
    }
    
    /**
     * Allows the user to add next nodes
     */
    private void addNextNode(Node newNode)
    {
        
        // for empty nextNodes
        if(!activeNode.hasNextNodes())
        {
            activeNode.setNextNode(newNode);
            return;
        }
        
        String answer = this.askQuestion("What condition will lead to this node?");
        
        // handles duplicate conditions
        if(!activeNode.setNextNode(answer, newNode))
        {
            System.out.println("Duplicate condition encountered. New node discarded.");
        }
        
    }
    
    /**
     * Allows the user to add next nodes
     */
    
    private void addNextNode()
    {
        
        // variables
        Node newNode = new Node();
        this.addNextNode(newNode);
        
    }
    
    /**
     * Takes input.
     * @param question The question that needs to be answered.
     * @return The answer to the question.
     */
    private String askQuestion(String question)
    {
        
        // variables
        String answer = "";
        Scanner reader = new Scanner(System.in);
        
        System.out.println(question);
        answer = reader.nextLine();
        reader.close();
        return answer;
        
    }
    
    /**
     * Allows the user to edit conditions leading to other nodes.
     */
    private void editCondition()
    {
        
        // Handles nodes with not enough next nodes to have conditions
        if(activeNode.getNumberOfNextNodes() < 2)
        {
            System.out.println("No conditions available for editing.");
            return;
        }
        
        System.out.println("Which condition would you like to edit?");
        String[] conditions = activeNode.getConditions();
        int choice = new Menu(conditions).addToEnd("Cancel Edit").provideMenu();
        
        // for leaving edit mode
        if (choice == conditions.length + 1)
        {
            System.out.println("Edit aborted.");
            return;
        }
        
        String newCondition="";
        while(newCondition.equals(""))
        {
            newCondition = askQuestion("What will the new condition be?");
        }
        Node deletedNode = activeNode.deleteNextNode(conditions[choice - 1]);
        if(deletedNode == null)
        {
            System.out.println("Node not found. Edit aborted.");
            return;
        }
        boolean hasAdded = activeNode.setNextNode(newCondition, deletedNode);
        if (hasAdded)
        {
            System.out.println("Condition changed.");
        } else {
            System.out.println("Error. Node potentially lost.");
        }
    }
    
    /**
     * Getter for startNode
     * @return Starting node.
     */
    public Node getStartNode()
    {
        
        return startNode;
        
    }
    
    /**
     * Setter for startNode
     * @param newStartNode The new starting node.
     */
    public void setStartNode(Node newStartNode)
    {
        
        startNode = newStartNode;
        activeNode = startNode;
        
    }
    
    /**
     * Makes a node linkable.
     */
    private void makeLinkable()
    {
        
        // checks to see if node is already linkable.
        if(linkNodes.contains(activeNode))
        {
            System.out.println("This node is already tagged as linkable.");
            return;
        }
        
        linkNodes.add(activeNode);
        System.out.println("Node made linkable.");
        
    }
    
    /**
     * Adds a previously linked node as a next node.
     */
    private void addLinkedNode()
    {
        
        // checks for an empty set.
        if(linkNodes.isEmpty())
        {
            System.out.println("No nodes marked as linkable.");
            return;
        }
        
        
        // creating data for menu
        String[] nodeText = new String[linkNodes.size()];
        Node[] nodes = new Node[linkNodes.size()];
        Iterator<Node> iterator = linkNodes.iterator();
        int i = 0;
        while(iterator.hasNext())
        {
            nodes[i] = iterator.next();
            nodeText[i] = nodes[i].getText();
            i++;
        }
        int choice = new Menu(nodeText).addToEnd("Don't add linked node").provideMenu();
        
        // for aborting add
        if(choice == nodes.length + 1)
        {
            System.out.println("Add aborted.");
            return;
        }
        
        this.addNextNode(nodes[choice - 1]);
        
    }
    
    /**
     * Used to delete a next node.
     */
    private void deleteNode()
    {
        
        if (activeNode.selectNodeToDelete() == null)
        {
            System.out.println("Deletion aborted.");
        }
        
    }
    
    /**
     * Getter for linked nodes list.
     * @return a list of linked nodes.
     */
    public Node[] getLinkedNodes()
    {
        
        Node[] nodesArray = new Node[linkNodes.size()];
        Iterator<Node> iterator = linkNodes.iterator();
        int i = 0;
        while(iterator.hasNext())
        {
            nodesArray[i] = iterator.next();
            i++;
        }
        
        return nodesArray;
        
    }
    
    /**
     * setter for linked nodes list.
     * @param linkedNodes a list of linked nodes.
     */
    public void setLinkedNodes(Node[] linkedNodes)
    {
        
        for(Node thisNode : linkedNodes)
        {
            linkNodes.add(thisNode);
        }
        
    }
    
}
