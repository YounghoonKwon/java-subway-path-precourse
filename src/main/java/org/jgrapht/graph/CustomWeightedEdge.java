package org.jgrapht.graph;

public class CustomWeightedEdge
        extends
        IntrusiveWeightedEdge
{
    private static final long serialVersionUID = -3259071493169286685L;

    public Object getSource()
    {
        return source;
    }

    public Object getTarget()
    {
        return target;
    }

    public double getWeight()
    {
        return weight;
    }

    @Override
    public String toString()
    {
        return "(" + source + " : " + target + ")";
    }
}
