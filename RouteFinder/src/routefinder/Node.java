/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routefinder;

import java.util.List;

/**
 *
 * @author l-bishop
 */
public class Node implements Comparable<Node>
{   
    private boolean visited;
    protected int curvature;
    private double comparableValue;
    private List<Edge> adjacenciesList;
    private Node predecessor;
    private double distance = Double.MAX_VALUE;
    public void addNeighbor(Road road, Corner corner)
    {
        Edge edge = new Edge(corner,road);
    }
    public double getComparableValue()
    {
        return comparableValue;
    }
    public int getCurvature()
    {
        return curvature;
    }
    public void setCurvature(int curvature)
    {
        this.curvature = curvature;
    }
    public List getAdjacenciesList()
    {
        return adjacenciesList;
    }
    public boolean isVisited()
    {
        return visited;
    }
    public void setVisited(boolean visited)
    {
        this.visited = visited;
    }
    public Node getPredecessor()
    {
        return predecessor;
    }
    public void setPredecessor(Node precedessor)
    {
        this.predecessor = predecessor;
    }
    public double getDistance()
    {
        return distance;
    }
    public void setDistance(double distance)
    {
        this.distance = distance;
    }
    
    @Override
    public int compareTo(Node othernode) 
    {
    	return Double.compare(this.getComparableValue(), othernode.getComparableValue());
    }
}
