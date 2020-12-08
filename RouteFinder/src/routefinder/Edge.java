/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routefinder;

/**
 *
 * @author l-bishop
 */
public class Edge 
{
    private Node sourceNode;
    private Node targetNode;
    public Node getSourceNode()
    {
        return sourceNode;
    }
    public void setSourceNode(Node newRoad)
    {
        this.sourceNode = newRoad;
    }
    public Node getTargetNode()
    {
        return targetNode;
    }
    public void setTargetNode(Node newCorner)
    {
        this.targetNode = newCorner;
    }
}
