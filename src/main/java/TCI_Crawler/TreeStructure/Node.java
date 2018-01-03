package TCI_Crawler.TreeStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Node<T> {
		T data;
		boolean visited;



	int weight = -1;
		HashSet<Node> neighbours;
 
		public Node(T data)
		{
			this.data=data;
			this.neighbours=new HashSet<>();

		}

		public T getData(){return data;}
		public void addneighbours(Node neighbourNode)
		{
			this.neighbours.add(neighbourNode);
		}
		public List getNeighbours() {return neighbours.stream().collect(Collectors.toCollection(ArrayList::new));
		}
		public void setNeighbours(List neighbours) {
			this.neighbours = new HashSet<Node>(neighbours) ;

		}
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	}