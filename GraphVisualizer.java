import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.samples.SimpleGraphDraw;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

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
	
	public String toString() {
		return name;
	}
}
class edge {
	public int nodeID;
	public int count;
	
	public edge(int id, int n) {
		nodeID = id;
		count = n;
	}
	
	public String toString() {
		return ""+ count;
	}
}
public class GraphVisualizer {
	private DigitalStoryWorld world;
	private Graph<node, edge> graph;
	private HashMap<Integer, node> help;
	
	public static void main(String[] args) {
		GraphVisualizer gv = new GraphVisualizer(DbHelper.getAllNodes(), DbHelper.getAllEdges());
		gv.visualize();
	}
	
	public GraphVisualizer(HashMap<Integer, String> nodes, int[][] edges) {
		graph = new SparseMultigraph<node, edge>();
		help = new HashMap<Integer, node>();
		for(Integer nodeId : nodes.keySet()) {
			help.put(nodeId, new node(nodeId, nodes.get(nodeId)));
			System.out.println(nodeId);
			graph.addVertex(help.get(nodeId)); 
		}
		for(int i = 0; i < edges.length; i++) {
			graph.addEdge(new edge(i, edges[i][2]), help.get(edges[i][0]), help.get(edges[i][1]), EdgeType.DIRECTED);
		}
	}
	
	public void visualize() {
		Layout<node, edge> layout = new CircleLayout<node,edge>(graph);
		//layout.setSize(new Dimension(300,300));
		BasicVisualizationServer<node,edge> vv =
				 new BasicVisualizationServer<node,edge>(layout);
				 //vv.setPreferredSize(new Dimension(350,350));
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<node>());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<edge>());
		JFrame frame = new JFrame("Simple Graph View");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true); 
	}
}
