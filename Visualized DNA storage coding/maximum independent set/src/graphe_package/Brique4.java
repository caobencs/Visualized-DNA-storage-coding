package graphe_package;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Bibi on 24/10/2016.
 */
public class Brique4 {

    /**
     * Retourne le sommet possédant le plus de voisins
     * @param g le graphe
     * @return l'index du sommet
     */
    public static int getV(Graphe g){
        int v = g.getFirst();
        for (Map.Entry<Integer, ArrayList<Integer>> entry : g.getGraphe().entrySet()){
            if(g.nombreVoisins(v) < g.nombreVoisins(entry.getKey())){
                v=entry.getKey();
            }
        }
        return v;
    }

    /**
     * Teste si la liste est une clique
     * @param voisins   la liste à tester
     * @param g         le graphe
     * @return true si c'est une clique
     */
    private static boolean clique(List<Integer> voisins, Graphe g){
    	if(voisins.size()==0){
    		return true;
    	}
        for(int v:voisins){
            for(int u:voisins){
                if(v != u){
                    if(! g.estVoisin(v,u)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Cherche les miroirs d'un sommet et retourne le graphe modifié
     * @param v         le sommet à travailler
     * @param graphe    le graphe
     * @return          Le graphe moins le sommet et ses miroirs
     */
    public static Graphe miroir(int v, Graphe graphe){
        Graphe g = graphe.clone();
        List<Integer> sommetsASupprimer = new ArrayList<>();
        sommetsASupprimer.add(v);
        // Pour tous les voisins u à distance 2 de v
        List<Integer> voisins2 = new ArrayList<>();
        for(int voisinDeV:g.voisinage(v)){
            for(int voisin2DeV:g.voisinage(voisinDeV)){
                if(v != voisin2DeV && !g.estVoisin(v,voisin2DeV) && !voisins2.contains(voisin2DeV)){
                    voisins2.add(voisin2DeV);
                }
            }
        }
        // Pour tous les voisins w de v non voisins de u => clique
        List<Integer> cliqueATester = new ArrayList<>();
        for(int u:voisins2){
            for(int w:g.voisinage(v)) {
                if (! g.estVoisin(w, u)){
                    cliqueATester.add(w);
                }
            }
            if(clique(cliqueATester,g)){
                // rajouter u  la liste des sommets à supprimer
                if(! sommetsASupprimer.contains(u)) {
                    sommetsASupprimer.add(u);
                }
            }
            cliqueATester.clear();
        }
        for(int aSupp:sommetsASupprimer) {
            g.supprimerSommet(aSupp);
        }
        return g;
    }

    /**
     * Renvoie le graphe sans un sommet et ses voisins
     * @param v     le sommet
     * @param g     le graphe
     * @return  le graphe moins le sommet et ses voisins
     */
    public static Graphe voisins(int v, Graphe g){
        g.suppressionSommetsVoisins(v);
        return g;
    }
}