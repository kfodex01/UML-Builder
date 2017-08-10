package umlbuilder;

// imports
import java.util.HashMap;
import java.util.Collections;
import java.util.Set;
import java.util.Iterator;

/**
 * The node class is where entered UML data is stored for later use
 *
 * @author Kevin Fode
 * @version 1.0
 */
public class Node
{
    
    // variables
    private String text;
    private HashMap<String, Node> nextNodes = new HashMap();
    
    /**
     * Default Constructor for objects of class Node. Text filled with default value.
     * 
     */
    public Node()
    {
        this("***Default Text***");
    }
    
    /**
     * Constructor for objects of class Node
     * 
     * @param text This is the text being stored. It is the instruction of this UML step.
     */
    public Node(String text)
    {
        
        this.setText(text);
        
    }
    
    /**
     * Setter method for text.
     * 
     * @param text This is the text being stored. It is the instruction of this UML step.
     */
    public void setText(String text)
    {
        
        this.text = text;
        
    }
    
    /**
     * Getter method for text.
     * 
     * @return The text being stored. It is the instruction of this UML step.
     */
    public String getText()
    {
        
        return text;
        
    }
    
    /**
     * Setter method for nextNode.
     * 
     * @param condition The condition which, when true, will lead to the next node.
     * @param nextNode This is the next node in the chain
     * @return True if Node was added, false if not.
     */
    public boolean setNextNode(String condition, Node nextNode)
    {
        
        if(nextNodes.containsKey(condition))
        {
            return false;
        }
        nextNodes.put(condition, nextNode);
        return true;
        
    }
    
    /**
     * Setter method for nextNode, when it is the first node entered.
     * 
     * @param nextNode This is the next node in the chain
     * @return True if Node was added, false if not.
     */
    public boolean setNextNode(Node nextNode)
    {
        
        return this.setNextNode("***Default Condition***", nextNode);
        
    }
    
    /**
     * Getter method for nextNode.
     * 
     * @return The next node in the chain.
     */
    public Node getNextNode()
    {
        
        // takes care of situations where their are 1 or less next nodes
        if(!this.hasNextNodes())
        {
            return null;
        } else if(nextNodes.size() == 1)
        {
            Object[] node = nextNodes.values().toArray();
            return (Node) node[0];
        }
        
        // building menu
        String[] conditions = getConditions();
        int choice = new Menu(conditions).addToEnd("Exit Node Change").provideMenu();
        
        // if user chooses to abort
        if(choice == conditions.length + 1)
        {
            System.out.println("Movement aborted.");
            return this;
        }
        return nextNodes.get(conditions[choice - 1]);
        
    }
    
    /**
     * Getter method for nextNode.
     * @param condition The condition of the next node.
     * @return The next node in the chain.
     */
    public Node getNextNode(String condition)
    {
        return nextNodes.get(condition);
    }
    
    /**
     * Determines if this node has nextNodes
     * @return true if Node has nextNodes, false if not
     */
    public boolean hasNextNodes()
    {
        return !nextNodes.isEmpty();
    }
    
    /**
     * Getter for conditions.
     * @return A String[] of conditions.
     */
    public String[] getConditions()
    {
        Object[] stupidObjects = nextNodes.keySet().toArray();
        String[] conditions = new String[stupidObjects.length];
        for(int i = 0; i < conditions.length; i++)
        {
            conditions[i] = (String) stupidObjects[i];
        }
        return conditions;
    }
    
    /**
     * Getter for number of next nodes.
     * @return An int of the number of next nodes.
     */
    public int getNumberOfNextNodes()
    {
        return nextNodes.size();
    }
    
    /**
     * For deleteing next nodes.
     * @param condition The condition of the node to be deleted.
     * @return The node that was deleted, or null if no node was deleted.
     */
    public Node deleteNextNode(String condition)
    {
        
        return nextNodes.remove(condition);
        
    }
    
    /**
     * For selecting node to delete.
     */
    public Node selectNodeToDelete()
    {
        
        // takes care of situations where their are 1 or less next nodes
        if(!this.hasNextNodes())
        {
            System.out.println("No next nodes found.");
            return null;
        } else if(nextNodes.size() == 1)
        {
            Object[] key = nextNodes.keySet().toArray();
            return this.deleteNextNode((String)key[0]);
        }
        
        // building menu
        String[] conditions = getConditions();
        int choice = new Menu(conditions).addToEnd("Exit Node Deletion").provideMenu();
        
        // if user chooses to abort
        if(choice == conditions.length + 1)
        {
            return null;
        }
        return this.deleteNextNode(conditions[choice - 1]);
        
    }
    
}
