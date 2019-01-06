package graph;

public class PriorityQueueNode
{
    private int ID;
    private int cost;
    public PriorityQueueNode(int ID,int cost )
    {
       this.ID = ID;
       this.cost = cost;

    }

    public int getCost() {
        return cost;
    }
    public int getID()
    {
        return ID;
    }
    public void setPriority(int cost)
    {
        this.cost = cost;
    }
}
