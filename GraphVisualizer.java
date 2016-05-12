import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.samples.SimpleGraphDraw;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.util.HashMap;

class node {
	public int nodeID;
	public String name;
	
	public node(int id, String n) {
		nodeID = id;
		name = n;
	}
}

public class GraphVisualizer {
	private DigitalStoryWorld world;
	private Graph<node, Integer> graph;
	private HashMap<Integer, node> help;
	
	public static void main(String[] args) {
		GraphVisualizer gv = new GraphVisualizer(DbHelper.getAllNodes(), DbHelper.getAllEdges());
		gv.visualize();
	}
	
	public GraphVisualizer(HashMap<Integer, String> nodes, int[][] edges) {
		help = new HashMap<Integer, node>();
		for(Integer nodeId : nodes.keySet()) {
			help.put(nodeId, new node(nodeId, nodes.get(nodeId)));
			graph.addVertex(help.get(nodeId)); 
		}
		for(int i = 0; i < edges.length; i++) {
			graph.addEdge(edges[i][2], help.get(edges[i][0]), help.get(edges[i][1]), EdgeType.DIRECTED);
		}
	}
	
	public void visualize() {
		Layout<node, Integer> layout = new DAGLayout<node,Integer>(graph);
		layout.setSize(new Dimension(300,300));
		BasicVisualizationServer<node,Integer> vv =
				 new BasicVisualizationServer<node,Integer>(layout);
				 vv.setPreferredSize(new Dimension(350,350));
				 
		 JFrame frame = new JFrame("Simple Graph View");
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.getContentPane().add(vv);
		 frame.pack();
		 frame.setVisible(true); 
	}
}
