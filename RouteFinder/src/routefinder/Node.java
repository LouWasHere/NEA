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
    protected String name;
    protected int curvature;
    private double comparableValue;
    private List<Edge> adjacenciesList;
    public void addNeighbor(Road road, Corner corner)
    {
        Edge edge = new Edge(corner,road);
    }
    public double getComparableValue()
    {
        return comparableValue;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public int getCurvature()
    {
        return curvature;
    }
    public void setCurvature(int curvature)
    {
        this.curvature = curvature;
    }
    
    @Override
    public int compareTo(Node othernode) 
    {
    	return Double.compare(this.getComparableValue(), othernode.getComparableValue());
    }
}
