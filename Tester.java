
/**
 * This is the class is not apart of the program on completion. It merely tests the other
 * classes.
 *
 * @author Kevin Fode
 * @version 1.0
 */

package umlbuilder;

public class Tester
{
    
    public static void main(String[] args)
    {
        
        Tester thisTester = new Tester();
        thisTester.menuTester();
        thisTester.nodeTester();
        
    }
    
    /**
     * Tests the Menu class.
     * 
     */
    public void menuTester()
    {
        
        String[] choices = {"Choice 1", "Choice 2"};
        Menu thisMenu = new Menu(choices);
        thisMenu.provideMenu();
        
    }
    
    /**
     * Tests the Node class.
     * 
     */
    public void nodeTester()
    {
        
        Node thisNode = new Node("I'm stored in a Node object.");
        System.out.println(thisNode.getText());
        
    }
    
}